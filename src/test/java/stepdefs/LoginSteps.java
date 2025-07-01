package stepdefs;

import api.clients.LoginService;
import api.models.LoginRequest;
import context.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import utilities.reporting.ReportHelper;

public class LoginSteps {

    private Response loginResponse;
    private ScenarioContext scenarioContext;
    private LoginService loginService;
    public LoginRequest loginRequest;

    public LoginSteps(ScenarioContext scenarioContext, LoginService loginService) {
        this.scenarioContext = scenarioContext;
        this.loginService = loginService;
    }

    @Given("I login as a user with username {string} and password {string}")
    public void iLoginAsAUserWithUsernameAndPassword(String username, String password) {
        loginRequest = LoginRequest.builder()
                .userEmail(username)
                .userPassword(password)
                .build();

        loginResponse = loginService.loginUser(loginRequest);
        scenarioContext.setLatestResponse(loginResponse);
        ReportHelper.addRequestResponseToReport(new com.google.gson.Gson().toJsonTree(loginRequest).getAsJsonObject(), loginResponse, "Add To Cart API");
        System.out.println("Login Response: " + loginResponse.asString());
    }

    @And("Store the authentication token for future requests")
    public void storeTheAuthenticationTokenForFutureRequests() {
//        String authToken = loginService.extractAuthToken(loginResponse);
        String authToken = loginService.extractAuthToken(scenarioContext.getLatestResponse());
        scenarioContext.set("token", authToken);
        if (authToken != null) {
            System.out.println("Authentication token stored successfully: " + authToken);
        } else {
            System.out.println("Failed to extract authentication token from login response.");
        }

    }

    @And("Store the userId from response for future requests")
    public void storeTheUserIdFromResponseForFutureRequests() {
        String userId = loginService.extractUserId(scenarioContext.getLatestResponse());
        scenarioContext.set("userId", userId);
    }
}
