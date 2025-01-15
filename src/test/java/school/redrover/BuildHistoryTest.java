package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.BuildHistoryPage;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;


public class BuildHistoryTest extends BaseTest {

    private static final String FREESTYLE_PROJECT_NAME = "FreestyleProject";
    private static final String PIPELINE_PROJECT_NAME = "PipelineProject";


    @Test
    public void testCreateFreestyleProject() {
        String newProject = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .getItemNameByOrder(1);

        Assert.assertEquals(newProject, FREESTYLE_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreateFreestyleProject")
    public void testDisplaySuccessBuild() {

        BuildHistoryPage buildHistoryPage = new HomePage(getDriver())
                .clickScheduleBuild(FREESTYLE_PROJECT_NAME)
                .gotoBuildHistoryPageFromLeftPanel();

        Assert.assertEquals(buildHistoryPage.getProjectName(), FREESTYLE_PROJECT_NAME);
        Assert.assertEquals(buildHistoryPage.getBuildDisplayName(), "#1");
        Assert.assertEquals(buildHistoryPage.getListOfStatuses().get(0), "stable");
        Assert.assertEquals(buildHistoryPage.getBuildStatusSignColor(), "blue");
    }

    @Test(dependsOnMethods = "testDisplaySuccessBuild")
    public void testSuccessfulBuildConsoleOutputCheck() {
        String result = new HomePage(getDriver())
                .gotoBuildHistoryPageFromLeftPanel()
                .clickConsoleOutput()
                .getFinishResult();

        Assert.assertEquals(result, "SUCCESS");
    }

    @Test
    public void testCreatePipelineProjectWithoutSaveButton() {
        String pipelineProject = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PIPELINE_PROJECT_NAME)
                .selectPipelineAndClickOk()
                .gotoHomePage()
                .getItemNameByOrder(1);
        Assert.assertEquals(pipelineProject,PIPELINE_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreatePipelineProjectWithoutSaveButton")
    public void testDisplayFirstFailedBuild() {

        BuildHistoryPage buildHistoryPage = new HomePage(getDriver())
                .openPipelineProject(PIPELINE_PROJECT_NAME)
                .clickOnBuildNowItemOnSidePanelAndWait()
                .gotoHomePage()
                .gotoBuildHistoryPageFromLeftPanel();

        Assert.assertEquals(buildHistoryPage.getProjectName(), PIPELINE_PROJECT_NAME);
        Assert.assertEquals(buildHistoryPage.getBuildDisplayName(), "#1");
        Assert.assertEquals(buildHistoryPage.getListOfStatuses().get(0), "broken since this build");
        Assert.assertEquals(buildHistoryPage.getCssValueOfStatusByIndex(0, "color"), "rgba(230, 0, 31, 1)");
        Assert.assertEquals(buildHistoryPage.getBuildStatusSignColor(), "red");
    }

    @Test(dependsOnMethods = "testDisplayFirstFailedBuild")
    public void testFailedBuildConsoleOutputCheck() {
        String result = new HomePage(getDriver())
                .gotoBuildHistoryPageFromLeftPanel()
                .clickConsoleOutput()
                .getFinishResult();

        Assert.assertEquals(result, "FAILURE");
    }

    @Test(dependsOnMethods = "testFailedBuildConsoleOutputCheck")
    public void testDisplayNextFailedBuild() {
        BuildHistoryPage buildHistoryPage = new HomePage(getDriver())
                .clickScheduleBuild(PIPELINE_PROJECT_NAME)
                .gotoBuildHistoryPageFromLeftPanel();

        Assert.assertEquals(buildHistoryPage.getProjectName(), PIPELINE_PROJECT_NAME);
        Assert.assertEquals(buildHistoryPage.getBuildDisplayName(), "#2");
        Assert.assertEquals(buildHistoryPage.getListOfStatuses().get(0), "broken for a long time");
        Assert.assertEquals(buildHistoryPage.getCssValueOfStatusByIndex(0, "color"), "rgba(20, 20, 31, 1)");
        Assert.assertEquals(buildHistoryPage.getBuildStatusSignColor(), "red");
    }

    @Test(dependsOnMethods = "testDisplayNextFailedBuild")
    public void testDisplayFirstSuccessfulBuildAfterFailed() {
        BuildHistoryPage buildHistoryPage = new HomePage(getDriver())
                .openPipelineProject(PIPELINE_PROJECT_NAME)
                .clickSidebarConfigButton()
                .clickSaveButton()
                .gotoHomePage()
                .clickScheduleBuild(PIPELINE_PROJECT_NAME)
                .gotoBuildHistoryPageFromLeftPanel();

        Assert.assertEquals(buildHistoryPage.getProjectName(), PIPELINE_PROJECT_NAME);
        Assert.assertEquals(buildHistoryPage.getBuildDisplayName(), "#3");
        Assert.assertEquals(buildHistoryPage.getListOfStatuses().get(0), "back to normal");
        Assert.assertEquals(buildHistoryPage.getBuildStatusSignColor(), "blue");
    }

    @Test(dependsOnMethods = "testDisplayFirstSuccessfulBuildAfterFailed")
    public void testDisplayNextSuccessfulBuildAfterSuccessful() {
        BuildHistoryPage buildHistoryPage = new HomePage(getDriver())
                .clickScheduleBuild(PIPELINE_PROJECT_NAME)
                .gotoBuildHistoryPageFromLeftPanel();

        Assert.assertEquals(buildHistoryPage.getProjectName(), PIPELINE_PROJECT_NAME);
        Assert.assertEquals(buildHistoryPage.getBuildDisplayName(), "#4");
        Assert.assertEquals(buildHistoryPage.getListOfStatuses().get(0), "stable");
        Assert.assertEquals(buildHistoryPage.getBuildStatusSignColor(), "blue");
    }
}
