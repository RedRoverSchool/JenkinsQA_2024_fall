package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class UpdatesBuildHistoryTest extends BaseTest {
    private final String PROJECT_NAME = "TestName";

    @Test
    public void testBuildHistoryIsEmpty() {
        List<String> emptyHistory = new HomePage(getDriver())
                .createFreestyleProject(PROJECT_NAME)
                .gotoBuildHistoryPageFromLeftPanel()
                .getListOfStatuses();

        Assert.assertEquals(emptyHistory.size(), 0);
    }

    @Test(dependsOnMethods = "testBuildHistoryIsEmpty")
    public void testUpdateAfterExecutingBuild() {
        List<String> oneExecution = new HomePage(getDriver())
                .clickScheduleBuild(PROJECT_NAME)
                .gotoBuildHistoryPageFromLeftPanel()
                .getListOfStatuses();

        Assert.assertEquals(oneExecution.get(0), "stable");
        Assert.assertEquals(oneExecution.size(), 1);
    }

    @Test(dependsOnMethods = "testUpdateAfterExecutingBuild")
    public void testUpdateAfterChangingConfig() {
        List<String> changeConfig = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .addBuildStep("Run with timeout")
                .gotoHomePage()
                .clickScheduleBuild(PROJECT_NAME)
                .gotoBuildHistoryPageFromLeftPanel()
                .getListOfStatuses();

        Assert.assertEquals(changeConfig.size(), 2);
    }
}
