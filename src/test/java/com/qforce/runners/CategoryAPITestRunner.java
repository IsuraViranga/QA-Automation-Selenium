package com.qforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * TestNG runner for Category API tests
 */
@CucumberOptions(
        features = "src/test/resources/features/CategoryAPI.feature",
        glue = {"com.qforce.stepdefinitions"},
        tags = "@CategoryAPI",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/category-api.html",
                "json:target/cucumber-reports/category-api.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true,
        dryRun = false
)
public class CategoryAPITestRunner extends AbstractTestNGCucumberTests {
    
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
