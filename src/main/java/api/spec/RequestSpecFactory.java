package api.spec;


import config.ConfigManager;
import context.ScenarioContext;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecFactory {

    private static RequestSpecification commonSpec;
    private String Auth_token;

    private RequestSpecFactory() {

    }

    public static RequestSpecification getRequestSpec() {
        if (commonSpec == null) {
            ConfigManager config = ConfigManager.getInstance();
            commonSpec = new RequestSpecBuilder()
                    .setBaseUri("https://rahulshettyacademy.com")
                    .setContentType(ContentType.JSON) // Default to JSON, can be overridden
                    .addHeader("Accept", ContentType.JSON.toString())
                    .log(LogDetail.ALL) // Log all request details
                    .build();
        }
        return commonSpec;
    }

    public static RequestSpecification getAuthRequestSpec(String Auth_token) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getRequestSpec())
                .addHeader("Authorization", Auth_token)
                .build();
    }

    public static RequestSpecification getBasicAuthRequestSpec(String username, String password) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getRequestSpec()).setAuth(io.restassured.RestAssured.basic(username, password))
                .build();
    }

    public static RequestSpecification getApiKeyRequestSpec(String apiKeyHeaderName, String apiKeyValue) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getRequestSpec())
                .addHeader(apiKeyHeaderName, apiKeyValue)
                .build();
    }
}