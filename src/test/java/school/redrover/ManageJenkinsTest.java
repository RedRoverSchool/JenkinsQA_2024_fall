package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.page.manage.AppearancePage;
import school.redrover.page.home.HomePage;
import school.redrover.page.systemConfiguration.SystemPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;
import school.redrover.runner.TestUtils;

import java.util.List;

public class ManageJenkinsTest extends BaseTest {

    private static final String MY_NODE_NAME = "My name of node";
    private static final String NODE_NAME = "NewNodeName";
    private static final String FULL_USER_NAME = "Ivan Petrov";

    @DataProvider
    public Object[][] provideSystemConfigurationItems() {
        return new Object[][]{
                {"System"}, {"Tools"}, {"Plugins"}, {"Nodes"}, {"Clouds"}, {"Appearance"}
        };
    }

    @Test
    public void testCheckTitle() {
        String title = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .getTitle();

        Assert.assertTrue(title.startsWith("Users"), title);
    }

    @Test
    public void testImpossiblyToCreateNewUserWithEmptyFields() {
        String validationMessage = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .clickCreateUser()
                .clickCreateUserButton()
                .getValidationMessage();

        Assert.assertEquals(validationMessage, "\"\" is prohibited as a username for security reasons.");
    }

    @Test
    public void testCreateNewUser() {
        List<String> userList = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .clickCreateUser()
                .fillFormByValidDataToCreateUser(FULL_USER_NAME)
                .getCreatedUserName();

        Assert.assertEquals(userList.size(), 2);
        Assert.assertEquals(userList.get(1), FULL_USER_NAME);
    }

    @Test
    public void testAddDescriptionForUser() {
        String userDescription = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .createNewUser(FULL_USER_NAME)
                .clickToConfigureUser(FULL_USER_NAME)
                .addUserDescription()
                .getUserDescription();

        Assert.assertEquals(userDescription, "User Description");
    }

    @Test
    public void testAddTimeZoneForUser() {
        String userTimeZone = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .createNewUser(FULL_USER_NAME)
                .clickToConfigureUser(FULL_USER_NAME)
                .addUserTimeZone()
                .clickConfigureUserSidebar()
                .getUserTimeZone();

        Assert.assertEquals(userTimeZone, "Etc/GMT+2");
    }

    @Test
    public void testDeleteUserViaDeleteButtonOnUsersPage() {
        List<String> userList = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .createNewUser(FULL_USER_NAME)
                .deleteUserFromUsersPage()
                .getCreatedUserName();

        Assert.assertEquals(userList.size(), 1);
        Assert.assertEquals(userList.get(0), "admin");
    }

    @Test
    public void testDeleteUserViaConfigureUserPage() {
        List<String> userList = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .createNewUser(FULL_USER_NAME)
                .clickToConfigureUser(FULL_USER_NAME)
                .deleteUserFromConfigureUserPage()
                .openManageJenkinsPage()
                .openUsersPage()
                .getCreatedUserName();

        Assert.assertEquals(userList.size(), 1);
        Assert.assertEquals(userList.get(0), "admin");
    }

    @Test
    public void testRedirectionToManage() {
        HomePage homePage = new HomePage(getDriver());
        String urlBeforeRedirection = homePage
                .getCurrentUrl();

        String currentURL = homePage
                .openManageJenkinsPage()
                .getCurrentUrl();

        Assert.assertEquals(currentURL, urlBeforeRedirection + "manage/");
    }

    @Test
    public void testManageJenkinsSections() {
        List<String> mainSections = new HomePage(getDriver())
                .openManageJenkinsPage()
                .getAllSections();

        Assert.assertEquals(mainSections.size(), 5);
        Assert.assertEquals(mainSections, List.of("System Configuration", "Security", "Status Information",
                "Troubleshooting", "Tools and Actions"));
    }

    @Test
    public void testManageJenkinsSystemConfigurationItems() {
        List<String> itemsNames = new HomePage(getDriver())
                .openManageJenkinsPage()
                .getSystemConfigurationItems();

        Assert.assertEquals(itemsNames, List.of("System", "Tools", "Plugins",
                "Nodes", "Clouds", "Appearance"));
    }

    @Test
    public void testManageJenkinsSystemConfigureBreadcrumbs() {
        String breadCrumbs = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickSystemSection()
                .getBreadCrumbs();

        Assert.assertEquals(breadCrumbs, "Dashboard\nManage Jenkins\nSystem");
    }

    @Test
    public void testManageJenkinsTabSearch() {
        String searchField = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickOnSearchField()
                .getAttribute("placeholder");

        Assert.assertEquals(searchField, "Search settings");
    }

    @Test(dataProvider = "provideSystemConfigurationItems")
    public void testSearchSystemConfigurationAllItemsAreFound(String systemConfigurationItem) {

        List<String> searchDropdown = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickOnSearchField()
                .typeSearchInputField(systemConfigurationItem)
                .getSearchResults();

        Assert.assertEquals(searchDropdown.get(0), systemConfigurationItem,
                "Item '" + systemConfigurationItem + "' not found in dropdown or not in the expected position.");
    }

    @Test
    public void testSearchSettingsWithNoResult() {
        String resultLabelText = new HomePage(getDriver())
                .openManageJenkinsPage()
                .typeSearchInputField("1333")
                .getNoResultLabelText();

        Assert.assertEquals(resultLabelText, "No results");
    }

