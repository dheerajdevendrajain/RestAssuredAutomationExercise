package utilities.reporting;


import com.google.gson.JsonObject;
import io.restassured.response.Response;

public class ReportHelper {
    public static void addRequestResponseToReport(JsonObject request, Response response, String baseUrl) {
        if (baseUrl != null && !baseUrl.isEmpty()) {
            ScenarioStorage.getScenario().log("Base URL: " + baseUrl);

        }
        if (request != null) {
            ScenarioStorage.getScenario().log("Request: " + request.toString());
        } else {
            ScenarioStorage.getScenario().log("Request: No request data available.");
        }
        if (response != null) {
            ScenarioStorage.getScenario().log("Response: " + response.asString());
            ScenarioStorage.getScenario().log("Status Code: " + response.getStatusCode());
            ScenarioStorage.getScenario().log("Response Time: " + response.getTime() + " ms");
        } else {
            ScenarioStorage.getScenario().log("Response: No response data available.");
        }
    }

//    public  static void addRequestResponseToHtmlReport(JsonObject request, Response response, String baseUrl) {
//        addRequestResponseToReport(null, response, baseUrl);
//    }
}