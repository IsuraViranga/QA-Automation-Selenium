Feature: Plant User Management
  As a user
  I want to view and search plants
  So that I can browse available plants and their details

  Background:
    Given User is logged in and on Dashboard

  @TC_PLT_USER_01 @Positive @Navigation
  Scenario: Verify that user can view all plants in the plants list page
    When User navigates to Plants List page
    Then Plants List page should load successfully
    And Page should display heading "Plants"
    And Search bar with placeholder "Search plant" should be visible
    And Category filter dropdown with "All Categories" default should be visible
    And "Search" and "Reset" buttons should be visible
    And "Add a Plant" button should NOT be visible for user
    And Table should display with columns "Name, Category, Price, Stock, Actions"
    And All existing plants should be listed in the table with correct data
    And Actions column should be empty for user
    And If no plants exist "No plants found" message should be displayed

  @TC_PLT_USER_02 @Positive @Search
  Scenario: Verify that user can search for plants by name
    Given User is on Plants List page and multiple plants exist in the system
    When User enters a plant name "Rose" in the Search plant field
    And User clicks the Search button
    Then Only plants matching the search criteria should be displayed
    And Table should filter without full page refresh
    And Matching plants should show complete details "Name, Category, Price, Stock"
    When User clicks the Reset button
    Then All plants should be displayed again
    And Search field should be cleared after reset

  @TC_PLT_USER_03 @Positive @Filter
  Scenario: Verify that user can filter plants by category
    Given User is on Plants List page and multiple plants exist in different categories
    When User verifies Category dropdown displays "All Categories" by default
    And User clicks on the Category dropdown
    And User selects a specific category "Flowers"
    And User clicks the Search button
    Then Only plants belonging to the selected category should be displayed in the table
    And Table should update without page refresh
    And Other plants from different categories should not be visible
    And User can still perform search by name within filtered category results

  @TC_PLT_USER_04 @Positive @Reset
  Scenario: Verify that user can reset filters to view all plants
    Given User is on Plants List page and has applied search or category filters
    When User enters a search term "Cactus" in the Search plant field
    And User selects a specific category "Ornamental" from the dropdown
    And User clicks the Search button
    And User observes the filtered results
    And User clicks the Reset button
    Then All filters should be cleared
    And Search field should be empty
    And Category dropdown should return to "All Categories"
    And All plants in the system should be displayed in the table
    And Table should display full list without requiring additional page reload

  @TC_PLT_USER_05 @Negative @EmptyState
  Scenario: Verify that user sees appropriate message when no plants match search criteria
    Given User is on Plants List page
    When User enters a search term that doesn't match any existing plants "XYZ123NonExistent"
    And User clicks the Search button
    Then Table should display no plant rows
    And "No plants found" message should be centered in the table area
    And Table headers "Name, Category, Price, Stock, Actions" should remain visible
    And Search and Reset buttons should remain functional
    When User clicks the Reset button
    Then All plants should be displayed again
    When User enters a search term that doesn't match any existing plants "NonExistentCategory123"
    And User clicks the Search button
    Then "No plants found" message should appear
    And No error messages or console errors should occur
    And User can continue to use search and filter functionality