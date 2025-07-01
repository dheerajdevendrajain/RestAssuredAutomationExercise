package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerDeUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SerDeUtils.class);
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT); // Pretty print JSON
    private static final XmlMapper XML_MAPPER = new XmlMapper();

    private SerDeUtils() {

    }
    public static ObjectMapper getJsonMapper() {
        return JSON_MAPPER;
    }

    public static String serializeToJson(Object obj) {
        try {
            return JSON_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to serialize object to JSON: {}", e.getMessage());
            throw new RuntimeException("JSON serialization failed", e);
        }
    }

    public static <T> T deserializeJson(String jsonString, Class<T> clazz) {
        try {
            return JSON_MAPPER.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to deserialize JSON string to object ({}): {}", clazz.getSimpleName(), e.getMessage());
            throw new RuntimeException("JSON deserialization failed", e);
        }
    }

    public static String serializeToXml(Object obj) {
        try {
            return XML_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to serialize object to XML: {}", e.getMessage());
            throw new RuntimeException("XML serialization failed", e);
        }
    }

    public static <T> T deserializeXml(String xmlString, Class<T> clazz) {
        try {
            return XML_MAPPER.readValue(xmlString, clazz);
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to deserialize XML string to object ({}): {}", clazz.getSimpleName(), e.getMessage());
            throw new RuntimeException("XML deserialization failed", e);
        }
    }
}