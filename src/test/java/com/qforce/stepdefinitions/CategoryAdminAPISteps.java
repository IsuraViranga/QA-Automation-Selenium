package com.qforce.stepdefinitions;

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
 * Step Definitions for Category Admin API Test Cases
 */
public class CategoryAdminAPISteps {
    
    private static final Logger logger = LoggerFactory.getLogger(CategoryAdminAPISteps.class);
    
    private RequestSpecification request;
    private Response response;
    private String authToken;
    private String baseUrl;
    
    public CategoryAdminAPISteps() {
        // Initialize base URL from config
        this.baseUrl = ConfigReader.getApiBaseUrl();
        RestAssured.baseURI = this.baseUrl;
        logger.info("API Base URL set to: {}", this.baseUrl);
    }
    
    // ==================== BACKGROUND STEPS ====================
    
    @Given("Admin authentication token is set in request header")
    public void admin_authentication_token_is_set_in_request_header() {
        logger.info("Step: Setting up authentication token in request header");
        
        // Get admin credentials from config
        String username = ConfigReader.getAdminUsername();
        String password = ConfigReader.getAdminPassword();
        
        // Authenticate and get token
        // Option 1: If your API uses Basic Auth
        // authToken = "Basic " + java.util.Base64.getEncoder()
        //     .encodeToString((username + ":" + password).getBytes());
        
        // Option 2: If your API uses Bearer token (login endpoint)
        // Uncomment and modify this if you have a login endpoint
        
        response = given()
            .contentType("application/json")
            .body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}")
            .when()
            .post("/api/auth/login");
        
        authToken = "Bearer " + response.jsonPath().getString("token");
        
