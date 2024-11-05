package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

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

    @Test
    public void testRenameJob() {
        String nameJob = "Pipeline1";
        String newNameJob = "Pipeline1New";

        getDriver().findElement(By.xpath("//a[@href ='newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(nameJob);
        getDriver().findElement(By.xpath(
                "//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//table[@id='projectstatus']/tbody/tr/td/a/span")).click();

        getDriver().findElement(By.xpath("//div[@id='tasks']/div[7]")).click();

        WebElement inputName = getDriver().findElement(By.xpath("//input[@checkdependson='newName']"));
        inputName.clear();
        inputName.sendKeys(newNameJob);

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();

        String actualJobName = getDriver().findElement(By.xpath(
                "//table[@id='projectstatus']/tbody/tr/td/a/span")).getText();

        Assert.assertEquals(actualJobName, newNameJob);
    }

    @Test
    public void testDeleteJob() {
        String nameJob = "Pipeline1";

        getDriver().findElement(By.xpath("//a[@href ='newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(nameJob);
        getDriver().findElement(By.xpath(
                "//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//table[@id='projectstatus']/tbody/tr/td/a/span")).click();

        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-id='ok']"))).click();

        String actualMessage = getDriver().findElement(By.xpath("//div[@class='empty-state-block']/h1")).getText();

        Assert.assertEquals(actualMessage, "Welcome to Jenkins!");
    }
}


