package api.clients;

import api.models.CreateOrderRequest;
import api.spec.RequestSpecFactory;
import context.ScenarioContext;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SerDeUtils;

import static io.restassured.RestAssured.given;

public class CreateOrderService {
    private final ScenarioContext scenarioContext;
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateOrderService.class);
    private static final String CREATE_ORDER_ENDPOINT = "api/ecom/order/create-order";
    String Auth_token = "";

    public CreateOrderService(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    public Response createOrder(CreateOrderRequest createOrderRequest) {
        Auth_token = scenarioContext.get("token").toString();
        String jsonPayload = SerDeUtils.serializeToJson(createOrderRequest);
        Response response = given()
                .spec(RequestSpecFactory.getAuthRequestSpec(Auth_token))
                .body(jsonPayload)
                .when()
                .post(CREATE_ORDER_ENDPOINT);

        LOGGER.info("Create Order API response received with status code: {}", response.getStatusCode());
        return response;
    }
}
