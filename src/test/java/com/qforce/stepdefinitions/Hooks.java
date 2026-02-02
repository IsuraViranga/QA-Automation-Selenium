package com.qforce.stepdefinitions;

import com.qforce.utils.DriverManager;
import com.qforce.utils.ScreenshotUtil;
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
     * by checking if the scenario name contains "/api/"
     */
    private boolean isApiTest(Scenario scenario) {
        return scenario.getName().contains("/api/");
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
        
        // Take screenshot if scenario failed
        if (scenario.isFailed()) {
            try {
                WebDriver driver = DriverManager.getDriver();
                String screenshotPath = ScreenshotUtil.captureFailureScreenshot(driver, scenario.getName());
                
                // Attach screenshot to Cucumber report
                byte[] screenshot = ScreenshotUtil.getBase64Screenshot(driver).getBytes();
                scenario.attach(screenshot, "image/png", scenario.getName());
                
                logger.error("Scenario FAILED: {}. Screenshot saved at: {}", 
                           scenario.getName(), screenshotPath);
            } catch (Exception e) {
                logger.error("Failed to capture screenshot for failed scenario", e);
            }
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
        // Optional: Take screenshot after each step
        // Uncomment if needed for detailed debugging
        /*
        if (scenario.isFailed()) {
            try {
                WebDriver driver = DriverManager.getDriver();
                byte[] screenshot = ScreenshotUtil.getBase64Screenshot(driver).getBytes();
                scenario.attach(screenshot, "image/png", "step_screenshot");
            } catch (Exception e) {
                logger.error("Failed to capture step screenshot", e);
            }
        }
        */
    }
}
