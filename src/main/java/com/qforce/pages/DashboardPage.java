package com.qforce.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for Dashboard Page
 */
public class DashboardPage extends BasePage {
    
    // ========== LOCATORS ==========
    @FindBy(xpath = "//a[contains(@href,'/ui/categories')]")
    private WebElement categoriesTab;
    
    @FindBy(linkText = "Plants")
    private WebElement plantsTab;
    
    //@FindBy(xpath = "//a[contains(@class, 'active') and (contains(@href, '/categories') or contains(text(), 'Categories'))]")
    @FindBy(xpath = "//a[contains(@href,'/ui/categories') and contains(@class,'active')]")
    private WebElement activeCategoriesTab;
    
    @FindBy(xpath = "//a[contains(@class, 'active') and (contains(@href, '/plants') or contains(text(), 'Plants'))]")
    private WebElement activePlantsTab;
    
    @FindBy(xpath = "//nav//a[contains(@href, '/dashboard')]")
    private WebElement dashboardTab;
    
    // Alternative locators (update based on your actual application)
    @FindBy(css = "nav a[href*='categories']")
    private WebElement categoriesNavLink;
    
    @FindBy(css = "nav a[href*='plants']")
    private WebElement plantsNavLink;
    
    @FindBy(css = "nav a.active[href*='categories']")
    private WebElement activeCategoriesNavLink;
    
    @FindBy(css = "nav a.active[href*='plants']")
    private WebElement activePlantsNavLink;
    
    // By locators for dynamic elements
    private static final String CATEGORIES_TAB_XPATH = "//a[contains(@href, '/categories') or contains(text(), 'Categories')]";
    private static final String ACTIVE_TAB_XPATH = "//a[contains(@class, 'active')]";
    
    // ========== METHODS ==========
    
    /**
     * Check if user is on Dashboard page
     */
    public boolean isOnDashboardPage() {
        String currentUrl = getCurrentUrl();
        boolean onDashboard = currentUrl.contains("/dashboard");
        logger.info("On Dashboard page: {}", onDashboard);
        return onDashboard;
    }
    
    /**
     * Click on Categories tab in navigation menu
     */
    public void clickCategoriesTab() {
        logger.info("Clicking on Categories tab");
        
        try {
            // Try primary locator
            if (isDisplayed(categoriesTab)) {
                click(categoriesTab);
            } else if (isDisplayed(categoriesNavLink)) {
                // Try alternative locator
                click(categoriesNavLink);
            } else {
                // Try to find by text
                logger.warn("Categories tab not found with standard locators, trying text-based locator");
                navigateTo(getCurrentUrl().replaceAll("/[^/]*$", "") + "/categories");
            }
        } catch (Exception e) {
            logger.error("Failed to click Categories tab", e);
            throw new RuntimeException("Could not navigate to Categories page", e);
        }
    }
    
    /**
     * Click on Plants tab in navigation menu
     */
    public void clickPlantsTab() {
        logger.info("Clicking on Plants tab");
        
        try {
            // Try primary locator
            if (isDisplayed(plantsTab)) {
                click(plantsTab);
            } else if (isDisplayed(plantsNavLink)) {
                // Try alternative locator
                click(plantsNavLink);
            } else {
                // Try to find by text
                logger.warn("Plants tab not found with standard locators, trying text-based locator");
                navigateTo(getCurrentUrl().replaceAll("/[^/]*$", "") + "/plants");
            }
        } catch (Exception e) {
            logger.error("Failed to click Plants tab", e);
            throw new RuntimeException("Could not navigate to Plants page", e);
        }
    }
    
    /**
     * Check if Categories tab is active/highlighted
     */
    public boolean isCategoriesTabActive() {
        logger.info("Checking if Categories tab is active");
        
        try {
            // Method 1: Check for active class on Categories link
            if (isDisplayed(activeCategoriesTab)) {
                String className = activeCategoriesTab.getAttribute("class");
                if (className != null && className.contains("active")) {
                    logger.info("Categories tab is active (found active class)");
                    return true;
                }
            }
            
            // Method 2: Check alternative locator
            if (isDisplayed(activeCategoriesNavLink)) {
                String className = activeCategoriesNavLink.getAttribute("class");
                if (className != null && className.contains("active")) {
                    logger.info("Categories tab is active (found active nav link)");
                    return true;
                }
            }
            
            // Method 3: Check the main categories link directly
            if (isDisplayed(categoriesTab)) {
                String className = categoriesTab.getAttribute("class");
                logger.info("Categories tab class: {}", className);
                if (className != null && className.contains("active")) {
                    logger.info("Categories tab is active (found active in main link)");
                    return true;
                }
            }
            
            logger.info("Categories tab is NOT active (no 'active' class found)");
            return false;
            
        } catch (Exception e) {
            logger.error("Error checking if Categories tab is active", e);
            return false;
        }
    }
    
    /**
     * Get active tab text
     */
    public String getActiveTabText() {
        try {
            if (isDisplayed(activeCategoriesTab)) {
                return getText(activeCategoriesTab);
            }
            if (isDisplayed(activeCategoriesNavLink)) {
                return getText(activeCategoriesNavLink);
            }
            return "No active tab found";
        } catch (Exception e) {
            logger.error("Error getting active tab text", e);
            return "Error";
        }
    }
    
    /**
     * Navigate to Dashboard
     */
    public void navigateToDashboard() {
        logger.info("Navigating to Dashboard");
        String baseUrl = getCurrentUrl().replaceAll("/[^/]*$", "");
        navigateTo(baseUrl + "/dashboard");
    }
    
    /**
     * Navigate to Categories page directly
     */
    public void navigateToCategories() {
        logger.info("Navigating to Categories page directly");
        String baseUrl = getCurrentUrl().replaceAll("/[^/]*$", "");
        navigateTo(baseUrl + "/categories");
    }
    
    /**
     * Navigate to Plants page directly
     */
    public void navigateToPlants() {
        logger.info("Navigating to Plants page directly");
        String baseUrl = getCurrentUrl().replaceAll("/[^/]*$", "");
        navigateTo(baseUrl + "/plants");
    }
    
    /**
     * Check if navigation menu is displayed
     */
    public boolean isNavigationMenuDisplayed() {
        try {
            return categoriesTab != null && isDisplayed(categoriesTab);
        } catch (Exception e) {
            logger.error("Error checking navigation menu", e);
            return false;
        }
    }
}
