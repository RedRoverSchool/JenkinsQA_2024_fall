package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class SearchBox2Test extends BaseTest {

    private static final String SEARCH_TEXT = "g";
    private static final String SEARCH_RESULT = "manage";
    private static final String UNVALID_SEARCH_TEXT = "444";
    private static final By SEARCH_BOX = By.id("search-box");

    private WebElement getSearchBox() {
        return getDriver().findElement(SEARCH_BOX);
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