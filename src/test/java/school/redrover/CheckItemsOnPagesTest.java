package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CheckItemsOnPagesTest extends BaseTest {

    private void verifyElementText(By locator, String expectedText) {
        WebElement element = getDriver().findElement(locator);
        Assert.assertTrue(element.isDisplayed(), "Элемент не отображается: " + locator);
        Assert.assertEquals(element.getText(), expectedText, "Текст элемента не соответствует: " + locator);
    }

    @Test
    public void checkTitlesAndButtonsOnMainPage(){
        verifyElementText(By.cssSelector("#description-link"), "Add description");
        verifyElementText(By.cssSelector("div[id='tasks'] div:nth-child(1) span:nth-child(1) a:nth-child(1) span:nth-child(2)"), "New Item");
        verifyElementText(By.cssSelector("body > div:nth-child(4) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > span:nth-child(1) > a:nth-child(1) > span:nth-child(2)"), "Build History");
        verifyElementText(By.cssSelector("a[href='/manage']"), "Manage Jenkins");
        verifyElementText(By.cssSelector("a[href='/me/my-views']"), "My Views");
  }


}
