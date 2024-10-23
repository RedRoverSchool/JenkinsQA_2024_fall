import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class AnastasiyaSvTest {
    ChromeOptions options;
    WebDriver driver;

    String url = "https://www.saucedemo.com";
    String username = "standard_user";
    String password = "secret_sauce";
    String locked_username = "locked_out_user";
    String lockedMsg = "Epic sadface: Sorry, this user has been locked out.";
    String wrongCredsMsg = "Epic sadface: Username and password do not match any user in this service";
    String error_username = "error_user";
    String usernameFieldLoc = "//input[@id='user-name']";
    String passwordFieldLoc = "//input[@id='password']";
    String loginBtnLoc = "//input[@id='login-button']";
    String error_msgLoc = "//div[@class='error-message-container error']";

    @BeforeClass
    public void setUp() {
        options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
        driver.get(url);
    }


    @Test()
    public void shouldNotEnterLockedUser() {
        WebElement usernameField = driver.findElement(By.xpath(usernameFieldLoc));
        usernameField.clear();
        usernameField.sendKeys(locked_username);
        WebElement passField = driver.findElement(By.xpath(passwordFieldLoc));
        passField.clear();
        passField.sendKeys(password);
        driver.findElement(By.xpath(loginBtnLoc)).click();
        String actualMsg = driver.findElement(By.xpath(error_msgLoc)).getText();
        Assert.assertEquals(actualMsg, lockedMsg);
    }

    @Test()
    public void shouldNotEnterWrongCreds() {
        WebElement usernameField = driver.findElement(By.xpath(usernameFieldLoc));
        usernameField.clear();
        usernameField.sendKeys("someUserName");
        WebElement passField = driver.findElement(By.xpath(passwordFieldLoc));
        passField.clear();
        passField.sendKeys(password);
        driver.findElement(By.xpath(loginBtnLoc)).click();
        String actualMsg = driver.findElement(By.xpath(error_msgLoc)).getText();
        Assert.assertEquals(actualMsg, wrongCredsMsg);
    }

    @Test(priority = 1)
    public void shouldSuccessEnter() {
        WebElement usernameField = driver.findElement(By.xpath(usernameFieldLoc));
        usernameField.clear();
        usernameField.sendKeys(username);
        WebElement passField = driver.findElement(By.xpath(passwordFieldLoc));
        passField.clear();
        passField.sendKeys(password);
        driver.findElement(By.xpath(loginBtnLoc)).click();
        WebElement actual = driver.findElement(By.xpath("//span[@data-test='title']"));
        Assert.assertEquals(actual.getText(), "Products");
    }

    @Test(priority = 2, description = "Добавление в корзину и удаление из неё из карточки товара на главной странице")
    public void shouldAddAndRemoveToCart() {
        List<WebElement> buttons = driver.findElements(By.className("btn_inventory"));
        for (WebElement button : buttons) {
            button.click();
        }
        WebElement cartBadge = driver.findElement(By.xpath("//span[@data-test='shopping-cart-badge']"));
        Assert.assertEquals(Integer.parseInt(cartBadge.getText()), buttons.size());

        List<WebElement> removeButtons = driver.findElements(By.className("btn_inventory"));
        for (int i = removeButtons.size() - 1; i > 2; i--) {
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(500));
            removeButtons.get(i).click();
        }
        Assert.assertEquals(Integer.parseInt(cartBadge.getText()), 3);
    }

    @Test(priority = 2, description = "Проверка сортировки по цене в оба направления")
    public void shouldSortByPrice() {
        WebElement sorter = driver.findElement(By.xpath("//select[@class='product_sort_container']"));
        sorter.click();
        driver.findElement(By.xpath("//option[@value='lohi']")).click();

        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        double first = Double.parseDouble(prices.get(0).getText().replace("$", ""));
        double last = Double.parseDouble(prices.get(prices.size() - 1).getText().replace("$", ""));
        Assert.assertTrue(first < last);

        sorter = driver.findElement(By.className("product_sort_container"));
        sorter.click();
        driver.findElement(By.xpath("//option[@value='hilo']")).click();
        prices = driver.findElements(By.className("inventory_item_price"));
        first = Double.parseDouble(prices.get(0).getText().replace("$", ""));
        last = Double.parseDouble(prices.get(prices.size() - 1).getText().replace("$", ""));
        Assert.assertTrue(first > last);
    }

    @Test(priority = 3)
    public void shouldOpenCart(){
        driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();
        String expectUrl = "https://www.saucedemo.com/cart.html";
        Assert.assertEquals(driver.getCurrentUrl(),expectUrl);
    }

    @AfterClass
    public void down() {
        driver.close();
    }

}
