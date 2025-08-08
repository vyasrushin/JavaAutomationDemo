package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class TestDataUtil {
    public static Object[][] getTestData() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(new File("src/test/resources/testdata/testData.json"));
            Object[][] data = new Object[root.size()][6];
            for (int i = 0; i < root.size(); i++) {
                JsonNode record = root.get(i);
                data[i][0] = record.get("firstName").asText();
                data[i][1] = record.get("lastName").asText();
                data[i][2] = record.get("email").asText();
                data[i][3] = record.get("age").asText();
                data[i][4] = record.get("salary").asText();
                data[i][5] = record.get("department").asText();
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[0][0];
        }
    }
}