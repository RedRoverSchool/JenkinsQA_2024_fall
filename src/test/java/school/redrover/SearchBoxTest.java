package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

import java.util.List;

@Epic("14 Header")
@Ignore
public class SearchBoxTest extends BaseTest {

    private static final String ITEM_NAME = "Item Name";
    private static final String SEARCH_TEXT = "g";
    private static final String SEARCH_RESULT = "manage";

    @Test
    @Story("US_14.003 Questions box")
    @Description("TC_14.003.01 Click on the question in search box")
    public void testVerifyTitleSearchBoxLink() {
        String title = new HomePage(getDriver())
                .getHeader()
                .gotoSearchBox()
                .getTitle();

        Allure.step("Expected result: SearchBox title is checking within this action");
        Assert.assertEquals(title, "Command Palette");
    }

    @Test
    @Story("US_14.002 Search box")
    @Description("(NO TC) TC_14.002.01 Display and type in search field")
    public void testBuiltInSearch() {
        String result = new HomePage(getDriver())
                .getHeader()
                .enterSearchText("built")
                .pressEnter()
                .getSearchResult();

        Allure.step("Expected result: Search action with 'built' is performed with 'Built-In Node' result");
        Assert.assertEquals(result, "Built-In Node");
    }

    @Test
    @Story("US_14.002 Search box")
    @Description("(NO TC) 14.002.04 Verify suggestions based on search in field")
    public void testSuggestionList() {
        String result = new HomePage(getDriver())
                .getHeader()
                .enterSearchText("bu")
                .selectFirstSuggestion()
                .pressEnter()
                .getSearchResult();

        Allure.step("Expected result: Search result is displayed according to typed staff in the search field");
        Assert.assertEquals(result, "Built-In Node");
    }

    @Test
    @Story("US_14.002 Search box")
    @Description("TC_14.002.02 Redirect based on search results")
    public void testManageJenkinsPageSearch() {
        String title = new HomePage(getDriver())
                .getHeader()
                .enterSearchText(SEARCH_RESULT)
                .pressEnter()
                .getTitle();

        Allure.step("Expected result: 'Manage Jenkins' is displayed due to this search");
        Assert.assertEquals(title, "Manage Jenkins");
    }

    @Test
    @Story("US_14.002 Search box")
    @Description("(NO TC) 14.002.03 No results are found")
    public void testNoResultSearch() {
        String result = new HomePage(getDriver())
                .getHeader()
                .enterSearchText("TestSearch")
                .pressEnter()
                .getTitle();

        Allure.step("Expected result: No specified result exists for this search so verifying an empty result");
        Assert.assertEquals(result, "Search for 'TestSearch'");
    }

    @Test
    @Story("US_14.002 Search box")
    @Description("(NO TC) 14.002.05 Go to Manage Jenkins through 'bell'")
    public void testGoToManageJenkinsThroughBell() {
        String title = new HomePage(getDriver())
                .getHeader()
                .clickBell()
                .clickManageJenkins()
                .getTitle();

        Allure.step("Expected result: 'Manage Jenkins' is reached though the 'bell'");
        Assert.assertEquals(title, "Manage Jenkins");
    }

    @Test
    @Story("US_14.002 Search box")
    @Description("(NO TC) 14.002.06 Search configure page")
    public void testConfigurationPageSearch() {
        String url = new HomePage(getDriver())
                .getHeader()
                .enterSearchText("config")
                .pressEnter()
                .getCurrentUrl();

        Allure.step("Expected result: The result of searching configure page is displayed");
        Assert.assertEquals(url, ProjectUtils.getUrl() + "configure");
    }

    @Test
    @Story("US_14.002 Search box")
    @Description("(NO TC) 14.002.07 Search for logs")
    public void testLogPageSearch() {
        String url = new HomePage(getDriver())
                .getHeader()
                .enterSearchText("log")
                .pressEnter()
                .getCurrentUrl();

        Allure.step("Expected result: Log page is displayed");
        Assert.assertEquals(url, ProjectUtils.getUrl() + "log/");
    }

    @Test
    @Story("US_14.002 Search box")
    @Description("(NO TC) 14.002.08 Search for some item")
    public void testSearchCreatedInstance() {
        String title = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ITEM_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .getHeader()
                .enterSearchText(ITEM_NAME)
                .pressEnter()
                .getTitle();

        Allure.step("Expected result: Some item has been created so it's checked of possibility to search for it");
        Assert.assertEquals(title, ITEM_NAME);
    }

    @Test
    @Story("US_14.002 Search box")
    @Description("(NO TC) 14.002.09 Search with one symbol is contained in pages")
    public void testResultOfSearch() {
        List<String> suggestionList = new HomePage(getDriver())
                .getHeader()
                .enterSearchText(SEARCH_TEXT)
                .getListSuggestion();

        Allure.step("Expected result: One symbol search is performed so it's got some list of results");
        Assert.assertEquals(suggestionList.size(), 4);
        Assert.assertEquals(suggestionList.get(2), SEARCH_RESULT);
    }

    @Test(dependsOnMethods = "testResultOfSearch")
    @Story("US_14.002 Search box")
    @Description("(NO TC) 14.002.10 Go to Manage Jenkins from search result page")
    public void testSearchManageJenkinsPageFromRedirectedSearchResult() {
        String title = new HomePage(getDriver())
                .getHeader()
                .enterSearchText(SEARCH_TEXT)
                .clickOnSuggestedManage()
                .getHeader()
                .pressEnter()
                .getTitle();

        Allure.step("Expected result: 'Manage jenkins' page is searching from redirected search result");
        Assert.assertEquals(title, "Manage Jenkins");
    }

    @Test(dependsOnMethods = "testSearchManageJenkinsPageFromRedirectedSearchResult")
    @Story("US_14.002 Search box")
    @Description("(NO TC) 14.002.11 No results are found from search result page")
    public void testEmptySearchField() {
        String url = new HomePage(getDriver())
                .getHeader()
                .enterSearchText("")
                .pressEnter()
                .getCurrentUrl();

        Allure.step("Expected result: Empty search is performed from search result page with no matching");
        Assert.assertEquals(url, ProjectUtils.getUrl() + "computer/(built-in)/");
    }
}