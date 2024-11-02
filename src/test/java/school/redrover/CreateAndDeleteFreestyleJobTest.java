package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateAndDeleteFreestyleJobTest extends BaseTest {

    @Test
    public void testCreateAndDeleteFreestyleProject () {

        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/div[2]/div/section[1]/ul/li/a")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("MyJob");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/section[1]/div[3]/div[1]/div/span/label")).click();
        getDriver().findElement(By.className("jenkins-submit-button")).click();
        String jobName = getDriver().findElement(By.className("job-index-headline")).getText();

        Assert.assertEquals(jobName, "MyJob");

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[6]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"jenkins\"]/dialog/div[3]/button[1]")).click();
        String h1 = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(h1, "Welcome to Jenkins!");
    }
}
