package com.qforce.pages;

import com.qforce.utils.DriverManager;
import com.qforce.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Base Page class with common methods for all page objects
 */
public class BasePage {
    
    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    
    public BasePage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Click on element
     */
    protected void click(By locator) {
        logger.info("Clicking element: {}", locator);
        WaitUtil.waitForElementClickable(driver, locator).click();
    }
    
    protected void click(WebElement element) {
        logger.info("Clicking element");
        element.click();
    }
    
    /**
     * Enter text into element
     */
    protected void sendKeys(By locator, String text) {
        logger.info("Entering text '{}' into element: {}", text, locator);
        WebElement element = WaitUtil.waitForElementVisible(driver, locator);
        element.clear();
        element.sendKeys(text);
    }
    
    protected void sendKeys(WebElement element, String text) {
        logger.info("Entering text: {}", text);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Get text from element
     */
    protected String getText(By locator) {
        String text = WaitUtil.waitForElementVisible(driver, locator).getText();
        logger.info("Getting text from element {}: {}", locator, text);
        return text;
    }
    
    protected String getText(WebElement element) {
        String text = element.getText();
        logger.info("Getting text: {}", text);
        return text;
    }
    
    /**
     * Check if element is displayed
     */
    protected boolean isDisplayed(By locator) {
        try {
            boolean displayed = driver.findElement(locator).isDisplayed();
            logger.info("Element {} displayed: {}", locator, displayed);
            return displayed;
        } catch (Exception e) {
            logger.info("Element {} not displayed", locator);
            return false;
        }
    }
    
    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if element is enabled
     */
    protected boolean isEnabled(By locator) {
        boolean enabled = driver.findElement(locator).isEnabled();
        logger.info("Element {} enabled: {}", locator, enabled);
        return enabled;
    }
    
    /**
     * Select from dropdown by visible text
     */
    protected void selectByVisibleText(By locator, String text) {
        logger.info("Selecting '{}' from dropdown: {}", text, locator);
        WebElement element = WaitUtil.waitForElementVisible(driver, locator);
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }
    
    protected void selectByVisibleText(WebElement element, String text) {
        logger.info("Selecting from dropdown: {}", text);
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }
    
    /**
     * Select from dropdown by value
     */
    protected void selectByValue(By locator, String value) {
        logger.info("Selecting value '{}' from dropdown: {}", value, locator);
        WebElement element = WaitUtil.waitForElementVisible(driver, locator);
        Select select = new Select(element);
        select.selectByValue(value);
    }
    
    /**
     * Get selected option from dropdown
     */
    protected String getSelectedOption(By locator) {
        WebElement element = WaitUtil.waitForElementVisible(driver, locator);
        Select select = new Select(element);
        String selectedText = select.getFirstSelectedOption().getText();
        logger.info("Selected option from {}: {}", locator, selectedText);
        return selectedText;
    }
    
    protected String getSelectedOption(WebElement element) {
        Select select = new Select(element);
        String selectedText = select.getFirstSelectedOption().getText();
        logger.info("Selected option: {}", selectedText);
        return selectedText;
    }
    
    /**
     * Wait for element
     */
    protected WebElement waitForElement(By locator) {
        return WaitUtil.waitForElementVisible(driver, locator);
    }
    
    /**
     * Navigate to URL
     */
    public void navigateTo(String url) {
        logger.info("Navigating to URL: {}", url);
        driver.get(url);
    }
    
    /**
     * Get current URL
     */
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.info("Current URL: {}", url);
        return url;
    }
    
    /**
     * Get page title
     */
    protected String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Page title: {}", title);
        return title;
    }
    
    /**
     * Scroll to element
     */
    protected void scrollToElement(WebElement element) {
        logger.info("Scrolling to element");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    /**
     * JavaScript click
     */
    protected void jsClick(WebElement element) {
        logger.info("JavaScript clicking element");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
    
    /**
     * Get attribute value
     */
    protected String getAttribute(By locator, String attribute) {
        String value = driver.findElement(locator).getAttribute(attribute);
        logger.info("Getting attribute '{}' from {}: {}", attribute, locator, value);
        return value;
    }
    
    protected String getAttribute(WebElement element, String attribute) {
        String value = element.getAttribute(attribute);
        logger.info("Getting attribute '{}': {}", attribute, value);
        return value;
    }
    
    /**
     * Clear element
     */
    protected void clear(WebElement element) {
        logger.info("Clearing element");
        element.clear();
    }
    
    /**
     * Find elements
     */
    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }
    
    /**
     * Accept alert
     */
    protected void acceptAlert() {
        logger.info("Accepting alert");
        driver.switchTo().alert().accept();
    }
    
    /**
     * Dismiss alert
     */
    protected void dismissAlert() {
        logger.info("Dismissing alert");
        driver.switchTo().alert().dismiss();
    }
    
    /**
     * Get alert text
     */
    protected String getAlertText() {
        String alertText = driver.switchTo().alert().getText();
        logger.info("Alert text: {}", alertText);
        return alertText;
    }
}
