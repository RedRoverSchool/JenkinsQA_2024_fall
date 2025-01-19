package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class StartPageTest extends BaseTest {

    @Test
    public void testStartPageMainPanelContent() {
        List<String> startPageMainContent = new HomePage(getDriver())
                .getContentBlock();

        Assert.assertEquals(startPageMainContent.size(), 4);
        Assert.assertEquals(
                startPageMainContent,
                List.of("Create a job", "Set up an agent", "Configure a cloud", "Learn more about distributed builds"));
    }

    @Test
    public void testStartPageSidePanelTaskContent() {
        List<String> startPageSideContent = new HomePage(getDriver())
                .getSideContent();

        Assert.assertEquals(startPageSideContent.size(), 4);
        Assert.assertEquals(startPageSideContent, List.of("New Item", "Build History", "Manage Jenkins", "My Views"));
    }

    @Test
    public void testCheckLinksSidePanel() {
        List<String> startPageSideContent = new HomePage(getDriver())
                .getSideContentAttribute();

        Assert.assertEquals(startPageSideContent.size(), 4);
        Assert.assertEquals(
                startPageSideContent,
                List.of("/view/all/newJob", "/view/all/builds",
                        "/manage", "/me/my-views"));
    }

    @Test
    public void testCheckBuildQueueMessage() {
        String buildQueueText = new HomePage(getDriver())
                .getBuildQueueText();

        Assert.assertEquals(buildQueueText, "No builds in the queue.");
    }
}
