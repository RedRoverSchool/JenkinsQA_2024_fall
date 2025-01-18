package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.page.ViewPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

@Epic("16 Dashboard")
public class NewViewDashboardTest extends BaseTest {

    private static final String PROJECT_NAME = "New Freestyle Project";
    private static final String LIST_VIEW = "ListView";
    private static final String MY_VIEW = "MyView";

    @Test
    @Story("US_16.002 Create View")
    @Description("TC_16.002.01 Create My View")
    public void testAddNewMyView() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        List<String> listOfViews = new HomePage(getDriver())
                .clickCreateNewViewButton()
                .typeNameIntoInputField(MY_VIEW)
                .selectMyViewType()
                .clickCreateButton()
                .getViewsList();

        Allure.step(String.format("Expected result: The '%s' view is created and is displayed in the list of views", MY_VIEW));
        Assert.assertTrue(listOfViews.contains(MY_VIEW));
    }

    @Test
    @Story("US_16.002 Create View")
    @Description("TC_16.002.02 Create basic List View")
    public void testAddNewListView() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        List<String> listOfViews = new HomePage(getDriver())
                .clickCreateNewViewButton()
                .typeNameIntoInputField(LIST_VIEW)
                .selectListViewType()
                .clickCreateButton()
                .clickOkButton()
                .getViewsList();

        Allure.step(String.format("Expected result: The '%s' view is created and is displayed in the list of views", LIST_VIEW));
        Assert.assertTrue(listOfViews.contains(LIST_VIEW));
    }

    @Test (dependsOnMethods = "testAddNewMyView")
    @Story("US_16.004 Delete view")
    @Description("TC_16.004.01 Delete 'My View'")
    public void testDeleteMyView() {
        List<String> listOfViews = new ViewPage(getDriver())
                .selectViewTypeToDelete(MY_VIEW)
                .deleteView()
                .clickYesInPopUp()
                .getViewsList();

        Allure.step(String.format("Expected result: The '%s' view was deleted and is not displayed in the list of views", MY_VIEW));
        Assert.assertFalse(listOfViews.contains(MY_VIEW));
    }

    @Test (dependsOnMethods = "testAddNewListView")
    @Story("US_16.004 Delete view")
    @Description("TC_16.004.02 Delete 'List View'")
    public void testDeleteListView() {
        List<String> listOfViews = new ViewPage(getDriver())
                .selectViewTypeToDelete(LIST_VIEW)
                .deleteView()
                .clickYesInPopUp()
                .getViewsList();

        Allure.step(String.format("Expected result: The '%s' view was deleted and is not displayed in the list of views", LIST_VIEW));
        Assert.assertFalse(listOfViews.contains(LIST_VIEW));
    }
}
