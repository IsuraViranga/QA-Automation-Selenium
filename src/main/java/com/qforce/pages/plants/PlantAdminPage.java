package com.qforce.pages.plants;

import com.qforce.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Page Object for Plant Admin Management (Add/Edit Plant Page)
 */
public class PlantAdminPage extends BasePage {
    
    // ========== LOCATORS FOR ADD/EDIT PLANT PAGE ELEMENTS ==========
    
    // Page heading
    @FindBy(xpath = "//h3[normalize-space()='Add Plant' or normalize-space()='Edit Plant']")
    private WebElement pageHeading;
    
    // Form fields
    @FindBy(id = "name")
    private WebElement plantNameField;
    
    @FindBy(name = "name")
    private WebElement plantNameFieldByName;
    
    @FindBy(css = "input[th\\:field='*{name}']")
    private WebElement plantNameFieldByCss;
    
    @FindBy(xpath = "//input[@type='text' and contains(@class,'form-control')]")
    private WebElement plantNameFieldByXpath;
    
    // Category dropdown
    @FindBy(id = "categoryId")
    private WebElement categoryDropdown;
    
    @FindBy(name = "categoryId")
    private WebElement categoryDropdownByName;
    
    @FindBy(css = "select[th\\:field='*{categoryId}']")
    private WebElement categoryDropdownByCss;
    
    @FindBy(xpath = "//select[contains(@class,'form-select')]")
    private WebElement categoryDropdownByXpath;
    
    // Price field
    @FindBy(id = "price")
    private WebElement priceField;
    
    @FindBy(name = "price")
    private WebElement priceFieldByName;
    
    @FindBy(css = "input[th\\:field='*{price}']")
    private WebElement priceFieldByCss;
    
    @FindBy(xpath = "//input[@type='number' and @step='0.01']")
    private WebElement priceFieldByXpath;
    
    // Quantity field
    @FindBy(id = "quantity")
    private WebElement quantityField;
    
    @FindBy(name = "quantity")
    private WebElement quantityFieldByName;
    
    @FindBy(css = "input[th\\:field='*{quantity}']")
    private WebElement quantityFieldByCss;
    
    @FindBy(xpath = "//input[@type='number' and contains(@class,'form-control')]")
    private WebElement quantityFieldByXpath;
    
    // Buttons
    @FindBy(id = "saveButton")
    private WebElement saveButton;
    
    @FindBy(name = "save")
    private WebElement saveButtonByName;
    
    @FindBy(css = "button.btn.btn-primary")
    private WebElement saveButtonByCss;
    
    @FindBy(xpath = "//button[@class='btn btn-primary' and normalize-space()='Save']")
    private WebElement saveButtonByXpath;
    
    @FindBy(id = "cancelButton")
    private WebElement cancelButton;
    
    @FindBy(name = "cancel")
    private WebElement cancelButtonByName;
    
    @FindBy(css = "a.btn.btn-secondary")
    private WebElement cancelButtonByCss;
    
    @FindBy(xpath = "//a[@href='/ui/plants' and @class='btn btn-secondary' and normalize-space()='Cancel']")
    private WebElement cancelButtonByXpath;
    
    // Labels
    @FindBy(xpath = "//label[normalize-space()='Plant Name']")
    private WebElement plantNameLabel;
    
    @FindBy(xpath = "//label[normalize-space()='Category']")
    private WebElement categoryLabel;
    
    @FindBy(xpath = "//label[normalize-space()='Price']")
    private WebElement priceLabel;
    
    @FindBy(xpath = "//label[normalize-space()='Quantity']")
    private WebElement quantityLabel;
    
    // Error messages
    @FindBy(css = "div.text-danger")
    private List<WebElement> errorMessages;
    
    @FindBy(xpath = "//div[@class='text-danger']")
    private List<WebElement> errorMessagesByXpath;
    
    @FindBy(xpath = "//div[contains(@class,'alert-danger')]//span")
    private WebElement generalErrorMessage;
    