    @Test
    public void testRedirectToFirstItemAfterPressEnter() {

        String currentUrl = new HomePage(getDriver())
                .openManageJenkinsPage()
                .pressEnterAfterInput("c")
                .getCurrentUrl();

        Assert.assertEquals(currentUrl, ProjectUtils.getUrl() + "manage/cloud/");
    }

    @Test
    public void testSearchSettingTooltip() {

        boolean shortcutTooltipIsDisplayed = new HomePage(getDriver())
                .openManageJenkinsPage()
                .isHiddenShortcutTooltipDisplayed();

        Assert.assertTrue(shortcutTooltipIsDisplayed);
    }

    @Test
    public void testShortcutMovesFocusToSearchSettingsField() {
        final String expectedText = "123456";

        String inputText = new HomePage(getDriver())
                .openManageJenkinsPage()
                .switchFocusToSearchFieldAndTypeText(expectedText)
                .getInputText();

        Assert.assertEquals(inputText, expectedText);
    }

    @Test
    public void testSearchSettingsFieldIsDisplayed() {
        boolean searchSettingFieldIsDisplayed = new HomePage(getDriver())
                .openManageJenkinsPage()
                .isSearchSettingFieldDisplayed();

        Assert.assertTrue(searchSettingFieldIsDisplayed);
    }

    @Test
    public void testSearchResultsList() {
        List<String> searchResults = new HomePage(getDriver())
                .openManageJenkinsPage()
                .typeSearchInputField("sy")
                .getSearchResults();

        Assert.assertEquals(searchResults, List.of("System", "System Information", "System Log"));
    }

    @Test
    public void testRedirectToSelectedItemFromResultList() {
        String currentUrl = new HomePage(getDriver())
                .openManageJenkinsPage()
                .typeSearchInputField("cr")
                .clickConfigureCredentialsItem()
                .getCurrentUrl();

        Assert.assertTrue(currentUrl.endsWith("/manage/configureCredentials/"));
    }

    @Test
    public void testSystemPageNavigation() {
        String currentUrl = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickSystemSection()
                .getCurrentUrl();

        String pageTitle = new SystemPage(getDriver())
                .getTitle();

        Assert.assertTrue(currentUrl.contains("manage/configure"), "Current URL does not meet the requirement.");
        Assert.assertEquals(pageTitle, "System");
    }

    @Test
    public void testVisitPageThreadDumps() {
        String pageTitle = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openSystemInformationPage()
                .openThreadDumpsTab()
                .clickThisPageLink()
                .getTitle();

        Assert.assertEquals(pageTitle, "Thread Dump");
    }

    @Test
    public void testCreateNewAgent() {

        List<String> nodeItemList = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickNodesButton()
                .clickButtonNewNode()
                .enterNodeName(NODE_NAME)
                .selectPermanentAgent()
                .clickButtonCreate()
                .clickButtonSave()
                .getNodeList();

        Assert.assertListContainsObject(nodeItemList, NODE_NAME, "List not contain new node");
    }

    @Test
    public void testCreationAndDisplayNewNameOnNodesPage() {
        List<String> itemNoteList = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickNodesButton()
                .clickButtonNewNode()
                .enterNodeName(MY_NODE_NAME)
                .selectPermanentAgent()
                .clickButtonCreate()
                .clickButtonSave()
                .getNodeList();

        Assert.assertListContainsObject(itemNoteList, MY_NODE_NAME, MY_NODE_NAME);
    }

    @Test()
    public void testDeleteAgent() {
        TestUtils.createNode(getDriver(),NODE_NAME);

        List<String> nodeNameAfterList = new HomePage(getDriver())
                .openNodeFromBuildExecutorStatusBlock(NODE_NAME)
                .clickDeleteButtonSidebarAndConfirm()
                .gotoHomePage()
                .getNodeNameList();

        Assert.assertEquals(nodeNameAfterList.size(), 0);
    }

    @Test
    public void testThemesOnPage() {
        List<WebElement> themeList = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickAppearanceButton()
                .getThemeList();

        Assert.assertEquals(themeList.size(), 3);
    }

    @Test
    public void testPickDarkTheme() {
        String initialColorTheme = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickAppearanceButton()
                .getColorBackground();

        String changedColor = new AppearancePage(getDriver())
                .clickSelectDarkThemes()
                .getColorBackground();

        Assert.assertEquals(initialColorTheme, changedColor);
    }

    @Test
    public void testThemesDark() {
        String attributeData = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickAppearanceButton()
                .clickSelectDarkThemes()
                .clickCheckboxDifferentTheme()
                .clickApplyButton()
                .getAttributeData();

        Assert.assertEquals(attributeData, "dark");
    }

    @Test
    public void testThemesDefault() {
        String attributeData = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickAppearanceButton()
                .clickSelectDefaultThemes()
                .clickCheckboxDifferentTheme()
                .clickApplyButton()
                .getAttributeData();

        Assert.assertEquals(attributeData, "none");
    }

    @Test
    public void testThemesSystem() {
        String attributeData = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickAppearanceButton()
                .clickSelectSystemThemes()
                .clickCheckboxDifferentTheme()
                .clickApplyButton()
                .getAttributeData();

        Assert.assertTrue(attributeData.contains("system"));
    }
}