package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.testdata.TestDataProvider;
import school.redrover.page.home.HomePage;
import school.redrover.page.manage.AppearancePage;
import school.redrover.page.systemConfiguration.PluginsPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

import java.util.List;

@Epic("09 Manage Jenkins")
public class ManageJenkinsTest extends BaseTest {

    @Test
    @Story("US_09.003 View 'Manage Jenkins' page > Redirection")
    @Description("TC_09.003.02 Redirect to 'Manage Jenkins' page")
    public void testRedirectionToManage() {
        HomePage homePage = new HomePage(getDriver());
        String urlBeforeRedirection = homePage
                .getCurrentUrl();

        String currentURL = homePage
                .openManageJenkinsPage()
                .getCurrentUrl();
        Allure.step(" \uD83D\uDCCC Expected result: Redirection to 'Manage Jenkins' page");
        Assert.assertEquals(currentURL, urlBeforeRedirection + "manage/");
    }

    @Test
    @Story("US_09.002 View 'Manage Jenkins' page > Visibility and clickability")
    @Description("TC_09.002.01 Check the five manage jenkins sections are displayed")
    public void testManageJenkinsSections() {
        List<String> mainSections = new HomePage(getDriver())
                .openManageJenkinsPage()
                .getAllSections();

        Allure.step(" \uD83D\uDCCC Expected result: The five manage jenkins sections are displayed", () -> {
            Assert.assertEquals(mainSections.size(), 5);
            Assert.assertEquals(mainSections, List.of("System Configuration", "Security", "Status Information",
                    "Troubleshooting", "Tools and Actions"));
        });
    }

    @Test
    @Story("US_09.002 View 'Manage Jenkins' page > Visibility and clickability")
    @Description("TC_09.002.02 Check items of System Configuration section")
    public void testManageJenkinsSystemConfigurationItems() {
        List<String> itemsNames = new HomePage(getDriver())
                .openManageJenkinsPage()
                .getSystemConfigurationItems();

        Allure.step(" \uD83D\uDCCC Expected result: The items of System Configuration section are displayed");
        Assert.assertEquals(itemsNames, List.of("System", "Tools", "Plugins",
                "Nodes", "Clouds", "Appearance"));
    }

    @Test
    @Story("US_09.003 View 'Manage Jenkins' page > Redirection")
    @Description("TC_09.003.01 Manage Jenkins System Configure Breadcrumbs")
    public void testManageJenkinsSystemConfigureBreadcrumbs() {
        String breadCrumbs = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickSystemSection()
                .getBreadCrumbs();

        Allure.step(" \uD83D\uDCCC Expected result: Manage Jenkins System Configure Breadcrumbs");
        Assert.assertEquals(breadCrumbs, "Dashboard\nManage Jenkins\nSystem");
    }

    @Test
    @Story("US_09.001 Search settings")
    @Description("TC_09.001.08 Check search field placeholder")
    public void testManageJenkinsTabSearch() {
        String searchField = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickOnSearchField()
                .getAttribute("placeholder");

        Allure.step(" \uD83D\uDCCC Expected result: Search field placeholder");
        Assert.assertEquals(searchField, "Search settings");
    }

    @Test(dataProvider = "provideSystemConfigurationItems", dataProviderClass = TestDataProvider.class)
    @Story("US_09.001 Search settings")
    @Description("TC_09.001.07 Check search system configuration items")
    public void testSearchSystemConfigurationAllItemsAreFound(String systemConfigurationItem) {

        List<String> searchDropdown = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickOnSearchField()
                .typeSearchInputField(systemConfigurationItem)
                .getSearchResults();

        Allure.step(" \uD83D\uDCCC Expected result: Search system configuration items");
        Assert.assertEquals(searchDropdown.get(0), systemConfigurationItem,
                "Item '" + systemConfigurationItem + "' not found in dropdown or not in the expected position.");
    }

