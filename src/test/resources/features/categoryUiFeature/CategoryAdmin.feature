Feature: Category Admin Management
  As an admin user
  I want to manage categories in the system
  So that I can organize products/items properly

  Background:
    Given Admin is logged in and navigated to Add Category page

  @TC_CAT_ADMIN_01 @Positive @Smoke
  Scenario: Verify admin can successfully add a new main category with a valid name
    When Admin enters a valid category name "Electronic"
    And Admin leaves Parent Category dropdown as Main Category
    And Admin clicks the Save button
    Then System should successfully create the category
    And Admin should be redirected to Category List page with URL "/ui/categories"
    And Success message "Category created successfully" should be displayed on the Category List page
    And Newly added category "Electronic" should appear in the category list

  @TC_CAT_ADMIN_03 @Negative @Validation
  Scenario: Verify admin cannot add category with empty category name field
    When Admin leaves Category Name field empty
    And Admin clicks the Save button
    Then Category should not be saved
    And Validation message "Category name is required" should be displayed in red color below the Category Name field
    And Admin should remain on Add Category page

  @TC_CAT_ADMIN_04 @Positive @SubCategory
  Scenario: Verify admin can successfully add a new sub-category with valid inputs
    Given At least one parent Category "Electronic" exists in the system
    When Admin enters a valid category name "smallcat"
    And Admin selects an existing parent category "Electronic" from Parent Category dropdown
    And Admin clicks the Save button
    Then System should validate and accept the parent category from Parent Category dropdown
    And New sub-category should be created successfully
    And Admin should be redirected to Category List page with URL "/ui/categories"
    And System should display the success message "Category created successfully"
    And System should validate navigation to category list page
    And Newly added sub-category "smallcat" should appear in the category list
    And Correct parent category name "Electronic" should be displayed in the Parent column

  @TC_CAT_ADMIN_05 @Positive @Cancel
  Scenario: Verify admin is redirected to category list page when clicking Cancel button on Add Category page
    When Admin enters any data in Category Name field "TestCategory"
    And Admin selects any option from Parent Category dropdown if applicable
    And Admin clicks Cancel button
    Then Admin should be redirected to "/ui/categories" page
    And Category list page should be displayed
    And No new category should be added to the system
    And Any data entered in the form should be discarded

  @TC_CAT_ADMIN_02 @Negative @Validation @DataDriven
  Scenario Outline: Verify category name validation with different invalid inputs
    When Admin enters category name "<categoryName>"
    And Admin clicks the Save button
    Then Category should not be saved
    And Error message "<errorMessage>" should be displayed
    And Admin should remain on Add Category page

    Examples:
      | categoryName | errorMessage                                         |
      | AB           | Category name must be between 3 and 10 characters   |
      | 12345678901  | Category name must be between 3 and 10 characters   |
