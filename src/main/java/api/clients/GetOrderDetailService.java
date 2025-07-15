package api.clients;

import api.spec.RequestSpecFactory;
import context.ScenarioContext;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class GetOrderDetailService {
    private final ScenarioContext scenarioContext;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetProductFromOrderDetailsService.class);
    private static final String GET_ORDER_ENDPOINT = "api/ecom/order/get-orders-details";
    String Auth_token = "";

    public GetOrderDetailService(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    public Response getOrderDetails(String orderId) {
        Auth_token = scenarioContext.get("token").toString();
        LOGGER.info("Attempting to get order details for order ID: {}", orderId);

        // Assuming a method to make the API call exists, e.g., using RestAssured
        Response response = given()
                .spec(RequestSpecFactory.getAuthRequestSpec(Auth_token))
                .when()
                .get(GET_ORDER_ENDPOINT + "/?id=" + orderId)
                .then()
                .extract().response();

        LOGGER.info("Get Order Details API response received for order ID {}: {}", orderId, response);
        scenarioContext.setLatestResponse(response);
        return response;
    }
}
