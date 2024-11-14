package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

public class CreateNewItem2Test extends BaseTest {

    private static final String EXPECTED_PROJECT_NAME = "Some_name_for_project_or_folder";
    private final String ACTUAL_PROJECT_NAME = getDriver().findElement(By.xpath("//tr/td/a/span")).getText();

    private void createProject(String projectName) {

        ProjectUtils.log("Create a new project");
        getDriver().findElement(By.cssSelector("[href='/view/all/newJob']")).click();

        ProjectUtils.log("Enter the name of the project");
        getDriver().findElement(By.className("jenkins-input")).sendKeys(projectName);
    }

    private void saveProjectAndGoToMainPage() {

        ProjectUtils.log("Push OK button to save the project");
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        ProjectUtils.log("Going to the main page");
        getDriver().findElement(By.xpath("//*[text()='Dashboard']")).click();

    }

    @Test
    public void testCreateFreestyleProject() {

        createProject(EXPECTED_PROJECT_NAME);

        ProjectUtils.log("Select project type");
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();

        saveProjectAndGoToMainPage();

        ProjectUtils.log("Verifying the job name");
        Assert.assertEquals(ACTUAL_PROJECT_NAME, EXPECTED_PROJECT_NAME);
    }

    @Test
    public void testCreatePipeline() {

        createProject(EXPECTED_PROJECT_NAME);

        ProjectUtils.log("Select project type");
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();

        saveProjectAndGoToMainPage();

        ProjectUtils.log("Verifying the job name");
        Assert.assertEquals(ACTUAL_PROJECT_NAME, EXPECTED_PROJECT_NAME);
    }

    @Test
    public void testMultiConfigurationProject() {

        createProject(EXPECTED_PROJECT_NAME);

        ProjectUtils.log("Select the project type");
        getDriver().findElement(By.xpath("//li[@class='hudson_matrix_MatrixProject']")).click();

        saveProjectAndGoToMainPage();

        ProjectUtils.log("Verifying the job name");
        Assert.assertEquals(ACTUAL_PROJECT_NAME, EXPECTED_PROJECT_NAME);
    }
}
