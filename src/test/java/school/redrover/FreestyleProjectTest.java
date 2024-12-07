package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.FreestyleProjectPage;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FreestyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "MyFreestyleProject";
    private static final String FREESTYLE_PROJECT = "Freestyle project";
    private static final String DESCRIPTION = "Bla-bla-bla project";
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
    public void testCreateProjectViaCreateJobButton() {
        String actualProjectName = new HomePage(getDriver())
                .clickCreateJob()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(actualProjectName, PROJECT_NAME);
    }

    @Test
    public void testCreateProjectViaSidebarMenu() {
        String actualProjectName = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(actualProjectName, PROJECT_NAME);
    }

    @Test
    public void testCreateFreestyleProjectFromMyViews() {
        List<String> projectName = new HomePage(getDriver())
                .clickMyViewsButton()
                .clickCreateJob()
                .enterItemName(PROJECT_NAME)
                .selectTypeProject(FREESTYLE_PROJECT)
                .clickOkButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(projectName.size(), 1);
        Assert.assertEquals(projectName.get(0), PROJECT_NAME);
    }

    @Test
    public void testCreateFreestyleProjectWithDurationCheckbox() {
        String periodCheckbox = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .selectDurationCheckbox("minute")
                .clickSaveButton()
                .gotoHomePage()
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .getTimePeriod();

        Assert.assertEquals(periodCheckbox, "minute");
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateProjectViaCreateJobButton")
    public void testAddDescription() {
        String description = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .editDescription(DESCRIPTION)
                .clickSubmitButton()
                .getDescription();

        Assert.assertEquals(description, DESCRIPTION);
    }

    @Ignore
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

    @Ignore
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
                .clickRenameSidebar()
                .clearOldAndInputNewProjectName(newName)
                .clickRenameButton()
                .getProjectName();

        Assert.assertEquals(actualProjectName, newName);
    }

    @Test(dependsOnMethods = "testCreateProjectViaCreateJobButton")
    public void testRenameProjectViaDropdown() {
        final String newName = "New " + PROJECT_NAME;

        String actualProjectName = new HomePage(getDriver())
                .openDropdownViaChevron(PROJECT_NAME)
                .clickRenameInProjectDropdown(PROJECT_NAME)
                .clearOldAndInputNewProjectName(newName)
                .clickRenameButton()
                .getProjectName();

        Assert.assertEquals(actualProjectName, newName);
    }

    @Test
    public void testCheckSidebarMenuItemsOnProjectPage() {
        final List<String> templateSidebarMenu = List.of(
                "Status", "Changes", "Workspace", "Build Now", "Configure", "Delete Project", "Rename");

        List<String> actualSidebarMenu = new HomePage(getDriver())
                .createFreestyleProject(PROJECT_NAME)
                .openFreestyleProject(PROJECT_NAME)
                .getSidebarOptionList();

        Assert.assertEquals(actualSidebarMenu, templateSidebarMenu);
    }

    @Test
    public void testConfigureProjectAddBuildStepsExecuteShellCommand() {
        final String testCommand = "echo \"TEST! Hello Jenkins!\"";

        String extractedText = new HomePage(getDriver())
                .createFreestyleProject(PROJECT_NAME)
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
        String buildInfo = new HomePage(getDriver())
                .createFreestyleProject(PROJECT_NAME)
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

    @Test
    public void testDeleteLastBuild() {
        FreestyleProjectPage freestyleProjectPage = new HomePage(getDriver())
                .createFreestyleProject(PROJECT_NAME)
                .openFreestyleProject(PROJECT_NAME)
                .clickBuildNowSidebar();
        String lastBuildNumber = freestyleProjectPage.getLastBuildNumber();

        freestyleProjectPage
                .clickOnSuccessBuildIconForLastBuild()
                .clickDeleteBuildSidebar()
                .confirmDeleteBuild();

        Assert.assertListNotContainsObject(freestyleProjectPage.getListOfBuilds(), lastBuildNumber, "The last build wasn't deleted");
    }

    @Test
    public void testDeleteProjectViaSidebarMenuOnProjectPage() {
        String welcomeText = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .clickDeleteProjectSidebar()
                .clickYesToConfirmDelete()
                .getWelcomeTitle();

        Assert.assertEquals(welcomeText, "Welcome to Jenkins!", "There is a project on Dashboard");
    }

    @Test
    public void testDeleteProjectViaDropdown() {
        String welcomeText = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .gotoHomePage()

                .openDropdownViaChevron(PROJECT_NAME)
                .clickDeleteInProjectDropdown(PROJECT_NAME)
                .clickYesForConfirmDelete()
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
        TestUtils.createFreestyleProject(this, FREESTYLE_PROJECT);

        String descriptionPreview = new HomePage(getDriver())
                .openFreestyleProject(FREESTYLE_PROJECT)
                .editDescription(DESCRIPTION)
                .clickPreview()
                .getPreviewDescriptionText();

        Assert.assertEquals(descriptionPreview, DESCRIPTION);
    }

    @Test
    public void testJobNameSorting() {
        HomePage homePage = new HomePage(getDriver());

        List<String> projectNames = List.of("aaa", "bbb", "aabb");
        projectNames.forEach(homePage::createFreestyleProject);

        // This XPath targets the links containing the job names
        List<WebElement> jobLinks = getDriver()
                .findElements(By.xpath("//table[@id='projectstatus']//tbody//tr/td[3]/a"));

        // Extract text from the elements
        List<String> actualOrder = new ArrayList<>();
        for (WebElement link : jobLinks) {
            actualOrder.add(link.getText().trim());
        }

        // Create a copy of the list and sort it alphabetically for expected order
        List<String> expectedOrder = new ArrayList<>(actualOrder);
        Collections.sort(expectedOrder); // Ascending order

        // Verify if the actual order matches the expected order
        Assert.assertEquals(actualOrder, expectedOrder);
    }

}