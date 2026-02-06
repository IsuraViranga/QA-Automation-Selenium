package com.qforce.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for authentication operations
 */
public class AuthUtils {
    
    private static final Logger logger = LogManager.getLogger(AuthUtils.class);
    private static final String LOGIN_ENDPOINT = "/api/auth/login";
    private static final String BASE_URL = ConfigReader.getApiBaseUrl();
    
    private static String currentAuthToken = null;
    
    /**
     * Get authentication token for admin user
     */
    public static String getAdminToken() {
        logger.info("Getting admin authentication token");
        
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", ConfigReader.getAdminUsername());
        loginRequest.put("password", ConfigReader.getAdminPassword());
        
        Response response = RestAssured.given()
            .baseUri(BASE_URL)
            .contentType(ContentType.JSON)
            .body(loginRequest)
            .when()
            .post(LOGIN_ENDPOINT)
            .then()
            .extract().response();
        
        if (response.getStatusCode() == 200) {
            String token = response.jsonPath().getString("token");
            logger.info("Admin token retrieved successfully");
            return token;
        } else {
            logger.error("Failed to get admin token. Status: {}, Response: {}", 
                        response.getStatusCode(), response.getBody().asString());
            throw new RuntimeException("Failed to authenticate admin user");
        }
    }
    
    /**
     * Get authentication token for test user (TESTUSER)
     */
    public static String getTestUserToken() {
        logger.info("Getting TESTUSER authentication token");
        
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", ConfigReader.getUserUsername());
        loginRequest.put("password", ConfigReader.getUserPassword());
        
        Response response = RestAssured.given()
            .baseUri(BASE_URL)
            .contentType(ContentType.JSON)
            .body(loginRequest)
            .when()
            .post(LOGIN_ENDPOINT)
            .then()
            .extract().response();
        
        if (response.getStatusCode() == 200) {
            String token = response.jsonPath().getString("token");
            logger.info("TESTUSER token retrieved successfully");
            return token;
        } else {
            logger.error("Failed to get TESTUSER token. Status: {}, Response: {}", 
                        response.getStatusCode(), response.getBody().asString());
            throw new RuntimeException("Failed to authenticate TESTUSER");
        }
    }
    
    /**
     * Set authentication token for subsequent requests
     */
    public static void setAuthToken(String token) {
        currentAuthToken = token;
        // Set default authentication header for RestAssured
        RestAssured.requestSpecification = RestAssured.given()
            .header("Authorization", "Bearer " + token);
        logger.info("Authentication token set for subsequent requests");
    }
    
    /**
     * Clear authentication token
     */
    public static void clearAuthToken() {
        currentAuthToken = null;
        RestAssured.requestSpecification = null;
        logger.info("Authentication token cleared");
    }
    
    /**
     * Get current authentication token
     */
    public static String getCurrentAuthToken() {
        return currentAuthToken;
    }
    
    /**
     * Check if user is currently authenticated
     */
    public static boolean isAuthenticated() {
        return currentAuthToken != null && !currentAuthToken.isEmpty();
    }
}