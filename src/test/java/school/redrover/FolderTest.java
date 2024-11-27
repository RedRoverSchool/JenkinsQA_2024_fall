package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.page.CreateNewItemPage;
import school.redrover.page.FolderProjectPage;
import school.redrover.runner.BaseTest;

public class FolderTest extends BaseTest {

    private static final String FIRST_FOLDER_NAME = "Freestyle projects";
    private static final String FREESTYLE_PROJECT_NAME = "First freestyle project job";
    private static final String FOLDER_NAME_MAX_LENGTH = "012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234";

    @Test
    public void testCreateWithMaxNameLength() {

        String folderName = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_NAME_MAX_LENGTH)
                .selectProjectTypeAndSave(CreateNewItemPage.ItemType.FOLDER)
                .gotoHomePage()
                .getItemNameByOrder(1);

        Assert.assertEquals(folderName, FOLDER_NAME_MAX_LENGTH);
    }

    @Test
    public void testCreateWithMinNameLength() {

        new HomePage(getDriver())
                .clickNewItem().enterItemName("F")
                .selectProjectTypeAndSave(CreateNewItemPage.ItemType.FOLDER)
                .gotoHomePage();

        Assert.assertEquals(getDriver().findElement(By.xpath("//td/a/span")).getText(),"F");
    }

    @Test(dependsOnMethods = "testCreateWithMinNameLength")
    public void testConfigureNameByChevron() {

        String configurationName = new HomePage(getDriver())
                .selectConfigureFromItemMenu("F")
                .enterName(FIRST_FOLDER_NAME)
                .clickSaveButton()
                .getDisplayName();

        Assert.assertEquals(configurationName, FIRST_FOLDER_NAME);
        Assert.assertEquals(new FolderProjectPage(getDriver()).getFolderName(), "F");
    }

    @Test
    public void testConfigureDescriptionByChevron() {

       String desc =  new HomePage(getDriver())
               .clickNewItem()
               .nameAndSelectItemType(FIRST_FOLDER_NAME, CreateNewItemPage.ItemType.FOLDER)
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
                .nameAndSelectItemType(FREESTYLE_PROJECT_NAME, CreateNewItemPage.ItemType.FREESTYLE_PROJECT)
                .addExecuteWindowsBatchCommand("echo 'Hello world!'")
                .clickSaveButton()
                .gotoHomePage()
                .openProject(FIRST_FOLDER_NAME)
                .getItemNameByOrder(1);

        Assert.assertEquals(projectName, FREESTYLE_PROJECT_NAME);
    }

    @Test
    public void testCreateNewItemFromFolderPage() {
        String projectName =  new HomePage(getDriver())
                .clickNewItem()
                .nameAndSelectItemType(FIRST_FOLDER_NAME, CreateNewItemPage.ItemType.FOLDER)
                .gotoHomePage()
                .openProject(FIRST_FOLDER_NAME)
                .clickNewItem()
                .nameAndSelectItemType(FREESTYLE_PROJECT_NAME, CreateNewItemPage.ItemType.FREESTYLE_PROJECT)
                .addExecuteWindowsBatchCommand("echo 'Hello world!'")
                .clickSaveButton()
                .gotoHomePage()
                .openProject(FIRST_FOLDER_NAME)
                .getItemNameByOrder(1);

        Assert.assertEquals(projectName, FREESTYLE_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreateNewItemFromFolderPage")
    public void testOpenBuildHistoryByChevron() {

        String buildHistoryName = new HomePage(getDriver())
                .openProject(FIRST_FOLDER_NAME)
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
                .selectProjectType(CreateNewItemPage.ItemType.FOLDER)
                .getInvalidNameMessage();

        Assert.assertEquals(errorMessage, "» A name cannot end with ‘.’");
    }

    @Test
    public void testErrorAfterCreationWithDotInEnd() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("Folder.")
                .selectProjectType(CreateNewItemPage.ItemType.FOLDER)
                .selectProjectType(CreateNewItemPage.ItemType.FOLDER)
                .saveInvalidData()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "A name cannot end with ‘.’");
    }

    @Test
    public void testErrorEmptyNameCreation() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .selectProjectType(CreateNewItemPage.ItemType.FOLDER)
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


}
