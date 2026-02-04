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

    @FindBy(xpath = "//h3[normalize-space()='Categories']")
    private WebElement categoriesPageTitle;
    
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
        try {
            String currentUrl = getCurrentUrl();
            boolean urlCorrect = currentUrl.contains("/categories") && !currentUrl.contains("/add");
            boolean titleDisplayed = isDisplayed(categoriesPageTitle);

            logger.info("On category list page - URL correct: {}, Title displayed: {}", 
                        urlCorrect, titleDisplayed);

            return urlCorrect && titleDisplayed;
        } catch (Exception e) {
            logger.error("Error verifying Category List Page", e);
            return false;
        }
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
            for (WebElement header : tableHeaders) {
                String headerText = getText(header).trim();

                if (headerText.startsWith(columnName)) {

                    // Look for span inside header (your actual case)
                    List<WebElement> spans = header.findElements(By.tagName("span"));
                    for (WebElement span : spans) {
                        String spanText = span.getText().trim();
                        if (spanText.contains("↑") || spanText.contains("↓")) {
                            logger.info("Column '{}' has sorting indicator via <span>: {}", columnName, spanText);
                            return true;
                        }
                    }

                    // Fallback: check full HTML
                    String html = header.getAttribute("innerHTML");
                    if (html.contains("↑") || html.contains("↓")) {
                        logger.info("Column '{}' has sorting indicator via HTML", columnName);
                        return true;
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
                String headerText = getText(header).trim();
                if (headerText.equalsIgnoreCase(columnName)) {
                    click(header);
                    logger.info("Clicked on column header: {}", columnName);
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

    public void refreshPage() {
        logger.info("Refreshing the current page");
        driver.navigate().refresh();
    }

}
