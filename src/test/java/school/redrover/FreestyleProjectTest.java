package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;


public class FreestyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "MyFreestyleProject";

    private static final String DESCRIPTION = "Bla-bla-bla project";

    @Test
    public void testCreateFreestyleProjectWithEmptyName() {
        String emptyNameMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("")
                .selectFreestyleProject()
                .getEmptyNameMessage();

        Assert.assertEquals(emptyNameMessage, "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateFreestyleProjectWithDuplicateName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProject()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘%s’".formatted(PROJECT_NAME));
    }

    @Test
    public void testCreateProjectViaCreateJobButton() {
        String actualProjectName = new HomePage(getDriver())
                .clickCreateJob()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(actualProjectName, PROJECT_NAME);
    }

    @Test
    public void testCreateProjectViaSidebarMenu() {
        String actualProjectName = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(actualProjectName, PROJECT_NAME);
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateProjectViaSidebarMenu")
    public void testEditDescriptionOnProjectPage() {
        final String newDescription = "New " + DESCRIPTION;

        String actualDescription = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clearDescription()
                .editDescription(newDescription)
                .getDescription();

        Assert.assertEquals(actualDescription, newDescription);
    }

    @Test(dependsOnMethods = "testCreateProjectViaSidebarMenu")
    public void testRenameProjectViaSidebarMenu() {
        final String newName = "New " + PROJECT_NAME;

        String actualProjectName = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickRenameOnSidebar()
                .clearOldAndInputNewProjectName(newName)
                .clickRenameButton()
                .getProjectName();

        Assert.assertEquals(actualProjectName, newName);
    }

    @Test
    public void testCheckSidebarMenuItemsOnProjectPage() {
        final List<String> templateSidebarMenu = List.of(
                "Status", "Changes", "Workspace", "Build Now", "Configure", "Delete Project", "Rename");

        List<String> actualSidebarMenu = new HomePage(getDriver())
                .createFreestyleProject(PROJECT_NAME)
                .openFreestyleProject(PROJECT_NAME)
                .getSidebarOptionList();

        Assert.assertEquals(actualSidebarMenu, templateSidebarMenu);
    }
}