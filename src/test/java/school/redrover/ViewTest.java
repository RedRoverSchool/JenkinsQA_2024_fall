package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.page.NewViewPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;
import java.util.Map;

public class ViewTest extends BaseTest {

    private static final String PIPELINE_NAME = "PipelineName";
    private static final String VIEW_NAME = "ViewName";

    @Test
    @Epic("16 Dashboard")
    @Story("US_16.002 Create view")
    @Description("TC_16.002.02 Create basic List View")
    public void testCreateListViewForSpecificJob() {
        TestUtils.createPipelineProject(getDriver(), PIPELINE_NAME);

        List<String> viewList = new HomePage(getDriver())
                .clickCreateNewViewButton()
                .typeNameIntoInputField(VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
                .selectJobCheckBoxByName(PIPELINE_NAME)
                .clickOkButton()
                .getHeader()
                .gotoHomePage()
                .getViewList();

        Allure.step(String.format("Expected result: New List View '%s' is displayed", VIEW_NAME));
        Assert.assertListContainsObject(
                viewList,
                VIEW_NAME,
                "New List View is displayed");
    }

    @Test(dependsOnMethods = "testCreateListViewForSpecificJob")
    @Epic("16 Dashboard")
    @Story("US_16.002 Create view")
    @Description("(NO TC) 16.002.04 Verify description for available types of Views")
    public void testVerifyDescriptionsForViewTypes() {
        final Map<String, String> expectedDescriptions = Map.of(
                "List View",
                "Shows items in a simple list format. You can choose which jobs are to be displayed in which view.",
                "My View",
                "This view automatically displays all the jobs that the current user has an access to."
        );

        Map<String, String> actualDescriptions = new HomePage(getDriver())
                .clickCreateNewViewButton()
                .getTypeToDescriptionMap();

        expectedDescriptions.forEach((expectedType, expectedDescription) -> {
            String actualDescription = actualDescriptions.get(expectedType);

            Allure.step("Expected result: Two types of Views with their description are presented to create: List View and My View");
            Assert.assertNotNull(
                    actualDescription, "Description is missing for type: " + expectedType);
            Assert.assertEquals(actualDescription, expectedDescription,
                    "Description does not match for type: " + expectedType);
        });
    }

    @Test(dependsOnMethods = "testVerifyDescriptionsForViewTypes")
    @Epic("16 Dashboard")
    @Story("US_16.002 Create view")
    @Description("(NO TC) 16.005.02 Edit view by delete its columns")
    public void testDeleteViewColumnForSpecificProjectByXButton() {
        final String columnName = "Weather";

        List<String> columnList = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME)
                .clickEditView(VIEW_NAME)
                .clickDeleteColumnByName(columnName)
                .clickOkButton()
                .getColumnList();

        Allure.step(String.format("Expected result: '%s' column has been deleted and hasn't presented anymore", columnName));
        Assert.assertEquals(columnList.size(), 6);
        Assert.assertListNotContainsObject(columnList, columnName, "Deleted column is not displayed");
    }

    @Test(dependsOnMethods = "testDeleteViewColumnForSpecificProjectByXButton")
    @Epic("16 Dashboard")
    @Story("US_16.002 Create view")
    @Description("(NO TC) 16.005.03 Edit view by add more columns")
    public void testAddColumn() {
        final String columnName = "Git Branches";

        List<String> columnList = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME)
                .clickEditView(VIEW_NAME)
                .clickAddColumn()
                .selectColumnByName(columnName)
                .clickOkButton()
                .getColumnList();

        Allure.step(String.format("Expected result: New column '%s' is added", columnName));
        Assert.assertEquals(columnList.size(), 7);
        Assert.assertTrue(columnList.contains(columnName));
    }

    @Test
    @Epic("16 Dashboard")
    @Story("US_16.002 Create view")
    @Description("(NO TC) 16.002.05 Verify default page source while View creating")
    public void testCreateNewViewForm() {
        TestUtils.createFolder(getDriver(), "NewFolder");

        NewViewPage newViewPage = new HomePage(getDriver())
                .clickCreateNewViewButton();

        Allure.step("Expected result: Input field should be empty.");
        Assert.assertTrue(newViewPage.getInputFromNameField().isEmpty(), "Input field should be empty.");

        Allure.step("Expected result: ListView radio button should not be selected.");
        Assert.assertFalse(newViewPage.isRadioButtonListViewSelected(), "ListView radio button should not be selected.");

        Allure.step("Expected result: MyView radio button should not be selected.");
        Assert.assertFalse(newViewPage.isRadioButtonMyViewSelected(), "MyView radio button should not be selected.");

        Allure.step("Expected result: Create button should be disabled.");
        Assert.assertFalse(newViewPage.isCreateButtonEnabled(), "Create button should be disabled.");
    }
}