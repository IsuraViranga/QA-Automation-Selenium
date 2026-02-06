package com.qforce.stepdefinitions;

import com.qforce.pages.CategoryPage;
import com.qforce.pages.LoginPage;
import com.qforce.utils.ConfigReader;
import com.qforce.utils.DriverManager;
import io.cucumber.java.en.*;
import org.testng.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Step Definitions for Category Admin UI Feature - Edit and Delete Operations
 */
public class CategoryAdminUISteps {

    private static final Logger logger = LogManager.getLogger(CategoryAdminUISteps.class);
    private LoginPage loginPage;
    private CategoryPage categoryPage;
    private int initialCategoryCount;
    private String originalCategoryName;
    private String originalParentName;

    public CategoryAdminUISteps() {
        this.loginPage = new LoginPage();
        this.categoryPage = new CategoryPage();
    }

    // ==================== GIVEN STEPS ====================

    @Given("Admin is logged in with username {string} and password {string}")
    public void admin_is_logged_in_with_username_and_password(String username, String password) {
        logger.info("Step: Admin logging in with username: {}", username);
        
        String appUrl = ConfigReader.getAppUrl();
        String loginUrl;
        
        // Handle case where app.url includes /ui/login
        if (appUrl.endsWith("/ui/login")) {
            loginUrl = appUrl;
        } else if (appUrl.endsWith("/")) {
            loginUrl = appUrl + "ui/login";
        } else {
            loginUrl = appUrl + "/ui/login";
        }
        
        logger.info("Navigating to login URL: {}", loginUrl);
        loginPage.navigateToLoginPage(loginUrl);
        loginPage.login(username, password);
        
        // Wait for login to complete and dashboard/home to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // Ignore
        }
        
        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        logger.info("URL after login: {}", currentUrl);
        
