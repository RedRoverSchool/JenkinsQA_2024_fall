package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.BuildHistoryPage;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class UpdatesBuildHistoryTest extends BaseTest {
    private final String PROJECT_NAME = "TestName";

    @Test
    public void testBuildHistoryIsEmpty() {
        List <String> emptyHistory = new HomePage(getDriver())
                .createFreestyleProject(PROJECT_NAME)
                .gotoBuildHistoryPageFromLeftPanel()
                .getListOfStatuses();

        Assert.assertEquals(emptyHistory.size(),0);
    }

    @Test(dependsOnMethods = "testBuildHistoryIsEmpty")
    public void testUpdateAfterExecutingBuild() {
        List <String> oneExecution = new HomePage(getDriver())
                .scheduleBuild(PROJECT_NAME)
                .gotoBuildHistoryPageFromLeftPanel()
                .getListOfStatuses();

        Assert.assertEquals(oneExecution.get(0), "stable");
        Assert.assertEquals(oneExecution.size(),1);
    }

    @Test(dependsOnMethods = "testUpdateAfterExecutingBuild")
    public void testUpdateAfterChangingConfig() {
        List <String> changeConfig = new BuildHistoryPage(getDriver())
                .addBuildSteps(PROJECT_NAME,"Run with timeout")
                .gotoHomePage()
                .scheduleBuild(PROJECT_NAME)
                .gotoHomePage()
                .gotoBuildHistoryPageFromLeftPanel()
                .getListOfStatuses();

        Assert.assertEquals(changeConfig.get(0), "broken since this build");
        Assert.assertEquals(changeConfig.size(),2);
    }
}
