# QA Automation Framework - Professional Test Automation Suite

## 📋 Overview

This is a **professional-grade, hybrid test automation framework** built with industry best practices. It combines:
- **Data-Driven Testing** (Excel-based test data)
- **Keyword-Driven Testing** (Reusable keywords)
- **Behavior-Driven Development** (Cucumber BDD)
- **Page Object Model** (Clean, maintainable code structure)

## 🏗️ Framework Architecture

```
qa-automation-framework/
├── src/
│   ├── main/java/com/qforce/
│   │   ├── pages/              # Page Object Model classes
│   │   │   ├── BasePage.java
│   │   │   ├── LoginPage.java
│   │   │   └── CategoryPage.java
│   │   ├── utils/              # Utility classes
│   │   │   ├── ConfigReader.java
│   │   │   ├── DriverManager.java
│   │   │   ├── ExcelReader.java
│   │   │   ├── ScreenshotUtil.java
│   │   │   └── WaitUtil.java
│   │   ├── keywords/           # Keyword-driven framework
│   │   │   ├── KeywordExecutor.java
│   │   │   └── KeywordReader.java
│   │   └── api/                # API testing utilities (Rest Assured)
│   └── test/
│       ├── java/com/qforce/
│       │   ├── stepdefinitions/ # Cucumber step definitions
│       │   │   ├── CategoryAdminSteps.java
│       │   │   └── Hooks.java
│       │   ├── runners/         # TestNG runners
│       │   │   ├── TestRunner.java
│       │   │   └── SmokeTestRunner.java
│       │   └── tests/           # Traditional TestNG tests (if needed)
│       └── resources/
│           ├── features/        # Cucumber feature files
│           │   └── CategoryAdmin.feature
│           ├── testdata/        # Test data Excel files
│           │   └── CategoryTestData.xlsx
│           ├── keywords/        # Keyword definition files
│           ├── config/          # Configuration files
│           │   └── config.properties
│           ├── testng.xml       # TestNG suite files
│           ├── testng-smoke.xml
│           └── log4j2.xml       # Logging configuration
├── test-output/                # Test execution reports
├── screenshots/                # Test screenshots
├── logs/                       # Application logs
└── pom.xml                     # Maven dependencies
```

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 11+ | Programming Language |
| Selenium WebDriver | 4.16.1 | Browser Automation |
| Cucumber | 7.15.0 | BDD Framework |
| TestNG | 7.8.0 | Test Execution Framework |
| Rest Assured | 5.4.0 | API Testing |
| Apache POI | 5.2.5 | Excel Operations |
| WebDriverManager | 5.6.3 | Automatic Driver Management |
| ExtentReports | 5.1.1 | HTML Reporting |
| Allure | 2.25.0 | Advanced Reporting |
| Log4j2 | 2.22.1 | Logging |
| Maven | 3.6+ | Build Tool |

## 📦 Prerequisites

### Required Software:

1. **Java JDK 11 or higher**
   ```bash
   java -version
   ```

2. **Apache Maven 3.6+**
   ```bash
   mvn -version
   ```

3. **Git** (for version control)
   ```bash
   git --version
   ```

4. **IDE** (Choose one):
   - IntelliJ IDEA (Recommended)
   - Eclipse
   - VS Code with Java extensions

### Browser Requirements:
- Chrome (latest version) - Recommended
- Firefox (latest version)
- Edge (latest version)

## 🚀 Setup Instructions

### Step 1: Clone or Download the Framework

```bash
# If using Git
git clone <repository-url>
cd qa-automation-framework

# Or extract the ZIP file and navigate to the directory
```

### Step 2: Import Project in IDE

#### For IntelliJ IDEA:
1. Open IntelliJ IDEA
2. File → Open → Select `qa-automation-framework` folder
3. Wait for Maven to download dependencies automatically

#### For Eclipse:
1. Open Eclipse
2. File → Import → Maven → Existing Maven Projects
3. Browse to `qa-automation-framework` folder
4. Click Finish

### Step 3: Update Configuration

Edit `src/test/resources/config/config.properties`:

```properties
# Update with your application URL
app.url=https://your-application-url.com

# Update admin credentials
app.admin.username=your-admin@email.com
app.admin.password=your-password

# Browser configuration
browser=chrome  # Options: chrome, firefox, edge
headless=false  # Set to true for headless execution
```

### Step 4: Install Dependencies

```bash
mvn clean install -DskipTests
```

This will:
- Download all required Maven dependencies
- Compile the project
- Skip test execution during setup

### Step 5: Verify Setup

Run a simple test to verify everything is working:

```bash
mvn test -Dtest=SmokeTestRunner
```

## 🎯 Running Tests

### Method 1: Using Maven Commands

#### Run All Tests
```bash
mvn clean test
```

#### Run Smoke Tests Only
```bash
mvn test -Dtest=SmokeTestRunner
```

#### Run Specific Tags
```bash
mvn test -Dcucumber.filter.tags="@Positive"
mvn test -Dcucumber.filter.tags="@Negative"
mvn test -Dcucumber.filter.tags="@Smoke"
```

#### Run with Specific Browser
```bash
mvn test -Dbrowser=firefox
mvn test -Dbrowser=chrome
mvn test -Dbrowser=edge
```

#### Run in Headless Mode
```bash
mvn test -Dheadless=true
```

#### Generate Allure Reports
```bash
mvn clean test
mvn allure:serve
```

