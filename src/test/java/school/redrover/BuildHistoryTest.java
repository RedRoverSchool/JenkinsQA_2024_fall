package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.BuildHistoryPage;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class BuildHistoryTest extends BaseTest {

    private static final String FREESTYLE_PROJECT_NAME = "FreestyleProject";
    private static final String PIPELINE_PROJECT_NAME = "PipelineProject";

    @Test
    public void testDisplaySuccessBuild() {

        TestUtils.createFreestyleProject(getDriver(), FREESTYLE_PROJECT_NAME);

        BuildHistoryPage buildHistoryPage = new HomePage(getDriver())
                .clickScheduleBuild(FREESTYLE_PROJECT_NAME)
                .gotoBuildHistoryPageFromLeftPanel();

        Assert.assertEquals(buildHistoryPage.getBuildName(), FREESTYLE_PROJECT_NAME);
        Assert.assertEquals(buildHistoryPage.getListOfStatuses().get(0), "stable");
        Assert.assertEquals(buildHistoryPage.getBuildStatusSignColor(), "blue");
    }

    @Test
    public void testDisplayFirstFailedBuild() {

        TestUtils.createPipelineProjectWithoutSaveButton(getDriver(), PIPELINE_PROJECT_NAME);

        BuildHistoryPage buildHistoryPage = new HomePage(getDriver())
                .openPipelineProject(PIPELINE_PROJECT_NAME)
                .clickOnBuildNowItemOnSidePanelAndWait()
                .gotoHomePage()
                .gotoBuildHistoryPageFromLeftPanel();

        Assert.assertEquals(buildHistoryPage.getBuildName(), PIPELINE_PROJECT_NAME);
        Assert.assertEquals(buildHistoryPage.getListOfStatuses().get(0), "broken since this build");
        Assert.assertEquals(buildHistoryPage.getBuildStatusSignColor(), "red");
    }
}
