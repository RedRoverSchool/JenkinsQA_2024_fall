package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject6Test extends BaseTest {

    final String projectName = "MyJob";

    public void createFreestyleProject() {
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/div[2]/div/section[1]/ul/li/a")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/section[1]/div[3]/div[1]/div/span/label")).click();
        getDriver().findElement(By.className("jenkins-submit-button")).click();
    }

    @Test
    public void testCreateProject() {

        createFreestyleProject();

        String jobName = getDriver().findElement(By.className("job-index-headline")).getText();

        Assert.assertEquals(jobName, projectName);
    }

    @Test
    public void testDeleteProject() {

        createFreestyleProject();

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[6]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"jenkins\"]/dialog/div[3]/button[1]")).click();
        String h1 = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(h1, "Welcome to Jenkins!");
    }
}
