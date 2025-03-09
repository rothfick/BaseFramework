package com.peliQAn.framework.stepdefinitions;

import com.peliQAn.framework.config.PropertyManager;
import com.peliQAn.framework.core.DriverFactory;
import com.peliQAn.framework.utils.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

/**
 * Cucumber hooks for setup and teardown
 */
@Slf4j
public class Hooks {
    private WebDriver driver;
    private final boolean screenshotOnFailure;
    
    public Hooks() {
        this.screenshotOnFailure = PropertyManager.getInstance().getBooleanProperty("screenshot.on.failure", true);
    }

    /**
     * Setup before each scenario
     *
     * @param scenario The current scenario
     */
    @Before
    public void setup(Scenario scenario) {
        log.info("Starting scenario: {}", scenario.getName());
        
        // Initialize WebDriver
        driver = DriverFactory.initDriver();
    }

    /**
     * Take screenshot after each step if scenario is failing
     *
     * @param scenario The current scenario
     */
    @AfterStep
    public void afterStep(Scenario scenario) {
        if (screenshotOnFailure && scenario.isFailed() && driver != null) {
            log.info("Taking screenshot after failed step in scenario: {}", scenario.getName());
            
            // Take screenshot and attach to report
            byte[] screenshot = ScreenshotUtils.attachScreenshotToAllure(driver);
            scenario.attach(screenshot, "image/png", "Failed Step Screenshot");
        }
    }

    /**
     * Cleanup after each scenario
     *
     * @param scenario The current scenario
     */
    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && driver != null) {
            log.info("Scenario failed: {}", scenario.getName());
            
            if (screenshotOnFailure) {
                log.info("Taking screenshot for failed scenario: {}", scenario.getName());
                
                // Take screenshot and attach to report
                byte[] screenshot = ScreenshotUtils.attachScreenshotToAllure(driver);
                scenario.attach(screenshot, "image/png", "Failed Scenario Screenshot");
            }
        }
        
        // Quit WebDriver
        DriverFactory.quitDriver();
        
        log.info("Finished scenario: {} - Status: {}", scenario.getName(), scenario.getStatus());
    }
}