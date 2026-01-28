package com.qforce.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for taking and managing screenshots
 */
public class ScreenshotUtil {
    
    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_DIR = ConfigReader.getProperty("screenshot.path", "screenshots/");
    
    /**
     * Capture screenshot and save with timestamp
     */
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            // Create screenshots directory if it doesn't exist
            File screenshotDir = new File(SCREENSHOT_DIR);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            
            // Generate unique filename with timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = screenshotName + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + fileName;
            
            // Capture screenshot
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(filePath);
            
            // Copy to destination
            FileUtils.copyFile(sourceFile, destinationFile);
            
            logger.info("Screenshot captured: {}", filePath);
            return filePath;
            
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}", screenshotName, e);
            return null;
        }
    }
    
    /**
     * Capture screenshot for failed test
     */
    public static String captureFailureScreenshot(WebDriver driver, String testName) {
        return captureScreenshot(driver, "FAILED_" + testName);
    }
    
    /**
     * Get screenshot as Base64 string for embedding in reports
     */
    public static String getBase64Screenshot(WebDriver driver) {
        try {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            return takesScreenshot.getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            logger.error("Failed to capture Base64 screenshot", e);
            return null;
        }
    }
}
