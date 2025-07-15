package stepdefs;

import api.clients.DeleteOrderService;
import context.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.reporting.ReportHelper;

import java.util.List;

public class DeleteOrderSteps {
    private Response DeleteOrderResponse;
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteOrderSteps.class);
    private ScenarioContext scenarioContext;
    private DeleteOrderService deleteOrderService;

    public DeleteOrderSteps(ScenarioContext scenarioContext, DeleteOrderService deleteOrderService) {
        this.scenarioContext = scenarioContext;
        this.deleteOrderService = deleteOrderService;
    }

    @Then("I delete the order with ID")
    public void iDeleteTheOrderWithID() {
        List<String> orderIds = (List<String>) scenarioContext.get("allOrderIds");
        for (String orderId : orderIds) {
            deleteOrderService = new DeleteOrderService(scenarioContext);
            deleteOrderService.deleteOrder(orderId);
            DeleteOrderResponse = scenarioContext.getLatestResponse();
            scenarioContext.setLatestResponse(DeleteOrderResponse);
            LOGGER.info("Delete Order Response: {}", DeleteOrderResponse.asString());
            ReportHelper.addRequestResponseToReport(null, DeleteOrderResponse, "Delete Order Details API");
        }


    }

    @And("I verify the order deletion response message is {string}")
    public void iVerifyTheOrderDeletionResponseMessageIs(String arg0) {
        if (DeleteOrderResponse != null && DeleteOrderResponse.getStatusCode() == 200) {
            String responseMessage = DeleteOrderResponse.jsonPath().getString("message");
            LOGGER.info("Response message: {}", responseMessage);
            if (!responseMessage.equals(arg0)) {
                LOGGER.error("Expected message: {}, but got: {}", arg0, responseMessage);
                throw new AssertionError("Order deletion message mismatch");
            }
        } else {
            LOGGER.error("Delete Order Response is null or not successful");
            throw new AssertionError("Delete Order Response is null or not successful");
        }
    }
}
