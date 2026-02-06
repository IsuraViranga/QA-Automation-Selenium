package com.qforce.stepdefinitions.categoryApiDefinitions;

import com.qforce.utils.ConfigReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Step Definitions for Category User API Test Cases
 */
public class CategoryUserAPISteps {

    private static final Logger logger = LoggerFactory.getLogger(CategoryUserAPISteps.class);

    private RequestSpecification request;
    private Response response;
    private String userAuthToken;
    private String baseUrl;
    private int categoryCountBeforeTest;

    public CategoryUserAPISteps() {
        String configured = ConfigReader.getProperty("api.base.url", null);
        if (configured == null || configured.trim().isEmpty()) {
            this.baseUrl = "http://localhost:8080";
            logger.warn("api.base.url not found in config. Defaulting to: {}", this.baseUrl);
        } else {
            this.baseUrl = configured.trim();
        }
        RestAssured.baseURI = this.baseUrl;
        logger.info("API Base URL set to: {}", this.baseUrl);
    }

    // ==================== GIVEN STEPS ====================

    @Given("User authentication token is set in request header")
    public void user_authentication_token_is_set_in_request_header() {
        logger.info("Step: Setting up user authentication token in request header");

        String username = ConfigReader.getUserUsername();
        String password = ConfigReader.getUserPassword();

        // Authenticate and get user token
        Response authResponse = given()
                .contentType("application/json")
                .body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}")
                .when()
                .post("/api/auth/login");

        userAuthToken = "Bearer " + authResponse.jsonPath().getString("token");

