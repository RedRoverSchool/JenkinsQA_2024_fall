package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class CreatePipelineInFolderTest extends BaseTest {

    @Ignore
    @Test
    public void testCreateFolderAndPipeline() {
        final String folderName = "FirstFolder";
        final String pipelineName = "FirstPipeline";
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().findElement(By.xpath("//*[@id='main-panel']/div[2]/div/section[1]/ul/li/a")).click();
        getDriver().findElement(By.className("jenkins-input")).clear();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();

        String actualFolderName = getDriver().findElement(By.tagName("h1")).getText();

        getDriver().findElement(By.className("content-block")).click();
        getDriver().findElement(By.className("jenkins-input")).clear();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();

        String actualPipelineName = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(actualFolderName, folderName);
        Assert.assertEquals(actualPipelineName, pipelineName);
    }
}
