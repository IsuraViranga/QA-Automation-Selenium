# Plant Admin API Tests Mapping

## Overview
This document maps the Plant Admin API test cases from the CSV file to the implemented TestNG test methods.

## Test Case Mapping

| CSV Test Case ID | Test Method | Description | API Endpoint | Expected Status |
|------------------|-------------|-------------|--------------|-----------------|
| TC_API_PLT_ADMIN_01 | `testCreatePlantAsAdmin()` | Verify admin can create new plant under sub-category | POST /api/plants/category/{categoryId} | 201 Created |
| TC_API_PLT_ADMIN_02 | `testUpdatePlantAsAdmin()` | Verify admin can update existing plant details | PUT /api/plants/{id} | 200 OK |
| TC_API_PLT_ADMIN_03 | `testDeletePlantAsAdmin()` | Verify admin can delete plant by ID | DELETE /api/plants/{id} | 204 No Content |
| TC_API_PLT_ADMIN_04 | `testCreatePlantWithMissingRequiredFields()` | Verify validation error for missing required fields | POST /api/plants/category/{categoryId} | 400 Bad Request |
| TC_API_PLT_ADMIN_05 | `testGetAllPlantsAsAdmin()` | Verify admin can retrieve all plants | GET /api/plants | 200 OK |

## Test Implementation Details

### TC_API_PLT_ADMIN_01: Create Plant
- **Method**: `testCreatePlantAsAdmin()`
- **Priority**: 1
- **Request Body**:
  ```json
  {
    "name": "Rose Red",
    "price": 250.00,
    "quantity": 50
  }
  ```
- **Validations**:
  - Status code: 201
  - Response contains id, name, price, quantity, category fields
  - Values match request data
  - Plant ID is extracted for subsequent tests

### TC_API_PLT_ADMIN_02: Update Plant
- **Method**: `testUpdatePlantAsAdmin()`
- **Priority**: 2
- **Dependencies**: `testCreatePlantAsAdmin()`
- **Request Body**:
  ```json
  {
    "name": "Rose Crimson",
    "price": 300.00,
    "quantity": 60
  }
  ```
- **Validations**:
  - Status code: 200
  - Same plant ID maintained
  - Updated values reflected in response

### TC_API_PLT_ADMIN_03: Delete Plant
- **Method**: `testDeletePlantAsAdmin()`
- **Priority**: 3
- **Dependencies**: `testUpdatePlantAsAdmin()`
- **Validations**:
  - DELETE status code: 204
  - Subsequent GET returns 404 (plant not found)

### TC_API_PLT_ADMIN_04: Validation Error
- **Method**: `testCreatePlantWithMissingRequiredFields()`
- **Priority**: 4
- **Request Body** (missing name):
  ```json
  {
    "price": 250.00,
    "quantity": 50
  }
  ```
- **Validations**:
  - Status code: 400
  - Error response contains status, error, message, timestamp fields

### TC_API_PLT_ADMIN_05: Get All Plants
- **Method**: `testGetAllPlantsAsAdmin()`
- **Priority**: 5
- **Validations**:
  - Status code: 200
  - Response is array of plant objects
  - Each plant contains required fields

## Authentication
- All tests use ADMIN user authentication token
- Token obtained via `AuthUtils.getAdminToken()`
- Token set using `AuthUtils.setAuthToken(adminToken)`

## Test Data
- **Category ID**: "1" (assumed to exist)
- **Plant Names**: "Rose Red", "Rose Crimson"
- **Prices**: 250.00, 300.00
- **Quantities**: 50, 60

## Dependencies
- Tests run in sequence due to dependencies
- Plant created in test 1 is used in tests 2 and 3
- Test 4 is independent (validation test)
- Test 5 is independent (read operation)

## Error Handling
- All tests include proper status code validation
- Error responses validated for required fields
- Logging implemented for debugging

## File Locations
- **Test Class**: `src/test/java/com/qforce/api/tests/PlantAdminAPITests.java`
- **TestNG Suite**: `src/test/resources/testng-plant-admin-api.xml`
- **API Methods**: `src/main/java/com/qforce/api/PlantAPI.java`
- **Authentication**: `src/main/java/com/qforce/utils/AuthUtils.java`

## Execution
Run the tests using:
```bash
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-plant-admin-api.xml
```

## Notes
- Tests are designed to be independent where possible
- Proper cleanup handled by delete test
- Comprehensive logging for debugging
- Follows existing project patterns and conventions