### Method 2: Using TestNG XML

#### Run from IDE:
1. Right-click on `testng.xml` or `testng-smoke.xml`
2. Select "Run as TestNG Suite"

#### Run from Command Line:
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
mvn test -DsuiteXmlFile=src/test/resources/testng-smoke.xml
```

### Method 3: Using IDE

#### Run Feature File:
1. Navigate to `src/test/resources/features/CategoryAdmin.feature`
2. Right-click → Run 'Feature: CategoryAdmin'

#### Run Specific Scenario:
1. Open feature file
2. Right-click on specific scenario
3. Select "Run 'Scenario: ...'"

#### Run Runner Class:
1. Navigate to `src/test/java/com/qforce/runners/TestRunner.java`
2. Right-click → Run 'TestRunner'

## 📊 Test Reports

### 1. Cucumber HTML Report
- Location: `test-output/cucumber-reports/cucumber-html-report.html`
- Open in browser to view detailed test results

### 2. ExtentReports
- Location: `test-output/ExtentReport.html`
- Beautiful, interactive HTML reports

### 3. Allure Reports
```bash
# Generate and view Allure report
mvn allure:serve
```

### 4. TestNG Reports
- Location: `test-output/index.html`

### 5. Screenshots
- Location: `screenshots/`
- Automatically captured for failed tests

## 📝 Test Case Mapping

## 🔧 Adding New Test Cases

### Step 1: Add Test Data (Data-Driven)

Edit `src/test/resources/testdata/CategoryTestData.xlsx`:
- Add new row with test data
- Include: Test ID, Category Name, Parent Category, Expected Result

### Step 2: Create Feature Scenario (BDD)

Edit `src/test/resources/features/CategoryAdmin.feature`:

```gherkin
@TC_NEW_TEST @YourTag
Scenario: Your test scenario description
  Given Admin is logged in and navigated to Add Category page
  When Admin performs some action
  Then Expected result should occur
```

### Step 3: Add Step Definitions (if needed)

Edit `src/test/java/com/qforce/stepdefinitions/CategoryAdminSteps.java`:

```java
@When("Admin performs some action")
public void admin_performs_some_action() {
    // Implementation
}
```

### Step 4: Update Page Objects (if needed)

Edit `src/main/java/com/qforce/pages/CategoryPage.java`:

```java
public void newAction() {
    // Implementation
}
```

## 🎨 Framework Features

### 1. **Page Object Model (POM)**
- Clean separation of page logic and test logic
- Reusable page methods
- Easy maintenance

### 2. **Data-Driven Testing**
- Test data in Excel files
- Easy to update test data without code changes
- Supports multiple test iterations

### 3. **Keyword-Driven Testing**
- Reusable keywords
- Non-technical users can write tests
- Flexible test creation

### 4. **BDD with Cucumber**
- Gherkin syntax for readable scenarios
- Bridges communication between technical and non-technical team
- Living documentation

### 5. **Automatic Driver Management**
- WebDriverManager handles browser drivers
- No manual driver downloads needed
- Automatic version compatibility

### 6. **Smart Waits**
- Implicit and explicit waits configured
- Wait utilities for common scenarios
- Reduced flakiness

### 7. **Screenshot Capture**
- Automatic screenshots for failed tests
- Embedded in reports
- Timestamped for easy tracking

### 8. **Multiple Report Formats**
- Cucumber HTML reports
- ExtentReports
- Allure reports
- TestNG reports

### 9. **Logging**
- Log4j2 integration
- Detailed logs for debugging
- Configurable log levels

### 10. **Parallel Execution Support**
- TestNG parallel execution
- Thread-safe WebDriver management
- Faster test execution

## 🐛 Troubleshooting

### Issue: Tests not running
**Solution:** 
- Verify Java and Maven installation
- Run `mvn clean install -DskipTests`
- Check configuration in `config.properties`

### Issue: Browser not launching
**Solution:**
- Update browser to latest version
- Check `browser` property in config
- Clear Maven cache: `mvn dependency:purge-local-repository`

### Issue: Element not found errors
**Solution:**
- Update locators in Page Objects
- Increase wait times in `config.properties`
- Check if application URL is correct

### Issue: Dependencies not downloading
**Solution:**
```bash
mvn clean install -U -DskipTests
```

## 📚 Best Practices

1. **Always update Page Objects** when UI changes
2. **Keep test data in Excel files** for easy maintenance
3. **Use meaningful test names** and IDs
4. **Add proper tags** to scenarios (@Smoke, @Regression, etc.)
5. **Review logs** after test execution
6. **Capture screenshots** for important steps
7. **Write independent tests** (no dependencies between tests)
8. **Use Page Object methods** instead of direct WebDriver calls
9. **Keep scenarios atomic** (one scenario = one test case)
10. **Regular framework updates** with latest dependencies

## 📞 Support

For questions or issues:
1. Check this README
2. Review log files in `logs/` directory
3. Check screenshots in `screenshots/` directory
4. Review test reports in `test-output/`

## 🎓 Learning Resources

- **Selenium**: https://www.selenium.dev/documentation/
- **Cucumber**: https://cucumber.io/docs/cucumber/
- **TestNG**: https://testng.org/doc/documentation-main.html
- **Maven**: https://maven.apache.org/guides/
- **Page Object Model**: https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/

## 📄 License

This framework is created for QA automation purposes.

---

**Happy Testing! 🚀**
