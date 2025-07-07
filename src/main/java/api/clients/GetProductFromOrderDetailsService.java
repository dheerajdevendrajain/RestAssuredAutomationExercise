package api.clients;

import api.spec.RequestSpecFactory;
import context.ScenarioContext;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class GetProductFromOrderDetailsService {
    private final ScenarioContext scenarioContext;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetProductFromOrderDetailsService.class);
    private static final String GET_ORDER_ENDPOINT = "api/ecom/order/get-orders-for-customer";
    String Auth_token = "";

    public GetProductFromOrderDetailsService(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    public Response getProductFromOrderDetails(String userID) {
        Auth_token = scenarioContext.get("token").toString();
        LOGGER.info("Attempting to get product details from order for user: {}", userID);

        // Assuming a method to make the API call exists, e.g., using RestAssured
        Response response = given()
                .spec(RequestSpecFactory.getAuthRequestSpec(Auth_token))
                .when()
                .get(GET_ORDER_ENDPOINT + "/" + userID);

        LOGGER.info("Get Product from Order API response received with status code: {}", response.getStatusCode());

        // Store the response in the scenario context for further use
        scenarioContext.setLatestResponse(response);

        return response;
    }
}
