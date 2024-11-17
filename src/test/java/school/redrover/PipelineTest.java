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

    private static final String PROJECT_NAME = "Project";

    @Test
    public void testCreatePipeline() {

        createNewProject(PROJECT_NAME + 1, ProjectType.PipelineProject);

        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        String actualJobName = getDriver().findElement(By.xpath(
                "//table[@id='projectstatus']/tbody/tr/td/a/span")).getText();

        Assert.assertEquals(actualJobName, PROJECT_NAME + 1);
    }

    @Test
    public void testCreateWithEmptyName() {

        getDriver().findElement(By.xpath("//a[@href ='newJob']")).click();

        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();

        WebElement actualErrorMessage = getDriver().findElement(By.id("itemname-required"));

        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled());
        Assert.assertEquals(actualErrorMessage.getText(), "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testRenameJob() {

        createNewProject(PROJECT_NAME + 2, ProjectType.PipelineProject);

        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//table[@id='projectstatus']/tbody/tr/td/a/span")).click();

        getDriver().findElement(By.xpath("//div[@id='tasks']/div[7]")).click();

        WebElement inputName = getDriver().findElement(By.xpath("//input[@checkdependson='newName']"));
        inputName.clear();
        inputName.sendKeys(PROJECT_NAME + "2New");

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();

        String actualJobName = getDriver().findElement(By.xpath("//table[@id='projectstatus']/tbody/tr/td/a/span")).getText();

        Assert.assertEquals(actualJobName, PROJECT_NAME + "2New");
    }

    @Test
    public void testDeleteJob() {

        createNewProject(PROJECT_NAME + 3, ProjectType.PipelineProject);

        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//table[@id='projectstatus']/tbody/tr/td/a/span")).click();

        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-id='ok']"))).click();

        String actualMessage = getDriver().findElement(By.xpath("//div[@class='empty-state-block']/h1")).getText();

        Assert.assertEquals(actualMessage, "Welcome to Jenkins!");
    }

    private void createNewProject(String name, ProjectType projectType) {

        getDriver().findElement(By.xpath("//a[@href ='newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(name);

        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath(("//div[@id='items']//label/span[text()= '%s']".formatted(projectType))))).click();

        getDriver().findElement(By.id("ok-button")).click();
    }

    private enum ProjectType {
        FreestyleProject("Freestyle project"),
        PipelineProject("Pipeline"),
        MultiConfigurationProject("Multi-configuration project"),
        Folder("Folder"),
        MultibranchPipeline("Multibranch Pipeline"),
        OrganizationFolder("Organization Folder");

        private final String htmlText;

        ProjectType(String htmlText) {
            this.htmlText = htmlText;
        }

        public String getHtmlText() {
            return htmlText;
        }
    }
}