        logger.info("Authentication token set successfully");
    }
    
    @Given("Admin is authenticated and has valid authentication token")
    public void admin_is_authenticated_and_has_valid_authentication_token() {
        logger.info("Step: Verifying admin has valid authentication token");
        
        // Initialize request with auth header
        request = given()
            .header("Authorization", authToken)
            .contentType("application/json")
            .accept("application/json");
        
        logger.info("Request initialized with authentication token");
    }
    
    @Given("At least one parent category {string} exists in the system with id {string}")
    public void at_least_one_parent_category_exists_in_the_system(String categoryName, String categoryId) {
        logger.info("Step: Verifying parent category '{}' exists with id '{}'", categoryName, categoryId);
        
        // Verify category exists by making GET request
        Response getResponse = given()
            .header("Authorization", authToken)
            .when()
            .get("/api/categories/" + categoryId);
        
        if (getResponse.getStatusCode() == 200) {
            logger.info("Parent category exists: {}", categoryName);
        } else {
            logger.warn("Parent category might not exist, will be created by previous test or manually");
        }
    }
    
    @Given("A category {string} already exists under parent {string}")
    public void a_category_already_exists_under_parent(String categoryName, String parentId) {
        logger.info("Step: Ensuring category '{}' exists under parent '{}'", categoryName, parentId);
        
        // Create the category to ensure it exists for duplicate test
        String requestBody = String.format(
            "{\"name\":\"%s\",\"parent\":{\"id\":%s}}", 
            categoryName, parentId
        );
        
        Response createResponse = given()
            .header("Authorization", authToken)
            .contentType("application/json")
            .body(requestBody)
            .when()
            .post("/api/categories");
        
        logger.info("Category creation response status: {}", createResponse.getStatusCode());
        
        if (createResponse.getStatusCode() == 201 || createResponse.getStatusCode() == 400) {
            logger.info("Category '{}' exists (created or already exists)", categoryName);
        }
    }
    
    // ==================== WHEN STEPS ====================
    
    @When("Admin sends POST request to {string} with valid category data:")
    public void admin_sends_post_request_with_valid_category_data(String endpoint, DataTable dataTable) {
        logger.info("Step: Sending POST request to {} with valid category data", endpoint);
        
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        String name = data.get("name");
        String parent = data.get("parent");
        
        String requestBody;
        if (parent == null || parent.equals("null")) {
            // Main category (no parent)
            requestBody = String.format("{\"name\":\"%s\"}", name);
        } else {
            // Sub-category with parent
            requestBody = String.format(
                "{\"name\":\"%s\",\"parent\":{\"id\":%s}}", 
                name, parent
            );
        }
        
        logger.info("Request body: {}", requestBody);
        
        response = request
            .body(requestBody)
            .when()
            .post(endpoint);
        
        logger.info("Response status code: {}", response.getStatusCode());
        logger.info("Response body: {}", response.getBody().asString());
    }
    
    @When("Admin sends POST request to {string} with valid sub-category data:")
    public void admin_sends_post_request_with_valid_sub_category_data(String endpoint, DataTable dataTable) {
        logger.info("Step: Sending POST request to {} with valid sub-category data", endpoint);
        
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        String name = data.get("name");
        String parentId = data.get("parentId");
        
        String requestBody = String.format(
            "{\"name\":\"%s\",\"parent\":{\"id\":%s}}", 
            name, parentId
        );
        
        logger.info("Request body: {}", requestBody);
        
        response = request
            .body(requestBody)
            .when()
            .post(endpoint);
        
        logger.info("Response status code: {}", response.getStatusCode());
        logger.info("Response body: {}", response.getBody().asString());
    }
    
    @When("Admin sends POST request to {string} with invalid name length:")
    public void admin_sends_post_request_with_invalid_name_length(String endpoint, DataTable dataTable) {
        logger.info("Step: Sending POST request to {} with invalid name length", endpoint);
        
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        String name = data.get("name");
        String parentId = data.get("parentId");
        
        String requestBody;
        if (parentId != null && !parentId.isEmpty()) {
            requestBody = String.format(
                "{\"name\":\"%s\",\"parent\":{\"id\":%s}}", 
                name, parentId
            );
        } else {
            requestBody = String.format("{\"name\":\"%s\"}", name);
        }
        
        logger.info("Request body: {}", requestBody);
        
        response = request
            .body(requestBody)
            .when()
            .post(endpoint);
        
        logger.info("Response status code: {}", response.getStatusCode());
        logger.info("Response body: {}", response.getBody().asString());
    }
    
    @When("Admin sends POST request to {string} with empty name:")
    public void admin_sends_post_request_with_empty_name(String endpoint, DataTable dataTable) {
        logger.info("Step: Sending POST request to {} with empty name", endpoint);
        
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        String name = data.get("name");
        String parentId = data.get("parentId");

        // Convert null to empty string for empty name test
        if (name == null) {
            name = "";
        }
        
        String requestBody;
        if (parentId != null && !parentId.isEmpty()) {
            requestBody = String.format(
                "{\"name\":\"%s\",\"parent\":{\"id\":%s}}", 
                name, parentId
            );
        } else {
            requestBody = String.format("{\"name\":\"%s\"}", name);
        }
        
        logger.info("Request body: {}", requestBody);
        
        response = request
            .body(requestBody)
            .when()
            .post(endpoint);
        
        logger.info("Response status code: {}", response.getStatusCode());
        logger.info("Response body: {}", response.getBody().asString());
    }
    
    @When("Admin sends POST request to {string} with duplicate category data:")
    public void admin_sends_post_request_with_duplicate_category_data(String endpoint, DataTable dataTable) {
        logger.info("Step: Sending POST request to {} with duplicate category data", endpoint);
        
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        String name = data.get("name");
        String parentId = data.get("parentId");
        
        String requestBody = String.format(
            "{\"name\":\"%s\",\"parent\":{\"id\":%s}}", 
            name, parentId
        );
        
        logger.info("Request body: {}", requestBody);
        
        response = request
            .body(requestBody)
            .when()
            .post(endpoint);
        
        logger.info("Response status code: {}", response.getStatusCode());
        logger.info("Response body: {}", response.getBody().asString());
    }
    
    // ==================== THEN STEPS ====================
    
    @Then("Response status code should be {int}")
    public void response_status_code_should_be(int expectedStatusCode) {
        logger.info("Step: Verifying response status code is {}", expectedStatusCode);
        
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
            "Expected status code " + expectedStatusCode + " but got " + actualStatusCode);
        
        logger.info("Response status code verification passed: {}", actualStatusCode);
    }
    
    @Then("Response body should contain created category data")
    public void response_body_should_contain_created_category_data() {
        logger.info("Step: Verifying response body contains created category data");
        
        // Verify response has category object
        Assert.assertNotNull(response.getBody(), "Response body should not be null");
        
        // Verify essential fields exist
        response.then()
            .assertThat()
            .body("id", notNullValue())
            .body("name", notNullValue());
        
        logger.info("Response body contains category data");
    }
    
    @Then("Response body field {string} should match {string}")
    public void response_body_field_should_match(String fieldPath, String expectedValue) {
        logger.info("Step: Verifying field '{}' matches '{}'", fieldPath, expectedValue);
        
        String actualValue = response.jsonPath().getString(fieldPath);
        Assert.assertEquals(actualValue, expectedValue,
            "Expected field '" + fieldPath + "' to be '" + expectedValue + "' but got '" + actualValue + "'");
        
        logger.info("Field '{}' matches expected value: {}", fieldPath, expectedValue);
    }
    
    @Then("Response body field {string} should be {string}")
    public void response_body_field_should_be(String fieldPath, String expectedValue) {
        logger.info("Step: Verifying field '{}' equals '{}'", fieldPath, expectedValue);
        
        String actualValue = response.jsonPath().getString(fieldPath);
        Assert.assertEquals(actualValue, expectedValue,
            "Expected field '" + fieldPath + "' to be '" + expectedValue + "' but got '" + actualValue + "'");
        
        logger.info("Field '{}' equals expected value: {}", fieldPath, expectedValue);
    }
    
    @Then("Response body should contain fields {string}, {string}, {string}")
    public void response_body_should_contain_fields(String field1, String field2, String field3) {
        logger.info("Step: Verifying response contains fields: {}, {}, {}", field1, field2, field3);
        
        response.then()
            .assertThat()
            .body(field1, notNullValue())
            .body(field2, notNullValue())
            .body("$", hasKey(field3));
        
        logger.info("All required fields are present in response");
    }
    
    @Then("Response body should contain error object with status, error, message, and timestamp fields")
    public void response_body_should_contain_error_object() {
        logger.info("Step: Verifying response contains error object with required fields");
        
        response.then()
            .assertThat()
            .body("status", notNullValue())
            .body("error", notNullValue())
            .body("message", notNullValue())
            .body("timestamp", notNullValue());
        
        logger.info("Error object structure verified");
    }

    @Then("Error message should indicate name length validation failed")
    public void error_message_should_indicate_name_length_validation_failed() {
        logger.info("Step: Verifying error message indicates name length validation failure");
        
        // Check details.name field for actual validation message
        String detailsName = response.jsonPath().getString("details.name");
        logger.info("details.name: {}", detailsName);
        
        Assert.assertNotNull(detailsName, "details.name should not be null");
        
        boolean containsValidationMessage = 
            detailsName.toLowerCase().contains("length") ||
            detailsName.toLowerCase().contains("characters") ||
            detailsName.toLowerCase().contains("between") ||
            detailsName.toLowerCase().contains("3") ||
            detailsName.toLowerCase().contains("10");
        
        Assert.assertTrue(containsValidationMessage,
            "details.name should indicate name length validation: " + detailsName);
        
        logger.info("Error message correctly indicates name length validation failure");
    }

    @Then("Error message should indicate name is required")
    public void error_message_should_indicate_name_is_required() {
        logger.info("Step: Verifying error message indicates name is required");
        
        // Check details.name field for actual validation message
        String detailsName = response.jsonPath().getString("details.name");
        logger.info("details.name: {}", detailsName);
        
        Assert.assertNotNull(detailsName, "details.name should not be null");
        
        boolean containsRequiredMessage = 
            detailsName.toLowerCase().contains("required") ||
            detailsName.toLowerCase().contains("mandatory") ||
            detailsName.toLowerCase().contains("empty") ||
            detailsName.toLowerCase().contains("missing") ||
            detailsName.toLowerCase().contains("blank");
        
        Assert.assertTrue(containsRequiredMessage,
            "details.name should indicate name is required: " + detailsName);
        
        logger.info("Error message correctly indicates name is required");
    }
        
    @Then("Error message should indicate duplicate category already exists")
    public void error_message_should_indicate_duplicate_category_already_exists() {
        logger.info("Step: Verifying error message indicates duplicate category");
        
        String errorMessage = response.jsonPath().getString("message");
        logger.info("Error message: {}", errorMessage);
        
        boolean containsDuplicateMessage = 
            errorMessage.toLowerCase().contains("duplicate") ||
            errorMessage.toLowerCase().contains("already exists") ||
            errorMessage.toLowerCase().contains("exist");
        
        Assert.assertTrue(containsDuplicateMessage,
            "Error message should indicate duplicate category: " + errorMessage);
        
        logger.info("Error message correctly indicates duplicate category");
    }
}
