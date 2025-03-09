package com.peliQAn.framework.pages;

import com.peliQAn.framework.config.PropertyManager;
import com.peliQAn.framework.core.DriverFactory;
import com.peliQAn.framework.utils.ScreenshotUtils;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Base class for all Page Objects with common methods
 */
@Slf4j
public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Actions actions;
    protected final JavascriptExecutor js;
    protected final String baseUrl;

    protected BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
        this.baseUrl = PropertyManager.getInstance().getProperty("app.baseUrl");
        
        PageFactory.initElements(driver, this);
    }

    /**
     * Navigate to a page URL
     */
    @Step("Navigate to URL: {url}")
    public void navigateTo(String url) {
        log.info("Navigating to URL: {}", url);
        driver.get(url);
    }

    /**
     * Navigate to the application base URL
     */
    @Step("Navigate to base URL")
    public void navigateToBaseUrl() {
        log.info("Navigating to base URL: {}", baseUrl);
        driver.get(baseUrl);
    }

    /**
     * Wait for an element to be clickable
     */
    protected WebElement waitForElementToBeClickable(WebElement element) {
        log.debug("Waiting for element to be clickable: {}", element);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for an element to be clickable by locator
     */
    protected WebElement waitForElementToBeClickable(By locator) {
        log.debug("Waiting for element to be clickable by locator: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for an element to be visible
     */
    protected WebElement waitForElementToBeVisible(WebElement element) {
        log.debug("Waiting for element to be visible: {}", element);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for an element to be visible by locator
     */
    protected WebElement waitForElementToBeVisible(By locator) {
        log.debug("Waiting for element to be visible by locator: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Click on an element with wait
     */
    @Step("Click on element: {element}")
    protected void click(WebElement element) {
        try {
            waitForElementToBeClickable(element).click();
            log.debug("Clicked on element: {}", element);
        } catch (StaleElementReferenceException e) {
            log.warn("StaleElementReferenceException occurred, retrying click operation", e);
            WebElement refreshedElement = waitForElementToBeClickable(element);
            refreshedElement.click();
        }
    }

    /**
     * Click on an element with JavaScript
     */
    @Step("JavaScript click on element: {element}")
    protected void jsClick(WebElement element) {
        waitForElementToBeVisible(element);
        log.debug("JavaScript click on element: {}", element);
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * Type text into an element
     */
    @Step("Type text: {text} into element: {element}")
    protected void type(WebElement element, String text) {
        WebElement visibleElement = waitForElementToBeVisible(element);
        visibleElement.clear();
        visibleElement.sendKeys(text);
        log.debug("Typed text: '{}' into element: {}", text, element);
    }

    /**
     * Clear text from an element
     */
    @Step("Clear text from element: {element}")
    protected void clear(WebElement element) {
        waitForElementToBeVisible(element).clear();
        log.debug("Cleared text from element: {}", element);
    }

    /**
     * Get text from an element
     */
    @Step("Get text from element: {element}")
    protected String getText(WebElement element) {
        String text = waitForElementToBeVisible(element).getText();
        log.debug("Got text: '{}' from element: {}", text, element);
        return text;
    }

    /**
     * Check if an element is displayed
     */
    @Step("Check if element is displayed: {element}")
    protected boolean isElementDisplayed(WebElement element) {
        try {
            boolean isDisplayed = element.isDisplayed();
            log.debug("Element is displayed: {} - {}", isDisplayed, element);
            return isDisplayed;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            log.debug("Element is not displayed: {}", element);
            return false;
        }
    }

    /**
     * Check if an element is displayed with wait
     */
    @Step("Check if element is displayed with wait: {locator}")
    protected boolean isElementDisplayed(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            longWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            log.debug("Element is displayed after waiting: {}", locator);
            return true;
        } catch (TimeoutException e) {
            log.debug("Element is not displayed after waiting: {}", locator);
            return false;
        }
    }

    /**
     * Select option by visible text
     */
    @Step("Select option by text: {text} from dropdown: {element}")
    protected void selectByVisibleText(WebElement element, String text) {
        Select select = new Select(waitForElementToBeVisible(element));
        select.selectByVisibleText(text);
        log.debug("Selected option by text: '{}' from dropdown: {}", text, element);
    }

    /**
     * Select option by value
     */
    @Step("Select option by value: {value} from dropdown: {element}")
    protected void selectByValue(WebElement element, String value) {
        Select select = new Select(waitForElementToBeVisible(element));
        select.selectByValue(value);
        log.debug("Selected option by value: '{}' from dropdown: {}", value, element);
    }

    /**
     * Get selected option text from dropdown
     */
    @Step("Get selected option text from dropdown: {element}")
    protected String getSelectedOptionText(WebElement element) {
        Select select = new Select(waitForElementToBeVisible(element));
        String text = select.getFirstSelectedOption().getText();
        log.debug("Selected option text: '{}' from dropdown: {}", text, element);
        return text;
    }

    /**
     * Hover over an element
     */
    @Step("Hover over element: {element}")
    protected void hoverOver(WebElement element) {
        actions.moveToElement(waitForElementToBeVisible(element)).perform();
        log.debug("Hovering over element: {}", element);
    }

    /**
     * Scroll to element
     */
    @Step("Scroll to element: {element}")
    protected void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        log.debug("Scrolled to element: {}", element);
    }

    /**
     * Take screenshot
     */
    @Step("Take screenshot with name: {screenshotName}")
    protected void takeScreenshot(String screenshotName) {
        ScreenshotUtils.takeScreenshot(driver, screenshotName);
    }

    /**
     * Check if page title contains expected text
     */
    @Step("Check if page title contains: {expectedTitle}")
    protected boolean pageTitleContains(String expectedTitle) {
        try {
            wait.until(ExpectedConditions.titleContains(expectedTitle));
            log.debug("Page title contains: '{}'", expectedTitle);
            return true;
        } catch (TimeoutException e) {
            log.debug("Page title does not contain: '{}'", expectedTitle);
            return false;
        }
    }

    /**
     * Wait for page to load completely
     */
    @Step("Wait for page to load completely")
    protected void waitForPageToLoad() {
        wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
        log.debug("Page loaded completely");
    }

    /**
     * Switch to frame
     */
    @Step("Switch to frame: {frame}")
    protected void switchToFrame(WebElement frame) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
        log.debug("Switched to frame: {}", frame);
    }

    /**
     * Switch to default content
     */
    @Step("Switch to default content")
    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        log.debug("Switched to default content");
    }

    /**
     * Get all elements by locator
     */
    @Step("Get all elements by locator: {locator}")
    protected List<WebElement> findElements(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        log.debug("Found {} elements with locator: {}", elements.size(), locator);
        return elements;
    }
}