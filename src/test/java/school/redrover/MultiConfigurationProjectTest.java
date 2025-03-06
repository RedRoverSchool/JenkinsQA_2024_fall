package school.redrover;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.page.multiConfiguration.MultiConfigurationConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

@Feature("03 MultiConfiguration project")
public class MultiConfigurationProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "MultiConfigurationProject";
    private static final String DESCRIPTIONS = "Descriptions of project";

    @Test
    @Epic("00 New Item")
    @Story("US_00.003 Create MultiConfiguration project")
    @Description("TC_00.003.00 Create MultiConfiguration project with left sidebar button")
    public void testCreateProjectWithoutDescription() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectMultiConfigurationAndClickOk()
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Allure.step(" \uD83D\uDCCC Expected result: Project created");
        Assert.assertEquals(itemList.size(), 1);
        Assert.assertEquals(itemList.get(0), PROJECT_NAME);
    }

    @Test()
    @Story("US_03.001 Add/edit description")
    @Description("TC_03.001.01 Add description to the Multiconfiguration project")
    public void testAddDescriptions() {
        TestUtils.createMultiConfigurationProject(getDriver(), PROJECT_NAME);

        String addDescription = new HomePage(getDriver())
                .openMultiConfigurationProject(PROJECT_NAME)
                .editDescription(DESCRIPTIONS)
                .clickSubmitButton()
                .getDescription();

        Allure.step(" \uD83D\uDCCC Expected result: Description added");
        Assert.assertEquals(addDescription, DESCRIPTIONS);
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.003 Create MultiConfiguration project")
    @Description("TC 00.003.01 Create MultiConfiguration project without name")
    public void testCreateProjectWithoutName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .selectMultiConfigurationProject()
                .getErrorMessage();

        Allure.step(" \uD83D\uDCCC Expected result: The message 'This field cannot be empty, please enter a valid name' ");
        Assert.assertEquals(errorMessage, "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled());
    }

    @Test
    @Story("US_03.004 Build")
    @Description("TC_03.004.01 Check the select time period throttle builds")
    public void testSelectTimePeriodThrottleBuilds() {
        MultiConfigurationConfigPage multiConfigPage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectMultiConfigurationAndClickOk()
                .clickThrottleBuildsCheckbox();

        List<String> selectItems = multiConfigPage.getAllDurationItemsFromSelect();

        for (String item : selectItems) {
            multiConfigPage.selectDurationItem(item);

            Allure.step(" \uD83D\uDCCC Expected result: The assembly time corresponds to the selected one ");
            Assert.assertEquals(multiConfigPage.getSelectedDurationItemText(), item);
        }
    }

    @Test
    @Story("US_03.003 Delete project")
    @Description("TC_03.003.03 Confirmation popup appears when deleting a project on the project page")
    public void testDeletionPopupAppearsOnProjectPage() {
        TestUtils.createMultiConfigurationProject(getDriver(), PROJECT_NAME);

        WebElement deletionPopup = new HomePage(getDriver())
                .openMultiConfigurationProject(PROJECT_NAME)
                .clickDeleteProject()
                .getDeletionPopup();

        Allure.step(" \uD83D\uDCCC Expected result: A pop-up window for deletion is displayed. ");
        Assert.assertTrue(deletionPopup.isDisplayed());
    }

    @Test(dependsOnMethods = "testDeletionPopupAppearsOnProjectPage")
    @Story("US_03.003 Delete project")
    @Description("TC_03.003.05 Confirmation popup appears when deleting a project from the main page via dropdown menu")
    public void testDeletionPopupAppearsOnMainPage() {
        WebElement deletionPopup = new HomePage(getDriver())
                .selectDeleteFromItemMenu(PROJECT_NAME)
                .getDeletionPopup();

        Allure.step(" \uD83D\uDCCC Expected result: A pop-up window for deletion is displayed. ");
        Assert.assertTrue(deletionPopup.isDisplayed());
    }

    @Test(dependsOnMethods = "testDeletionPopupAppearsOnMainPage")
    @Story("US_03.003 Delete project")
    @Description("TC_03.003.06 Confirmation popup appears when deleting a project from 'My views' page")
    public void testDeletionPopupAppearsOnMyViews() {
        WebElement deletionPopup = new HomePage(getDriver())
                .goToMyViews()
                .openDropdownViaChevron(PROJECT_NAME)
                .clickDeleteInProjectDropdown()
                .getDeletionPopup();

        Allure.step(" \uD83D\uDCCC Expected result: A pop-up window for deletion is displayed. ");
        Assert.assertTrue(deletionPopup.isDisplayed());
    }

    @Test
    @Story("US_03.003 Delete project")
    @Description("TC_03.003.02 Delete project directly from project's page")
    public void testDeleteProjectFromProjectPage() {
        TestUtils.createMultiConfigurationProject(getDriver(), PROJECT_NAME);

        String welcomeText = new HomePage(getDriver())
                .openMultiConfigurationProject(PROJECT_NAME)
                .clickDeleteButtonSidebarAndConfirm()
                .getWelcomeTitle();

        Allure.step(" \uD83D\uDCCC Expected result: A welcome message is displayed on the main page");
        Assert.assertEquals(welcomeText, "Welcome to Jenkins!");
    }

    @Test
    @Story("US_03.003 Delete project")
    @Description("TC_03.003.08 Cancel deleting in 'Delete Multi-configuration project' window via 'Cancel' button")
    public void testCancelDeleteProjectFromProjectPage() {
        TestUtils.createMultiConfigurationProject(getDriver(), PROJECT_NAME);

        List<String> itemList= new HomePage(getDriver())
                .openMultiConfigurationProject(PROJECT_NAME)
                .clickDeleteButtonSidebarAndCancel()
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Allure.step(" \uD83D\uDCCC Expected result: Project is not deleted ");
        Assert.assertEquals(itemList.size(), 1);
        Assert.assertEquals(itemList.get(0),PROJECT_NAME);
    }

    @Test
    @Story("US_03.003 Delete project")
    @Description("TC_03.003.01 Delete MultiConfiguration project from the main page via 'Dropdown menu'")
    public void testDeleteViaDropDownMenu() {
        TestUtils.createMultiConfigurationProject(getDriver(), PROJECT_NAME);

        String welcomeText = new HomePage(getDriver())
                .selectDeleteFromItemMenuAndClickYes(PROJECT_NAME)
                .getWelcomeTitle();

        Allure.step(" \uD83D\uDCCC Expected result: A welcome message is displayed on the main page");
        Assert.assertEquals(welcomeText, "Welcome to Jenkins!");
    }

    @Test
    @Story("US_03.003 Delete project")
    @Description("TC_03.003.04 Delete MultiConfiguration project  from 'My views' page via 'Dropdown menu'")
    public void testDeleteFromMyViewsPage() {
        TestUtils.createMultiConfigurationProject(getDriver(), PROJECT_NAME);

        String welcomeText = new HomePage(getDriver())
                .goToMyViews()
                .selectDeleteFromItemMenuAndClickYes(PROJECT_NAME)
                .getTextEmptyFolder();

        Allure.step(" \uD83D\uDCCC Expected result: A message 'This folder is empty' displayed");
        Assert.assertEquals(welcomeText, "This folder is empty");
    }
}