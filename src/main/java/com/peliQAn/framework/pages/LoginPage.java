package com.peliQAn.framework.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for the login page
 */
@Slf4j
public class LoginPage extends BasePage {
    
    @FindBy(id = "username")
    private WebElement usernameField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    @FindBy(id = "login-button")
    private WebElement loginButton;
    
    @FindBy(id = "error-message")
    private WebElement errorMessage;
    
    /**
     * Navigate to the login page
     * 
     * @return This LoginPage instance
     */
    @Step("Navigate to login page")
    public LoginPage navigateToLoginPage() {
        navigateToBaseUrl();
        log.info("Navigated to login page");
        return this;
    }
    
    /**
     * Enter username
     * 
     * @param username Username to enter
     * @return This LoginPage instance
     */
    @Step("Enter username: {username}")
    public LoginPage enterUsername(String username) {
        type(usernameField, username);
        log.info("Entered username: {}", username);
        return this;
    }
    
    /**
     * Enter password
     * 
     * @param password Password to enter
     * @return This LoginPage instance
     */
    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        type(passwordField, password);
        log.info("Entered password (hidden)");
        return this;
    }
    
    /**
     * Click login button
     * 
     * @return DashboardPage instance if login is successful
     */
    @Step("Click login button")
    public void clickLoginButton() {
        click(loginButton);
        log.info("Clicked login button");
    }
    
    /**
     * Login with credentials
     * 
     * @param username Username to enter
     * @param password Password to enter
     */
    @Step("Login with username: {username} and password")
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        log.info("Attempted login with username: {}", username);
    }
    
    /**
     * Check if error message is displayed
     * 
     * @return True if error message is displayed, false otherwise
     */
    @Step("Check if error message is displayed")
    public boolean isErrorMessageDisplayed() {
        boolean isDisplayed = isElementDisplayed(errorMessage);
        log.info("Error message displayed: {}", isDisplayed);
        return isDisplayed;
    }
    
    /**
     * Get error message text
     * 
     * @return Error message text
     */
    @Step("Get error message text")
    public String getErrorMessageText() {
        String text = getText(errorMessage);
        log.info("Error message text: {}", text);
        return text;
    }
    
    /**
     * Check if on login page
     * 
     * @return True if on login page, false otherwise
     */
    @Step("Check if on login page")
    public boolean isOnLoginPage() {
        boolean isOnPage = isElementDisplayed(loginButton);
        log.info("On login page: {}", isOnPage);
        return isOnPage;
    }
}