package com.qforce.utils;

import com.github.javafaker.Faker;
import java.util.Random;

/**
 * Utility class for generating test data
 */
public class TestDataGenerator {
    
    private static final Faker faker = new Faker();
    private static final Random random = new Random();
    
    /**
     * Generate random email
     */
    public static String generateEmail() {
        return faker.internet().emailAddress();
    }
    
    /**
     * Generate random name
     */
    public static String generateName() {
        return faker.name().fullName();
    }
    
    /**
     * Generate random category name (3-10 characters)
     */
    public static String generateCategoryName() {
        String[] categories = {
            "Electronic", "Clothing", "Books", "Toys", "Sports",
            "Furniture", "Beauty", "Jewelry", "Food", "Garden"
        };
        return categories[random.nextInt(categories.length)];
    }
    
    /**
     * Generate random string of specified length
     */
    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
    
    /**
     * Generate random number
     */
    public static int generateRandomNumber(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
    
    /**
     * Generate random phone number
     */
    public static String generatePhoneNumber() {
        return faker.phoneNumber().phoneNumber();
    }
    
    /**
     * Generate random address
     */
    public static String generateAddress() {
        return faker.address().fullAddress();
    }
    
    /**
     * Generate random company name
     */
    public static String generateCompanyName() {
        return faker.company().name();
    }
    
    /**
     * Generate random product name
     */
    public static String generateProductName() {
        return faker.commerce().productName();
    }
    
    /**
     * Generate random price
     */
    public static String generatePrice() {
        return faker.commerce().price();
    }
}
