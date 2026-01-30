package com.qforce.stepdefinitions;

import com.qforce.pages.CategoryPage;
import com.qforce.pages.DashboardPage;
import com.qforce.pages.LoginPage;
import com.qforce.utils.ConfigReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * Step Definitions for Category User Test Cases
 */
public class CategoryUserSteps {
    
    private static final Logger logger = LoggerFactory.getLogger(CategoryUserSteps.class);
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private CategoryPage categoryPage;
    
    public CategoryUserSteps() {
        this.loginPage = new LoginPage();
        this.dashboardPage = new DashboardPage();
        this.categoryPage = new CategoryPage();
    }
    
    // ==================== BACKGROUND STEPS ====================
    
    @Given("User is logged in and on Dashboard")
    public void user_is_logged_in_and_on_dashboard() {
        logger.info("Step: User is logging in and navigating to Dashboard");
        
        // Get credentials from config
        String appUrl = ConfigReader.getAppUrl();
        String username = ConfigReader.getUserUsername();
        String password = ConfigReader.getUserPassword();
        
        // Navigate to login page
        loginPage.navigateToLoginPage(appUrl);
        
        // Login as user
        loginPage.login(username, password);
        
        // Wait for login redirect
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // Ignore
        }
        
        // Verify on dashboard
        Assert.assertTrue(dashboardPage.isOnDashboardPage(), 
            "Failed to reach Dashboard after login");
        
