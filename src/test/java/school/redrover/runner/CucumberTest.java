package school.redrover.runner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/cucumber",
        glue = {"school.redrover.cucumber", "school.redrover.runner"},
        plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        tags = "not @ignore")
public class CucumberTest extends AbstractTestNGCucumberTests {
}


