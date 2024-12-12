package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;


public class StartPageTest extends BaseTest {

    private static final String NEW_FOLDER_NAME = "FirstFolder";

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
    public void testCreateNewFolder() {
        TestUtils.createFolder(getDriver(), NEW_FOLDER_NAME);

        List<String> projectList = new HomePage(getDriver())
                .getItemList();

        Assert.assertListContainsObject(
                projectList,
                NEW_FOLDER_NAME,
                "Folder is not created");
    }

    @Test(dependsOnMethods = "testCreateNewFolder")
    public void testDeleteNewFolder() {

        String welcomeText = new HomePage(getDriver())
                .openFolder(NEW_FOLDER_NAME)
                .clickDeleteButtonSidebarAndConfirm()
                .getWelcomeDescriptionText();

        Assert.assertEquals(welcomeText,
                "This page is where your Jenkins jobs will be displayed. To get started, you can set up distributed builds or start building a software project.");
    }

    @Test
    public void testDeleteNewFolderViaChevron() {
        TestUtils.createFolder(getDriver(), NEW_FOLDER_NAME);

        String welcomeText = new HomePage(getDriver())
                .selectDeleteFromItemMenuAndClickYes(NEW_FOLDER_NAME)
                .getWelcomeDescriptionText();

        Assert.assertEquals(welcomeText,
                "This page is where your Jenkins jobs will be displayed. To get started, you can set up distributed builds or start building a software project.");
    }

    @Test
    public void testCheckBuildQueueMessageTest() {

        WebElement QueueMessageArrow =  getDriver().findElement(By.className("widget-refresh-reference"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(QueueMessageArrow).click().perform();

        String BuildQueueMessage = getDriver().findElement(By.xpath("//td[text()= 'No builds in the queue.' ]")).getText();

        Assert.assertEquals(BuildQueueMessage, "No builds in the queue.");
    }
}
