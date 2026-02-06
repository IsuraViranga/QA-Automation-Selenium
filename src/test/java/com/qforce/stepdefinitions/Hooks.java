package com.qforce.stepdefinitions;

import com.qforce.utils.DriverManager;
import io.cucumber.java.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * Cucumber Hooks for setup and teardown
 */
public class Hooks {
    
    private static final Logger logger = LogManager.getLogger(Hooks.class);

    /**
     * Detects whether the current scenario is an API test
     * by checking if the scenario has the @API tag
     */
    private boolean isApiTest(Scenario scenario) {
        return scenario.getSourceTagNames().contains("@API") || 
               scenario.getSourceTagNames().contains("@CategoryAPI");
    }
    
    @Before
    public void setUp(Scenario scenario) {
        logger.info("========== Starting Scenario: {} ==========", scenario.getName());

        // Skip WebDriver for API tests — they don't need a browser
        if (isApiTest(scenario)) {
            logger.info("API test detected — skipping WebDriver initialization");
            return;
        }

        DriverManager.initializeDriver();
    }
    
    @After
    public void tearDown(Scenario scenario) {
        logger.info("========== Finishing Scenario: {} - Status: {} ==========", 
                   scenario.getName(), scenario.getStatus());

        // Skip driver teardown entirely for API tests
        if (isApiTest(scenario)) {
            logger.info("API test detected — skipping WebDriver teardown");
            return;
        }
        
        // Quit driver
        DriverManager.quitDriver();
    }
    
    @BeforeStep
    public void beforeStep(Scenario scenario) {
        // Optional: Add any setup needed before each step
    }
    
    @AfterStep
    public void afterStep(Scenario scenario) {
        // Optional: Add any teardown needed after each step
    }
}
