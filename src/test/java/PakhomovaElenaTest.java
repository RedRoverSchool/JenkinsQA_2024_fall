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
import java.util.Objects;

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
    }

    @Test
    public void testNewElementIsVisible() {
        String divLocator = "p[class='bg-success']";

        driver.get("http://uitestingplayground.com/ajax");

        driver.findElement(By.cssSelector("button[id='ajaxButton']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        String textInDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(divLocator))).getText();

        Assert.assertEquals(textInDiv,"Data loaded with AJAX get request.");
    }

    @Test
    public void testVisibilityOfButtons() {

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
    }

    @Test
    public void testWaitForLoadingPicture() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        Boolean textInp = wait.until(ExpectedConditions.textToBe(By.id("text"), "Done!"));
        Assert.assertEquals(textInp, true);
    }

    @Test
    public void testcheckboxandradio() {

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement checkedCheckbox1 = driver.findElement(By.id("my-check-1"));

        Assert.assertEquals((checkedCheckbox1.getAttribute("name")), "my-check");
        Assert.assertEquals((checkedCheckbox1.getAttribute("type")), "checkbox");
        Assert.assertTrue(checkedCheckbox1.isSelected());

        checkedCheckbox1.click();

        Assert.assertFalse(checkedCheckbox1.isSelected());

        WebElement defaultCheckbox2 = driver.findElement(By.id("my-check-2"));
        Assert.assertEquals((defaultCheckbox2.getAttribute("name")), "my-check");
        Assert.assertEquals((defaultCheckbox2.getAttribute("type")), "checkbox");
        Assert.assertFalse(defaultCheckbox2.isSelected());

        defaultCheckbox2.click();
        checkedCheckbox1.click();

        Assert.assertTrue(defaultCheckbox2.isSelected());
        Assert.assertTrue(checkedCheckbox1.isSelected());

        WebElement checkedRadio1 = driver.findElement(By.id("my-radio-1"));

        Assert.assertEquals((checkedRadio1.getAttribute("name")), "my-radio");
        Assert.assertEquals((checkedRadio1.getAttribute("type")), "radio");
        Assert.assertTrue(checkedRadio1.isSelected());

        checkedRadio1.click();

        Assert.assertTrue(checkedRadio1.isSelected());

        WebElement defaultRadio2 = driver.findElement(By.id("my-radio-2"));

        Assert.assertEquals((defaultRadio2.getAttribute("name")), "my-radio");
        Assert.assertEquals((defaultRadio2.getAttribute("type")), "radio");
        Assert.assertFalse(defaultRadio2.isSelected());

        defaultRadio2.click();

        Assert.assertTrue(defaultRadio2.isSelected());
        Assert.assertFalse(checkedRadio1.isSelected());
    }
}
