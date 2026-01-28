# ⚡ QUICK START GUIDE

## 🎯 For Absolute Beginners - Get Running in 30 Minutes

### Prerequisites Check (5 minutes)
```bash
# Check if Java is installed
java -version
# Should show: java version "11" or higher

# Check if Maven is installed  
mvn -version
# Should show: Apache Maven 3.x.x

# If not installed, see SETUP_GUIDE.md Part 1
```

### Quick Setup (10 minutes)

1. **Extract the framework**
   - Unzip `qa-automation-framework.zip`
   - Place in: `C:\workspace\qa-automation-framework`

2. **Open in IDE**
   - IntelliJ IDEA: File → Open → Select folder
   - Wait for Maven import (status bar shows progress)

3. **Download dependencies**
   ```bash
   cd C:\workspace\qa-automation-framework
   mvn clean install -DskipTests
   ```
   Wait 2-3 minutes for first-time setup

4. **Update configuration**
   - Open: `src/test/resources/config/config.properties`
   - Change:
     ```properties
     app.url=https://your-app-url.com
     app.admin.username=your-email@example.com
     app.admin.password=your-password
     ```

### First Test Run (5 minutes)

**From IDE:**
1. Right-click: `src/test/resources/features/CategoryAdmin.feature`
2. Click: "Run 'Feature: CategoryAdmin'"
3. Watch: Browser opens, test executes
4. Check: Console shows results

**From Command Line:**
```bash
mvn test -Dcucumber.filter.tags="@TC_CAT_ADMIN_01"
```

### View Results (2 minutes)

1. Open in browser:
   - `test-output/cucumber-reports/cucumber-html-report.html`
   - `test-output/ExtentReport.html`

2. Check screenshots (if test failed):
   - `screenshots/` folder

### Common First-Time Issues

**Issue:** "Element not found"
→ **Fix:** Update locators in `src/main/java/com/qforce/pages/CategoryPage.java`

**Issue:** Browser doesn't open
→ **Fix:** Install Chrome, or change `browser=firefox` in config.properties

**Issue:** Dependencies won't download
→ **Fix:** Check internet connection, run: `mvn clean install -U -DskipTests`

### Next Steps

✅ Test ran successfully? Great! Now:
1. Read: `README.md` for detailed framework info
2. Follow: `SETUP_GUIDE.md` for adding your test cases
3. Update: Page Object locators for your application

### Quick Commands Reference

```bash
# Run all tests
mvn test

# Run smoke tests only
mvn test -Dtest=SmokeTestRunner

# Run specific test
mvn test -Dcucumber.filter.tags="@TC_CAT_ADMIN_01"

# Run with Firefox
mvn test -Dbrowser=firefox

# Run headless (no browser window)
mvn test -Dheadless=true

# View Allure reports
mvn allure:serve
```

### File Structure Quick Reference

```
Important Files to Know:
├── pom.xml                                    ← Maven dependencies
├── src/test/resources/
│   ├── config/config.properties              ← YOUR APP CONFIG
│   ├── features/CategoryAdmin.feature        ← TEST SCENARIOS  
│   └── testdata/CategoryTestData.xlsx        ← TEST DATA
├── src/main/java/com/qforce/pages/
│   ├── LoginPage.java                        ← UPDATE LOCATORS
│   └── CategoryPage.java                     ← UPDATE LOCATORS
└── src/test/java/com/qforce/
    ├── stepdefinitions/CategoryAdminSteps.java ← STEP CODE
    └── runners/TestRunner.java                 ← RUN TESTS
```

### Getting Help

1. Check: `logs/application.log` for detailed errors
2. Check: `screenshots/` for visual debugging
3. Read: Full `SETUP_GUIDE.md` for detailed instructions
4. Review: `README.md` for framework documentation

---

**Ready to automate? Let's go! 🚀**

Remember: Start small, test one scenario, then expand!
