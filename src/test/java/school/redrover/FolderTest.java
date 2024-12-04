package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.page.FolderProjectPage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FolderTest extends BaseTest {

    private static final String FIRST_FOLDER_NAME = "Freestyle projects";
    private static final String FREESTYLE_PROJECT_NAME = "First freestyle project job";
    private static final String FOLDER_NAME_MAX_LENGTH = "012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234";
    private static final String TYPE_FOLDER = "Folder";

    @Test
    public void testCreateWithMaxNameLength() {

        String folderName = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_NAME_MAX_LENGTH)
                .selectTypeProject(TYPE_FOLDER)
                .clickOkButton()
                .gotoHomePage()
                .getItemNameByOrder(1);

        Assert.assertEquals(folderName, FOLDER_NAME_MAX_LENGTH);
    }

    @Test
    public void testCreateWithMinNameLength() {

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("F")
                .selectTypeProject(TYPE_FOLDER)
                .clickOkButton()
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

    @Test(dependsOnMethods = "testConfigureNameByChevron")
    public void testConfigureDescriptionByChevron() {

        String desc = new HomePage(getDriver())
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

    @Test
    public void testErrorDuringCreationWithDotInEnd() {

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("Folder.")
                .selectTypeProject(TYPE_FOLDER)
                .getInvalidNameMessage();

        Assert.assertEquals(errorMessage, "» A name cannot end with ‘.’");
    }

    @Test
    public void testErrorAfterCreationWithDotInEnd() {

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("Folder.")
                .selectTypeProject(TYPE_FOLDER)
                .selectTypeProject(TYPE_FOLDER)
                .saveInvalidData()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "A name cannot end with ‘.’");
    }

    @Test
    public void testErrorEmptyNameCreation() {

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .selectTypeProject(TYPE_FOLDER)
                .getEmptyNameMessage();

        Assert.assertEquals(errorMessage, "» This field cannot be empty, please enter a valid name");
    }
    
    @Test(dependsOnMethods = "testOpenBuildHistoryByChevron")
    public void testErrorDuplicateNameCreation() {

        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FIRST_FOLDER_NAME)
                .selectTypeProject(TYPE_FOLDER)
                .getInvalidNameMessage();

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘Freestyle projects’");
    }


}
