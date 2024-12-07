package school.redrover;

import org.testng.Assert;
import org.testng.annotations.*;
import school.redrover.page.WorkspaceCreatePage;
import school.redrover.runner.BaseTest;

public class WorkspaceTest extends BaseTest {

    @Test
    public void testWorkspaceCreated() {
        WorkspaceCreatePage createWorkspace = new WorkspaceCreatePage(getDriver());
        createWorkspace.createWorkspace();

        String breadcrumbText = createWorkspace.getBreadcrumbText();
        Assert.assertEquals(
                breadcrumbText,
                "Workspace of TestJobWorkspace on Built-In Node",
                "Breadcrumb does not match the expected workspace text!");
    }

    @Test
    public void testBuildNavigation() {
        WorkspaceCreatePage createWorkspace = new WorkspaceCreatePage(getDriver());
        createWorkspace.createWorkspace();

        createWorkspace.clickBuildTwo();
        String breadcrumbText = createWorkspace.getBreadcrumbText();
        Assert.assertEquals(
                breadcrumbText,
                "#2",
                "Breadcrumb does not match build number #2!");
    }

    @Test
    public void testWorkspaceNavigation() {
        WorkspaceCreatePage createWorkspace = new WorkspaceCreatePage(getDriver());
        createWorkspace.createWorkspace();

        createWorkspace.clickBuildTwo();
        createWorkspace.navigateBackToWorkspace();

        String workspaceBreadcrumb = createWorkspace.getWorkspaceBreadcrumbText();
        Assert.assertEquals(
                workspaceBreadcrumb,
                "TestJobWorkspace",
                "Breadcrumb does not match the expected workspace name!");
    }
}