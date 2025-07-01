package stepdefs;

import api.clients.AddToCartService;
import api.models.AddToCartRequest;
import api.models.Product;
import context.ScenarioContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utilities.reporting.ReportHelper;

import java.util.List;
import java.util.Map;

public class AddToCartSteps {
    private Response addToCartResponse;
    private ScenarioContext scenarioContext;
    private AddToCartService addToCartService;
    public AddToCartRequest addToCartRequest;

    public AddToCartSteps(ScenarioContext scenarioContext, AddToCartService addToCartService) {
        this.scenarioContext = scenarioContext;
        this.addToCartService = addToCartService;
    }


    @When("I add a item to the cart with below mentioned details")
    public void iAddAItemToTheCartWithBelowMentionedDetails(DataTable dataTable) {
        String userId = scenarioContext.get("userId").toString();
        List<Map<String, String>> products = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> productDetails : products) {
            addToCartRequest = AddToCartRequest.builder()
                    ._id(userId)
                    .product(Product.builder()
                            ._id(productDetails.get("_id"))
                            .productName(productDetails.get("productName"))
                            .productCategory(productDetails.get("productCategory"))
                            .productSubCategory(productDetails.get("productSubCategory"))
                            .productPrice(Integer.parseInt(productDetails.get("productPrice")))
                            .productDescription(productDetails.get("productDescription"))
                            .productImage(productDetails.get("productImage"))
                            .productRating(productDetails.get("productRating"))
                            .productTotalOrders(productDetails.get("productTotalOrders"))
                            .productStatus(Boolean.parseBoolean(productDetails.get("productStatus")))
                            .productAddedBy(productDetails.get("productAddedBy"))
                            .__v(Integer.parseInt(productDetails.get("__v")))
                            .productFor(productDetails.get("productFor")).build())
                    .build();
        }

        addToCartResponse = addToCartService.addProductToCart(addToCartRequest);
        scenarioContext.setLatestResponse(addToCartResponse);
        ReportHelper.addRequestResponseToReport(new com.google.gson.Gson().toJsonTree(addToCartRequest).getAsJsonObject(), addToCartResponse, "Add To Cart API");
        System.out.println("Add To cart Response: " + addToCartResponse.asString());
    }
}