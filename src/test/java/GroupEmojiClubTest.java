import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GroupEmojiClubTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }


    @Test
    public void testCart() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://stockmann.ru/");

        WebElement submitButton = driver.findElement(By.xpath("//*[@id='__next']/header/div[1]/div[3]/a[2]"));
        submitButton.click();

        WebElement message = driver.findElement(By.xpath("//*[@id='__next']/main/div[2]/div/div/div/div[2]"));
        Assert.assertEquals(message.getText(), "Сейчас в корзине нет товаров");

        driver.quit();
    }

    @Test
    public void testSearch() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://stockmann.ru/");

        WebElement submitButton = driver.findElement(By.xpath("//*[@id='__next']/header/div[1]/div[1]/button[2]"));
        submitButton.click();

        WebElement textBox = driver.findElement(By.xpath("//*[@id='__next']/div[4]/div/div[2]/div/section/header/div/div/div/input"));
        textBox.sendKeys("Платье");
        Thread.sleep(500);
        textBox.sendKeys(Keys.RETURN);

        Thread.sleep(2000);

        WebElement message = driver.findElement(By.xpath("//*[@id='__next']/main/section[1]/div/h1"));
        Assert.assertEquals(message.getText(), "Результаты поиска «Платье»");

        driver.quit();
    }

    @Test
    public void testLimitedEdition() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://stockmann.ru/");

        WebElement submitButton = driver.findElement(By.xpath("//*[@id='__next']/header/div[1]/div[1]/button[1]"));
        submitButton.click();

        WebElement headerItem = driver.findElement(By.xpath("//*[@id='__next']/header/nav/div[2]/ul/li[6]/a"));
        headerItem.click();

        Thread.sleep(2000);

        WebElement message = driver.findElement(By.xpath("//*[@id='__next']/main/section[4]/div/div[2]/div[1]/div/div[1]/article/a/p"));
        Assert.assertEquals(message.getText(), "Сумка на пояс с принтом Эсла");

        driver.quit();
    }

    @Test
    public void testLockedOutUserAuthorization() throws InterruptedException {

        driver.get("https://www.saucedemo.com/");

        WebElement textBoxUsername = driver.findElement(By.xpath("//input[@name='user-name']"));
        textBoxUsername.sendKeys("locked_out_user");

        WebElement textBoxPassword = driver.findElement(By.xpath("//input[@name='password']"));
        textBoxPassword.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.xpath("//input[@id='login-button']"));
        loginButton.click();

        Thread.sleep(2000);

        WebElement textBoxError = driver.findElement(By.cssSelector(".error-message-container"));
        Assert.assertEquals(textBoxError.getText(), "Epic sadface: Sorry, this user has been locked out.");

    }

    @Test
    public void testSuccessfulAuthorization() throws InterruptedException {

        driver.get("https://www.saucedemo.com/");

        WebElement textBoxUsername = driver.findElement(By.xpath("//input[@name='user-name']"));
        textBoxUsername.sendKeys("standard_user");

        WebElement textBoxPassword = driver.findElement(By.xpath("//input[@name='password']"));
        textBoxPassword.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.xpath("//input[@id='login-button']"));
        loginButton.click();

        Thread.sleep(2000);

        WebElement itemTitle = driver.findElement(By.xpath("//a[@id='item_4_title_link']/div[@class='inventory_item_name ']"));
        Assert.assertEquals(itemTitle.getText(), "Sauce Labs Backpack");

    }

    @Test
    public void testMcDonalds() throws InterruptedException {

        driver.get("https://www.mcdonalds.com/us/en-us.html");

        WebElement submitMenuButton = driver.findElement(By.xpath("//*[@id='container-1e52aa8d39']/div/div[2]/div/div/div[2]/div/div[2]/div/nav/ul/li[1]/button"));
        submitMenuButton.click();

        Thread.sleep(1000);

        WebElement submitButton = driver.findElement(By.xpath("//*[@id='desktop-nav-1498826098']/div/div/ul/li[1]/a/span"));
        submitButton.click();

        Thread.sleep(1000);

        WebElement itemTitle = driver.findElement(By.xpath("//*[@id='title-34dd02d5b0']/h1"));
        Assert.assertEquals(itemTitle.getText(), "Chicken Big Mac®");

    }
}
