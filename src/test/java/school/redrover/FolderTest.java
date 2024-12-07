package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.page.FolderProjectPage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FolderTest extends BaseTest {

    private static final String FIRST_FOLDER_NAME = "FreestyleProjects";
    private static final String FREESTYLE_PROJECT_NAME = "FirstFreestyleProjectJob";
    private static final String FOLDER_NAME_MAX_LENGTH = "012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234";
    private static final String FOLDER_MOVE_PARENT_NAME = "FolderParent";
    private static final String FOLDER_MOVE_CHILD_NAME = "FolderChild";
    private static final String FOLDER_MOVE_CHILD2_NAME = "FolderChild2";

    @Test
    public void testCreateWithMaxNameLength() {

        String folderName = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_NAME_MAX_LENGTH)
                .selectFolderAndClickOk()
                .gotoHomePage()
                .getItemNameByOrder(1);

        Assert.assertEquals(folderName, FOLDER_NAME_MAX_LENGTH);
    }

    @Test
    public void testCreateWithMinNameLength() {

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("F")
                .selectFolderAndClickOk()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(itemList.size(), 1);
        Assert.assertEquals(itemList.get(0), "F");
    }

    @Test(dependsOnMethods = "testCreateWithMinNameLength")
    public void testConfigureNameByChevron() {

        String configurationName = new HomePage(getDriver())
                .selectConfigureFromItemMenu("F")
                .enterConfigurationName(FIRST_FOLDER_NAME)
                .clickSaveButton()
                .getConfigurationName();

        Assert.assertEquals(configurationName, FIRST_FOLDER_NAME);
        Assert.assertEquals(new FolderProjectPage(getDriver()).getFolderName(), "F");
    }

    @Test
    public void testConfigureDescriptionByChevron() {

        String desc = new HomePage(getDriver())
                .clickNewItem()
                .nameAndSelectFolderType(FIRST_FOLDER_NAME)
                .gotoHomePage()
                .selectConfigureFromItemMenu(FIRST_FOLDER_NAME)
                .enterDescription("This is new description")
                .clickSaveButton()
                .getFolderDescription();

        Assert.assertEquals(desc,
                "This is new description");
    }

    @Test(dependsOnMethods = "testConfigureDescriptionByChevron")
    public void testCreateNewItemByChevron() {

        String projectName = new FolderProjectPage(getDriver())
                .gotoHomePage()
                .selectNewItemFromFolderMenu(FIRST_FOLDER_NAME)
                .nameAndSelectFreestyleProject(FREESTYLE_PROJECT_NAME)
                .addExecuteWindowsBatchCommand("echo 'Hello world!'")
                .clickSaveButton()
                .gotoHomePage()
                .openFolder(FIRST_FOLDER_NAME)
                .getItemNameByOrder(1);

        Assert.assertEquals(projectName, FREESTYLE_PROJECT_NAME);
    }

    @Test
    public void testCreateNewItemFromFolderPage() {

        String projectName = new HomePage(getDriver())
                .clickNewItem()
                .nameAndSelectFolderType(FIRST_FOLDER_NAME)
                .gotoHomePage()
                .openFolder(FIRST_FOLDER_NAME)
                .clickNewItem()
                .nameAndSelectFreestyleProject(FREESTYLE_PROJECT_NAME)
                .addExecuteWindowsBatchCommand("echo 'Hello world!'")
                .clickSaveButton()
                .gotoHomePage()
                .openFolder(FIRST_FOLDER_NAME)
                .getItemNameByOrder(1);

        Assert.assertEquals(projectName, FREESTYLE_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreateNewItemFromFolderPage")
    public void testOpenBuildHistoryByChevron() {

        String buildHistoryName = new HomePage(getDriver())
                .openFolder(FIRST_FOLDER_NAME)
                .runJob(FREESTYLE_PROJECT_NAME)
                .gotoHomePage()
                .selectBuildHistoryFromItemMenu(FIRST_FOLDER_NAME)
                .getBuildName();

        Assert.assertEquals(buildHistoryName, "%s » %s".formatted(FIRST_FOLDER_NAME, FREESTYLE_PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testOpenBuildHistoryByChevron")
    public void testAddDisplayName() {
        final String displayName = "DisplayName";

        List<String> projectList = new HomePage(getDriver())
                .openFolder(FIRST_FOLDER_NAME)
                .clickSidebarConfigButton()
                .enterConfigurationName(displayName)
                .clickSaveButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertListContainsObject(projectList, displayName, "Display name is not added");
    }

    @Test
    public void testErrorDuringCreationWithDotInEnd() {

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("Folder.")
                .selectFolderType()
                .getInvalidNameMessage();

        Assert.assertEquals(errorMessage, "» A name cannot end with ‘.’");
    }

    @Test
    public void testErrorAfterCreationWithDotInEnd() {

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("Folder.")
                .selectFolderType()
                .selectFolderType()
                .saveInvalidData()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "A name cannot end with ‘.’");
    }

    @Test
    public void testErrorEmptyNameCreation() {

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .selectFolderType()
                .getEmptyNameMessage();

        Assert.assertEquals(errorMessage, "» This field cannot be empty, please enter a valid name");
    }

    @Ignore
    @Test(dependsOnMethods = "testOpenBuildHistoryByChevron")
    public void testErrorDuplicateNameCreation() {

        String errorMessage = new HomePage(getDriver())
                .clickNewItem().enterItemName(FIRST_FOLDER_NAME)
                .getInvalidNameMessage();

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘Freestyle projects’");
    }

    @Test
    public void testDeleteViaMainPageChevron () {
        List<String> setOfProjects = new HomePage(getDriver())
                .createNewFolder(FIRST_FOLDER_NAME)
                .deleteFolderViaChevron(FIRST_FOLDER_NAME)
                .getItemList();

        Assert.assertTrue(setOfProjects.isEmpty());
    }

    @Test
    public void testDeleteViaSidebarFromProjectPage () {
        List<String> setOfProjects = new HomePage(getDriver())
                .createNewFolder(FIRST_FOLDER_NAME)
                .deleteFolder(FIRST_FOLDER_NAME)
                .getItemList();

        Assert.assertTrue(setOfProjects.isEmpty());
    }

    @Test
    public void testCancelDeletingViaSidebarProjectPage () {
        List<String> setOfProjects = new HomePage(getDriver())
                .createNewFolder(FIRST_FOLDER_NAME)
                .openFolder(FIRST_FOLDER_NAME)
                .cancelDeletingViaModalWindow()
                .gotoHomePage()
                .getItemList();

        Assert.assertTrue(setOfProjects.contains(FIRST_FOLDER_NAME));
    }

    @Test
    public void testDeleteViaMyViewChevron () {
        List<String> setOfProjects = new HomePage(getDriver())
                .createNewFolder(FIRST_FOLDER_NAME)
                .clickMyViewsButton()
                .deleteItemViaChevronItem(FIRST_FOLDER_NAME)
                .gotoHomePage()
                .getItemList();

        Assert.assertTrue(setOfProjects.isEmpty());
    }

    @Test
    public void testMoveFromFoldersPage() {

        List<String> nameProjectList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_MOVE_PARENT_NAME)
                .selectFolderAndClickOk()
                .gotoHomePage()

                .clickNewItem()
                .enterItemName(FOLDER_MOVE_CHILD_NAME)
                .selectFolderAndClickOk()
                .gotoHomePage()

                .openFolder(FOLDER_MOVE_CHILD_NAME)
                .clickMoveOnSidebar()
                .selectParentFolderAndClickMove(FOLDER_MOVE_PARENT_NAME)
                .getBreadcrumsBarItemsList();

        Assert.assertEquals(nameProjectList, List.of("Dashboard", FOLDER_MOVE_PARENT_NAME, FOLDER_MOVE_CHILD_NAME));
    }

    @Test(dependsOnMethods = "testMoveFromFoldersPage")
    public void testMoveFromTheSameLevel() {
        List<String> nameProjectsList = new HomePage(getDriver())
                .openFolder(FOLDER_MOVE_PARENT_NAME)
                .clickNewItem()
                .enterItemName(FOLDER_MOVE_CHILD2_NAME)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .openFolder(FOLDER_MOVE_PARENT_NAME)
                .openFolder(FOLDER_MOVE_CHILD2_NAME)
                .clickMoveOnSidebar()
                .selectParentFolderAndClickMove(FOLDER_MOVE_PARENT_NAME +"/" + FOLDER_MOVE_CHILD_NAME)
                .getBreadcrumsBarItemsList();

        Assert.assertEquals(nameProjectsList, List.of("Dashboard", FOLDER_MOVE_PARENT_NAME, FOLDER_MOVE_CHILD_NAME, FOLDER_MOVE_CHILD2_NAME));
    }

    @Test(dependsOnMethods = "testMoveFromTheSameLevel")
    public void testTryToMoveInTheSamePlace () {
        List<String> nameProjectsListBreadcrumbs = new HomePage(getDriver())
                .openFolder(FOLDER_MOVE_PARENT_NAME)
                .openFolder(FOLDER_MOVE_CHILD_NAME)
                .clickMoveOnSidebar()
                .selectParentFolderAndClickMove(FOLDER_MOVE_PARENT_NAME)
                .getBreadcrumsBarItemsList();

        Assert.assertEquals(nameProjectsListBreadcrumbs, List.of("Dashboard", FOLDER_MOVE_PARENT_NAME, FOLDER_MOVE_CHILD_NAME, "Move"));
    }

    @Test(dependsOnMethods = "testMoveFromTheSameLevel")
    public void testMoveOnTheHigherLevel() {
        List<String> nameProjectsList = new HomePage(getDriver())
                .openFolder(FOLDER_MOVE_PARENT_NAME)
                .openFolder(FOLDER_MOVE_CHILD_NAME)
                .openFolder(FOLDER_MOVE_CHILD2_NAME)
                .clickMoveOnSidebar()
                .selectParentFolderAndClickMove(FOLDER_MOVE_PARENT_NAME)
                .getBreadcrumsBarItemsList();

        Assert.assertEquals(nameProjectsList, List.of("Dashboard", FOLDER_MOVE_PARENT_NAME, FOLDER_MOVE_CHILD2_NAME));
    }

    @Test(dependsOnMethods = "testMoveOnTheHigherLevel")
    public void testNoOptionsToMoveParentIntoChild () {
        List<String> itemsSidebar = new HomePage(getDriver())
                .openFolder(FOLDER_MOVE_PARENT_NAME)
                .getListOfItemsSidebar();

        Assert.assertListNotContains(itemsSidebar, item->item.contains("Move"), "list of sidebar items contains Move");
    }

    @Test
    public void testMoveViaChevronMainPage () {

        List<String> nameProjectsList =  new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_MOVE_PARENT_NAME)
                .selectFolderAndClickOk()
                .gotoHomePage()

                .clickNewItem()
                .enterItemName(FOLDER_MOVE_CHILD_NAME)
                .selectFolderAndClickOk()
                .gotoHomePage()

                .selectMoveFromItemMenuByChevron(FOLDER_MOVE_CHILD_NAME)
                .selectParentFolderAndClickMove(FOLDER_MOVE_PARENT_NAME)
                .getBreadcrumsBarItemsList();

        Assert.assertEquals(nameProjectsList, List.of("Dashboard", FOLDER_MOVE_PARENT_NAME, FOLDER_MOVE_CHILD_NAME));
    }

    @Test
    public void testMoveViaChevronMyView () {

        List<String> nameProjectsList =  new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_MOVE_PARENT_NAME)
                .selectFolderAndClickOk()
                .gotoHomePage()

                .clickNewItem()
                .enterItemName(FOLDER_MOVE_CHILD_NAME)
                .selectFolderAndClickOk()
                .gotoHomePage()

                .clickMyViewsButton()
                .selectMoveFromItemMenuByChevron(FOLDER_MOVE_CHILD_NAME)
                .selectParentFolderAndClickMove(FOLDER_MOVE_PARENT_NAME)
                .getBreadcrumsBarItemsList();

        Assert.assertEquals(nameProjectsList, List.of("Dashboard", FOLDER_MOVE_PARENT_NAME, FOLDER_MOVE_CHILD_NAME));
    }
}
