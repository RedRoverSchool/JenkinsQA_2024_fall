package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

public class CreateNewItem2Test extends BaseTest {

    @Test
    public void testCreateFreestyleProject() throws InterruptedException {
        ProjectUtils.log("Create a new project");
        WebElement newItemButton = getDriver().findElement(By.cssSelector("[href='/view/all/newJob']"));
        newItemButton.click();

        ProjectUtils.log("Enter the name of the project");
        String jobName = "Some name for Freestyle project";
        WebElement inputItemNameField = getDriver().findElement(By.className("jenkins-input"));
        inputItemNameField.sendKeys(jobName);

        ProjectUtils.log("Select the project type");
        WebElement freestyleProjectButton = getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject"));
        freestyleProjectButton.click();

        ProjectUtils.log("Push OK button to save the project");
        WebElement okButton = getDriver().findElement(By.cssSelector("#ok-button"));
        okButton.click();

        ProjectUtils.log("Going to the main page");
        WebElement dashboardButtonInBreadcrumbs = getDriver().findElement(By.xpath("//a[@class='model-link' and text()='Dashboard']"));
        dashboardButtonInBreadcrumbs.click();

        ProjectUtils.log("Verifying the job name");
        String jobNameText = getDriver().findElement(By.xpath("//tr/td/a/span")).getText();
        Assert.assertEquals(jobNameText, jobName);
    }

    @Test
    public void testCreatePipeline() {
        ProjectUtils.log("Create a new project");
        WebElement newItemButton = getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']"));
        newItemButton.click();

        ProjectUtils.log("Enter the name of the project");
        String jobName = "Some name for Freestyle project";
        WebElement itemNameField = getDriver().findElement(By.xpath("//input[@class='jenkins-input']"));
        itemNameField.sendKeys(jobName);

        ProjectUtils.log("Select the project type");
        WebElement pipeline = getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']"));
        pipeline.click();

        ProjectUtils.log("Push OK button to save the project");
        WebElement okButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
        okButton.click();

        ProjectUtils.log("Going to the main page");
        WebElement dashboardButtonInBreadcrumbs = getDriver().findElement(By.xpath("//*[text()='Dashboard']"));
        dashboardButtonInBreadcrumbs.click();

        ProjectUtils.log("Verifying the job name");
        String jobNameText = getDriver().findElement(By.xpath("//td/a/span")).getText();
        Assert.assertEquals(jobNameText, jobName);
    }

    @Test
    public void testMultiConfigurationProject() throws InterruptedException {
        ProjectUtils.log("Create a new project");
        WebElement newItemButton = getDriver().findElement(By.cssSelector("[href='/view/all/newJob']"));
        newItemButton.click();

        ProjectUtils.log("Enter the name of the project");
        String jobName = "Some name for Multi-configuration Project";
        WebElement inputItemNameField = getDriver().findElement(By.className("jenkins-input"));
        inputItemNameField.sendKeys(jobName);

        ProjectUtils.log("Select the project type");
        WebElement multiConfigurationProjectButton = getDriver().findElement(By.xpath("//li[@class='hudson_matrix_MatrixProject']"));
        multiConfigurationProjectButton.click();

        ProjectUtils.log("Push OK button to save the project");
        WebElement okButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
        okButton.click();

        ProjectUtils.log("Going to the main page");
        WebElement dashboardButtonInBreadcrumbs = getDriver().findElement(By.xpath("//a[@class='model-link' and text()='Dashboard']"));
        dashboardButtonInBreadcrumbs.click();

        ProjectUtils.log("Verifying the job name");
        String jobNameText = getDriver().findElement(By.xpath("//td/a/span")).getText();
        Assert.assertEquals(jobNameText, jobName);
    }
}
