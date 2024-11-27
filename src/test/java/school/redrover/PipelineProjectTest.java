package school.redrover;

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

    @Test
    public void testCreateProjectWithValidNameViaSidebar() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertListContainsObject(
                itemList,
                PIPELINE_NAME,
                "Project is not created");
    }

    @Test(dependsOnMethods = "testCreateProjectWithValidNameViaSidebar")
    public void testVerifySidebarOptionsOnProjectPage() {
        List<String> actualSidebarOptionList = new HomePage(getDriver())
                .openPipelineProject(PIPELINE_NAME)
                .getSidebarOptionList();

        Assert.assertEquals(
                actualSidebarOptionList,
                List.of("Status", "Changes", "Build Now", "Configure", "Delete Pipeline", "Stages", "Rename", "Pipeline Syntax"),
                "Sidebar options on Project page do not match expected list.");
    }

    @Test(dependsOnMethods = "testVerifySidebarOptionsOnProjectPage")
    public void testVerifySidebarOptionsOnConfigurationPage() {
        List<String> actualSidebarOptionList = new HomePage(getDriver())
                .openPipelineProject(PIPELINE_NAME)
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
                .openPipelineProject(PIPELINE_NAME)
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
                .openPipelineProject(PIPELINE_NAME)
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
                .openPipelineProject(PIPELINE_NAME)
                .clickConfigureSidebar(PIPELINE_NAME)
                .clickToggleToDisableOrEnableProject()
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
                .openPipelineProject(PIPELINE_NAME)
                .clickEnableButton()
                .gotoHomePage()
                .isGreenScheduleBuildTrianglePresent(PIPELINE_NAME);

        Assert.assertTrue(
                isGreenBuildButtonPresent,
                "Green build button is not displayed for the project");
    }

    @Test
    public void testGetPermalinksInformationUponSuccessfulBuild() {
        TestUtils.createPipeline(this, PIPELINE_NAME);

        List<String> permalinkList = new HomePage(getDriver())
                .clickScheduleBuild(PIPELINE_NAME)
                .openPipelineProject(PIPELINE_NAME)
                .getPermalinkList();

        Assert.assertTrue(
                permalinkList.containsAll(
                        List.of(
                                "Last build",
                                "Last stable build",
                                "Last successful build",
                                "Last completed build")),
                "Not all expected permalinks are present in the actual permalinks list.");
    }

    @Test(dependsOnMethods = "testGetPermalinksInformationUponSuccessfulBuild")
    public void testGetSuccessTooltipDisplayedWhenHoverOverGreenMark() {
        String greenMarkTooltip = new HomePage(getDriver())
                .openPipelineProject(PIPELINE_NAME)
                .hoverOverBuildStatusMark()
                .getStatusMarkTooltipText();

        Assert.assertEquals(
                greenMarkTooltip,
                "Success");
    }

    @Test(dependsOnMethods = "testGetSuccessTooltipDisplayedWhenHoverOverGreenMark")
    public void testKeepBuildForever() {
        boolean isDeleteOptionPresent = new HomePage(getDriver())
                .openPipelineProject(PIPELINE_NAME)
                .clickBuildStatusMark()
                .clickKeepThisBuildForever()
                .isDeleteBuildOptionSidebarPresent(PIPELINE_NAME);

        Assert.assertFalse(
                isDeleteOptionPresent,
                "Delete build sidebar option is displayed, but it should not be.");
    }

    @Test(dependsOnMethods = "testEnableProject")
    public void testRenameProjectViaSidebar() {
        List<String> projectList = new HomePage(getDriver())
                .openPipelineProject(PIPELINE_NAME)
                .clickRenameSidebar(PIPELINE_NAME)
                .cleanInputFieldAndTypeName(NEW_PROJECT_NAME)
                .clickRenameButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertListContainsObject(
                projectList,
                NEW_PROJECT_NAME,
                "Project is not renamed");
    }

    @Test(dependsOnMethods = "testRenameProjectViaSidebar")
    public void testDeleteProjectViaSidebar() {
        List<String> projectList = new HomePage(getDriver())
                .openPipelineProject(NEW_PROJECT_NAME)
                .clickDeletePipelineSidebarAndConfirmDeletion()
                .getItemList();

        Assert.assertListNotContainsObject(
                projectList,
                NEW_PROJECT_NAME,
                "Project is not deleted");
    }
}
