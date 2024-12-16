package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class MultibranchPipelineTest extends BaseTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "MultibranchName";
    private static final String MULTIBRANCH_PIPELINE_NAME2 = "NewMultibranchName";

    @DataProvider
    public Object[][] providerUnsafeCharacters() {

        return new Object[][]{
                {"\\"}, {"]"}, {":"}, {"#"}, {"&"}, {"?"}, {"!"}, {"@"},
                {"$"}, {"%"}, {"^"}, {"*"}, {"|"}, {"/"}, {"<"}, {">"},
                {"["}, {";"}
        };
    }

    @Test
    public void testCreate() {
        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertListContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME,
                "Project is not created");
    }

    @Test
    public void testAddDescriptionCreatingProject() {
        final String description = "AddedDescription";

        String actualDescription = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .enterDescription(description)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(actualDescription, description);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testVerifyErrorMessageWhenCreateWithSameName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineProject()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘%s’".formatted(MULTIBRANCH_PIPELINE_NAME));
    }

    @Test
    public void testVerifyErrorMessageWhenCreateWithDot() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(".")
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "» “.” is not an allowed name");
    }

    @Test
    public void testVerifyErrorMessageWhenCreateWithEmptyName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .selectMultibranchPipelineProject()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "» This field cannot be empty, please enter a valid name");
    }

    @Test(dependsOnMethods = "testVerifyErrorMessageWhenCreateWithSameName")
    public void testRenameMultibranchViaSideBar() {
        List<String> projectList = new HomePage(getDriver())
                .openMultibranchPipelineProject(MULTIBRANCH_PIPELINE_NAME)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName(MULTIBRANCH_PIPELINE_NAME2)
                .clickRenameButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertListContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME2,
                "Project is not renamed");
    }

    @Test
    public void testSelectingTriggersScanPeriodFromConfigPage() {
        WebElement selectedValue = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickScanMultibranchPipelineButton()
                .clickPeriodicalScanningCheckbox()
                .selectingIntervalValue()
                .getSelectedValue();

        Assert.assertTrue(selectedValue.isSelected());
    }

    @Test
    public void testCreateJobAndDisplayAmongOtherJobsOnStartPage() {

        List<String> jobNames = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME2)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertListContainsObject(jobNames, MULTIBRANCH_PIPELINE_NAME2, MULTIBRANCH_PIPELINE_NAME2);
    }

    @Test(dataProvider = "providerUnsafeCharacters")
    public void testEnterInvalidNameAndSeesAppropriateMessages(String unsafeCharacter) {
        String invalidNameMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(unsafeCharacter)
                .selectMultibranchPipeline()
                .getInvalidNameMessage();

        Assert.assertEquals(invalidNameMessage, "» ‘%s’ is an unsafe character".formatted(unsafeCharacter));
    }

    @Test(dependsOnMethods = "testRenameMultibranchViaSideBar")
    public void testCreateJobAndJobNameVisibleOnStatusPage() {
        String title = new HomePage(getDriver())
                .openMultibranchPipelineProject(MULTIBRANCH_PIPELINE_NAME2)
                .getItemName();

        Assert.assertEquals(title, MULTIBRANCH_PIPELINE_NAME2);
    }

    @Test(dependsOnMethods = "testCreateJobAndJobNameVisibleOnStatusPage")
    public void testCreateJobAndJobNameVisibleOnBreadcrumb() {
        String breadcrumbName = new HomePage(getDriver())
                .openMultibranchPipelineProject(MULTIBRANCH_PIPELINE_NAME2)
                .getBreadcrumbName();

        Assert.assertEquals(breadcrumbName, MULTIBRANCH_PIPELINE_NAME2);
    }

    @Test
    public void testDeleteJobUsingSidebarStatusPage() {
        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .openMultibranchPipelineProject(MULTIBRANCH_PIPELINE_NAME)
                .clickDeleteButtonSidebarAndConfirm()
                .getItemList();

        Assert.assertListNotContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME,
                "Project is not deleted");
    }

    @Test(dependsOnMethods = "testCreateJobAndJobNameVisibleOnBreadcrumb")
    public void testDeleteJobUsingItemDropdownOnDashboard() {
        List<String> projectList = new HomePage(getDriver())
                .selectDeleteFromItemMenuAndClickYes(MULTIBRANCH_PIPELINE_NAME2)
                .getItemList();

        Assert.assertListNotContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME2,
                "Project is not deleted");
    }

    @Test
    public void testDeleteJobUsingDropdownBreadcrumbJobPage() {
        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .openMultibranchPipelineProject(MULTIBRANCH_PIPELINE_NAME)
                .openBreadcrumbDropdown()
                .clickDeleteBreadcrumbDropdownAndConfirm()
                .getItemList();

        Assert.assertListNotContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME,
                "Project is not deleted");
    }
}