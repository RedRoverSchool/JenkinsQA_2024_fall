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

public class AleksandrKTest {

    private static WebDriver driver;
    private static final String validLogin = "standard_user";
    private static final String lockedOutUser = "locked_out_user";
    private static final String validPassword = "secret_sauce";
    private static final String invalidLogin = "";
    private static final String invalidPassword = "123";
    private static final String URL = "https://www.saucedemo.com/";
    private static final By LOGIN_FIELD = By.id("user-name");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By BURGER_MENU = By.id("react-burger-menu-btn");
    private static final By LOGOUT_BUTTON = By.id("logout_sidebar_link");
    private static final By PRODUCTS = By.xpath("//span[contains(text(), 'Products')]");
    private static final By ERROR_MESSAGE_BLOCKED_USER = By.xpath("//h3[contains(text(),'Epic sadface: Sorry, this user has been locked out')]");
    private static final By ERROR_MESSAGE_USER = By.xpath("//h3[contains(text(),'Epic sadface: Username is required')]");
    private static final By ERROR_MESSAGE_USER_AND_PASSWORD = By.xpath("//h3[contains(text(),'Epic sadface: Username and password do not match any user in this service')]");
    private static final By SAUCE_LABS_BACKPACK = By.xpath("//div[contains(text(),'Sauce Labs Backpack')]");
    private static final By ADD_TO_CART_BUTTON = By.id("add-to-cart");
    private static final By SHOPPING_CART = By.className("shopping_cart_link");
    private static final By PRODUCT_COUNTER = By.xpath("//span[@class='shopping_cart_badge']");
    private static final By ERROR_BUTTON = By.className("error-button");


    @BeforeTest
    public void setUpWebDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterTest
    public void closeWebDriver() {
        if (driver != null) {
            driver.quit();
        }
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

        WebElement result = driver.findElement(PRODUCTS);
        Assert.assertEquals(result.getText(), "Products");
    }

    @Test
    public void testLogout() {

        testAuthorization();

        timeSleep();

        driver.findElement(BURGER_MENU).click();

        timeSleep();

        driver.findElement(LOGOUT_BUTTON).click();

        timeSleep();

        Assert.assertTrue(driver.findElement(LOGIN_BUTTON).isEnabled());


    }

    @Test
    public void testAuthorizationForBlockedUser() {

        driver.get(URL);

        timeSleep();

        driver.findElement(LOGIN_FIELD).sendKeys(lockedOutUser);
        driver.findElement(PASSWORD_FIELD).sendKeys(validPassword);
        driver.findElement(LOGIN_BUTTON).click();

        timeSleep();

        Assert.assertEquals(driver.findElement(ERROR_MESSAGE_BLOCKED_USER).getText(), "Epic sadface: Sorry, this user has been locked out.");
        driver.findElement(ERROR_BUTTON).click();
    }

    @Test
    public void testItemToTheShoppingCart() {

        testAuthorization();

        driver.findElement(SAUCE_LABS_BACKPACK).click();

        timeSleep();

        WebElement imgSauceLabsBackpack = driver.findElement(By.xpath("//img[@alt='Sauce Labs Backpack']"));
        Assert.assertEquals(imgSauceLabsBackpack.getAttribute("alt"), "Sauce Labs Backpack");

        driver.findElement(ADD_TO_CART_BUTTON).click();

        timeSleep();

        Assert.assertEquals(driver.findElement(PRODUCT_COUNTER).getText(), "1");

        driver.findElement(SHOPPING_CART).click();

        Assert.assertEquals(driver.findElement(SAUCE_LABS_BACKPACK).getText(), "Sauce Labs Backpack");

    }

    @Test
    public void testAuthorizationInvalidLogin() {

        driver.get(URL);

        timeSleep();

        driver.findElement(LOGIN_FIELD).sendKeys(invalidLogin);
        driver.findElement(PASSWORD_FIELD).sendKeys(validPassword);
        driver.findElement(LOGIN_BUTTON).click();

        timeSleep();

        Assert.assertEquals(driver.findElement(ERROR_MESSAGE_USER).getText(), "Epic sadface: Username is required");
        driver.findElement(ERROR_BUTTON).click();
    }

    @Test
    public void testAuthorizationInvalidPassword() {

        driver.get(URL);

        timeSleep();

        driver.findElement(LOGIN_FIELD).sendKeys(validLogin);
        driver.findElement(PASSWORD_FIELD).sendKeys(invalidPassword);
        driver.findElement(LOGIN_BUTTON).click();

        timeSleep();

        Assert.assertEquals(driver.findElement(ERROR_MESSAGE_USER_AND_PASSWORD).getText(), "Epic sadface: Username and password do not match any user in this service");
        driver.findElement(ERROR_BUTTON).click();
    }

    @Test
    public void testSocialNetwork() {

        testAuthorization();

        List<WebElement> socialNetwork = driver.findElements(By.xpath("//ul[@class='social']/li"));
        Assert.assertEquals(socialNetwork.size(), 3);
    }
}