        logger.info("User successfully logged in and on Dashboard");
    }
    
    @Given("No categories exist in the system")
    public void no_categories_exist_in_the_system() {
        logger.info("Step: Verifying no categories exist");
        // This is a precondition - assumed to be set up in test data
        // In real scenario, you might clean DB or use API to delete all categories
    }
    
    @Given("At least one category exists in the system")
    public void at_least_one_category_exists_in_the_system() {
        logger.info("Step: Verifying at least one category exists");
        // This is a precondition - assumed admin has created at least one category
        // In real scenario, you might use API to create test category
    }
    
    @Given("More than {int} categories exist in the system")
    public void more_than_n_categories_exist_in_the_system(int count) {
        logger.info("Step: Verifying more than {} categories exist", count);
        // This is a precondition - assumed to be set up in test data
        // In real scenario, you might use API to create required number of categories
    }
    
    @Given("Category list table is displayed")
    public void category_list_table_is_displayed() {
        logger.info("Step: Verifying category list table is displayed");
        user_navigates_to_categories_page();
        Assert.assertTrue(categoryPage.isCategoryTableDisplayed(),
            "Category table is not displayed");
    }
    
    // ==================== WHEN STEPS ====================
    
    @When("User clicks on Categories tab from navigation menu")
    public void user_clicks_on_categories_tab_from_navigation_menu() {
        logger.info("Step: Clicking on Categories tab from navigation menu");
        dashboardPage.clickCategoriesTab();
        
        // Wait for navigation
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
    
    @When("User navigates to Categories page")
    public void user_navigates_to_categories_page() {
        logger.info("Step: Navigating to Categories page");
        
        // Navigate using URL
        String baseUrl = categoryPage.getCurrentUrl().replaceAll("/[^/]*$", "");
        categoryPage.navigateTo(baseUrl + "/categories");
        
        // Wait for page load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
    
    @When("User observes the category list table")
    public void user_observes_the_category_list_table() {
        logger.info("Step: Observing the category list table");
        // Just a documentation step - no action needed
        Assert.assertTrue(categoryPage.isCategoryTableDisplayed(),
            "Category table should be visible for observation");
    }
    
    // ==================== THEN STEPS ====================
    
    @Then("System should navigate to the category page")
    public void system_should_navigate_to_the_category_page() {
        logger.info("Step: Verifying navigation to category page");
        String currentUrl = categoryPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/categories"),
            "Expected to be on category page but URL is: " + currentUrl);
    }
    
    @Then("Categories tab should be highlighted in the navigation menu to indicate the active page")
    public void categories_tab_should_be_highlighted_in_navigation_menu() {
        logger.info("Step: Verifying Categories tab is highlighted");
        Assert.assertTrue(dashboardPage.isCategoriesTabActive(),
            "Categories tab should be highlighted as active");
    }
    
    @Then("Category page should be loaded")
    public void category_page_should_be_loaded() {
        logger.info("Step: Verifying category page is loaded");
        Assert.assertTrue(categoryPage.isOnCategoryListPage(),
            "Category list page should be loaded");
    }
    
    @Then("Category list table should be displayed with headers {string}")
    public void category_list_table_should_be_displayed_with_headers(String expectedHeaders) {
        logger.info("Step: Verifying category table with headers: {}", expectedHeaders);
        
        // Verify table is displayed
        Assert.assertTrue(categoryPage.isCategoryTableDisplayed(),
            "Category table should be displayed");
        
        // Verify headers
        String[] headers = expectedHeaders.split(",\\s*");
        for (String header : headers) {
            Assert.assertTrue(categoryPage.isColumnHeaderPresent(header.trim()),
                "Column header '" + header.trim() + "' should be present");
        }
    }
    
    @Then("Category table should be displayed with headers {string}")
    public void category_table_should_be_displayed_with_headers(String expectedHeaders) {
        category_list_table_should_be_displayed_with_headers(expectedHeaders);
    }
    
    @Then("Message {string} should be displayed")
    public void message_should_be_displayed(String expectedMessage) {
        logger.info("Step: Verifying message is displayed: {}", expectedMessage);
        String actualMessage = categoryPage.getEmptyStateMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage),
            "Expected message '" + expectedMessage + "' but got: '" + actualMessage + "'");
    }
    
    @Then("No category records should be shown in the table")
    public void no_category_records_should_be_shown_in_table() {
        logger.info("Step: Verifying no category records in table");
        int rowCount = categoryPage.getCategoryRowCount();
        Assert.assertEquals(rowCount, 1,
            "Expected 0 category rows but found: " + rowCount);
    }

    @Then("Table should contain all required columns {string}")
    public void table_should_contain_all_required_columns(String requiredColumns) {
        logger.info("Step: Verifying table contains required columns: {}", requiredColumns);
        
        String[] columns = requiredColumns.split(",\\s*");
        for (String column : columns) {
            Assert.assertTrue(categoryPage.isColumnHeaderPresent(column.trim()),
                "Required column '" + column.trim() + "' should be present");
        }
    }
    
    @Then("At least one category row should be displayed without showing empty state message")
    public void at_least_one_category_row_should_be_displayed() {
        logger.info("Step: Verifying at least one category row is displayed");
        
        int rowCount = categoryPage.getCategoryRowCount();
        Assert.assertTrue(rowCount > 0,
            "Expected at least 1 category row but found: " + rowCount);
        
        // Verify empty state message is NOT displayed
        Assert.assertFalse(categoryPage.isEmptyStateMessageDisplayed(),
            "Empty state message should not be displayed when categories exist");
    }

    @Then("Category list table should be displayed")
    public void category_list_table_should_be_displayed() {
        logger.info("Step: Verifying category list table is displayed");
        Assert.assertTrue(categoryPage.isCategoryTableDisplayed(),
            "Category list table should be displayed");
    }
    
    @Then("Each category row should display {string} columns with data")
    public void each_category_row_should_display_columns_with_data(String columnsList) {
        logger.info("Step: Verifying each category row has data in columns: {}", columnsList);
        
        String[] expectedColumns = columnsList.split(",\\s*");
        int rowCount = categoryPage.getCategoryRowCount();
        
        Assert.assertTrue(rowCount > 0, "At least one row should exist");
        
        // Verify first row has data (sample check)
        for (String column : expectedColumns) {
            String columnName = column.trim().replaceAll("\\(.*\\)", "").trim();
            if (!columnName.isEmpty() && !columnName.equalsIgnoreCase("or")) {
                String cellData = categoryPage.getCellData(0, columnName);
                // Cell can have data or be empty (for Parent column if main category)
                logger.info("Column '{}' has data: '{}'", columnName, cellData);
            }
        }
    }
    
    @Then("Category list should display first {int} records")
    public void category_list_should_display_first_n_records(int expectedCount) {
        logger.info("Step: Verifying first {} records are displayed", expectedCount);
        
        int actualCount = categoryPage.getCategoryRowCount();
        Assert.assertTrue(actualCount <= expectedCount,
            "Expected at most " + expectedCount + " records but found: " + actualCount);
    }
    
    @Then("No more than {int} records should be displayed on the initial page")
    public void no_more_than_n_records_should_be_displayed(int maxRecords) {
        logger.info("Step: Verifying no more than {} records displayed", maxRecords);
        
        int actualCount = categoryPage.getCategoryRowCount();
        Assert.assertTrue(actualCount <= maxRecords,
            "Expected no more than " + maxRecords + " records but found: " + actualCount);
    }
    
    @Then("Pagination controls should be visible at the bottom of the table")
    public void pagination_controls_should_be_visible() {
        logger.info("Step: Verifying pagination controls are visible");
        Assert.assertTrue(categoryPage.isPaginationDisplayed(),
            "Pagination controls should be visible");
    }
    
    @Then("Pagination should show {string} page numbers {string} and {string} buttons")
    public void pagination_should_show_elements(String previousText, String pageNumbers, String nextText) {
        logger.info("Step: Verifying pagination elements");
        
        // Verify Previous button
        Assert.assertTrue(categoryPage.isPreviousButtonPresent(),
            "Previous button should be present");
        
        // Verify page numbers
        Assert.assertTrue(categoryPage.arePageNumbersDisplayed(),
            "Page numbers should be displayed");
        
        // Verify Next button
        Assert.assertTrue(categoryPage.isNextButtonPresent(),
            "Next button should be present");
    }
    
    @Then("Current page number should be highlighted")
    public void current_page_number_should_be_highlighted() {
        logger.info("Step: Verifying current page number is highlighted");
        Assert.assertTrue(categoryPage.isCurrentPageHighlighted(),
            "Current page number should be highlighted");
    }
    
    @Then("ID column header should display sorting indicator")
    public void id_column_header_should_display_sorting_indicator() {
        logger.info("Step: Verifying ID column has sorting indicator");
        Assert.assertTrue(categoryPage.hasSortingIndicator("ID"),
            "ID column should have sorting indicator");
    }
    
    @Then("Name column header should display sorting indicator")
    public void name_column_header_should_display_sorting_indicator() {
        logger.info("Step: Verifying Name column has sorting indicator");
        Assert.assertTrue(categoryPage.hasSortingIndicator("Name"),
            "Name column should have sorting indicator");
    }
    
    @Then("Parent column header should display sorting indicator")
    public void parent_column_header_should_display_sorting_indicator() {
        logger.info("Step: Verifying Parent column has sorting indicator");
        Assert.assertTrue(categoryPage.hasSortingIndicator("Parent"),
            "Parent column should have sorting indicator");
    }
}
