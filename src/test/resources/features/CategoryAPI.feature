@CategoryAPI
Feature: Category API Testing for User Role

  Background:
    Given User authentication token is set in request header
    And User is authenticated and has valid authentication token

  @TC_CAT_USER_API_01 @Positive @API
  Scenario: Verify User can retrieve all categories via GET API
    Given At least one category exists in the system for user
    When User sends GET request to "/api/categories"
    Then Response status code must be 200
    And Response body should contain array of category objects
    And Each category object should contain id, name, parentName fields

  @TC_CAT_USER_API_02 @Positive @API @Pagination
  Scenario: Verify User can search categories with pagination via GET API
    Given At least one category exists in the system for user
    When User sends GET request to "/api/categories/page?page=0&size=5"
    Then Response status code must be 200
    And Response body should contain pagination data
    And Response should contain at most 5 categories in content

  @TC_CAT_USER_API_03 @Negative @API @Authorization
  Scenario: Verify User cannot update category via PUT API
    Given At least one category exists in the system with id "1"
    When User sends PUT request to "/api/categories/1" with name "UpdatedName"
    Then Response status code must be 403
    And Error should indicate forbidden access

  @TC_CAT_USER_API_04 @Positive @API @Search
  Scenario: Verify User can search categories by name via GET API
    Given At least one category exists in the system for user
    When User sends GET request to "/api/categories/page?name=Flower"
    Then Response status code must be 200
    And Response body should contain pagination data
    And Response should contain only categories matching "Flower" in name

  @TC_CAT_USER_API_05 @Positive @API
  Scenario: Verify User can retrieve specific category by ID via GET API
    Given At least one category exists in the system with id "2"
    When User sends GET request to "/api/categories/2"
    Then Response status code must be 200
    And Response body should contain category object
    And Category object should have id, name, parentName fields
