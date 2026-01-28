# 🎯 COMPLETE SETUP GUIDE - Step by Step

## Part 1: Environment Setup (One-Time Setup)

### Step 1: Install Java JDK

#### Windows:
1. Download JDK 11 or higher from: https://www.oracle.com/java/technologies/downloads/
2. Run the installer
3. Set JAVA_HOME environment variable:
   - Right-click "This PC" → Properties
   - Advanced system settings → Environment Variables
   - New System Variable:
     - Variable name: `JAVA_HOME`
     - Variable value: `C:\Program Files\Java\jdk-11` (your Java installation path)
   - Edit Path variable, add: `%JAVA_HOME%\bin`
4. Verify installation:
   ```cmd
   java -version
   javac -version
   ```

#### Mac:
```bash
# Install using Homebrew
brew install openjdk@11

# Set JAVA_HOME in ~/.zshrc or ~/.bash_profile
export JAVA_HOME=/usr/local/opt/openjdk@11
export PATH=$JAVA_HOME/bin:$PATH

# Verify
java -version
```

#### Linux:
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-11-jdk

# Set JAVA_HOME in ~/.bashrc
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

# Verify
java -version
```

---

### Step 2: Install Apache Maven

#### Windows:
1. Download Maven from: https://maven.apache.org/download.cgi
2. Extract to: `C:\Program Files\Apache\maven`
3. Set environment variables:
   - New System Variable:
     - Variable name: `MAVEN_HOME`
     - Variable value: `C:\Program Files\Apache\maven`
   - Edit Path variable, add: `%MAVEN_HOME%\bin`
4. Verify:
   ```cmd
   mvn -version
   ```

#### Mac:
```bash
# Install using Homebrew
brew install maven

# Verify
mvn -version
```

#### Linux:
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install maven

# Verify
mvn -version
```

---

### Step 3: Install Git (Optional but Recommended)

#### Windows:
1. Download from: https://git-scm.com/downloads
2. Run installer with default settings
3. Verify:
   ```cmd
   git --version
   ```

#### Mac:
```bash
brew install git
git --version
```

#### Linux:
```bash
sudo apt install git
git --version
```

---

### Step 4: Install IDE (Choose One)

#### Option A: IntelliJ IDEA (Recommended)
1. Download from: https://www.jetbrains.com/idea/download/
2. Install Community Edition (Free)
3. Install plugins:
   - Cucumber for Java
   - Gherkin
   - TestNG

#### Option B: Eclipse
1. Download from: https://www.eclipse.org/downloads/
2. Install Eclipse IDE for Java Developers
3. Install plugins:
   - TestNG: Help → Eclipse Marketplace → Search "TestNG"
   - Cucumber: Help → Eclipse Marketplace → Search "Cucumber"

#### Option C: VS Code
1. Download from: https://code.visualstudio.com/
2. Install extensions:
   - Extension Pack for Java
   - Maven for Java
   - Cucumber (Gherkin)
   - TestNG Runner

---

## Part 2: Project Setup

### Step 1: Get the Framework

#### Option A: Extract ZIP
1. Extract the `qa-automation-framework.zip` file
2. Place it in your workspace directory
   - Example: `C:\Users\YourName\workspace\qa-automation-framework`

#### Option B: Clone from Git (if applicable)
```bash
cd C:\Users\YourName\workspace
git clone <repository-url>
cd qa-automation-framework
```

---

### Step 2: Import Project in IDE

#### IntelliJ IDEA:
1. Open IntelliJ IDEA
2. Click: **File** → **Open**
3. Navigate to `qa-automation-framework` folder
4. Click **OK**
5. Wait for Maven to import (bottom right shows progress)
6. When prompted "Maven projects need to be imported", click **Import Changes**
7. Wait for all dependencies to download (5-10 minutes first time)

#### Eclipse:
1. Open Eclipse
2. Click: **File** → **Import**
3. Select: **Maven** → **Existing Maven Projects**
4. Click **Next**
5. Browse to `qa-automation-framework` folder
6. Click **Finish**
7. Wait for Maven dependencies to download

