package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class CreateNewJobTest extends BaseTest {

    private WebElement waitForElementVisibility(By locator) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element;
    }

    @Test
    public void testCreateNewPipeline() {
        WebElement createJobButton = waitForElementVisibility(By.xpath("//a[@href='newJob']"));
        createJobButton.click();

        WebElement inputField = waitForElementVisibility(By.id("name"));
        inputField.sendKeys("CodeBrew");

        WebElement pipelineButton = waitForElementVisibility(By.xpath("//span[text()='Pipeline']"));
        pipelineButton.click();

        WebElement okButton = waitForElementVisibility(By.id("ok-button"));
        okButton.click();

        WebElement dashBoardButton = waitForElementVisibility(By.xpath("//a[text()='Dashboard']"));
        dashBoardButton.click();

        String actualPipeLineName = waitForElementVisibility(By.xpath("//span[text()='CodeBrew']")).getText().toLowerCase();

        Assert.assertEquals(actualPipeLineName, "codebrew", "Actual result doesn't meet expectation");

    }
}
