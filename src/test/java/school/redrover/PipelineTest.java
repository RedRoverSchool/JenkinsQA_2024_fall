package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.CreateNewItemPage;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;


public class PipelineTest extends BaseTest {

    private static final String PROJECT_NAME = "Project";

    @Test
    public void testCreate() {
        String projectName = "PipelineProjectNew";
        String actualProjectName = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .getItemName();

        Assert.assertEquals(actualProjectName, projectName);
    }

    @Test
    public void testCreateWithEmptyName() {

        String actualErrorMessage = new HomePage(getDriver())
                .clickNewItem()
                .selectPipeline()
                .getErrorMessage();

        Assert.assertEquals(actualErrorMessage, "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateWithDescription() {
        final String description = "The leading open source automation server, Jenkins provides hundreds of plugins to support building, deploying and automating any project.";
        final String projectName = "PipelineProjectAndDescription";

        String actualDescription = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .enterDescription(description)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(actualDescription, description);
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

    @Test(description = "AT_02.007.01")
    public void testWarningMessageOnRenameProjectPage() {
        final String name = "PipelineProjectRename";
        createNewProjectAndGoMainPageByLogo(name, ProjectType.Pipeline);

        findProjectOnDashboardByName(name).click();
        clickRenameButtonOnSidebar();

        String actualWarningMessage = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='validation-error-area validation-error-area--visible']"))).getText();

        Assert.assertEquals(actualWarningMessage, "The new name is the same as the current name.");
    }

    @Test
    public void testRenameByChevronDashboard() {
        final String projectName = "PipelineRenameByChevron";
        final String projectNameNew = "PipelineRenameByChevronNew";
        createNewProjectAndGoMainPageByLogo(projectName, ProjectType.Pipeline);

        findProjectOnDashboardByName(projectName);

        WebElement buttonChevron = getWait10().until(TestUtils.ExpectedConditions.elementIsNotMoving(
                By.xpath("//a[@href ='job/%s/']/button[@class='jenkins-menu-dropdown-chevron']"
                        .formatted(projectName))));

        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonChevron);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href = '/job/%s/confirm-rename']".formatted(projectName)))).click();

        WebElement inputName = getDriver().findElement(By.xpath("//input[@checkdependson='newName']"));
        inputName.clear();
        inputName.sendKeys(projectNameNew);

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String verifyBreadcrumb = getDriver().findElement(
                By.xpath("//ol[@id='breadcrumbs']/li[@class='jenkins-breadcrumbs__list-item'][2]")).getText();

        String currentUrl = getDriver().getCurrentUrl();

        String nameFromUrl = currentUrl.substring(projectNameNew.length(), currentUrl.length() - 1);

        Assert.assertEquals(getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='jenkins-app-bar']//h1"))).getText(),
                projectNameNew);
        Assert.assertEquals(verifyBreadcrumb, projectNameNew);
        Assert.assertEquals(nameFromUrl, projectNameNew);

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

    @Test(description = "AT_02.005.02")
    public void testDeleteByChevronDashboard() {
        final String projectName = "ProjectDeleteByChevron";
        createNewProjectAndGoMainPageByLogo(projectName, ProjectType.Pipeline);

        new Actions(getDriver())
                .moveToElement(findProjectOnDashboardByName(projectName))
                .perform();

        WebElement buttonChevron = getWait10().until(TestUtils.ExpectedConditions.elementIsNotMoving(
                By.xpath("//a[@href ='job/%s/']/button[@class='jenkins-menu-dropdown-chevron']"
                        .formatted(projectName))));

        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonChevron);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@href = '/job/%s/doDelete']".formatted(projectName)))).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@data-id='ok']"))).click();

        goToHomePageByLogo();

        String actualMessage = getDriver().findElement(By.xpath("//div[@class='empty-state-block']/h1")).getText();

        Assert.assertEquals(actualMessage, "Welcome to Jenkins!");
    }

    @Test(description = "AT_02.005.03")
    public void testDeleteByChevronBreadcrumb() {
        final String projectName = "ProjectDeleteByChevronBreadcrumb";
        createNewProjectAndGoMainPageByLogo(projectName, ProjectType.Pipeline);

        new Actions(getDriver())
                .moveToElement(findProjectOnDashboardByName(projectName))
                .click()
                .perform();

        WebElement buttonChevron = getWait10().until(TestUtils.ExpectedConditions.elementIsNotMoving(
                By.xpath("//a[@href ='/job/%s/']/button[@class='jenkins-menu-dropdown-chevron']"
                        .formatted(projectName))));

        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonChevron);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@href = '/job/%s/doDelete']".formatted(projectName)))).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@data-id='ok']"))).click();

        goToHomePageByLogo();

        String actualMessage = getDriver().findElement(By.xpath("//div[@class='empty-state-block']/h1")).getText();

        Assert.assertEquals(actualMessage, "Welcome to Jenkins!");
    }

    @Test
    public void testCreateWithNotUniqueName() {
        String nonUniqueProjectName = PROJECT_NAME + "Unique";

        createNewProjectAndGoMainPageByLogo(nonUniqueProjectName, ProjectType.Pipeline);

        getDriver().findElement(By.xpath("//a[contains(@href, 'newJob')]")).click();

        getDriver().findElement(By.id("name")).sendKeys(nonUniqueProjectName);

        String actualErrorMessage = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("itemname-invalid"))).getText();

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

    @Test
    public void testPipelineDisabledTooltipOnHomePage() {
        String tooltipValue = new HomePage(getDriver())
                                  .clickNewItem()
                                  .enterItemName(PROJECT_NAME)
                                  .selectPipelineAndClickOk()
                                  .clickToggleToDisableOrEnableProject()
                                  .clickSaveButton()
                                  .gotoHomePage()
                                  .getTooltipValue(PROJECT_NAME);

        Assert.assertEquals(tooltipValue, "Disabled");
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

    private WebElement findProjectOnDashboardByName(String name) {
        return getDriver().findElement(By.xpath("//a[@href ='job/%s/']".formatted(name)));
    }

    private void clickRenameButtonOnSidebar() {

        getDriver().findElement(
                By.xpath("//div[@id='tasks']/div//span[contains(text(), 'Rename')]/..")).click();
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


