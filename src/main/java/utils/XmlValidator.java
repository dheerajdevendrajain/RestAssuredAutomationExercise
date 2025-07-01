package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.fail;

public class XmlValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlValidator.class);

    private XmlValidator() {
        // Private constructor
    }

    /**
     * Validates an XML string against an XSD schema.
     *
     * @param xmlString The XML string to validate.
     * @param xsdPath   The path to the XSD schema file (e.g., "schemas/product_schema.xsd").
     */
    public static void validateXmlAgainstSchema(String xmlString, String xsdPath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Source schemaSource = new StreamSource(Files.newInputStream(Paths.get("src/test/resources", xsdPath)));
            Schema schema = factory.newSchema(schemaSource);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xmlString)));
            LOGGER.info("XML response successfully validated against schema: {}", xsdPath);
        } catch (SAXException e) {
            LOGGER.error("XML schema validation failed for {}: {}", xsdPath, e.getMessage());
            fail("XML schema validation failed: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.error("Error reading XSD schema file or XML content: {}", e.getMessage());
            fail("Error during XML schema validation: " + e.getMessage());
        }
    }
}