@PlantAdmin @UI
Feature: Plant Admin Management
  As an admin user
  I want to manage plants in the system
  So that I can add, view, filter, and manage plant inventory

  Background:
    Given Admin user is logged in with username "admin" and password "admin123"

  @TC_PLT_ADMIN_01 @AddPlant @Positive
  Scenario: Verify that admin can successfully add a new plant with all valid details
    Given Admin is logged in and navigated to Add Plant page "/ui/plants/add"
    When Admin enters a valid plant name "Rose Red Test 2026" in the Plant Name field
    And Admin selects a valid sub-category "Flowers" from the Category dropdown
    And Admin enters a valid price "250.00" in the Price field
    And Admin enters a valid quantity "50" in the Quantity field
    And Admin clicks the Save button for plant
    Then All valid inputs should be accepted in their respective fields
    And System should successfully create a new plant record
    And User should be redirected to Plants List page "/ui/plants"
    And Success message "Plant has been added successfully." should be displayed
    And Newly added plant should appear in the plants table with correct Name, Category, Price, and Stock values

  @TC_PLT_ADMIN_02 @AddPlant @Negative @Validation
  Scenario: Verify that admin cannot add a plant without selecting a category
    Given Admin is logged in and navigated to Add Plant page "/ui/plants/add"
    When Admin enters a valid plant name "Orchid White" in the Plant Name field
    And Admin leaves the Category dropdown at default "-- Select Sub Category --"
    And Admin enters a valid price "500.00" in the Price field
    And Admin enters a valid quantity "30" in the Quantity field
    And Admin clicks the Save button for plant
    Then System should prevent form submission
    And Validation error message should be displayed near Category field: "Category is required"
    And Plant should not be created in the system
    And User should remain on the Add Plant page
    And All entered data should be retained in the form fields

  @TC_PLT_ADMIN_03 @ViewPlants @Positive
  Scenario: Verify that admin can view all plants in the plants list page
    Given Admin is logged in and at least one plant exists in the system
    When Admin navigates to Plants List page "/ui/plants"
    And Admin observes the page layout and table structure
    And Admin verifies the presence of search functionality
    And Admin verifies the presence of category filter dropdown
    And Admin verifies the presence of "Add a Plant" button
    Then Plants List page should load successfully
    And Page should display heading "Plants"
    And Search bar with placeholder "Search plant" should be visible
    And Category filter dropdown with "All Categories" default should be visible
    And "Search" and "Reset" buttons should be visible
    And "Add a Plant" button should be visible in the top-right
    And Table should display with columns "Name, Category, Price, Stock, Actions"
    And All existing plants should be listed in the table with correct data
    And If no plants exist "No plants found" message should be displayed

  @TC_PLT_ADMIN_04 @FilterPlants @Positive
  Scenario: Verify that admin can filter plants by category
    Given Admin is logged in, navigated to Plants List page "/ui/plants", and multiple plants exist in different categories
    When Admin verifies the Category dropdown displays "All Categories" by default
    And Admin clicks on the Category dropdown
    And Admin selects a specific category "Flowers"
    And Admin clicks the Search button
    And Admin observes the filtered results in the table
    Then Category dropdown should open and display all available categories
    And Selected category should be displayed in the dropdown
    And After clicking Search, only plants belonging to the selected category should be displayed in the table
    And Table should update without page refresh
    And Other plants from different categories should not be visible
    And "No plants found" message should appear if no plants exist in the selected category

  @TC_PLT_ADMIN_05 @AddPlant @Cancel @Positive
  Scenario: Verify that admin can cancel adding a plant and return to plants list
    Given Admin is logged in and navigated to Add Plant page "/ui/plants/add"
    When Admin enters a valid plant name "Tulip Yellow" in the Plant Name field
    And Admin selects a category "Ornamental" from the Category dropdown
    And Admin enters a valid price "150.00" in the Price field
    And Admin enters a valid quantity "40" in the Quantity field
    And Admin clicks the Cancel button for plant
    Then User should be immediately redirected to Plants List page "/ui/plants"
    And No plant record should be created in the system
    And Entered data should not be saved
    And No success or error messages should be displayed
    And Plants List page should display existing plants without the cancelled entry