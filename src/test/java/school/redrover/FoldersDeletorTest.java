package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;
import java.util.List;

public class FoldersDeletorTest extends BaseTest {

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
