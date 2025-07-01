package stepdefs;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class CommonStepDefs {

    private Response response;
    private String requestBody;

    @When("I send a POST request to {string} with body:")
    public void iSendAPOSTRequestToWithBody(String endpoint, String body) {
        requestBody = body;
        response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post(endpoint);
    }

    @When("I send a GET request to {string}")
    public void iSendAGETRequestTo(String endpoint) {
        response = given()
                .header("Content-Type", "application/json")
                .when()
                .get(endpoint);
    }

    @When("I send a DELETE request to {string}")
    public void iSendADELETERequestTo(String endpoint) {
        response = given()
                .header("Content-Type", "application/json")
                .when()
                .delete(endpoint);
    }

    @When("I send a PATCH request to {string} with body:")
    public void iSendAPATCHRequestToWithBody(String endpoint, String body) {
        requestBody = body;
        response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .patch(endpoint);
    }

    @When("I send a PUT request to {string} with body:")
    public void iSendAPUTRequestToWithBody(String endpoint, String body) {
        requestBody = body;
        response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put(endpoint);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }


    public Response getResponse() {
        return response;
    }

    public String getRequestBody() {
        return requestBody;
    }
}
