import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AleksandrKTest {

    private static WebDriver driver;
    private static final String validLogin = "standard_user";
    private static final String lockedOutUser = "locked_out_user";
    private static final String validPassword = "secret_sauce";
    private static final String URL = "https://www.saucedemo.com/";
    private static final By LOGIN_FIELD = By.id("user-name");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By BURGER_MENU = By.id("react-burger-menu-btn");
    private static final By LOGOUT_BUTTON = By.id("logout_sidebar_link");
    private static final By PRODUCTS = By.xpath("//span[contains(text(), 'Products')]");
    private static final By LOGIN_LOGO = By.xpath( "//div[contains(text(), 'Swag Labs')]");
    private static final By ERROR_MESSAGE = By.xpath("//h3[contains(text(),'Epic sadface: Sorry, this user has been locked out')]");
    private static final By SAUCE_LABS_BACKPACK = By.xpath("//div[contains(text(),'Sauce Labs Backpack')]");
    private static final By ADD_TO_CART_BUTTON = By.id("add-to-cart");
    private static final By SHOPPING_CART = By.className("shopping_cart_link");
    private static final By PRODUCT_COUNTER = By.xpath("//span[@class='shopping_cart_badge']");


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

    @Test
    public void testValidAuthorization() throws InterruptedException {

        driver.get(URL);

        Thread.sleep(1000);

        driver.findElement(LOGIN_FIELD).sendKeys(validLogin);
        driver.findElement(PASSWORD_FIELD).sendKeys(validPassword);
        driver.findElement(LOGIN_BUTTON).click();

        Thread.sleep(1000);

        WebElement result = driver.findElement(PRODUCTS);
        Assert.assertEquals(result.getText(), "Products");
    }

    @Test
    public void testLogout() throws InterruptedException {

        testValidAuthorization();

        Thread.sleep(1000);

        driver.findElement(BURGER_MENU).click();

        Thread.sleep(1000);

        driver.findElement(LOGOUT_BUTTON).click();

        Thread.sleep(1000);

        WebElement result = driver.findElement(LOGIN_LOGO);
        Assert.assertEquals(result.getText(), "Swag Labs");


    }

    @Test
    public void testAuthorizationForBlockedUser() throws InterruptedException {

        driver.get(URL);

        Thread.sleep(1000);

        driver.findElement(LOGIN_FIELD).sendKeys(lockedOutUser);
        driver.findElement(PASSWORD_FIELD).sendKeys(validPassword);
        driver.findElement(LOGIN_BUTTON).click();

        Thread.sleep(1000);

        WebElement errorMessage = driver.findElement(ERROR_MESSAGE);
        Assert.assertEquals(errorMessage.getText(), "Epic sadface: Sorry, this user has been locked out.");
    }

    @Test
    public void testItemToTheShoppingCart() throws InterruptedException {

        testValidAuthorization();

        driver.findElement(SAUCE_LABS_BACKPACK).click();

        Thread.sleep(1000);

        WebElement imgSauceLabsBackpack = driver.findElement(By.xpath("//img[@alt='Sauce Labs Backpack']"));
        Assert.assertEquals(imgSauceLabsBackpack.getAttribute("alt"), "Sauce Labs Backpack");

        driver.findElement(ADD_TO_CART_BUTTON).click();

        Thread.sleep(1000);

        Assert.assertEquals(driver.findElement(PRODUCT_COUNTER).getText(), "1");

        driver.findElement(SHOPPING_CART).click();

        Assert.assertEquals(driver.findElement(SAUCE_LABS_BACKPACK).getText(), "Sauce Labs Backpack");

    }
}

