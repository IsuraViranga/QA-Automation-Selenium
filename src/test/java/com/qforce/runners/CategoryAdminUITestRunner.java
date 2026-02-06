package com.qforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * TestNG Runner for Category Admin UI Tests
 */
@CucumberOptions(
        features = "src/test/resources/features/CategoryAdminUI.feature",
        glue = {"com.qforce.stepdefinitions", "com.qforce.hooks"},
        tags = "@CategoryAdminUI",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/category-admin-ui.html",
                "json:target/cucumber-reports/category-admin-ui.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true
)
public class CategoryAdminUITestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
