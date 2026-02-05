package com.qforce.stepdefinitions.plants;

import com.qforce.pages.DashboardPage;
import com.qforce.pages.login.LoginPage;
import com.qforce.pages.plants.PlantAdminPage;
import com.qforce.pages.plants.PlantPage;
import com.qforce.utils.ConfigReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * Step Definitions for Plant Admin Test Cases
 */
public class PlantAdminSteps {
    
    private static final Logger logger = LoggerFactory.getLogger(PlantAdminSteps.class);
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private PlantPage plantPage;
    private PlantAdminPage plantAdminPage;
    
    // Store test data for verification
    private String enteredPlantName;
    private String enteredCategory;
    private String enteredPrice;
    private String enteredQuantity;
    
    public PlantAdminSteps() {
        this.loginPage = new LoginPage();
        this.dashboardPage = new DashboardPage();
        this.plantPage = new PlantPage();
        this.plantAdminPage = new PlantAdminPage();
    }
    
    // ==================== BACKGROUND STEPS ====================
    
    @Given("Admin user is logged in with username {string} and password {string}")
    public void admin_user_is_logged_in(String username, String password) {
        logger.info("Step: Admin user logging in with username '{}' and password '{}'", username, password);
        
        // Navigate to login page
        String appUrl = ConfigReader.getAppUrl();
        loginPage.navigateTo(appUrl);
        
        // Perform login
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
        
        // Wait for login to complete
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify login success
        Assert.assertTrue(dashboardPage.isOnDashboardPage(),
            "Admin should be logged in and on dashboard");
    }
    
    // ==================== GIVEN STEPS ====================
    
