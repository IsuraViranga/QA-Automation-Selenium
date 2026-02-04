package com.qforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * TestNG Runner for Plant User Test Cases
 */
@CucumberOptions(
    features = "src/test/resources/features/plants/PlantUser.feature",
    glue = {
        "com.qforce.stepdefinitions.plants",
        "com.qforce.stepdefinitions"
    },
    plugin = {
        "pretty",
        "html:test-output/cucumber-reports/plant-user-html",
        "json:test-output/cucumber-reports/plant-user.json",
        "junit:test-output/cucumber-reports/plant-user.xml",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    tags = "@TC_PLT_USER_01 or @TC_PLT_USER_02 or @TC_PLT_USER_03 or @TC_PLT_USER_04 or @TC_PLT_USER_05",
    monochrome = true,
    dryRun = false
)
public class PlantUserTestRunner extends AbstractTestNGCucumberTests {
    
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}