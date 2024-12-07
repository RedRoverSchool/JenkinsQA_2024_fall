package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.page.PipelineProjectPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;
import java.util.Map;

public class PipelineProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "PipelineName";
    private static final String NEW_PROJECT_NAME = "NewPipelineName";
    private static final List<String> PIPELINE_STAGES = List.of("Start", "Build", "Test", "End");
    private static final String PIPELINE_SCRIPT = """
            pipeline {agent any\n stages {
            stage('Build') {steps {echo 'Building the application'}}
            stage('Test') {steps {error 'Test stage failed due to an error'}}
            }
            """;

    @DataProvider
    public Object[][] providerUnsafeCharacters() {

        return new Object[][]{
                {"\\"}, {"]"}, {":"}, {"#"}, {"&"}, {"?"}, {"!"}, {"@"},
                {"$"}, {"%"}, {"^"}, {"*"}, {"|"}, {"/"}, {"<"}, {">"},
                {"["}, {";"}
        };

    }

    @Test
    public void testCreateProjectWithValidNameViaSidebar() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertListContainsObject(
                itemList,
                PROJECT_NAME,
                "Project is not created");
    }

    @Test(dependsOnMethods = "testCreateProjectWithValidNameViaSidebar")
    public void testVerifySidebarOptionsOnProjectPage() {
        List<String> actualSidebarOptionList = new HomePage(getDriver())
                .openPipelineProject(PROJECT_NAME)
                .getSidebarOptionList();

        Assert.assertEquals(
                actualSidebarOptionList,
                List.of("Status", "Changes", "Build Now", "Configure", "Delete Pipeline", "Stages", "Rename", "Pipeline Syntax"),
                "Sidebar options on Project page do not match expected list.");
    }

    @Test(dependsOnMethods = "testVerifySidebarOptionsOnProjectPage")
    public void testVerifySidebarOptionsOnConfigurationPage() {
        List<String> actualSidebarOptionList = new HomePage(getDriver())
                .openPipelineProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .getSidebarConfigurationOption();

        Assert.assertEquals(
                actualSidebarOptionList,
                List.of("General", "Advanced Project Options", "Pipeline"),
                "Sidebar options on Configuration page do not match expected list.");
    }

    @Test(dependsOnMethods = "testVerifySidebarOptionsOnConfigurationPage")
    public void testVerifyCheckboxTooltipsContainCorrectText() {
        Map<String, String> labelToTooltipTextMap = new HomePage(getDriver())
                .openPipelineProject(PROJECT_NAME)
                .clickSidebarConfigButton()
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
                .openPipelineProject(PROJECT_NAME)
                .editDescription(expectedProjectDescription)
                .clickSubmitButton()
                .getDescription();

        Assert.assertEquals(
                actualDescription,
                expectedProjectDescription,
                "Expected description for the project is not found");
    }

    @Test(dependsOnMethods = "testAddDescriptionToProject")
    public void testGetWarningMessageWhenDisableProject() {
        PipelineProjectPage pipelineProjectPage = new HomePage(getDriver())
                .openPipelineProject(PROJECT_NAME)
                .clickSidebarConfigButton()
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
                homePage.isDisableCircleSignPresent(PROJECT_NAME));
        Assert.assertFalse(
                homePage.isGreenScheduleBuildTrianglePresent(PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testDisableProject")
    public void testEnableProject() {
        boolean isGreenBuildButtonPresent = new HomePage(getDriver())
                .openPipelineProject(PROJECT_NAME)
                .clickEnableButton()
                .gotoHomePage()
                .isGreenScheduleBuildTrianglePresent(PROJECT_NAME);

        Assert.assertTrue(
                isGreenBuildButtonPresent,
                "Green build button is not displayed for the project");
    }

    @Test
    public void testGetPermalinksInformationUponSuccessfulBuild() {
        TestUtils.createPipeline(this, PROJECT_NAME);

        List<String> permalinkList = new HomePage(getDriver())
                .clickScheduleBuild(PROJECT_NAME)
                .openPipelineProject(PROJECT_NAME)
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
                .openPipelineProject(PROJECT_NAME)
                .hoverOverBuildStatusMark()
                .getStatusMarkTooltipText();

        Assert.assertEquals(
                greenMarkTooltip,
                "Success");
    }

    @Test(dependsOnMethods = "testGetSuccessTooltipDisplayedWhenHoverOverGreenMark")
    public void testKeepBuildForever() {
        boolean isDeleteOptionPresent = new HomePage(getDriver())
                .openPipelineProject(PROJECT_NAME)
                .clickBuildStatusMark()
                .clickKeepThisBuildForever()
                .isDeleteBuildOptionSidebarPresent(PROJECT_NAME);

        Assert.assertFalse(
                isDeleteOptionPresent,
                "Delete build sidebar option is displayed, but it should not be.");
    }

    @Test(dependsOnMethods = "testEnableProject")
    public void testRenameProjectViaSidebar() {
        List<String> projectList = new HomePage(getDriver())
                .openPipelineProject(PROJECT_NAME)
                .clickRenameSidebar(PROJECT_NAME)
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

    @Test
    public void testDeleteViaChevron() {
        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .selectDeleteFromItemMenu(PROJECT_NAME)
                .clickYesForConfirmDelete()
                .getItemList();

        Assert.assertListNotContainsObject(projectList, PROJECT_NAME, "Project is not deleted");
    }

    @Test
    public void testCreateWithEmptyName() {
        String emptyNameMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("")
                .selectPipeline()
                .getEmptyNameMessage();

        Assert.assertEquals(emptyNameMessage, "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateWithDuplicateName() {
        String errorMessage = new HomePage(getDriver()).clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipeline()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘%s’".formatted(PROJECT_NAME));
    }

    @Test(dataProvider = "providerUnsafeCharacters")
    public void testCreateWithUnsafeCharactersInName(String unsafeCharacter) {
        String invalidNameMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(unsafeCharacter)
                .selectPipeline()
                .getInvalidNameMessage();

        Assert.assertEquals(invalidNameMessage, "» ‘%s’ is an unsafe character".formatted(unsafeCharacter));
    }

    @Test()
    public void testRename() {
        PipelineProjectPage projectPage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .openPipelineProject(PROJECT_NAME)
                .clickRenameSidebar(PROJECT_NAME)
                .cleanInputFieldAndTypeName(NEW_PROJECT_NAME)
                .clickRenameButton();

        Assert.assertEquals(projectPage.getTitle(), NEW_PROJECT_NAME);
        Assert.assertEquals(projectPage.getProjectNameBreadcrumb(), NEW_PROJECT_NAME);
    }

    @Test()
    public void testWarningMessageOnRenameProjectPage() {
        String actualWarningMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .openPipelineProject(PROJECT_NAME)
                .clickRenameSidebar(PROJECT_NAME)
                .getWarningMessage();

        Assert.assertEquals(actualWarningMessage, "The new name is the same as the current name.");
    }

    @Test
    public void testRenameByChevronDashboard() {
        PipelineProjectPage projectPage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .goToPipelineRenamePageViaDropdown(PROJECT_NAME)
                .cleanInputFieldAndTypeName(NEW_PROJECT_NAME)
                .clickRenameButton();

        Assert.assertEquals(projectPage.getTitle(), NEW_PROJECT_NAME);
        Assert.assertEquals(projectPage.getProjectNameBreadcrumb(), NEW_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testRenameByChevronDashboard")
    public void testRenameByChevronDisplayedOnHomePageWithCorrectName() {
        boolean isDisplayed = new HomePage(getDriver())
                .getItemList()
                .contains(NEW_PROJECT_NAME);

        Assert.assertTrue(isDisplayed);
    }

    @Test()
    public void testDeleteByChevronBreadcrumb() {
        String welcomeTitle = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .gotoHomePage()
                .openPipelineProject(PROJECT_NAME)
                .openDropDownMenuByChevronBreadcrumb(PROJECT_NAME)
                .clickDeletePipelineSidebarAndConfirmDeletion()
                .getWelcomeTitle();

        Assert.assertEquals(welcomeTitle, "Welcome to Jenkins!");
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
                .getStatusBuild(PROJECT_NAME);

        Assert.assertEquals(tooltipValue, "Disabled");
    }

    @Test
    public void testBuildWithValidPipelineScript() {
        final String validPipelineScriptFile = """
                pipeline {
                    agent any
                    stages {
                        stage('Checkout') {
                            steps {echo 'Step: Checkout code from repository'}
                        }
                     }
                }
                """;

        String statusBuild = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .enterScriptFromFile(validPipelineScriptFile)
                .clickSaveButton()
                .gotoHomePage()
                .selectBuildNowFromItemMenu(PROJECT_NAME)
                .refreshAfterBuild()
                .getStatusBuild(PROJECT_NAME);

        Assert.assertEquals(statusBuild, "Success");
    }

    @Test
    public void testBuildWithInvalidPipelineScript() {
        final String invalidPipelineScriptFile = """
                error_pipeline {{{
                    agent any
                    stages {
                        stage('Checkout') {
                            steps {echo 'Step: Checkout code from repository'}
                        }
                     }
                }
                """;

        String statusBuild = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .enterScriptFromFile(invalidPipelineScriptFile)
                .clickSaveButton()
                .gotoHomePage()
                .selectBuildNowFromItemMenu(PROJECT_NAME)
                .refreshAfterBuild()
                .getStatusBuild(PROJECT_NAME);

        Assert.assertEquals(statusBuild, "Failed");
    }

    @Test
    public void testVerifyListOfActionsOnSidebar() {

        List<String> actualListActions = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .openPipelineProject(PROJECT_NAME)
                .getSidebarOptionList();

        Assert.assertEquals(actualListActions,
                List.of("Status", "Changes", "Build Now", "Configure", "Delete Pipeline", "Stages", "Rename", "Pipeline Syntax"));
    }

    @Test
    public void testListOfRecentBuildsISDisplayedOnStages() {

        List<WebElement> pipelineBuilds = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickOnBuildNowItemOnSidePanelAndWait()
                .clickOnStagesItemOnSidePanel()
                .getAllPipelineBuilds();

        Assert.assertFalse(pipelineBuilds.isEmpty());
    }

    @Test
    public void testStagesAreDisplayedInPipelineGraph() {

        List<String> stagesNames = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .addScriptToPipeline(PIPELINE_SCRIPT)
                .clickSaveButton()
                .clickOnBuildNowItemOnSidePanelAndWait()
                .clickOnStagesItemOnSidePanel()
                .getAllStagesNames();

        Assert.assertEquals(stagesNames, PIPELINE_STAGES);
    }

    @Test
    public void testStatusIconsAreDisplayedInPipelineGraph() {

        List<WebElement> icons = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .addScriptToPipeline(PIPELINE_SCRIPT)
                .clickSaveButton()
                .clickOnBuildNowItemOnSidePanelAndWait()
                .clickOnStagesItemOnSidePanel()
                .getGreenAndRedIcons();

        Assert.assertTrue(icons.get(0).isDisplayed(), "Green Icon must be displayed");
        Assert.assertTrue(icons.get(1).isDisplayed(), "Red Icon must be displayed");
    }

    @Test
    public void testStatusIconsColor() {

        List<WebElement> icons = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .addScriptToPipeline(PIPELINE_SCRIPT)
                .clickSaveButton()
                .clickOnBuildNowItemOnSidePanelAndWait()
                .clickOnStagesItemOnSidePanel()
                .getGreenAndRedIcons();

        Assert.assertEquals(icons.get(0).getCssValue("color"), "rgba(30, 166, 75, 1)");
        Assert.assertEquals(icons.get(1).getCssValue("color"), "rgba(230, 0, 31, 1)");
    }

}
