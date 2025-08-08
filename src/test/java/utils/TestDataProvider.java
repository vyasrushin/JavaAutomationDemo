package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Reporter;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestDataProvider {

    private static Map<String, String> testDataMap = new HashMap<>();

    /**
     * Overloaded method for calls without testCaseName.
     */
    public static void loadData(String dataType, String fileName) {
        loadData(dataType, fileName, null);
    }

    /**
     * Loads data based on TestNG parameters (works for both with and without testCaseName)
     */
    public static void loadData(String dataType, String fileName, String testCaseName) {
        try {
            testDataMap.clear();

            if ("json".equalsIgnoreCase(dataType)) {
                loadJsonData(fileName, testCaseName);
            } else if ("excel".equalsIgnoreCase(dataType)) {
                loadExcelData(fileName, testCaseName);
            } else {
                throw new IllegalArgumentException("Invalid dataType parameter: " + dataType);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data: " + e.getMessage(), e);
        }
    }

    /**
     * Returns value for given key (case-insensitive).
     */
    public static String getTestData(String key) {
        if (key == null) return null;
        return testDataMap.getOrDefault(key, testDataMap.get(key.toLowerCase()));
    }

    /**
     * Load Key-Value data from JSON file for a specific test case or all cases
     */
    private static void loadJsonData(String fileName, String testCaseName) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/test/resources/testdata/" + fileName);
        JsonNode rootNode = mapper.readTree(file);

        if (testCaseName != null && !testCaseName.isEmpty()) {
            // Single test case load
            JsonNode tcNode = rootNode.get(testCaseName);
            if (tcNode != null && tcNode.isObject()) {
                Iterator<String> fieldNames = tcNode.fieldNames();
                while (fieldNames.hasNext()) {
                    String key = fieldNames.next();
                    testDataMap.put(key, tcNode.get(key).asText());
                }
            } else {
                throw new IllegalArgumentException("Test case not found in JSON: " + testCaseName);
            }
            Reporter.log("Loaded JSON test data for " + testCaseName + ": " + testDataMap, true);
        } else {
            // Load all data (flatten)
            Iterator<String> tcNames = rootNode.fieldNames();
            while (tcNames.hasNext()) {
                String tc = tcNames.next();
                JsonNode tcNode = rootNode.get(tc);
                if (tcNode != null && tcNode.isObject()) {
                    Iterator<String> fieldNames = tcNode.fieldNames();
                    while (fieldNames.hasNext()) {
                        String key = fieldNames.next();
                        testDataMap.put(tc + "." + key, tcNode.get(key).asText());
                    }
                }
            }
            Reporter.log("Loaded ALL JSON test data: " + testDataMap, true);
        }
    }

    /**
     * Load Key-Value or Multi-column Excel data
     */
    private static void loadExcelData(String fileName, String testCaseName) throws Exception {
        FileInputStream fis = new FileInputStream("src/test/resources/testdata/" + fileName);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        Row headerRow = sheet.getRow(0);
        int targetColumn = -1;

        if (testCaseName != null && !testCaseName.isEmpty()) {
            // Find the column index for the given test case name
            for (int i = 1; i < headerRow.getLastCellNum(); i++) {
                if (headerRow.getCell(i).getStringCellValue().equalsIgnoreCase(testCaseName)) {
                    targetColumn = i;
                    break;
                }
            }
            if (targetColumn == -1) {
                throw new IllegalArgumentException("Test case not found in Excel: " + testCaseName);
            }
            // Load single test case
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    String key = row.getCell(0).getStringCellValue();
                    Cell valueCell = row.getCell(targetColumn);
                    String value = valueCell != null ? valueCell.toString() : "";
                    testDataMap.put(key, value);
                }
            }
            Reporter.log("Loaded Excel test data for " + testCaseName + ": " + testDataMap, true);

        } else {
            // Load all test cases
            for (int col = 1; col < headerRow.getLastCellNum(); col++) {
                String tcName = headerRow.getCell(col).getStringCellValue();
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        String key = row.getCell(0).getStringCellValue();
                        Cell valueCell = row.getCell(col);
                        String value = valueCell != null ? valueCell.toString() : "";
                        testDataMap.put(tcName + "." + key, value);
                    }
                }
            }
            Reporter.log("Loaded ALL Excel test data: " + testDataMap, true);
        }

        workbook.close();
        fis.close();
    }
}