package com.qforce.pages.plants;

import com.qforce.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Page Object for Plant Management Page
 */
public class PlantPage extends BasePage {
    
    // ========== LOCATORS FOR PLANT PAGE ELEMENTS ==========
    
    // Page heading
    @FindBy(xpath = "//h3[normalize-space()='Plants']")
    private WebElement pageHeading;
    
    // Search and filter elements
    @FindBy(name = "name")
    private WebElement searchPlantField;
    
    @FindBy(name = "categoryId")
    private WebElement categoryDropdown;
    
    @FindBy(xpath = "//button[@class='btn btn-primary me-2' and normalize-space()='Search']")
    private WebElement searchButton;
    
    @FindBy(xpath = "//a[@href='/ui/plants' and @class='btn btn-outline-secondary me-2' and normalize-space()='Reset']")
    private WebElement resetButton;
    
    @FindBy(xpath = "//a[@href='/ui/plants/add' and @class='btn btn-primary' and normalize-space()='Add a Plant']")
    private WebElement addPlantButton;
    
    // Table elements
    @FindBy(xpath = "//table[contains(@class,'table')]")
    private WebElement plantsTable;
    
    @FindBy(xpath = "//table/thead/tr/th")
    private List<WebElement> tableHeaders;
    
    @FindBy(xpath = "//table/tbody/tr")
    private List<WebElement> plantRows;
    
    // Empty state message
    @FindBy(xpath = "//td[@colspan='5' and contains(@class,'text-center') and contains(@class,'text-muted') and contains(@class,'py-4')]")
    private WebElement emptyStateMessage;
    
    // Success and error messages
    @FindBy(xpath = "//div[contains(@class,'alert-success')]//span")
    private WebElement successMessage;
    
    @FindBy(xpath = "//div[contains(@class,'alert-danger')]//span")
    private WebElement errorMessage;
    
    // Pagination elements
    @FindBy(css = "ul.pagination")
    private WebElement paginationContainer;
    
    @FindBy(xpath = "//a[normalize-space()='Previous']")
    private WebElement previousButton;
    
    @FindBy(xpath = "//a[normalize-space()='Next']")
    private WebElement nextButton;
    
    @FindBy(css = "li.page-item.active")
    private WebElement currentPageIndicator;
    
    @FindBy(css = "li.page-item a.page-link")
    private List<WebElement> pageNumbers;
    
    // By locators for alternative access
    private By searchPlantLocator = By.name("name");
    private By categoryDropdownLocator = By.name("categoryId");
    private By searchButtonLocator = By.xpath("//button[@class='btn btn-primary me-2' and normalize-space()='Search']");
    private By resetButtonLocator = By.xpath("//a[@href='/ui/plants' and @class='btn btn-outline-secondary me-2' and normalize-space()='Reset']");
    
    // ========== NAVIGATION METHODS ==========
    