#### VS Code:
1. Open VS Code
2. Click: **File** → **Open Folder**
3. Select `qa-automation-framework` folder
4. VS Code will detect Maven project automatically
5. Wait for Java extension to activate

---

### Step 3: Configure the Framework

#### 3.1: Update Application Configuration

Open: `src/test/resources/config/config.properties`

```properties
# IMPORTANT: Update these values for your application
app.url=https://your-actual-application-url.com
app.admin.username=your-admin-email@example.com
app.admin.password=your-admin-password

# Browser settings
browser=chrome          # Options: chrome, firefox, edge
headless=false         # Set to true for CI/CD or background execution
browser.window.maximize=true

# Wait times (in seconds)
implicit.wait=10       # How long to wait for elements by default
explicit.wait=20       # How long to wait for specific conditions
page.load.timeout=30   # How long to wait for page to load

# Leave other settings as default for now
```

#### 3.2: Verify Project Structure

Ensure you have these key files:
```
qa-automation-framework/
├── pom.xml                    ✓ Maven configuration
├── README.md                  ✓ Documentation
├── src/
│   ├── main/java/            ✓ Framework code
│   └── test/
│       ├── java/             ✓ Test code
│       └── resources/
│           ├── features/     ✓ Cucumber features
│           ├── testdata/     ✓ Test data
│           └── config/       ✓ Configuration
```

---

### Step 4: Download Dependencies

Open terminal/command prompt in project root:

```bash
# Navigate to project directory
cd C:\Users\YourName\workspace\qa-automation-framework

# Download all dependencies
mvn clean install -DskipTests
```

**What this does:**
- `clean`: Removes old build files
- `install`: Downloads all required libraries
- `-DskipTests`: Skips running tests during installation

**Expected output:**
```
[INFO] BUILD SUCCESS
[INFO] Total time: 2:34 min
```

**If you see errors:**
```bash
# Try force update
mvn clean install -U -DskipTests

# If still fails, check:
# 1. Internet connection
# 2. Java installation: java -version
# 3. Maven installation: mvn -version
```

---

## Part 3: Update Locators for Your Application

### Step 1: Identify Your Page Elements

1. Open your application in Chrome
2. Right-click on Category Name field → Inspect
3. Find the element's:
   - **id** (best)
   - **name**
   - **class**
   - **xpath** (last resort)

Example:
```html
<input id="categoryName" name="category" class="form-control" />
```

### Step 2: Update Page Objects

Open: `src/main/java/com/qforce/pages/CategoryPage.java`

Find this section:
```java
// Locators for Category Page Elements
@FindBy(id = "categoryName")  // ← UPDATE THIS
private WebElement categoryNameField;

@FindBy(id = "parentCategory")  // ← UPDATE THIS
private WebElement parentCategoryDropdown;

@FindBy(id = "saveButton")  // ← UPDATE THIS
private WebElement saveButton;
```

**Replace with your actual locators:**
```java
// Example: If your element has id="cat_name"
@FindBy(id = "cat_name")
private WebElement categoryNameField;

// Example: If your element has name="parent"
@FindBy(name = "parent")
private WebElement parentCategoryDropdown;

// Example: If using xpath
@FindBy(xpath = "//button[@type='submit']")
private WebElement saveButton;
```

### Step 3: Update URLs

In `CategoryPage.java`, find:
```java
public void navigateToAddCategoryPage() {
    // UPDATE THIS URL PATH
    navigateTo(url.replace(url.substring(url.lastIndexOf("/")), "/categories/add"));
}
```

Replace `/categories/add` with your actual add category URL path.

---

## Part 4: Running Your First Test

### Method 1: Run from IDE (Easiest)

#### IntelliJ IDEA:
1. Right-click on `src/test/resources/features/CategoryAdmin.feature`
2. Select **Run 'Feature: Category Admin...'**
3. Watch browser open and test execute
4. Check console for results

#### Eclipse:
1. Right-click on `CategoryAdmin.feature`
2. Select **Run As** → **Cucumber Feature**

### Method 2: Run from Command Line

```bash
# Make sure you're in project root directory
cd C:\Users\YourName\workspace\qa-automation-framework

# Run all tests
mvn test

# Run only smoke tests
mvn test -Dtest=SmokeTestRunner

# Run specific scenario by tag
mvn test -Dcucumber.filter.tags="@TC_CAT_ADMIN_01"
```

