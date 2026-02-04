# Plant Admin Test Cases

This document describes the 5 admin UI test cases for the Plants page functionality.

## Test Cases Overview

### TC_PLT_ADMIN_01: Add Plant with Valid Details
- **Purpose**: Verify that admin can successfully add a new plant with all valid details
- **Precondition**: Admin is logged in and navigated to Add Plant page
- **Test Data**: Plant Name: "Rose Red", Category: "Flowers", Price: "250.00", Quantity: "50"
- **Expected Result**: Plant is created successfully and appears in the plants list

### TC_PLT_ADMIN_02: Add Plant without Category (Validation)
- **Purpose**: Verify that admin cannot add a plant without selecting a category
- **Precondition**: Admin is logged in and navigated to Add Plant page
- **Test Data**: Plant Name: "Orchid White", Category: Not selected, Price: "500.00", Quantity: "30"
- **Expected Result**: Validation error is displayed and plant is not created

### TC_PLT_ADMIN_03: View Plants List Page
- **Purpose**: Verify that admin can view all plants in the plants list page
- **Precondition**: Admin is logged in and at least one plant exists
- **Expected Result**: Plants list page loads with all expected elements and functionality

### TC_PLT_ADMIN_04: Filter Plants by Category
- **Purpose**: Verify that admin can filter plants by category
- **Precondition**: Admin is on Plants List page with multiple plants in different categories
- **Test Data**: Filter by "Flowers" category
- **Expected Result**: Only plants from selected category are displayed

### TC_PLT_ADMIN_05: Cancel Adding Plant
- **Purpose**: Verify that admin can cancel adding a plant and return to plants list
- **Precondition**: Admin is on Add Plant page
- **Test Data**: Plant Name: "Tulip Yellow", Category: "Ornamental", Price: "150.00", Quantity: "40"
- **Expected Result**: User is redirected to plants list without saving data

## File Structure

```
src/test/resources/features/plants/
├── PlantAdmin.feature              # Cucumber feature file with scenarios
└── PlantAdminREADME.md            # This documentation file

src/main/java/com/qforce/pages/plants/
├── PlantPage.java                  # Page object for Plants List page
└── PlantAdminPage.java            # Page object for Add/Edit Plant page

src/test/java/com/qforce/stepdefinitions/plants/
├── PlantUserSteps.java            # Step definitions for user scenarios
└── PlantAdminSteps.java           # Step definitions for admin scenarios

src/test/java/com/qforce/runners/
└── PlantAdminTestRunner.java      # TestNG runner for admin tests

src/test/resources/
└── testng-plant-admin.xml         # TestNG suite configuration
```

## Page Object Locator Strategy

Following the specified locator priority order:
1. **ID** - Primary choice when available
2. **Name** - Secondary choice
3. **CSS Selector** - Third choice
4. **XPath** - Last resort

### Add Plant Page Elements

| Element | ID | Name | CSS | XPath |
|---------|----|----- |-----|-------|
| Plant Name Field | `name` | `name` | `input[th:field='*{name}']` | `//input[@type='text' and contains(@class,'form-control')]` |
| Category Dropdown | `categoryId` | `categoryId` | `select[th:field='*{categoryId}']` | `//select[contains(@class,'form-select')]` |
| Price Field | `price` | `price` | `input[th:field='*{price}']` | `//input[@type='number' and @step='0.01']` |
| Quantity Field | `quantity` | `quantity` | `input[th:field='*{quantity}']` | `//input[@type='number' and contains(@class,'form-control')]` |
| Save Button | `saveButton` | `save` | `button.btn.btn-primary` | `//button[@class='btn btn-primary' and normalize-space()='Save']` |
| Cancel Button | `cancelButton` | `cancel` | `a.btn.btn-secondary` | `//a[@href='/ui/plants' and @class='btn btn-secondary' and normalize-space()='Cancel']` |

## Test Data

### Categories Available
- Flowers
- Ornamental

### Login Credentials
- **Admin Username**: admin
- **Admin Password**: admin123

### URLs
- **Login Page**: http://localhost:8080/ui/login
- **Plants List**: http://localhost:8080/ui/plants
- **Add Plant**: http://localhost:8080/ui/plants/add

## Running the Tests

### Using TestNG XML
```bash
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-plant-admin.xml
```

### Using Cucumber Tags
```bash
mvn test -Dcucumber.filter.tags="@PlantAdmin and @UI"
```

### Using TestNG Runner Class
```bash
mvn test -Dtest=PlantAdminTestRunner
```

## Expected Test Results

All 5 test cases should pass when:
1. Application is running on localhost:8080
2. Database contains the required categories (Flowers, Ornamental)
3. Admin user credentials are valid
4. Browser is properly configured

## Troubleshooting

### Common Issues
1. **Login Fails**: Verify admin credentials in config.properties
2. **Page Not Found**: Ensure application is running on correct port
3. **Element Not Found**: Check if locators match the actual HTML structure
4. **Category Not Available**: Verify test data setup in database

### Debug Mode
Enable debug logging by setting log level to DEBUG in config.properties:
```properties
log.level=DEBUG
```

## Integration with Existing Framework

These admin test cases integrate with the existing test framework:
- Uses same BasePage class and utilities
- Follows same naming conventions
- Uses same reporting (Allure, Cucumber reports)
- Shares common step definitions where applicable
- Uses same configuration management