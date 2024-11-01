package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineTest extends BaseTest {

    @Test
    public void testCreatePipeline() {

        getDriver().findElement(By.xpath("//a[@href ='newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys("Pipeline1");
        getDriver().findElement(By.xpath(
                "//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        String actualJobName = getDriver().findElement(By.xpath(
                "//table[@id='projectstatus']/tbody/tr/td/a/span")).getText();

        Assert.assertEquals(actualJobName, "Pipeline1");
    }

    @Test
    public void testVerifySwitchingEnableButton() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("New Pipeline");

        getDriver().findElement(By.cssSelector("[class$='WorkflowJob']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.cssSelector("#toggle-switch-enable-disable-project > label")).click();

        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();
        getDriver().findElement(By.cssSelector("#enable-project > button")).click();

        String foundText = getDriver().findElement(By.cssSelector("#pipeline-box > div")).getText();
        Assert.assertEquals(foundText, "No data available. This Pipeline has not yet run.");
    }

}
