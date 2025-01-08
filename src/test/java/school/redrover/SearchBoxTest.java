package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

import java.util.List;

public class SearchBoxTest extends BaseTest {

    private static final String ITEM_NAME = "Item Name";
    private static final String SEARCH_TEXT = "g";
    private static final String SEARCH_RESULT = "manage";
    private static final String INVALID_SEARCH_TEXT = "444";

    @Test
    public void testVerifyTitleSearchBoxLink() {
        String title = new HomePage(getDriver())
                .gotoSearchBox()
                .getTitle();

        Assert.assertEquals(title, "Search Box1");
    }

    @Test
    public void testSearch() {
        String result = new HomePage(getDriver())
                .enterSearch("built")
                .enter()
                .getResult();

        Assert.assertEquals(result, "Built-In Node");
    }

    @Test
    public void testSuggestionList() {
        String result = new HomePage(getDriver())
                .enterSearch("bu")
                .getSuggestion()
                .enter()
                .getResult();

        Assert.assertEquals(result, "Built-In Node");
    }

    @Test
    public void testSearchManage() {
        String title = new HomePage(getDriver())
                .resultManage(SEARCH_RESULT)
                .getTitle();

        Assert.assertEquals(title, "Manage Jenkins");
    }

    @Test
    public void testFindSearchTest() {
        String result = new HomePage(getDriver())
                .enterSearch("TestSearch")
                .enter()
                .getTitle();

        Assert.assertEquals(result, "Search for 'TestSearch'");
    }

    @Test
    public void testClickButtonTest() {
        String title = new HomePage(getDriver())
                .clickBell()
                .clickLinkWithPopup()
                .getTitle();

        Assert.assertEquals(title, "Manage Jenkins");
    }

    @Test
    public void testSearchField() {
        String url = new HomePage(getDriver())
                .enterSearch("config")
                .enter()
                .getCurrentUrl();

        Assert.assertEquals(url, ProjectUtils.getUrl() + "configure");
    }

    @Test
    public void testInputField() {
        String text = new HomePage(getDriver())
                .enterGotoLogPage("log")
                .getResultSearch();

        Assert.assertTrue(text.toLowerCase().contains("log"));
    }

    @Test
    public void testSearchCreatedInstance() {
        String title = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ITEM_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .enterSearch(ITEM_NAME)
                .enter()
                .getTitle();

        Assert.assertEquals(title, ITEM_NAME);
    }

    @Test
    public void testResultOfSearch() {
        List<String> suggestionList = new HomePage(getDriver())
                .enterSearch(SEARCH_TEXT)
                .getListSuggestion();

        Assert.assertEquals(suggestionList.size(), 4);
        Assert.assertEquals(suggestionList.get(2), SEARCH_RESULT);
    }

    @Test(dependsOnMethods = "testResultOfSearch")
    public void testRedirectToResult() {
        String title = new HomePage(getDriver())
                .enterSearch(SEARCH_TEXT)
                .getResultManage()
                .getTitle();

        Assert.assertEquals(title, "Manage Jenkins");
    }

    @Test(dependsOnMethods = "testRedirectToResult")
    public void testEmptySearchField() {
        String errorMessage = new HomePage(getDriver())
                .enterSearch(INVALID_SEARCH_TEXT)
                .enter()
                .getMessageError();

        Assert.assertEquals(errorMessage, "Nothing seems to match.");
    }
}