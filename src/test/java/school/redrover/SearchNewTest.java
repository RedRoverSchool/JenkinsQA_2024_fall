package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class SearchNewTest extends BaseTest {


    @Test
    public void FindSearchTest() {

        Actions actions = new Actions(getDriver());
        Wait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));

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
    public void ClickButtonTest() {

        Actions actions = new Actions(getDriver());
        Wait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));

        WebElement manageJenkinsLink = getDriver().findElement(By.cssSelector("a[href='/manage']"));

        getDriver()
                .findElement(By.cssSelector("a[id='visible-am-button'] svg"))
                .click();
        wait.until(d -> manageJenkinsLink.isDisplayed());

        manageJenkinsLink.click();

    }

}
