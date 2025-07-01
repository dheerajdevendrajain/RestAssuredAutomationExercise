package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Path to feature files
        glue = {"stepdefs", "hooks"}, // Path to step definitions and hooks
        plugin = {"pretty", "html:target/cucumber-reports.html", "json:target/cucumber-reports.json"},
        monochrome = true, // Readable console output
        tags = "@Login" // Run specific tags (optional)
)
public class TestRunner {
    // This class runs your Cucumber tests
}