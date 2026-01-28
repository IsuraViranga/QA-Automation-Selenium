package com.qforce.keywords;

import com.qforce.utils.ExcelReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Reads and manages test keywords from Excel files
 */
public class KeywordReader {
    
    private static final Logger logger = LogManager.getLogger(KeywordReader.class);
    
    /**
     * Read test steps for a specific test case
     */
    public static List<KeywordStep> getTestSteps(String filePath, String sheetName, String testId) {
        List<KeywordStep> steps = new ArrayList<>();
        List<Map<String, String>> allData = ExcelReader.getTestData(filePath, sheetName);
        
        for (Map<String, String> row : allData) {
            if (testId.equals(row.get("TestID"))) {
                KeywordStep step = new KeywordStep(
                    row.get("Keyword"),
                    row.get("Object"),
                    row.get("TestData"),
                    row.get("Expected")
                );
                steps.add(step);
            }
        }
        
        logger.info("Loaded {} test steps for test ID: {}", steps.size(), testId);
        return steps;
    }
    
    /**
     * Inner class to represent a keyword step
     */
    public static class KeywordStep {
        private String keyword;
        private String object;
        private String testData;
        private String expected;
        
        public KeywordStep(String keyword, String object, String testData, String expected) {
            this.keyword = keyword;
            this.object = object;
            this.testData = testData;
            this.expected = expected;
        }
        
        public String getKeyword() {
            return keyword;
        }
        
        public String getObject() {
            return object;
        }
        
        public String getTestData() {
            return testData;
        }
        
        public String getExpected() {
            return expected;
        }
        
        @Override
        public String toString() {
            return "KeywordStep{" +
                    "keyword='" + keyword + '\'' +
                    ", object='" + object + '\'' +
                    ", testData='" + testData + '\'' +
                    ", expected='" + expected + '\'' +
                    '}';
        }
    }
}
