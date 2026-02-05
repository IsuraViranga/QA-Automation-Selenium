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

    // ========== NEW LOCATORS FOR USER TEST CASES ==========

    //@FindBy(css = "table.category-table, table#categoryTable, .table")
    @FindBy(xpath = "//table[.//th[normalize-space()='Name']]")
    private WebElement categoryTable;

    //@FindBy(css = ".empty-state, .no-data, .alert-info")
    @FindBy(css = "td.text-center.text-muted.py-4")
    private WebElement emptyStateMessage;

    // @FindBy(css = ".pagination, nav[aria-label='pagination'], .pagination-container")
    @FindBy(css = "ul.pagination")
    private WebElement paginationContainer;

    // @FindBy(css = ".pagination .previous, button.previous, a.previous")
    
    private WebElement previousButton;

    // @FindBy(css = ".pagination .next, button.next, a.next")
    @FindBy(xpath = "//a[normalize-space()='Next']")
    private WebElement nextButton;

    // @FindBy(css = ".pagination .active, .pagination .current, .page-item.active")
    @FindBy(css = "li.page-item.active")
    private WebElement currentPageIndicator;

    // @FindBy(css = ".pagination .page-number, .pagination li, .page-item")
    @FindBy(css = "li.page-item a.page-link")
    private List<WebElement> pageNumbers;

    // @FindBy(xpath = "//th[contains(@class, 'sortable') or contains(@class, 'sort')]")
    @FindBy(xpath = "//table/thead/tr/th[a]")
    private List<WebElement> sortableHeaders;

    //@FindBy(xpath = "//table//thead//th")
    @FindBy(xpath = "//table/thead/tr/th")
    private List<WebElement> tableHeaders;

    // ========== SEARCH AND FILTER LOCATORS (Following ID > Name > CSS > XPath) ==========

    // Search input field - using name attribute (no ID available)
    @FindBy(name = "name")
    private WebElement searchInputField;

    // Parent filter dropdown - using name attribute (no ID available)
    @FindBy(name = "parentId")
    private WebElement parentFilterDropdown;

    // Search button - using CSS selector for type and text
    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    // Reset button - using XPath for href attribute
    @FindBy(xpath = "//a[@href='/ui/categories' and contains(@class, 'btn-outline-secondary')]")
    private WebElement resetButton;

    // Add Category button - using XPath for href and text (admin only)
    @FindBy(xpath = "//a[@href='/ui/categories/add' and contains(text(), 'Add A Category')]")
    private WebElement addCategoryButton_User;

    // ========== ACTION BUTTON LOCATORS ==========

    // Edit buttons - using CSS selector
    @FindBy(css = "a.btn-outline-primary")
    private List<WebElement> editButtons;

    // Delete buttons - using CSS selector
    @FindBy(css = "button.btn-outline-danger")
    private List<WebElement> deleteButtons;

    // ========== PAGINATION LOCATORS (Enhanced) ==========

    // Previous button - using XPath for text
    @FindBy(xpath = "//a[normalize-space()='Previous']")
    private WebElement previousButtonPagination;

    // Next button - already defined above
    // @FindBy(xpath = "//a[normalize-space()='Next']")
    // private WebElement nextButton;

    // Active page indicator - using CSS
    @FindBy(css = "li.page-item.active a.page-link")
    private WebElement activePageNumber;
    
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

    // ========== NEW METHODS FOR USER TEST CASES ==========

    /**
     * Check if category table is displayed
     */
    public boolean isCategoryTableDisplayed() {
        try {
            boolean displayed = isDisplayed(categoryTable);
            logger.info("Category table displayed: {}", displayed);
            return displayed;
        } catch (Exception e) {
            logger.error("Error checking if category table is displayed", e);
            return false;
        }
    }

    /**
     * Check if a specific column header is present
     */
    
    // public boolean isColumnHeaderPresent(String columnName) {
    //     logger.info("Checking if column header '{}' is present", columnName);
        
    //     try {
    //         for (WebElement header : tableHeaders) {
    //             String headerText = getText(header).trim();
    //             if (headerText.equalsIgnoreCase(columnName)) {
    //                 logger.info("Column header '{}' found", columnName);
    //                 return true;
    //             }
    //         }
    //         logger.info("Column header '{}' NOT found", columnName);
    //         return false;
    //     } catch (Exception e) {
    //         logger.error("Error checking column header", e);
    //         return false;
    //     }
    // }

    public boolean isColumnHeaderPresent(String columnName) {
    logger.info("Checking if column header '{}' is present", columnName);
    
        try {
            for (WebElement header : tableHeaders) {
                String headerText = getText(header).trim();
                
                // Exact match (case-insensitive)
                if (headerText.equalsIgnoreCase(columnName)) {
                    logger.info("Column header '{}' found (exact match)", columnName);
                    return true;
                }
                
                // Partial match - check if header STARTS with columnName
                // This handles cases like "ID ?" matching "ID"
                if (headerText.toLowerCase().startsWith(columnName.toLowerCase())) {
                    logger.info("Column header '{}' found (starts with '{}')", columnName, headerText);
                    return true;
                }
                
                // Remove special characters and check
                // This handles "ID ?" → "ID"
                String cleanedHeader = headerText.replaceAll("[^a-zA-Z0-9]", "").trim();
                if (cleanedHeader.equalsIgnoreCase(columnName)) {
                    logger.info("Column header '{}' found (cleaned match from '{}')", columnName, headerText);
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
     * Get empty state message text
     */
    public String getEmptyStateMessage() {
        try {
            if (isDisplayed(emptyStateMessage)) {
                String message = getText(emptyStateMessage);
                logger.info("Empty state message: {}", message);
                return message;
            }
            
            // Alternative: check for "No category found" text in table
            String pageText = driver.findElement(By.tagName("body")).getText();
            if (pageText.contains("No category found")) {
                return "No category found";
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
            
            // Check if page text contains "No category found"
            String pageText = driver.findElement(By.tagName("body")).getText();
            return pageText.contains("No category found") || pageText.contains("no categories");
            
        } catch (Exception e) {
            logger.error("Error checking empty state message", e);
            return false;
        }
    }

    /**
     * Get number of category rows in table
     */
    public int getCategoryRowCount() {
        try {
            List<WebElement> rows = categoryRows;
            
            // Filter out empty rows or header rows
            int count = 0;
            for (WebElement row : rows) {
                String rowText = row.getText().trim();
                // Skip if row is empty or contains only whitespace
                if (!rowText.isEmpty() && rowText.length() > 5) {
                    count++;
                }
            }
            
            logger.info("Category row count: {}", count);
            return count;
            
        } catch (Exception e) {
            logger.error("Error getting category row count", e);
            return 0;
        }
    }

    /**
     * Get cell data from specific row and column
     */
    public String getCellData(int rowIndex, String columnName) {
        logger.info("Getting cell data for row {} column {}", rowIndex, columnName);
        
        try {
            // Find column index
            int columnIndex = -1;
            for (int i = 0; i < tableHeaders.size(); i++) {
                String headerText = getText(tableHeaders.get(i)).trim();
                if (headerText.equalsIgnoreCase(columnName)) {
                    columnIndex = i;
                    break;
                }
            }
            
            if (columnIndex == -1) {
                logger.warn("Column '{}' not found", columnName);
                return "";
            }
            
            // Get cell
            WebElement row = categoryRows.get(rowIndex);
            List<WebElement> cells = row.findElements(By.tagName("td"));
            
            if (columnIndex < cells.size()) {
                String cellData = getText(cells.get(columnIndex));
                logger.info("Cell data: {}", cellData);
                return cellData;
            }
            
            return "";
            
        } catch (Exception e) {
            logger.error("Error getting cell data", e);
            return "";
        }
    }

    /**
     * Check if pagination is displayed
     */
    public boolean isPaginationDisplayed() {
        try {
            boolean displayed = isDisplayed(paginationContainer);
            logger.info("Pagination displayed: {}", displayed);
            return displayed;
        } catch (Exception e) {
            // Try alternative check
            try {
                boolean hasPageNumbers = pageNumbers != null && pageNumbers.size() > 0;
                logger.info("Pagination displayed (via page numbers): {}", hasPageNumbers);
                return hasPageNumbers;
            } catch (Exception ex) {
                logger.error("Error checking pagination", ex);
                return false;
            }
        }
    }

    /**
     * Check if Previous button is present
     */
    public boolean isPreviousButtonPresent() {
        try {
            boolean present = previousButton != null;
            logger.info("Previous button present: {}", present);
            return present;
        } catch (Exception e) {
            logger.error("Error checking Previous button", e);
            return false;
        }
    }

    /**
     * Check if Next button is present
     */
    public boolean isNextButtonPresent() {
        try {
            boolean present = nextButton != null;
            logger.info("Next button present: {}", present);
            return present;
        } catch (Exception e) {
            logger.error("Error checking Next button", e);
            return false;
        }
    }

    /**
     * Check if page numbers are displayed
     */
    public boolean arePageNumbersDisplayed() {
        try {
            boolean displayed = pageNumbers != null && pageNumbers.size() > 0;
            logger.info("Page numbers displayed: {} (count: {})", displayed, pageNumbers.size());
            return displayed;
        } catch (Exception e) {
            logger.error("Error checking page numbers", e);
            return false;
        }
    }

    /**
     * Check if current page is highlighted
     */
    public boolean isCurrentPageHighlighted() {
        try {
            if (currentPageIndicator != null && isDisplayed(currentPageIndicator)) {
                logger.info("Current page is highlighted");
                return true;
            }
            
            // Alternative: check for active class in page numbers
            for (WebElement pageNumber : pageNumbers) {
                String className = pageNumber.getAttribute("class");
                if (className != null && (className.contains("active") || className.contains("current"))) {
                    logger.info("Current page is highlighted (found active class)");
                    return true;
                }
            }
            
            return false;
        } catch (Exception e) {
            logger.error("Error checking current page highlight", e);
            return false;
        }
    }

    /**
     * Check if column has sorting indicator
     */
    public boolean hasSortingIndicator(String columnName) {
        logger.info("Checking if column '{}' has sorting indicator", columnName);
        
        try {
            // Find the column header
            for (WebElement header : tableHeaders) {
                String headerText = getText(header).trim();
                
                if (headerText.equalsIgnoreCase(columnName)) {
                    // Check for sortable class
                    String className = header.getAttribute("class");
                    if (className != null && (className.contains("sortable") || className.contains("sort"))) {
                        logger.info("Column '{}' has sorting indicator (via class)", columnName);
                        return true;
                    }
                    
                    // Check for sorting icon (▲ ▼ ↑ ↓)
                    String headerHtml = header.getAttribute("innerHTML");
                    if (headerHtml != null && (
                        headerHtml.contains("▲") || 
                        headerHtml.contains("▼") || 
                        headerHtml.contains("↑") || 
                        headerHtml.contains("↓") ||
                        headerHtml.contains("sort") ||
                        headerHtml.contains("<i") ||  // Icon element
                        headerHtml.contains("arrow")
                    )) {
                        logger.info("Column '{}' has sorting indicator (via icon)", columnName);
                        return true;
                    }
                    
                    // Check for nested icon element
                    try {
                        WebElement icon = header.findElement(By.tagName("i"));
                        if (icon != null) {
                            logger.info("Column '{}' has sorting indicator (icon element found)", columnName);
                            return true;
                        }
                    } catch (Exception e) {
                        // No icon element
                    }
                }
            }
            
            logger.info("Column '{}' does NOT have sorting indicator", columnName);
            return false;
            
        } catch (Exception e) {
            logger.error("Error checking sorting indicator", e);
            return false;
        }
    }

    /**
     * Click on column header to sort
     */
    public void clickColumnHeader(String columnName) {
        logger.info("Clicking on column header: {}", columnName);
        
        try {
            for (WebElement header : tableHeaders) {
                String headerText = getText(header).trim().replaceAll("[↑↓]", "").trim();
                if (headerText.equalsIgnoreCase(columnName)) {
                    // Find the <a> link inside the <th> header
                    WebElement link = header.findElement(By.tagName("a"));
                    click(link);
                    logger.info("Clicked on column header link: {}", columnName);
                    
                    // Wait for page to reload after sort
                    Thread.sleep(2000);
                    return;
                }
            }
            logger.warn("Column header '{}' not found", columnName);
        } catch (Exception e) {
            logger.error("Error clicking column header", e);
        }
    }

    /**
     * Click on Next button in pagination
     */
    public void clickNextPage() {
        logger.info("Clicking Next page button");
        try {
            click(nextButton);
        } catch (Exception e) {
            logger.error("Error clicking Next button", e);
        }
    }

    /**
     * Click on Previous button in pagination
     */
    public void clickPreviousPage() {
        logger.info("Clicking Previous page button");
        try {
            click(previousButton);
        } catch (Exception e) {
            logger.error("Error clicking Previous button", e);
        }
    }

    /**
     * Click on specific page number
     */
    public void clickPageNumber(int pageNumber) {
        logger.info("Clicking on page number: {}", pageNumber);
        
        try {
            for (WebElement pageElement : pageNumbers) {
                String pageText = getText(pageElement);
                if (pageText.equals(String.valueOf(pageNumber))) {
                    click(pageElement);
                    logger.info("Clicked on page number: {}", pageNumber);
                    return;
                }
            }
            logger.warn("Page number {} not found", pageNumber);
        } catch (Exception e) {
            logger.error("Error clicking page number", e);
        }
    }

    // ========== NEW METHODS FOR SEARCH AND FILTER ==========

    /**
     * Enter text in search field
     */
    public void enterSearchText(String searchText) {
        logger.info("Entering search text: {}", searchText);
        try {
            clear(searchInputField);
            sendKeys(searchInputField, searchText);
        } catch (Exception e) {
            logger.error("Error entering search text", e);
        }
    }

    /**
     * Get current search text
     */
    public String getSearchText() {
        try {
            String searchText = getAttribute(searchInputField, "value");
            logger.info("Current search text: {}", searchText);
            return searchText != null ? searchText : "";
        } catch (Exception e) {
            logger.error("Error getting search text", e);
            return "";
        }
    }

    /**
     * Select parent from filter dropdown
     */
    public void selectParentFilter(String parentName) {
        logger.info("Selecting parent filter: {}", parentName);
        try {
            selectByVisibleText(parentFilterDropdown, parentName);
        } catch (Exception e) {
            logger.error("Error selecting parent filter", e);
        }
    }

    /**
     * Get currently selected parent filter
     */
    public String getSelectedParentFilter() {
        try {
            String selected = getSelectedOption(parentFilterDropdown);
            logger.info("Currently selected parent filter: {}", selected);
            return selected;
        } catch (Exception e) {
            logger.error("Error getting selected parent filter", e);
            return "";
        }
    }

    /**
     * Click Search button
     */
    public void clickSearchButton() {
        logger.info("Clicking Search button");
        try {
            click(searchButton);
            // Wait for results to load
            Thread.sleep(1000);
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
            // Wait for page to reload
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.error("Error clicking Reset button", e);
        }
    }

    /**
     * Check if search field is cleared
     */
    public boolean isSearchFieldCleared() {
        String searchText = getSearchText();
        boolean cleared = searchText.isEmpty();
        logger.info("Search field cleared: {}", cleared);
        return cleared;
    }

    // ========== NEW METHODS FOR ACCESS CONTROL ==========

    /**
     * Check if Add A Category button is visible (should not be for users)
     */
    public boolean isAddCategoryButtonVisible() {
        try {
            boolean visible = isDisplayed(addCategoryButton_User);
            logger.info("Add Category button visible: {}", visible);
            return visible;
        } catch (Exception e) {
            logger.info("Add Category button not visible (expected for users)");
            return false;
        }
    }

    /**
     * Check if edit buttons are disabled
     */
    public boolean areEditButtonsDisabled() {
        try {
            if (editButtons.isEmpty()) {
                logger.info("No edit buttons found");
                return true; // If no buttons found, consider as "disabled" state
            }

            for (WebElement editButton : editButtons) {
                // Check if button is enabled and clickable
                boolean isEnabled = editButton.isEnabled();
                boolean isDisplayed = editButton.isDisplayed();
                
                // If button is enabled and displayed, it means it's NOT disabled
                if (isEnabled && isDisplayed) {
                    logger.info("Found ENABLED edit button - button is clickable");
                    return false; // Found an enabled button, so NOT all disabled
                }
            }
            
            logger.info("All edit buttons are disabled");
            return true;
        } catch (Exception e) {
            logger.error("Error checking edit button status", e);
            return false;
        }
    }

    /**
     * Check if delete buttons are disabled
     */
    public boolean areDeleteButtonsDisabled() {
        try {
            if (deleteButtons.isEmpty()) {
                logger.info("No delete buttons found");
                return true;
            }

            for (WebElement deleteButton : deleteButtons) {
                String disabled = deleteButton.getAttribute("disabled");
                String ariaDisabled = deleteButton.getAttribute("aria-disabled");
                
                // Check if disabled attribute exists or aria-disabled is true
                if (disabled == null && (ariaDisabled == null || !ariaDisabled.equals("true"))) {
                    logger.info("Found enabled delete button");
                    return false;
                }
            }
            
            logger.info("All delete buttons are disabled");
            return true;
        } catch (Exception e) {
            logger.error("Error checking delete button status", e);
            return false;
        }
    }

    // ========== ENHANCED SORTING METHODS ==========

    /**
     * Get sort direction for a column
     */
    public String getSortDirection(String columnName) {
        logger.info("Getting sort direction for column: {}", columnName);
        
        try {
            for (WebElement header : tableHeaders) {
                String headerText = getText(header).trim();
                
                // Remove sorting indicators from header text for comparison
                String cleanHeaderText = headerText.replaceAll("[↑↓]", "").trim();
                
                if (cleanHeaderText.equalsIgnoreCase(columnName)) {
                    // Check for ascending indicator
                    if (headerText.contains("↑") || headerText.contains("asc")) {
                        logger.info("Column '{}' sorted in ascending order", columnName);
                        return "ascending";
                    }
                    // Check for descending indicator
                    else if (headerText.contains("↓") || headerText.contains("desc")) {
                        logger.info("Column '{}' sorted in descending order", columnName);
                        return "descending";
                    }
                    else {
                        logger.info("Column '{}' has no sort direction", columnName);
                        return "none";
                    }
                }
            }
            
            logger.warn("Column '{}' not found", columnName);
            return "none";
        } catch (Exception e) {
            logger.error("Error getting sort direction", e);
            return "none";
        }
    }

    /**
     * Verify categories are sorted in specified order
     */
    public boolean verifySortedOrder(String columnName, String direction) {
        logger.info("Verifying sorted order for column '{}' in '{}' direction", columnName, direction);
        
        try {
            // Get column index
            int columnIndex = -1;
            for (int i = 0; i < tableHeaders.size(); i++) {
                String headerText = getText(tableHeaders.get(i)).trim().replaceAll("[↑↓]", "").trim();
                if (headerText.equalsIgnoreCase(columnName)) {
                    columnIndex = i;
                    break;
                }
            }
            
            if (columnIndex == -1) {
                logger.warn("Column '{}' not found", columnName);
                return false;
            }
            
            // Get all values from the column
            List<String> values = new java.util.ArrayList<>();
            for (WebElement row : categoryRows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (columnIndex < cells.size()) {
                    String cellValue = getText(cells.get(columnIndex)).trim();
                    if (!cellValue.isEmpty()) {
                        values.add(cellValue);
                    }
                }
            }
            
            // Check if sorted
            if (values.size() < 2) {
                logger.info("Not enough data to verify sorting");
                return true; // Can't verify with less than 2 items
            }
            
            boolean isSorted = true;
            for (int i = 0; i < values.size() - 1; i++) {
                String current = values.get(i);
                String next = values.get(i + 1);
                
                int comparison;
                // Try numeric comparison for ID column
                if (columnName.equalsIgnoreCase("ID")) {
                    try {
                        comparison = Integer.compare(Integer.parseInt(current), Integer.parseInt(next));
                    } catch (NumberFormatException e) {
                        comparison = current.compareToIgnoreCase(next);
                    }
                } else {
                    comparison = current.compareToIgnoreCase(next);
                }
                
                if (direction.equalsIgnoreCase("ascending") && comparison > 0) {
                    isSorted = false;
                    break;
                } else if (direction.equalsIgnoreCase("descending") && comparison < 0) {
                    isSorted = false;
                    break;
                }
            }
            
            logger.info("Column '{}' is {} sorted in {} order", columnName, isSorted ? "correctly" : "NOT", direction);
            return isSorted;
            
        } catch (Exception e) {
            logger.error("Error verifying sorted order", e);
            return false;
        }
    }

    // ========== ENHANCED PAGINATION METHODS ==========

    /**
     * Get current page number
     */
    public int getCurrentPageNumber() {
        try {
            if (activePageNumber != null && isDisplayed(activePageNumber)) {
                String pageText = getText(activePageNumber);
                int pageNum = Integer.parseInt(pageText);
                logger.info("Current page number: {}", pageNum);
                return pageNum;
            }
            logger.info("No active page found, assuming page 1");
            return 1;
        } catch (Exception e) {
            logger.error("Error getting current page number", e);
            return 1;
        }
    }

    /**
     * Check if Previous button is enabled
     */
    public boolean isPreviousButtonEnabled() {
        try {
            if (previousButtonPagination == null) {
                return false;
            }
            
            WebElement parentLi = previousButtonPagination.findElement(By.xpath(".."));
            String className = parentLi.getAttribute("class");
            boolean enabled = !className.contains("disabled");
            logger.info("Previous button enabled: {}", enabled);
            return enabled;
        } catch (Exception e) {
            logger.error("Error checking Previous button status", e);
            return false;
        }
    }

    /**
     * Check if Next button is enabled
     */
    public boolean isNextButtonEnabled() {
        try {
            if (nextButton == null) {
                return false;
            }
            
            WebElement parentLi = nextButton.findElement(By.xpath(".."));
            String className = parentLi.getAttribute("class");
            boolean enabled = !className.contains("disabled");
            logger.info("Next button enabled: {}", enabled);
            return enabled;
        } catch (Exception e) {
            logger.error("Error checking Next button status", e);
            return false;
        }
    }

    /**
     * Click Next button in pagination
     */
    public void clickNextPageButton() {
        logger.info("Clicking Next page button");
        try {
            if (isNextButtonEnabled()) {
                click(nextButton);
                Thread.sleep(1000); // Wait for page load
            } else {
                logger.warn("Next button is disabled, cannot click");
            }
        } catch (Exception e) {
            logger.error("Error clicking Next button", e);
        }
    }

    /**
     * Click Previous button in pagination
     */
    public void clickPreviousPageButton() {
        logger.info("Clicking Previous page button");
        try {
            if (isPreviousButtonEnabled()) {
                click(previousButtonPagination);
                Thread.sleep(1000); // Wait for page load
            } else {
                logger.warn("Previous button is disabled, cannot click");
            }
        } catch (Exception e) {
            logger.error("Error clicking Previous button", e);
        }
    }

    /**
     * Verify category list contains only matching results
     */
    public boolean categoryListContainsOnly(String searchTerm) {
        logger.info("Verifying category list contains only: {}", searchTerm);
        
        try {
            if (categoryRows.isEmpty()) {
                logger.info("No categories found");
                return false;
            }
            
            for (WebElement row : categoryRows) {
                String rowText = getText(row).toLowerCase();
                if (!rowText.contains(searchTerm.toLowerCase())) {
                    logger.info("Found row that doesn't match search term: {}", rowText);
                    return false;
                }
            }
            
            logger.info("All categories match search term");
            return true;
        } catch (Exception e) {
            logger.error("Error verifying category list", e);
            return false;
        }
    }

    /**
     * Verify category list filtered by parent
     */
    public boolean categoryListFilteredByParent(String parentName) {
        logger.info("Verifying category list filtered by parent: {}", parentName);
        
        try {
            if (categoryRows.isEmpty()) {
                logger.info("No categories found");
                return false;
            }
            
            // Find Parent column index
            int parentColumnIndex = -1;
            for (int i = 0; i < tableHeaders.size(); i++) {
                String headerText = getText(tableHeaders.get(i)).trim();
                if (headerText.equalsIgnoreCase("Parent")) {
                    parentColumnIndex = i;
                    break;
                }
            }
            
            if (parentColumnIndex == -1) {
                logger.warn("Parent column not found");
                return false;
            }
            
            // Check each row
            for (WebElement row : categoryRows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (parentColumnIndex < cells.size()) {
                    String parentValue = getText(cells.get(parentColumnIndex)).trim();
                    if (!parentValue.equalsIgnoreCase(parentName) && !parentValue.isEmpty()) {
                        logger.info("Found row with different parent: {}", parentValue);
                        return false;
                    }
                }
            }
            
            logger.info("All categories have parent: {}", parentName);
            return true;
        } catch (Exception e) {
            logger.error("Error verifying parent filter", e);
            return false;
        }
    }

}
