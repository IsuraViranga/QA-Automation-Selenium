package com.qforce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page Object for Category Management Page
 */
public class CategoryPage extends BasePage {
    
    // Locators for Category Page Elements
    @FindBy(id = "name")
    private WebElement categoryNameField;
    
    @FindBy(id = "parentId")
    private WebElement parentCategoryDropdown;
    
    @FindBy(xpath = "//button[@type='submit' and text()='Save']")
    private WebElement saveButton;
    
    @FindBy(xpath = "//a[normalize-space()='Cancel']")
    private WebElement cancelButton;
    
    @FindBy(xpath = "//div[contains(@class,'alert-success')]//span")
    private WebElement successMessage;
    
    @FindBy(css = ".error-message")
    private WebElement errorMessage;
    
    @FindBy(css = "div.invalid-feedback")
    private WebElement validationError;
    
    @FindBy(xpath = "//a[@href='/ui/categories/add' and normalize-space()='Add A Category']")
    private WebElement addCategoryButton;
    
    @FindBy(xpath = "//table//tbody//tr")
    private List<WebElement> categoryRows;
    
    // By locators (alternative approach)
    private By categoryNameLocator = By.id("name");
    private By parentCategoryLocator = By.id("parentId");
    // private By saveButtonLocator = By.id("saveButton");
    // private By cancelButtonLocator = By.id("cancelButton");
    // private By successMessageLocator = By.cssSelector(".success-message");
    // private By errorMessageLocator = By.cssSelector(".error-message");
    // private By validationErrorLocator = By.cssSelector(".validation-error");
    
    /**
     * Navigate to Add Category page
     */
    public void navigateToAddCategoryPage() {
        logger.info("Navigating to Add Category page");
        String url = getCurrentUrl();
        if (!url.contains("/categories/add")) {
            navigateTo(url.replace(url.substring(url.lastIndexOf("/")), "/categories/add"));
        }
    }
    
    /**
     * Click Add Category button
     */
    public void clickAddCategory() {
        logger.info("Clicking Add Category button");
        click(addCategoryButton);
    }
    
    /**
     * Enter category name
     */
    public void enterCategoryName(String categoryName) {
        logger.info("Entering category name: {}", categoryName);
        sendKeys(categoryNameField, categoryName);
    }
    
    /**
     * Select parent category from dropdown
     */
    public void selectParentCategory(String parentCategory) {
        if (parentCategory != null && !parentCategory.isEmpty() && !parentCategory.equalsIgnoreCase("Main Category")) {
            logger.info("Selecting parent category: {}", parentCategory);
            selectByVisibleText(parentCategoryDropdown, parentCategory);
        } else {
            logger.info("Leaving Parent Category as Main Category (no selection)");
        }
    }
    
    /**
     * Get selected parent category
     */
    public String getSelectedParentCategory() {
        return getSelectedOption(parentCategoryLocator);
    }
    
    /**
     * Click Save button
     */
    public void clickSave() {
        logger.info("Clicking Save button");
        click(saveButton);
    }
    
    /**
     * Click Cancel button
     */
    public void clickCancel() {
        logger.info("Clicking Cancel button");
        click(cancelButton);
    }
    
    /**
     * Get success message
     */
    public String getSuccessMessage() {
        if (isDisplayed(successMessage)) {
            String message = getText(successMessage);
            logger.info("Success message: {}", message);
            return message;
        }
        return "";
    }
    
    /**
     * Get error message
     */
    public String getErrorMessage() {
        if (isDisplayed(errorMessage)) {
            String message = getText(errorMessage);
            logger.info("Error message: {}", message);
            return message;
        }
        return "";
    }
    
    /**
     * Get validation error message
     */
    public String getValidationError() {
        if (isDisplayed(validationError)) {
            String message = getText(validationError);
            logger.info("Validation error: {}", message);
            return message;
        }
        return "";
    }
    
    /**
     * Check if category name field is empty
     */
    public boolean isCategoryNameEmpty() {
        String value = getAttribute(categoryNameLocator, "value");
        return value == null || value.trim().isEmpty();
    }
    
    /**
     * Check if validation error is displayed below category name field
     */
    public boolean isValidationErrorDisplayed() {
        return isDisplayed(validationError);
    }
    
    /**
     * Verify navigation to category list page
     */
    public boolean isOnCategoryListPage() {
        String currentUrl = getCurrentUrl();
        boolean onListPage = currentUrl.contains("/categories") && !currentUrl.contains("/add");
        logger.info("On category list page: {}", onListPage);
        return onListPage;
    }
    
    /**
     * Get number of categories in the list
     */
    public int getCategoryCount() {
        int count = categoryRows.size();
        logger.info("Number of categories: {}", count);
        return count;
    }
    
    /**
     * Check if category exists in the list by name
     */
    public boolean isCategoryInList(String categoryName) {
        for (WebElement row : categoryRows) {
            if (row.getText().contains(categoryName)) {
                logger.info("Category '{}' found in list", categoryName);
                return true;
            }
        }
        logger.info("Category '{}' not found in list", categoryName);
        return false;
    }
    
    /**
     * Get parent category name for a specific category from the table
     */
    public String getParentCategoryFromTable(String categoryName) {
        for (WebElement row : categoryRows) {
            if (row.getText().contains(categoryName)) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() > 1) {
                    String parentCategory = cells.get(1).getText(); // Assuming parent is in 2nd column
                    logger.info("Parent category for '{}': {}", categoryName, parentCategory);
                    return parentCategory;
                }
            }
        }
        return "";
    }
    
    /**
     * Verify user remains on Add Category page
     */
    public boolean isOnAddCategoryPage() {
        String currentUrl = getCurrentUrl();
        boolean onAddPage = currentUrl.contains("/categories/add");
        logger.info("On Add Category page: {}", onAddPage);
        return onAddPage;
    }
    
    /**
     * Check if any data is entered in the form
     */
    public boolean isFormDataDiscarded() {
        return isCategoryNameEmpty();
    }
    
    /**
     * Create a new category (complete flow)
     */
    public void createCategory(String categoryName, String parentCategory) {
        logger.info("Creating category: {} with parent: {}", categoryName, parentCategory);
        enterCategoryName(categoryName);
        selectParentCategory(parentCategory);
        clickSave();
    }
    
    /**
     * Attempt to save category with empty name
     */
    public void attemptToSaveWithEmptyName(String parentCategory) {
        logger.info("Attempting to save category with empty name");
        // Leave category name empty
        selectParentCategory(parentCategory);
        clickSave();
    }
}
