# Plant User API Tests Implementation

This document maps the CSV test cases to the implemented API tests using Rest Assured framework.

## Test Case Mapping

### TC_API_PLT_USER_01
**CSV Test Case:** Verify that authenticated user can successfully retrieve all plants via GET /api/plants  
**Implementation:** `testGetAllPlantsAsTestUser()`  
**Method:** GET /api/plants  
**Expected:** 200 OK with plant array containing id, name, price, quantity, category fields  

### TC_API_PLT_USER_02
**CSV Test Case:** Verify that unauthorized user cannot retrieve plants via GET /api/plants without authentication token  
**Implementation:** `testGetAllPlantsWithoutAuthentication()`  
**Method:** GET /api/plants (no auth token)  
**Expected:** 401 Unauthorized with error object containing status, error, message, timestamp  

### TC_API_PLT_USER_03
**CSV Test Case:** Verify that TESTUSER cannot create a plant via POST /api/plants/category/{categoryId} due to read-only access  
**Implementation:** `testCreatePlantAsTestUserDenied()`  
**Method:** POST /api/plants/category/{categoryId}  
**Expected:** 403 Forbidden with error object, plant not created  

### TC_API_PLT_USER_04
**CSV Test Case:** Verify that TESTUSER can successfully retrieve a specific plant by ID via GET /api/plants/{id}  
**Implementation:** `testGetPlantByIdAsTestUser()`  
**Method:** GET /api/plants/{id}  
**Expected:** 200 OK with single plant object matching requested ID  

### TC_API_PLT_USER_05
**CSV Test Case:** Verify that TESTUSER cannot delete a plant via DELETE /api/plants/{id} due to read-only access  
**Implementation:** `testDeletePlantAsTestUserDenied()`  
**Method:** DELETE /api/plants/{id}  
**Expected:** 403 Forbidden, subsequent GET confirms plant still exists  

## Framework Components

### PlantAPI.java
- Contains all plant-related API methods
- Follows same pattern as CategoryAPI.java
- Methods: getAllPlants(), getPlantById(), createPlant(), updatePlant(), deletePlant()

### AuthUtils.java
- Handles JWT authentication for both ADMIN and TESTUSER
- Methods: getAdminToken(), getTestUserToken(), setAuthToken(), clearAuthToken()
- Integrates with RestAssured for automatic token handling

### APIClient.java (Updated)
- Enhanced to work with authentication tokens
- Automatically applies authentication headers when token is set
- Maintains existing functionality for all HTTP methods

### Configuration
- Uses existing config.properties for credentials
- TESTUSER credentials: testuser/test123
- ADMIN credentials: admin/admin123
- API base URL: http://localhost:8080

## Test Execution

### Prerequisites
1. Application server running on localhost:8080
2. At least one plant and category exist in the system
3. TESTUSER and ADMIN accounts are active

### Running Tests
```bash
# Run via TestNG XML
mvn test -DsuiteXmlFile=src/test/resources/testng/PlantUserAPITests.xml

# Run specific test class
mvn test -Dtest=PlantUserAPITests
```

### Test Data Requirements
- Valid categoryId (assumed: "1")
- Valid plantId (assumed: "1")
- These can be adjusted in the test class if needed

## Validation Points

Each test validates:
1. **HTTP Status Codes** - Correct response codes (200, 401, 403)
2. **Response Structure** - Required fields present in JSON response
3. **Authentication** - Proper handling of JWT tokens
4. **Authorization** - TESTUSER read-only access restrictions
5. **Data Integrity** - Plant data accuracy and persistence

## Error Handling

Tests verify proper error responses:
- **401 Unauthorized** - Missing/invalid authentication
- **403 Forbidden** - Insufficient permissions for TESTUSER
- **Error Response Format** - status, error, message, timestamp fields

## Logging

All tests include comprehensive logging:
- Request/response details via RestAssured
- Test execution flow via Log4j2
- Authentication token management
- Validation results

This implementation provides complete coverage of the 5 USER API test cases from the CSV file using Rest Assured framework.