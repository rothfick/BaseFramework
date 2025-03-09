package com.peliQAn.framework.utils;

import com.peliQAn.framework.config.PropertyManager;
import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for taking screenshots
 */
@Slf4j
public class ScreenshotUtils {
    private static final String SCREENSHOT_PATH = PropertyManager.getInstance().getProperty("screenshot.path", "target/screenshots");

    private ScreenshotUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Takes a screenshot and saves it to the configured directory
     *
     * @param driver        WebDriver instance
     * @param screenshotName Base name for the screenshot
     * @return Path to the saved screenshot
     */
    public static String takeScreenshot(WebDriver driver, String screenshotName) {
        if (driver == null) {
            log.error("WebDriver is null, cannot take screenshot");
            return null;
        }

        if (!(driver instanceof TakesScreenshot)) {
            log.error("WebDriver does not support taking screenshots");
            return null;
        }

        try {
            // Create directory if it doesn't exist
            File directory = new File(SCREENSHOT_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate timestamp for unique filename
            String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
            String fileName = String.format("%s_%s.png", screenshotName, timestamp);
            Path screenshotPath = Paths.get(SCREENSHOT_PATH, fileName);

            // Take screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), screenshotPath);

            String fullPath = screenshotPath.toAbsolutePath().toString();
            log.info("Screenshot saved to: {}", fullPath);

            // Attach to Allure report
            attachScreenshotToAllure(driver);

            return fullPath;
        } catch (IOException e) {
            log.error("Failed to save screenshot: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Attaches screenshot to Allure report
     *
     * @param driver WebDriver instance
     * @return Screenshot as byte array
     */
    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] attachScreenshotToAllure(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}