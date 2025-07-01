package stepdefs;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;

public class CommonResponseValidationSteps {
    // This class can be used to define common response validation steps
    // For example, you can add methods to validate status codes, response bodies, etc.
    private Response response;

    // Setter to inject response from other stepdefs
    public void setResponse(Response response) {
        this.response = response;
    }

    @Then("the response body should contain {string} with value {string}")
    public void theResponseBodyShouldContainWithValue(String field, String expectedValue) {
        String actualValue = response.jsonPath().getString(field);
        assertEquals(expectedValue, actualValue);
    }
}