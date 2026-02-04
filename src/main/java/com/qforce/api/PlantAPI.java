package com.qforce.api;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * API endpoints and methods for Plant operations
 */
public class PlantAPI {
    
    private static final Logger logger = LogManager.getLogger(PlantAPI.class);
    private static final String PLANTS_ENDPOINT = "/api/plants";
    
    /**
     * Get all plants
     */
    public static Response getAllPlants() {
        logger.info("Getting all plants");
        return APIClient.get(PLANTS_ENDPOINT);
    }
    
    /**
     * Get plant by ID
     */
    public static Response getPlantById(String plantId) {
        logger.info("Getting plant with ID: {}", plantId);
        return APIClient.get(PLANTS_ENDPOINT + "/" + plantId);
    }
    
    /**
     * Create new plant under a category
     */
    public static Response createPlant(String categoryId, String plantName, double price, int quantity) {
        logger.info("Creating plant: {} under category: {} with price: {} and quantity: {}", 
                   plantName, categoryId, price, quantity);
        
        Map<String, Object> requestBody = new HashMap<>();
        // Only add name if it's not null (for validation testing)
        if (plantName != null) {
            requestBody.put("name", plantName);
        }
        requestBody.put("price", price);
        requestBody.put("quantity", quantity);
        
        return APIClient.post(PLANTS_ENDPOINT + "/category/" + categoryId, requestBody);
    }
    
    /**
     * Update plant
     */
    public static Response updatePlant(String plantId, String plantName, double price, int quantity) {
        logger.info("Updating plant {} with name: {}, price: {}, quantity: {}", 
                   plantId, plantName, price, quantity);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", plantName);
        requestBody.put("price", price);
        requestBody.put("quantity", quantity);
        
        return APIClient.put(PLANTS_ENDPOINT + "/" + plantId, requestBody);
    }
    
    /**
     * Delete plant
     */
    public static Response deletePlant(String plantId) {
        logger.info("Deleting plant: {}", plantId);
        return APIClient.delete(PLANTS_ENDPOINT + "/" + plantId);
    }
    
    /**
     * Get plants by category
     */
    public static Response getPlantsByCategory(String categoryId) {
        logger.info("Getting plants by category: {}", categoryId);
        return APIClient.get(PLANTS_ENDPOINT + "/category/" + categoryId);
    }
    
    /**
     * Verify plant creation via API
     * This can be used in UI tests to set up test data
     */
    public static boolean verifyPlantExists(String plantName) {
        Response response = getAllPlants();
        String responseBody = response.getBody().asString();
        return responseBody.contains(plantName);
    }
}