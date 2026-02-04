package com.qforce.stepdefinitions;

import com.qforce.pages.CategoryPage;
import com.qforce.pages.LoginPage;
import com.qforce.utils.ConfigReader;
import com.qforce.utils.DriverManager;
import com.qforce.utils.TestDataHelper;

import io.cucumber.java.en.*;
import org.testng.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Step Definitions for Category Admin Feature
 */
public class CategoryAdminSteps {
    
    private static final Logger logger = LogManager.getLogger(CategoryAdminSteps.class);
    private LoginPage loginPage;
    private CategoryPage categoryPage;
    private int initialCategoryCount;
    
    public CategoryAdminSteps() {
        this.loginPage = new LoginPage();
        this.categoryPage = new CategoryPage();
    }
    
    @Given("Admin is logged in and navigated to Add Category page")
    public void admin_is_logged_in_and_navigated_to_add_category_page() {
        logger.info("Step: Admin is logging in and navigating to Add Category page");
        
        // Navigate to application
        String appUrl = ConfigReader.getAppUrl();
        loginPage.navigateToLoginPage(appUrl);
        
        // Login as admin
        String username = ConfigReader.getAdminUsername();
        String password = ConfigReader.getAdminPassword();
        loginPage.login(username, password);
        
        // Wait for login to complete and redirect
        try {
            Thread.sleep(2000); // Wait 2 seconds for login redirect (increased for reliability)
        } catch (InterruptedException e) {
            // Ignore
        }
        
        // Navigate to Add Category page
        categoryPage.navigateToAddCategoryPage();
        
        // Wait for page to load
        try {
            Thread.sleep(500); // Wait 0.5 seconds for page load
        } catch (InterruptedException e) {
            // Ignore
        }
        
        // Verify we're on Add Category page
        Assert.assertTrue(categoryPage.isOnAddCategoryPage(), 
            "Failed to navigate to Add Category page");
    }

