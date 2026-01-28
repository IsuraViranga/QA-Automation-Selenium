package com.qforce.api;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * API endpoints and methods for Category operations
 */
public class CategoryAPI {
    
    private static final Logger logger = LogManager.getLogger(CategoryAPI.class);
    private static final String CATEGORIES_ENDPOINT = "/api/categories";
    
    /**
     * Get all categories
     */
    public static Response getAllCategories() {
        logger.info("Getting all categories");
        return APIClient.get(CATEGORIES_ENDPOINT);
    }
    
    /**
     * Get category by ID
     */
    public static Response getCategoryById(String categoryId) {
        logger.info("Getting category with ID: {}", categoryId);
        return APIClient.get(CATEGORIES_ENDPOINT + "/" + categoryId);
    }
    
    /**
     * Create new category
     */
    public static Response createCategory(String categoryName, String parentCategory) {
        logger.info("Creating category: {} with parent: {}", categoryName, parentCategory);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", categoryName);
        requestBody.put("parentCategory", parentCategory);
        
        return APIClient.post(CATEGORIES_ENDPOINT, requestBody);
    }
    
    /**
     * Update category
     */
    public static Response updateCategory(String categoryId, String newName) {
        logger.info("Updating category {} to: {}", categoryId, newName);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", newName);
        
        return APIClient.put(CATEGORIES_ENDPOINT + "/" + categoryId, requestBody);
    }
    
    /**
     * Delete category
     */
    public static Response deleteCategory(String categoryId) {
        logger.info("Deleting category: {}", categoryId);
        return APIClient.delete(CATEGORIES_ENDPOINT + "/" + categoryId);
    }
    
    /**
     * Verify category creation via API
     * This can be used in UI tests to set up test data
     */
    public static boolean verifyCategoryExists(String categoryName) {
        Response response = getAllCategories();
        String responseBody = response.getBody().asString();
        return responseBody.contains(categoryName);
    }
}
