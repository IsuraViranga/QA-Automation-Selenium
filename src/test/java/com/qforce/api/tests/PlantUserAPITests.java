package com.qforce.api.tests;

import com.qforce.api.PlantAPI;
import com.qforce.utils.AuthUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * API Tests for Plant operations with TESTUSER (read-only access)
 * Based on test cases: TC_API_PLT_USER_01 to TC_API_PLT_USER_05
 */
public class PlantUserAPITests {
    
    private static final Logger logger = LogManager.getLogger(PlantUserAPITests.class);
    private String testUserToken;
    private String validPlantId = "8"; // Using existing plant ID from response
    
    @BeforeClass
    public void setUp() {
        logger.info("Setting up Plant User API Tests");
        // Get TESTUSER authentication token
        testUserToken = AuthUtils.getTestUserToken();
        Assert.assertNotNull(testUserToken, "Failed to get TESTUSER authentication token");
    }
    
    /**
     * TC_API_PLT_USER_01: Verify that authenticated user can successfully retrieve all plants via GET /api/plants
     * This test verifies that an authenticated TESTUSER (read-only) can retrieve all plants from the system.
     */
    @Test(priority = 1, description = "TC_API_PLT_USER_01 - Verify TESTUSER can retrieve all plants")
    public void testGetAllPlantsAsTestUser() {
        logger.info("Executing TC_API_PLT_USER_01: Get all plants as TESTUSER");
        
        // Set authentication token for TESTUSER
        AuthUtils.setAuthToken(testUserToken);
        
        // Send GET request to /api/plants
        Response response = PlantAPI.getAllPlants();
        
        // Validate response status code
        Assert.assertEquals(response.getStatusCode(), 200, 
            "Expected status code 200 but got: " + response.getStatusCode());
        
        // Validate response body structure
        Assert.assertTrue(response.getBody().asString().contains("id"), 
            "Response should contain plant objects with id field");
        Assert.assertTrue(response.getBody().asString().contains("name"), 
            "Response should contain plant objects with name field");
        Assert.assertTrue(response.getBody().asString().contains("price"), 
            "Response should contain plant objects with price field");
        Assert.assertTrue(response.getBody().asString().contains("quantity"), 
            "Response should contain plant objects with quantity field");
        Assert.assertTrue(response.getBody().asString().contains("category"), 
            "Response should contain plant objects with category field");
        
        logger.info("TC_API_PLT_USER_01 completed successfully - TESTUSER can view plant data");
    }
    
    /**
     * TC_API_PLT_USER_02: Verify that unauthorized user cannot retrieve plants via GET /api/plants without authentication token
     * This test verifies that the system properly restricts access to plant data when no authentication token is provided.
     */
    @Test(priority = 2, description = "TC_API_PLT_USER_02 - Verify unauthorized access is denied")
    public void testGetAllPlantsWithoutAuthentication() {
        logger.info("Executing TC_API_PLT_USER_02: Get all plants without authentication");
        
        // Clear authentication token
        AuthUtils.clearAuthToken();
        
        // Send GET request to /api/plants without authentication
        Response response = PlantAPI.getAllPlants();
        
        // Validate response status code
        Assert.assertEquals(response.getStatusCode(), 401, 
            "Expected status code 401 (Unauthorized) but got: " + response.getStatusCode());
        
        // Validate error response body
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("status"), 
            "Error response should contain status field");
        Assert.assertTrue(responseBody.contains("error"), 
            "Error response should contain error field");
        Assert.assertTrue(responseBody.contains("message"), 
            "Error response should contain message field");
        // Note: This API doesn't return timestamp field in 401 responses
        
        // Verify no plant data is returned
        Assert.assertFalse(responseBody.contains("\"id\":"), 
            "Response should not contain plant data when unauthorized");
        