    // Success messages
    @FindBy(xpath = "//div[contains(@class,'alert-success')]//span")
    private WebElement successMessage;
    
    // Category validation error
    @FindBy(xpath = "//div[@class='text-danger' and contains(text(),'Please select a category')]")
    private WebElement categoryValidationError;
    
    // By locators for alternative access
    private By plantNameLocator = By.name("name");
    private By categoryDropdownLocator = By.name("categoryId");
    private By priceLocator = By.name("price");
    private By quantityLocator = By.name("quantity");
    private By saveButtonLocator = By.xpath("//button[@class='btn btn-primary' and normalize-space()='Save']");
    private By cancelButtonLocator = By.xpath("//a[@href='/ui/plants' and @class='btn btn-secondary' and normalize-space()='Cancel']");
    
    // ========== NAVIGATION METHODS ==========
    
    /**
     * Navigate to Add Plant page
     */
    public void navigateToAddPlantPage() {
        logger.info("Navigating to Add Plant page");
        String baseUrl = getCurrentUrl().split("/ui")[0];
        navigateTo(baseUrl + "/ui/plants/add");
        waitForPageLoad();
    }
    
    /**
     * Check if on Add Plant page
     */
    public boolean isOnAddPlantPage() {
        String currentUrl = getCurrentUrl();
        boolean onAddPage = currentUrl.contains("/ui/plants/add");
        logger.info("On Add Plant page: {}", onAddPage);
        return onAddPage;
    }
    
    /**
     * Check if on Plants List page
     */
    public boolean isOnPlantsListPage() {
        String currentUrl = getCurrentUrl();
        boolean onPlantsPage = currentUrl.contains("/ui/plants") && !currentUrl.contains("/add") && !currentUrl.contains("/edit");
        logger.info("On Plants List page: {}", onPlantsPage);
        return onPlantsPage;
    }
    
    /**
     * Wait for page to load
     */
    private void waitForPageLoad() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // ========== PAGE ELEMENT VERIFICATION METHODS ==========
    
    /**
     * Check if page loads successfully
     */
    public boolean isPageLoadedSuccessfully() {
        try {
            boolean loaded = isDisplayed(pageHeading) && isDisplayed(getPlantNameField());
            logger.info("Add Plant page loaded successfully: {}", loaded);
            return loaded;
        } catch (Exception e) {
            logger.error("Error checking if page loaded successfully", e);
            return false;
        }
    }
    
    /**
     * Get page heading text
     */
    public String getPageHeading() {
        try {
            String heading = getText(pageHeading);
            logger.info("Page heading: {}", heading);
            return heading;
        } catch (Exception e) {
            logger.error("Error getting page heading", e);
            return "";
        }
    }
    
    // ========== FORM FIELD METHODS ==========
    
    /**
     * Get Plant Name field using locator priority (ID > Name > CSS > XPath)
     */
    private WebElement getPlantNameField() {
        try {
            if (plantNameField != null && plantNameField.isDisplayed()) {
                return plantNameField;
            }
        } catch (Exception ignored) {}
        
        try {
            if (plantNameFieldByName != null && plantNameFieldByName.isDisplayed()) {
                return plantNameFieldByName;
            }
        } catch (Exception ignored) {}
        
        try {
            if (plantNameFieldByCss != null && plantNameFieldByCss.isDisplayed()) {
                return plantNameFieldByCss;
            }
        } catch (Exception ignored) {}
        
        return plantNameFieldByXpath;
    }
    
    /**
     * Get Category dropdown using locator priority
     */
    private WebElement getCategoryDropdown() {
        try {
            if (categoryDropdown != null && categoryDropdown.isDisplayed()) {
                return categoryDropdown;
            }
        } catch (Exception ignored) {}
        
        try {
            if (categoryDropdownByName != null && categoryDropdownByName.isDisplayed()) {
                return categoryDropdownByName;
            }
        } catch (Exception ignored) {}
        
        try {
            if (categoryDropdownByCss != null && categoryDropdownByCss.isDisplayed()) {
                return categoryDropdownByCss;
            }
        } catch (Exception ignored) {}
        
        return categoryDropdownByXpath;
    }
    
