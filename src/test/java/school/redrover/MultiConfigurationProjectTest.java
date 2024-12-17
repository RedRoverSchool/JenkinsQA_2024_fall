package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.page.multiConfiguration.MultiConfigurationConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class MultiConfigurationProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "MultiConfigurationProject";
    private static final String DESCRIPTIONS = "Descriptions of project";

    @Test(description = "Create project without descriptions")
    public void testCreateProjectWithoutDescription() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectMultiConfigurationAndClickOk()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(itemList.size(), 1);
        Assert.assertEquals(itemList.get(0), PROJECT_NAME);
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateProjectWithoutDescription", description = " MultiConfigurationProjectTest | Add descriptions to existing project")
    public void testAddDescriptions() {
        String addDescription = new HomePage(getDriver())
                .openMultiConfigurationProject(PROJECT_NAME)
                .editDescription(DESCRIPTIONS)
                .clickSubmitButton()
                .getDescription();


        Assert.assertEquals(addDescription, DESCRIPTIONS);
    }

    @Test
    public void testCreateProjectWithoutName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .selectMultiConfigurationProject()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled());
    }

    @Test
    public void testSelectTimePeriodThrottleBuilds() {
        MultiConfigurationConfigPage multiConfigPage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectMultiConfigurationAndClickOk()
                .clickThrottleBuildsCheckbox();

        List<String> selectItems = multiConfigPage.getAllDurationItemsFromSelect();

        for (String item : selectItems) {
            multiConfigPage.selectDurationItem(item);

            Assert.assertEquals(multiConfigPage.getSelectedDurationItemText(), item);
        }
    }

    @Test(dependsOnMethods = "testCreateProjectWithoutDescription")
    public void testCreateWithExistingName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProject()
                .getInvalidNameMessage();

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘%s’".formatted(PROJECT_NAME));
    }

    @Test
    public void testDeletionPopupAppearsOnProjectPage() {
        TestUtils.createMultiConfigurationProject(getDriver(), PROJECT_NAME);

        WebElement deletionPopup = new HomePage(getDriver())
                .openMultiConfigurationProject(PROJECT_NAME)
                .clickDeleteProject()
                .getDeletionPopup();

        Assert.assertTrue(deletionPopup.isDisplayed());
    }

    @Test(dependsOnMethods = "testDeletionPopupAppearsOnProjectPage")
    public void testDeletionPopupAppearsOnMainPage() {
        WebElement deletionPopup = new HomePage(getDriver())
                .selectDeleteFromItemMenu(PROJECT_NAME)
                .getDeletionPopup();

        Assert.assertTrue(deletionPopup.isDisplayed());
    }

    @Test(dependsOnMethods = "testDeletionPopupAppearsOnMainPage")
    public void testDeletionPopupAppearsOnMyViews() {
        WebElement deletionPopup = new HomePage(getDriver())
                .goToMyViews()
                .openDropdownViaChevron(PROJECT_NAME)
                .clickDeleteInProjectDropdown()
                .getDeletionPopup();

        Assert.assertTrue(deletionPopup.isDisplayed());
    }

    @Test
    public void testDeleteProjectFromProjectPage() {
        TestUtils.createMultiConfigurationProject(getDriver(), PROJECT_NAME);

        String welcomeText = new HomePage(getDriver())
                .openMultiConfigurationProject(PROJECT_NAME)
                .clickDeleteButtonSidebarAndConfirm()
                .getWelcomeTitle();

        Assert.assertEquals(welcomeText, "Welcome to Jenkins!");
    }

    @Test
    public void testDeleteViaDropDownMenu() {
        TestUtils.createMultiConfigurationProject(getDriver(), PROJECT_NAME);

        String welcomeText = new HomePage(getDriver())
                .selectDeleteFromItemMenuAndClickYes(PROJECT_NAME)
                .getWelcomeTitle();

        Assert.assertEquals(welcomeText, "Welcome to Jenkins!");
    }

    @Test
    public void testDeleteFromMyViewsPage() {
        TestUtils.createMultiConfigurationProject(getDriver(), PROJECT_NAME);

        String welcomeText = new HomePage(getDriver())
                .goToMyViews()
                .selectDeleteFromItemMenuAndClickYes(PROJECT_NAME)
                .getTextEmptyFolder();

        Assert.assertEquals(welcomeText, "This folder is empty");
    }
}