package school.redrover;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.freestyle.FreestyleConfigPage;
import school.redrover.page.freestyle.FreestyleProjectPage;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class FreestyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "MyFreestyleProject";
    private static final String NEW_PROJECT_NAME = "NewFreestyleProjectName";
    private static final String DESCRIPTION = "FreestyleDescription";
    private static final String BUILD_NAME = "BuildName";

    @DataProvider
    public Object[][] providerUnsafeCharacters() {

        return new Object[][]{
                {"\\"}, {"]"}, {":"}, {"#"}, {"&"}, {"?"}, {"!"}, {"@"},
                {"$"}, {"%"}, {"^"}, {"*"}, {"|"}, {"/"}, {"<"}, {">"},
                {"["}, {";"}
        };
    }

    @Test
    public void testCreateFreestyleProjectWithEmptyName() {
        String emptyNameMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("")
                .selectFreestyleProject()
                .getEmptyNameMessage();

        Assert.assertEquals(emptyNameMessage, "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateFreestyleProjectWithDuplicateName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProject()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘%s’".formatted(PROJECT_NAME));
    }

    @Test
    public void testCreateProjectViaContentBlockButton() {
        List<String> actualProjectsList = new HomePage(getDriver())
                .clickNewItemContentBlock()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(actualProjectsList.size(), 1);
        Assert.assertEquals(actualProjectsList.get(0), PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithDuplicateName")
    public void testRenameViaBreadcrumbDropdown() {
        String renamedProject = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .openBreadcrumbDropdown()
                .clickRenameBreadcrumbDropdown()
                .clearInputFieldAndTypeName(NEW_PROJECT_NAME)
                .clickRenameButton()
                .getProjectName();

        Assert.assertEquals(renamedProject, NEW_PROJECT_NAME);
    }

    @Test
    public void testCreateProjectViaSidebarMenu() {
        List<String> actualProjectsList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(actualProjectsList.size(), 1);
        Assert.assertEquals(actualProjectsList.get(0), PROJECT_NAME);
    }

    @Test
    public void testCreateFreestyleProjectFromMyViews() {
        List<String> projectName = new HomePage(getDriver())
                .clickMyViewsButton()
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(projectName.size(), 1);
        Assert.assertEquals(projectName.get(0), PROJECT_NAME);
    }

    @Test
    public void testCreateFreestyleProjectWithDurationCheckbox() {
        final String durationPeriod = "minute";

        String periodCheckbox = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .selectDurationCheckbox(durationPeriod)
                .clickSaveButton()
                .gotoHomePage()
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .getTimePeriod();

        Assert.assertEquals(periodCheckbox, durationPeriod);
    }

    @Test(dependsOnMethods = "testCreateProjectViaContentBlockButton")
    public void testAddDescription() {
        String description = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .editDescription(DESCRIPTION)
                .clickSubmitButton()
                .getDescription();

        Assert.assertEquals(description, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testAddDescription")
    public void testEditDescriptionOnProjectPage() {
        final String newDescription = "New " + DESCRIPTION;

        String actualDescription = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clearDescription()
                .editDescription(newDescription)
                .clickSubmitButton()
                .getDescription();

        Assert.assertEquals(actualDescription, newDescription);
    }

    @Test(dependsOnMethods = "testEditDescriptionOnProjectPage")
    public void testDeleteDescription() {
        String description = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clearDescription()
                .getDescription();

        Assert.assertEquals(description, "");
    }

    @Test(dependsOnMethods = "testCreateProjectViaSidebarMenu")
    public void testRenameProjectViaSidebarMenu() {
        final String newName = "New " + PROJECT_NAME;

        String actualProjectName = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName(newName)
                .clickRenameButton()
                .getProjectName();

        Assert.assertEquals(actualProjectName, newName);
    }

    @Test
    public void testRenameProjectViaDropdown() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        final String newName = "New " + PROJECT_NAME;

        String actualProjectName = new HomePage(getDriver())
                .clickRenameInProjectDropdown(PROJECT_NAME)
                .clearInputFieldAndTypeName(newName)
                .clickRenameButton()
                .getProjectName();

        Assert.assertEquals(actualProjectName, newName);
    }

    @Test
    public void testCheckSidebarMenuItemsOnProjectPage() {
        final List<String> templateSidebarMenu = List.of(
                "Status", "Changes", "Workspace", "Build Now", "Configure", "Delete Project", "Rename");

        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        List<String> actualSidebarMenu = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .getSidebarOptionList();

        Assert.assertEquals(actualSidebarMenu, templateSidebarMenu);
    }

    @Test
    public void testConfigureProjectAddBuildStepsExecuteShellCommand() {
        final String testCommand = "echo \"TEST! Hello Jenkins!\"";

        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String extractedText = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .clickAddBuildStep()
                .selectExecuteShellBuildStep()
                .addExecuteShellCommand(testCommand)
                .clickSaveButton()
                .clickSidebarConfigButton()
                .getTextExecuteShellTextArea();

        Assert.assertEquals(extractedText, testCommand);
    }

    @Test
    public void testBuildProjectViaSidebarMenuOnProjectPage() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String buildInfo = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickBuildNowSidebar()
                .clickOnSuccessBuildIconForLastBuild()
                .getConsoleOutputText();

        Assert.assertTrue(buildInfo.contains("Finished: SUCCESS"));
    }

    @Test(dependsOnMethods = "testBuildProjectViaSidebarMenuOnProjectPage")
    public void testAddBuildDisplayName() {
        String actualBuildName = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickOnSuccessBuildIconForLastBuild()
                .clickEditBuildInformationSidebar()
                .addDisplayName(BUILD_NAME)
                .clickSaveButton()
                .getStatusTitle();

        Assert.assertTrue(actualBuildName.contains(BUILD_NAME), "Title doesn't contain build name");
    }

    @Test(dependsOnMethods = {"testBuildProjectViaSidebarMenuOnProjectPage", "testAddBuildDisplayName"})
    public void testEditBuildDisplayName() {
        final String newDisplayName = "New " + BUILD_NAME;

        String actualBuildName = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickOnSuccessBuildIconForLastBuild()
                .clickEditBuildInformationSidebar()
                .editDisplayName(newDisplayName)
                .clickSaveButton()
                .getStatusTitle();

        Assert.assertTrue(actualBuildName.contains(newDisplayName));
    }

    @Test(dependsOnMethods = "testBuildProjectViaSidebarMenuOnProjectPage")
    public void testAddBuildDescription() {
        String actualDescription = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickOnSuccessBuildIconForLastBuild()
                .clickEditBuildInformationSidebar()
                .addBuildDescription(DESCRIPTION)
                .clickSaveButton()
                .getBuildDescription();

        Assert.assertEquals(actualDescription, DESCRIPTION);
    }

    @Test(dependsOnMethods = {"testBuildProjectViaSidebarMenuOnProjectPage", "testAddBuildDescription"})
    public void testEditBuildDescription() {
        final String newDescription = "New " + DESCRIPTION;

        String actualDescription = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickOnSuccessBuildIconForLastBuild()
                .clickEditBuildInformationSidebar()
                .editBuildDescription(newDescription)
                .clickSaveButton()
                .getBuildDescription();

        Assert.assertEquals(actualDescription, newDescription);
    }
    @Ignore
    @Test
    public void testDeleteLastBuild() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        FreestyleProjectPage freestyleProjectPage = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickBuildNowSidebar();
        String lastBuildNumber = freestyleProjectPage.getLastBuildNumber();

        freestyleProjectPage
                .clickOnSuccessBuildIconForLastBuild()
                .clickDeleteBuildSidebar()
                .confirmDeleteBuild();

        Assert.assertListNotContainsObject(freestyleProjectPage.getListOfBuilds(), lastBuildNumber, "The last build wasn't deleted");
    }

    @Test(description = "Verify existing of, total build time, for projects build")
    public void testTotalBuildTimeForProjectsBuild() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        int lastBuildTotalTime = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickBuildNowSidebar()
                .clickLastBuildDateTime()
                .getLastBuildTotalTime();

        Assert.assertTrue(lastBuildTotalTime > 0);
    }

    @Test(dependsOnMethods = "testTotalBuildTimeForProjectsBuild")
    public void testDeleteProjectViaSidebarMenuOnProjectPage() {
        String welcomeText = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickDeleteButtonSidebarAndConfirm()
                .getWelcomeTitle();

        Assert.assertEquals(welcomeText, "Welcome to Jenkins!", "There is a project on Dashboard");
    }

    @Test
    public void testDeleteProjectViaDropdown() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String welcomeText = new HomePage(getDriver())
                .selectDeleteFromItemMenuAndClickYes(PROJECT_NAME)
                .getWelcomeTitle();

        Assert.assertEquals(welcomeText, "Welcome to Jenkins!");
    }

    @Test(dependsOnMethods = "testBuildProjectViaSidebarMenuOnProjectPage")
    public void testDeleteWorkspace() {
        String workspaceText = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickWorkspaceSidebar()
                .clickWipeOutCurrentWorkspaceSidebar()
                .clickYesToWipeOutCurrentWorkspace()
                .clickWorkspaceSidebar()
                .getWorkspaceTitle();

        Assert.assertEquals(workspaceText, "Error: no workspace");
    }

    @Test(dependsOnMethods = "testBuildProjectViaSidebarMenuOnProjectPage")
    public void testDeleteWorkspaceConfirmationOptions() {
        List<String> dialogOptions = List.of("Wipe Out Current Workspace", "Are you sure about wiping out the workspace?", "Cancel", "Yes");

        boolean areAllConfirmationDialogOptionsPresent = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickWorkspaceSidebar()
                .clickWipeOutCurrentWorkspaceSidebar()
                .verifyConfirmationDialogOptionsPresence(dialogOptions);

        Assert.assertTrue(areAllConfirmationDialogOptionsPresent, "Some dialog options weren't found");
    }

    @Test(dataProvider = "providerUnsafeCharacters")
    public void testErrorMessageDisplayedForInvalidCharactersInProjectName(String unsafeCharacter) {
        String invalidNameMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(unsafeCharacter)
                .selectFreestyleProject()
                .getInvalidNameMessage();

        Assert.assertEquals(invalidNameMessage, "» ‘%s’ is an unsafe character".formatted(unsafeCharacter));
    }

    @Test
    public void testFreestyleProjectDescriptionPreview() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String descriptionPreview = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .editDescription(DESCRIPTION)
                .clickPreview()
                .getPreviewDescriptionText();

        Assert.assertEquals(descriptionPreview, DESCRIPTION);
    }

    @Test
    public void testJobNameSorting() {
        List<String> projectNames = List.of("aaa", "bbb", "aabb");
        projectNames.forEach(name -> TestUtils.createFreestyleProject(getDriver(), name));

        Boolean isSorted = new HomePage(getDriver())
                .isInAlphabeticalOrder();

        Assert.assertTrue(isSorted, "Projects is not sorted alphabetically");
    }

    @Test
    public void testNotificationBarAppears() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String notificationBar = new HomePage(getDriver())
                .selectBuildNowFromItemMenu(PROJECT_NAME)
                .getNotificationBarStatus();

        Assert.assertEquals(notificationBar, "Build Now: Done.");
    }

    @Test(dependsOnMethods = "testNotificationBarAppears")
    public void testCounterOfRunsIncrease() {
        String progressBar = new HomePage(getDriver())
                .selectBuildNowFromItemMenu(PROJECT_NAME)
                .refreshAfterBuild()
                .getNumberOfRuns();

        Assert.assertEquals(progressBar, "#2");
    }

    @Test(dependsOnMethods = "testCounterOfRunsIncrease")
    public void testStatusOnHomePageIsSuccess() {

        String statusBuild = new HomePage(getDriver())
                .getStatusBuild(PROJECT_NAME);

        Assert.assertEquals(statusBuild, "Success");
    }

    @Test
    public void testBuildHistoryIsEmpty() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        List<String> emptyHistory = new HomePage(getDriver())
                .gotoBuildHistoryPageFromLeftPanel()
                .getListOfStatuses();

        Assert.assertEquals(emptyHistory.size(), 0);
    }

    @Test
    public void testUpdateAfterExecutingBuild() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        List<String> oneExecution = new HomePage(getDriver())
                .clickScheduleBuild(PROJECT_NAME)
                .gotoBuildHistoryPageFromLeftPanel()
                .getListOfStatuses();

        Assert.assertEquals(oneExecution.get(0), "stable");
        Assert.assertEquals(oneExecution.size(), 1);
    }

    @Test(dependsOnMethods = "testUpdateAfterExecutingBuild")
    public void testUpdateAfterChangingConfig() {
        List<String> changeConfig = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .addBuildStep("Run with timeout")
                .gotoHomePage()
                .clickScheduleBuild(PROJECT_NAME)
                .gotoBuildHistoryPageFromLeftPanel()
                .getListOfStatuses();

        Assert.assertEquals(changeConfig.size(), 2);
    }

    @Test
    public void testWorkspaceIsOpened() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String workspace = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickBuildNowSidebar()
                .clickWorkspaceSidebar()
                .getBreadCrumb();

        Assert.assertEquals(workspace, "Workspace of " + PROJECT_NAME + " on Built-In Node");
    }

    @Test(dependsOnMethods = "testWorkspaceIsOpened")
    public void testLastBuildIsOpened() {

        String secondBuild = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickBuildNowSidebar()
                .clickWorkspaceSidebar()
                .clickOnSuccessBuildIconForLastBuild()
                .getBreadCrumb();

        Assert.assertEquals(secondBuild, "#2");
    }

    @Test(dependsOnMethods = "testBuildHistoryIsEmpty")
    public void testDeleteViaBreadcrumbDropdown() {
        List<String> projectList = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .openBreadcrumbDropdown()
                .clickDeleteBreadcrumbDropdownAndConfirm()
                .getItemList();

        Assert.assertListNotContainsObject(projectList, PROJECT_NAME, "Project is not deleted.");
    }

    @Test
    public void testCreateFreestyleProjectFromExistingOne() {
        String secondProjectName = "Second" + PROJECT_NAME;
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        List<String> itemNameList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(secondProjectName)
                .enterName(PROJECT_NAME)
                .clickOkLeadingToCofigPageOfCopiedProject(new FreestyleConfigPage(getDriver()))
                .gotoHomePage()
                .getItemList();

        Assert.assertTrue(itemNameList.contains(secondProjectName));
    }
}