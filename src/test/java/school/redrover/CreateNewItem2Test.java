package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

public class CreateNewItem2Test extends BaseTest {
    final String PROJECT_NAME = "Some_name_for_project_or_folder";

    public void createAndEnterProjectName() {
        ProjectUtils.log("Create a new project");
        getDriver().findElement(By.cssSelector("[href='/view/all/newJob']")).click();

        ProjectUtils.log("Enter the name of the project");
        getDriver().findElement(By.className("jenkins-input")).sendKeys(PROJECT_NAME);
    }

    @Test
    public void testCreateFreestyleProject() {
        createAndEnterProjectName();

        ProjectUtils.log("Select project type");
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();

        ProjectUtils.log("Push OK button to save the project");
        getDriver().findElement(By.cssSelector("#ok-button")).click();

        ProjectUtils.log("Going to the main page");
        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();

        ProjectUtils.log("Verifying the job name");
        String actualProjectName = getDriver().findElement(By.xpath("//tr/td/a/span")).getText();

        Assert.assertEquals(actualProjectName, PROJECT_NAME);
    }

    @Test
    public void testCreatePipeline() {
        createAndEnterProjectName();

        ProjectUtils.log("Select project type");
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();

        ProjectUtils.log("Push OK button to save the project");
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        ProjectUtils.log("Going to the main page");
        getDriver().findElement(By.xpath("//*[text()='Dashboard']")).click();

        ProjectUtils.log("Verifying the job name");
        String actualProjectName = getDriver().findElement(By.xpath("//td/a/span")).getText();

        Assert.assertEquals(actualProjectName, PROJECT_NAME);
    }

    @Test
    public void testMultiConfigurationProject() {
        createAndEnterProjectName();

        ProjectUtils.log("Select the project type");
        getDriver().findElement(By.xpath("//li[@class='hudson_matrix_MatrixProject']")).click();

        ProjectUtils.log("Push OK button to save the project");
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        ProjectUtils.log("Going to the main page");
        getDriver().findElement(By.xpath("//a[@class='model-link' and text()='Dashboard']")).click();

        ProjectUtils.log("Verifying the job name");
        String actualProjectName = getDriver().findElement(By.xpath("//td/a/span")).getText();

        Assert.assertEquals(actualProjectName, PROJECT_NAME);
    }
}
