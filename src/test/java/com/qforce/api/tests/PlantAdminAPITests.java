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
 * API Tests for Plant operations with ADMIN user (full access)
 * Based on test cases: TC_API_PLT_ADMIN_01 to TC_API_PLT_ADMIN_05
 */
public class PlantAdminAPITests {
    
    private static final Logger logger = LogManager.getLogger(PlantAdminAPITests.class);
    private String adminToken;
    private String validCategoryId = "8"; // Using existing sub-category ID (Flowers)
    private String createdPlantId; // Will store ID of plant created during tests
    
    @BeforeClass
    public void setUp() {
        logger.info("Setting up Plant Admin API Tests");
        // Get ADMIN authentication token
        adminToken = AuthUtils.getAdminToken();
        Assert.assertNotNull(adminToken, "Failed to get ADMIN authentication token");
    }
    
    /**
     * TC_API_PLT_ADMIN_01: Verify that admin can successfully create a new plant under a sub-category via POST /api/plants/category/{categoryId}
     * This test verifies that an authenticated admin user can create a new plant by providing valid plant details (name, price, quantity) under a specific sub-category.
     */
    @Test(priority = 1, description = "TC_API_PLT_ADMIN_01 - Verify admin can create a new plant")
    public void testCreatePlantAsAdmin() {
        logger.info("Executing TC_API_PLT_ADMIN_01: Create new plant as ADMIN");
        
        // Set authentication token for ADMIN
        AuthUtils.setAuthToken(adminToken);
        
        // Prepare request body with valid plant data
        String plantName = "Rose Red";
        double price = 250.00;
        int quantity = 50;
        
        // Send POST request to /api/plants/category/{categoryId}
        Response response = PlantAPI.createPlant(validCategoryId, plantName, price, quantity);
        
        // Validate response status code
        Assert.assertEquals(response.getStatusCode(), 201, 
            "Expected status code 201 (Created) but got: " + response.getStatusCode());
        
        // Validate response body
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("id"), 
            "Response should contain newly created plant object with id field");
        Assert.assertTrue(responseBody.contains("\"name\":\"" + plantName + "\""), 
            "Plant name should match: " + plantName);
        Assert.assertTrue(responseBody.contains("\"price\":" + price), 
            "Price should match: " + price);
        Assert.assertTrue(responseBody.contains("\"quantity\":" + quantity), 
            "Quantity should match: " + quantity);
        Assert.assertTrue(responseBody.contains("category"), 
            "Response should include category information");
        
        // Extract plant ID for use in subsequent tests
        createdPlantId = response.jsonPath().getString("id");
        Assert.assertNotNull(createdPlantId, "Created plant ID should not be null");
        