    @Test
    @Story("US_09.001 Search settings")
    @Description("TC_09.001.01 Check no result message")
    public void testSearchSettingsWithNoResult() {
        String resultLabelText = new HomePage(getDriver())
                .openManageJenkinsPage()
                .typeSearchInputField("1333")
                .getNoResultLabelText();

        Allure.step(" \uD83D\uDCCC Expected result: No result message");
        Assert.assertEquals(resultLabelText, "No results");
    }

    @Test
    @Story("US_09.001 Search settings")
    @Description("TC_09.001.02 Check redirection after press enter")
    public void testRedirectToFirstItemAfterPressEnter() {

        String currentUrl = new HomePage(getDriver())
                .openManageJenkinsPage()
                .pressEnterAfterInput("c")
                .getCurrentUrl();

        Allure.step(" \uD83D\uDCCC Expected result: Redirect to first item after press enter");
        Assert.assertEquals(currentUrl, ProjectUtils.getUrl() + "manage/cloud/");
    }

    @Test
    @Story("US_09.001 Search settings")
    @Description("TC_09.001.03 Check Search Setting tooltip is displayed")
    public void testSearchSettingTooltip() {

        boolean shortcutTooltipIsDisplayed = new HomePage(getDriver())
                .openManageJenkinsPage()
                .isHiddenShortcutTooltipDisplayed();

        Allure.step(" \uD83D\uDCCC Expected result: Search Setting tooltip is displayed");
        Assert.assertTrue(shortcutTooltipIsDisplayed);
    }

    @Test
    @Story("US_09.001 Search settings")
    @Description("TC_09.001.04 Check shortcut switch focus to search setting field")
    public void testShortcutMovesFocusToSearchSettingsField() {
        final String expectedText = "123456";

        String inputText = new HomePage(getDriver())
                .openManageJenkinsPage()
                .switchFocusToSearchFieldAndTypeText(expectedText)
                .getInputText();

        Allure.step(" \uD83D\uDCCC Expected result: Shortcut switch focus to search setting field");
        Assert.assertEquals(inputText, expectedText);
    }

    @Test
    @Story("US_09.001 Search settings")
    @Description("TC_09.001.05 Check visibility of search settings field")
    public void testSearchSettingsFieldIsDisplayed() {
        boolean searchSettingFieldIsDisplayed = new HomePage(getDriver())
                .openManageJenkinsPage()
                .isSearchSettingFieldDisplayed();

        Allure.step(" \uD83D\uDCCC Expected result: Search settings field is displayed");
        Assert.assertTrue(searchSettingFieldIsDisplayed);
    }

    @Test
    @Story("US_09.001 Search settings")
    @Description("TC_09.001.06 Search result list")
    public void testSearchResultsList() {
        List<String> searchResults = new HomePage(getDriver())
                .openManageJenkinsPage()
                .typeSearchInputField("sy")
                .getSearchResults();

        Allure.step(" \uD83D\uDCCC Expected result: Search result list");
        Assert.assertEquals(searchResults, List.of("System", "System Information", "System Log"));
    }

    @Test
    @Story("US_09.001 Search settings")
    @Description("TC_09.001.07 Redirection to selected item")
    public void testRedirectToSelectedItemFromResultList() {
        String currentUrl = new HomePage(getDriver())
                .openManageJenkinsPage()
                .typeSearchInputField("cr")
                .clickConfigureCredentialsItem()
                .getCurrentUrl();

        Allure.step(" \uD83D\uDCCC Expected result: Redirection to selected item");
        Assert.assertTrue(currentUrl.endsWith("/manage/configureCredentials/"));
    }

    @Test
    public void testVisitPageThreadDumps() {
        String pageTitle = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openSystemInformationPage()
                .openThreadDumpsTab()
                .clickThisPageLink()
                .getTitle();

        Allure.step(" \uD83D\uDCCC Expected result: Visit page Thread Dumps");
        Assert.assertEquals(pageTitle, "Thread Dump");
    }

