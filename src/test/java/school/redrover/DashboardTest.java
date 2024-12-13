package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.Comparator;
import java.util.List;

public class DashboardTest extends BaseTest {

    private static final String invalidPipelineScriptFile = """
            error_pipeline {{{
                agent any
                stages {
                    stage('Checkout') {
                        steps {echo 'Step: Checkout code from repository'}
                    }
                 }
            }
            """;

    private static final String validPipelineScriptFile = """
            pipeline {
                agent any
                stages {
                    stage('Checkout') {
                        steps {echo 'Step: Checkout code from repository'}
            
            """;

    private static final String SuccessBuilt = "FPipelineProject";
    private static final String Disabled = "APipelineProject";
    private static final String FailedBuilt = "ZPipelineProject";
    private static final String NotBuilt = "1PipelineProject";
    private static final List<String> PROJECT_NAMES = List.of("FPipelineProject", "APipelineProject", "ZPipelineProject");

    @Test
    public void testVerifyProjectOrderByNameASCByDefault() {
        PROJECT_NAMES.forEach(jobName -> TestUtils.createFreestyleProject(getDriver(), jobName));
        final List<String> expectedList = PROJECT_NAMES.stream().sorted(Comparator.naturalOrder()).toList();

        List<String> projectNameList = new HomePage(getDriver())
                .getItemList();

        Assert.assertEquals(projectNameList.size(), 3);
        Assert.assertEquals(projectNameList, expectedList);
    }

    @Test(dependsOnMethods = "testVerifyDisplayIconDownArrowNextToNameByDefault")
    public void testVerifyProjectOrderByNameDesc() {
        final List<String> expectedList = PROJECT_NAMES.stream().sorted(Comparator.reverseOrder()).toList();

        List<String> actualList = new HomePage(getDriver())
                .clickNameTableHeaderChangeOrder()
                .getItemList();

        Assert.assertEquals(actualList, expectedList);
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

        testPreparationCreateNotBuiltProject(NotBuilt);
        testPreparationCreateDisableProject(Disabled);
        testPreparationCreateSuccessBuiltProject(SuccessBuilt);
        testPreparationCreateFailedBuiltProject(FailedBuilt);

        List<String> projectNameList = new HomePage(getDriver())
                .clickStatusTableHeaderChangeOrder()
                .getItemList();

        Assert.assertEquals(projectNameList.size(), 4);
        Assert.assertEquals(projectNameList, List.of(NotBuilt, Disabled, SuccessBuilt, FailedBuilt));
    }

    private void testPreparationCreateNotBuiltProject(String projectName) {
        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage();
    }

    private void testPreparationCreateDisableProject(String projectName) {
        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .clickToggleToDisableOrEnableProject()
                .clickSaveButton()
                .gotoHomePage();
    }

    private void testPreparationCreateSuccessBuiltProject(String projectName) {
        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .addScriptToPipeline(validPipelineScriptFile)
                .clickSaveButton()
                .clickOnBuildNowItemOnSidePanelAndWait()
                .gotoHomePage();

    }

    private void testPreparationCreateFailedBuiltProject(String projectName) {
        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .addScriptToPipeline(invalidPipelineScriptFile)
                .clickSaveButton()
                .clickOnBuildNowItemOnSidePanelAndWait()
                .gotoHomePage();
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

}
