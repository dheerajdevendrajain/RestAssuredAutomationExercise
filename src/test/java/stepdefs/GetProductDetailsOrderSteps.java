package stepdefs;

import api.clients.GetProductFromOrderDetailsService;
import context.ScenarioContext;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import utilities.reporting.ReportHelper;

public class GetProductDetailsOrderSteps {
    private Response GetProductDetailsResponse;
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
}