### Method 3: Run TestNG Suite

#### From IDE:
1. Right-click on `src/test/resources/testng.xml`
2. Select **Run As** → **TestNG Suite**

#### From Command Line:
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

---

## Part 5: Understanding Test Results

### Where to Find Reports:

#### 1. Cucumber HTML Report
- **Location:** `test-output/cucumber-reports/cucumber-html-report.html`
- **How to view:** Double-click to open in browser
- **Shows:** Detailed step-by-step results with pass/fail status

#### 2. ExtentReports
- **Location:** `test-output/ExtentReport.html`
- **How to view:** Double-click to open in browser
- **Shows:** Beautiful interactive dashboard with charts

#### 3. TestNG Report
- **Location:** `test-output/index.html`
- **How to view:** Double-click to open in browser
- **Shows:** Traditional TestNG results

#### 4. Console Output
- **Location:** IDE console or terminal
- **Shows:** Real-time test execution logs

#### 5. Screenshots (for failed tests)
- **Location:** `screenshots/` folder
- **Naming:** `FAILED_TestName_timestamp.png`

#### 6. Logs
- **Location:** `logs/application.log`
- **Shows:** Detailed execution logs for debugging

---

## Part 6: Adding Your Test Cases

### Example: Adding Test Case TC_CAT_ADMIN_06

#### Step 1: Add to Feature File

Open: `src/test/resources/features/CategoryAdmin.feature`

Add at the end:
```gherkin
@TC_CAT_ADMIN_06 @YourNewTest
Scenario: Verify admin can edit existing category
  Given Admin is logged in and navigated to Category List page
  When Admin clicks edit button for category "Electronic"
  And Admin updates category name to "Consumer Electronic"
  And Admin clicks Update button
  Then Category should be updated successfully
  And Success message "Category updated successfully." should be displayed
```

#### Step 2: Add Step Definitions

Open: `src/test/java/com/qforce/stepdefinitions/CategoryAdminSteps.java`

Add new methods:
```java
@When("Admin clicks edit button for category {string}")
public void admin_clicks_edit_button(String categoryName) {
    categoryPage.clickEditForCategory(categoryName);
}

@When("Admin updates category name to {string}")
public void admin_updates_category_name(String newName) {
    categoryPage.enterCategoryName(newName);
}
```

#### Step 3: Add Page Object Methods

Open: `src/main/java/com/qforce/pages/CategoryPage.java`

Add new methods:
```java
public void clickEditForCategory(String categoryName) {
    // Find the category row and click edit
    for (WebElement row : categoryRows) {
        if (row.getText().contains(categoryName)) {
            WebElement editButton = row.findElement(By.cssSelector(".edit-button"));
            click(editButton);
            break;
        }
    }
}
```

#### Step 4: Run the New Test

```bash
mvn test -Dcucumber.filter.tags="@TC_CAT_ADMIN_06"
```

---

## Part 7: Scaling to Hundreds of Tests

### Strategy 1: Organize Feature Files by Module

```
src/test/resources/features/
├── category/
│   ├── CategoryCreate.feature
│   ├── CategoryEdit.feature
│   ├── CategoryDelete.feature
│   └── CategorySearch.feature
├── product/
│   ├── ProductCreate.feature
│   └── ProductEdit.feature
└── user/
    ├── UserLogin.feature
    └── UserPermissions.feature
```

### Strategy 2: Use Tags Effectively

```gherkin
@CategoryModule @Smoke @Positive
Scenario: Critical category creation

@CategoryModule @Regression @Negative
Scenario: Category validation tests

@ProductModule @Smoke
Scenario: Product creation
```

Run by tags:
```bash
# Run all smoke tests
mvn test -Dcucumber.filter.tags="@Smoke"

# Run specific module
mvn test -Dcucumber.filter.tags="@CategoryModule"

# Run combinations
mvn test -Dcucumber.filter.tags="@CategoryModule and @Positive"
```

### Strategy 3: Create Multiple TestNG Suites

