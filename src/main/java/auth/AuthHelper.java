package auth;

import config.ConfigManager;
import config.FrameworkConfig;
import context.ScenarioContext;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AuthHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthHelper.class);
    private static String accessToken; // Cache token
    private static long tokenExpiryTime; // Store expiry time

    private AuthHelper() {
        // Private constructor
    }

    public static String getAuthToken() {
        if (accessToken == null || isTokenExpired()) {
            LOGGER.info("Access token is null or expired. Requesting new token...");
            generateNewToken();
        }
        return accessToken;
    }

    private static boolean isTokenExpired() {
        return System.currentTimeMillis() >= tokenExpiryTime;
    }

    private static synchronized void generateNewToken() {
        if (accessToken != null && !isTokenExpired()) {
            // Another thread might have already refreshed the token
            LOGGER.info("Access token is still valid. No need to generate a new one.");
            return;
        }

        ConfigManager config = ConfigManager.getInstance();
        String authType = config.getProperty(FrameworkConfig.AUTH_TYPE);

        switch (authType.toLowerCase()) {
            case "No auth":
                LOGGER.info("No authentication selected. No token will be generated.");
                accessToken = null;
                tokenExpiryTime = 0;
                break;
            case "oauth2":
                requestOAuth2Token(config);
                break;
            case "basic":
                // For basic auth, typically you don't generate a "token" but use credentials directly
                // If you need a session token, implement it here
                LOGGER.info("Basic authentication selected. No token generation needed for basic auth.");
                break;
            case "apikey":
                // API Key is usually passed as a header/query param directly.
                LOGGER.info("API Key authentication selected. No token generation needed for API Key.");
                break;
            default:
                LOGGER.warn("Unsupported authentication type: {}. No token will be generated.", authType);
                accessToken = null;
                tokenExpiryTime = 0;
                break;
        }

        if (accessToken == null) {
            throw new RuntimeException("Failed to generate authentication token for type: " + authType);
        }
        LOGGER.info("New access token generated successfully.");
    }

    private static void requestOAuth2Token(ConfigManager config) {
        String tokenEndpoint = config.getProperty(FrameworkConfig.BASE_URL) + config.getProperty(FrameworkConfig.AUTH_TOKEN_ENDPOINT);
        String clientId = config.getProperty(FrameworkConfig.AUTH_CLIENT_ID);
        String clientSecret = config.getProperty(FrameworkConfig.AUTH_CLIENT_SECRET);

        Map<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", "client_credentials"); // Or "password", "authorization_code" etc.
        formParams.put("client_id", clientId);
        formParams.put("client_secret", clientSecret);

        Response response = RestAssured.given()
                .log().all()
                .formParams(formParams)
                .when()
                .post(tokenEndpoint)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        accessToken = response.jsonPath().getString("access_token");
        long expiresIn = response.jsonPath().getLong("expires_in"); // typically seconds
        tokenExpiryTime = System.currentTimeMillis() + (expiresIn * 1000) - 5000; // 5 seconds buffer

        if (accessToken == null || accessToken.isEmpty()) {
            throw new RuntimeException("OAuth2 token generation failed: access_token not found in response.");
        }
    }
}