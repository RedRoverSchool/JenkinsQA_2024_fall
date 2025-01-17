package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DashboardTest extends BaseTest {

    private static final String DESCRIPTION_TEXT = "It's my workspace";
    private static final String NEW_TEXT = "Hello! ";
    private static final String TEXT_DESCRIPTION_BUTTON = "Add description";
    private final static String NEW_ITEM = "New Item";
    private static final String VALID_PIPELINE_SCRIPT = """
            pipeline {
                agent any
                stages {
                    stage('Checkout') {
                        steps {echo 'Step: Checkout code from repository'}
                        
            """;
    private static final String INVALID_PIPELINE_SCRIPT = """
            error_pipeline {{{
                agent any
                stages {
                    stage('Checkout') {
                        steps {echo 'Step: Checkout code from repository'}
                    }
                 }
            }
            """;
    private final List<String> createdProjectList = new ArrayList<>();

    private void preparationCreateNotBuiltProject(String projectName) {
        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage();
    }

    private void preparationCreateDisableProject(String projectName) {
        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .clickToggleToDisableOrEnableProject()
                .clickSaveButton()
                .gotoHomePage();
    }

    private void preparationCreateSuccessBuiltProject(String projectName) {
        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .addScriptToPipeline(VALID_PIPELINE_SCRIPT)
                .clickSaveButton()
                .clickOnBuildNowItemOnSidePanelAndWait()
                .gotoHomePage();
    }

    private void preparationCreateFailedBuiltProject(String projectName) {
        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .addScriptToPipeline(INVALID_PIPELINE_SCRIPT)
                .clickSaveButton()
                .clickOnBuildNowItemOnSidePanelAndWait()
                .gotoHomePage();
    }

    @DataProvider
    public Object[][] projectNameProvider() {
        return new Object[][]{
                {"FPipelineProject"},
                {"APipelineProject"},
                {"ZPipelineProject"}
        };
    }

    @Test(dataProvider = "projectNameProvider")
    public void testCreate(String projectName) {
        TestUtils.createFreestyleProject(getDriver(), projectName);
        createdProjectList.add(projectName);

        List<String> projectNameList = new HomePage(getDriver())
                .getItemList();

        Assert.assertListContainsObject(
                projectNameList,
                projectName,
                "Project is not created or displayed on Home page");
    }

    @Test(dependsOnMethods = "testCreate")
    public void testVerifyProjectOrderByNameASCByDefault() {
        List<String> projectNameList = new HomePage(getDriver())
                .getItemList();

        List<String> expectedList = createdProjectList.stream()
                .sorted(Comparator.naturalOrder())
                .toList();

        Assert.assertEquals(
                projectNameList,
                expectedList,
                "Projects are not sorted in ascending order by default.");
    }

    @Test(dependsOnMethods = "testVerifyDisplayIconDownArrowNextToNameByDefault")
    public void testVerifyProjectOrderByNameDesc() {
        final List<String> expectedList = createdProjectList.stream().sorted(Comparator.reverseOrder()).toList();

        List<String> actualList = new HomePage(getDriver())
                .clickNameTableHeaderChangeOrder()
                .getItemList();

        Assert.assertEquals(
                actualList,
                expectedList,
                "The project list is not sorted in descending order.");
    }

    @Test(dependsOnMethods = "testVerifyProjectOrderByNameASCByDefault")
    public void testVerifyDisplayIconDownArrowNextToNameByDefault() {
        String titleTableHeader = new HomePage(getDriver())
                .getTitleTableHeaderWithDownArrow();

        Assert.assertEquals(titleTableHeader, "Name");
    }

    @Test(dependsOnMethods = "testVerifyDisplayIconDownArrowNextToNameByDefault")
    public void testDisplayDownArrowOnSelectedColumnName() {

        String titleTableHeader = new HomePage(getDriver())
                .clickStatusTableHeaderChangeOrder()
                .getTitleTableHeaderWithUpArrow();

        Assert.assertEquals(titleTableHeader, "S");
    }

    @Test
    public void testVerifyProjectOrderByStatusASCByDefault() {
        final String successBuilt = "FPipelineProject";
        final String disabled = "APipelineProject";
        final String failedBuilt = "ZPipelineProject";
        final String notBuilt = "1PipelineProject";

        preparationCreateNotBuiltProject(notBuilt);
        preparationCreateDisableProject(disabled);
        preparationCreateSuccessBuiltProject(successBuilt);
        preparationCreateFailedBuiltProject(failedBuilt);

        List<String> projectNameList = new HomePage(getDriver())
                .clickStatusTableHeaderChangeOrder()
                .getItemList();

        Assert.assertEquals(projectNameList.size(), 4);
        Assert.assertEquals(projectNameList, List.of(notBuilt, disabled, successBuilt, failedBuilt));
    }

    @Test
    public void testFullNameHelperText() {
        String fullNameInputTip = new HomePage(getDriver())
                .clickAdmin()
                .clickConfigureSidebar()
                .clickFullNameTooltip()
                .getFullNameHelperInputText();

        Assert.assertTrue(fullNameInputTip.contains(
                "Specify your name in a more human-friendly format, so that people can see your real name as opposed to your ID."));
    }

    @Test
    public void testLogOut() {
        String signInTitle = new HomePage(getDriver())
                .clickLogOut()
                .getSignInTitle();

        Assert.assertEquals(signInTitle, "Sign in to Jenkins");
    }

    @Test
    public void testGetStatusIDDescription() {
        String adminDescription = new HomePage(getDriver())
                .openAdminDropdownMenu()
                .clickConfigureAdminDropdownMenu()
                .clickStatusSidebar()
                .getUserIDText();

        Assert.assertEquals(adminDescription, "Jenkins User ID: admin");
    }

    @Test
    public void testNavigateCredentialsMenu() {
        String pageTitleText = new HomePage(getDriver())
                .openAdminDropdownMenu()
                .clickCredentialsAdminDropdownMenu()
                .getPageTitleText();

        Assert.assertEquals(pageTitleText, "Credentials");
    }

    @Test
    public void testAddDomainArrow() {
        String user = new HomePage(getDriver())
                .openAdminDropdownMenu()
                .clickCredentialsAdminDropdownMenu()
                .getUserName();

        Assert.assertFalse(user.isEmpty());
    }

    @Test
    public void testisDisplayedDomainElementDropdown() {
        boolean itemMenuDisplayed = new HomePage(getDriver())
                .openAdminDropdownMenu()
                .clickCredentialsAdminDropdownMenu()
                .clickDropdownMenu()
                .getDisplayedItemMenu();

        Assert.assertTrue(itemMenuDisplayed);
    }

    @Test
    public void testAddDescription() {

        String textDescription = new HomePage(getDriver())
                .clickDescriptionButton()
                .addDescription(DESCRIPTION_TEXT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(textDescription, DESCRIPTION_TEXT);
    }

    @Test(dependsOnMethods = "testAddDescription")
    public void testEditDescription() {

        String newText = new HomePage(getDriver())
                .clickDescriptionButton()
                .addDescription(NEW_TEXT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(newText, NEW_TEXT + DESCRIPTION_TEXT);
    }

    @Test(dependsOnMethods = "testEditDescription")
    public void testDeleteDescription() {

        String descriptionButton = new HomePage(getDriver())
                .clickDescriptionButton()
                .clearDescription()
                .clickSaveButton()
                .getDescriptionButtonTitle();

        Assert.assertEquals(descriptionButton, TEXT_DESCRIPTION_BUTTON);
    }

    @Test
    public void testDescriptionPreviewButton() {

        String textPreview = new HomePage(getDriver())
                .clickDescriptionButton()
                .addDescription(DESCRIPTION_TEXT)
                .clickPreviewButton()
                .getTextPreview();

        Assert.assertEquals(textPreview, DESCRIPTION_TEXT);
    }

    @Test
    @Epic("16 Dashboard")
    @Story("US_16.003 Item context Menu > Check content")
    @Description("TC_16.003.01 'New Item' button is present in the 'Dashboard' dropdown options")
    public void testPossibilityOfCreatingNewItemFromBreadcrumbBar() {
        String newItemButton = new HomePage(getDriver())
                .selectBreadcrumbBarMenu()
                .getBreadcrumbBarMenuList().get(0).getText();

        Allure.step(String.format("Expected Result: The button '%s' is present in dropdown options.", NEW_ITEM));
        Assert.assertEquals(newItemButton, NEW_ITEM);
    }
}