    /**
     * Get Price field using locator priority
     */
    private WebElement getPriceField() {
        try {
            if (priceField != null && priceField.isDisplayed()) {
                return priceField;
            }
        } catch (Exception ignored) {}
        
        try {
            if (priceFieldByName != null && priceFieldByName.isDisplayed()) {
                return priceFieldByName;
            }
        } catch (Exception ignored) {}
        
        try {
            if (priceFieldByCss != null && priceFieldByCss.isDisplayed()) {
                return priceFieldByCss;
            }
        } catch (Exception ignored) {}
        
        return priceFieldByXpath;
    }
    
    /**
     * Get Quantity field using locator priority
     */
    private WebElement getQuantityField() {
        try {
            if (quantityField != null && quantityField.isDisplayed()) {
                return quantityField;
            }
        } catch (Exception ignored) {}
        
        try {
            if (quantityFieldByName != null && quantityFieldByName.isDisplayed()) {
                return quantityFieldByName;
            }
        } catch (Exception ignored) {}
        
        try {
            if (quantityFieldByCss != null && quantityFieldByCss.isDisplayed()) {
                return quantityFieldByCss;
            }
        } catch (Exception ignored) {}
        
        return quantityFieldByXpath;
    }
    
    /**
     * Get Save button using locator priority
     */
    private WebElement getSaveButton() {
        try {
            if (saveButton != null && saveButton.isDisplayed()) {
                return saveButton;
            }
        } catch (Exception ignored) {}
        
        try {
            if (saveButtonByName != null && saveButtonByName.isDisplayed()) {
                return saveButtonByName;
            }
        } catch (Exception ignored) {}
        
        try {
            if (saveButtonByCss != null && saveButtonByCss.isDisplayed()) {
                return saveButtonByCss;
            }
        } catch (Exception ignored) {}
        
        return saveButtonByXpath;
    }
    
    /**
     * Get Cancel button using locator priority
     */
    private WebElement getCancelButton() {
        try {
            if (cancelButton != null && cancelButton.isDisplayed()) {
                return cancelButton;
            }
        } catch (Exception ignored) {}
        
        try {
            if (cancelButtonByName != null && cancelButtonByName.isDisplayed()) {
                return cancelButtonByName;
            }
        } catch (Exception ignored) {}
        
        try {
            if (cancelButtonByCss != null && cancelButtonByCss.isDisplayed()) {
                return cancelButtonByCss;
            }
        } catch (Exception ignored) {}
        
        return cancelButtonByXpath;
    }
    
    /**
     * Enter plant name
     */
    public void enterPlantName(String plantName) {
        logger.info("Entering plant name: {}", plantName);
        try {
            WebElement field = getPlantNameField();
            clearAndSendKeys(field, plantName);
        } catch (Exception e) {
            logger.error("Error entering plant name", e);
        }
    }
    
    /**
     * Select category from dropdown
     */
    public void selectCategory(String categoryName) {
        logger.info("Selecting category: {}", categoryName);
        try {
            WebElement dropdown = getCategoryDropdown();
            selectByVisibleText(dropdown, categoryName);
        } catch (Exception e) {
            logger.error("Error selecting category", e);
        }
    }
    
    /**
     * Enter price
     */
    public void enterPrice(String price) {
        logger.info("Entering price: {}", price);
        try {
            WebElement field = getPriceField();
            clearAndSendKeys(field, price);
        } catch (Exception e) {
            logger.error("Error entering price", e);
        }
    }
    
    /**
     * Enter quantity
     */
    public void enterQuantity(String quantity) {
        logger.info("Entering quantity: {}", quantity);
        try {
            WebElement field = getQuantityField();
            clearAndSendKeys(field, quantity);
        } catch (Exception e) {
            logger.error("Error entering quantity", e);
        }
    }
    
