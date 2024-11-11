package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class CreatePipelineInFolderTest extends BaseTest {

    @Test
    public void testCreateFolderAndPipeline() throws InterruptedException {
        final String expectedFolderName = "FirstFolder";
        final String expectedPipelineName = "FirstPipeline";
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().findElement(By.xpath("//*[@id='main-panel']/div[2]/div/section[1]/ul/li/a")).click();
        getDriver().findElement(By.className("jenkins-input")).clear();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(expectedFolderName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
//        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
//        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();

        String folderName = getDriver().findElement(By.tagName("h1")).getText();

        getDriver().findElement(By.className("content-block")).click();
        getDriver().findElement(By.className("jenkins-input")).clear();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(expectedPipelineName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[2]")).click();
//        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
//        getDriver().findElement(By.id("ok-button")).click();
//        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();
//        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String pipelineName = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(folderName, expectedFolderName);
        Assert.assertEquals(pipelineName, expectedPipelineName);

//        Thread.sleep(5000);
    }
}
