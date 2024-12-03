package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

import java.util.List;

public class SearchBoxTest extends BaseTest {

    private final static By SEARCH_FIELD = By.xpath("//input[@role = 'searchbox']");
    private static final String ITEM_NAME = "Item Name";
    private static final String SEARCH_TEXT = "g";
    private static final String SEARCH_RESULT = "manage";
    private static final String UNVALID_SEARCH_TEXT = "444";
    private static final By SEARCH_BOX = By.id("search-box");

    private WebElement getSearchBox() {
        return getDriver().findElement(SEARCH_BOX);
    }

    @Test
    public void testVerifyTitleSearchBoxLink() {
        getDriver().findElement(By.xpath("//a[contains(@class, 'main-search')]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Search Box");
    }

    @Test
    public void testSearch() {
        final WebElement searchField = getDriver().findElement(SEARCH_FIELD);
        searchField.click();
        searchField.sendKeys("built", Keys.ENTER);

        Assert.assertEquals(getDriver().findElement(By.xpath("//li[@id = 'item_Built-In Node']/a")).getText()
                , "Built-In Node");
    }

    @Test
    public void testSuggestionList() {
        WebElement searchField = getDriver().findElement(SEARCH_FIELD);

        searchField.click();
        searchField.sendKeys("bu");

        WebElement itemOfSuggestionList = getDriver().findElement(By.xpath("//div[@class = 'yui-ac-bd']/ul/li[1]"));

        getWait2().until(ExpectedConditions.elementToBeClickable(itemOfSuggestionList));

        new Actions(getDriver())
                .moveToElement(itemOfSuggestionList).click().perform();

        searchField.sendKeys(Keys.ENTER);

        Assert.assertEquals(getDriver().findElement(By.xpath("//li[@id = 'item_Built-In Node']/a")).getText()
                , "Built-In Node");
    }

    @Test
    public void testSearchManage() {
        WebElement search = getDriver().findElement(By.xpath("//input[@id='search-box']"));
        search.click();
        search.sendKeys("manage", Keys.ENTER);

        String name = getDriver().findElement(By.xpath("//*[@id='main-panel']/div/div[1]/h1")).getText();

        Assert.assertEquals(name, "Manage Jenkins");
    }

    @Test
    public void testFindSearchTest() {
        Actions actions = new Actions(getDriver());

        WebElement searchField = getDriver()
                .findElement(By.cssSelector("#search-box"));

        actions.moveToElement(searchField)
                .click()
                .sendKeys("TestSearch")
                .sendKeys(Keys.ENTER)
                .perform();

        String actual = getDriver()
                .findElement(By.cssSelector("div[id='main-panel'] h1"))
                .getText();

        Assert.assertEquals(actual, "Search for 'TestSearch'");
    }

    @Test
    public void testClickButtonTest() {
        WebElement manageJenkinsLink = getDriver().findElement(By.cssSelector("a[href='/manage']"));

        getDriver()
                .findElement(By.cssSelector("a[id='visible-am-button'] svg"))
                .click();
        getWait5().until(d -> manageJenkinsLink.isDisplayed());

        manageJenkinsLink.click();
    }

    @Test
    public void testSearchField() {
        WebElement searchField = getDriver().findElement(By.xpath("//input[@role='searchbox']"));
        searchField.click();
        searchField.sendKeys("config" + Keys.ENTER);

        Assert.assertEquals(getDriver().getCurrentUrl(),
                ProjectUtils.getUrl() + "configure", "Current URL does not meet requirements");
    }

    @Test
    public void testInputField() {
        WebElement searchField = getDriver().findElement(By.xpath("//input[@role='searchbox']"));
        searchField.click();
        searchField.sendKeys("log" + Keys.ENTER);

        String logPage = getDriver().findElement(By.xpath("//div[@id='main-panel']")).getText().toLowerCase();

        Assert.assertTrue(logPage.contains("log"), "Current page does not contain 'log' text");
    }

    @Test
    public void testSearchCreatedInstance() {
        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();

        getDriver().findElement(
                By.xpath("//div/button[@class='jenkins-button jenkins-submit-button jenkins-button--primary ']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        WebElement searchBox = getDriver().findElement(By.id("search-box"));
        searchBox.click();
        searchBox.sendKeys(ITEM_NAME + Keys.ENTER);

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div/h1")).getText(),
                ITEM_NAME);
    }

    @Test
    public void testSearchNotExistingInstance() {
        WebElement searchBox = getDriver().findElement(By.id("search-box"));
        searchBox.click();
        searchBox.sendKeys("hjk" + Keys.ENTER);

        Assert.assertEquals(
                getDriver().findElement(By.className("error")).getText(),
                "Nothing seems to match.");
    }

    @Test
    public void testResultOfSearch() {
        getSearchBox().click();
        getSearchBox().sendKeys(SEARCH_TEXT);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='yui-ac-bd']/ul/li")));

        List<WebElement> searchList = getDriver().findElements(
                By.xpath("//div[@class='yui-ac-bd']/ul/li[not(contains(@style, 'display: none'))]"));
        List<String> resultsList = searchList.stream().map(WebElement::getText).toList();

        Assert.assertEquals(resultsList.size(), 4);
        Assert.assertEquals(resultsList.get(2), SEARCH_RESULT);
    }

    @Test
    public void testRedirectToResult() {
        getSearchBox().click();
        getSearchBox().sendKeys(SEARCH_TEXT);

        getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(By.className("yui-ac-bd"))));
        getDriver().findElement(By.xpath("//div[@class = 'yui-ac-bd']/ul/li[3]")).click();
        getSearchBox().sendKeys(Keys.ENTER);

        Assert.assertEquals(
                getDriver().findElement(By.className("jenkins-app-bar__content")).getText(),
                "Manage Jenkins");
    }

    @Test
    public void testEmptySearchField() {
        getSearchBox().click();
        getSearchBox().sendKeys(UNVALID_SEARCH_TEXT + Keys.ENTER);

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@class='error']")).getText(),
                "Nothing seems to match.");
    }
}