    /**
     * Click Save button
     */
    public void clickSaveButton() {
        logger.info("Clicking Save button");
        try {
            WebElement button = getSaveButton();
            click(button);
            waitForPageLoad();
        } catch (Exception e) {
            logger.error("Error clicking Save button", e);
        }
    }
    
    /**
     * Click Cancel button
     */
    public void clickCancelButton() {
        logger.info("Clicking Cancel button");
        try {
            WebElement button = getCancelButton();
            click(button);
            waitForPageLoad();
        } catch (Exception e) {
            logger.error("Error clicking Cancel button", e);
        }
    }
    
    // ========== FIELD VALUE VERIFICATION METHODS ==========
    
    /**
     * Get plant name field value
     */
    public String getPlantNameValue() {
        try {
            String value = getAttribute(plantNameLocator, "value");
            logger.info("Plant name field value: {}", value);
            return value != null ? value : "";
        } catch (Exception e) {
            logger.error("Error getting plant name value", e);
            return "";
        }
    }
    
    /**
     * Get selected category
     */
    public String getSelectedCategory() {
        try {
            String selected = getSelectedOption(categoryDropdownLocator);
            logger.info("Selected category: {}", selected);
            return selected;
        } catch (Exception e) {
            logger.error("Error getting selected category", e);
            return "";
        }
    }
    
    /**
     * Get price field value
     */
    public String getPriceValue() {
        try {
            String value = getAttribute(priceLocator, "value");
            logger.info("Price field value: {}", value);
            return value != null ? value : "";
        } catch (Exception e) {
            logger.error("Error getting price value", e);
            return "";
        }
    }
    
    /**
     * Get quantity field value
     */
    public String getQuantityValue() {
        try {
            String value = getAttribute(quantityLocator, "value");
            logger.info("Quantity field value: {}", value);
            return value != null ? value : "";
        } catch (Exception e) {
            logger.error("Error getting quantity value", e);
            return "";
        }
    }
    
    /**
     * Check if all entered data is retained
     */
    public boolean isEnteredDataRetained(String expectedName, String expectedCategory, String expectedPrice, String expectedQuantity) {
        logger.info("Checking if entered data is retained");
        
        String actualName = getPlantNameValue();
        String actualCategory = getSelectedCategory();
        String actualPrice = getPriceValue();
        String actualQuantity = getQuantityValue();
        
        // Handle name comparison
        boolean nameRetained = actualName.equals(expectedName);
        
        // Handle category comparison - if expected is empty, check if dropdown is at default
        boolean categoryRetained;
        if (expectedCategory == null || expectedCategory.isEmpty()) {
            categoryRetained = actualCategory.contains("Select Sub Category") || actualCategory.isEmpty();
        } else {
            categoryRetained = actualCategory.equals(expectedCategory);
        }
        
        // Handle price comparison - normalize decimal format (500.0 vs 500.00)
        boolean priceRetained;
        try {
            double expectedPriceNum = Double.parseDouble(expectedPrice);
            double actualPriceNum = Double.parseDouble(actualPrice);
            priceRetained = Math.abs(expectedPriceNum - actualPriceNum) < 0.01;
        } catch (NumberFormatException e) {
            priceRetained = actualPrice.equals(expectedPrice);
        }
        
        // Handle quantity comparison
        boolean quantityRetained = actualQuantity.equals(expectedQuantity);
        
        boolean allRetained = nameRetained && categoryRetained && priceRetained && quantityRetained;
        logger.info("Data retained - Name: {} (expected: '{}', actual: '{}'), Category: {} (expected: '{}', actual: '{}'), Price: {} (expected: '{}', actual: '{}'), Quantity: {} (expected: '{}', actual: '{}'), All: {}", 
                   nameRetained, expectedName, actualName, categoryRetained, expectedCategory, actualCategory, 
                   priceRetained, expectedPrice, actualPrice, quantityRetained, expectedQuantity, actualQuantity, allRetained);
        
        return allRetained;
    }
    
