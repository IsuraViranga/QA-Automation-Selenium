package com.qforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * TestNG Runner for Cucumber tests
 */
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.qforce.stepdefinitions"},
    plugin = {
        "pretty",
        "html:test-output/cucumber-reports/cucumber-html-report.html",
        "json:test-output/cucumber-reports/cucumber.json",
        "junit:test-output/cucumber-reports/cucumber.xml",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    monochrome = true,
    dryRun = false,
    tags = "" // Run all scenarios by default, can be changed to "@Smoke" or "@Regression"
)
public class TestRunner extends AbstractTestNGCucumberTests {
    
    @Override
    @DataProvider(parallel = false) // Set to true for parallel execution
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
