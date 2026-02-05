package com.qforce.utils;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * Test Data Helper for managing test data via API
 * Provides methods to set up and clean up test data for UI tests
 */
public class TestDataHelper {
    
    private static final Logger logger = LogManager.getLogger(TestDataHelper.class);
    private static String adminToken;
    
    /**
     * Get admin authentication token
     */
    private static String getAdminToken() {
        if (adminToken == null) {
            String username = ConfigReader.getAdminUsername();
            String password = ConfigReader.getAdminPassword();
            String baseUrl = ConfigReader.getApiBaseUrl();
            
            Response response = given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}")
                .when()
                .post("/api/auth/login");
            
            adminToken = "Bearer " + response.jsonPath().getString("token");
            logger.info("Admin token obtained successfully");
        }
        return adminToken;
    }
    
    /**
     * Delete all categories from the system
     * Used to ensure empty state for tests
     */
    public static void deleteAllCategories() {
        try {
            String token = getAdminToken();
            String baseUrl = ConfigReader.getApiBaseUrl();

            // Get all categories
            Response response = given()
                    .baseUri(baseUrl)
                    .header("Authorization", token)
                    .when()
                    .get("/api/categories");

            if (response.getStatusCode() == 200) {
                List<Object> categories = response.jsonPath().getList("$");

                if (categories != null && !categories.isEmpty()) {
                    logger.info("Deleting {} categories in two steps", categories.size());

                    // First: Delete child categories (parentName != "-")
                    for (Object catObj : categories) {
                        int id = (Integer) ((java.util.Map<?, ?>) catObj).get("id");
                        String parentName = (String) ((java.util.Map<?, ?>) catObj).get("parentName");

                        if (!"-".equals(parentName)) {
                            Response delResp = given()
                                    .baseUri(baseUrl)
                                    .header("Authorization", token)
                                    .when()
                                    .delete("/api/categories/" + id);

                            if (delResp.getStatusCode() != 200 && delResp.getStatusCode() != 204) {
                                logger.warn("Failed to delete child category {} (parent: {}). Status: {}", id, parentName, delResp.getStatusCode());
                            } else {
                                logger.info("Deleted child category {} (parent: {})", id, parentName);
                            }
                        }
                    }

                    // Second: Delete parent categories (parentName == "-")
                    for (Object catObj : categories) {
                        int id = (Integer) ((java.util.Map<?, ?>) catObj).get("id");
                        String parentName = (String) ((java.util.Map<?, ?>) catObj).get("parentName");

                        if ("-".equals(parentName)) {
                            Response delResp = given()
                                    .baseUri(baseUrl)
                                    .header("Authorization", token)
                                    .when()
                                    .delete("/api/categories/" + id);

                            if (delResp.getStatusCode() != 200 && delResp.getStatusCode() != 204) {
                                logger.warn("Failed to delete parent category {}. Status: {}", id, delResp.getStatusCode());
                            } else {
                                logger.info("Deleted parent category {}", id);
                            }
                        }
                    }

                    logger.info("All categories deleted successfully");
                } else {
                    logger.info("No categories to delete - system already empty");
                }
            } else {
                logger.warn("Failed to get categories. Status: {}", response.getStatusCode());
            }

        } catch (Exception e) {
            logger.error("Error deleting categories", e);
        }
    }

    /**
     * Create a single category
     * 
     * @param categoryName Name of the category to create
     * @return Response object
     */
    public static Response createCategory(String categoryName) {
        return createCategory(categoryName, null);
    }
    
    /**
     * Create a category with optional parent
     * 
     * @param categoryName Name of the category
     * @param parentId Parent category ID (null for main category)
     * @return Response object
     */
    public static Response createCategory(String categoryName, Integer parentId) {
        String token = getAdminToken();
        String baseUrl = ConfigReader.getApiBaseUrl();

        // Always include "parent": null if parentId is null
        String requestBody;
        if (parentId == null) {
            requestBody = String.format("{\"name\":\"%s\", \"parent\": null}", categoryName);
        } else {
            requestBody = String.format("{\"name\":\"%s\", \"parent\": {\"id\": %d}}", categoryName, parentId);
        }

        logger.info("Creating category: {} (parent: {})", categoryName, parentId);

        Response response = given()
                .baseUri(baseUrl)
                .header("Authorization", token)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/categories");

        if (response.getStatusCode() == 201) {
            logger.info("Successfully created category: {}", categoryName);
        } else {
            logger.warn("Failed to create category: {}. Status: {}. Response body: {}",
                    categoryName, response.getStatusCode(), response.getBody().asString());
        }

        return response;
    }
    
    /**
     * Create multiple test categories
     * 
     * @param categoryNames Array of category names to create
     */
    public static void createTestCategories(String... categoryNames) {
        logger.info("Creating {} test categories", categoryNames.length);
        
        for (String name : categoryNames) {
            createCategory(name);
        }
        
        logger.info("Finished creating {} test categories", categoryNames.length);
    }
    
    /**
     * Create a specific number of test categories with default names
     * 
     * @param count Number of categories to create
     */
    public static void createTestCategories(int count , String prefix) {
        logger.info("Creating {} test categories with default names", count);
        
        for (int i = 1; i <= count; i++) {
            createCategory(prefix + i);
        }
        
        logger.info("Finished creating {} test categories", count);
    }
    
    /**
     * Get current count of categories in the system
     * 
     * @return Number of categories
     */
    public static int getCategoryCount() {
        try {
            String token = getAdminToken();
            String baseUrl = ConfigReader.getApiBaseUrl();
            
            Response response = given()
                .baseUri(baseUrl)
                .header("Authorization", token)
                .when()
                .get("/api/categories");
            
            if (response.getStatusCode() == 200) {
                List<Object> categories = response.jsonPath().getList("$");
                int count = categories != null ? categories.size() : 0;
                logger.info("Current category count: {}", count);
                return count;
            } else {
                logger.warn("Failed to get category count. Status: {}", response.getStatusCode());
                return 0;
            }
        } catch (Exception e) {
            logger.error("Error getting category count", e);
            return 0;
        }
    }
    
    /**
     * Verify if a category with given name exists
     * 
     * @param categoryName Name to search for
     * @return true if category exists
     */
    public static boolean categoryExists(String categoryName) {
        try {
            String token = getAdminToken();
            String baseUrl = ConfigReader.getApiBaseUrl();
            
            Response response = given()
                .baseUri(baseUrl)
                .header("Authorization", token)
                .when()
                .get("/api/categories");
            
            if (response.getStatusCode() == 200) {
                String responseBody = response.getBody().asString();
                boolean exists = responseBody.contains("\"name\":\"" + categoryName + "\"");
                logger.info("Category '{}' exists: {}", categoryName, exists);
                return exists;
            }
            return false;
        } catch (Exception e) {
            logger.error("Error checking if category exists", e);
            return false;
        }
    }
    
    /**
     * Ensure at least N categories exist in the system
     * Creates additional categories if needed
     * 
     * @param minCount Minimum number of categories required
     * @param prefix Prefix for auto-generated category names
     */
    public static void ensureMinimumCategories(int minCount , String prefix) {
        int currentCount = getCategoryCount();
        
        if (currentCount < minCount) {
            int needed = minCount - currentCount;
            logger.info("Need to create {} more categories (current: {}, required: {})", 
                needed, currentCount, minCount);
            createTestCategories(needed, prefix);
        } else {
            logger.info("Sufficient categories exist (current: {}, required: {})", 
                currentCount, minCount);
        }
    }
    
    /**
     * Reset categories to a clean state and create specific test data
     * 
     * @param categoriesToCreate Array of category names to create after cleanup
     */
    public static void resetAndCreateCategories(String... categoriesToCreate) {
        logger.info("Resetting categories and creating {} new ones", categoriesToCreate.length);
        deleteAllCategories();
        createTestCategories(categoriesToCreate);
    }
    
    /**
     * Reset token (useful if token expires)
     */
    public static void resetToken() {
        adminToken = null;
        logger.info("Admin token reset");
    }

    /**
     * Get ID of a random or first parent category (categories with parentName == "-")
     * 
     * @return valid parent category ID, or null if none exists
     */
    public static Integer getAnyParentCategoryId() {
        try {
            String token = getAdminToken();
            String baseUrl = ConfigReader.getApiBaseUrl();

            Response response = given()
                    .baseUri(baseUrl)
                    .header("Authorization", token)
                    .when()
                    .get("/api/categories");

            if (response.getStatusCode() == 200) {
                List<Object> categories = response.jsonPath().getList("$");

                for (Object catObj : categories) {
                    java.util.Map<?, ?> catMap = (java.util.Map<?, ?>) catObj;
                    String parentName = (String) catMap.get("parentName");
                    if ("-".equals(parentName)) { // Only pick parent categories
                        int id = (Integer) catMap.get("id");
                        logger.info("Selected parent category ID: {}", id);
                        return id;
                    }
                }
                logger.warn("No parent category found in the system");
                return null;
            } else {
                logger.warn("Failed to fetch categories. Status: {}", response.getStatusCode());
                return null;
            }

        } catch (Exception e) {
            logger.error("Error fetching parent category ID", e);
            return null;
        }
    }

    /**
     * Get any existing sub-category (category with parentName != "-")
     * Returns a map with keys: name, parentId
     */
    public static java.util.Map<String, Object> getAnySubCategory() {
        try {
            String token = getAdminToken();
            String baseUrl = ConfigReader.getApiBaseUrl();

            Response response = given()
                    .baseUri(baseUrl)
                    .header("Authorization", token)
                    .when()
                    .get("/api/categories");

            if (response.getStatusCode() == 200) {
                List<Object> categories = response.jsonPath().getList("$");

                for (Object catObj : categories) {
                    java.util.Map<?, ?> catMap = (java.util.Map<?, ?>) catObj;
                    String parentName = (String) catMap.get("parentName");

                    // sub-category = has a parent
                    if (!"-".equals(parentName)) {
                        int id = (Integer) catMap.get("id");
                        String name = (String) catMap.get("name");

                        // find parent id by matching parentName
                        for (Object parentObj : categories) {
                            java.util.Map<?, ?> parentMap = (java.util.Map<?, ?>) parentObj;
                            if (parentName.equals(parentMap.get("name"))) {
                                int parentId = (Integer) parentMap.get("id");

                                java.util.Map<String, Object> result = new java.util.HashMap<>();
                                result.put("name", name);
                                result.put("parentId", parentId);

                                logger.info("Found sub-category: {} under parentId {}", name, parentId);
                                return result;
                            }
                        }
                    }
                }
            }

            logger.warn("No sub-category found");
            return null;

        } catch (Exception e) {
            logger.error("Error fetching sub-category", e);
            return null;
        }
    }

}
