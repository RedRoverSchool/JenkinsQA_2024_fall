package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;


public class PipelineTest extends BaseTest {

    private static final String PROJECT_NAME = "Project";

    @Test
    public void testCreate() {
        createNewProjectAndGoMainPageByLogo(PROJECT_NAME + 1, ProjectType.Pipeline);

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
    public void testCreateWithDescription() {
        final String desc = "The leading open source automation server, Jenkins provides hundreds of plugins to support building, deploying and automating any project.";
        final String name = PROJECT_NAME + "AndDescription";
        createNewProjectWithDescriptionAndGoHomePageByLogo(name, ProjectType.Pipeline,desc);

        getDriver().findElement(By.xpath("//td/a/span[text()='%s']/..".formatted(name))).click();
        getDriver().findElement(By.xpath("//div[@id='description']/div")).getText();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//div[@id='description']/div")).getText(),
                desc);
    }

    @Test(dependsOnMethods = "testCreateWithDescription")
    public void testRename() {
        final String newName = PROJECT_NAME + "2New";

        getDriver().findElement(By.xpath("//table[@id='projectstatus']/tbody/tr/td/a/span/..")).click();

        getDriver().findElement(By.xpath("//div[@id='tasks']/div[7]")).click();

        WebElement inputName = getDriver().findElement(By.xpath("//input[@checkdependson='newName']"));
        inputName.clear();
        inputName.sendKeys(newName);

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        goToHomePageByLogo();

        String actualJobName = getDriver().findElement(
                By.xpath("//table[@id='projectstatus']/tbody/tr/td/a/span")).getText();

        Assert.assertEquals(actualJobName, newName);
    }

    @Test
    public void testAddDescription() {
        final String name = PROJECT_NAME + "AndDesc";
        final String desc = "Add description for new project 45";
        createNewProjectAndGoMainPageByLogo(name, ProjectType.Pipeline);

        getDriver().findElement(By.xpath("//td/a/span[text() = '%s']/..".formatted(name))).click();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(desc);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.id("description")).getText(), desc);
    }

    @Test(dependsOnMethods = "testRename")
    public void testDelete() {

        getDriver().findElement(By.xpath("//table[@id='projectstatus']/tbody/tr/td/a/span/..")).click();

        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-id='ok']"))).click();

        String actualMessage = getDriver().findElement(By.xpath("//div[@class='empty-state-block']/h1")).getText();

        Assert.assertEquals(actualMessage, "Welcome to Jenkins!");
    }

    @Ignore
    @Test
    public void testCreateWithNotUniqueName() {
        String nonUniqueProjectName = PROJECT_NAME + "Unique";

        createNewProjectAndGoMainPageByLogo(nonUniqueProjectName, ProjectType.Pipeline);

        getDriver().findElement(By.xpath("//a[contains(@href, 'newJob')]")).click();

        getDriver().findElement(By.id("name")).sendKeys(nonUniqueProjectName);

        String actualErrorMessage = getDriver().findElement(By.id("itemname-invalid")).getText();

        Assert.assertEquals(actualErrorMessage, "» A job already exists with the name ‘%s’".formatted(nonUniqueProjectName));
    }

    @Test
    public void testCreateSeveralProjects() {
        int numOfProjects = 6;
        List<String> expectedNameList = createSeveralProjects(numOfProjects, "PR", ProjectType.Pipeline);

        List<WebElement> projectList = getDriver().findElements(By.xpath("//table[@id='projectstatus']/tbody/tr/td[3]"));
        List<String> nameList = projectList.stream().map(WebElement::getText).toList();

        Assert.assertEquals(nameList.size(), numOfProjects);

        for (int i = 0; i < numOfProjects; i++) {
            Assert.assertEquals(nameList.get(i),expectedNameList.get(i));
            System.out.println(nameList.get(i) + " ---> " + expectedNameList.get(i));
        }

    }
    private List<String> createSeveralProjects(int numOfProjects, String name, ProjectType projectType) {
        List<String> projectNameList = new ArrayList<>();
        for (int i = 1; i <= numOfProjects; i++) {
            String projectName = name + "_" + i;
            projectNameList.add(projectName);
            getDriver().findElement(By.xpath("//a[@href ='/view/all/newJob']")).click();

            getDriver().findElement(By.id("name")).sendKeys(projectName);

            getWait10().until(ExpectedConditions.elementToBeClickable(
                    By.xpath(("//div[@id='items']//label/span[text()= '%s']".formatted(projectType))))).click();

            getDriver().findElement(By.id("ok-button")).click();

            getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();

            goToHomePageByLogo();
        }
        return projectNameList;
    }

    private void createNewProjectWithDescriptionAndGoHomePageByLogo(String name, ProjectType projectType, String description) {

        getDriver().findElement(By.xpath("//a[@href ='newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(name);

        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath(("//div[@id='items']//label/span[text()= '%s']".formatted(projectType))))).click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("description")).sendKeys(description);
        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();

        goToHomePageByLogo();
    }

    private void createNewProjectAndGoMainPageByLogo(String name, ProjectType projectType) {

        getDriver().findElement(By.xpath("//a[@href ='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(name);

        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath(("//div[@id='items']//label/span[text()= '%s']".formatted(projectType))))).click();

        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void goToHomePageByLogo() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private enum ProjectType {
        FreestyleProject("Freestyle project"),
        Pipeline("Pipeline"),
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


