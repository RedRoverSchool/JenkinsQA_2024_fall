package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;


public class StartPageTest extends BaseTest {

    private static final String NEW_FOLDER_NAME = "FirstFolder";
    private static final String DESCRIPTIONS_TEXT = "Hello World";


    @Test
    public void testStartPageMainPanelContent() {

        List<WebElement> startPageMainContent = new HomePage(getDriver())
                .getContentBlock();

        Assert.assertEquals(startPageMainContent.size(), 4);
        Assert.assertEquals(startPageMainContent.get(0).getText(), "Create a job");
        Assert.assertEquals(startPageMainContent.get(1).getText(), "Set up an agent");
        Assert.assertEquals(startPageMainContent.get(2).getText(), "Configure a cloud");
        Assert.assertEquals(startPageMainContent.get(3).getText(), "Learn more about distributed builds");
    }

    @Test
    public void testStartPageSidePanelTaskContent() {

        List<WebElement> startPageSideContent = new HomePage(getDriver())
                .getSideContent();

        Assert.assertEquals(startPageSideContent.size(), 4);
        Assert.assertEquals(startPageSideContent.get(0).getText(), "New Item");
        Assert.assertEquals(startPageSideContent.get(1).getText(), "Build History");
        Assert.assertEquals(startPageSideContent.get(2).getText(), "Manage Jenkins");
        Assert.assertEquals(startPageSideContent.get(3).getText(), "My Views");
    }

    @Test
    public void testCheckLinksSidePanel() {

        List<WebElement> startPageSideContent = new HomePage(getDriver())
                .getSideContent();

        Assert.assertEquals(startPageSideContent.size(), 4);
        Assert.assertEquals(startPageSideContent.get(0).getAttribute("href"), "http://localhost:8080/view/all/newJob");
        Assert.assertEquals(startPageSideContent.get(1).getAttribute("href"), "http://localhost:8080/view/all/builds");
        Assert.assertEquals(startPageSideContent.get(2).getAttribute("href"), "http://localhost:8080/manage");
        Assert.assertEquals(startPageSideContent.get(3).getAttribute("href"), "http://localhost:8080/me/my-views");
    }

    @Test
    public void testCreateDescription() {

        String actualDescription = new HomePage(getDriver())
                .clickDescriptionButton()
                .enterDescription(DESCRIPTIONS_TEXT)
                .clickSaveButton()
                .getDescriptionText();


        Assert.assertEquals(actualDescription, DESCRIPTIONS_TEXT);
    }

    @Test
    public void testCreateNewFolder() {

        List<String> projectList = new HomePage(getDriver())
                .createNewFolder(NEW_FOLDER_NAME)
                .getItemList();

        Assert.assertListContainsObject(
                projectList,
                NEW_FOLDER_NAME,
                "Folder is not created");
    }

    @Test(dependsOnMethods = "testCreateNewFolder")
    public void testDeleteNewFolder() {

        String welcomeText = new HomePage(getDriver())
                .deleteFolder(NEW_FOLDER_NAME)
                .getWelcomeDescriptionText();

        Assert.assertEquals(welcomeText,
                "This page is where your Jenkins jobs will be displayed. To get started, you can set up distributed builds or start building a software project.");
    }

    @Test
    public void testDeleteNewFolderViaChevron() {

        String welcomeText = new HomePage(getDriver())
                .createNewFolder(NEW_FOLDER_NAME)
                .deleteFolderViaChevron(NEW_FOLDER_NAME)
                .getWelcomeDescriptionText();

        Assert.assertEquals(welcomeText,
                "This page is where your Jenkins jobs will be displayed. To get started, you can set up distributed builds or start building a software project.");
    }

}
