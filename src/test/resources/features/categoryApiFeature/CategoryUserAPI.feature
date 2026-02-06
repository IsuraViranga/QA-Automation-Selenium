Feature: Category User API Management
  As a user
  I want to interact with categories via REST API
  So that I can retrieve category data but not modify it

  @TC_API_CAT_USER_01 @API @Positive @GET
  Scenario: Verify that user can successfully retrieve all categories via GET /api/categories
    Given User authentication token is set in request header
    And User is authenticated and has valid authentication token
    And At least one category exists in the system for user
    When User sends GET request to "/api/categories"
    Then Response status code must be 200
    And Response body should contain array of category objects
    And Each category object should contain id, name, parentName fields
    And All existing categories should be returned in the response

  @TC_API_CAT_USER_02 @API @Negative @Authentication
  Scenario: Verify that unauthorized user cannot retrieve categories via GET /api/categories without authentication token
    Given User authentication token is not set in request header
    When User sends GET request to "/api/categories" without authentication
    Then Response status code must be 401
    And Response body should contain error object with status, error, message fields
    And Error message should indicate unauthorized access
    And No category data should be returned in the response

  @TC_API_CAT_USER_03 @API @Negative @Authorization
  Scenario: Verify that user cannot create a category via POST /api/categories due to insufficient permissions
    Given User authentication token is set in request header
    And User is authenticated and has valid authentication token
    When User sends POST request to "/api/categories" with valid category data:
      | name   | FlowerUser |
      | parent | null       |
    Then Response status code must be 403
    And Response body should contain error object with status, error, timestamp, path fields
    And Error should indicate forbidden access

  @TC_API_CAT_USER_04 @API @Negative @Authentication
  Scenario: Verify that unauthorized user cannot create a category via POST /api/categories without authentication token
    Given User authentication token is not set in request header
    When User sends POST request to "/api/categories" without authentication:
      | name   | Flowers |
      | parent | null    |
    Then Response status code must be 401
    And Response body should contain error object with status, error, message fields
    And Error message should indicate unauthorized access

  @TC_API_CAT_USER_05 @API @Negative @Authorization
  Scenario: Verify that user cannot delete a category via DELETE /api/categories/{id} due to insufficient permissions
    Given User authentication token is set in request header
    And User is authenticated and has valid authentication token
    And At least one category exists in the system with id "1"
    When User sends DELETE request to "/api/categories/1"
    Then Response status code must be 403
    And Response body should contain error object with status, error, timestamp, path fields
    And Error should indicate forbidden access