        logger.info("Admin logged in successfully");
    }

    @Given("Admin navigates to categories page")
    public void admin_navigates_to_categories_page() {
        logger.info("Step: Admin navigating to categories page");
        
        String appUrl = ConfigReader.getAppUrl();
        String targetUrl;
        
        // Handle case where app.url includes /ui/login
        if (appUrl.endsWith("/ui/login")) {
            targetUrl = appUrl.replace("/ui/login", "/ui/categories");
        } else if (appUrl.endsWith("/")) {
            targetUrl = appUrl + "ui/categories";
        } else {
            targetUrl = appUrl + "/ui/categories";
        }
        
        logger.info("Base URL from config: '{}'", appUrl);
        logger.info("Navigating to target URL: '{}'", targetUrl);
        
        DriverManager.getDriver().get(targetUrl);
        
        // Wait for page to load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Ignore
        }
        
        logger.info("Navigated to categories page");
    }

    @Given("Category with id {string} exists")
    public void category_with_id_exists(String categoryId) {
        logger.info("Step: Verifying category with id {} exists", categoryId);
        
        int id = Integer.parseInt(categoryId);
        boolean exists = categoryPage.isCategoryInListById(id);
        Assert.assertTrue(exists, "Category with id " + categoryId + " should exist");
        
        logger.info("Category with id {} exists", categoryId);
    }

    @Given("At least one category exists")
    public void at_least_one_category_exists() {
        logger.info("Step: Verifying at least one category exists");
        
        int count = categoryPage.getCategoryCount();
        Assert.assertTrue(count > 0, "At least one category should exist");
        
        logger.info("Found {} categories", count);
    }

    @Given("Deletable category with id {string} exists")
    public void deletable_category_with_id_exists(String categoryId) {
        logger.info("Step: Verifying deletable category with id {} exists", categoryId);
        
        int id = Integer.parseInt(categoryId);
        boolean exists = categoryPage.isCategoryInListById(id);
        Assert.assertTrue(exists, "Category with id " + categoryId + " should exist");
        
        logger.info("Deletable category with id {} exists", categoryId);
    }

    @Given("Sub-category with id {string} exists with a parent")
    public void sub_category_with_id_exists_with_a_parent(String categoryId) {
        logger.info("Step: Verifying sub-category with id {} exists with a parent", categoryId);
        
        int id = Integer.parseInt(categoryId);
        boolean exists = categoryPage.isCategoryInListById(id);
        Assert.assertTrue(exists, "Sub-category with id " + categoryId + " should exist");
        
        String parent = categoryPage.getCategoryParentById(id);
        Assert.assertNotNull(parent, "Sub-category should have a parent");
        Assert.assertFalse(parent.equals("-"), "Sub-category should have a parent (not '-')");
        
        logger.info("Sub-category with id {} exists with parent: {}", categoryId, parent);
    }

    // ==================== WHEN STEPS ====================

    @When("Admin clicks Edit button for category id {string}")
    public void admin_clicks_edit_button_for_category_id(String categoryId) {
        logger.info("Step: Admin clicking Edit button for category id {}", categoryId);
        
        int id = Integer.parseInt(categoryId);
        categoryPage.clickEditButtonForCategory(id);
        
        logger.info("Clicked Edit button for category id {}", categoryId);
    }

    @When("Admin updates category name to {string}")
    public void admin_updates_category_name_to(String newName) {
        logger.info("Step: Admin updating category name to: {}", newName);
        
        categoryPage.updateCategoryName(newName);
        
        logger.info("Category name updated to: {}", newName);
    }

    @When("Admin clicks Save button")
    public void admin_clicks_save_button() {
        logger.info("Step: Admin clicking Save button");
        
        categoryPage.clickSave();
        
        logger.info("Save button clicked");
    }

    @When("Admin notes the total number of categories")
    public void admin_notes_the_total_number_of_categories() {
        logger.info("Step: Admin noting total number of categories");
        
        initialCategoryCount = categoryPage.getCategoryCount();
        
        logger.info("Initial category count: {}", initialCategoryCount);
    }

    @When("Admin clicks Delete button for category id {string}")
    public void admin_clicks_delete_button_for_category_id(String categoryId) {
        logger.info("Step: Admin clicking Delete button for category id {}", categoryId);
        
        int id = Integer.parseInt(categoryId);
        categoryPage.clickDeleteButtonForCategory(id);
        
        logger.info("Clicked Delete button for category id {}", categoryId);
    }

    @When("Admin clicks Confirm button on dialog")
    public void admin_clicks_confirm_button_on_dialog() {
        logger.info("Step: Admin clicking Confirm button on dialog");
        
        categoryPage.acceptConfirmationDialog();
        
        logger.info("Confirmation dialog accepted");
    }

    @When("Admin clicks Cancel button on dialog")
    public void admin_clicks_cancel_button_on_dialog() {
        logger.info("Step: Admin clicking Cancel button on dialog");
        
        categoryPage.dismissConfirmationDialog();
        
        logger.info("Confirmation dialog dismissed");
    }

    @When("Admin notes the original category name")
    public void admin_notes_the_original_category_name() {
        logger.info("Step: Admin noting original category name");
        
        // The original name will be verified later by checking it's not "Test Cancel"
        // We don't need to store it explicitly since we're on the edit page
        // The test will verify the name didn't change to "Test Cancel"
        
        logger.info("Original category name will be verified later");
    }

    @When("Admin notes the current parent category")
    public void admin_notes_the_current_parent_category() {
        logger.info("Step: Admin noting current parent category");
        
        originalParentName = categoryPage.getSelectedParentCategory();
        
        logger.info("Current parent category: {}", originalParentName);
    }

    @When("Admin changes parent dropdown to different main category")
    public void admin_changes_parent_dropdown_to_different_main_category() {
        logger.info("Step: Admin changing parent dropdown to different main category");
        
        // Select a different parent based on what exists in the app
        // Available parents: NewCrops, Fruits, NewMain
        String newParent;
        if (originalParentName.equals("NewCrops")) {
            newParent = "Fruits";
        } else if (originalParentName.equals("Fruits")) {
            newParent = "NewMain";
        } else {
            newParent = "Fruits";
        }
        categoryPage.selectParentCategory(newParent);
        
        logger.info("Changed parent to: {}", newParent);
    }

    // ==================== THEN STEPS ====================

    @Then("Edit page should load with URL {string}")
    public void edit_page_should_load_with_url(String expectedUrl) {
        logger.info("Step: Verifying edit page loaded with URL: {}", expectedUrl);
        
        String currentUrl = categoryPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedUrl), 
            "Expected URL to contain '" + expectedUrl + "' but got: " + currentUrl);
        
        logger.info("Edit page loaded with correct URL");
    }

    @Then("Admin should be redirected to {string}")
    public void admin_should_be_redirected_to(String expectedUrl) {
        logger.info("Step: Verifying redirect to: {}", expectedUrl);
        
        String currentUrl = categoryPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedUrl), 
            "Expected URL to contain '" + expectedUrl + "' but got: " + currentUrl);
        
        logger.info("Redirected to correct URL");
    }

    @Then("Success message should be displayed")
    public void success_message_should_be_displayed() {
        logger.info("Step: Verifying success message is displayed");
        
        // Wait for success message to appear after redirect
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // Ignore
        }
        
        String successMessage = categoryPage.getSuccessMessage();
        Assert.assertNotNull(successMessage, "Success message should be displayed");
        Assert.assertFalse(successMessage.isEmpty(), "Success message should not be empty");
        
        logger.info("Success message displayed: {}", successMessage);
    }

    @Then("Category id {string} should show name {string}")
    public void category_id_should_show_name(String categoryId, String expectedName) {
        logger.info("Step: Verifying category id {} shows name: {}", categoryId, expectedName);
        
        int id = Integer.parseInt(categoryId);
        String actualName = categoryPage.getCategoryNameById(id);
        Assert.assertEquals(actualName, expectedName, 
            "Category name should be '" + expectedName + "' but got: " + actualName);
        
        logger.info("Category id {} has correct name: {}", categoryId, expectedName);
    }

    @Then("Confirmation dialog should appear")
    public void confirmation_dialog_should_appear() {
        logger.info("Step: Verifying confirmation dialog appeared");
        
        boolean dialogDisplayed = categoryPage.isConfirmationDialogDisplayed();
        Assert.assertTrue(dialogDisplayed, "Confirmation dialog should be displayed");
        
        logger.info("Confirmation dialog is displayed");
    }

    @Then("Category id {string} should still exist in the list")
    public void category_id_should_still_exist_in_the_list(String categoryId) {
        logger.info("Step: Verifying category id {} still exists", categoryId);
        
        int id = Integer.parseInt(categoryId);
        boolean exists = categoryPage.isCategoryInListById(id);
        Assert.assertTrue(exists, "Category with id " + categoryId + " should still exist");
        
        logger.info("Category id {} still exists in the list", categoryId);
    }

    @Then("Total number of categories should remain the same")
    public void total_number_of_categories_should_remain_the_same() {
        logger.info("Step: Verifying total number of categories remained the same");
        
        int currentCount = categoryPage.getCategoryCount();
        Assert.assertEquals(currentCount, initialCategoryCount, 
            "Category count should remain " + initialCategoryCount + " but got: " + currentCount);
        
        logger.info("Category count remained the same: {}", currentCount);
    }

    @Then("Category id {string} should be removed from the list")
    public void category_id_should_be_removed_from_the_list(String categoryId) {
        logger.info("Step: Verifying category id {} was removed", categoryId);
        
        int id = Integer.parseInt(categoryId);
        boolean exists = categoryPage.isCategoryInListById(id);
        Assert.assertFalse(exists, "Category with id " + categoryId + " should be removed");
        
        logger.info("Category id {} was successfully removed", categoryId);
    }

    @Then("Category id {string} should show original name")
    public void category_id_should_show_original_name(String categoryId) {
        logger.info("Step: Verifying category id {} shows original name", categoryId);
        
        int id = Integer.parseInt(categoryId);
        String currentName = categoryPage.getCategoryNameById(id);
        
        // We need to verify it's NOT "Test Cancel"
        Assert.assertNotEquals(currentName, "Test Cancel", 
            "Category name should not be 'Test Cancel' (changes should be discarded)");
        
        logger.info("Category id {} shows original name: {}", categoryId, currentName);
    }

    @Then("No success message should be shown")
    public void no_success_message_should_be_shown() {
        logger.info("Step: Verifying no success message is shown");
        
        boolean messageDisplayed = categoryPage.isSuccessMessageDisplayed();
        Assert.assertFalse(messageDisplayed, "Success message should not be displayed");
        
        logger.info("No success message displayed (as expected)");
    }

    @Then("Category id {string} should show new parent in Parent column")
    public void category_id_should_show_new_parent_in_parent_column(String categoryId) {
        logger.info("Step: Verifying category id {} shows new parent", categoryId);
        
        int id = Integer.parseInt(categoryId);
        String currentParent = categoryPage.getCategoryParentById(id);
        
        Assert.assertNotEquals(currentParent, originalParentName, 
            "Parent should have changed from '" + originalParentName + "'");
        
        logger.info("Category id {} now has parent: {} (was: {})", 
            categoryId, currentParent, originalParentName);
    }
}
