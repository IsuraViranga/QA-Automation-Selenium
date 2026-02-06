package com.qforce.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestNG Runner for Plant Admin Feature Tests
 */
@CucumberOptions(
    features = "src/test/resources/features/plants/PlantAdmin.feature",
    glue = {"com.qforce.stepdefinitions"},
    tags = "@PlantAdmin and @UI",
    plugin = {
        "pretty",
        "html:target/cucumber-reports/plant-admin-html",
        "json:target/cucumber-reports/plant-admin-json/Cucumber.json",
        "junit:target/cucumber-reports/plant-admin-xml/Cucumber.xml",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    monochrome = true,
    publish = false
)
public class PlantAdminTestRunner extends AbstractTestNGCucumberTests {
    
    // This class serves as the entry point for running Plant Admin Cucumber tests with TestNG
    // The @CucumberOptions annotation configures:
    // - features: Location of feature files
    // - glue: Package containing step definitions
    // - tags: Which scenarios to run (Plant Admin UI tests only)
    // - plugin: Report generation (HTML, JSON, JUnit XML, Allure)
    // - monochrome: Clean console output
    // - publish: Disable Cucumber Reports service
}