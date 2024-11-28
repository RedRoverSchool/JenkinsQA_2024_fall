package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.page.PipelineProjectPage;
import school.redrover.runner.BaseTest;


public class PipelineTest extends BaseTest {

    private static final String PROJECT_NAME = "PipelineProject";

    @Test
    public void testCreate() {
        String projectName = "PipelineProjectNew";
        String actualProjectName = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .gotoHomePage()
                .getItemName();

        Assert.assertEquals(actualProjectName, projectName);
    }

    @Test
    public void testCreateWithEmptyName() {

        String actualErrorMessage = new HomePage(getDriver())
                .clickNewItem()
                .selectPipeline()
                .getErrorMessage();

        Assert.assertEquals(actualErrorMessage, "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateWithNotUniqueName() {
        String nonUniqueProjectName = "PipelineProjectUnique";

        String actualErrorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(nonUniqueProjectName)
                .selectPipelineAndClickOk()
                .gotoHomePage()
                .clickNewItem()
                .enterItemName(nonUniqueProjectName)
                .selectPipeline()
                .getErrorMessage();

        Assert.assertEquals(actualErrorMessage, "» A job already exists with the name ‘%s’".formatted(nonUniqueProjectName));
    }

    @Test
    public void testCreateWithDescription() {
        final String description = "The leading open source automation server, Jenkins provides hundreds of plugins to support building, deploying and automating any project.";
        final String projectName = "PipelineProjectAndDescription";

        String actualDescription = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .enterDescription(description)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(actualDescription, description);
    }

    @Test()
    public void testRename() {
        final String projectName = "PipelineProject2New";

        PipelineProjectPage projectPage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .clickOnPipelineName(PROJECT_NAME)
                .clickRenameSidebar(PROJECT_NAME)
                .cleanInputFieldAndTypeName(projectName)
                .clickRenameButton();

        Assert.assertEquals(projectPage.getTitle(), projectName);
        Assert.assertEquals(projectPage.getProjectNameBreadcrumb(),projectName);
    }

    @Test()
    public void testWarningMessageOnRenameProjectPage() {
        final String projectName = "PipelineProjectRename";

        String actualWarningMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .clickOnPipelineName(projectName)
                .clickRenameSidebar(projectName)
                .getWarningMessage();

        Assert.assertEquals(actualWarningMessage, "The new name is the same as the current name.");
    }

    @Test
    public void testRenameByChevronDashboard() {
        final String projectName = "PipelineRenameByChevron";
        final String projectNameNew = "PipelineRenameByChevronNew";

        PipelineProjectPage projectPage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .goToPipelineRenamePageViaDropdown(projectName)
                .cleanInputFieldAndTypeName(projectNameNew)
                .clickRenameButton();

        Assert.assertEquals(projectPage.getTitle(), projectNameNew);
        Assert.assertEquals(projectPage.getProjectNameBreadcrumb(), projectNameNew);
    }

    @Test(dependsOnMethods = "testRenameByChevronDashboard")
    public void testRenameByChevronDisplayedOnHomePageWithCorrectName() {
        final String projectNameNew = "PipelineRenameByChevronNew";

        boolean isDisplayed = new HomePage(getDriver())
                .getItemList()
                .contains(projectNameNew);

        Assert.assertTrue(isDisplayed);
    }

    @Test
    public void testAddDescription() {
        final String projectName = "PipelineProjectAndDesc";
        final String desc = "Add description for new project 45";

        String descriptionText = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .gotoHomePage()
                .openPipelineProject(projectName)
                .clickAddDescriptionButton()
                .enterDescription(desc)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(descriptionText, desc);
    }

    @Test(dependsOnMethods = "testRename")
    public void testDelete() {

        String welcomeTitle = new HomePage(getDriver())
                .openPipelineProject("PipelineProject2New")
                .clickDeletePipelineSidebarAndConfirmDeletion()
                .getWelcomeTitle();

        Assert.assertEquals(welcomeTitle, "Welcome to Jenkins!");
    }

    @Test()
    public void testDeleteByChevronDashboard() {
        final String projectName = "ProjectDeleteByChevron";

        String welcomeTitle = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .gotoHomePage()
                .openDropdownViaChevron(projectName)
                .clickDeletePipelineChevronDropdownMenu(projectName)
                .clickYesForConfirmDelete()
                .getWelcomeTitle();

        Assert.assertEquals(welcomeTitle, "Welcome to Jenkins!");
    }

    @Test()
    public void testDeleteByChevronBreadcrumb() {
        final String projectName = "ProjectDeleteByChevronBreadcrumb";

        String welcomeTitle = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .gotoHomePage()
                .openPipelineProject2(projectName)
                .openDropDownMenuByChevronBreadcrumb(projectName)
                .clickDeletePipelineSidebarAndConfirmDeletion()
                .getWelcomeTitle();

        Assert.assertEquals(welcomeTitle, "Welcome to Jenkins!");
    }


    @Test
    public void testPipelineDisabledTooltipOnHomePage() {
        String tooltipValue = new HomePage(getDriver())
                                  .clickNewItem()
                                  .enterItemName(PROJECT_NAME)
                                  .selectPipelineAndClickOk()
                                  .clickToggleToDisableOrEnableProject()
                                  .clickSaveButton()
                                  .gotoHomePage()
                                  .getTooltipValue(PROJECT_NAME);

        Assert.assertEquals(tooltipValue, "Disabled");
    }
}
