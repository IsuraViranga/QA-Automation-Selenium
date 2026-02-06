# Plant User API Test Results

## Test Execution Summary
**Date:** February 4, 2026  
**Framework:** Rest Assured + TestNG  
**Total Tests:** 5  
**Passed:** 5 ✅  
**Failed:** 0 ❌  
**Success Rate:** 100%

## Individual Test Results

### ✅ TC_API_PLT_USER_01 - Get All Plants as TESTUSER
- **Status:** PASSED
- **Description:** Verify that authenticated TESTUSER can retrieve all plants via GET /api/plants
- **Response Code:** 200 OK
- **Validation:** 
  - Response contains plant array with id, name, price, quantity, category fields
  - 4 plants returned: Rose Plant, Orchid, Bonsai, Rose Red Test 2026
  - All required fields present in response

### ✅ TC_API_PLT_USER_02 - Unauthorized Access Denied
- **Status:** PASSED
- **Description:** Verify unauthorized user cannot retrieve plants without authentication token
- **Response Code:** 401 Unauthorized
- **Validation:**
  - Error response contains status, error, message fields
  - No plant data returned in response
  - Proper security enforcement

### ✅ TC_API_PLT_USER_03 - TESTUSER Cannot Create Plants
- **Status:** PASSED
- **Description:** Verify TESTUSER cannot create plants due to read-only access
- **Response Code:** 403 Forbidden
- **Validation:**
  - Error response contains status, error, timestamp fields
  - Plant creation properly denied
  - Read-only access enforced correctly

### ✅ TC_API_PLT_USER_04 - Get Plant by ID as TESTUSER
- **Status:** PASSED
- **Description:** Verify TESTUSER can retrieve specific plant by ID
- **Response Code:** 200 OK
- **Plant ID:** 8 (Rose Plant)
- **Validation:**
  - Single plant object returned with correct ID
  - All plant details accurately returned (id: 8, name: "Rose Plant", price: 1200.0, quantity: 15, categoryId: 8)

### ✅ TC_API_PLT_USER_05 - TESTUSER Cannot Delete Plants
- **Status:** PASSED
- **Description:** Verify TESTUSER cannot delete plants due to read-only access
- **Response Code:** 403 Forbidden
- **Validation:**
  - Delete operation properly denied
  - Plant still exists after failed delete attempt
  - Read-only access enforced correctly

## Key Findings

### Authentication & Authorization
- JWT authentication working correctly for TESTUSER
- Proper 401 responses for unauthenticated requests
- Correct 403 responses for unauthorized operations (create/delete)
- Read-only access properly enforced for TESTUSER role

### API Response Structure
- Consistent error response format with status, error, timestamp fields
- Plant objects contain all required fields: id, name, price, quantity, category/categoryId
- Different response formats for list vs individual plant endpoints

### Data Validation
- Plant data integrity maintained across operations
- Existing plants: IDs 8, 9, 10, 16 with various categories (Flowers, Ornamental)
- Price ranges from 250.0 to 5000.0
- Quantity ranges from 3 to 50

## Test Coverage Mapping

| CSV Test Case | Implementation | Status | HTTP Method | Expected Code | Actual Code |
|---------------|----------------|--------|-------------|---------------|-------------|
| TC_API_PLT_USER_01 | testGetAllPlantsAsTestUser | ✅ PASS | GET /api/plants | 200 | 200 |
| TC_API_PLT_USER_02 | testGetAllPlantsWithoutAuthentication | ✅ PASS | GET /api/plants | 401 | 401 |
| TC_API_PLT_USER_03 | testCreatePlantAsTestUserDenied | ✅ PASS | POST /api/plants/category/{id} | 403 | 403 |
| TC_API_PLT_USER_04 | testGetPlantByIdAsTestUser | ✅ PASS | GET /api/plants/{id} | 200 | 200 |
| TC_API_PLT_USER_05 | testDeletePlantAsTestUserDenied | ✅ PASS | DELETE /api/plants/{id} | 403 | 403 |

## Framework Components Used

### PlantAPI.java
- getAllPlants() - GET /api/plants
- getPlantById() - GET /api/plants/{id}
- createPlant() - POST /api/plants/category/{categoryId}
- deletePlant() - DELETE /api/plants/{id}

### AuthUtils.java
- getTestUserToken() - JWT authentication for TESTUSER
- setAuthToken() - Set authentication for requests
- clearAuthToken() - Remove authentication

### APIClient.java
- Enhanced with authentication support
- Automatic token handling via RestAssured.requestSpecification
- Comprehensive request/response logging

## Recommendations

1. **Test Data Management:** Consider using dynamic plant IDs instead of hardcoded values
2. **Error Message Validation:** API returns generic "Forbidden" messages - could be more specific
3. **Response Consistency:** Different endpoints return different plant object structures (category vs categoryId)
4. **Test Isolation:** Tests currently depend on existing data - consider setup/teardown methods

## Conclusion

All 5 USER API test cases from the CSV file have been successfully implemented and are passing. The tests validate:
- ✅ TESTUSER read access to plants
- ✅ Proper authentication enforcement
- ✅ Read-only access restrictions
- ✅ Correct HTTP status codes
- ✅ Response data integrity

The implementation follows Rest Assured best practices and provides comprehensive validation of the Plant API functionality for user roles.