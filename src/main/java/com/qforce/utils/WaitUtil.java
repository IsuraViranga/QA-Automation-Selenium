package com.qforce.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

/**
 * Utility class for explicit waits
 */
public class WaitUtil {
    
    private static final Logger logger = LogManager.getLogger(WaitUtil.class);
    private static final int DEFAULT_WAIT_TIME = ConfigReader.getExplicitWait();
    
    /**
     * Wait for element to be visible
     */
    public static WebElement waitForElementVisible(WebDriver driver, By locator) {
        return waitForElementVisible(driver, locator, DEFAULT_WAIT_TIME);
    }
    
    public static WebElement waitForElementVisible(WebDriver driver, By locator, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element not visible: {}", locator, e);
            throw e;
        }
    }
    
    /**
     * Wait for element to be clickable
     */
    public static WebElement waitForElementClickable(WebDriver driver, By locator) {
        return waitForElementClickable(driver, locator, DEFAULT_WAIT_TIME);
    }
    
    public static WebElement waitForElementClickable(WebDriver driver, By locator, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            logger.error("Element not clickable: {}", locator, e);
            throw e;
        }
    }
    
    /**
     * Wait for element to be present in DOM
     */
    public static WebElement waitForElementPresent(WebDriver driver, By locator) {
        return waitForElementPresent(driver, locator, DEFAULT_WAIT_TIME);
    }
    
    public static WebElement waitForElementPresent(WebDriver driver, By locator, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element not present: {}", locator, e);
            throw e;
        }
    }
    
    /**
     * Wait for element to disappear
     */
    public static boolean waitForElementInvisible(WebDriver driver, By locator) {
        return waitForElementInvisible(driver, locator, DEFAULT_WAIT_TIME);
    }
    
    public static boolean waitForElementInvisible(WebDriver driver, By locator, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element still visible: {}", locator, e);
            return false;
        }
    }
    
    /**
     * Wait for URL to contain text
     */
    public static boolean waitForUrlContains(WebDriver driver, String urlFragment) {
        return waitForUrlContains(driver, urlFragment, DEFAULT_WAIT_TIME);
    }
    
    public static boolean waitForUrlContains(WebDriver driver, String urlFragment, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            return wait.until(ExpectedConditions.urlContains(urlFragment));
        } catch (Exception e) {
            logger.error("URL does not contain: {}", urlFragment, e);
            return false;
        }
    }
    
    /**
     * Wait for alert to be present
     */
    public static boolean waitForAlert(WebDriver driver) {
        return waitForAlert(driver, DEFAULT_WAIT_TIME);
    }
    
    public static boolean waitForAlert(WebDriver driver, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (Exception e) {
            logger.error("Alert not present", e);
            return false;
        }
    }
}
