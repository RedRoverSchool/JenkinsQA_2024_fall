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
                .getHeader()
                .gotoSearchBox()
                .getTitle();

        Assert.assertEquals(title, "Search Box");
    }

    @Test
    public void testSearch() {
        String result = new HomePage(getDriver())
                .getHeader()
                .enterSearch("built")
                .getHeader()
                .enter()
                .getResult();

        Assert.assertEquals(result, "Built-In Node");
    }

    @Test
    public void testSuggestionList() {
        String result = new HomePage(getDriver())
                .getHeader()
                .enterSearch("bu")
                .getHeader()
                .getSuggestion()
                .getHeader()
                .enter()
                .getResult();

        Assert.assertEquals(result, "Built-In Node");
    }

    @Test
    public void testSearchManage() {
        String title = new HomePage(getDriver())
                .getHeader()
                .resultManage(SEARCH_RESULT)
                .getTitle();

        Assert.assertEquals(title, "Manage Jenkins");
    }

    @Test
    public void testFindSearchTest() {
        String result = new HomePage(getDriver())
                .getHeader()
                .enterSearch("TestSearch")
                .getHeader()
                .enter()
                .getTitle();

        Assert.assertEquals(result, "Search for 'TestSearch'");
    }

    @Test
    public void testClickButtonTest() {
        String title = new HomePage(getDriver())
                .getHeader()
                .clickBell()
                .getHeader()
                .clickLinkWithPopup()
                .getTitle();

        Assert.assertEquals(title, "Manage Jenkins");
    }

    @Test
    public void testSearchField() {
        String url = new HomePage(getDriver())
                .getHeader()
                .enterSearch("config")
                .getHeader()
                .enter()
                .getCurrentUrl();

        Assert.assertEquals(url, ProjectUtils.getUrl() + "configure");
    }

    @Test
    public void testInputField() {
        String text = new HomePage(getDriver())
                .getHeader()
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
                .getHeader()
                .gotoHomePage()
                .getHeader()
                .enterSearch(ITEM_NAME)
                .getHeader()
                .enter()
                .getTitle();

        Assert.assertEquals(title, ITEM_NAME);
    }

    @Test
    public void testSearchNotExistingInstance() {
        String error = new HomePage(getDriver())
                .getHeader()
                .enterSearch("hjk")
                .getHeader()
                .enter()
                .getMessageError();

        Assert.assertEquals(error, "Nothing seems to match.");
    }

    @Test
    public void testResultOfSearch() {
        List<String> suggestionList = new HomePage(getDriver())
                .getHeader()
                .enterSearch(SEARCH_TEXT)
                .getHeader()
                .getListSuggestion();

        Assert.assertEquals(suggestionList.size(), 4);
        Assert.assertEquals(suggestionList.get(2), SEARCH_RESULT);
    }

    @Test
    public void testRedirectToResult() {
        String title = new HomePage(getDriver())
                .getHeader()
                .enterSearch(SEARCH_TEXT)
                .getHeader()
                .getResultManage()
                .getTitle();

        Assert.assertEquals(title, "Manage Jenkins");
    }

    @Test
    public void testEmptySearchField() {
        String errorMessage = new HomePage(getDriver())
                .getHeader()
                .enterSearch(INVALID_SEARCH_TEXT)
                .getHeader()
                .enter()
                .getMessageError();

        Assert.assertEquals(errorMessage, "Nothing seems to match.");
    }
}