    // ========== VALIDATION AND ERROR MESSAGE METHODS ==========
    
    /**
     * Get validation error message for category
     */
    public String getCategoryValidationError() {
        try {
            if (isDisplayed(categoryValidationError)) {
                String error = getText(categoryValidationError);
                logger.info("Category validation error: {}", error);
                return error;
            }
            
            // Check for any error message near category field
            for (WebElement errorMsg : errorMessages) {
                String errorText = getText(errorMsg);
                if (errorText.toLowerCase().contains("category") || errorText.toLowerCase().contains("select")) {
                    logger.info("Found category error: {}", errorText);
                    return errorText;
                }
            }
            
            return "";
        } catch (Exception e) {
            logger.error("Error getting category validation error", e);
            return "";
        }
    }
    
    /**
     * Get general error message
     */
    public String getGeneralErrorMessage() {
        try {
            if (isDisplayed(generalErrorMessage)) {
                String error = getText(generalErrorMessage);
                logger.info("General error message: {}", error);
                return error;
            }
            return "";
        } catch (Exception e) {
            logger.error("Error getting general error message", e);
            return "";
        }
    }
    
    /**
     * Get success message
     */
    public String getSuccessMessage() {
        try {
            if (isDisplayed(successMessage)) {
                String message = getText(successMessage);
                logger.info("Success message: {}", message);
                return message;
            }
            return "";
        } catch (Exception e) {
            logger.error("Error getting success message", e);
            return "";
        }
    }
    
    /**
     * Check if form submission is prevented (still on add page with validation errors)
     */
    public boolean isFormSubmissionPrevented() {
        boolean onAddPage = isOnAddPlantPage();
        boolean hasValidationErrors = !getCategoryValidationError().isEmpty() || !getGeneralErrorMessage().isEmpty();
        boolean prevented = onAddPage && hasValidationErrors;
        logger.info("Form submission prevented: {} (on add page: {}, has errors: {})", prevented, onAddPage, hasValidationErrors);
        return prevented;
    }
    
    /**
     * Check if validation error is displayed near category field
     */
    public boolean isValidationErrorDisplayedNearCategoryField(String expectedError) {
        String actualError = getCategoryValidationError();
        boolean errorDisplayed = actualError.contains(expectedError);
        logger.info("Validation error displayed near category field: {} (expected: '{}', actual: '{}')", 
                   errorDisplayed, expectedError, actualError);
        return errorDisplayed;
    }
    
    // ========== DROPDOWN METHODS ==========
    
    /**
     * Leave category dropdown at default value
     */
    public void leaveCategoryAtDefault() {
        logger.info("Leaving category dropdown at default value");
        // Don't select anything - it should remain at "-- Select Sub Category --"
    }
    
    /**
     * Get available categories from dropdown
     */
    public List<String> getAvailableCategories() {
        logger.info("Getting available categories from dropdown");
        try {
            return getAllOptions(categoryDropdownLocator);
        } catch (Exception e) {
            logger.error("Error getting available categories", e);
            return List.of();
        }
    }
    
    /**
     * Check if category dropdown is at default
     */
    public boolean isCategoryDropdownAtDefault() {
        String selected = getSelectedCategory();
        boolean atDefault = selected.isEmpty() || selected.contains("Select Sub Category");
        logger.info("Category dropdown at default: {} (selected: '{}')", atDefault, selected);
        return atDefault;
    }
    
    // ========== UTILITY METHODS ==========
    
    /**
     * Clear and send keys to element
     */
    private void clearAndSendKeys(WebElement element, String text) {
        logger.info("Clearing and entering text: {}", text);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Get all options from dropdown
     */
    private List<String> getAllOptions(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            Select select = new Select(element);
            return select.getOptions().stream()
                    .map(WebElement::getText)
                    .toList();
        } catch (Exception e) {
            logger.error("Error getting dropdown options", e);
            return List.of();
        }
    }
}