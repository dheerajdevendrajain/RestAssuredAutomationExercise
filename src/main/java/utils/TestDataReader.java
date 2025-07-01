package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Utility class for reading various types of test data from files.
 * Supports .properties, .json, and .csv file formats.
 */
public class TestDataReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataReader.class);

    // Private constructor to prevent instantiation of utility class
    private TestDataReader() {
    }

    /**
     * Reads properties from a .properties file.
     *
     * @param filePath The path to the properties file (e.g., "src/test/resources/data/test_data.properties").
     * The path should be relative to the project root or an absolute path.
     * @return A Properties object containing the data.
     * @throws RuntimeException if an IOException occurs during file reading.
     */
    public static Properties readPropertiesFile(String filePath) {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(filePath)) {
            properties.load(input);
            LOGGER.info("Successfully read properties from: {}", filePath);
        } catch (IOException e) {
            LOGGER.error("Error reading properties file {}: {}", filePath, e.getMessage());
            throw new RuntimeException("Failed to read properties file: " + filePath, e);
        }
        return properties;
    }

    /**
     * Reads JSON data from a file into a Map.
     * This is useful for flexible JSON structures where a fixed POJO might not be practical.
     *
     * @param filePath The path to the JSON file (e.g., "src/test/resources/data/user_data.json").
     * @return A Map representing the JSON content.
     * @throws RuntimeException if an IOException occurs during file reading or JSON parsing.
     */
    public static Map<String, Object> readJsonToMap(String filePath) {
        try {
            // Using SerDeUtils's shared ObjectMapper for consistency
            Map<String, Object> data = SerDeUtils.getJsonMapper().readValue(Files.readString(Paths.get(filePath)), Map.class);
            LOGGER.info("Successfully read JSON to Map from: {}", filePath);
            return data;
        } catch (IOException e) {
            LOGGER.error("Error reading JSON file {} into Map: {}", filePath, e.getMessage());
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }

    /**
     * Reads JSON data from a file into a specific POJO (Plain Old Java Object) class.
     * This requires that your POJO class has fields matching the JSON structure
     * and appropriate getters/setters or Lombok annotations.
     *
     * @param filePath The path to the JSON file.
     * @param clazz    The class type to deserialize the JSON into.
     * @param <T>      The generic type of the POJO.
     * @return An instance of the specified class populated with JSON data.
     * @throws RuntimeException if an IOException occurs during file reading or JSON parsing.
     */
    public static <T> T readJsonToPojo(String filePath, Class<T> clazz) {
        try {
            T data = SerDeUtils.getJsonMapper().readValue(Files.readString(Paths.get(filePath)), clazz);
            LOGGER.info("Successfully read JSON to POJO ({}) from: {}", clazz.getSimpleName(), filePath);
            return data;
        } catch (IOException e) {
            LOGGER.error("Error reading JSON file {} into POJO {}: {}", filePath, clazz.getSimpleName(), e.getMessage());
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }

    /**
     * Reads CSV data from a file into a list of maps.
     * Each map represents a row, and keys are derived from the header row (first line of CSV).
     * This method assumes a simple comma-separated format without complex escaping.
     * For more robust CSV parsing, consider adding a library like `opencsv`.
     *
     * @param filePath The path to the CSV file (e.g., "src/test/resources/data/users.csv").
     * @return A List of Maps, where each map corresponds to a row of data, and keys are header names.
     * @throws RuntimeException if an IOException occurs during file reading.
     */
    public static List<Map<String, String>> readCsvToMapList(String filePath) {
        List<Map<String, String>> records = new ArrayList<>();
        String line;
        String[] headers = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read header row
            if ((line = br.readLine()) != null) {
                headers = line.split(","); // Basic comma split
                // Trim headers to remove potential whitespace
                for (int i = 0; i < headers.length; i++) {
                    headers[i] = headers[i].trim();
                }
                LOGGER.debug("CSV Headers: {}", Arrays.toString(headers));
            } else {
                LOGGER.warn("CSV file {} is empty or has no header.", filePath);
                return records; // Return empty list if file is empty
            }

            // Read data rows
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (headers != null && values.length == headers.length) {
                    Map<String, String> record = new LinkedHashMap<>(); // Use LinkedHashMap to preserve order
                    for (int i = 0; i < headers.length; i++) {
                        record.put(headers[i], values[i].trim()); // Trim values as well
                    }
                    records.add(record);
                } else {
                    LOGGER.warn("Skipping malformed row in {}: Values count ({}) does not match header count ({}) -> {}",
                            filePath, values.length, headers != null ? headers.length : "N/A", line);
                }
            }
            LOGGER.info("Successfully read CSV to List<Map> from: {}", filePath);

        } catch (IOException e) {
            LOGGER.error("Error reading CSV file {}: {}", filePath, e.getMessage());
            throw new RuntimeException("Failed to read CSV file: " + filePath, e);
        }
        return records;
    }

    /**
     * Reads a simple text file line by line into a list of strings.
     *
     * @param filePath The path to the text file.
     * @return A List of strings, where each string is a line from the file.
     * @throws RuntimeException if an IOException occurs during file reading.
     */
    public static List<String> readTextFile(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            LOGGER.info("Successfully read text file from: {}", filePath);
            return lines;
        } catch (IOException e) {
            LOGGER.error("Error reading text file {}: {}", filePath, e.getMessage());
            throw new RuntimeException("Failed to read text file: " + filePath, e);
        }
    }
}