package com.peliQAn.framework.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for the dashboard page
 */
@Slf4j
public class DashboardPage extends BasePage {
    
    @FindBy(id = "dashboard-heading")
    private WebElement dashboardHeading;
    
    @FindBy(id = "welcome-message")
    private WebElement welcomeMessage;
    
    @FindBy(id = "logout-button")
    private WebElement logoutButton;
    
    /**
     * Check if on dashboard page
     * 
     * @return True if on dashboard page, false otherwise
     */
    @Step("Check if on dashboard page")
    public boolean isOnDashboardPage() {
        boolean isOnPage = isElementDisplayed(dashboardHeading);
        log.info("On dashboard page: {}", isOnPage);
        return isOnPage;
    }
    
    /**
     * Get welcome message text
     * 
     * @return Welcome message text
     */
    @Step("Get welcome message text")
    public String getWelcomeMessage() {
        String text = getText(welcomeMessage);
        log.info("Welcome message text: {}", text);
        return text;
    }
    
    /**
     * Click logout button
     */
    @Step("Click logout button")
    public void clickLogoutButton() {
        click(logoutButton);
        log.info("Clicked logout button");
    }
}