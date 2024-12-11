package school.redrover;

import org.testng.Assert;
import org.testng.annotations.*;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;

public class WorkspaceTest extends BaseTest {

    final private String PROJECT_NAME = "TestJobWorkspace";

    @Test
    public void testWorkspaceIsOpened() {
        String workspace = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProject()
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .clickBuildNowSidebar()
                .clickWorkspaceSidebar()
                .getBreadCrumb();

        Assert.assertEquals(workspace, "Workspace of " + PROJECT_NAME + " on Built-In Node");
    }

    @Test(dependsOnMethods = "testWorkspaceIsOpened")
    public void testLastBuildIsOpened() {

        String secondBuild = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickBuildNowSidebar()
                .clickWorkspaceSidebar()
                .clickOnSuccessBuildIconForLastBuild()
                .getBreadCrumb();

        Assert.assertEquals(secondBuild, "#2");
    }
}