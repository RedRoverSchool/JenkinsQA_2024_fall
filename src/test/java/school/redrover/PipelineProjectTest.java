package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;


public class PipelineProjectTest extends BaseTest {

    private static final String PIPELINE_NAME = "Pipeline_name";
    private static final String NEW_PROJECT_NAME = "New_Pipeline_name";

    private void createProjectViaSidebar(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void returnToHomePage() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void clickJobByName(String projectName) {
        getDriver().findElement(By.xpath("//td/a[@href='job/" + projectName + "/']")).click();
    }

    private List<String> getProjectList() {
        List<WebElement> jobList = getDriver().findElements(By.xpath("//td/a[contains(@href,'job/')]"));

        return jobList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    private static WebDriverWait getWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void testCreateProjectWithValidNameViaSidebar() {
        createProjectViaSidebar(PIPELINE_NAME);
        returnToHomePage();

        Assert.assertListContainsObject(getProjectList(), PIPELINE_NAME, "Project is not found");
    }

    @Test
    public void testAddDescriptionToProject() {
        final String expectedProjectDescription = "Certain project description";

        createProjectViaSidebar(PIPELINE_NAME);
        returnToHomePage();
        clickJobByName(PIPELINE_NAME);

        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(expectedProjectDescription);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String actualDescription = getDriver().findElement(By.id("description")).getText();

        Assert.assertEquals(actualDescription, expectedProjectDescription, "Expected description for the project is not found");
    }

    @Test
    public void testRenameProjectViaSidebar() {
        createProjectViaSidebar(PIPELINE_NAME);
        returnToHomePage();
        clickJobByName(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/confirm-rename']")).click();

        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        returnToHomePage();

        Assert.assertListContainsObject(getProjectList(), NEW_PROJECT_NAME, "Project is not renamed");
    }

    @Test
    public void testRenameProjectViaDropdownMenu() {
        createProjectViaSidebar(PIPELINE_NAME);
        returnToHomePage();

        WebElement projectElement = getWait(getDriver()).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='job/" + PIPELINE_NAME + "/']")));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(projectElement)
                .pause(1000)
                .scrollToElement(getDriver().findElement(
                        By.xpath("//a[@href='job/" + PIPELINE_NAME + "/']//button[@class='jenkins-menu-dropdown-chevron']")))
                .click()
                .perform();

        getWait(getDriver()).until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/job/Pipeline_name/confirm-rename']"))).click();

        WebElement nameInput = getWait(getDriver()).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@checkdependson='newName']")));
        nameInput.clear();
        nameInput.sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        returnToHomePage();
        Assert.assertListContainsObject(getProjectList(), NEW_PROJECT_NAME, "Project is not renamed");
    }

    @Test
    public void testDeleteProjectViaSidebar() {
        createProjectViaSidebar(PIPELINE_NAME);
        returnToHomePage();
        clickJobByName(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        Assert.assertListNotContainsObject(getProjectList(), PIPELINE_NAME, "Project is not deleted");
    }
}
