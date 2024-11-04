package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.security.Key;

public class SearchFieldTest extends BaseTest {

    @Test
    public void testSearchField() {

        WebElement searchField = getDriver().findElement(By.xpath("//input[@role='searchbox']"));
        searchField.click();
        searchField.sendKeys("config" + Keys.ENTER);

        String currentUrl = getDriver().getCurrentUrl();

        Assert.assertEquals(currentUrl, "http://localhost:8080/configure", "Current URL does not meet requirements");

    }

    @Test
    public void testInputField() {

        WebElement searchField = getDriver().findElement(By.xpath("//input[@role='searchbox']"));
        searchField.click();
        searchField.sendKeys("log" + Keys.ENTER);

        String logPage = getDriver().findElement(By.xpath("//div[@id='main-panel']")).getText().toLowerCase();

        Assert.assertTrue(logPage.contains("log"), "Current page does not contain 'log' text");
    }

}
