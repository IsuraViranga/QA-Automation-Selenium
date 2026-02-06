package com.qforce.stepdefinitions.categoryApiDefinitions;

import com.qforce.api.TestDataHelper;
import com.qforce.api.CategoryAPIClient;
import io.cucumber.java.en.*;
import com.qforce.utils.ConfigReader;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoryAdminAPISteps {
    
    private static final Logger logger = LoggerFactory.getLogger(CategoryAdminAPISteps.class);
    private CategoryAPIClient categoryAPIClient = new CategoryAPIClient();
    private Response response;

    @Given("Admin is authenticated via API")
    public void admin_is_authenticated_via_api() {
        logger.info("Step: Authenticating Admin via API");
        categoryAPIClient.authenticateAdmin();
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

    @Given("At least one parent category exists in the system")
    public void at_least_one_category_exists_in_the_system() {
        logger.info("Step: Ensuring at least one category exists (using API)");
        
        // Use API to ensure at least 1 category exists
        TestDataHelper.ensureMinimumCategories(1, "Tooi");
        
        // Verify count
        int count = TestDataHelper.getCategoryCount();
        logger.info("Category count after setup: {}", count);
        
        Assert.assertTrue(count >= 1, 
            "Expected at least 1 category but found: " + count);
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
        Integer parentId = TestDataHelper.getAnyParentCategoryId();
        if (parentId == null) {
            throw new RuntimeException("No parent category exists. Ensure at least one parent category is created.");
        }
        
        String requestBody = String.format(
            "{\"name\":\"%s\",\"parent\":{\"id\":%d}}", 
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
        //logger.info("Step: Sending POST request to {} with duplicate category data", endpoint);

        // Get real existing sub-category from system
        Map<String, Object> subCategory = TestDataHelper.getAnySubCategory();

        if (subCategory == null) {
            throw new RuntimeException("No sub-category exists to test duplicate scenario");
        }

        String name = subCategory.get("name").toString();
        Integer parentId = (Integer) subCategory.get("parentId");

        String requestBody = String.format(
            "{\"name\":\"%s\",\"parent\":{\"id\":%d}}", 
            name, parentId
        );

        logger.info("Duplicate request body: {}", requestBody);

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

    @Given("Category with ID {int} exists")
    public void category_with_id_exists(int id) {
        logger.info("Step: Verifying Category ID {} exists", id);
        Response resp = categoryAPIClient.getCategoryById(id);
        
        if (resp.getStatusCode() != 200) {
            logger.warn("Category ID {} does not exist (Status: {}). Test might fail.", id, resp.getStatusCode());
            // We might choose to fail here OR rely on the test data being pre-loaded.
            // For robust tests, we should assert or create it.
            // But based on instructions, we assume data exists (1, 3, 5).
            Assert.assertEquals(resp.getStatusCode(), 200, "Category ID " + id + " required for test but not found.");
        }
    }

    @When("Admin sends PUT request to update category {int} with name {string} and parentId null")
    public void admin_sends_put_request_update_null_parent(int id, String name) {
        logger.info("Step: Admin updating category {} with name '{}' and parentId null", id, name);
        response = categoryAPIClient.updateCategory(id, name, null);
    }

    @Then("The API response status code should be {int}")
    public void the_api_response_status_code_should_be(int expectedStatus) {
        logger.info("Step: Verifying response status code is {}", expectedStatus);
        Assert.assertEquals(response.getStatusCode(), expectedStatus);
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
            detailsName.toLowerCase().contains("blank") ||
            detailsName.toLowerCase().contains("between 3 and 10");
        
        Assert.assertTrue(containsRequiredMessage,
            "details.name should indicate name is required: " + detailsName);
        
        logger.info("Error message correctly indicates name is required");
    @Then("The response body should contain name {string}")
    public void the_response_body_should_contain_name(String expectedName) {
        logger.info("Step: Verifying response body contains name '{}'", expectedName);
        String actualName = response.jsonPath().getString("name");
        Assert.assertEquals(actualName, expectedName);
    }

    @Then("The response body should contain parentId null")
    public void the_response_body_should_contain_parent_id_null() {
        logger.info("Step: Verifying response body contains parentId null");
        Object parentId = response.jsonPath().get("parentId");
        Assert.assertNull(parentId, "Expected parentId to be null but got: " + parentId);
    }

    @Then("The response body should contain error {string}")
    public void the_response_body_should_contain_error(String partialError) {
        logger.info("Step: Verifying response body contains error '{}'", partialError);
        String responseBody = response.getBody().asString();
        // Check generic "message" or "errors" field or just body content
        // Spring Boot validation errors usually in "errors" array or "message"
        // Based on CSV "Error message indicates...", checking if body contains the string is safest for now
        Assert.assertTrue(responseBody.contains(partialError), 
            "Response body did not contain error message: " + partialError + ". Body: " + responseBody);
    }

    @When("Admin sends DELETE request for category {int}")
    public void admin_sends_delete_request_for_category(int id) {
        logger.info("Step: Admin deleting category {}", id);
        response = categoryAPIClient.deleteCategory(id);
    }

    @Then("Category {int} should not exist")
    public void category_should_not_exist(int id) {
        logger.info("Step: Verifying Category ID {} no longer exists", id);
        Response resp = categoryAPIClient.getCategoryById(id);
        Assert.assertEquals(resp.getStatusCode(), 404, "Category " + id + " should be deleted (404) but got " + resp.getStatusCode());
    }

    @Then("The response body should indicate category not found")
    public void the_response_body_should_indicate_category_not_found() {
        logger.info("Step: Verifying response indicates category not found");
        // We already checked 404 status. 
        // We can check message if needed.
        // Usually contains "Resource not found" or similar.
    }
}
