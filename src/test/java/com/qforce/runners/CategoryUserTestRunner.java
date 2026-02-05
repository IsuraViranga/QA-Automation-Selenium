package com.qforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * TestNG Runner for Category User Test Cases
 */
@CucumberOptions(
    features = "src/test/resources/features/CategoryUser.feature",
    glue = {
        "com.qforce.stepdefinitions"
    },
    plugin = {
        "pretty",
        "html:test-output/cucumber-reports/category-user-html",
        "json:test-output/cucumber-reports/category-user.json",
        "junit:test-output/cucumber-reports/category-user.xml",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    tags = "@CategoryUser",
    monochrome = true,
    dryRun = false
)
public class CategoryUserTestRunner extends AbstractTestNGCucumberTests {
    
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
