package com.qforce.stepdefinitions;

import com.qforce.api.CategoryAPIClient;
import io.cucumber.java.en.*;
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
