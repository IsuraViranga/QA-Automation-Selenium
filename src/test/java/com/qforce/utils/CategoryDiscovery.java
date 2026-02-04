package com.qforce.utils;

import com.qforce.pages.LoginPage;
import com.qforce.pages.plants.PlantPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Utility to discover available categories in the system
 */
public class CategoryDiscovery {
    
    private static final Logger logger = LoggerFactory.getLogger(CategoryDiscovery.class);
    
    public static void main(String[] args) {
        try {
            // Initialize driver
            DriverManager.initializeDriver();
            
            // Login and navigate to plants page
            LoginPage loginPage = new LoginPage();
            loginPage.navigateToLoginPage("http://localhost:8080/ui/login");
            loginPage.login("testuser", "test123");
            
            // Navigate to plants page
            PlantPage plantPage = new PlantPage();
            plantPage.navigateToPlantsList();
            
            // Get available categories
            List<String> categories = plantPage.getAvailableCategories();
            
            System.out.println("=== AVAILABLE CATEGORIES ===");
            for (String category : categories) {
                System.out.println("- " + category);
            }
            System.out.println("=============================");
            
        } catch (Exception e) {
            logger.error("Error discovering categories", e);
        } finally {
            DriverManager.quitDriver();
        }
    }
}