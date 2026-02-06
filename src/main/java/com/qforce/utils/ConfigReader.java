package com.qforce.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to read configuration properties
 */
public class ConfigReader {
    
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config/config.properties";
    
    static {
        loadProperties();
    }
    
    private static void loadProperties() {
        try {
            FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH);
            properties = new Properties();
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + CONFIG_FILE_PATH, e);
        }
    }
    
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in configuration file");
        }
        return value;
    }
    
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    public static String getAppUrl() {
        return getProperty("app.url");
    }
    
    public static String getBrowser() {
        return getProperty("browser");
    }
    
    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }
    
    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }
    
    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "20"));
    }
    
    public static String getAdminUsername() {
        return getProperty("app.admin.username");
    }
    
    public static String getAdminPassword() {
        return getProperty("app.admin.password");
    }

    public static String getUserUsername() {
        return properties.getProperty("app.user.username");
    }

    public static String getUserPassword() {
        return properties.getProperty("app.user.password");
    }
    
    public static String getTestDataPath() {
        return getProperty("test.data.path");
    }
    
    public static String getKeywordDataPath() {
        return getProperty("keyword.data.path");
    }

    public static String getApiBaseUrl() {
        return properties.getProperty("api.base.url");
    }

    public static String getApiBasePath() {
        return properties.getProperty("api.base.path");
    }

    public static String getApiUrl() {
        return getApiBaseUrl() + getApiBasePath();
    }
}
