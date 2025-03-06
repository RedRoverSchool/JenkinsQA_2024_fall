package school.redrover;

import io.qameta.allure.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.testdata.TestDataProvider;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

@Feature("05 Multibranch pipeline")
public class MultibranchPipelineTest extends BaseTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "MultibranchName";
    private static final String MULTIBRANCH_PIPELINE_NAME2 = "NewMultibranchName";

    @Test
    @Epic("00 New Item")
    @Story("US_00.005 Create Multibranch Pipeline")
    @Description("TC_00.005.01 Create Multibranch Pipeline with left sidebar button")
    public void testCreate() {
        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Allure.step(" \uD83D\uDCCC Expected result: Created project '%s' is displayed on the Home page".formatted(MULTIBRANCH_PIPELINE_NAME));
        Assert.assertListContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME, "Project is not created");
    }

    @Test
    @Story("US_05.006 Multibranch pipeline")
    @Description("TC_05.006.01 Add description while creating Multibranch Pipeline")
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
    @Epic("00 New Item")
    @Story("US_00.005 Create Multibranch Pipeline")
    @Description("TC_00.005.03 Verify error message when create with same name")
    public void testVerifyErrorMessageWhenCreateWithSameName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineProject()
                .getErrorMessage();

        Allure.step(" \uD83D\uDCCC Expected result: Error message '» A job already exists with the name ‘%s’' is displayed".formatted(MULTIBRANCH_PIPELINE_NAME));
        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘%s’".formatted(MULTIBRANCH_PIPELINE_NAME));
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.005 Create Multibranch Pipeline")
    @Description("TC_00.005.04 Verify error message when create with dot")
    public void testVerifyErrorMessageWhenCreateWithDot() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(".")
                .getErrorMessage();

        Allure.step(" \uD83D\uDCCC Expected result: Error message '» “.” is not an allowed name' is displayed");
        Assert.assertEquals(errorMessage, "» “.” is not an allowed name");
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.005 Create Multibranch Pipeline")
    @Description("TC_00.005.05 Verify error message when create with empty name")
    public void testVerifyErrorMessageWhenCreateWithEmptyName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .selectMultibranchPipelineProject()
                .getErrorMessage();

        Allure.step(" \uD83D\uDCCC Expected result: Error message '» This field cannot be empty, please enter a valid name' is displayed");
        Assert.assertEquals(errorMessage, "» This field cannot be empty, please enter a valid name");
    }

    @Test(dependsOnMethods = "testVerifyErrorMessageWhenCreateWithSameName")
    @Story("US_05.001 Multibranch pipeline")
    @Description("TC_05.001.01 Rename its title using sidebar (EM)")
    public void testRenameMultibranchViaSideBar() {
        List<String> projectList = new HomePage(getDriver())
                .openMultibranchPipelineProject(MULTIBRANCH_PIPELINE_NAME)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName(MULTIBRANCH_PIPELINE_NAME2)
                .clickRenameButton()
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Allure.step(" \uD83D\uDCCC Expected result: Renamed project '%s' is displayed on the Home page".formatted(MULTIBRANCH_PIPELINE_NAME2));
        Assert.assertListContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME2,
                "Project is not renamed");
    }

    @Test
    @Story("US_05.001 Multibranch pipeline")
    @Description("TC_05.001.03 Rename its title using dropdown on the dashboard (EM)")
    public void testRenameProjectViaDashboardChevron() {
        TestUtils.createMultiBranchPipeline(getDriver(), MULTIBRANCH_PIPELINE_NAME);

        List<String> projectList = new HomePage(getDriver())
                .selectRenameFromItemDropdown(MULTIBRANCH_PIPELINE_NAME)
                .clearInputFieldAndTypeName(MULTIBRANCH_PIPELINE_NAME2)
                .clickRenameButton()
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Allure.step(" \uD83D\uDCCC Expected result: Renamed project '%s' is displayed on the Home page".formatted(MULTIBRANCH_PIPELINE_NAME2), () -> {
            Assert.assertListContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME2, "Project didn't rename");
            Assert.assertListNotContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME, "Project didn't rename");
        });
    }

    @Test
    @Story("US_05.001 Multibranch pipeline")
    @Description("TC_05.001.02 Rename its title using dropdown in the breadcrumb trail (EM)")
    public void testRenameProjectViaBreadcrumbChevron() {
        TestUtils.createMultiBranchPipeline(getDriver(), MULTIBRANCH_PIPELINE_NAME);

        List<String> projectList = new HomePage(getDriver())
                .openMultibranchPipelineProject(MULTIBRANCH_PIPELINE_NAME)
                .openBreadcrumbDropdown()
                .clickRenameBreadcrumbDropdown()
                .clearInputFieldAndTypeName(MULTIBRANCH_PIPELINE_NAME2)
                .clickRenameButton()
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Allure.step(" \uD83D\uDCCC Expected result: Renamed project '%s' is displayed on the Home page".formatted(MULTIBRANCH_PIPELINE_NAME2), () -> {
            Assert.assertListContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME2, "List doesn't contain rename project");
            Assert.assertListNotContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME, "List contains not renamed project");
        });
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.005 Create Multibranch Pipeline")
    @Description("TC_00.005.02 Selecting Triggers Scan Period From Config Page Multibranch Pipeline")
    public void testSelectingTriggersScanPeriodFromConfigPage() {
        WebElement selectedValue = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickScanMultibranchPipelineButton()
                .clickPeriodicalScanningCheckbox()
                .selectingIntervalValue()
                .getSelectedValue();

        Allure.step(" \uD83D\uDCCC Expected result: Selected value is displayed");
        Assert.assertTrue(selectedValue.isSelected());
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.005 Create Multibranch Pipeline")
    public void testCreateJobAndDisplayAmongOtherJobsOnStartPage() {
        TestUtils.createPipelineProject(getDriver(), MULTIBRANCH_PIPELINE_NAME);
        TestUtils.createPipelineProject(getDriver(), MULTIBRANCH_PIPELINE_NAME2);

        List<String> jobNames = new HomePage(getDriver())
                .getItemList();

        Assert.assertListContainsObject(jobNames, MULTIBRANCH_PIPELINE_NAME2, MULTIBRANCH_PIPELINE_NAME2);
    }

    @Test(dataProvider = "providerUnsafeCharacters", dataProviderClass = TestDataProvider.class)
    @Epic("00 New Item")
    @Story("US_00.005 Create Multibranch Pipeline")
    @Description("TC_00.005.06 Create Multibranch Pipeline with unsafe characters in name")
    public void testEnterInvalidNameAndSeesAppropriateMessages(String unsafeCharacter) {
        String invalidNameMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(unsafeCharacter)
                .selectMultibranchPipeline()
                .getInvalidNameMessage();

        Allure.step(" \uD83D\uDCCC Expected result: Error message is displayed");
        Assert.assertEquals(invalidNameMessage, "» ‘%s’ is an unsafe character".formatted(unsafeCharacter));

    }

    @Test
    @Story("US_05.005 Delete Multibranch pipeline")
    @Description("TC_05.005.02 Delete Multibranch pipeline using sidebar status page")
    public void testDeleteJobUsingSidebarStatusPage() {
        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .getHeader()
                .gotoHomePage()
                .openMultibranchPipelineProject(MULTIBRANCH_PIPELINE_NAME)
                .clickDeleteButtonSidebarAndConfirm()
                .getItemList();

        Allure.step(" \uD83D\uDCCC Expected result: Project '%s' is deleted".formatted(MULTIBRANCH_PIPELINE_NAME));
        Assert.assertListNotContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME,
                "Project is not deleted");
    }

    @Test(dependsOnMethods = "testRenameMultibranchViaSideBar")
    @Story("US_05.005 Delete Multibranch pipeline")
    @Description("TC_05.005.03 Delete Multibranch pipeline using item dropdown on dashboard")
    public void testDeleteJobUsingItemDropdownOnDashboard() {
        List<String> projectList = new HomePage(getDriver())
                .selectDeleteFromItemMenuAndClickYes(MULTIBRANCH_PIPELINE_NAME2)
                .getItemList();

        Allure.step(" \uD83D\uDCCC Expected result: Project '%s' is deleted".formatted(MULTIBRANCH_PIPELINE_NAME2));
        Assert.assertListNotContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME2,
                "Project is not deleted");
    }

    @Test
    @Story("US_05.005 Delete Multibranch pipeline")
    @Description("TC_05.005.04 Delete Multibranch pipeline using dropdown breadcrumb on job page")
    public void testDeleteJobUsingDropdownBreadcrumbJobPage() {
        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .getHeader()
                .gotoHomePage()
                .openMultibranchPipelineProject(MULTIBRANCH_PIPELINE_NAME)
                .openBreadcrumbDropdown()
                .clickDeleteBreadcrumbDropdownAndConfirm()
                .getItemList();

        Allure.step(" \uD83D\uDCCC Expected result: Project '%s' is deleted".formatted(MULTIBRANCH_PIPELINE_NAME));
        Assert.assertListNotContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME,
                "Project is not deleted");
    }
}