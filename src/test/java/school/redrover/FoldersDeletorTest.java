package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

import static school.redrover.runner.TestUtils.newItemsData;

public class FoldersDeletorTest extends BaseTest {

    private void clickOnSave () {
        getDriver().findElement(By.name("Submit")).click();
    }

    private static final String FOLDER_NAME = "FolderToDelete";

    @Test
    public void testViaMainPageChevron () {
        List<String> setOfProjects = new HomePage(getDriver())
                .createNewFolder(FOLDER_NAME)
                .gotoHomePage()
                .deleteFolderViaChevron(FOLDER_NAME)
                .getItemList();

        Assert.assertTrue(setOfProjects.isEmpty());
    }

    @Test
    public void testFromProjectPage () {
        List<String> setOfProjects = new HomePage(getDriver())
                .createNewFolder(FOLDER_NAME)
                .deleteFolder(FOLDER_NAME)
                .gotoHomePage()
                .getItemList();

       Assert.assertTrue(setOfProjects.isEmpty());
    }

    @Test
    public void testViaMyViewChevron () {
        List<String> setOfProjects = new HomePage(getDriver())
                .createNewFolder(FOLDER_NAME)
                .gotoHomePage()
                .clickMyViewsButton()
                .deleteItemViaChevronItem(FOLDER_NAME)
                .gotoHomePage()
                .getItemList();

        Assert.assertTrue(setOfProjects.isEmpty());
    }

    @Test
    public void testCancelDeleting () {
        List<String> setOfProjects = new HomePage(getDriver())
                .createNewFolder(FOLDER_NAME)
                .gotoHomePage()
                .openFolder(FOLDER_NAME)
                .cancelDeletingViaModalWindow()
                .gotoHomePage()
                .getItemList();

        Assert.assertTrue(setOfProjects.contains(FOLDER_NAME));
    }
}
