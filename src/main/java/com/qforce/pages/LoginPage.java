package com.qforce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for Login Page
 */
public class LoginPage extends BasePage {
    
    // Locators using @FindBy annotations
    @FindBy(name = "username")
    private WebElement usernameField;
    
    @FindBy(name = "password")
    private WebElement passwordField;
    
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;
    
    @FindBy(css = "div.alert.alert-danger")
    private WebElement errorMessage;
    
    // Alternative: Using By locators
    // private By usernameLocator = By.name("username");
    // private By passwordLocator = By.name("password");
    // private By loginButtonLocator = By.xpath("//button[@type='submit' and text()='Login']");
    
    /**
     * Navigate to login page
     */
    public void navigateToLoginPage(String url) {
        logger.info("Navigating to login page: {}", url);
        navigateTo(url);
    }
    
    /**
     * Enter username
     */
    public void enterUsername(String username) {
        logger.info("Entering username: {}", username);
        sendKeys(usernameField, username);
    }
    
    /**
     * Enter password
     */
    public void enterPassword(String password) {
        logger.info("Entering password");
        sendKeys(passwordField, password);
    }
    
    /**
     * Click login button
     */
    public void clickLogin() {
        logger.info("Clicking login button");
        click(loginButton);
    }
    
    /**
     * Complete login process
     */
    public void login(String username, String password) {
        logger.info("Logging in as: {}", username);
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
    
    /**
     * Get error message
     */
    public String getErrorMessage() {
        if (isDisplayed(errorMessage)) {
            return getText(errorMessage);
        }
        return "";
    }
    
    /**
     * Verify login page is displayed
     */
    public boolean isLoginPageDisplayed() {
        return isDisplayed(usernameField) && isDisplayed(passwordField) && isDisplayed(loginButton);
    }
}
