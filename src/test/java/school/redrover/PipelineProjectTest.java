package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.page.PipelineProjectPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;
import java.util.Map;

public class PipelineProjectTest extends BaseTest {

    private static final String PIPELINE_NAME = "PipelineName";
    private static final String NEW_PROJECT_NAME = "NewPipelineName";

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
        getWait10().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='tippy-content']")));
    }

    @Test
    public void testCreateProjectWithValidNameViaSidebar() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .returnToHomePage()
                .getItemList();

        Assert.assertListContainsObject(
                itemList,
                PIPELINE_NAME,
                "Project is not created");
    }

    @Test(dependsOnMethods = "testCreateProjectWithValidNameViaSidebar")
    public void testVerifySidebarOptionsOnProjectPage() {
        List<String> actualSidebarOptionList = new HomePage(getDriver())
                .openProject(PIPELINE_NAME)
                .getSidebarOptionList();

        Assert.assertEquals(
                actualSidebarOptionList,
                List.of("Status", "Changes", "Build Now", "Configure", "Delete Pipeline", "Stages", "Rename", "Pipeline Syntax"),
                "Sidebar options on Project page do not match expected list.");
    }

    @Test(dependsOnMethods = "testVerifySidebarOptionsOnProjectPage")
    public void testVerifySidebarOptionsOnConfigurationPage() {
        List<String> actualSidebarOptionList = new HomePage(getDriver())
                .openProject(PIPELINE_NAME)
                .clickConfigureSidebar(PIPELINE_NAME)
                .getSidebarConfigurationOption();

        Assert.assertEquals(
                actualSidebarOptionList,
                List.of("General", "Advanced Project Options", "Pipeline"),
                "Sidebar options on Configuration page do not match expected list.");
    }

    @Test(dependsOnMethods = "testVerifySidebarOptionsOnConfigurationPage")
    public void testVerifyCheckboxTooltipsContainCorrectText() {
        Map<String, String> labelToTooltipTextMap = new HomePage(getDriver())
                .openProject(PIPELINE_NAME)
                .clickConfigureSidebar(PIPELINE_NAME)
                .getCheckboxWithTooltipTextMap();

        labelToTooltipTextMap.forEach((checkbox, tooltip) ->
                Assert.assertTrue(
                        tooltip.contains("Help for feature: " + checkbox),
                        "Tooltip for feature '" + checkbox + "' does not contain the correct text"));
    }

    @Test(dependsOnMethods = "testVerifyCheckboxTooltipsContainCorrectText")
    public void testAddDescriptionToProject() {
        final String expectedProjectDescription = "Certain_project_description";

        String actualDescription = new HomePage(getDriver())
                .openProject(PIPELINE_NAME)
                .editDescription(expectedProjectDescription)
                .getDescription();

        Assert.assertEquals(
                actualDescription,
                expectedProjectDescription,
                "Expected description for the project is not found");
    }

    @Test(dependsOnMethods = "testAddDescriptionToProject")
    public void testGetWarningMessageWhenDisableProject() {
        PipelineProjectPage pipelineProjectPage = new HomePage(getDriver())
                .openProject(PIPELINE_NAME)
                .clickConfigureSidebar(PIPELINE_NAME)
                .clickToggleToDisableProject()
                .clickSaveButton();

        Assert.assertEquals(
                pipelineProjectPage.getWarningDisabledMessage(),
                "This project is currently disabled");
        Assert.assertEquals(
                pipelineProjectPage.getStatusButtonText(),
                "Enable");
    }

    @Test(dependsOnMethods = "testGetWarningMessageWhenDisableProject")
    public void testDisableProject() {
        HomePage homePage = new HomePage(getDriver());

        Assert.assertTrue(
                homePage.isDisableCircleSignPresent(PIPELINE_NAME));
        Assert.assertFalse(
                homePage.isGreenScheduleBuildTrianglePresent(PIPELINE_NAME));
    }

    @Test(dependsOnMethods = "testDisableProject")
    public void testEnableProject() {
        boolean isGreenBuildButtonPresent = new HomePage(getDriver())
                .openProject(PIPELINE_NAME)
                .clickEnableButton()
                .goToDashboard()
                .isGreenScheduleBuildTrianglePresent(PIPELINE_NAME);

        Assert.assertTrue(
                isGreenBuildButtonPresent,
                "Green build button is not displayed for the project");
    }

    @Test(dependsOnMethods = "testEnableProject")
    public void testGetSuccessTooltipDisplayedWhenHoverOverGreenMark() {
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

    @Test
    public void testGetPermalinksInformationUponSuccessfulBuild() {
        TestUtils.createPipeline(this, PIPELINE_NAME);
        clickGreenTriangleToScheduleBuildForProject(PIPELINE_NAME);

        final List<String> expectedPermalinkList = List.of(
                "Last build",
                "Last stable build",
                "Last successful build",
                "Last completed build");

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

    @Test(dependsOnMethods = "testGetPermalinksInformationUponSuccessfulBuild")
    public void testKeepBuildForever() {
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
        TestUtils.createPipeline(this, PIPELINE_NAME);
        clickProjectByName(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/confirm-rename']")).click();

        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        returnToHomePage();

        Assert.assertListContainsObject(getProjectList(), NEW_PROJECT_NAME,
                "Project is not renamed");
    }

    @Test(dependsOnMethods = "testRenameProjectViaSidebar")
    public void testDeleteProjectViaSidebar() {
        clickProjectByName(NEW_PROJECT_NAME);

        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        Assert.assertListNotContainsObject(getProjectList(), PIPELINE_NAME,
                "Project is not deleted");
    }
}