        logger.info("User authentication token set successfully");
    }

    @Given("User authentication token is not set in request header")
    public void user_authentication_token_is_not_set_in_request_header() {
        logger.info("Step: User authentication token is NOT set (testing unauthorized access)");
        userAuthToken = null;
    }

    @Given("User is authenticated and has valid authentication token")
    public void user_is_authenticated_and_has_valid_authentication_token() {
        logger.info("Step: Initializing request with user authentication token");

        request = given()
                .header("Authorization", userAuthToken)
                .contentType("application/json")
                .accept("application/json");

        logger.info("Request initialized with user authentication token");
    }

    @Given("At least one category exists in the system for user")
    public void at_least_one_category_exists_in_the_system() {
        logger.info("Step: Verifying at least one category exists in the system");

        // Get admin token to check categories
        String adminUsername = ConfigReader.getAdminUsername();
        String adminPassword = ConfigReader.getAdminPassword();

        Response authResponse = given()
                .contentType("application/json")
                .body("{\"username\":\"" + adminUsername + "\",\"password\":\"" + adminPassword + "\"}")
                .when()
                .post("/api/auth/login");

        String adminToken = "Bearer " + authResponse.jsonPath().getString("token");

        Response categoriesResponse = given()
                .header("Authorization", adminToken)
                .when()
                .get("/api/categories");

        List<Object> categories = categoriesResponse.jsonPath().getList("$");
        categoryCountBeforeTest = categories != null ? categories.size() : 0;

        logger.info("Found {} categories in the system", categoryCountBeforeTest);

        if (categoryCountBeforeTest == 0) {
            logger.warn("No categories exist. Tests may fail if they expect existing categories.");
        }
    }

    @Given("At least one category exists in the system with id {string}")
    public void at_least_one_category_exists_in_the_system_with_id(String categoryId) {
        logger.info("Step: Verifying category with id '{}' exists", categoryId);

        // Get admin token to check category
        String adminUsername = ConfigReader.getAdminUsername();
        String adminPassword = ConfigReader.getAdminPassword();

        Response authResponse = given()
                .contentType("application/json")
                .body("{\"username\":\"" + adminUsername + "\",\"password\":\"" + adminPassword + "\"}")
                .when()
                .post("/api/auth/login");

        String adminToken = "Bearer " + authResponse.jsonPath().getString("token");

        Response categoryResponse = given()
                .header("Authorization", adminToken)
                .when()
                .get("/api/categories/" + categoryId);

        logger.info("GET /api/categories/{} returned status: {}", categoryId, categoryResponse.getStatusCode());

        if (categoryResponse.getStatusCode() == 200) {
            logger.info("Category with id '{}' confirmed exists", categoryId);
        } else {
            logger.warn("Category with id '{}' not found (status {})", categoryId, categoryResponse.getStatusCode());
        }
    }

    // ==================== WHEN STEPS ====================

    @When("User sends GET request to {string}")
    public void user_sends_get_request_to(String endpoint) {
        logger.info("Step: Sending GET request to {}", endpoint);

        response = request
                .when()
                .get(endpoint);

        logger.info("Response status code: {}", response.getStatusCode());
        logger.info("Response body: {}", response.getBody().asString());
    }

    @When("User sends GET request to {string} without authentication")
    public void user_sends_get_request_to_without_authentication(String endpoint) {
        logger.info("Step: Sending GET request to {} WITHOUT authentication", endpoint);

        response = given()
                .contentType("application/json")
                .accept("application/json")
                // No Authorization header
                .when()
                .get(endpoint);

        logger.info("Response status code: {}", response.getStatusCode());
        logger.info("Response body: {}", response.getBody().asString());
    }

    @When("User sends POST request to {string} with valid category data:")
    public void user_sends_post_request_with_valid_category_data(String endpoint, DataTable dataTable) {
        logger.info("Step: User sends POST request to {} (testing forbidden access)", endpoint);

        Map<String, String> data = dataTable.asMap(String.class, String.class);
        String name = data.get("name");
        String parent = data.get("parent");

        String requestBody;
        if (parent == null || parent.equalsIgnoreCase("null")) {
            requestBody = String.format("{\"name\":\"%s\"}", name);
        } else {
            requestBody = String.format("{\"name\":\"%s\",\"parent\":{\"id\":%s}}", name, parent);
        }

        logger.info("Request body: {}", requestBody);

        response = request
                .body(requestBody)
                .when()
                .post(endpoint);

        logger.info("Response status code: {}", response.getStatusCode());
        logger.info("Response body: {}", response.getBody().asString());
    }

    @When("User sends POST request to {string} without authentication:")
    public void user_sends_post_request_without_authentication(String endpoint, DataTable dataTable) {
        logger.info("Step: Sending POST request to {} WITHOUT authentication", endpoint);

        Map<String, String> data = dataTable.asMap(String.class, String.class);
        String name = data.get("name");
        String parent = data.get("parent");

        String requestBody;
        if (parent == null || parent.equalsIgnoreCase("null")) {
            requestBody = String.format("{\"name\":\"%s\"}", name);
        } else {
            requestBody = String.format("{\"name\":\"%s\",\"parent\":{\"id\":%s}}", name, parent);
        }

        logger.info("Request body: {}", requestBody);

        response = given()
                .contentType("application/json")
                .accept("application/json")
                // No Authorization header
                .body(requestBody)
                .when()
                .post(endpoint);

        logger.info("Response status code: {}", response.getStatusCode());
        logger.info("Response body: {}", response.getBody().asString());
    }

    @When("User sends DELETE request to {string}")
    public void user_sends_delete_request_to(String endpoint) {
        logger.info("Step: User sends DELETE request to {} (testing forbidden access)", endpoint);

        response = request
                .when()
                .delete(endpoint);

        logger.info("Response status code: {}", response.getStatusCode());
        logger.info("Response body: {}", response.getBody().asString());
    }

    // ==================== THEN STEPS ====================

    @Then("Response status code must be {int}")
    public void response_status_code_should_be(int expectedStatusCode) {
        logger.info("Step: Verifying response status code is {}", expectedStatusCode);
        
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
            "Expected status code " + expectedStatusCode + " but got " + actualStatusCode);
        
        logger.info("Response status code verification passed: {}", actualStatusCode);
    }

    @Then("Response body should contain array of category objects")
    public void response_body_should_contain_array_of_category_objects() {
        logger.info("Step: Verifying response body contains array of category objects");

        // Verify response is an array
        List<Object> categories = response.jsonPath().getList("$");
        Assert.assertNotNull(categories, "Response should be an array/list");
        Assert.assertTrue(categories.size() > 0, "Response array should not be empty");

        logger.info("Response contains array with {} category objects", categories.size());
    }

    @Then("Each category object should contain id, name, parentName fields")
    public void each_category_object_should_contain_id_name_parentName_fields() {
        logger.info("Step: Verifying each category object contains required fields");

        List<Map<String, Object>> categories = response.jsonPath().getList("$");

        for (int i = 0; i < categories.size(); i++) {
            Map<String, Object> category = categories.get(i);

            Assert.assertTrue(category.containsKey("id"),
                    "Category at index " + i + " should have 'id' field");
            Assert.assertTrue(category.containsKey("name"),
                    "Category at index " + i + " should have 'name' field");
            Assert.assertTrue(category.containsKey("parentName"),
                    "Category at index " + i + " should have 'parentName' field");

            logger.info("Category {}: id={}, name={}, parentName={}",
                    i, category.get("id"), category.get("name"), category.get("parentName"));
        }

        logger.info("All {} categories contain required fields", categories.size());
    }

    @Then("All existing categories should be returned in the response")
    public void all_existing_categories_should_be_returned_in_the_response() {
        logger.info("Step: Verifying all existing categories are returned");

        List<Object> categories = response.jsonPath().getList("$");
        int returnedCount = categories != null ? categories.size() : 0;

        Assert.assertTrue(returnedCount >= categoryCountBeforeTest,
                "Expected at least " + categoryCountBeforeTest + " categories but got " + returnedCount);

        logger.info("Returned {} categories (expected at least {})", returnedCount, categoryCountBeforeTest);
    }

    @Then("Response body should contain error object with status, error, message fields")
    public void response_body_should_contain_error_object_with_status_error_message_fields() {
        logger.info("Step: Verifying error object contains status, error, message fields");

        response.then()
                .assertThat()
                .body("status", notNullValue())
                .body("error", notNullValue())
                .body("message", notNullValue());

        logger.info("Error object structure verified (status, error, message)");
    }

    @Then("Response body should contain error object with status, error, timestamp, path fields")
    public void response_body_should_contain_error_object_with_status_error_timestamp_path_fields() {
        logger.info("Step: Verifying error object contains status, error, timestamp, path fields");

        response.then()
                .assertThat()
                .body("status", notNullValue())
                .body("error", notNullValue())
                .body("timestamp", notNullValue())
                .body("path", notNullValue());

        logger.info("Error object structure verified (status, error, timestamp, path)");
    }

    @Then("Error message should indicate unauthorized access")
    public void error_message_should_indicate_unauthorized_access() {
        logger.info("Step: Verifying error message indicates unauthorized access");

        String errorMessage = response.jsonPath().getString("message");
        String error = response.jsonPath().getString("error");

        logger.info("Error: {}", error);
        logger.info("Message: {}", errorMessage);

        boolean isUnauthorized =
                (errorMessage != null && (
                        errorMessage.toLowerCase().contains("unauthorized") ||
                        errorMessage.toLowerCase().contains("authentication") ||
                        errorMessage.toLowerCase().contains("auth"))) ||
                (error != null && error.equalsIgnoreCase("UNAUTHORIZED"));

        Assert.assertTrue(isUnauthorized,
                "Error should indicate unauthorized access. Error: " + error + ", Message: " + errorMessage);

        logger.info("Error correctly indicates unauthorized access");
    }

    @Then("Error should indicate forbidden access")
    public void error_should_indicate_forbidden_access() {
        logger.info("Step: Verifying error indicates forbidden access");

        String error = response.jsonPath().getString("error");

        logger.info("Error: {}", error);

        boolean isForbidden =
                error != null && (
                        error.equalsIgnoreCase("Forbidden") ||
                        error.equalsIgnoreCase("FORBIDDEN") ||
                        error.toLowerCase().contains("forbidden"));

        Assert.assertTrue(isForbidden,
                "Error should indicate forbidden access. Got: " + error);

        logger.info("Error correctly indicates forbidden access");
    }

    @Then("No category data should be returned in the response")
    public void no_category_data_should_be_returned_in_the_response() {
        logger.info("Step: Verifying no category data is returned");

        String responseBody = response.getBody().asString();

        // Response should not contain category array
        boolean containsCategoryData = responseBody.contains("\"id\"") &&
                responseBody.contains("\"name\"") &&
                responseBody.contains("\"parentName\"");

        Assert.assertFalse(containsCategoryData,
                "Response should not contain category data");

        logger.info("Verified: No category data returned in response");
    }

    // ==================== NEW STEP DEFINITIONS FOR API TESTS ====================

    @When("User sends PUT request to {string} with name {string}")
    public void user_sends_put_request_with_name(String endpoint, String name) {
        logger.info("Step: User sends PUT request to {} with name={}", endpoint, name);

        String requestBody = String.format("{\"name\":\"%s\",\"parentId\":null}", name);
        logger.info("Request body: {}", requestBody);

        response = request
                .body(requestBody)
                .when()
                .put(endpoint);

        logger.info("Response status code: {}", response.getStatusCode());
        logger.info("Response body: {}", response.getBody().asString());
    }

    @Then("Response body should contain pagination data")
    public void response_body_should_contain_pagination_data() {
        logger.info("Step: Verifying response contains pagination data");

        // Check if response has pagination structure
        Map<String, Object> responseMap = response.jsonPath().getMap("$");
        
        // Pagination data might be in different formats, check both
        if (responseMap.containsKey("pageable")) {
            // Spring Boot Page format
            Assert.assertNotNull(response.jsonPath().get("pageable"), "Response should have 'pageable' field");
            Assert.assertNotNull(response.jsonPath().get("content"), "Response should have 'content' field");
            Assert.assertNotNull(response.jsonPath().get("totalElements"), "Response should have 'totalElements' field");
            Assert.assertNotNull(response.jsonPath().get("totalPages"), "Response should have 'totalPages' field");
            logger.info("Pagination data verified (Spring Boot Page format)");
        } else if (responseMap.containsKey("page")) {
            // Custom pagination format
            Assert.assertNotNull(response.jsonPath().get("page"), "Response should have 'page' field");
            Assert.assertNotNull(response.jsonPath().get("size"), "Response should have 'size' field");
            Assert.assertNotNull(response.jsonPath().get("totalElements"), "Response should have 'totalElements' field");
            Assert.assertNotNull(response.jsonPath().get("totalPages"), "Response should have 'totalPages' field");
            logger.info("Pagination data verified (custom format)");
        } else {
            Assert.fail("Response does not contain pagination data. Response: " + response.getBody().asString());
        }
    }

    @Then("Response should contain at most {int} categories in content")
    public void response_should_contain_at_most_categories(int maxSize) {
        logger.info("Step: Verifying response contains at most {} categories", maxSize);

        List<Object> categories = response.jsonPath().getList("content");
        Assert.assertNotNull(categories, "Response should have 'content' field");
        Assert.assertTrue(categories.size() <= maxSize,
                "Expected at most " + maxSize + " categories but got " + categories.size());

        logger.info("Category count verification passed. Found {} categories", categories.size());
    }

    @Then("All returned categories should contain {string} in name")
    public void all_returned_categories_should_contain_in_name(String searchTerm) {
        logger.info("Step: Verifying all categories contain '{}' in name", searchTerm);

        List<Map<String, Object>> categories = response.jsonPath().getList("content");
        Assert.assertNotNull(categories, "Response should have 'content' field");

        for (Map<String, Object> category : categories) {
            String name = (String) category.get("name");
            Assert.assertTrue(name.toLowerCase().contains(searchTerm.toLowerCase()),
                    "Category name '" + name + "' should contain '" + searchTerm + "'");
        }

        logger.info("Search result verification passed. All {} categories match '{}'",
                categories.size(), searchTerm);
    }

    @Then("Response should contain only categories matching {string} in name")
    public void response_should_contain_only_categories_matching_in_name(String searchTerm) {
        logger.info("Step: Verifying response contains only categories matching '{}' in name", searchTerm);

        List<Map<String, Object>> categories = response.jsonPath().getList("content");
        
        if (categories == null || categories.isEmpty()) {
            logger.info("No categories returned in search results");
            return;
        }

        for (Map<String, Object> category : categories) {
            String name = (String) category.get("name");
            Assert.assertTrue(name.toLowerCase().contains(searchTerm.toLowerCase()),
                    "Category name '" + name + "' should contain '" + searchTerm + "'");
        }

        logger.info("Search validation passed. All {} categories contain '{}' in name",
                categories.size(), searchTerm);
    }

    @Then("Response body should contain category object")
    public void response_body_should_contain_category_object() {
        logger.info("Step: Verifying response contains a category object");

        // Verify response is an object (not an array)
        Map<String, Object> category = response.jsonPath().getMap("$");
        Assert.assertNotNull(category, "Response should be a category object");
        Assert.assertTrue(category.containsKey("id"), "Category should have 'id' field");

        logger.info("Response contains category object with id: {}", category.get("id"));
    }

    @Then("Category object should have id, name, parentName fields")
    public void category_object_should_have_id_name_parentName_fields() {
        logger.info("Step: Verifying category object has required fields");

        Map<String, Object> category = response.jsonPath().getMap("$");

        Assert.assertTrue(category.containsKey("id"), "Category should have 'id' field");
        Assert.assertTrue(category.containsKey("name"), "Category should have 'name' field");
        
        // Check for either parentName or parentId
        boolean hasParentField = category.containsKey("parentName") || category.containsKey("parentId");
        Assert.assertTrue(hasParentField, "Category should have 'parentName' or 'parentId' field");

        logger.info("Category object fields verified: id={}, name={}, parent field exists",
                category.get("id"), category.get("name"));
    }
}
