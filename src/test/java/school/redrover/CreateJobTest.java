package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateJobTest extends BaseTest {

    @Test
    public void testCreateJob(){

        getDriver().findElement(By.xpath("//*[@id='main-panel']/div[2]/div/section[1]/ul/li/a")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("TestBuild");
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();
        getDriver().findElement(By.xpath("//*[@id='job_TestBuild']/td[3]/a/span")).getText();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='job_TestBuild']/td[3]/a/span")).getText(),"TestBuild");

    }

}
