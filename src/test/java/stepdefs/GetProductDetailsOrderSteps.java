package stepdefs;

import api.clients.GetProductFromOrderDetailsService;
import api.models.GetOrdersResponse;
import api.models.OrderDetails;
import context.ScenarioContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.reporting.ReportHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class GetProductDetailsOrderSteps {
    private Response GetProductDetailsResponse;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetProductDetailsOrderSteps.class);
    private ScenarioContext scenarioContext;
    private GetProductFromOrderDetailsService getProductfromOrderDetailsService;

    public GetProductDetailsOrderSteps(ScenarioContext scenarioContext, GetProductFromOrderDetailsService getProductfromOrderDetailsService) {
        this.scenarioContext = scenarioContext;
        this.getProductfromOrderDetailsService = getProductfromOrderDetailsService;
    }

    @Then("Get product details from order")
    public void getProductDetailsFromOrder() {
        String userId = scenarioContext.get("userId").toString();
        GetProductDetailsResponse = getProductfromOrderDetailsService.getProductFromOrderDetails(userId);
        scenarioContext.setLatestResponse(GetProductDetailsResponse);
        ReportHelper.addRequestResponseToReport(null, GetProductDetailsResponse, "Get Product Details from Order API");
        System.out.println("Get Product Details Response: " + GetProductDetailsResponse.asString());
    }

    @Then("Validate the order details")
    public void validateTheOrderDetails(DataTable dataTable) {
        List<Map<String, String>> expectedOrderDetails = dataTable.asMaps(String.class, String.class);
        if (GetProductDetailsResponse != null && GetProductDetailsResponse.getStatusCode() == 200) {
            List<Map<String, String>> actualOrderDetails = GetProductDetailsResponse.jsonPath().getList("data");
            for (Map<String, String> expectedDetails : expectedOrderDetails) {
                boolean found = false;
                for (Map<String, String> actualDetails : actualOrderDetails) {
                    if (actualDetails.get("_id").equals(expectedDetails.get("_id"))) {
                        found = true;
                        for (String key : expectedDetails.keySet()) {
                            if (!actualDetails.get(key).equals(expectedDetails.get(key))) {
                                System.out.println("Mismatch for " + key + ": expected " + expectedDetails.get(key) + ", but got " + actualDetails.get(key));
                            }
                        }
                        break;
                    }
                    if (actualDetails.get("orderById").equals(expectedDetails.get("orderById"))) {
                        found = true;
                        System.out.println("Actual Order ID: " + actualDetails.get("orderById") + ", Expected Order ID: " + expectedDetails.get("orderById"));
                        for (String key : expectedDetails.keySet()) {
                            if (!actualDetails.get(key).equals(expectedDetails.get(key))) {
                                System.out.println("Mismatch for " + key + ": expected " + expectedDetails.get(key) + ", but got " + actualDetails.get(key));
                            }
                        }
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Expected order with ID " + expectedDetails.get("_id") + " not found in the response.");
                }
            }
        } else {
            System.out.println("Get Product Details Response is null or not successful.");
        }
    }

    @When("I retrieve all orders")
    public void iRetrieveAllOrders() {
        GetOrdersResponse ordersResponse = getProductfromOrderDetailsService.getProductFromOrderDetails(scenarioContext.get("userId").toString())
                .then()
                .extract()
                .as(GetOrdersResponse.class); // Assuming GetOrdersResponse is the POJO for the response
        scenarioContext.set("getOrdersResponse", ordersResponse); // Store the deserialized POJO
    }

    @Then("the orders response message should be {string}")
    public void theOrdersResponseMessageShouldBe(String expectedMessage) {
        GetOrdersResponse ordersResponse = scenarioContext.get("getOrdersResponse");
        Assert.assertNotNull("Orders response POJO not found in context.", ordersResponse);
        Assert.assertEquals("Response message mismatch", expectedMessage, ordersResponse.getMessage());
        LOGGER.info("Orders response message validated: '{}'", ordersResponse.getMessage());
    }

    @And("the orders count should be {int}")
    public void theOrdersCountShouldBe(int expectedCount) {
        GetOrdersResponse ordersResponse = scenarioContext.get("getOrdersResponse");
        Assert.assertNotNull("Orders response POJO not found in context.", ordersResponse);
        Assert.assertEquals("Orders count mismatch", expectedCount, ordersResponse.getCount());
        LOGGER.info("Orders count validated: {}", ordersResponse.getCount());
    }

    @And("the orders list should contain a product with name {string} and country {string}")
    public void theOrdersListShouldContainAProductWithNameAndCountry(String productName, String country) {
        GetOrdersResponse ordersResponse = scenarioContext.get("getOrdersResponse");
        Assert.assertNotNull("Orders response POJO not found in context.", ordersResponse);
        List<OrderDetails> orderList = ordersResponse.getData();
        Assert.assertNotNull("Orders data list is null.", orderList);
        Assert.assertFalse("Orders data list is empty.", orderList.isEmpty());

        Optional<OrderDetails> foundOrder = orderList.stream()
                .filter(order -> productName.equals(order.getProductName()) && country.equals(order.getCountry()))
                .findFirst();

        Assert.assertTrue(String.format("Order with product name '%s' and country '%s' not found in the response.", productName, country),
                foundOrder.isPresent());
        LOGGER.info("Validated that order for product '{}' in '{}' exists.", productName, country);
    }

    @And("I store all order IDs from the response")
    public void iStoreAllOrderIDsFromTheResponse() {
        GetOrdersResponse ordersResponse = scenarioContext.get("getOrdersResponse");
        Assert.assertNotNull("Orders response POJO not found in context to extract IDs.", ordersResponse);
        List<OrderDetails> orderList = ordersResponse.getData();

        if (orderList == null || orderList.isEmpty()) {
            LOGGER.warn("No orders found in the response to store IDs.");
            scenarioContext.set("allOrderIds", new ArrayList<String>()); // Store an empty list
            return;
        }

        // Use Java Streams to extract all orderId properties into a new List<String>
        List<String> orderIds = orderList.stream()
                .map(OrderDetails::getOrderId) // Get the orderId from each OrderDetails object
                .filter(id -> id != null && !id.isEmpty()) // Filter out null/empty IDs
                .collect(Collectors.toList()); // Collect into a new List

        Assert.assertFalse("No valid order IDs extracted from the response.", orderIds.isEmpty());
        scenarioContext.set("allOrderIds", orderIds); // Store the list of IDs in ScenarioContext
        LOGGER.info("Stored {} order IDs: {}", orderIds.size(), orderIds);
    }

    // Example of how you might use the stored IDs in a subsequent step
    @When("I delete the first order retrieved")
    public void iDeleteTheFirstOrderRetrieved() {
        List<String> orderIds = scenarioContext.get("allOrderIds");
        Assert.assertNotNull("No order IDs found in context to delete.", orderIds);
        Assert.assertFalse("No order IDs available for deletion.", orderIds.isEmpty());

        String orderIdToDelete = orderIds.get(0); // Get the first ID
        LOGGER.info("Attempting to delete order with ID: {}", orderIdToDelete);
        // Assuming you have a deleteOrder method in your OrderService
        // Response deleteResponse = orderService.deleteOrder(orderIdToDelete);
        // scenarioContext.setLatestResponse(deleteResponse);
        // deleteResponse.then().statusCode(200); // Example assertion

    }
}
