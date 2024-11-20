package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateNewJobTest extends BaseTest {

    @Test
    public void testCreateNewPipeline() {

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys("CodeBrew");

        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();

        String actualPipeLineName = getDriver().findElement(
                By.xpath("//span[text()='CodeBrew']")).getText().toLowerCase();

        Assert.assertEquals(actualPipeLineName, "codebrew", "Actual result doesn't meet expectation");
    }
}
