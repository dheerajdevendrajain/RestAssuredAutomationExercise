package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.fail;

public class FrameworkJsonValidator { // Or JsonValidator
    private static final Logger LOGGER = LoggerFactory.getLogger(FrameworkJsonValidator.class);
    private static final JsonSchemaFactory FACTORY = JsonSchemaFactory.byDefault();

    private FrameworkJsonValidator() {
        // Private constructor
    }

    // ... (existing validateJsonAgainstSchema method) ...

    public static void validateJsonAgainstSchemaDetailed(String jsonString, String schemaPath) {
        try (InputStream schemaStream = Files.newInputStream(Paths.get("src/test/resources", schemaPath))) {
            // Use the new getter method
            JsonNode schemaNode = SerDeUtils.getJsonMapper().readTree(schemaStream);
            JsonNode dataNode = SerDeUtils.getJsonMapper().readTree(jsonString);

            com.github.fge.jsonschema.main.JsonValidator validator = FACTORY.getValidator();
            ProcessingReport report = validator.validate(schemaNode, dataNode);

            if (!report.isSuccess()) {
                LOGGER.error("Detailed JSON schema validation failed for {}: {}", schemaPath, report);
                fail("JSON schema validation failed: " + report.toString());
            } else {
                LOGGER.info("Detailed JSON response successfully validated against schema: {}", schemaPath);
            }
        } catch (IOException | ProcessingException e) {
            LOGGER.error("Error during detailed JSON schema validation: {}", e.getMessage());
            fail("Error during JSON schema validation: " + e.getMessage());
        }
    }
}