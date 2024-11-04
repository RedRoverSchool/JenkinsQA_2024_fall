package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateNewJobTest extends BaseTest {

    @Test
    public void testCreateNewPipeline() throws InterruptedException {
        WebElement createJobButton = getDriver().findElement(By.xpath("//a[@href='newJob']"));
        createJobButton.click();

        WebElement inputField = getDriver().findElement(By.id("name"));
        inputField.sendKeys("CodeBrew");
        Thread.sleep(1000);

        WebElement pipelineButton = getDriver().findElement(By.xpath("//span[text()='Pipeline']"));
        pipelineButton.click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();

        WebElement dashBoardButton = getDriver().findElement(By.xpath("//a[text()='Dashboard']"));
        dashBoardButton.click();

        String actualPipeLineName = getDriver().findElement(By.xpath("//span[text()='CodeBrew']")).getText().toLowerCase();

        Assert.assertEquals(actualPipeLineName, "codebrew", "Actual result doesn't meet expectation");

    }
}
