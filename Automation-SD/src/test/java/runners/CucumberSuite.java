package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)  // <-- Correct Runner class
@CucumberOptions(
        features = "src/test/resources/features/saucedemo.feature",
        glue = {"com.steps", "com.hooks"},
        tags = "@sauceDemo",
        plugin = {
                "pretty",
                "json:AutomationReports/Cucumber.json",
                "html:BNYM_BDD_Reports/cucumber.html"
        }
)
public class CucumberSuite {
}