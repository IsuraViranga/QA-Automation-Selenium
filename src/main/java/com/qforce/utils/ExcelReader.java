package com.qforce.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class to read test data from Excel files
 */
public class ExcelReader {
    
    private static final Logger logger = LogManager.getLogger(ExcelReader.class);
    
    /**
     * Read all data from a sheet and return as List of Maps
     * Each map represents a row with column name as key
     */
    public static List<Map<String, String>> getTestData(String filePath, String sheetName) {
        List<Map<String, String>> testData = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in file: " + filePath);
            }
            
            // Get header row
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Header row not found in sheet: " + sheetName);
            }
            
            // Get all column names
            List<String> columnNames = new ArrayList<>();
            for (Cell cell : headerRow) {
                columnNames.add(cell.getStringCellValue());
            }
            
            // Read data rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < columnNames.size(); j++) {
                    Cell cell = row.getCell(j);
                    String cellValue = getCellValueAsString(cell);
                    rowData.put(columnNames.get(j), cellValue);
                }
                testData.add(rowData);
            }
            
            logger.info("Successfully read {} rows from sheet '{}' in file '{}'", 
                       testData.size(), sheetName, filePath);
            
        } catch (IOException e) {
            logger.error("Error reading Excel file: {}", filePath, e);
            throw new RuntimeException("Failed to read Excel file: " + filePath, e);
        }
        
        return testData;
    }
    
    /**
     * Read data for a specific test case by matching Test Identifier
     */
    public static Map<String, String> getTestCaseData(String filePath, String sheetName, String testId) {
        List<Map<String, String>> allData = getTestData(filePath, sheetName);
        
        for (Map<String, String> row : allData) {
            if (testId.equals(row.get("Test Identifier"))) {
                logger.info("Found test data for Test ID: {}", testId);
                return row;
            }
        }
        
        logger.warn("No test data found for Test ID: {}", testId);
        return new HashMap<>();
    }
    
    /**
     * Convert cell value to string regardless of cell type
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }
}
