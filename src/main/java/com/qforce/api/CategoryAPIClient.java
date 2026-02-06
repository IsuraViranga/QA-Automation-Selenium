package com.qforce.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Category API Client for REST API testing
 */
public class CategoryAPIClient {
    private static final Logger logger = LoggerFactory.getLogger(CategoryAPIClient.class);
    private String authToken;
    private final String baseUrl;

    public CategoryAPIClient() {
        this.baseUrl = APIConfig.getBaseUrl();
        RestAssured.baseURI = baseUrl;
    }

    /**
     * Authenticate user and get JWT token
     */
    public void authenticateUser() {
        logger.info("Authenticating user: {}", APIConfig.getUserUsername());
        
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", APIConfig.getUserUsername());
        credentials.put("password", APIConfig.getUserPassword());

        authenticate(credentials);
    }

    /**
     * Authenticate admin and get JWT token
     */
    public void authenticateAdmin() {
        logger.info("Authenticating admin: {}", APIConfig.getAdminUsername());

        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", APIConfig.getAdminUsername());
        credentials.put("password", APIConfig.getAdminPassword());

        authenticate(credentials);
    }

    private void authenticate(Map<String, String> credentials) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post(APIConfig.getAuthEndpoint())
                .then()
                .extract()
                .response();

        if (response.getStatusCode() == 200) {
            this.authToken = response.jsonPath().getString("token");
            logger.info("Authentication successful. Token obtained.");
        } else {
            logger.error("Authentication failed. Status code: {}", response.getStatusCode());
            throw new RuntimeException("Authentication failed: " + response.getBody().asString());
        }
    }

    /**
     * Get request specification with auth token
     */
    private RequestSpecification getAuthenticatedRequest() {
        return given()
                .header("Authorization", "Bearer " + authToken)
                .contentType(ContentType.JSON);
    }

    /**
     * GET all categories
     */
    public Response getAllCategories() {
        logger.info("Sending GET request to: {}", APIConfig.getCategoriesEndpoint());
        
        return getAuthenticatedRequest()
                .when()
                .get(APIConfig.getFullUrl(APIConfig.getCategoriesEndpoint()))
                .then()
                .extract()
                .response();
    }

    /**
     * GET categories with pagination
     */
    public Response getCategoriesWithPagination(int page, int size) {
        logger.info("Sending GET request with pagination: page={}, size={}", page, size);
        
        return getAuthenticatedRequest()
                .queryParam("page", page)
                .queryParam("size", size)
                .when()
                .get(APIConfig.getFullUrl(APIConfig.getCategoriesPageEndpoint()))
                .then()
                .extract()
                .response();
    }

    /**
     * GET categories with search parameter
     */
    public Response getCategoriesBySearch(String searchTerm) {
        logger.info("Sending GET request with search: {}", searchTerm);
        
        return getAuthenticatedRequest()
                .queryParam("search", searchTerm)
                .when()
                .get(APIConfig.getFullUrl(APIConfig.getCategoriesPageEndpoint()))
                .then()
                .extract()
                .response();
    }

    /**
     * GET category by ID
     */
    public Response getCategoryById(int id) {
        logger.info("Sending GET request for category ID: {}", id);
        
        return getAuthenticatedRequest()
                .when()
                .get(APIConfig.getFullUrl(APIConfig.getCategoriesEndpoint() + "/" + id))
                .then()
                .extract()
                .response();
    }

    /**
     * PUT update category
     */
    /**
     * PUT update category
     */
    public Response updateCategory(int id, String name, Integer parentId) {
        logger.info("Sending PUT request to update category ID: {}", id);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("parentId", parentId);

        return getAuthenticatedRequest()
                .body(requestBody)
                .when()
                .put(APIConfig.getFullUrl(APIConfig.getCategoriesEndpoint() + "/" + id))
                .then()
                .extract()
                .response();
    }

    /**
     * DELETE category by ID
     */
    public Response deleteCategory(int id) {
        logger.info("Sending DELETE request for category ID: {}", id);
        
        return getAuthenticatedRequest()
                .when()
                .delete(APIConfig.getFullUrl(APIConfig.getCategoriesEndpoint() + "/" + id))
                .then()
                .extract()
                .response();
    }

    /**
     * Get current auth token
     */
    public String getAuthToken() {
        return authToken;
    }
}
