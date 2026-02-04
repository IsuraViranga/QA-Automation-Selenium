# Plant User Test Cases

This folder contains Cucumber feature files for Plant User functionality testing.

## Test Coverage

### PlantUser.feature
Contains test scenarios for regular users (non-admin) interacting with the Plants page:

1. **TC_PLT_USER_01**: Verify that user can view all plants in the plants list page
2. **TC_PLT_USER_02**: Verify that user can search for plants by name
3. **TC_PLT_USER_03**: Verify that user can filter plants by category
4. **TC_PLT_USER_04**: Verify that user can reset filters to view all plants
5. **TC_PLT_USER_05**: Verify that user sees appropriate message when no plants match search criteria

## Test User Credentials
- Username: `testuser`
- Password: `test123`

## Page URL
- Plants List Page: `http://localhost:8080/ui/plants`

## Key Features Tested
- Page navigation and loading
- Search functionality
- Category filtering
- Filter reset functionality
- Empty state handling
- User permissions (no Add/Edit/Delete buttons)
- Table display and data verification

## Running Tests

### Using TestNG
```bash
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-plant-user.xml
```

### Using Cucumber Runner
```bash
mvn test -Dtest=PlantUserTestRunner
```

## Page Objects
- **PlantPage**: `src/main/java/com/qforce/pages/plants/PlantPage.java`
- **Step Definitions**: `src/test/java/com/qforce/stepdefinitions/plants/PlantUserSteps.java`

## Test Data Requirements
- At least one plant should exist in the system for positive test scenarios
- Multiple plants in different categories for filtering tests
- Categories should be available for dropdown testing