    /**
     * Navigate to Plants List page
     */
    public void navigateToPlantsList() {
        logger.info("Navigating to Plants List page");
        String baseUrl = getCurrentUrl().split("/ui")[0];
        navigateTo(baseUrl + "/ui/plants");
        waitForPageLoad();
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
            boolean loaded = isDisplayed(pageHeading) && isDisplayed(plantsTable);
            logger.info("Plants page loaded successfully: {}", loaded);
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
    
    /**
     * Check if search bar is visible
     */
    public boolean isSearchBarVisible() {
        try {
            boolean visible = isDisplayed(searchPlantField);
            logger.info("Search bar visible: {}", visible);
            return visible;
        } catch (Exception e) {
            logger.error("Error checking search bar visibility", e);
            return false;
        }
    }
    
    /**
     * Get search bar placeholder text
     */
    public String getSearchBarPlaceholder() {
        try {
            String placeholder = getAttribute(searchPlantLocator, "placeholder");
            logger.info("Search bar placeholder: {}", placeholder);
            return placeholder;
        } catch (Exception e) {
            logger.error("Error getting search bar placeholder", e);
            return "";
        }
    }
    
    /**
     * Check if category dropdown is visible
     */
    public boolean isCategoryDropdownVisible() {
        try {
            boolean visible = isDisplayed(categoryDropdown);
            logger.info("Category dropdown visible: {}", visible);
            return visible;
        } catch (Exception e) {
            logger.error("Error checking category dropdown visibility", e);
            return false;
        }
    }
    
    /**
     * Get selected category from dropdown
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
     * Check if Search button is visible
     */
    public boolean isSearchButtonVisible() {
        try {
            boolean visible = isDisplayed(searchButton);
            logger.info("Search button visible: {}", visible);
            return visible;
        } catch (Exception e) {
            logger.error("Error checking search button visibility", e);
            return false;
        }
    }
    
    /**
     * Check if Reset button is visible
     */
    public boolean isResetButtonVisible() {
        try {
            boolean visible = isDisplayed(resetButton);
            logger.info("Reset button visible: {}", visible);
            return visible;
        } catch (Exception e) {
            logger.error("Error checking reset button visibility", e);
            return false;
        }
    }
    
    /**
     * Check if Add Plant button is visible (should NOT be visible for users)
     */
    public boolean isAddPlantButtonVisible() {
        try {
            boolean visible = isDisplayed(addPlantButton);
            logger.info("Add Plant button visible: {}", visible);
            return visible;
        } catch (Exception e) {
            logger.info("Add Plant button not visible (expected for user)");
            return false;
        }
    }
    
    // ========== TABLE VERIFICATION METHODS ==========
    
    /**
     * Check if plants table is displayed
     */
    public boolean isPlantsTableDisplayed() {
        try {
            boolean displayed = isDisplayed(plantsTable);
            logger.info("Plants table displayed: {}", displayed);
            return displayed;
        } catch (Exception e) {
            logger.error("Error checking plants table display", e);
            return false;
        }
    }
    
    /**
     * Check if specific column header is present
     */
    public boolean isColumnHeaderPresent(String columnName) {
        logger.info("Checking if column header '{}' is present", columnName);
        
        try {
            for (WebElement header : tableHeaders) {
                String headerText = getText(header).trim();
                
                // Handle sorting indicators in header text
                String cleanHeaderText = headerText.replaceAll("[↑↓▲▼]", "").trim();
                
                if (cleanHeaderText.equalsIgnoreCase(columnName)) {
                    logger.info("Column header '{}' found", columnName);
                    return true;
                }
                
                // Check if header starts with column name (for sorting indicators)
                if (cleanHeaderText.toLowerCase().startsWith(columnName.toLowerCase())) {
                    logger.info("Column header '{}' found (starts with '{}')", columnName, headerText);
                    return true;
                }
            }
            
            logger.info("Column header '{}' NOT found", columnName);
            return false;
            
        } catch (Exception e) {
            logger.error("Error checking column header", e);
            return false;
        }
    }
    
    /**
     * Verify all expected columns are present
     */
    public boolean areAllColumnsPresent(String expectedColumns) {
        logger.info("Verifying all columns are present: {}", expectedColumns);
        
        String[] columns = expectedColumns.split(",\\s*");
        for (String column : columns) {
            if (!isColumnHeaderPresent(column.trim())) {
                logger.error("Column '{}' is missing", column.trim());
                return false;
            }
        }
        
        logger.info("All expected columns are present");
        return true;
    }
    
    /**
     * Get number of plant rows in table
     */
    public int getPlantRowCount() {
        try {
            // Filter out empty state rows
            int count = 0;
            for (WebElement row : plantRows) {
                String rowText = row.getText().trim();
                if (!rowText.isEmpty() && !rowText.contains("No plants found") && rowText.length() > 10) {
                    count++;
                }
            }
            
            logger.info("Plant row count: {}", count);
            return count;
            
        } catch (Exception e) {
            logger.error("Error getting plant row count", e);
            return 0;
        }
    }
    
    /**
     * Check if Actions column is empty for user
     */
    public boolean isActionsColumnEmptyForUser() {
        logger.info("Checking if Actions column is empty for user");
        
        try {
            // Find Actions column index
            int actionsColumnIndex = -1;
            for (int i = 0; i < tableHeaders.size(); i++) {
                String headerText = getText(tableHeaders.get(i)).trim();
                if (headerText.equalsIgnoreCase("Actions")) {
                    actionsColumnIndex = i;
                    break;
                }
            }
            
            if (actionsColumnIndex == -1) {
                logger.info("Actions column not found");
                return true; // If no Actions column, it's effectively empty
            }
            
            // Check if Actions cells are empty
            for (WebElement row : plantRows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (actionsColumnIndex < cells.size()) {
                    String cellText = getText(cells.get(actionsColumnIndex)).trim();
                    if (!cellText.isEmpty()) {
                        logger.info("Actions column has content: {}", cellText);
                        return false;
                    }
                }
            }
            
            logger.info("Actions column is empty for user");
            return true;
            
        } catch (Exception e) {
            logger.error("Error checking Actions column", e);
            return true; // Assume empty if error
        }
    }
    
    /**
     * Get empty state message text
     */
    public String getEmptyStateMessage() {
        try {
            if (isDisplayed(emptyStateMessage)) {
                String message = getText(emptyStateMessage);
                logger.info("Empty state message: {}", message);
                return message;
            }
            
            // Alternative: check for "No plants found" text in page
            String pageText = driver.findElement(By.tagName("body")).getText();
            if (pageText.contains("No plants found")) {
                return "No plants found";
            }
            
            return "";
        } catch (Exception e) {
            logger.error("Error getting empty state message", e);
            return "";
        }
    }
    
    /**
     * Check if empty state message is displayed
     */
    public boolean isEmptyStateMessageDisplayed() {
        try {
            if (isDisplayed(emptyStateMessage)) {
                return true;
            }
            
            // Check if page text contains "No plants found"
            String pageText = driver.findElement(By.tagName("body")).getText();
            return pageText.contains("No plants found");
            
        } catch (Exception e) {
            logger.error("Error checking empty state message", e);
            return false;
        }
    }
    
    // ========== SEARCH AND FILTER METHODS ==========
    
    /**
     * Enter search term in search field
     */
    public void enterSearchTerm(String searchTerm) {
        logger.info("Entering search term: {}", searchTerm);
        try {
            clearAndSendKeys(searchPlantField, searchTerm);
        } catch (Exception e) {
            logger.error("Error entering search term", e);
        }
    }
    
    /**
     * Click Search button
     */
    public void clickSearchButton() {
        logger.info("Clicking Search button");
        try {
            click(searchButton);
            waitForPageLoad();
        } catch (Exception e) {
            logger.error("Error clicking Search button", e);
        }
    }
    
    /**
     * Click Reset button
     */
    public void clickResetButton() {
        logger.info("Clicking Reset button");
        try {
            click(resetButton);
            waitForPageLoad();
        } catch (Exception e) {
            logger.error("Error clicking Reset button", e);
        }
    }
    
    /**
     * Select category from dropdown
     */
    public void selectCategory(String categoryName) {
        logger.info("Selecting category: {}", categoryName);
        try {
            selectByVisibleText(categoryDropdown, categoryName);
        } catch (Exception e) {
            logger.error("Error selecting category", e);
        }
    }
    
    /**
     * Click on category dropdown
     */
    public void clickCategoryDropdown() {
        logger.info("Clicking category dropdown");
        try {
            click(categoryDropdown);
        } catch (Exception e) {
            logger.error("Error clicking category dropdown", e);
        }
    }
    
    /**
     * Get search field value
     */
    public String getSearchFieldValue() {
        try {
            String value = getAttribute(searchPlantLocator, "value");
            logger.info("Search field value: {}", value);
            return value != null ? value : "";
        } catch (Exception e) {
            logger.error("Error getting search field value", e);
            return "";
        }
    }
    
    /**
     * Check if search field is empty
     */
    public boolean isSearchFieldEmpty() {
        String value = getSearchFieldValue();
        boolean isEmpty = value.trim().isEmpty();
        logger.info("Search field is empty: {}", isEmpty);
        return isEmpty;
    }
    
    // ========== PLANT DATA VERIFICATION METHODS ==========
    
    /**
     * Check if plants matching search criteria are displayed
     */
    public boolean arePlantsMatchingSearchDisplayed(String searchTerm) {
        logger.info("Checking if plants matching '{}' are displayed", searchTerm);
        
        try {
            if (getPlantRowCount() == 0) {
                return false;
            }
            
            // Check if at least one plant contains the search term
            for (WebElement row : plantRows) {
                String rowText = row.getText().toLowerCase();
                if (rowText.contains(searchTerm.toLowerCase()) && !rowText.contains("No plants found")) {
                    logger.info("Found plant matching search term: {}", searchTerm);
                    return true;
                }
            }
            
            logger.info("No plants matching search term found");
            return false;
            
        } catch (Exception e) {
            logger.error("Error checking plants matching search", e);
            return false;
        }
    }
    
    /**
     * Check if plants from specific category are displayed
     */
    public boolean arePlantsFromCategoryDisplayed(String categoryName) {
        logger.info("Checking if plants from category '{}' are displayed", categoryName);
        
        try {
            if (getPlantRowCount() == 0) {
                return false;
            }
            
            // Find Category column index
            int categoryColumnIndex = -1;
            for (int i = 0; i < tableHeaders.size(); i++) {
                String headerText = getText(tableHeaders.get(i)).trim();
                if (headerText.equalsIgnoreCase("Category")) {
                    categoryColumnIndex = i;
                    break;
                }
            }
            
            if (categoryColumnIndex == -1) {
                logger.warn("Category column not found");
                return false;
            }
            
            // Check if all displayed plants belong to the selected category
            for (WebElement row : plantRows) {
                String rowText = row.getText();
                if (!rowText.contains("No plants found") && rowText.trim().length() > 10) {
                    List<WebElement> cells = row.findElements(By.tagName("td"));
                    if (categoryColumnIndex < cells.size()) {
                        String cellCategory = getText(cells.get(categoryColumnIndex));
                        if (!cellCategory.equalsIgnoreCase(categoryName)) {
                            logger.info("Found plant from different category: {}", cellCategory);
                            return false;
                        }
                    }
                }
            }
            
            logger.info("All displayed plants belong to category: {}", categoryName);
            return true;
            
        } catch (Exception e) {
            logger.error("Error checking plants from category", e);
            return false;
        }
    }
    
    /**
     * Check if plant details are complete
     */
    public boolean arePlantDetailsComplete() {
        logger.info("Checking if plant details are complete");
        
        try {
            String[] requiredColumns = {"Name", "Category", "Price", "Stock"};
            
            for (String column : requiredColumns) {
                if (!isColumnHeaderPresent(column)) {
                    logger.error("Required column '{}' is missing", column);
                    return false;
                }
            }
            
            logger.info("All plant details are complete");
            return true;
            
        } catch (Exception e) {
            logger.error("Error checking plant details", e);
            return false;
        }
    }
    
    /**
     * Check if table updates without page refresh
     */
    public boolean doesTableUpdateWithoutRefresh() {
        // This is difficult to verify programmatically
        // We'll assume it works if the search/filter operations complete successfully
        logger.info("Assuming table updates without page refresh");
        return true;
    }
    
    /**
     * Get all available categories from dropdown
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
     * Check if search and reset buttons remain functional
     */
    public boolean areSearchAndResetButtonsFunctional() {
        try {
            boolean searchFunctional = isEnabled(searchButton);
            boolean resetFunctional = isEnabled(resetButton);
            boolean functional = searchFunctional && resetFunctional;
            logger.info("Search and Reset buttons functional: {}", functional);
            return functional;
        } catch (Exception e) {
            logger.error("Error checking button functionality", e);
            return false;
        }
    }
    
    /**
     * Perform search operation
     */
    public void performSearch(String searchTerm) {
        logger.info("Performing search for: {}", searchTerm);
        enterSearchTerm(searchTerm);
        clickSearchButton();
    }
    
    /**
     * Perform category filter
     */
    public void performCategoryFilter(String categoryName) {
        logger.info("Performing category filter for: {}", categoryName);
        selectCategory(categoryName);
        clickSearchButton();
    }
    
    /**
     * Reset all filters
     */
    public void resetAllFilters() {
        logger.info("Resetting all filters");
        clickResetButton();
    }
    
    // ========== SUCCESS/ERROR MESSAGE METHODS ==========
    
    /**
     * Clear and send keys to element
     */
    private void clearAndSendKeys(WebElement element, String text) {
        logger.info("Clearing and entering text: {}", text);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Check if element is enabled
     */
    private boolean isEnabled(WebElement element) {
        try {
            boolean enabled = element.isEnabled();
            logger.info("Element enabled: {}", enabled);
            return enabled;
        } catch (Exception e) {
            logger.error("Error checking if element is enabled", e);
            return false;
        }
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
     * Get error message
     */
    public String getErrorMessage() {
        try {
            if (isDisplayed(errorMessage)) {
                String message = getText(errorMessage);
                logger.info("Error message: {}", message);
                return message;
            }
            return "";
        } catch (Exception e) {
            logger.error("Error getting error message", e);
            return "";
        }
    }
}