package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.page.NewItemPage;
import school.redrover.page.ProjectPage;
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
                .selectProjectTypeAndSave(NewItemPage.ItemType.FOLDER)
                .goToDashboard()
                .getItemNameByOrder(1);

        Assert.assertEquals(folderName, FOLDER_NAME_MAX_LENGTH);
    }

    @Test
    public void testCreateWithMinNameLength() {

        new HomePage(getDriver())
                .clickNewItem().enterItemName("F")
                .selectProjectTypeAndSave(NewItemPage.ItemType.FOLDER)
                .goToDashboard();

        Assert.assertEquals(getDriver().findElement(By.xpath("//td/a/span")).getText(),"F");
    }

    @Test(dependsOnMethods = "testCreateWithMinNameLength")
    public void testConfigureNameByChevron() {

        String configurationName = new HomePage(getDriver())
                .selectConfigureFromItemMenu("F")
                .enterName(FIRST_FOLDER_NAME)
                .saveConfigurations()
                .getDisplayName();

        Assert.assertEquals(configurationName, FIRST_FOLDER_NAME);
        Assert.assertEquals(new ProjectPage(getDriver()).getFolderName(), "F");

    }

    @Test
    public void testConfigureDescriptionByChevron() {

       String desc =  new HomePage(getDriver())
                .clickNewItem()
                .nameAndSelectItemType(FIRST_FOLDER_NAME, NewItemPage.ItemType.FOLDER)
                .goToDashboard()
                .selectConfigureFromItemMenu(FIRST_FOLDER_NAME)
               .enterDescription("This is new description")
               .saveConfigurations()
               .getFolderDescription();

        Assert.assertEquals(desc,
                "This is new description");
    }

    @Test(dependsOnMethods = "testConfigureDescriptionByChevron")
    public void testCreateNewItemByChevron() {
        String projectName = new ProjectPage(getDriver())
                .goToDashboard()
                .selectNewItemFromFolderMenu(FIRST_FOLDER_NAME)
                .nameAndSelectItemType(FREESTYLE_PROJECT_NAME, NewItemPage.ItemType.FREESTYLE_PROJECT)
                .addExecuteWindowsBatchCommand("echo 'Hello world!'")
                .saveConfigurations()
                .goToDashboard()
                .openProject(FIRST_FOLDER_NAME)
                .getItemNameByOrder(1);

        Assert.assertEquals(projectName, FREESTYLE_PROJECT_NAME);
    }

    @Test
    public void testCreateNewItemFromFolderPage() {
        String projectName =  new HomePage(getDriver())
                .clickNewItem()
                .nameAndSelectItemType(FIRST_FOLDER_NAME, NewItemPage.ItemType.FOLDER)
                .goToDashboard()
                .openProject(FIRST_FOLDER_NAME)
                .clickNewItem()
                .nameAndSelectItemType(FREESTYLE_PROJECT_NAME, NewItemPage.ItemType.FREESTYLE_PROJECT)
                .addExecuteWindowsBatchCommand("echo 'Hello world!'")
                .saveConfigurations()
                .goToDashboard()
                .openProject(FIRST_FOLDER_NAME)
                .getItemNameByOrder(1);

        Assert.assertEquals(projectName, FREESTYLE_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreateNewItemFromFolderPage")
    public void testOpenBuildHistoryByChevron() {

        String buildHistoryName = new HomePage(getDriver())
                .openProject(FIRST_FOLDER_NAME)
                .runJob(FREESTYLE_PROJECT_NAME)
                .goToDashboard()
                .selectBuildHistoryFromItemMenu(FIRST_FOLDER_NAME)
                .getBuildHistory();

        Assert.assertEquals(buildHistoryName, "%s » %s".formatted(FIRST_FOLDER_NAME, FREESTYLE_PROJECT_NAME));

    }
}
