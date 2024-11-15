package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class SearchBoxTest extends BaseTest {

    private final static By SEARCH_FIELD = (By.xpath("//input[@role = 'searchbox']"));

    @Test
    public void testReadDocumentation() {

        getDriver().findElement(By.xpath("//a[contains(@class, 'main-search')]")).click();
        Assert.assertEquals(getDriver().findElement(By.id("search-box")).getText(), "Search Box");
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

        final WebElement searchField = getDriver().findElement(SEARCH_FIELD);
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
}