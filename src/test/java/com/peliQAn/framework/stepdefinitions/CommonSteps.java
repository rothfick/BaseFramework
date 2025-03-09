package com.peliQAn.framework.stepdefinitions;

import com.peliQAn.framework.config.PropertyManager;
import com.peliQAn.framework.core.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Common step definitions for Cucumber scenarios
 */
@Slf4j
public class CommonSteps {
    private final WebDriver driver;
    private final PropertyManager propertyManager;

    public CommonSteps() {
        this.driver = DriverFactory.getDriver();
        this.propertyManager = PropertyManager.getInstance();
    }

    @Given("I navigate to the application")
    public void iNavigateToTheApplication() {
        String baseUrl = propertyManager.getProperty("app.baseUrl");
        log.info("Navigating to base URL: {}", baseUrl);
        driver.get(baseUrl);
    }

    @Given("I navigate to {string}")
    public void iNavigateTo(String url) {
        log.info("Navigating to URL: {}", url);
        driver.get(url);
    }

    @Then("the page title should be {string}")
    public void thePageTitleShouldBe(String expectedTitle) {
        String actualTitle = driver.getTitle();
        log.info("Verifying page title. Expected: '{}', Actual: '{}'", expectedTitle, actualTitle);
        Assert.assertEquals(actualTitle, expectedTitle, "Page title is incorrect");
    }

    @Then("the page title should contain {string}")
    public void thePageTitleShouldContain(String expectedTitlePart) {
        String actualTitle = driver.getTitle();
        log.info("Verifying page title contains. Expected part: '{}', Actual: '{}'", expectedTitlePart, actualTitle);
        Assert.assertTrue(actualTitle.contains(expectedTitlePart), 
                String.format("Page title '%s' does not contain expected '%s'", actualTitle, expectedTitlePart));
    }

    @When("I wait for {int} seconds")
    public void iWaitForSeconds(int seconds) {
        log.info("Waiting for {} seconds", seconds);
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Wait interrupted", e);
        }
    }

    @Then("the current URL should be {string}")
    public void theCurrentUrlShouldBe(String expectedUrl) {
        String actualUrl = driver.getCurrentUrl();
        log.info("Verifying current URL. Expected: '{}', Actual: '{}'", expectedUrl, actualUrl);
        Assert.assertEquals(actualUrl, expectedUrl, "Current URL is incorrect");
    }

    @Then("the current URL should contain {string}")
    public void theCurrentUrlShouldContain(String expectedUrlPart) {
        String actualUrl = driver.getCurrentUrl();
        log.info("Verifying current URL contains. Expected part: '{}', Actual: '{}'", expectedUrlPart, actualUrl);
        Assert.assertTrue(actualUrl.contains(expectedUrlPart), 
                String.format("Current URL '%s' does not contain expected '%s'", actualUrl, expectedUrlPart));
    }
}