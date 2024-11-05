package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
    public void testCreateWithEmptyName() {
        String expectErrorMessage = "» This field cannot be empty, please enter a valid name";
        getDriver().findElement(By.xpath("//a[@href ='newJob']")).click();

        getDriver().findElement(By.xpath(
                "//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        WebElement buttonSubmit = getDriver().findElement(By.id("ok-button"));

        WebElement actualErrorMessage = getDriver().findElement(By.id("itemname-required"));

        Assert.assertFalse(buttonSubmit.isEnabled());
        Assert.assertEquals(actualErrorMessage.getText(), expectErrorMessage);
    }
}


