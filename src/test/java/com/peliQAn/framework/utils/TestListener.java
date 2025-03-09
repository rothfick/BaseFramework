package com.peliQAn.framework.utils;

import com.peliQAn.framework.config.PropertyManager;
import com.peliQAn.framework.core.DriverFactory;
import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG listener for test execution events
 */
@Slf4j
public class TestListener implements ITestListener {
    private final boolean screenshotOnFailure;
    
    public TestListener() {
        this.screenshotOnFailure = PropertyManager.getInstance().getBooleanProperty("screenshot.on.failure", true);
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("Starting test: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Test passed: {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("Test failed: {}", result.getName());
        
        if (screenshotOnFailure) {
            WebDriver driver = DriverFactory.getDriver();
            if (driver != null) {
                log.info("Taking screenshot for failed test: {}", result.getName());
                String screenshotPath = ScreenshotUtils.takeScreenshot(driver, result.getName() + "_failure");
                log.info("Screenshot saved to: {}", screenshotPath);
            } else {
                log.warn("WebDriver is not available, cannot take screenshot");
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.info("Test skipped: {}", result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.info("Test failed but within success percentage: {}", result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        log.info("Starting test suite: {}", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("Finished test suite: {}", context.getName());
        log.info("Total tests: {}, Passed: {}, Failed: {}, Skipped: {}", 
                context.getAllTestMethods().length,
                context.getPassedTests().size(),
                context.getFailedTests().size(),
                context.getSkippedTests().size());
    }

    /**
     * Attach test logs to Allure report
     *
     * @param log Log message
     * @return Log message
     */
    @Attachment(value = "Test log", type = "text/plain")
    private String attachLog(String log) {
        return log;
    }
}