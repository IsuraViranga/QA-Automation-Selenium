package com.qforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Runner for Smoke Tests only
 */
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.qforce.stepdefinitions"},
    plugin = {
        "pretty",
        "html:test-output/cucumber-reports/smoke-report.html",
        "json:test-output/cucumber-reports/smoke.json",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    monochrome = true,
    tags = "@Smoke"
)
public class SmokeTestRunner extends AbstractTestNGCucumberTests {
    
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