    @Test
    @Story("US_09.005 Settings")
    @Description("TC_09.005.01 Check themes on page")
    public void testThemesOnPage() {
        List<WebElement> themeList = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickAppearanceButton()
                .getThemeList();

        Allure.step(" \uD83D\uDCCC Expected result: Check themes on page");
        Assert.assertEquals(themeList.size(), 3);
    }

    @Test
    @Story("US_09.005 Settings")
    @Description("TC_09.005.02 Check pick dark theme")
    public void testPickDarkTheme() {
        String initialColorTheme = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickAppearanceButton()
                .getColorBackground();

        String changedColor = new AppearancePage(getDriver())
                .clickSelectDarkThemes()
                .getColorBackground();

        Allure.step(" \uD83D\uDCCC Expected result: Pick dark theme");
        Assert.assertEquals(initialColorTheme, changedColor);
    }

    @Test
    @Story("US_09.005 Settings")
    @Description("TC_09.005.03 Check dark theme")
    public void testThemesDark() {
        String attributeData = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickAppearanceButton()
                .clickSelectDarkThemes()
                .clickCheckboxDifferentTheme()
                .clickApplyButton()
                .getAttributeData();

        Allure.step(" \uD83D\uDCCC Expected result: Check dark theme");
        Assert.assertEquals(attributeData, "dark");
    }

    @Test
    @Story("US_09.005 Settings")
    @Description("TC_09.005.04 Check default theme")
    public void testThemesDefault() {
        String attributeData = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickAppearanceButton()
                .clickSelectDefaultThemes()
                .clickCheckboxDifferentTheme()
                .clickApplyButton()
                .getAttributeData();

        Allure.step(" \uD83D\uDCCC Expected result: Check default theme");
        Assert.assertEquals(attributeData, "none");
    }

    @Test
    @Story("US_09.005 Settings")
    @Description("TC_09.005.05 Check system theme")
    public void testThemesSystem() {
        String attributeData = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickAppearanceButton()
                .clickSelectSystemThemes()
                .clickCheckboxDifferentTheme()
                .clickApplyButton()
                .getAttributeData();

        Allure.step(" \uD83D\uDCCC Expected result: Check system theme");
        Assert.assertTrue(attributeData.contains("system"));
    }

    @Test
    @Story("US_09.004 View 'Manage Jenkins' page > Users")
    @Description("TC_09.004.01 Manage Jenkins Users page")
    public void testCheckTitle() {
        String title = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .getTitle();

        Allure.step(" \uD83D\uDCCC Expected result: Open Users page");
        Assert.assertTrue(title.startsWith("Users"), title);
    }

    @Test
    @Story("US_09.006 Plugin")
    @Description("TC_09.006.01 Check number of update plugin")
    public void testNumberOfUpdatePlugin() {

        int indicatorCount = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openPluginsPage()
                .getUpdateCountFromIndicator();

        int countPluginsFromUpdateTable = new PluginsPage(getDriver())
                .getPluginsCountFromUpdateTable();

        Allure.step("Expected result: Check number of update plugin");
        Assert.assertEquals(indicatorCount, countPluginsFromUpdateTable);

    }

    @Test(dependsOnMethods = "testNumberOfUpdatePlugin")
    @Story("US_09.006 Plugin")
    @Description("TC_09.006.02 Check number of all update plugin")
    public void testNumberOfAllUpdatePlugin() {

        int countAvailablePlugins = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openPluginsPage()
                .getCountAvailablePlugins();

        Allure.step("Expected result: Check number of all update plugin");
        Assert.assertEquals(countAvailablePlugins, 50);

    }

    @Test (dependsOnMethods = "testNumberOfAllUpdatePlugin")
    @Story("US_09.006 Plugin")
    @Description("TC_09.006.03 Search plugin via tag")
    public void testSearchPluginViaTag() {

        int countOfPluginsFound = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openPluginsPage()
                .searchInstalledPlugin("Theme Manager")
                .getCountOfPluginsFound();

        Allure.step("Expected result: Search plugin via tag");
        Assert.assertEquals(countOfPluginsFound, 1);

    }
}