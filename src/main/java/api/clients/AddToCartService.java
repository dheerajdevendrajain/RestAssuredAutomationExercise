package api.clients;

import api.models.AddToCartRequest;
import api.spec.RequestSpecFactory;
import context.ScenarioContext;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SerDeUtils;

import static io.restassured.RestAssured.given;

public class AddToCartService {
    private ScenarioContext scenarioContext;
    private static final Logger LOGGER = LoggerFactory.getLogger(AddToCartService.class);
    private static final String ADD_TO_CART_ENDPOINT = "api/ecom/user/add-to-cart";
    String Auth_token = "";


    public Response addProductToCart(AddToCartRequest addToCartRequest) {
        Auth_token = scenarioContext.get("token").toString();
        LOGGER.info("Attempting adding product to cart: {}", addToCartRequest);
        String jsonPayload = SerDeUtils.serializeToJson(addToCartRequest);
        Response response = given()
                .spec(RequestSpecFactory.getAuthRequestSpec(Auth_token))
                .header("Authorization", Auth_token)
                .body(jsonPayload)
                .when()
                .post(ADD_TO_CART_ENDPOINT);

        LOGGER.info("Add to Cart API response received with status code: {}", response.getStatusCode());
        return response;
    }
}
