package com.qforce.api;

import com.qforce.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Base class for API testing using Rest Assured
 */
public class APIClient {
    
    private static final Logger logger = LogManager.getLogger(APIClient.class);
    private static final String BASE_URL = ConfigReader.getApiBaseUrl();
    
    /**
     * Get request specification with base configuration
     */
    private static RequestSpecification getRequestSpec() {
        RequestSpecification spec = RestAssured.given()
            .baseUri(BASE_URL)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .log().all();
        
        // Use existing authentication if set via RestAssured.requestSpecification
        if (RestAssured.requestSpecification != null) {
            return spec.spec(RestAssured.requestSpecification);
        }
        
        return spec;
    }
    
    /**
     * GET request
     */
    public static Response get(String endpoint) {
        logger.info("Sending GET request to: {}", endpoint);
        Response response = getRequestSpec()
            .when()
            .get(endpoint)
            .then()
            .log().all()
            .extract().response();
        
        logger.info("Response Status Code: {}", response.getStatusCode());
        return response;
    }
    
    /**
     * GET request with parameters
     */
    public static Response get(String endpoint, Map<String, String> params) {
        logger.info("Sending GET request to: {} with params: {}", endpoint, params);
        Response response = getRequestSpec()
            .queryParams(params)
            .when()
            .get(endpoint)
            .then()
            .log().all()
            .extract().response();
        
        logger.info("Response Status Code: {}", response.getStatusCode());
        return response;
    }
    
    /**
     * POST request
     */
    public static Response post(String endpoint, Object body) {
        logger.info("Sending POST request to: {}", endpoint);
        Response response = getRequestSpec()
            .body(body)
            .when()
            .post(endpoint)
            .then()
            .log().all()
            .extract().response();
        
        logger.info("Response Status Code: {}", response.getStatusCode());
        return response;
    }
    
    /**
     * POST request with headers
     */
    public static Response post(String endpoint, Object body, Map<String, String> headers) {
        logger.info("Sending POST request to: {} with custom headers", endpoint);
        Response response = getRequestSpec()
            .headers(headers)
            .body(body)
            .when()
            .post(endpoint)
            .then()
            .log().all()
            .extract().response();
        
        logger.info("Response Status Code: {}", response.getStatusCode());
        return response;
    }
    
    /**
     * PUT request
     */
    public static Response put(String endpoint, Object body) {
        logger.info("Sending PUT request to: {}", endpoint);
        Response response = getRequestSpec()
            .body(body)
            .when()
            .put(endpoint)
            .then()
            .log().all()
            .extract().response();
        
        logger.info("Response Status Code: {}", response.getStatusCode());
        return response;
    }
    
    /**
     * DELETE request
     */
    public static Response delete(String endpoint) {
        logger.info("Sending DELETE request to: {}", endpoint);
        Response response = getRequestSpec()
            .when()
            .delete(endpoint)
            .then()
            .log().all()
            .extract().response();
        
        logger.info("Response Status Code: {}", response.getStatusCode());
        return response;
    }
    
    /**
     * PATCH request
     */
    public static Response patch(String endpoint, Object body) {
        logger.info("Sending PATCH request to: {}", endpoint);
        Response response = getRequestSpec()
            .body(body)
            .when()
            .patch(endpoint)
            .then()
            .log().all()
            .extract().response();
        
        logger.info("Response Status Code: {}", response.getStatusCode());
        return response;
    }
    
    /**
     * Authentication - Bearer Token
     */
    public static Response getWithAuth(String endpoint, String token) {
        logger.info("Sending authenticated GET request to: {}", endpoint);
        Response response = getRequestSpec()
            .header("Authorization", "Bearer " + token)
            .when()
            .get(endpoint)
            .then()
            .log().all()
            .extract().response();
        
        return response;
    }
}
