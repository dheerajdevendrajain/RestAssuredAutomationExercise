package stepdefs;

import api.clients.GetOrderDetailService;
import context.ScenarioContext;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.reporting.ReportHelper;

import java.util.List;

public class ViewOrderDetailSteps {
    private Response GetProductDetailsResponse;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetProductDetailsOrderSteps.class);
    private ScenarioContext scenarioContext;
    private GetOrderDetailService getOrderDetailService;

    public ViewOrderDetailSteps(ScenarioContext scenarioContext, GetOrderDetailService getOrderDetailService) {
        this.scenarioContext = scenarioContext;
        this.getOrderDetailService = getOrderDetailService;
    }

    @And("I view order details for the all orders")
    public void iViewOrderDetailsForTheAllOrders() {
        List<String> orderIds = (List<String>) scenarioContext.get("allOrderIds");
        for (String orderId : orderIds) {
            GetProductDetailsResponse = getOrderDetailService.getOrderDetails(orderId);
            scenarioContext.setLatestResponse(GetProductDetailsResponse);
            LOGGER.info("Get Order Details Response: {}", GetProductDetailsResponse.asString());
            ReportHelper.addRequestResponseToReport(null, GetProductDetailsResponse, "Get Order Details API");
        }

    }

//    @Then("the order details should match the expected product details")
//    public void theOrderDetailsShouldMatchTheExpectedProductDetails(DataTable dataTable) {
//        List<Map<String, String>> expectedOrderDetails = dataTable.asMaps();
//        if (GetProductDetailsResponse != null && GetProductDetailsResponse.getStatusCode() == 200) {
//            List<Map<String, String>> actualOrderDetails = GetProductDetailsResponse.jsonPath().getList("data");
//            Assert.assertEquals(expectedOrderDetails.size(), actualOrderDetails.size());
//            Assert.assertEquals(expectedOrderDetails.get(0).get("_id"), actualOrderDetails.get(0).get("_id"));
//            Assert.assertEquals(expectedOrderDetails.get(0).get("orderById"), actualOrderDetails.get(0).get("orderById"));
//            Assert.assertEquals(expectedOrderDetails.get(0).get("orderBy"), actualOrderDetails.get(0).get("orderBy"));
//            Assert.assertEquals(expectedOrderDetails.get(0).get("productOrderedId"), actualOrderDetails.get(0).get("productOrderedId"));
//            Assert.assertEquals(expectedOrderDetails.get(0).get("productName"), actualOrderDetails.get(0).get("productName"));
//            Assert.assertEquals(expectedOrderDetails.get(0).get("country"), actualOrderDetails.get(0).get("country"));
//            Assert.assertEquals(expectedOrderDetails.get(0).get("productDescription"), actualOrderDetails.get(0).get("productDescription"));
//            Assert.assertEquals(expectedOrderDetails.get(0).get("productImage"), actualOrderDetails.get(0).get("productImage"));
//            Assert.assertEquals(expectedOrderDetails.get(0).get("orderPrice"), actualOrderDetails.get(0).get("orderPrice"));
//            Assert.assertEquals(expectedOrderDetails.get(0).get("__v"), actualOrderDetails.get(0).get("__v"));
//        }
//    }
}
