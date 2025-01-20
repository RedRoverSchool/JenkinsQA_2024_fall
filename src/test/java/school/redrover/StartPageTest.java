package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

@Epic("17 Start Page")
public class StartPageTest extends BaseTest {

    @Test
    @Story("US_17.001 Start Page")
    @Description("TC_17.001.01 Start page main panel content")
    public void testStartPageMainPanelContent() {
        List<String> startPageMainContent = new HomePage(getDriver())
                .getContentBlock();

        Allure.step("Expected result: Displaying the contents of the main panel of the start page");
        Assert.assertEquals(startPageMainContent.size(), 4);
        Assert.assertEquals(
                startPageMainContent,
                List.of("Create a job", "Set up an agent", "Configure a cloud", "Learn more about distributed builds"));
    }

    @Test
    @Story("US_17.001 Start Page")
    @Description("TC_17.001.02 Start page side panel task content")
    public void testStartPageSidePanelTaskContent() {
        List<String> startPageSideContent = new HomePage(getDriver())
                .getSideContent();

        Allure.step("Expected result: Displaying task contents in the start page sidebar");
        Assert.assertEquals(startPageSideContent.size(), 4);
        Assert.assertEquals(startPageSideContent, List.of("New Item", "Build History", "Manage Jenkins", "My Views"));
    }

    @Test
    @Story("US_17.001 Start Page")
    @Description("TC_17.001.03 Check links side panel")
    public void testCheckLinksSidePanel() {
        List<String> startPageSideContent = new HomePage(getDriver())
                .getSideContentAttribute();

        Allure.step("Expected result: Check links side panel");
        Assert.assertEquals(startPageSideContent.size(), 4);
        Assert.assertEquals(startPageSideContent, List.of("/view/all/newJob", "/view/all/builds", "/manage", "/me/my-views"));
    }

    @Test
    @Story("US_17.001 Start Page")
    @Description("TC_17.001.04 Check build queue message")
    public void testCheckBuildQueueMessage() {
        String buildQueueText = new HomePage(getDriver())
                .getBuildQueueText();

        Allure.step("Expected result: Check build queue message");
        Assert.assertEquals(buildQueueText, "No builds in the queue.");
    }
}
