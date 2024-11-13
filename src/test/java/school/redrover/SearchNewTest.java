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
    public void FindSearchTest() throws InterruptedException {

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

        Assert.assertEquals(actual, "Поиск 'TestSearch'");

    }

    @Test
    public void ClickButtonTest() throws InterruptedException {

        Actions actions = new Actions(getDriver());
        Wait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));

        WebElement manageJenkinsLink = getDriver().findElement(By.cssSelector("a[href='/manage']"));

        getDriver()
                .findElement(By.cssSelector("a[id='visible-am-button'] svg"))
                .click();
        wait.until(d -> manageJenkinsLink.isDisplayed());

        manageJenkinsLink.click();

    }

    @Test
    public void ButtonAdminTest() throws InterruptedException {

        getDriver().get("http://localhost:8080/user/admin/");

        Actions actions = new Actions(getDriver());
        Wait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        final WebElement buttonAdmin = getDriver().findElement(By.cssSelector("li:nth-child(3) a:nth-child(1)"));

        actions.moveToElement(buttonAdmin).perform();

        final WebElement chevron = getDriver().findElement(By.xpath("(//button[@class='jenkins-menu-dropdown-chevron'])[3]"));

        wait.until(d -> chevron.isEnabled());

        chevron.click();

        final WebElement buildButton = getDriver()
                .findElement(By.xpath("//div[@id='tippy-5']//a[1]"));

        wait.until(d -> buildButton.isDisplayed());

        buildButton.click();

    }
}
