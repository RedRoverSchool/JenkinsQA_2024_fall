package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class PipelineProject2Test extends BaseTest {

    private static final String PROJECT_NAME = "MyPipelineProject";

    private void createItemUtils(String name, String locator) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.xpath(locator)).click();
        getDriver().findElement(By.id("ok-button")).click();

    }

    @Test
    public void testCreatePipelineProjectWithValidName() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        createItemUtils(PROJECT_NAME, "//span[text()='Pipeline']");
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        String pipelineProjectName = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath(String.format("//span[text()='%s']", PROJECT_NAME))))
                .getText();

        Assert.assertEquals(pipelineProjectName, PROJECT_NAME);
    }

}
