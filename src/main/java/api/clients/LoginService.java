package api.clients;


import api.models.LoginRequest;
import api.spec.RequestSpecFactory;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SerDeUtils;

import static auth.AuthTokenHolder.setToken;
import static io.restassured.RestAssured.given;

public class LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    private static final String LOGIN_ENDPOINT = "api/ecom/auth/login"; // Example endpoint

    public Response loginUser(LoginRequest loginRequest) {
        LOGGER.info("Attempting to login user: {}", loginRequest.getUserEmail());
        String jsonPayload = SerDeUtils.serializeToJson(loginRequest);
        Response response = given()
                .spec(RequestSpecFactory.getRequestSpec()) // Use basic spec without auth for login
                .body(jsonPayload) // Set the JSON request body
                .when()
                .post(LOGIN_ENDPOINT);

        LOGGER.info("Login API response received with status code: {}", response.getStatusCode());
        return response;
    }

    public String extractAuthToken(Response loginResponse) {
        loginResponse.then().statusCode(200);
        String token = null;
        if (loginResponse.jsonPath().get("access_token") != null) {
            token = loginResponse.jsonPath().getString("access_token");
        } else if (loginResponse.jsonPath().get("token") != null) {
            token = loginResponse.jsonPath().getString("token");
        } else {
            LOGGER.warn("Could not find 'access_token' or 'token' in login response: {}", loginResponse.asString());
        }

        if (token != null) {
            LOGGER.info("Successfully extracted authentication token.");
        }
        setToken(token);
        return token;
    }

    public String extractUserId(Response loginResponse) {
        loginResponse.then().statusCode(200);
        String userId = null;
        if (loginResponse.jsonPath().get("userId") != null) {
            userId = loginResponse.jsonPath().getString("userId");
        } else {
            LOGGER.warn("Could not find 'userId' in login response: {}", loginResponse.asString());
        }

        if (userId != null) {
            LOGGER.info("Successfully extracted authentication userId.");
        }
        setToken(userId);
        return userId;
    }
}

