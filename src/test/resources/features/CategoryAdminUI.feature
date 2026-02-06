@CategoryAdminUI
Feature: Category Admin UI Testing - Edit and Delete Operations

  Background:
    Given Admin is logged in with username "admin" and password "admin123"
    And Admin navigates to categories page

  @TC_CAT_ADMIN_UI_01 @Positive @Edit
  Scenario: Verify Admin can successfully edit an existing category
    Given Category with id "1" exists
    When Admin clicks Edit button for category id "1"
    Then Edit page should load with URL "/ui/categories/edit/1"
    When Admin updates category name to "NewCrops"
    And Admin clicks Save button
    Then Admin should be redirected to "/ui/categories"
    And Category id "1" should show name "NewCrops"

  @TC_CAT_ADMIN_UI_02 @Negative @Delete
  Scenario: Verify Admin can cancel delete operation via confirmation dialog
    Given At least one category exists
    When Admin notes the total number of categories
    And Admin clicks Delete button for category id "2"
    Then Confirmation dialog should appear
    When Admin clicks Cancel button on dialog
    Then Category id "2" should still exist in the list
    And Total number of categories should remain the same

  @TC_CAT_ADMIN_UI_03 @Positive @Delete
  Scenario: Verify Admin can delete a category successfully
    Given Deletable category with id "4" exists
    When Admin clicks Delete button for category id "4"
    Then Confirmation dialog should appear
    When Admin clicks Confirm button on dialog
    Then Admin should be redirected to "/ui/categories"
    And Category id "4" should be removed from the list

  @TC_CAT_ADMIN_UI_04 @Negative @Edit
  Scenario: Verify Admin can cancel category edit without saving changes
    Given Category with id "2" exists
    When Admin clicks Edit button for category id "2"
    And Admin notes the original category name
    And Admin updates category name to "Test Cancel"
    And Admin clicks Cancel button
    Then Admin should be redirected to "/ui/categories"
    And Category id "2" should show original name
    And No success message should be shown

  @TC_CAT_ADMIN_UI_05 @Positive @Edit @Parent
  Scenario: Verify Admin can edit category and change parent category
    Given Sub-category with id "7" exists with a parent
    When Admin clicks Edit button for category id "7"
    And Admin notes the current parent category
    And Admin changes parent dropdown to different main category
    And Admin clicks Save button
    Then Admin should be redirected to "/ui/categories"
    And Category id "7" should show new parent in Parent column
