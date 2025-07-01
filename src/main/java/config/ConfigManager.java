package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.jasypt.util.text.BasicTextEncryptor;


public class ConfigManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigManager.class);
    private static ConfigManager instance;
    private Properties properties;
    private String environment;
    private BasicTextEncryptor textEncryptor; // For decryption

    private ConfigManager() {
        properties = new Properties();
        setupJasypt(); // Initialize Jasypt
        loadEnvironment();
        loadProperties();
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void setupJasypt() {
        textEncryptor = new BasicTextEncryptor();
        String encryptionPassword = System.getenv("JASYPT_ENCRYPTION_PASSWORD"); // Get from environment variable
        if (encryptionPassword == null || encryptionPassword.isEmpty()) {
            LOGGER.error("JASYPT_ENCRYPTION_PASSWORD environment variable is not set. Encrypted properties will not be decrypted.");
            // In a real scenario, you might want to throw an exception or handle this more gracefully.
        } else {
            textEncryptor.setPassword(encryptionPassword);
        }
    }

    private void loadEnvironment() {
        environment = System.getProperty("env", "qa").toLowerCase();
        LOGGER.info("Loading configurations for environment: {}", environment.toUpperCase());
    }

    private void loadProperties() {
        String configFilePath = "src/test/resources/config/" + environment + ".properties";
        String secretsFilePath = "src/test/resources/secrets/secrets.properties.enc";

        try (FileInputStream fis = new FileInputStream(configFilePath)) {
            properties.load(fis);
            LOGGER.info("Loaded properties from: {}", configFilePath);
        } catch (IOException e) {
            LOGGER.error("Failed to load properties from {}. Error: {}", configFilePath, e.getMessage());
            throw new RuntimeException("Could not load environment properties: " + configFilePath, e);
        }

        // Load encrypted secrets
        try (FileInputStream fis = new FileInputStream(secretsFilePath)) {
            Properties encryptedProperties = new Properties();
            encryptedProperties.load(fis);
            LOGGER.info("Loaded encrypted secrets from: {}", secretsFilePath);

            // Decrypt and add to main properties
            encryptedProperties.forEach((key, value) -> {
                String decryptedValue = value.toString();
                if (textEncryptor != null && decryptedValue.startsWith("ENC(")) {
                    try {
                        decryptedValue = textEncryptor.decrypt(decryptedValue.substring(4, decryptedValue.length() - 1));
                    } catch (Exception e) {
                        LOGGER.error("Failed to decrypt property '{}'. Ensure JASYPT_ENCRYPTION_PASSWORD is correct. Error: {}", key, e.getMessage());
                        // Depending on your policy, you might throw an error here.
                    }
                }
                properties.setProperty(key.toString(), decryptedValue);
            });

        } catch (IOException e) {
            LOGGER.warn("Secrets file {} not found or could not be loaded. Error: {}", secretsFilePath, e.getMessage());
            // It's okay if secrets.properties.enc doesn't exist for some environments, so warn instead of error.
        }
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            LOGGER.warn("Property '{}' not found in configuration.", key);
        }
        return value;
    }
}