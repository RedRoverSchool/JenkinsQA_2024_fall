package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.testdata.TestDataProvider;
import school.redrover.page.freestyle.FreestyleConfigPage;
import school.redrover.page.freestyle.FreestyleProjectPage;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

@Epic("01 FreestyleProject")
public class FreestyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "MyFreestyleProject";
    private static final String NEW_PROJECT_NAME = "NewFreestyleProjectName";
    private static final String DESCRIPTION = "FreestyleDescription";
    private static final String BUILD_NAME = "BuildName";

    private String escapeHtml(String input) {
        if (input == null) return null;
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("TC_00.001.05 Verify error when create the project without name.")
    public void testCreateFreestyleProjectWithEmptyName() {
        String emptyNameMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("")
                .selectFreestyleProject()
                .getEmptyNameMessage();

        Allure.step("Expected result: Error message is displayed");
        Assert.assertEquals(emptyNameMessage, "» This field cannot be empty, please enter a valid name");
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("TC_00.002.05 Create Freestyle Project with duplicate name via sidebar")
    public void testCreateFreestyleProjectWithDuplicateName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getHeader()
                .gotoHomePage()
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProject()
                .getErrorMessage();

        Allure.step("Expected result: Error message is displayed");
        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘%s’".formatted(PROJECT_NAME));
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("TC_00.001.08 Create project via content block button")
    public void testCreateProjectViaContentBlockButton() {
        List<String> actualProjectsList = new HomePage(getDriver())
                .clickNewItemContentBlock()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Allure.step(String.format("Expected result: Project '%s' is displayed on Home page", PROJECT_NAME), () -> {
            Assert.assertEquals(actualProjectsList.size(), 1, "The projects list size is not as expected");
            Assert.assertEquals(actualProjectsList.get(0), PROJECT_NAME, "The project name does not match the expected value");
        });
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithDuplicateName")
    @Story("US_01.002 Rename Project")
    @Description("TC_01.002.05 Rename via Breadcrumb dropdown")
    public void testRenameViaBreadcrumbDropdown() {
        String renamedProject = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .openBreadcrumbDropdown()
                .clickRenameBreadcrumbDropdown()
                .clearInputFieldAndTypeName(NEW_PROJECT_NAME)
                .clickRenameButton()
                .getProjectName();

        Allure.step(String.format("Expected result: Renamed project '%s' is displayed on Project page", NEW_PROJECT_NAME));
        Assert.assertEquals(renamedProject, NEW_PROJECT_NAME);
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("TC_00.001.09 Create from MyViews")
    public void testCreateFromMyViews() {
        List<String> projectName = new HomePage(getDriver())
                .clickMyViewsButton()
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Allure.step(String.format("Expected result: project '%s' is displayed on Home page", PROJECT_NAME), () -> {
            Assert.assertEquals(projectName.size(), 1);
            Assert.assertEquals(projectName.get(0), PROJECT_NAME);
        });
    }

    @Test(dataProvider = "providerUnsafeCharacters", dataProviderClass = TestDataProvider.class)
    @Story("US_01.002 Rename Project")
    @Description("TC_01.002.02 Rename FreestyleProject with incorrect symbols")
    public void testRenameWithIncorrectSymbols(String unsafeCharacter) {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String invalidNameMessage = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName(unsafeCharacter)
                .clickRenameButtonLeadingToError()
                .getErrorMessage();

        Allure.step("Expected result: Error message is displayed");
        Assert.assertEquals(invalidNameMessage, "‘%s’ is an unsafe character".formatted(escapeHtml(unsafeCharacter)));
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("TC_00.001.03 Create Freestyle Project via sidebar")
    public void testCreateProjectViaSidebarMenu() {
        List<String> actualProjectsList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Allure.step(String.format("Expected result: project '%s' is displayed on Home page", PROJECT_NAME), () -> {
            Assert.assertEquals(actualProjectsList.size(), 1);
            Assert.assertEquals(actualProjectsList.get(0), PROJECT_NAME);
        });
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("TC_00.001.10 Create with duration checkbox")
    public void testCreateFreestyleProjectWithDurationCheckbox() {
        final String durationPeriod = "minute";

        String periodCheckbox = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .selectDurationCheckbox(durationPeriod)
                .clickSaveButton()
                .getHeader()
                .gotoHomePage()
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .getTimePeriod();

        Allure.step(String.format("Expected result: The selected duration period is '%s'", durationPeriod));
        Assert.assertEquals(periodCheckbox, durationPeriod);
    }

    @Test(dependsOnMethods = "testCreateProjectViaContentBlockButton")
    @Story("US_01.001 Add description")
    @Description("TC_01.001.01 Add description to a new FreestyleProject")
    public void testAddDescription() {
        String description = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .editDescription(DESCRIPTION)
                .clickSubmitButton()
                .getDescription();

        Allure.step(String.format("Expected result: The description '%s' added for project '%s'", DESCRIPTION, PROJECT_NAME));
        Assert.assertEquals(description, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testAddDescription")
    @Story("US_01.010 Edit description")
    @Description("TC_01.010.01 Edit existed description with new one")
    public void testEditDescriptionOnProjectPage() {
        final String newDescription = "New " + DESCRIPTION;

        String actualDescription = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clearDescription()
                .editDescription(newDescription)
                .clickSubmitButton()
                .getDescription();

        Allure.step(String.format("Expected result: The description '%s' updated for project '%s'", newDescription, PROJECT_NAME));
        Assert.assertEquals(actualDescription, newDescription);
    }

    @Test
    @Story("US_01.001 Add description")
    @Description("TC_01.001.03 Add description on configure page")
    public void testAddDescriptionOnConfigPageViaItemDropdown() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String actualDescription = new HomePage(getDriver())
                .selectConfigureFromItemMenuForFreestyle(PROJECT_NAME)
                .enterDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescription();

        Allure.step(String.format("Expected result: The description '%s' added for project '%s'", DESCRIPTION, PROJECT_NAME));
        Assert.assertEquals(actualDescription, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testEditDescriptionOnProjectPage")
    @Story("US_01.011 Delete description")
    @Description("TC_01.011.01 Delete existed description for the project")
    public void testDeleteDescription() {
        String description = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clearDescription()
                .getDescription();

        Allure.step(String.format("Expected result: The description is successfully deleted for project '%s'", PROJECT_NAME));
        Assert.assertEquals(description, "");
    }

    @Test(dependsOnMethods = "testCreateProjectViaSidebarMenu")
    @Story("US_01.002 Rename project")
    @Description("TC_01.002.01 Rename FreestyleProject with correct name")
    public void testRenameProjectViaSidebarMenu() {
        final String newName = "New " + PROJECT_NAME;

        String actualProjectName = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName(newName)
                .clickRenameButton()
                .getProjectName();

        Allure.step(String.format("Expected result: The project is renamed to '%s'", newName));
        Assert.assertEquals(actualProjectName, newName);
    }

    @Test(dependsOnMethods = "testRenameProjectViaSidebarMenu")
    @Story("US_01.002 Rename project")
    @Description("TC_01.002.03 Rename FreestyleProject with empty name")
    public void testRenameEmptyName() {
        String emptyNameWarning = new HomePage(getDriver())
                .openFreestyleProject("New " + PROJECT_NAME)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName("")
                .clickRenameButtonLeadingToError()
                .getErrorMessage();

        Allure.step("Expected result: Warning message is displayed");
        Assert.assertEquals(emptyNameWarning, "No name is specified");
    }

    @Test(dependsOnMethods = "testRenameEmptyName")
    @Story("US_01.002 Rename project")
    @Description("TC_01.002.04 Rename FreestyleProject with the same name")
    public void testRenameWithTheSameName() {
        String theSameNameWarning = new HomePage(getDriver())
                .openFreestyleProject("New " + PROJECT_NAME)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName("New " + PROJECT_NAME)
                .clickRenameButtonLeadingToError()
                .getErrorMessage();

        Allure.step("Expected result: Warning message is displayed");
        Assert.assertEquals(theSameNameWarning, "The new name is the same as the current name.");
    }

    @Test
    @Story("US_01.002 Rename project")
    @Description("TC_01.002.06 Rename via item dropdown with valid name")
    public void testRenameProjectViaDropdown() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        final String newName = "New " + PROJECT_NAME;

        String actualProjectName = new HomePage(getDriver())
                .clickRenameInProjectDropdown(PROJECT_NAME)
                .clearInputFieldAndTypeName(newName)
                .clickRenameButton()
                .getProjectName();

        Allure.step(String.format("Expected result: The project is renamed to '%s'", newName));
        Assert.assertEquals(actualProjectName, newName);
    }

    @Test
    @Story("US_01.012 Sidebar Menu Verification")
    @Description("TC_01.012.01 Verify Sidebar Menu Items")
    public void testCheckSidebarMenuItemsOnProjectPage() {
        final List<String> templateSidebarMenu = List.of(
                "Status", "Changes", "Workspace", "Build Now", "Configure", "Delete Project", "Rename");

        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        List<String> actualSidebarMenu = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .getSidebarItemList();

        Allure.step("Expected result: The sidebar menu contains the correct items");
        Assert.assertEquals(actualSidebarMenu, templateSidebarMenu);
    }

    @Test
    @Story("US_01.013 Add Execute Shell Command")
    @Description("TC_01.013.01 Verify Shell Command is Added and Displayed ")
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

        Allure.step(String.format("Expected result: The Shell command '%s' is successfully added and displayed", testCommand));
        Assert.assertEquals(extractedText, testCommand);
    }

    @Test
    @Story("US_01.008 Build item")
    @Description("TC_01.008.01 Run the build")
    public void testBuildProjectViaSidebarMenuOnProjectPage() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String buildInfo = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickBuildNowSidebarAndWaite()
                .clickOnSuccessBuildIconForLastBuild()
                .getConsoleOutputText();

        Allure.step("Expected result: The project build is successfully triggered");
        Assert.assertTrue(buildInfo.contains("Finished: SUCCESS"));
    }

    @Test(dependsOnMethods = "testBuildProjectViaSidebarMenuOnProjectPage")
    @Story("US_01.008 Build item")
    @Description("TC_01.008.02 Verify display name is added and reflected in build title")
    public void testAddBuildDisplayName() {
        String actualBuildName = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickOnSuccessBuildIconForLastBuild()
                .clickEditBuildInformationSidebar()
                .addDisplayName(BUILD_NAME)
                .clickSaveButton()
                .getStatusTitle();

        Allure.step(String.format("Expected result: The build display name '%s' is successfully added to the build title", BUILD_NAME));
        Assert.assertTrue(actualBuildName.contains(BUILD_NAME), "Title doesn't contain build name");
    }

    @Test(dependsOnMethods = {"testBuildProjectViaSidebarMenuOnProjectPage", "testAddBuildDisplayName"})
    @Story("US_01.008 Build item")
    @Description("TC_01.008.03  Verify build display name is edited and reflected in build title")
    public void testEditBuildDisplayName() {
        final String newDisplayName = "New " + BUILD_NAME;

        String actualBuildName = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickOnSuccessBuildIconForLastBuild()
                .clickEditBuildInformationSidebar()
                .editDisplayName(newDisplayName)
                .clickSaveButton()
                .getStatusTitle();

        Allure.step(String.format("Expected result: The build display name is successfully updated to '%s'", newDisplayName));
        Assert.assertTrue(actualBuildName.contains(newDisplayName));
    }

    @Test(dependsOnMethods = "testBuildProjectViaSidebarMenuOnProjectPage")
    @Story("US_01.008 Build item")
    @Description("TC_01.008.04 Add description to the build")
    public void testAddBuildDescription() {
        String actualDescription = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickOnSuccessBuildIconForLastBuild()
                .clickEditBuildInformationSidebar()
                .addBuildDescription(DESCRIPTION)
                .clickSaveButton()
                .getBuildDescription();

        Allure.step(String.format("Expected result: The build description '%s' is successfully added", DESCRIPTION));
        Assert.assertEquals(actualDescription, DESCRIPTION);
    }

    @Test(dependsOnMethods = {"testBuildProjectViaSidebarMenuOnProjectPage", "testAddBuildDescription"})
    @Story("US_01.008 Build item")
    @Description("TC_01.008.05 Edit description to the build")
    public void testEditBuildDescription() {
        final String newDescription = "New " + DESCRIPTION;

        String actualDescription = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickOnSuccessBuildIconForLastBuild()
                .clickEditBuildInformationSidebar()
                .editBuildDescription(newDescription)
                .clickSaveButton()
                .getBuildDescription();

        Allure.step(String.format("Expected result: The build description is successfully updated to '%s'", newDescription));
        Assert.assertEquals(actualDescription, newDescription);
    }

    @Test
    @Story("US_01.008 Build item")
    @Description("TC_01.008.06 Delete the build")
    public void testDeleteLastBuild() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        FreestyleProjectPage freestyleProjectPage = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickBuildNowSidebarAndWaite()
                .clickBuildNowSidebarAndWaite();

        String lastBuildNumber = freestyleProjectPage.getLastBuildNumber();

        freestyleProjectPage
                .clickOnSuccessBuildIconForLastBuild()
                .clickDeleteBuildSidebar()
                .confirmDeleteBuild();

        Allure.step("Expected result: The last build is successfully deleted from the project");
        Assert.assertListNotContainsObject(freestyleProjectPage.getListOfBuilds(), lastBuildNumber, "The last build wasn't deleted");
    }

    @Test(description = "Verify existing of, total build time, for projects build")
    @Story("US_01.008 Build item")
    @Description("TC_01.008.07 Verify total build time for project build")
    public void testTotalBuildTimeForProjectsBuild() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        double lastBuildTotalTime = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickBuildNowSidebarAndWaite()
                .clickLastBuildDateTime()
                .getLastBuildTotalTime();

        Allure.step("Expected result: The total build time for the project build is greater than 0");
        Assert.assertTrue(lastBuildTotalTime > 0);
    }

    @Test(dependsOnMethods = "testTotalBuildTimeForProjectsBuild")
    @Story("US_01.004 Delete Project")
    @Description("TC_01.004.02 Delete the project from sidebar")
    public void testDeleteProjectViaSidebarMenuOnProjectPage() {
        String welcomeText = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickDeleteButtonSidebarAndConfirm()
                .getWelcomeTitle();

        Allure.step("Expected result: The project is successfully deleted, and the welcome text is displayed on the dashboard");
        Assert.assertEquals(welcomeText, "Welcome to Jenkins!", "There is a project on Dashboard");
    }

    @Test
    @Story("US_01.004 Delete Project")
    @Description("TC_01.004.03 Delete the project via project dropdown")
    public void testDeleteProjectViaDropdown() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String welcomeText = new HomePage(getDriver())
                .selectDeleteFromItemMenuAndClickYes(PROJECT_NAME)
                .getWelcomeTitle();

        Allure.step("Expected result: The project is successfully deleted, and the welcome text is displayed on the dashboard");
        Assert.assertEquals(welcomeText, "Welcome to Jenkins!");
    }

    @Test(dependsOnMethods = "testDeleteLastBuild")
    @Story("US_01.004 Delete Project")
    @Description("TC_01.004.03 Delete the project via project dropdown")
    public void testDeleteFirstProjectViaChevron() {
        final String secondProject = "MySecondFreestyleProject";
        TestUtils.createFreestyleProject(getDriver(), secondProject);

        List<String> projectsList = new HomePage(getDriver())
                .selectDeleteFromItemMenuAndClickYes(PROJECT_NAME)
                .getItemList();

        Allure.step(String.format("Expected result: The first project is successfully deleted and the remaining project is '%s'", secondProject), () -> {
            Assert.assertEquals(projectsList.size(), 1);
            Assert.assertEquals(projectsList.get(0), secondProject);
        });
    }

    @Test(dependsOnMethods = "testBuildProjectViaSidebarMenuOnProjectPage")
    @Story("US_01.007 Workspace delete")
    @Description("TC_01.007.01 Workspace is empty after deletion")
    public void testDeleteWorkspace() {
        String workspaceText = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickWorkspaceSidebar()
                .clickWipeOutCurrentWorkspaceSidebar()
                .clickYesToWipeOutCurrentWorkspace()
                .clickWorkspaceSidebar()
                .getWorkspaceTitle();

        Allure.step("Expected result: The workspace is successfully deleted.");
        Assert.assertEquals(workspaceText, "Error: no workspace");
    }

    @Test(dependsOnMethods = "testBuildProjectViaSidebarMenuOnProjectPage")
    @Story("US_01.007 Workspace delete")
    @Description("TC_01.007.02 Workspace confirmation options are displayed before deletion ")
    public void testDeleteWorkspaceConfirmationOptions() {
        List<String> dialogOptions = List.of("Wipe Out Current Workspace", "Are you sure about wiping out the workspace?");
        List<String> dialogOptionButtonName = List.of("Cancel", "Yes");

        FreestyleProjectPage areAllConfirmationDialogOptionsPresent = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickWorkspaceSidebar()
                .clickWipeOutCurrentWorkspaceSidebar();


        Allure.step("Expected result: All confirmation dialog texts are present");
        Assert.assertTrue(areAllConfirmationDialogOptionsPresent.verifyConfirmationDialogOptionsPresenceText(dialogOptions), "Some dialog options weren't found");
        Allure.step("Expected result: All confirmation dialog buttons are present");
        Assert.assertTrue(areAllConfirmationDialogOptionsPresent.verifyConfirmationDialogButtonsName(dialogOptionButtonName), "Some dialog options weren't found");
    }

    @Test(dataProvider = "providerUnsafeCharacters", dataProviderClass = TestDataProvider.class)
    @Epic("00 New Item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("TC_00.002.06 Validation that error message shown when invalid characters are entered in the projects name field")
    public void testErrorMessageDisplayedForInvalidCharactersInProjectName(String unsafeCharacter) {
        String invalidNameMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(unsafeCharacter)
                .selectFreestyleProject()
                .getInvalidNameMessage();

        Allure.step(String.format("Expected result: Error message is displayed for invalid character '%s' in project name", unsafeCharacter));
        Assert.assertEquals(invalidNameMessage, "» ‘%s’ is an unsafe character".formatted(unsafeCharacter));
    }

    @Test
    @Story("US_01.001 Add description")
    @Description("TC_01.001.04 Verify description preview")
    public void testFreestyleProjectDescriptionPreview() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String descriptionPreview = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .editDescription(DESCRIPTION)
                .clickPreview()
                .getPreviewDescriptionText();

        Allure.step(String.format("Expected result: The description preview matches the entered description '%s'", DESCRIPTION));
        Assert.assertEquals(descriptionPreview, DESCRIPTION);
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("TC_00.002.11 Verify that projects are sorted alphabetically")
    public void testJobNameSorting() {
        List<String> projectNames = List.of("aaa", "bbb", "aabb");
        projectNames.forEach(name -> TestUtils.createFreestyleProject(getDriver(), name));

        Boolean isSorted = new HomePage(getDriver())
                .isInAlphabeticalOrder();

        Allure.step("Expected result: Projects are sorted alphabetically");
        Assert.assertTrue(isSorted, "Projects is not sorted alphabetically");
    }

    @Test
    @Story("US_01.008 Build item")
    @Description("TC_01.008.08 Verify notification bar appears after performing a build")
    public void testNotificationBarAppears() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String notificationBar = new HomePage(getDriver())
                .selectBuildNowFromItemMenu(PROJECT_NAME)
                .getNotificationBarStatus();

        Allure.step(String.format("Expected result: Notification bar appears for project '%s'", PROJECT_NAME));
        Assert.assertEquals(notificationBar, "Build Now: Done.");
    }

    @Test(dependsOnMethods = "testNotificationBarAppears")
    @Story("US_01.008 Build item")
    @Description("TC_01.008.09 Verify counter of runs increases after new build is triggered")
    public void testCounterOfRunsIncrease() {
        String progressBar = new HomePage(getDriver())
                .selectBuildNowFromItemMenu(PROJECT_NAME)
                .refreshAfterBuild()
                .getNumberOfRuns();

        Allure.step(String.format("Expected result: The counter of runs increases and shows number of it for project '%s'", PROJECT_NAME));
        Assert.assertEquals(progressBar, "#2");
    }

    @Test(dependsOnMethods = "testCounterOfRunsIncrease")
    @Story("US_01.008 Build item")
    @Description("TC_01.008.10 Verify status on home page displays 'Success' after successful build")
    public void testStatusOnHomePageIsSuccess() {
        String statusBuild = new HomePage(getDriver())
                .getStatusBuild(PROJECT_NAME);

        Allure.step(String.format("Expected result: Success building status for project '%s' is displayed", PROJECT_NAME));
        Assert.assertEquals(statusBuild, "Success");
    }

    @Test
    @Story("US_01.008 Build item")
    @Description("TC_01.008.11 Verify build history is empty for newly created project")
    public void testBuildHistoryIsEmpty() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        List<String> emptyHistory = new HomePage(getDriver())
                .gotoBuildHistoryPageFromLeftPanel()
                .getListOfStatuses();

        Allure.step(String.format("Expected result: The build history for project '%s' is empty", PROJECT_NAME));
        Assert.assertEquals(emptyHistory.size(), 0);
    }

    @Test
    @Story("US_01.005 View build changes")
    @Description("TC_01.005.01 Build history updates after configuration changes and execution")
    public void testUpdateAfterExecutingBuild() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        List<String> oneExecution = new HomePage(getDriver())
                .clickScheduleBuild(PROJECT_NAME)
                .gotoBuildHistoryPageFromLeftPanel()
                .getListOfStatuses();

        Allure.step(String.format("Expected result: After executing the build, one stable build status is displayed for project '%s'",
                PROJECT_NAME), () -> {
            Assert.assertEquals(oneExecution.get(0), "stable");
            Assert.assertEquals(oneExecution.size(), 1);
        });
    }

    @Test(dependsOnMethods = "testUpdateAfterExecutingBuild")
    @Story("US_01.005 View build changes")
    @Description("TC_01.005.02 Verify build history updates after configuration change and executing another build")
    public void testUpdateAfterChangingConfig() {
        List<String> changeConfig = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .addBuildStep("Run with timeout")
                .getHeader()
                .gotoHomePage()
                .clickScheduleBuild(PROJECT_NAME)
                .gotoBuildHistoryPageFromLeftPanel()
                .getListOfStatuses();

        Allure.step(String.format("Expected result: After changing the config, there should be two executions for project '%s'", PROJECT_NAME));
        Assert.assertEquals(changeConfig.size(), 2);
    }

    @Test
    @Story("US_01.006 Workspace view")
    @Description("TC_01.006.01 Verify workspace is opened and displayed correctly")
    public void testWorkspaceIsOpened() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String workspace = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickBuildNowSidebarAndWaite()
                .clickWorkspaceSidebar()
                .getBreadCrumb();

        Allure.step(String.format("Expected result: The workspace for project '%s' should be opened and displayed correctly", PROJECT_NAME));
        Assert.assertEquals(workspace, "Workspace of " + PROJECT_NAME + " on Built-In Node");
    }

    @Test(dependsOnMethods = "testWorkspaceIsOpened")
    @Story("US_01.005 View build changes")
    @Description("TC_01.005.03 Verify the last build is opened and displayed correctly")
    public void testLastBuildIsOpened() {
        String secondBuild = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickBuildNowSidebarAndWaite()
                .clickWorkspaceSidebar()
                .clickOnSuccessBuildIconForLastBuild()
                .getBreadCrumb();

        Allure.step(String.format("Expected result: The last build for project '%s' is displayed correctly", PROJECT_NAME));
        Assert.assertEquals(secondBuild, "#2");
    }

    @Test(dependsOnMethods = "testBuildHistoryIsEmpty")
    @Story("US_01.004 Delete Project")
    @Description("TC_01.004.04 Delete the project via breadcrumb dropdown")
    public void testDeleteViaBreadcrumbDropdown() {
        List<String> projectList = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .openBreadcrumbDropdown()
                .clickDeleteBreadcrumbDropdownAndConfirm()
                .getItemList();

        Allure.step(String.format("Expected result: The project '%s' is not listed on Home page", PROJECT_NAME));
        Assert.assertListNotContainsObject(projectList, PROJECT_NAME, "Project is not deleted.");
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.001 Create Freestyle Project")
    @Description("TC_00.001.12 Create project from existing")
    public void testCreateFreestyleProjectFromExistingOne() {
        String secondProjectName = "Second" + PROJECT_NAME;
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        List<String> itemNameList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(secondProjectName)
                .enterName(PROJECT_NAME)
                .clickOkLeadingToCofigPageOfCopiedProject(new FreestyleConfigPage(getDriver()))
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Allure.step(String.format("Expected result: The project '%s' is displayed on Home page", secondProjectName));
        Assert.assertTrue(itemNameList.contains(secondProjectName));
    }

    @Test
    @Story("US_01.003 Disable/Enable Project")
    @Description("TC_01.003.01 Disability default state")
    public void testDefaultState() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        boolean currentState = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .getEnablingCurrentState();

        Allure.step(String.format("Expected result: The current state of the project '%s' is enabled", PROJECT_NAME));
        Assert.assertTrue(currentState);
    }

    @Test(dependsOnMethods = "testDefaultState")
    @Story("US_01.003 Disable/Enable Project")
    @Description("TC_01.003.02 FreestyleProject can be disabled")
    public void testDisableEnabled() {
        String indicatorText = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .changeEnablingState()
                .getDisabledProjectIndicator();

        Allure.step(String.format("Expected result: The project '%s' is disabled and shows the correct indicator text", PROJECT_NAME));
        Assert.assertEquals(indicatorText, "This project is currently disabled\n" + "Enable");
    }

    @Test(dependsOnMethods = "testDisableEnabled")
    @Story("US_01.003 Disable/Enable Project")
    @Description("TC_01.003.04 Disabled FreestyleProject can be enabled via indicator")
    public void testEnableWithIndicator() {
        boolean currentState = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .changeEnablingStateViaIndicator()
                .getHeader()
                .gotoHomePage()
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .getEnablingCurrentState();

        Allure.step(String.format("Expected result: The project '%s' is enabled", PROJECT_NAME));
        Assert.assertTrue(currentState);
    }

    @Test(dependsOnMethods = "testEnableWithIndicator")
    @Story("US_01.003 Disable/Enable Project")
    @Description("TC_01.003.03 Disabled FreestyleProject can be enabled from project's page")
    public void testEnabledFromProjectPage() {
        boolean currentState = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .changeEnablingState()
                .getHeader()
                .gotoHomePage()
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .changeEnablingState()
                .getHeader()
                .gotoHomePage()
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .getEnablingCurrentState();

        Allure.step(String.format("Expected result: The project '%s' is enabled", PROJECT_NAME));
        Assert.assertTrue(currentState);
    }
}