import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class PakhomovaElenaTest {
    WebDriver driver;

    @BeforeMethod
    public void setDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        driver = new ChromeDriver(options);
    }

    @AfterMethod
    public void closeBrowser() {
        driver.quit();
    }

    @Test
    public void testLogInStandardUser() {

        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String textLogoPage = driver.findElement(By.cssSelector("span[data-test='title']")).getText();

        Assert.assertEquals(textLogoPage, "Products");
    }

    @Test
    public void testCheckAmountInCard() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");

        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-onesie")).click();

        driver.findElement(By.xpath("//a[@data-test='shopping-cart-link']")).click();
        driver.findElement(By.xpath("//button[@id='checkout']")).click();

        driver.findElement(By.id("first-name")).sendKeys("Bobr");
        driver.findElement(By.id("last-name")).sendKeys("Bobry");
        driver.findElement(By.id("postal-code")).sendKeys("192243");
        driver.findElement(By.xpath("//input[@id='continue']")).click();

        String expectedAmountInCard = driver.findElement(By.xpath("//div[@data-test='total-label']")).getText();

        Assert.assertEquals(expectedAmountInCard,"Total: $58.29");

        driver.quit();
    }

    @Test
    public void testNewElementIsVisible() {
        String divLocator = "p[class='bg-success']";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("http://uitestingplayground.com/ajax");

        driver.findElement(By.cssSelector("button[id='ajaxButton']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        String textInDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(divLocator))).getText();

        Assert.assertEquals(textInDiv,"Data loaded with AJAX get request.");

        driver.quit();

    }

    @Test
    public void testVisibilityOfButtons() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("http://uitestingplayground.com/visibility");

        WebElement zeroWidthButton = driver.findElement(By.id("zeroWidthButton"));
        WebElement overlappedButton = driver.findElement(By.id("overlappedButton"));
        WebElement transparentButton = driver.findElement(By.id("transparentButton"));
        WebElement invisibleButton = driver.findElement(By.id("invisibleButton"));
        WebElement notdisplayedButton = driver.findElement(By.id("notdisplayedButton"));
        WebElement offscreenButton = driver.findElement(By.id("offscreenButton"));

        driver.findElement(By.id("hideButton")).click();

        Assert.assertFalse(zeroWidthButton.isDisplayed());
        Assert.assertTrue(overlappedButton.isDisplayed());
        Assert.assertFalse(transparentButton.isDisplayed());
        Assert.assertFalse(invisibleButton.isDisplayed());
        Assert.assertFalse(notdisplayedButton.isDisplayed());
        Assert.assertFalse(offscreenButton.isDisplayed());


        driver.quit();
    }

    @Test
    public void testWaitForLoadingPicture() {

    }
}