        logger.info("TC_API_PLT_ADMIN_01 completed successfully - Plant created with ID: {}", createdPlantId);
    }
    
    /**
     * TC_API_PLT_ADMIN_02: Verify that admin can successfully update an existing plant via PUT /api/plants/{id}
     * This test verifies that an authenticated admin user can update plant details (name, price, quantity) for an existing plant.
     */
    @Test(priority = 2, dependsOnMethods = "testCreatePlantAsAdmin", 
          description = "TC_API_PLT_ADMIN_02 - Verify admin can update existing plant")
    public void testUpdatePlantAsAdmin() {
        logger.info("Executing TC_API_PLT_ADMIN_02: Update existing plant as ADMIN");
        
        // Set authentication token for ADMIN
        AuthUtils.setAuthToken(adminToken);
        
        // Prepare updated plant data
        String updatedName = "Rose Crimson";
        double updatedPrice = 300.00;
        int updatedQuantity = 60;
        
        // Send PUT request to /api/plants/{id}
        Response response = PlantAPI.updatePlant(createdPlantId, updatedName, updatedPrice, updatedQuantity);
        
        // Validate response status code
        Assert.assertEquals(response.getStatusCode(), 200, 
            "Expected status code 200 (OK) but got: " + response.getStatusCode());
        
        // Validate response body
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("\"id\":" + createdPlantId) || 
                         responseBody.contains("\"id\":\"" + createdPlantId + "\""), 
            "Updated plant should have same ID: " + createdPlantId);
        Assert.assertTrue(responseBody.contains("\"name\":\"" + updatedName + "\""), 
            "Plant name should be updated to: " + updatedName);
        Assert.assertTrue(responseBody.contains("\"price\":" + updatedPrice), 
            "Price should be updated to: " + updatedPrice);
        Assert.assertTrue(responseBody.contains("\"quantity\":" + updatedQuantity), 
            "Quantity should be updated to: " + updatedQuantity);
        
        logger.info("TC_API_PLT_ADMIN_02 completed successfully - Plant updated successfully");
    }
    
    /**
     * TC_API_PLT_ADMIN_03: Verify that admin can successfully delete a plant via DELETE /api/plants/{id}
     * This test verifies that an authenticated admin user can delete an existing plant from the system.
     */
    @Test(priority = 3, dependsOnMethods = "testUpdatePlantAsAdmin", 
          description = "TC_API_PLT_ADMIN_03 - Verify admin can delete a plant")
    public void testDeletePlantAsAdmin() {
        logger.info("Executing TC_API_PLT_ADMIN_03: Delete plant as ADMIN");
        
        // Set authentication token for ADMIN
        AuthUtils.setAuthToken(adminToken);
        
        // Send DELETE request to /api/plants/{id}
        Response deleteResponse = PlantAPI.deletePlant(createdPlantId);
        
        // Validate response status code (204 No Content according to Swagger)
        Assert.assertEquals(deleteResponse.getStatusCode(), 204, 
            "Expected status code 204 (No Content) but got: " + deleteResponse.getStatusCode());
        
        // Verify deletion by attempting to get the plant
        Response getResponse = PlantAPI.getPlantById(createdPlantId);
        Assert.assertEquals(getResponse.getStatusCode(), 404, 
            "Expected status code 404 (Not Found) confirming plant is deleted but got: " + getResponse.getStatusCode());
        
        logger.info("TC_API_PLT_ADMIN_03 completed successfully - Plant deleted and confirmed via GET request");
    }
    
    /**
     * TC_API_PLT_ADMIN_04: Verify that admin receives validation error when creating plant without required fields via POST /api/plants/category/{categoryId}
     * This test verifies that the system validates required fields and returns appropriate error when admin attempts to create a plant with missing mandatory data.
     */
    @Test(priority = 4, description = "TC_API_PLT_ADMIN_04 - Verify validation error for missing required fields")
    public void testCreatePlantWithMissingRequiredFields() {
        logger.info("Executing TC_API_PLT_ADMIN_04: Create plant with missing required fields");
        
        // Set authentication token for ADMIN
        AuthUtils.setAuthToken(adminToken);
        
        // Create plant with missing name field (only price and quantity)
        // Using direct API call with incomplete data
        Response response = PlantAPI.createPlant(validCategoryId, null, 250.00, 50);
        
        // Validate response status code
        Assert.assertEquals(response.getStatusCode(), 400, 
            "Expected status code 400 (Bad Request) but got: " + response.getStatusCode());
        
        // Validate error response body
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("status"), 
            "Error response should contain status field");
        Assert.assertTrue(responseBody.contains("error"), 
            "Error response should contain error field");
        Assert.assertTrue(responseBody.contains("message"), 
            "Error response should contain validation error message");
        Assert.assertTrue(responseBody.contains("timestamp"), 
            "Error response should contain timestamp field");
        
        logger.info("TC_API_PLT_ADMIN_04 completed successfully - Validation error returned for missing required field");
    }
    
    /**
     * TC_API_PLT_ADMIN_05: Verify that admin can successfully retrieve all plants via GET /api/plants
     * This test verifies that an authenticated admin user can retrieve all plants from the system.
     */
    @Test(priority = 5, description = "TC_API_PLT_ADMIN_05 - Verify admin can retrieve all plants")
    public void testGetAllPlantsAsAdmin() {
        logger.info("Executing TC_API_PLT_ADMIN_05: Get all plants as ADMIN");
        
        // Set authentication token for ADMIN
        AuthUtils.setAuthToken(adminToken);
        
        // Send GET request to /api/plants
        Response response = PlantAPI.getAllPlants();
        
        // Validate response status code
        Assert.assertEquals(response.getStatusCode(), 200, 
            "Expected status code 200 (OK) but got: " + response.getStatusCode());
        
        // Validate response body structure
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("id"), 
            "Response should contain plant objects with id field");
        Assert.assertTrue(responseBody.contains("name"), 
            "Response should contain plant objects with name field");
        Assert.assertTrue(responseBody.contains("price"), 
            "Response should contain plant objects with price field");
        Assert.assertTrue(responseBody.contains("quantity"), 
            "Response should contain plant objects with quantity field");
        Assert.assertTrue(responseBody.contains("category"), 
            "Response should contain plant objects with category field");
        
        // Verify response is an array of plant objects
        Assert.assertTrue(responseBody.startsWith("[") && responseBody.endsWith("]"), 
            "Response should be an array of plant objects");
        
        logger.info("TC_API_PLT_ADMIN_05 completed successfully - All plants retrieved successfully");
    }
}