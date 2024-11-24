package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.List;

public class SearchBox2Test extends BaseTest {

    private static final String SEARCH_TEXT = "g";
    private static final String SEARCH_RESULT = "manage";
    @Test
    public void testResaltOfSearch() {

        WebElement searchBox = getDriver().findElement(By.id("search-box"));
        searchBox.click();
        searchBox.sendKeys(SEARCH_TEXT);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='yui-ac-bd']/ul/li")));

        List<WebElement> searchList = getDriver().findElements(
                By.xpath("//div[@class='yui-ac-bd']/ul/li[not(contains(@style, 'display: none'))]"));
        List<String> resultsList = searchList.stream().map(WebElement::getText).toList();

        Assert.assertEquals(resultsList.size(), 4);
        Assert.assertEquals(resultsList.get(2), SEARCH_RESULT);
    }
}
