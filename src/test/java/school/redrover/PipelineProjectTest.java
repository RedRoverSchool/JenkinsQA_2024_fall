package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PipelineProjectTest extends BaseTest {

    private static final String PIPELINE_NAME = "Pipeline_name";
    private static final String NEW_PROJECT_NAME = "New_Pipeline_name";

    private void createProjectViaSidebarAndReturnHome(String name) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(name);
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void returnToHomePage() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void clickProjectByName(String name) {
        getDriver().findElement(By.xpath("//td/a[@href='job/" + name + "/']")).click();
    }

    private List<String> getProjectList() {

        return getDriver().findElements(By.xpath("//td/a[contains(@href,'job/')]"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    private void clickGreenTriangleToScheduleBuildForProject(String name) {
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td[@class='jenkins-table__cell--tight']//a[@tooltip='Schedule a Build for " + name + "']"))).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='tippy-content']")));
    }

    @Test
    public void testCreateProjectWithValidNameViaSidebar() {
        createProjectViaSidebarAndReturnHome(PIPELINE_NAME);

        Assert.assertListContainsObject(getProjectList(), PIPELINE_NAME, "Project is not found");
    }

    @Test
    public void testVerifySidebarOptionsOnProjectPage() {
        final List<String> expectedSidebarOptionList =
                List.of("Status", "Changes", "Build Now", "Configure", "Delete Pipeline", "Stages", "Rename", "Pipeline Syntax");

        createProjectViaSidebarAndReturnHome(PIPELINE_NAME);
        clickProjectByName(PIPELINE_NAME);

        List<String> actualSideBarOptionList = getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[@class='task ']//span[2]"))).stream().map(WebElement::getText).toList();

        Assert.assertEquals(actualSideBarOptionList, expectedSidebarOptionList,
                "Sidebar options on Project page do not match expected list.");
    }

    @Test
    public void testVerifySidebarOptionsOnConfigurationPage() {
        final List<String> expectedSidebarOptionList =
                List.of("General", "Advanced Project Options", "Pipeline");

        createProjectViaSidebarAndReturnHome(PIPELINE_NAME);
        clickProjectByName(PIPELINE_NAME);

        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/configure']"))).click();

        List<String> actualSideBarOptionList = getWait2().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[@class='task']//span[2]"))).stream().map(WebElement::getText).toList();

        Assert.assertEquals(actualSideBarOptionList, expectedSidebarOptionList,
                "Sidebar options on Configuration page do not match expected list.");
    }

    @Test
    public void testAddDescriptionToProject() {
        final String expectedProjectDescription = "Certain project description";

        createProjectViaSidebarAndReturnHome(PIPELINE_NAME);
        clickProjectByName(PIPELINE_NAME);

        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(expectedProjectDescription);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String actualDescription = getDriver().findElement(By.id("description")).getText();

        Assert.assertEquals(actualDescription, expectedProjectDescription,
                "Expected description for the project is not found");
    }

    @Test
    public void testGetWarningMessageWhenDisableProject() {
        createProjectViaSidebarAndReturnHome(PIPELINE_NAME);
        clickProjectByName(PIPELINE_NAME);

        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/configure']"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[@data-title='Disabled']"))).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String actualWarningMessage = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//form[@id='enable-project']"))).getText().split("\n")[0];
        String buttonText = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//form[@id='enable-project']/button[@name='Submit']"))).getText();

        Assert.assertEquals(actualWarningMessage, "This project is currently disabled");
        Assert.assertEquals(buttonText, "Enable");
    }

    @Test
    public void testDisableProject() {
        createProjectViaSidebarAndReturnHome(PIPELINE_NAME);
        clickProjectByName(PIPELINE_NAME);

        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/configure']"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[@data-title='Disabled']"))).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        returnToHomePage();

        WebElement disableCircleSign = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//tr[@id='job_Pipeline_name']//*[@tooltip='Disabled']")));

        boolean isGreenScheduleBuildTrianglePresent = !getDriver().findElements(
                        By.xpath("//td[@class='jenkins-table__cell--tight']//a[@tooltip='Schedule a Build for " + PIPELINE_NAME + "']"))
                .isEmpty();

        Assert.assertTrue(disableCircleSign.isDisplayed());
        Assert.assertFalse(isGreenScheduleBuildTrianglePresent);
    }

    @Test(dependsOnMethods = "testDisableProject")
    public void testEnableProject() {
        clickProjectByName(PIPELINE_NAME);

        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@formNoValidate='formNoValidate']"))).click();
        returnToHomePage();

        WebElement greenBuildButton = getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td[@class='jenkins-table__cell--tight']//a[@tooltip='Schedule a Build for " + PIPELINE_NAME + "']")));

        Assert.assertTrue(greenBuildButton.isEnabled());
    }

    @Test
    public void testGetSuccessTooltipDisplayedWhenHoverOverGreenMark() {
        createProjectViaSidebarAndReturnHome(PIPELINE_NAME);
        clickGreenTriangleToScheduleBuildForProject(PIPELINE_NAME);
        clickProjectByName(PIPELINE_NAME);

        new Actions(getDriver())
                .moveToElement(getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@title='Success']"))))
                .perform();

        String greenMarkTooltip = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='tippy-content']"))).getText();

        Assert.assertEquals(greenMarkTooltip, "Success");
    }

    @Ignore
    @Test
    public void testGetPermalinksInformationUponSuccessfulBuild() {
        final List<String> expectedPermalinkList = List.of(
                "Last build",
                "Last stable build",
                "Last successful build",
                "Last completed build");

        createProjectViaSidebarAndReturnHome(PIPELINE_NAME);
        clickGreenTriangleToScheduleBuildForProject(PIPELINE_NAME);
        clickProjectByName(PIPELINE_NAME);

        List<String> actualPermalinkList = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//li[@class='permalink-item']")))
                .stream()
                .map(WebElement::getText)
                .map(string -> string.split("\\(#")[0].trim())
                .toList();

        Assert.assertTrue(actualPermalinkList.containsAll(expectedPermalinkList),
                "Not all expected permalinks are present in the actual permalinks list.");
    }

    @Test
    public void testVerifyCheckboxTooltipsContainCorrectText() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        List<WebElement> checkboxWithQuestionMarkList = getDriver().findElements(
                By.xpath("//div[@hashelp = 'true']//label[@class='attach-previous ']"));
        List<WebElement> questionMarkList = getDriver().findElements(
                By.xpath("//div[@hashelp = 'true']//a[@class='jenkins-help-button']"));

        Map<String, String> labelToTooltipTextMap = new HashMap<>();
        for (int i = 0; i < checkboxWithQuestionMarkList.size(); i++) {
            String checkboxText = checkboxWithQuestionMarkList.get(i).getText();
            String tooltipText = questionMarkList.get(i).getAttribute("tooltip");
            labelToTooltipTextMap.put(checkboxText, tooltipText);
        }

        labelToTooltipTextMap.forEach((checkbox, tooltip) ->
                Assert.assertTrue(tooltip.contains("Help for feature: " + checkbox),
                        "Tooltip for feature '" + checkbox + "' does not contain the correct text"));
    }

    @Test
    public void testKeepBuildForever() {
        createProjectViaSidebarAndReturnHome(PIPELINE_NAME);
        clickGreenTriangleToScheduleBuildForProject(PIPELINE_NAME);
        clickProjectByName(PIPELINE_NAME);

        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title='Success']"))).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();

        List<WebElement> sidebarTaskList = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[@id='tasks']/div")));

        boolean isDeleteBuildOptionPresent = sidebarTaskList.stream()
                .anyMatch(element -> element.getAttribute("href") != null &&
                        element.getAttribute("href").contains("/job/" + PIPELINE_NAME + "/1/confirmDelete"));

        Assert.assertFalse(isDeleteBuildOptionPresent,
                "Delete build sidebar option is displayed, but it should not be.");
    }

    @Test
    public void testRenameProjectViaSidebar() {
        createProjectViaSidebarAndReturnHome(PIPELINE_NAME);
        clickProjectByName(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/confirm-rename']")).click();

        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        returnToHomePage();

        Assert.assertListContainsObject(getProjectList(), NEW_PROJECT_NAME,
                "Project is not renamed");
    }

    @Test
    public void testDeleteProjectViaSidebar() {
        createProjectViaSidebarAndReturnHome(PIPELINE_NAME);
        clickProjectByName(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        Assert.assertListNotContainsObject(getProjectList(), PIPELINE_NAME,
                "Project is not deleted");
    }
}
