package com.qforce.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * API Configuration loader
 */
public class APIConfig {
    private static Properties properties;
    private static final String CONFIG_FILE = "src/test/resources/api.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load API configuration from " + CONFIG_FILE, e);
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("api.base.url");
    }

    public static String getBasePath() {
        return properties.getProperty("api.base.path");
    }

    public static String getCategoriesEndpoint() {
        return properties.getProperty("api.categories.endpoint");
    }

    public static String getCategoriesPageEndpoint() {
        return properties.getProperty("api.categories.page.endpoint");
    }

    public static String getAuthEndpoint() {
        return properties.getProperty("api.auth.endpoint");
    }

    public static String getUserUsername() {
        return properties.getProperty("api.user.username");
    }

    public static String getUserPassword() {
        return properties.getProperty("api.user.password");
    }

    public static String getAdminUsername() {
        return properties.getProperty("api.admin.username");
    }

    public static String getAdminPassword() {
        return properties.getProperty("api.admin.password");
    }

    public static String getFullUrl(String endpoint) {
        return getBaseUrl() + getBasePath() + endpoint;
    }
}
