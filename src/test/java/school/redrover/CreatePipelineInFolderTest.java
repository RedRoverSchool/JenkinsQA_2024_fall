package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreatePipelineInFolderTest extends BaseTest {

    @Test
    public void testCreateFolderAndPipeline() throws InterruptedException {
        getDriver().findElement(By.xpath("//*[@id='main-panel']/div[2]/div/section[1]/ul/li/a")).click();
        getDriver().findElement(By.className("jenkins-input")).clear();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("FirstFolder");
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        Thread.sleep(2000);
        getDriver().findElement(By.id("ok-button")).click();
        Thread.sleep(2000);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String folderName = getDriver().findElement(By.tagName("h1")).getText();

        getDriver().findElement(By.className("content-block")).click();
        getDriver().findElement(By.className("jenkins-input")).clear();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("FirstPipeline");
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[2]")).click();
        Thread.sleep(2000);
        getDriver().findElement(By.id("ok-button")).click();
        Thread.sleep(2000);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String pipelineName = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(folderName, "FirstFolder");
        Assert.assertEquals(pipelineName, "FirstPipeline");

        Thread.sleep(5000);
    }
}
