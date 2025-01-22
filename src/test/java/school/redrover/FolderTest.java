package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.page.folder.FolderProjectPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

@Epic("04 Folder")
public class FolderTest extends BaseTest {

    private static final String FREESTYLE_PROJECT_NAME = "FirstFreestyleProjectJob";
    private static final String FOLDER_NAME_MAX_LENGTH = "012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234";
    private static final String FOLDER_NAME_MIN_LENGTH = "F";
    private static final String FOLDER_MOVE_PARENT_NAME = "FolderParent";
    private static final String FOLDER_MOVE_CHILD_NAME = "FolderChild";
    private static final String FOLDER_MOVE_CHILD2_NAME = "FolderChild2";
    private static final String FOLDER_NAME = "FolderName";
    private static final String NEW_FOLDER_NAME = "NewFolderName";
    private static final String ERROR_MESSAGE_ON_RENAME_WITH_SAME_NAME = "The new name is the same as the current name.";
    private static final String ERROR_MESSAGE_ON_RENAME_WITH_EMPTY_NAME = "No name is specified";
    private static final String DESCRIPTION = "Description text";
    private static final String DESCRIPTION_EDITED = "Edited";

    private String escapeHtml(String input) {
        if (input == null) return null;
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    @DataProvider
    public Object[][] providerUnsafeCharacters() {

        return new Object[][]{
                {"\\"}, {"]"}, {":"}, {"#"}, {"&"}, {"?"}, {"!"}, {"@"},
                {"$"}, {"%"}, {"^"}, {"*"}, {"|"}, {"/"}, {"<"}, {">"},
                {"["}, {";"}
        };
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.006 Create Folder")
    @Description("TC_00.006.02 Create Folder with max name length")
    public void testCreateWithMaxNameLength() {
        String folderName = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_NAME_MAX_LENGTH)
                .selectFolderAndClickOk()
                .getHeader()
                .gotoHomePage()
                .getItemNameByOrder(1);

        Allure.step(String.format("Expected Result: The folder with the maximum allowed name length '%s' is created.", FOLDER_NAME_MAX_LENGTH));
        Assert.assertEquals(folderName, FOLDER_NAME_MAX_LENGTH);
    }

    @Test
    @Story("US_04.004 Add and edit description of the folder")
    @Description("TC_04.004.01 Add description to the folder")
    public void testAddDescription() {
        TestUtils.createFolder(getDriver(), FOLDER_NAME);

        String finalResult = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .editDescription(DESCRIPTION)
                .clickSubmitButton()
                .getDescription();

        Allure.step(String.format("Expected Result: The description '%s' is successfully added to the folder and displayed correctly.", DESCRIPTION));
        Assert.assertEquals(finalResult, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testAddDescription")
    @Story("US_04.004 Add and edit description of the folder")
    @Description("TC_04.004.02 Edit an existing folder's description")
    public void testEditExistingDescription() {
        String finalResult = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .editDescription(DESCRIPTION_EDITED)
                .clickSubmitButton()
                .getDescription();

        Allure.step(String.format("Expected Result: The description '%s' is successfully edited and displayed correctly.", DESCRIPTION_EDITED));
        Assert.assertEquals(finalResult, DESCRIPTION_EDITED);
    }

    @Test(dependsOnMethods = "testEditExistingDescription")
    @Story("US_04.004 Add and edit description of the folder")
    @Description("TC_04.004.04 Activate 'Preview' option while adding a description")
    public void testDescriptionsPreviewButton() {
        String finalResult = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .getDescriptionViaPreview();

        Allure.step(String.format("Expected Result: The description '%s' is correctly displayed in the preview mode.", DESCRIPTION_EDITED));
        Assert.assertEquals(finalResult, DESCRIPTION_EDITED);
    }

    @Test(dependsOnMethods = "testDescriptionsPreviewButton")
    @Story("US_04.004 Add and edit description of the folder")
    @Description("TC_04.004.03 Clear folder's description")
    public void testClearDescription() {
        String finalResult = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .clearDescription()
                .getDescriptionButtonText();

        Allure.step("Expected Result: The description button shows 'Add description'");
        Assert.assertEquals(finalResult, "Add description");
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.006 Create Folder")
    @Description("TC_00.006.01 Create Folder with min name length")
    public void testCreateWithMinNameLength() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_NAME_MIN_LENGTH)
                .selectFolderAndClickOk()
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Allure.step(String.format("Expected Result: The list of items contains '%d' folder(s). \n " +
                "The folder with the minimum allowed name length '%s' is created.", itemList.size(), FOLDER_NAME_MIN_LENGTH));
        Assert.assertEquals(itemList.size(), 1);
        Assert.assertEquals(itemList.get(0), FOLDER_NAME_MIN_LENGTH);
    }

    @Test(dependsOnMethods = "testCreateWithMinNameLength")
    @Story("US_04.005 Configure folder")
    @Description("TC_04.005.01 Configure name by chevron")
    public void testConfigureNameByChevron() {
        FolderProjectPage folderProjectPage = new HomePage(getDriver())
                .selectConfigureFromItemMenu(FOLDER_NAME_MIN_LENGTH)
                .enterConfigurationName(FOLDER_NAME)
                .clickSaveButton();

        Allure.step(String.format("Expected Result: The folder '%s' is successfully configured with the name '%s'.",
                FOLDER_NAME_MIN_LENGTH, FOLDER_NAME));
        Assert.assertEquals(folderProjectPage.getConfigurationName(), FOLDER_NAME);
        Assert.assertEquals(folderProjectPage.getFolderName(), FOLDER_NAME_MIN_LENGTH);
    }

    @Test
    @Story("US_04.005 Configure folder")
    @Description("TC_04.005.02 Configure description by chevron")
    public void testConfigureDescriptionByChevron() {
        String desc = new HomePage(getDriver())
                .clickNewItem()
                .nameAndSelectFolderType(FOLDER_NAME)
                .getHeader()
                .gotoHomePage()
                .selectConfigureFromItemMenu(FOLDER_NAME)
                .enterDescription(DESCRIPTION)
                .clickSaveButton()
                .getFolderDescription();

        Allure.step(String.format("Expected Result: The description '%s' is successfully added and displayed correctly.", DESCRIPTION));
        Assert.assertEquals(desc,DESCRIPTION);
    }

    @Test(dependsOnMethods = "testConfigureDescriptionByChevron")
    @Epic("00 New Item")
    @Story("US_00.004 Create new item from other existing")
    @Description("TC_00.004.01 Create new item from folder by chevron")
    public void testCreateNewItemByChevron() {
        String projectName = new HomePage(getDriver())
                .selectNewItemFromFolderMenu(FOLDER_NAME)
                .nameAndSelectFreestyleProject(FREESTYLE_PROJECT_NAME)
                .addExecuteWindowsBatchCommand("echo 'Hello world!'")
                .clickSaveButton()
                .getHeader()
                .gotoHomePage()
                .openFolder(FOLDER_NAME)
                .getItemNameByOrder(1);

        Allure.step(String.format("Expected Result: The new item '%s' is successfully created from the folder '%s' by chevron.",
                FREESTYLE_PROJECT_NAME, FOLDER_NAME));
        Assert.assertEquals(projectName, FREESTYLE_PROJECT_NAME);
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.004 Create new item from other existing")
    @Description("TC_00.004.02 Create new item from folder page")
    public void testCreateNewItemFromFolderPage() {
        TestUtils.createFolder(getDriver(), FOLDER_NAME);

        String projectName = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .clickNewItem()
                .nameAndSelectFreestyleProject(FREESTYLE_PROJECT_NAME)
                .getHeader()
                .gotoHomePage()

                .openFolder(FOLDER_NAME)
                .getItemNameByOrder(1);

        Allure.step(String.format("Expected Result: The new item '%s' is successfully created from the folder '%s'.",
                FREESTYLE_PROJECT_NAME, FOLDER_NAME));
        Assert.assertEquals(projectName, FREESTYLE_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreateNewItemFromFolderPage")
    @Story("US_04.006 Folder Buil History")
    @Description("TC_04.006.01 Open Build history by chevron")
    public void testOpenBuildHistoryByChevron() {
        String buildHistoryName = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .runJob(FREESTYLE_PROJECT_NAME)
                .getHeader()
                .gotoHomePage()
                .selectBuildHistoryFromItemMenu(FOLDER_NAME)
                .getProjectName();

        Allure.step(String.format("Expected Result: The build history for the folder '%s' correctly shows the project '%s'",
                FOLDER_NAME, FREESTYLE_PROJECT_NAME));
        Assert.assertEquals(buildHistoryName, "%s » %s".formatted(FOLDER_NAME, FREESTYLE_PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testOpenBuildHistoryByChevron")
    @Story("US_04.005 Configure folder")
    @Description("TC_04.005.03 Configure name by Sidebar menu")
    public void testAddDisplayName() {
        final String displayName = "DisplayName";

        List<String> projectList = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .clickSidebarConfigButton()
                .enterConfigurationName(displayName)
                .clickSaveButton()
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Allure.step(String.format("Expected Result: The display name '%s' is successfully added and shown in the project list.", displayName));
        Assert.assertListContainsObject(projectList, displayName, "Display name is not added");
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.006 Create Folder")
    @Description("TC_00.006.03 Error message when trying to create a folder with dot in the end of name")
    public void testErrorDuringCreationWithDotInEnd() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_NAME + ".")
                .selectFolderType()
                .getInvalidNameMessage();

        Allure.step("Expected Result: An error message indicating that a name cannot end with a dot is displayed.");
        Assert.assertEquals(errorMessage, "» A name cannot end with ‘.’");
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.006 Create Folder")
    @Description("TC_00.006.07 Error message after trying to create a folder with dot in the end of name")
    public void testErrorAfterCreationWithDotInEnd() {

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_NAME + ".")
                .selectFolderType()
                .selectFolderType()
                .clickOkButtonLeadingToErrorPage()
                .getErrorMessage();

        Allure.step("Expected Result: An error message indicating that a name cannot end with a dot is displayed on new page.");
        Assert.assertEquals(errorMessage, "A name cannot end with ‘.’");
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.006 Create Folder")
    @Description("TC_00.006.04 Forbidden to create a folder with empty name")
    public void testErrorEmptyNameCreation() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .selectFolderType()
                .getEmptyNameMessage();

        Allure.step("Expected Result: An error message indicating that the name field cannot be empty is displayed.");
        Assert.assertEquals(errorMessage, "» This field cannot be empty, please enter a valid name");
    }

    @Test()
    @Epic("00 New Item")
    @Story("US_00.006 Create Folder")
    @Description("TC_00.006.05 Forbidden to create a folder with duplicate name")
    public void testErrorDuplicateNameCreation() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .nameAndSelectFolderType(FOLDER_NAME)
                .getHeader()
                .gotoHomePage()
                .clickNewItem()
                .enterItemName(FOLDER_NAME).selectFolderType()
                .getInvalidNameMessage();

        Allure.step(String.format("Expected Result: An error message indicating that a job already exists with the name '%s' is displayed.", FOLDER_NAME));
        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘%s’".formatted(FOLDER_NAME));
    }

    @Test
    @Story("US_04.003 Delete Folder")
    @Description("TC_04.003.01 Delete from the main page via Dropdown menu")
    public void testDeleteViaMainPageChevron() {
        TestUtils.createFolder(getDriver(), FOLDER_NAME);

        String welcomeTitle = new HomePage(getDriver())
                .selectDeleteFromItemMenuAndClickYes(FOLDER_NAME)
                .getWelcomeTitle();

        Allure.step("Expected Result: 'Welcome to Jenkins' message is displayed on the page");
        Assert.assertEquals(welcomeTitle, "Welcome to Jenkins!");
    }

    @Test
    @Story("US_04.003 Delete Folder")
    @Description("TC_04.003.02 Delete directly from folder's page")
    public void testDeleteViaSidebarFromProjectPage() {
        TestUtils.createFolder(getDriver(), FOLDER_NAME);

        String welcomeTitle = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .clickDeleteButtonSidebarAndConfirm()
                .getWelcomeTitle();

        Allure.step("Expected Result: 'Welcome to Jenkins' message is displayed on the page");
        Assert.assertEquals(welcomeTitle, "Welcome to Jenkins!");
    }

    @Test
    @Story("US_04.003 Delete Folder")
    @Description("TC_04.003.04 Cancel deleting in 'Delete folder' window")
    public void testCancelDeletingViaSidebarProjectPage() {
        TestUtils.createFolder(getDriver(), FOLDER_NAME);

        List<String> setOfProjects = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .cancelDeletingViaModalWindow()
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Allure.step(String.format("Expected Result: The folder '%s' is not deleted and still present in the project list.", FOLDER_NAME));
        Assert.assertTrue(setOfProjects.contains(FOLDER_NAME));
    }

    @Test
    @Story("US_04.003 Delete Folder")
    @Description("TC_04.003.03 Delete from 'My views' page via 'Dropdown menu'")
    public void testDeleteViaMyViewChevron() {
        TestUtils.createFolder(getDriver(), FOLDER_NAME);

        String welcomeTitle = new HomePage(getDriver())
                .clickMyViewsButton()
                .selectDeleteFromItemMenuAndClickYes(FOLDER_NAME)
                .getHeader()
                .gotoHomePage()
                .getWelcomeTitle();

        Allure.step("Expected Result: 'Welcome to Jenkins' message is displayed on the page");
        Assert.assertEquals(welcomeTitle, "Welcome to Jenkins!");
    }

    @Test
    @Story("US_04.002 Move Folder to Folder")
    @Description("TC_04.002.01 Move directly from folder's page")
    public void testMoveFromFoldersPage() {
        List<String> nameProjectList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_MOVE_PARENT_NAME)
                .selectFolderAndClickOk()
                .getHeader()
                .gotoHomePage()

                .clickNewItem()
                .enterItemName(FOLDER_MOVE_CHILD_NAME)
                .selectFolderAndClickOk()
                .getHeader()
                .gotoHomePage()

                .openFolder(FOLDER_MOVE_CHILD_NAME)
                .clickMoveOnSidebar()
                .selectParentFolderAndClickMove(FOLDER_MOVE_PARENT_NAME)
                .getBreadcrumsBarItemsList();

        Allure.step(String.format("Expected Result: The folder '%s' is successfully moved to the parent folder '%s' and the breadcrumbs are updated accordingly.",
                FOLDER_MOVE_CHILD_NAME, FOLDER_MOVE_PARENT_NAME));
        Assert.assertEquals(nameProjectList, List.of("Dashboard", FOLDER_MOVE_PARENT_NAME, FOLDER_MOVE_CHILD_NAME));
    }

    @Test(dependsOnMethods = "testMoveFromFoldersPage")
    @Story("US_04.002 Move Folder to Folder")
    @Description("TC_04.002.04 Move folders which are on the same level")
    public void testMoveFromTheSameLevel() {
        List<String> nameProjectsList = new HomePage(getDriver())
                .openFolder(FOLDER_MOVE_PARENT_NAME)
                .clickNewItem()
                .enterItemName(FOLDER_MOVE_CHILD2_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .getHeader()
                .gotoHomePage()
                .openFolder(FOLDER_MOVE_PARENT_NAME)
                .openFolder(FOLDER_MOVE_CHILD2_NAME)
                .clickMoveOnSidebar()
                .selectParentFolderAndClickMove(FOLDER_MOVE_PARENT_NAME + "/" + FOLDER_MOVE_CHILD_NAME)
                .getBreadcrumsBarItemsList();

        Allure.step(String.format("Expected Result: The folder '%s' is successfully moved to the folder '%s' which is at the same level, and the breadcrumbs are updated accordingly.",
                FOLDER_MOVE_CHILD2_NAME, FOLDER_MOVE_CHILD_NAME));
        Assert.assertEquals(nameProjectsList, List.of("Dashboard", FOLDER_MOVE_PARENT_NAME, FOLDER_MOVE_CHILD_NAME, FOLDER_MOVE_CHILD2_NAME));
    }

    @Test(dependsOnMethods = "testMoveFromTheSameLevel")
    @Story("US_04.002 Move Folder to Folder")
    @Description("TC_04.002.07 No possibility to move to the same place")
    public void testTryToMoveInTheSamePlace() {
        List<String> nameProjectsListBreadcrumbs = new HomePage(getDriver())
                .openFolder(FOLDER_MOVE_PARENT_NAME)
                .openFolder(FOLDER_MOVE_CHILD_NAME)
                .clickMoveOnSidebar()
                .selectParentFolderAndClickMove(FOLDER_MOVE_PARENT_NAME)
                .getBreadcrumsBarItemsList();

        Allure.step("Expected Result: The move action is not allowed to proceed, and the breadcrumb remains unchanged.");
        Assert.assertEquals(nameProjectsListBreadcrumbs, List.of("Dashboard", FOLDER_MOVE_PARENT_NAME, FOLDER_MOVE_CHILD_NAME, "Move"));
    }

    @Test(dependsOnMethods = "testMoveFromTheSameLevel")
    @Story("US_04.002 Move Folder to Folder")
    @Description("TC_04.002.05 Move from lower level to upper level")
    public void testMoveOnTheHigherLevel() {
        List<String> nameProjectsList = new HomePage(getDriver())
                .openFolder(FOLDER_MOVE_PARENT_NAME)
                .openFolder(FOLDER_MOVE_CHILD_NAME)
                .openFolder(FOLDER_MOVE_CHILD2_NAME)
                .clickMoveOnSidebar()
                .selectParentFolderAndClickMove(FOLDER_MOVE_PARENT_NAME)
                .getBreadcrumsBarItemsList();

        Allure.step(String.format("Expected Result: The folder '%s' is successfully moved from '%s/%s' to the higher level '%s', and the breadcrumbs are updated accordingly.",
                FOLDER_MOVE_CHILD2_NAME, FOLDER_MOVE_PARENT_NAME, FOLDER_MOVE_CHILD_NAME, FOLDER_MOVE_PARENT_NAME));
        Assert.assertEquals(nameProjectsList, List.of("Dashboard", FOLDER_MOVE_PARENT_NAME, FOLDER_MOVE_CHILD2_NAME));
    }

    @Test(dependsOnMethods = "testMoveOnTheHigherLevel")
    @Story("US_04.002 Move Folder to Folder")
    @Description("TC_04.002.06 No possibility to move parent folder to child folder")
    public void testNoOptionsToMoveParentIntoChild() {
        List<String> itemsSidebar = new HomePage(getDriver())
                .openFolder(FOLDER_MOVE_PARENT_NAME)
                .getListOfItemsSidebar();

        Allure.step(String.format("Expected Result: The option to move the parent folder '%s' into the child folder is not available in the sidebar menu.",
                FOLDER_MOVE_PARENT_NAME));
        Assert.assertListNotContains(itemsSidebar, item -> item.contains("Move"), "list of sidebar items contains Move");
    }

    @Test
    @Story("US_04.002 Move Folder to Folder")
    @Description("TC_04.002.02 Move from the main page via 'Dropdown menu'")
    public void testMoveViaChevronMainPage() {
        List<String> nameProjectsList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_MOVE_PARENT_NAME)
                .selectFolderAndClickOk()
                .getHeader()
                .gotoHomePage()

                .clickNewItem()
                .enterItemName(FOLDER_MOVE_CHILD_NAME)
                .selectFolderAndClickOk()
                .getHeader()
                .gotoHomePage()

                .selectMoveFromItemMenuByChevron(FOLDER_MOVE_CHILD_NAME)
                .selectParentFolderAndClickMove(FOLDER_MOVE_PARENT_NAME)
                .getBreadcrumsBarItemsList();

        Allure.step(String.format("Expected Result: The folder '%s' is successfully moved from the main page to the parent folder '%s' using the dropdown menu, " +
                        "and the breadcrumbs are updated accordingly.", FOLDER_MOVE_CHILD_NAME, FOLDER_MOVE_PARENT_NAME));
        Assert.assertEquals(nameProjectsList, List.of("Dashboard", FOLDER_MOVE_PARENT_NAME, FOLDER_MOVE_CHILD_NAME));
    }

    @Test
    @Story("US_04.002 Move Folder to Folder")
    @Description("TC_04.002.03 Move from 'My views' page via 'Dropdown menu'")
    public void testMoveViaChevronMyView() {
        List<String> nameProjectsList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_MOVE_PARENT_NAME)
                .selectFolderAndClickOk()
                .getHeader()
                .gotoHomePage()

                .clickNewItem()
                .enterItemName(FOLDER_MOVE_CHILD_NAME)
                .selectFolderAndClickOk()
                .getHeader()
                .gotoHomePage()

                .clickMyViewsButton()
                .selectMoveFromItemMenuByChevron(FOLDER_MOVE_CHILD_NAME)
                .selectParentFolderAndClickMove(FOLDER_MOVE_PARENT_NAME)
                .getBreadcrumsBarItemsList();

        Allure.step(String.format("Expected Result: The folder '%s' is successfully moved from 'My views' page to the parent folder '%s' using the dropdown menu, " +
                "and the breadcrumbs are updated accordingly.", FOLDER_MOVE_CHILD_NAME, FOLDER_MOVE_PARENT_NAME));
        Assert.assertEquals(nameProjectsList, List.of("Dashboard", FOLDER_MOVE_PARENT_NAME, FOLDER_MOVE_CHILD_NAME));
    }

    @Test
    @Story("US_04.001 Rename Folder")
    @Description("TC_04.001.08 Rename Folder using sidebar menu")
    public void testRenameViaSidebar() {
        TestUtils.createFolder(getDriver(), FOLDER_NAME);

        List<String> projectList = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName(NEW_FOLDER_NAME)
                .clickRenameButton()
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Allure.step(String.format("Expected Result: The folder '%s' is successfully renamed to '%s' using the sidebar menu."
                , FOLDER_NAME, NEW_FOLDER_NAME));
        Assert.assertListContainsObject(projectList, NEW_FOLDER_NAME, "Folder is not renamed");
    }

    @Test
    @Story("US_04.001 Rename Folder")
    @Description("TC_04.001.05 Validate Error message, if New Folder Name is the same as Old Name")
    public void testErrorMessageOnRenameFolderWithSameName() {
        TestUtils.createFolder(getDriver(), FOLDER_NAME);

        String actualErrorMessage = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .clickRenameSidebarButton()
                .clickRenameButtonLeadingToError()
                .getErrorMessage();

        Allure.step(String.format("Expected Result: An error message '%s' is displayed indicating that the new folder " +
                "name '%s' cannot be the same as the old name.", ERROR_MESSAGE_ON_RENAME_WITH_SAME_NAME, FOLDER_NAME));
        Assert.assertEquals(actualErrorMessage, ERROR_MESSAGE_ON_RENAME_WITH_SAME_NAME);
    }

    @Test(dependsOnMethods = "testRenameViaSidebar")
    @Story("US_04.001 Rename Folder")
    @Description("TC_04.001.09 Rename Folder using breadcrumbs dropdown")
    public void testRenameViaBreadcrumbDropdown() {
        List<String> projectList = new HomePage(getDriver())
                .openFolder(NEW_FOLDER_NAME)
                .openBreadcrumbDropdown()
                .clickRenameBreadcrumbDropdown()
                .clearInputFieldAndTypeName(FOLDER_NAME)
                .clickRenameButton()
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Allure.step(String.format("Expected Result: The folder '%s' is successfully renamed to '%s' using the breadcrumbs dropdown menu",
                NEW_FOLDER_NAME, FOLDER_NAME));
        Assert.assertListContainsObject(projectList, FOLDER_NAME, "Folder is not renamed");
    }

    @Test(dependsOnMethods = "testRenameViaBreadcrumbDropdown")
    @Story("US_04.001 Rename Folder")
    @Description("TC_04.001.04 Rename Folder from the My Views directly via folder's page")
    public void testRenameFolderFromMyViewsViaFolderPage() {
        List<String> projectList = new HomePage(getDriver())
                .clickMyViewsButton()
                .openFolder(FOLDER_NAME)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName(NEW_FOLDER_NAME)
                .clickRenameButton()
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Allure.step(String.format("Expected Result: The folder '%s' is successfully renamed to '%s' from the 'My Views' " +
                "page via the folder's sidebar menu.", FOLDER_NAME, NEW_FOLDER_NAME));
        Assert.assertListContainsObject(projectList, NEW_FOLDER_NAME, "Folder is not renamed");
    }

    @Test(dependsOnMethods = "testRenameFolderFromMyViewsViaFolderPage")
    @Story("US_04.001 Rename Folder")
    @Description("TC_04.001.03 Rename Folder from the My Views via 'Drop-down menu'")
    public void testRenameFolderFromMyViewsViaDropdownMenu() {
        List<String> projectList = new HomePage(getDriver())
                .clickMyViewsButton()
                .openFolder(NEW_FOLDER_NAME)
                .openBreadcrumbDropdown()
                .clickRenameBreadcrumbDropdown()
                .clearInputFieldAndTypeName(FOLDER_NAME)
                .clickRenameButton()
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Allure.step(String.format("Expected Result: The folder '%s' is successfully renamed to '%s' from the 'My Views' " +
                "page using the dropdown menu.", NEW_FOLDER_NAME, FOLDER_NAME));
        Assert.assertListContainsObject(projectList, FOLDER_NAME, "Folder is not renamed");
    }

    @Test(dependsOnMethods = "testRenameFolderFromMyViewsViaDropdownMenu")
    @Story("US_04.001 Rename Folder")
    @Description("TC_04.001.07 Validate Error message, if New Folder Name is empty")
    public void testRenameFolderToEmptyName() {
        String actualErrorMessage = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName("")
                .clickRenameButtonLeadingToError()
                .getErrorMessage();

        Allure.step(String.format("Expected Result: An error message '%s' is displayed indicating that the new folder name cannot be empty."
                , ERROR_MESSAGE_ON_RENAME_WITH_EMPTY_NAME));
        Assert.assertEquals(actualErrorMessage, ERROR_MESSAGE_ON_RENAME_WITH_EMPTY_NAME);
    }

    @Test(dependsOnMethods = "testRenameFolderToEmptyName")
    @Story("US_04.003 Delete Folder")
    @Description("TC_04.003.05 Delete folder Via breadcrumbs dropdown")
    public void testDeleteViaBreadcrumbDropdown() {
        List<String> projectList = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .openBreadcrumbDropdown()
                .clickDeleteBreadcrumbDropdownAndConfirm()
                .getItemList();

        Allure.step(String.format("Expected Result: The folder '%s' is successfully deleted via the breadcrumbs dropdown menu."
                , FOLDER_NAME));
        Assert.assertListNotContainsObject(projectList, FOLDER_NAME, "Folder is not deleted.");
    }

    @Test(dataProvider = "providerUnsafeCharacters")
    @Story("US_04.001 Rename Folder")
    @Description("TC_04.001.06 Validate Error message, if New Folder Name contains special characters")
    public void testRenameFolderWithUnsafeCharactersInName(String unsafeCharacter) {
        TestUtils.createFolder(getDriver(), FOLDER_NAME);

        String invalidNameMessage = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName(unsafeCharacter)
                .clickRenameButtonLeadingToError()
                .getErrorMessage();

        Allure.step(String.format("Expected Result: An error message is displayed indicating that '%s' is an unsafe character.", escapeHtml(unsafeCharacter)));
        Assert.assertEquals(invalidNameMessage, "‘%s’ is an unsafe character".formatted(escapeHtml(unsafeCharacter)));
    }
}