        logger.info("TC_API_PLT_USER_02 completed successfully - Unauthorized access properly denied");
    }
    
    /**
     * TC_API_PLT_USER_03: Verify that TESTUSER cannot create a plant via POST /api/plants/category/{categoryId} due to read-only access
     * This test verifies that TESTUSER with read-only access is denied when attempting to create a new plant.
     */
    @Test(priority = 3, description = "TC_API_PLT_USER_03 - Verify TESTUSER cannot create plants")
    public void testCreatePlantAsTestUserDenied() {
        logger.info("Executing TC_API_PLT_USER_03: Attempt to create plant as TESTUSER");
        
        // Set authentication token for TESTUSER
        AuthUtils.setAuthToken(testUserToken);
        
        // Attempt to create a plant with valid data
        String categoryId = "1"; // Assuming category with ID 1 exists
        Response response = PlantAPI.createPlant(categoryId, "Orchid White", 500.00, 30);
        
        // Validate response status code
        Assert.assertEquals(response.getStatusCode(), 403, 
            "Expected status code 403 (Forbidden) but got: " + response.getStatusCode());
        
        // Validate error response body
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("status"), 
            "Error response should contain status field");
        Assert.assertTrue(responseBody.contains("error"), 
            "Error response should contain error field");
        Assert.assertTrue(responseBody.contains("timestamp"), 
            "Error response should contain timestamp field");
        // Note: API returns "Forbidden" as error message, not specific permission message
        
        logger.info("TC_API_PLT_USER_03 completed successfully - TESTUSER create access properly denied");
    }
    
    /**
     * TC_API_PLT_USER_04: Verify that TESTUSER can successfully retrieve a specific plant by ID via GET /api/plants/{id}
     * This test verifies that an authenticated TESTUSER can retrieve details of a specific plant using its ID.
     */
    @Test(priority = 4, description = "TC_API_PLT_USER_04 - Verify TESTUSER can retrieve specific plant by ID")
    public void testGetPlantByIdAsTestUser() {
        logger.info("Executing TC_API_PLT_USER_04: Get plant by ID as TESTUSER");
        
        // Set authentication token for TESTUSER
        AuthUtils.setAuthToken(testUserToken);
        
        // Send GET request to /api/plants/{id}
        Response response = PlantAPI.getPlantById(validPlantId);
        
        // Validate response status code
        Assert.assertEquals(response.getStatusCode(), 200, 
            "Expected status code 200 but got: " + response.getStatusCode());
        
        // Validate response body structure
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("id"), 
            "Response should contain plant object with id field");
        Assert.assertTrue(responseBody.contains("name"), 
            "Response should contain plant object with name field");
        Assert.assertTrue(responseBody.contains("price"), 
            "Response should contain plant object with price field");
        Assert.assertTrue(responseBody.contains("quantity"), 
            "Response should contain plant object with quantity field");
        
        // Verify plant id matches the requested id
        Assert.assertTrue(responseBody.contains("\"id\":" + validPlantId) || 
                         responseBody.contains("\"id\":\"" + validPlantId + "\""), 
            "Plant ID should match the requested ID: " + validPlantId);
        
        logger.info("TC_API_PLT_USER_04 completed successfully - TESTUSER can view individual plant details");
    }
    
    /**
     * TC_API_PLT_USER_05: Verify that TESTUSER cannot delete a plant via DELETE /api/plants/{id} due to read-only access
     * This test verifies that TESTUSER with read-only access is denied when attempting to delete an existing plant.
     */
    @Test(priority = 5, description = "TC_API_PLT_USER_05 - Verify TESTUSER cannot delete plants")
    public void testDeletePlantAsTestUserDenied() {
        logger.info("Executing TC_API_PLT_USER_05: Attempt to delete plant as TESTUSER");
        
        // Set authentication token for TESTUSER
        AuthUtils.setAuthToken(testUserToken);
        
        // Attempt to delete a plant
        Response deleteResponse = PlantAPI.deletePlant(validPlantId);
        
        // Validate response status code
        Assert.assertEquals(deleteResponse.getStatusCode(), 403, 
            "Expected status code 403 (Forbidden) but got: " + deleteResponse.getStatusCode());
        
        // Validate error response body
        String responseBody = deleteResponse.getBody().asString();
        Assert.assertTrue(responseBody.contains("status"), 
            "Error response should contain status field");
        Assert.assertTrue(responseBody.contains("error"), 
            "Error response should contain error field");
        Assert.assertTrue(responseBody.contains("timestamp"), 
            "Error response should contain timestamp field");
        // Note: API returns "Forbidden" as error message, not specific permission message
        
        // Verify plant still exists by attempting to get it
        Response getResponse = PlantAPI.getPlantById(validPlantId);
        Assert.assertEquals(getResponse.getStatusCode(), 200, 
            "Plant should still exist after failed delete attempt");
        
        logger.info("TC_API_PLT_USER_05 completed successfully - TESTUSER delete access properly denied and plant still exists");
    }
}