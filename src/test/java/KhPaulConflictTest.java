import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class KhPaulConflictTest {

    private static final String validLogin = "standard_user";
    private static final String validPassword = "secret_sauce";
    private static final String URL = "https://www.saucedemo.com/";
    private static final By LOGIN_FIELD = By.id("user-name");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");

    private static final By SAUCE_LABS_BACKPACK = By.xpath("//div[contains(text(),'Sauce Labs Backpack')]");
    private static final By ERROR_BUTTON = By.className("error-button");


    @BeforeTest
    public void setUpWebDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterTest
    public void closeWebDriver() {

    }

    public void timeSleep() {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
    }

    @Test
    public void testAuthorization() {

        driver.get(URL);

        timeSleep();

        driver.findElement(LOGIN_FIELD).sendKeys(validLogin);
        driver.findElement(PASSWORD_FIELD).sendKeys(validPassword);
        driver.findElement(LOGIN_BUTTON).click();

        timeSleep();

        WebElement result = driver.findElement(By.xpath("//span[contains(text(), 'Products')]"));
        Assert.assertEquals(result.getText(), "Products");
    }

    @Test
    public void testLogout() {

        testAuthorization();

        timeSleep();

        driver.findElement(By.id("react-burger-menu-btn")).click();

        timeSleep();

        driver.findElement(By.id("logout_sidebar_link")).click();

        timeSleep();

        Assert.assertTrue(driver.findElement(LOGIN_BUTTON).isEnabled());


    }

    @Test
    public void testAuthorizationForBlockedUser() {

        driver.get(URL);

        timeSleep();

        driver.findElement(LOGIN_FIELD).sendKeys("locked_out_user");
        driver.findElement(PASSWORD_FIELD).sendKeys(validPassword);
        driver.findElement(LOGIN_BUTTON).click();

        timeSleep();

        Assert.assertEquals(driver.findElement(
                By.xpath("//h3[contains(text(),'Epic sadface: Sorry, this user has been locked out')]"))
                .getText(), "Epic sadface: Sorry, this user has been locked out.");
        driver.findElement(ERROR_BUTTON).click();
    }

    @Test
    public void testItemToTheShoppingCart() {

        testAuthorization();

        driver.findElement(SAUCE_LABS_BACKPACK).click();

        timeSleep();

        WebElement imgSauceLabsBackpack = driver.findElement(By.xpath("//img[@alt='Sauce Labs Backpack']"));
        Assert.assertEquals(imgSauceLabsBackpack.getAttribute("alt"), "Sauce Labs Backpack");

        driver.findElement(By.id("add-to-cart")).click();

        timeSleep();

        Assert.assertEquals(driver.findElement(By.xpath("//span[@class='shopping_cart_badge']")).getText(), "1");

        driver.findElement(By.className("shopping_cart_link")).click();

        Assert.assertEquals(driver.findElement(SAUCE_LABS_BACKPACK).getText(), "Sauce Labs Backpack");

    }

    @Test
    public void testAuthorizationInvalidLogin() {

        driver.get(URL);

        timeSleep();

        driver.findElement(LOGIN_FIELD).sendKeys("");
        driver.findElement(PASSWORD_FIELD).sendKeys(validPassword);
        driver.findElement(LOGIN_BUTTON).click();

        timeSleep();

        Assert.assertEquals(driver.findElement(
                By.xpath("//h3[contains(text(),'Epic sadface: Username is required')]"))
                .getText(), "Epic sadface: Username is required");
        driver.findElement(ERROR_BUTTON).click();
    }

    @Test
    public void testAuthorizationInvalidPassword() {

        driver.get(URL);

        timeSleep();

        driver.findElement(LOGIN_FIELD).sendKeys(validLogin);
        driver.findElement(PASSWORD_FIELD).sendKeys("123");
        driver.findElement(LOGIN_BUTTON).click();

        timeSleep();

        Assert.assertEquals(driver.findElement(
                By.xpath("//h3[contains(text(),'Epic sadface: Username and password do not match any user in this service')]"))
                .getText(), "Epic sadface: Username and password do not match any user in this service");
        driver.findElement(ERROR_BUTTON).click();
    }

    @Test
    public void testSocialNetwork() {

        testAuthorization();

        List<WebElement> socialNetwork = driver.findElements(By.xpath("//ul[@class='social']/li"));
        Assert.assertEquals(socialNetwork.size(), 3);
    }
}