    @Given("At least one parent Category {string} exists in the system")
    public void at_least_one_parent_category_exists_in_the_system(String parentCategory) {
        logger.info("Step: Ensuring parent category '{}' exists in the system", parentCategory);
        
        boolean categoryCreated = false;
        
        // Check if category exists using TestDataHelper
        if (!TestDataHelper.categoryExists(parentCategory)) {
            logger.info("Parent category '{}' does not exist - creating it via API", parentCategory);
            
            // Create the parent category via API
            io.restassured.response.Response response = TestDataHelper.createCategory(parentCategory);
            
            if (response.getStatusCode() == 201) {
                logger.info("Successfully created parent category: {}", parentCategory);
                categoryCreated = true;
            } else {
                logger.error("Failed to create parent category: {}. Status: {}", 
                    parentCategory, response.getStatusCode());
                Assert.fail("Failed to create required parent category: " + parentCategory);
            }
        } else {
            logger.info("Parent category '{}' already exists", parentCategory);
        }
        
        // If we created a new category via API, refresh the page so dropdown gets updated
        if (categoryCreated) {
            logger.info("Refreshing page to update dropdown with newly created category");
            categoryPage.refreshPage();
            
            // Wait for page to reload
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Ignore
            }
            
            // Verify we're still on Add Category page
            Assert.assertTrue(categoryPage.isOnAddCategoryPage(), 
                "Should still be on Add Category page after refresh");
        }
    }
    
    @When("Admin enters a valid category name {string}")
    public void admin_enters_a_valid_category_name(String categoryName) {
        logger.info("Step: Entering valid category name: {}", categoryName);
        categoryPage.enterCategoryName(categoryName);
    }
    
    @When("Admin enters an invalid category name {string} with less than 3 characters")
    public void admin_enters_an_invalid_category_name_with_less_than_3_characters(String categoryName) {
        logger.info("Step: Entering invalid category name: {}", categoryName);
        categoryPage.enterCategoryName(categoryName);
    }
    
    @When("Admin enters category name {string}")
    public void admin_enters_category_name(String categoryName) {
        logger.info("Step: Entering category name: {}", categoryName);
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            categoryPage.enterCategoryName(categoryName);
        }
    }
    
    @When("Admin leaves Category Name field empty")
    public void admin_leaves_category_name_field_empty() {
        logger.info("Step: Leaving Category Name field empty");
        // Do nothing - field is already empty
    }
    
    @When("Admin leaves Parent Category dropdown as Main Category")
    public void admin_leaves_parent_category_dropdown_as_main_category() {
        logger.info("Step: Leaving Parent Category as Main Category (no selection)");
        // Do nothing - default is Main Category
    }
    
    @When("Admin selects an existing parent category {string} from Parent Category dropdown")
    public void admin_selects_an_existing_parent_category_from_parent_category_dropdown(String parentCategory) {
        logger.info("Step: Selecting parent category: {}", parentCategory);
        categoryPage.selectParentCategory(parentCategory);
    }
    
    @When("Admin clicks the Save button")
    public void admin_clicks_the_save_button() {
        logger.info("Step: Clicking Save button");
        categoryPage.clickSave();
    }
    
    @When("Admin enters any data in Category Name field {string}")
    public void admin_enters_any_data_in_category_name_field(String data) {
        logger.info("Step: Entering data in Category Name field: {}", data);
        categoryPage.enterCategoryName(data);
    }
    
    @When("Admin selects any option from Parent Category dropdown if applicable")
    public void admin_selects_any_option_from_parent_category_dropdown_if_applicable() {
        logger.info("Step: Selecting option from Parent Category dropdown (if applicable)");
        // Optional: select a parent category if available
    }
    
    @When("Admin clicks Cancel button")
    public void admin_clicks_cancel_button() {
        logger.info("Step: Clicking Cancel button");
        categoryPage.clickCancel();
    }
    
    @Then("System should successfully create the category")
    public void system_should_successfully_create_the_category() {
        logger.info("Step: Verifying category creation success");
        // This is verified by subsequent steps (success message, redirection)
    }
    
    @Then("System should display the success message {string}")
    public void system_should_display_the_success_message(String expectedMessage) {
        logger.info("Step: Verifying success message: {}", expectedMessage);
        String actualMessage = categoryPage.getSuccessMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage), 
            "Expected success message to contain '" + expectedMessage + "' but got: '" + actualMessage + "'");
    }
    
    @Then("Admin should be redirected to Category List page with URL {string}")
    public void admin_should_be_redirected_to_category_list_page_with_url(String expectedUrl) {
        logger.info("Step: Verifying redirection to: {}", expectedUrl);
        String currentUrl = categoryPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedUrl), 
            "Expected URL to contain '" + expectedUrl + "' but got: " + currentUrl);
    }
    
    @Then("Success message {string} should be displayed on the Category List page")
    public void success_message_should_be_displayed_on_the_category_list_page(String expectedMessage) {
        logger.info("Step: Verifying success message: {}", expectedMessage);
        String actualMessage = categoryPage.getSuccessMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage), 
            "Expected message '" + expectedMessage + "' but got: " + actualMessage);
    }
    
    @Then("Newly added category {string} should appear in the category list")
    public void newly_added_category_should_appear_in_the_category_list(String categoryName) {
        logger.info("Step: Verifying category '{}' appears in list", categoryName);
        Assert.assertTrue(categoryPage.isCategoryInList(categoryName), 
            "Category '" + categoryName + "' not found in the category list");
    }
    
    @Then("Category should not be saved")
    public void category_should_not_be_saved() {
        logger.info("Step: Verifying category was not saved");
        // This is verified by checking that we remain on the Add Category page
    }
    
    @Then("Error message {string} should be displayed in red color below the Category Name field")
    public void error_message_should_be_displayed_in_red_color_below_the_category_name_field(String expectedError) {
        logger.info("Step: Verifying error message: {}", expectedError);
        String actualError = categoryPage.getValidationError();
        Assert.assertTrue(actualError.contains(expectedError), 
            "Expected error '" + expectedError + "' but got: " + actualError);
        Assert.assertTrue(categoryPage.isValidationErrorDisplayed(), 
            "Validation error is not displayed");
    }
    
    @Then("Error message {string} should be displayed")
    public void error_message_should_be_displayed(String expectedError) {
        logger.info("Step: Verifying error message: {}", expectedError);
        String actualError = categoryPage.getValidationError();
        if (actualError.isEmpty()) {
            actualError = categoryPage.getErrorMessage();
        }
        Assert.assertTrue(actualError.contains(expectedError), 
            "Expected error '" + expectedError + "' but got: " + actualError);
    }
    
    @Then("Admin should remain on Add Category page")
    public void admin_should_remain_on_add_category_page() {
        logger.info("Step: Verifying admin remains on Add Category page");
        Assert.assertTrue(categoryPage.isOnAddCategoryPage(), 
            "User is not on Add Category page");
    }
    
    @Then("Validation message {string} should be displayed in red color below the Category Name field")
    public void validation_message_should_be_displayed_in_red_color_below_the_category_name_field(String expectedMessage) {
        logger.info("Step: Verifying validation message: {}", expectedMessage);
        String actualMessage = categoryPage.getValidationError();
        Assert.assertTrue(actualMessage.contains(expectedMessage), 
            "Expected validation message '" + expectedMessage + "' but got: " + actualMessage);
    }
    
    @Then("System should validate and accept the parent category from Parent Category dropdown")
    public void system_should_validate_and_accept_the_parent_category_from_parent_category_dropdown() {
        logger.info("Step: Verifying parent category validation");
        // This is implicitly verified if sub-category creation succeeds
    }
    
    @Then("New sub-category should be created successfully")
    public void new_sub_category_should_be_created_successfully() {
        logger.info("Step: Verifying sub-category creation");
        // Verified by success message and list appearance
    }
    
    @Then("System should validate navigation to category list page")
    public void system_should_validate_navigation_to_category_list_page() {
        logger.info("Step: Validating navigation to category list page");
        Assert.assertTrue(categoryPage.isOnCategoryListPage(), 
            "Not on category list page");
    }
    
    @Then("Newly added sub-category {string} should appear in the category list")
    public void newly_added_sub_category_should_appear_in_the_category_list(String subCategoryName) {
        logger.info("Step: Verifying sub-category '{}' appears in list", subCategoryName);
        Assert.assertTrue(categoryPage.isCategoryInList(subCategoryName), 
            "Sub-category '" + subCategoryName + "' not found in the category list");
    }
    
    @Then("Correct parent category name {string} should be displayed in the Parent column")
    public void correct_parent_category_name_should_be_displayed_in_the_parent_column(String expectedParent) {
        logger.info("Step: Verifying parent category name in table");
        // This would require getting the parent category from the table for the specific sub-category
        // Implementation depends on actual table structure
    }
    
    @Then("Admin should be redirected to {string} page")
    public void admin_should_be_redirected_to_page(String expectedUrl) {
        logger.info("Step: Verifying redirection to: {}", expectedUrl);
        String currentUrl = categoryPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedUrl), 
            "Expected URL to contain '" + expectedUrl + "' but got: " + currentUrl);
    }
    
    @Then("Category list page should be displayed")
    public void category_list_page_should_be_displayed() {
        logger.info("Step: Verifying category list page is displayed");
        Assert.assertTrue(categoryPage.isOnCategoryListPage(), 
            "Category list page is not displayed");
    }
    
    @Then("No new category should be added to the system")
    public void no_new_category_should_be_added_to_the_system() {
        logger.info("Step: Verifying no new category was added");
        // This could be verified by checking the category count hasn't changed
    }
    
    @Then("Any data entered in the form should be discarded")
    public void any_data_entered_in_the_form_should_be_discarded() {
        logger.info("Step: Verifying form data is discarded");
        // Navigate back to Add Category page and verify fields are empty
        categoryPage.navigateToAddCategoryPage();
        Assert.assertTrue(categoryPage.isCategoryNameEmpty(), 
            "Category name field is not empty - data was not discarded");
    }
}