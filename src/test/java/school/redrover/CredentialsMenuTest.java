package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

import static org.testng.Assert.assertTrue;


public class CredentialsMenuTest extends BaseTest {

    public void getCredentialsPage()  {

        WebElement clickAdmin = getDriver().findElement(By.xpath("//button[@data-href='http://localhost:8080/user/admin' and @aria-expanded='false']"));

        new Actions(getDriver())
                .moveToElement(clickAdmin) // Наводимся на элемент
                .click() // Выполняем клик
                .perform();



        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/user/admin/credentials']")))
                .click();

    }


    @Test
    public void testNavigateCredentialsMenu() throws InterruptedException {

        getCredentialsPage();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Credentials");
    }


    @Test
    public void testAddDomainArrow()  {

        getCredentialsPage();

        WebElement userAdmin = getDriver().findElement(By.cssSelector(".model-link.inside.jenkins-table__link"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(userAdmin).perform();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String arrowContent = (String) js.executeScript("return window.getComputedStyle(arguments[0], '::before').getPropertyValue('content');", userAdmin);

        assertTrue(!arrowContent.equals("none") && !arrowContent.isEmpty());

    }

    @Test
    public void testisDisplayedDomainElementDropdown()  {

        getCredentialsPage();

        WebElement element = getDriver().findElement(By.cssSelector(".model-link.inside.jenkins-table__link"));
        new Actions(getDriver()).moveToElement(element).perform();

        WebElement element1 = getDriver().findElement(By.xpath(
                "//a[@href='/user/admin/credentials/store/user']/button"));

        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("arguments[0].click();", element1);

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(50));
        WebElement element2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'tippy-box')]")));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element2);

        assertTrue(element2.isDisplayed());

    }

}



