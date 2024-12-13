package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.BuildHistoryPage;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class BuildHistoryTest extends BaseTest {

    private static final String FREESTYLE_PROJECT_NAME = "FreestyleProject";

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
}
