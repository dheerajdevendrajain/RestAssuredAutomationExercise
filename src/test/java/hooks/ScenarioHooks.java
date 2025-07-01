package hooks;

import config.ConfigManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.reporting.ScenarioStorage;

import java.sql.DriverManager;

public class ScenarioHooks {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScenarioHooks.class);

    @Before
    public void setup(Scenario scenario) {
        LOGGER.info("Starting Scenario: {}", scenario.getName());
        // Initialize ConfigManager once per test run or if environment changes
        // ConfigManager.getInstance() will handle the singleton and loading
        ConfigManager.getInstance();
    }

    @Before(order = 1)
    public void getScenario(Scenario scenario) {
        // This can be used to set up any preconditions or context for the scenario
        LOGGER.info("Before Scenario: {}", scenario.getName());
         ScenarioStorage.putScenario(scenario); // Store the scenario for later use if needed
    }

//     @After
//     public void tearDown(Scenario scenario) {
//        if (scenario.isFailed()) {
//            String screenshotName = ScenarioStorage.getScenario().getName().replaceAll(" ", "_");
//
//            LOGGER.error("Scenario failed: {}", scenario.getName());
//
//        }
//     }
}