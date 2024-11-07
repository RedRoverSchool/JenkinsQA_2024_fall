package school.redrover.runner;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

@Listeners({FilterForTests.class})
public abstract class BaseTest {

    private WebDriver driver;

    @BeforeMethod
    protected void beforeMethod(Method method) {
        ProjectUtils.logf("Run %s.%s", this.getClass().getName(), method.getName());

        ProjectUtils.log("Clear data");
        JenkinsUtils.clearData();

        ProjectUtils.log("Browser open");
        driver = ProjectUtils.createDriver();

        ProjectUtils.log("Get web page");
        ProjectUtils.get(driver);

        ProjectUtils.log("Login");
        JenkinsUtils.login(driver);
    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        if (ProjectUtils.isServerRun() || testResult.isSuccess() || ProjectUtils.closeBrowserIfError()) {
            try {
                JenkinsUtils.logout(driver);
            } catch (Exception ignore) {}
            driver.quit();
            ProjectUtils.log("Browser closed");
        }

        ProjectUtils.logf(
                "Execution time is %.3f sec",
                (testResult.getEndMillis() - testResult.getStartMillis()) / 1000.0);
    }

    protected WebDriver getDriver() {
        return driver;
    }
}