Create `testng-regression.xml`:
```xml
<suite name="Regression Suite" parallel="tests" thread-count="3">
    <test name="Regression Tests">
        <classes>
            <class name="com.qforce.runners.RegressionTestRunner"/>
        </classes>
    </test>
</suite>
```

### Strategy 4: Use Data-Driven Approach

Update Excel with multiple rows:
```
Test ID          | Category Name | Parent      | Expected
TC_CAT_ADMIN_01  | Electronic   | Main        | Success
TC_CAT_ADMIN_07  | Clothing      | Main        | Success
TC_CAT_ADMIN_08  | Furniture     | Main        | Success
...              | ...           | ...         | ...
(100 more rows)
```

Use Scenario Outline:
```gherkin
Scenario Outline: Create multiple categories
  When Admin creates category "<categoryName>" with parent "<parent>"
  Then Category should be created successfully
  
  Examples:
  | categoryName | parent |
  | Electronic  | Main   |
  | Clothing     | Main   |
  | Furniture    | Main   |
```

---

## Part 8: Common Issues and Solutions

### Issue 1: "Element not found"
**Solution:**
- Update locators in Page Objects
- Increase wait time in config.properties
- Use `WaitUtil` methods

### Issue 2: "WebDriver executable not found"
**Solution:**
- Framework uses WebDriverManager (automatic)
- Ensure internet connection for first run
- Check if browser is installed

### Issue 3: "Tests are too slow"
**Solution:**
- Enable parallel execution in testng.xml
- Reduce unnecessary waits
- Use headless mode

### Issue 4: "Cannot find feature files"
**Solution:**
- Check paths in TestRunner
- Ensure features are in `src/test/resources/features/`
- Rebuild project: `mvn clean install`

### Issue 5: "Dependencies not downloading"
**Solution:**
```bash
mvn clean install -U -DskipTests
# or delete: ~/.m2/repository
# then: mvn clean install
```

---

## Part 9: Next Steps

### 1. Complete Page Object Updates
- [ ] Update all locators for your application
- [ ] Add missing page elements
- [ ] Test each page method individually

### 2. Create More Feature Files
- [ ] Map all your test cases to scenarios
- [ ] Organize by modules
- [ ] Add appropriate tags

### 3. Expand Test Data
- [ ] Add more test data rows in Excel
- [ ] Create separate Excel files for different modules
- [ ] Use data-driven scenarios

### 4. Set Up CI/CD
- [ ] Jenkins integration
- [ ] Schedule automated runs
- [ ] Email reports configuration

### 5. Advanced Features
- [ ] API testing with Rest Assured
- [ ] Database validation
- [ ] Cross-browser testing
- [ ] Mobile testing integration

---

## 🎓 Quick Reference Commands

```bash
# Basic Commands
mvn clean test                    # Run all tests
mvn test -Dtest=SmokeTestRunner   # Run smoke tests
mvn clean install -DskipTests     # Install dependencies

# Run by Tags
mvn test -Dcucumber.filter.tags="@Smoke"
mvn test -Dcucumber.filter.tags="@Regression"
mvn test -Dcucumber.filter.tags="@Positive"

# Browser Selection
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dheadless=true

# Reports
mvn allure:serve                  # View Allure reports
# Then open: test-output/index.html (TestNG)
# Then open: test-output/cucumber-reports/cucumber-html-report.html

# Troubleshooting
mvn clean install -U              # Force update dependencies
mvn dependency:tree               # Check dependency conflicts
```

---

## ✅ Checklist Before Running Tests

- [ ] Java installed and JAVA_HOME set
- [ ] Maven installed and in PATH
- [ ] IDE installed and project imported
- [ ] Dependencies downloaded (`mvn clean install`)
- [ ] config.properties updated with app URL and credentials
- [ ] Page Object locators updated for your app
- [ ] Browser (Chrome/Firefox/Edge) installed
- [ ] Internet connection available (first run)

---

## 📞 Need Help?

1. Check logs in `logs/application.log`
2. Review screenshots in `screenshots/` folder
3. Check test reports in `test-output/`
4. Verify locators using browser DevTools
5. Ensure config.properties has correct values

---

**You're all set! Happy Testing! 🚀**
