Feature: Category Admin API Management
  As an admin
  I want to manage categories via REST API
  So that I can perform CRUD operations programmatically

  Background:
    Given Admin authentication token is set in request header

  @TC_API_CAT_ADMIN_01 @API @Positive @Smoke
  Scenario: Verify that admin can successfully create a main category with valid data via POST /api/categories
    Given Admin is authenticated and has valid authentication token
    When Admin sends POST request to "/api/categories" with valid category data:
      | name    | Flower |
      | parent  | null    |
    Then Response status code should be 201
    And Response body should contain created category data
    And Response body field "name" should match "Flower"
    And Response body should contain fields "id", "name", "subCategories"

  @TC_API_CAT_ADMIN_02 @API @Positive
  Scenario: Verify that admin can successfully create a sub-category with valid parent via POST /api/categories
    Given Admin is authenticated and has valid authentication token
    And At least one parent category exists in the system
    When Admin sends POST request to "/api/categories" with valid sub-category data:
      | name     | childCat |
    Then Response status code should be 201
    And Response body should contain created category data
    And Response body field "name" should match "childCat"

  @TC_API_CAT_ADMIN_03 @API @Negative @Validation
  Scenario: Verify that admin cannot create category with invalid name length via POST /api/categories
    Given Admin is authenticated and has valid authentication token
    When Admin sends POST request to "/api/categories" with invalid name length:
      | name    | awplantttttttttttttttttttttttttttttt |
      | parentId| 1                                     |
    Then Response status code should be 400
    And Response body should contain error object with status, error, message, and timestamp fields
    And Error message should indicate name length validation failed

  @TC_API_CAT_ADMIN_04 @API @Negative @Validation
  Scenario: Verify that admin cannot create category with empty/missing name via POST /api/categories
    Given Admin is authenticated and has valid authentication token
    When Admin sends POST request to "/api/categories" with empty name:
      | name    |    |
      | parentId| 1  |
    Then Response status code should be 400
    And Response body should contain error object with status, error, message, and timestamp fields
    And Error message should indicate name is required

  @TC_API_CAT_ADMIN_05 @API @Negative @DuplicateCheck
  Scenario: Verify that admin cannot create duplicate category with same name under same parent via POST /api/categories
    Given Admin is authenticated and has valid authentication token
    When Admin sends POST request to "/api/categories" with duplicate category data:
      | name    | awplanti |
      | parentId| 1       |
    Then Response status code should be 400
    And Response body should contain error object with status, error, message, and timestamp fields
    And Error message should indicate duplicate category already exists
