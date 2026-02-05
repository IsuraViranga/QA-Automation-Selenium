Feature: Category User Management
  As a user
  I want to view and navigate categories
  So that I can browse products by category

  Background:
    Given User is logged in and on Dashboard

  @TC_CAT_USER_01 @Positive @Navigation
  Scenario: Verify that user can navigate to category page and category tab is highlighted
    When User clicks on Categories tab from navigation menu
    Then System should navigate to the category page
    And Category page should be loaded
    And Categories tab should be highlighted in the navigation menu to indicate the active page

  @TC_CAT_USER_02 @Negative @EmptyState
  Scenario: Verify that "No category found" message is displayed when no categories exist
    Given No categories exist in the system
    When User navigates to Categories page
    Then Category page should be loaded
    And Category table should be displayed with headers "ID, Name, Parent, Actions"
    And Message "No category found" should be displayed
    And No category records should be shown in the table

  @TC_CAT_USER_03 @Positive @Display
  Scenario: Verify that category list is displayed with all columns when categories exist
    Given At least one category exists in the system
    When User navigates to Categories page
    Then Category list table should be displayed
    And Table should contain all required columns "ID, Name, Parent, Actions"
    And At least one category row should be displayed without showing empty state message
    And Each category row should display "ID, Name, Parent (or blank if main category), and Actions" columns with data

  @TC_CAT_USER_04 @Positive @Pagination
  Scenario: Verify that pagination is displayed when categories exceed 10 rows
    Given More than 10 categories exist in the system
    When User navigates to Categories page
    Then Category list should display first 10 records
    And No more than 10 records should be displayed on the initial page
    And Pagination controls should be visible at the bottom of the table
    And Pagination should show "Previous" page numbers "1, 2, ..." and "Next" buttons
    And Current page number should be highlighted

  @TC_CAT_USER_05 @Positive @Sorting
  Scenario: Verify that sorting indicators are displayed for ID, Name, and Parent columns
    Given Category list table is displayed
    When User observes the category list table
    Then ID column header should display sorting indicator
    And Name column header should display sorting indicator
    And Parent column header should display sorting indicator
