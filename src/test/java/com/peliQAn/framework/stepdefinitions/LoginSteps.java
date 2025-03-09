package com.peliQAn.framework.stepdefinitions;

import com.peliQAn.framework.pages.DashboardPage;
import com.peliQAn.framework.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

/**
 * Step definitions for login feature
 */
@Slf4j
public class LoginSteps {
    private final LoginPage loginPage;
    private DashboardPage dashboardPage;

    public LoginSteps() {
        this.loginPage = new LoginPage();
    }

    @When("I enter username {string} in the login form")
    public void iEnterUsernameInTheLoginForm(String username) {
        loginPage.enterUsername(username);
    }

    @When("I enter password {string} in the login form")
    public void iEnterPasswordInTheLoginForm(String password) {
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void iClickTheLoginButton() {
        loginPage.clickLoginButton();
    }

    @Then("I should be logged in successfully")
    public void iShouldBeLoggedInSuccessfully() {
        // Assumption that successful login redirects to dashboard
        dashboardPage = new DashboardPage();
        Assert.assertTrue(dashboardPage.isOnDashboardPage(), "Not redirected to dashboard page after login");
    }

    @Then("the dashboard page should be displayed")
    public void theDashboardPageShouldBeDisplayed() {
        Assert.assertTrue(dashboardPage.isOnDashboardPage(), "Dashboard page is not displayed");
    }

    @Then("the welcome message should contain the username {string}")
    public void theWelcomeMessageShouldContainTheUsername(String username) {
        String welcomeMessage = dashboardPage.getWelcomeMessage();
        Assert.assertTrue(welcomeMessage.contains(username), 
                String.format("Welcome message '%s' does not contain username '%s'", welcomeMessage, username));
    }

    @Then("I should see an error message {string}")
    public void iShouldSeeAnErrorMessage(String expectedErrorMessage) {
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed");
        String actualErrorMessage = loginPage.getErrorMessageText();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message text is incorrect");
    }

    @Then("I should remain on the login page")
    public void iShouldRemainOnTheLoginPage() {
        Assert.assertTrue(loginPage.isOnLoginPage(), "Not on login page");
    }

    @Given("I am logged in with username {string} and password {string}")
    public void iAmLoggedInWithUsernameAndPassword(String username, String password) {
        loginPage.navigateToLoginPage();
        loginPage.login(username, password);
        
        // Create dashboard page instance after successful login
        dashboardPage = new DashboardPage();
        Assert.assertTrue(dashboardPage.isOnDashboardPage(), "Login failed, not on dashboard page");
    }

    @When("I click the logout button")
    public void iClickTheLogoutButton() {
        dashboardPage.clickLogoutButton();
    }

    @Then("I should be logged out successfully")
    public void iShouldBeLoggedOutSuccessfully() {
        Assert.assertTrue(loginPage.isOnLoginPage(), "Not redirected to login page after logout");
    }

    @Then("I should be redirected to the login page")
    public void iShouldBeRedirectedToTheLoginPage() {
        Assert.assertTrue(loginPage.isOnLoginPage(), "Not on login page");
    }
}