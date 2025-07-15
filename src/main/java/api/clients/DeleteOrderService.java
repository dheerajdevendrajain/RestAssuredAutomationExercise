package api.clients;

import api.spec.RequestSpecFactory;
import context.ScenarioContext;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class DeleteOrderService {
    private final ScenarioContext scenarioContext;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetProductFromOrderDetailsService.class);
    private static final String GET_ORDER_ENDPOINT = "api/ecom/order/get-orders-details";
    String Auth_token = "";
    private static final String DELETE_ORDER_ENDPOINT = "api/ecom/order/delete-order";

    public DeleteOrderService(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    public void deleteOrder(String orderId) {
        Auth_token = scenarioContext.get("token").toString();
        LOGGER.info("Attempting to delete order with ID: {}", orderId);

        // Assuming a method to make the API call exists, e.g., using RestAssured
        Response response = given()
                .spec(RequestSpecFactory.getAuthRequestSpec(Auth_token))
                .when()
                .delete(DELETE_ORDER_ENDPOINT + "/" + orderId)
                .then()
                .extract().response();

        LOGGER.info("Delete Order API response received for order ID {}: {}", orderId, response);
        scenarioContext.setLatestResponse(response);
    }
}
