package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CheckItemsOnPages extends BaseTest {

    private void verifyElementText(By locator, String expectedText) {
        WebElement element = getDriver().findElement(locator);
        Assert.assertTrue(element.isDisplayed(), "Элемент не отображается: " + locator);
        Assert.assertEquals(element.getText(), expectedText, "Текст элемента не соответствует: " + locator);
    }

    @Test
    public void checkTitlesAndButtonsOnMainPage(){
        verifyElementText(By.tagName("h1"), "Welcome to Jenkins!");
        verifyElementText(By.xpath("/html[1]/body[1]/div[2]/div[2]/div[1]/div[2]/div[2]/a[1]"), "Add description");
        verifyElementText(By.xpath("/html[1]/body[1]/div[2]/div[2]/div[2]/div[1]/section[1]/ul[1]/li[1]/a[1]/span[1]"), "Create a job");
        verifyElementText(By.xpath("/html[1]/body[1]/div[2]/div[2]/div[2]/div[1]/section[2]/ul[1]/li[1]/a[1]/span[1]"), "Set up an agent");
        verifyElementText(By.xpath("/html[1]/body[1]/div[2]/div[2]/div[2]/div[1]/section[2]/ul[1]/li[2]/a[1]/span[1]"), "Configure a cloud");
        verifyElementText(By.xpath("/html[1]/body[1]/div[2]/div[2]/div[2]/div[1]/section[2]/ul[1]/li[3]/a[1]/span[1]"), "Learn more about distributed builds");
        verifyElementText(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/span[1]/a[1]"), "New Item");
        verifyElementText(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[2]/span[1]/a[1]/span[2]"), "Build History");
        verifyElementText(By.cssSelector("a[href='/manage']"), "Manage Jenkins");
        verifyElementText(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[4]/span[1]/a[1]/span[2]"), "My Views");
  }


}