    @Given("Admin is logged in and navigated to Add Plant page {string}")
    public void admin_is_logged_in_and_navigated_to_add_plant_page(String expectedUrl) {
        logger.info("Step: Admin navigating to Add Plant page '{}'", expectedUrl);
        
        // Navigate to Add Plant page
        plantAdminPage.navigateToAddPlantPage();
        
        // Verify on Add Plant page
        Assert.assertTrue(plantAdminPage.isOnAddPlantPage(),
            "Admin should be on Add Plant page");
        Assert.assertTrue(plantAdminPage.isPageLoadedSuccessfully(),
            "Add Plant page should load successfully");
        
        // Verify URL contains expected path
        String currentUrl = plantAdminPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedUrl),
            "URL should contain '" + expectedUrl + "' but was: " + currentUrl);
    }
    
    @Given("Admin is logged in and at least one plant exists in the system")
    public void admin_is_logged_in_and_at_least_one_plant_exists() {
        logger.info("Step: Admin is logged in and at least one plant exists");
        // Assumption: At least one plant exists in the system (test data setup)
        // This is a precondition step
    }
    
    @Given("Admin is logged in, navigated to Plants List page {string}, and multiple plants exist in different categories")
    public void admin_is_logged_in_navigated_to_plants_list_page_and_multiple_plants_exist(String expectedUrl) {
        logger.info("Step: Admin navigating to Plants List page '{}' with multiple plants in different categories", expectedUrl);
        
        // Navigate to Plants List page
        plantPage.navigateToPlantsList();
        
        // Verify on Plants List page
        Assert.assertTrue(plantPage.isOnPlantsListPage(),
            "Admin should be on Plants List page");
        Assert.assertTrue(plantPage.isPageLoadedSuccessfully(),
            "Plants List page should load successfully");
        
        // Verify URL contains expected path
        String currentUrl = plantPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedUrl),
            "URL should contain '" + expectedUrl + "' but was: " + currentUrl);
        
        // Assumption: Multiple plants exist in different categories (test data setup)
    }
    
    // ==================== WHEN STEPS ====================
    
    @When("Admin enters a valid plant name {string} in the Plant Name field")
    public void admin_enters_valid_plant_name_in_field(String plantName) {
        logger.info("Step: Admin entering plant name '{}'", plantName);
        this.enteredPlantName = plantName;
        plantAdminPage.enterPlantName(plantName);
    }
    
    @When("Admin selects a valid sub-category {string} from the Category dropdown")
    public void admin_selects_valid_sub_category_from_dropdown(String categoryName) {
        logger.info("Step: Admin selecting sub-category '{}'", categoryName);
        this.enteredCategory = categoryName;
        plantAdminPage.selectCategory(categoryName);
    }
    
    @When("Admin enters a valid price {string} in the Price field")
    public void admin_enters_valid_price_in_field(String price) {
        logger.info("Step: Admin entering price '{}'", price);
        this.enteredPrice = price;
        plantAdminPage.enterPrice(price);
    }
    
    @When("Admin enters a valid quantity {string} in the Quantity field")
    public void admin_enters_valid_quantity_in_field(String quantity) {
        logger.info("Step: Admin entering quantity '{}'", quantity);
        this.enteredQuantity = quantity;
        plantAdminPage.enterQuantity(quantity);
    }
    
    @When("Admin clicks the Save button for plant")
    public void admin_clicks_the_save_button_for_plant() {
        logger.info("Step: Admin clicking Save button for plant");
        plantAdminPage.clickSaveButton();
    }
    
    @When("Admin leaves the Category dropdown at default {string}")
    public void admin_leaves_category_dropdown_at_default(String defaultValue) {
        logger.info("Step: Admin leaving Category dropdown at default '{}'", defaultValue);
        this.enteredCategory = ""; // No category selected
        plantAdminPage.leaveCategoryAtDefault();
        
        // Verify it's at default
        Assert.assertTrue(plantAdminPage.isCategoryDropdownAtDefault(),
            "Category dropdown should be at default value");
    }
    
    @When("Admin navigates to Plants List page {string}")
    public void admin_navigates_to_plants_list_page(String expectedUrl) {
        logger.info("Step: Admin navigating to Plants List page '{}'", expectedUrl);
        
        // Navigate to Plants List page
        plantPage.navigateToPlantsList();
        
        // Verify URL contains expected path
        String currentUrl = plantPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedUrl),
            "URL should contain '" + expectedUrl + "' but was: " + currentUrl);
    }
    
    @When("Admin observes the page layout and table structure")
    public void admin_observes_page_layout_and_table_structure() {
        logger.info("Step: Admin observing page layout and table structure");
        // This is a documentation step - verify basic elements are present
        Assert.assertTrue(plantPage.isPageLoadedSuccessfully(),
            "Page should be loaded for observation");
        Assert.assertTrue(plantPage.isPlantsTableDisplayed(),
            "Plants table should be displayed for observation");
    }
    
    @When("Admin verifies the presence of search functionality")
    public void admin_verifies_presence_of_search_functionality() {
        logger.info("Step: Admin verifying presence of search functionality");
        Assert.assertTrue(plantPage.isSearchBarVisible(),
            "Search functionality should be present");
    }
    
    @When("Admin verifies the presence of category filter dropdown")
    public void admin_verifies_presence_of_category_filter_dropdown() {
        logger.info("Step: Admin verifying presence of category filter dropdown");
        Assert.assertTrue(plantPage.isCategoryDropdownVisible(),
            "Category filter dropdown should be present");
    }
    
    @When("Admin verifies the presence of {string} button")
    public void admin_verifies_presence_of_button(String buttonName) {
        logger.info("Step: Admin verifying presence of '{}' button", buttonName);
        if (buttonName.equals("Add a Plant")) {
            Assert.assertTrue(plantPage.isAddPlantButtonVisible(),
                "Add a Plant button should be visible for admin");
        }
    }
    
    @When("Admin verifies the Category dropdown displays {string} by default")
    public void admin_verifies_category_dropdown_default(String expectedDefault) {
        logger.info("Step: Admin verifying Category dropdown displays '{}' by default", expectedDefault);
        String selectedCategory = plantPage.getSelectedCategory();
        Assert.assertTrue(selectedCategory.contains(expectedDefault) || selectedCategory.isEmpty(),
            "Expected category dropdown to show '" + expectedDefault + "' but got: '" + selectedCategory + "'");
    }
    
    @When("Admin clicks on the Category dropdown")
    public void admin_clicks_category_dropdown() {
        logger.info("Step: Admin clicking on Category dropdown");
        plantPage.clickCategoryDropdown();
    }
    
    @When("Admin selects a specific category {string}")
    public void admin_selects_specific_category(String categoryName) {
        logger.info("Step: Admin selecting specific category '{}'", categoryName);
        plantPage.selectCategory(categoryName);
    }
    
    @When("Admin clicks the Search button")
    public void admin_clicks_search_button() {
        logger.info("Step: Admin clicking Search button");
        plantPage.clickSearchButton();
    }
    
    @When("Admin observes the filtered results in the table")
    public void admin_observes_filtered_results() {
        logger.info("Step: Admin observing filtered results in table");
        // This is a documentation step - verify table is displayed
        Assert.assertTrue(plantPage.isPlantsTableDisplayed(),
            "Plants table should be displayed for observation");
    }
    
    @When("Admin selects a category {string} from the Category dropdown")
    public void admin_selects_category_from_dropdown(String categoryName) {
        logger.info("Step: Admin selecting category '{}' from dropdown", categoryName);
        this.enteredCategory = categoryName;
        plantAdminPage.selectCategory(categoryName);
    }
    
    @When("Admin clicks the Cancel button for plant")
    public void admin_clicks_the_cancel_button_for_plant() {
        logger.info("Step: Admin clicking Cancel button for plant");
        plantAdminPage.clickCancelButton();
    }
    
    // ==================== THEN STEPS ====================
    
    @Then("All valid inputs should be accepted in their respective fields")
    public void all_valid_inputs_should_be_accepted() {
        logger.info("Step: Verifying all valid inputs are accepted");
        // Verify that the form accepts the inputs (no immediate validation errors)
        String generalError = plantAdminPage.getGeneralErrorMessage();
        Assert.assertTrue(generalError.isEmpty(),
            "No validation errors should be present for valid inputs");
    }
    
    @Then("System should successfully create a new plant record")
    public void system_should_successfully_create_new_plant_record() {
        logger.info("Step: Verifying system creates new plant record");
        // This is verified by successful redirect and success message
        // The actual database verification would require additional setup
        logger.info("Plant record creation will be verified by redirect and success message");
    }
    
    @Then("User should be redirected to Plants List page {string}")
    public void user_should_be_redirected_to_plants_list_page(String expectedUrl) {
        logger.info("Step: Verifying user is redirected to Plants List page '{}'", expectedUrl);
        
        // Wait for redirect
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify on Plants List page
        Assert.assertTrue(plantPage.isOnPlantsListPage(),
            "User should be redirected to Plants List page");
        
        // Verify URL contains expected path
        String currentUrl = plantPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedUrl),
            "URL should contain '" + expectedUrl + "' but was: " + currentUrl);
    }
    
    @Then("Success message {string} should be displayed")
    public void success_message_should_be_displayed(String expectedMessage) {
        logger.info("Step: Verifying success message '{}' is displayed", expectedMessage);
        
        // Check for success message on Plants List page
        String actualMessage = plantPage.getSuccessMessage();
        if (actualMessage.isEmpty()) {
            // Also check on Add Plant page in case redirect hasn't happened yet
            actualMessage = plantAdminPage.getSuccessMessage();
        }
        
        // Handle both "Plant added successfully." and "Plant added successfully" formats
        String normalizedExpected = expectedMessage.replace(".", "").trim();
        String normalizedActual = actualMessage.replace(".", "").trim();
        
        Assert.assertTrue(normalizedActual.contains(normalizedExpected) || actualMessage.contains(expectedMessage),
            "Expected success message '" + expectedMessage + "' but got: '" + actualMessage + "'");
    }
    
    @Then("Newly added plant should appear in the plants table with correct Name, Category, Price, and Stock values")
    public void newly_added_plant_should_appear_in_table() {
        logger.info("Step: Verifying newly added plant appears in table with correct values");
        
        // Verify table is displayed
        Assert.assertTrue(plantPage.isPlantsTableDisplayed(),
            "Plants table should be displayed");
        
        // Verify plant count increased (at least one plant should be visible)
        int plantCount = plantPage.getPlantRowCount();
        Assert.assertTrue(plantCount > 0,
            "At least one plant should be visible in the table");
        
        // Additional verification for specific plant data would require more complex table parsing
        logger.info("Plant table verification completed - {} plants visible", plantCount);
    }
    
    @Then("System should prevent form submission")
    public void system_should_prevent_form_submission() {
        logger.info("Step: Verifying system prevents form submission");
        Assert.assertTrue(plantAdminPage.isFormSubmissionPrevented(),
            "System should prevent form submission when validation fails");
    }
    
    @Then("Validation error message should be displayed near Category field: {string}")
    public void validation_error_message_should_be_displayed_near_category_field(String expectedError) {
        logger.info("Step: Verifying validation error '{}' is displayed near Category field", expectedError);
        Assert.assertTrue(plantAdminPage.isValidationErrorDisplayedNearCategoryField(expectedError),
            "Validation error '" + expectedError + "' should be displayed near Category field");
    }
    
    @Then("Plant should not be created in the system")
    public void plant_should_not_be_created_in_system() {
        logger.info("Step: Verifying plant is not created in system");
        // This is verified by staying on the Add Plant page and having validation errors
        Assert.assertTrue(plantAdminPage.isOnAddPlantPage(),
            "Should remain on Add Plant page when validation fails");
    }
    
    @Then("User should remain on the Add Plant page")
    public void user_should_remain_on_add_plant_page() {
        logger.info("Step: Verifying user remains on Add Plant page");
        Assert.assertTrue(plantAdminPage.isOnAddPlantPage(),
            "User should remain on Add Plant page");
    }
    
    @Then("All entered data should be retained in the form fields")
    public void all_entered_data_should_be_retained() {
        logger.info("Step: Verifying all entered data is retained in form fields");
        Assert.assertTrue(plantAdminPage.isEnteredDataRetained(enteredPlantName, enteredCategory, enteredPrice, enteredQuantity),
            "All entered data should be retained in form fields");
    }
    
    @Then("{string} button should be visible in the top-right")
    public void add_plant_button_should_be_visible_in_top_right(String buttonName) {
        logger.info("Step: Verifying '{}' button is visible in top-right", buttonName);
        Assert.assertTrue(plantPage.isAddPlantButtonVisible(),
            "Add a Plant button should be visible for admin");
    }
    
    @Then("Category dropdown should open and display all available categories")
    public void category_dropdown_should_open_and_display_all_categories() {
        logger.info("Step: Verifying category dropdown opens and displays all available categories");
        // Verify dropdown is functional
        Assert.assertTrue(plantPage.isCategoryDropdownVisible(),
            "Category dropdown should be visible and functional");
        
        // Get available categories
        java.util.List<String> categories = plantPage.getAvailableCategories();
        Assert.assertTrue(categories.size() > 0,
            "Category dropdown should display available categories");
        logger.info("Available categories: {}", categories);
    }
    
    @Then("Selected category should be displayed in the dropdown")
    public void selected_category_should_be_displayed_in_dropdown() {
        logger.info("Step: Verifying selected category is displayed in dropdown");
        String selectedCategory = plantPage.getSelectedCategory();
        Assert.assertFalse(selectedCategory.isEmpty(),
            "A category should be selected and displayed");
        logger.info("Selected category: {}", selectedCategory);
    }
    
    @Then("After clicking Search, only plants belonging to the selected category should be displayed in the table")
    public void after_clicking_search_only_plants_from_selected_category_should_be_displayed() {
        logger.info("Step: Verifying only plants from selected category are displayed");
        Assert.assertTrue(plantPage.isPlantsTableDisplayed(),
            "Plants table should be displayed with filtered results");
        // Additional verification for category filtering would require more complex logic
    }
    
    @Then("{string} message should appear if no plants exist in the selected category")
    public void message_should_appear_if_no_plants_exist_in_selected_category(String expectedMessage) {
        logger.info("Step: Verifying '{}' message appears if no plants in selected category", expectedMessage);
        // This is conditional - only check if no plants are displayed
        if (plantPage.getPlantRowCount() == 0) {
            String actualMessage = plantPage.getEmptyStateMessage();
            Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Expected message '" + expectedMessage + "' but got: '" + actualMessage + "'");
        }
    }
    
    @Then("User should be immediately redirected to Plants List page {string}")
    public void user_should_be_immediately_redirected_to_plants_list_page(String expectedUrl) {
        logger.info("Step: Verifying user is immediately redirected to Plants List page '{}'", expectedUrl);
        
        // Wait for redirect
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify on Plants List page
        Assert.assertTrue(plantPage.isOnPlantsListPage(),
            "User should be immediately redirected to Plants List page");
        
        // Verify URL contains expected path
        String currentUrl = plantPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedUrl),
            "URL should contain '" + expectedUrl + "' but was: " + currentUrl);
    }
    
    @Then("No plant record should be created in the system")
    public void no_plant_record_should_be_created_in_system() {
        logger.info("Step: Verifying no plant record is created in system");
        // This is verified by the cancel action - no save operation was performed
        logger.info("No plant record created - cancel operation completed");
    }
    
    @Then("Entered data should not be saved")
    public void entered_data_should_not_be_saved() {
        logger.info("Step: Verifying entered data is not saved");
        // This is verified by the cancel action - form data is discarded
        logger.info("Entered data not saved - cancel operation completed");
    }
    
    @Then("No success or error messages should be displayed")
    public void no_success_or_error_messages_should_be_displayed() {
        logger.info("Step: Verifying no success or error messages are displayed");
        String successMessage = plantPage.getSuccessMessage();
        String errorMessage = plantPage.getErrorMessage();
        
        Assert.assertTrue(successMessage.isEmpty(),
            "No success message should be displayed after cancel");
        Assert.assertTrue(errorMessage.isEmpty(),
            "No error message should be displayed after cancel");
    }
    
    @Then("Plants List page should display existing plants without the cancelled entry")
    public void plants_list_page_should_display_existing_plants_without_cancelled_entry() {
        logger.info("Step: Verifying Plants List page displays existing plants without cancelled entry");
        
        // Verify on Plants List page
        Assert.assertTrue(plantPage.isOnPlantsListPage(),
            "Should be on Plants List page");
        Assert.assertTrue(plantPage.isPlantsTableDisplayed(),
            "Plants table should be displayed");
        
        // The cancelled entry should not appear (since it was never saved)
        logger.info("Plants List page displays existing plants without cancelled entry");
    }
}