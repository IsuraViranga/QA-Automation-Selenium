package com.qforce.keywords;

import com.qforce.pages.categories.CategoryPage;
import com.qforce.pages.login.LoginPage;
import com.qforce.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * Keyword Executor for executing test keywords
 * This enables keyword-driven testing approach
 */
public class KeywordExecutor {
    
    private static final Logger logger = LogManager.getLogger(KeywordExecutor.class);
    private WebDriver driver;
    private LoginPage loginPage;
    private CategoryPage categoryPage;
    
    public KeywordExecutor() {
        this.driver = DriverManager.getDriver();
        this.loginPage = new LoginPage();
        this.categoryPage = new CategoryPage();
    }
    
    /**
     * Execute keyword with parameters
     */
    public boolean executeKeyword(String keyword, String... params) {
        logger.info("Executing keyword: {} with params: {}", keyword, String.join(", ", params));
        
        try {
            switch (keyword.toUpperCase()) {
                case "NAVIGATE":
                    navigate(params[0]);
                    break;
                    
                case "LOGIN":
                    login(params[0], params[1]);
                    break;
                    
                case "ENTER_CATEGORY_NAME":
                    enterCategoryName(params[0]);
                    break;
                    
                case "SELECT_PARENT_CATEGORY":
                    selectParentCategory(params[0]);
                    break;
                    
                case "CLICK_SAVE":
                    clickSave();
                    break;
                    
                case "CLICK_CANCEL":
                    clickCancel();
                    break;
                    
                case "VERIFY_SUCCESS_MESSAGE":
                    return verifySuccessMessage(params[0]);
                    
                case "VERIFY_ERROR_MESSAGE":
                    return verifyErrorMessage(params[0]);
                    
                case "VERIFY_CATEGORY_IN_LIST":
                    return verifyCategoryInList(params[0]);
                    
                case "VERIFY_ON_ADD_PAGE":
                    return verifyOnAddPage();
                    
                case "VERIFY_ON_LIST_PAGE":
                    return verifyOnListPage();
                    
                case "NAVIGATE_TO_ADD_CATEGORY":
                    navigateToAddCategory();
                    break;
                    
                case "WAIT":
                    wait(Integer.parseInt(params[0]));
                    break;
                    
                default:
                    logger.error("Unknown keyword: {}", keyword);
                    return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("Error executing keyword: {}", keyword, e);
            return false;
        }
    }
    
    // Keyword Methods
    
    private void navigate(String url) {
        loginPage.navigateToLoginPage(url);
    }
    
    private void login(String username, String password) {
        loginPage.login(username, password);
    }
    
    private void navigateToAddCategory() {
        categoryPage.navigateToAddCategoryPage();
    }
    
    private void enterCategoryName(String categoryName) {
        categoryPage.enterCategoryName(categoryName);
    }
    
    private void selectParentCategory(String parentCategory) {
        categoryPage.selectParentCategory(parentCategory);
    }
    
    private void clickSave() {
        categoryPage.clickSave();
    }
    
    private void clickCancel() {
        categoryPage.clickCancel();
    }
    
    private boolean verifySuccessMessage(String expectedMessage) {
        String actualMessage = categoryPage.getSuccessMessage();
        boolean result = actualMessage.contains(expectedMessage);
        logger.info("Success message verification: Expected='{}', Actual='{}', Result={}", 
                   expectedMessage, actualMessage, result);
        return result;
    }
    
    private boolean verifyErrorMessage(String expectedMessage) {
        String actualMessage = categoryPage.getValidationError();
        if (actualMessage.isEmpty()) {
            actualMessage = categoryPage.getErrorMessage();
        }
        boolean result = actualMessage.contains(expectedMessage);
        logger.info("Error message verification: Expected='{}', Actual='{}', Result={}", 
                   expectedMessage, actualMessage, result);
        return result;
    }
    
    private boolean verifyCategoryInList(String categoryName) {
        boolean result = categoryPage.isCategoryInList(categoryName);
        logger.info("Category '{}' in list: {}", categoryName, result);
        return result;
    }
    
    private boolean verifyOnAddPage() {
        boolean result = categoryPage.isOnAddCategoryPage();
        logger.info("On Add Category page: {}", result);
        return result;
    }
    
    private boolean verifyOnListPage() {
        boolean result = categoryPage.isOnCategoryListPage();
        logger.info("On Category List page: {}", result);
        return result;
    }
    
    private void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
