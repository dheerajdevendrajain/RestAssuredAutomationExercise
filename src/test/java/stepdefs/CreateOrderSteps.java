package stepdefs;

import api.clients.CreateOrderService;
import api.models.CreateOrderRequest;
import api.models.OrderItem;
import context.ScenarioContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utilities.reporting.ReportHelper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CreateOrderSteps {
    private Response createOrderResponse;
    private ScenarioContext scenarioContext;
    private CreateOrderService createOrderService;
    public CreateOrderRequest createOrderRequest;

    public CreateOrderSteps(ScenarioContext scenarioContext, CreateOrderService createOrderService) {
        this.scenarioContext = scenarioContext;
        this.createOrderService = createOrderService;
    }

    @When("I create an order with below mentioned details")
    public void iCreateAnOrderWithBelowMentionedDetails(DataTable dataTable) {
        System.out.println("Creating order with details:");
        List<Map<String, String>> ordersData = dataTable.asMaps(String.class, String.class);

        List<OrderItem> orderList = ordersData.stream()
                .map(row -> new OrderItem(row.get("country"), row.get("productOrderedId")))
                .collect(Collectors.toList());

        createOrderRequest = CreateOrderRequest.builder()
                .orders(orderList)
                .build();
        createOrderResponse = createOrderService.createOrder(createOrderRequest);
        scenarioContext.setLatestResponse(createOrderResponse);
        ReportHelper.addRequestResponseToReport(new com.google.gson.Gson().toJsonTree(createOrderRequest).getAsJsonObject(), createOrderResponse, "Create Order API");
        System.out.println("Create order Response: " + createOrderResponse.asString());

    }

}