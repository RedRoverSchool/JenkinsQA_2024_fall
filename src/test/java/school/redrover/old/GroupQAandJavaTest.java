package school.redrover.old;

import java.awt.*;
import org.openqa.selenium.*;
import org.testng.Assert;
import java.awt.event.KeyEvent;

import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

@Ignore
public class GroupQAandJavaTest {

    private static final String SAUCEDEMO_URL = "https://www.saucedemo.com/";

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testDenkhoCustomerLogin() throws InterruptedException {

        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        Thread.sleep(1000);

        driver.findElement(By.xpath("//button[@ng-click='customer()']")).click();
        Thread.sleep(1000);

        Select select = new Select(driver.findElement(By.id("userSelect")));
        select.selectByVisibleText("Harry Potter");
        Thread.sleep(1000);

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(1000);

        String customerNameFact = driver.findElement(By.xpath("//*[@class='fontBig ng-binding']")).getText();
        Thread.sleep(1000);
        Assert.assertEquals(customerNameFact, "Harry Potter");
    }

    @Test
    public void testJuliaKuziLogin() {

        driver.get(SAUCEDEMO_URL);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(driver.findElement(By.className("title")).getText(), "Products");
    }

    @Test
    public void testJuliaKuziItemPage() {

        driver.get(SAUCEDEMO_URL);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//DIV/*[@data-test='inventory-item-name']")).getText(), "Sauce Labs Backpack");
    }

    @Test
    public void testJuliaKuziAddToCart() {

        driver.get(SAUCEDEMO_URL);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a/span")).getText(), "1");
        driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div")).getText(), "Sauce Labs Backpack");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[3]/div[1]")).getText(), "1");
    }

    @Test
    public void testJuliaKuziLogout() throws InterruptedException {

        driver.get(SAUCEDEMO_URL);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("logout_sidebar_link")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");
    }

    @Test
    public void testMCode() {

        driver.get("https://thecode.media/");

        WebElement searchButton = driver.findElement(By.className("heading-search__open"));
        searchButton.click();

        WebElement searchText = driver.findElement(By.className("heading-search__input"));
        searchText.sendKeys("Api");

        searchButton.click();

        WebElement foundText = driver.findElement(By.className("search__title"));
        String resultSearch = foundText.getText();

        Assert.assertEquals("api", resultSearch);
    }

    @Test
    public void testTasksArea() {

        driver.get("https://thecode.media/");

        WebElement searchArea = driver.findElement(By.className("tab-questions"));

        Action myAction = new Actions(driver).doubleClick(searchArea).build();
        myAction.perform();

        WebElement foundText = driver.findElement(By.xpath("(//h1[@class='search__title'])"));
        String foundSearchTitle = foundText.getText();

        Assert.assertEquals("Как решить", foundSearchTitle);
    }

    @Test
    public void testRadioButton() throws InterruptedException {

        driver.get("https://demoqa.com/radio-button");
        driver.findElement(By.xpath("//*[@for='impressiveRadio']")).click();
        Thread.sleep(2000);
        Assert.assertTrue(driver.findElement(By.className("text-success")).isDisplayed(), "radiobutton is not selected");
    }

    @Test
    public void testToolsQA() {

        driver.get("https://demoqa.com/text-box");

        String title = driver.getTitle();
        Assert.assertEquals(title, "DEMOQA");

        WebElement primaryImage = driver.findElement(By.xpath("//img[@src = '/images/Toolsqa.jpg']"));
        Assert.assertTrue(primaryImage.isDisplayed());
    }

    @Test
    public void testDownloadImage() throws AWTException, InterruptedException {

        driver.get("https://wallscloud.net/en");
        driver.manage().window().maximize();

        //Maybe this pop-up window won't appear, then just comment out next line
        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[1]/div[2]/div[2]/button[1]")).click();

        Robot robot = new Robot();
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        String search = "//input[@placeholder='Search...']";
        String findText = "Volvo";
        String downLoadButton = "//button[@class='btn waves btn-block search_available_res dl_original starting_download']";
        String picture = "//*[@id='main']/div[3]/div[1]/figure[26]/div/a";

        WebElement searchField = driver.findElement(By.xpath(search));
        searchField.sendKeys(findText);
        searchField.sendKeys(Keys.ENTER);

        jse.executeScript("window.scrollBy(0,850)");

        driver.findElement(By.xpath(picture)).click();

        Thread.sleep(500);

        jse.executeScript("window.scrollBy(0,1200)");

        driver.findElement(By.xpath(downLoadButton)).click();

        Thread.sleep(2000);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    @Test
    public void testAuthorization() throws InterruptedException {

        driver.get("https://school.qa.guru/cms/system/login?required=true");
        driver.manage().window().maximize();

        String email = "gjcjavxusj@zvvzuv.com";
        String password = "E3&i&d1B";

        String emailField = "email";
        String passwordField = "password";
        String enterButton = "xdget33092_1";
        String menu = "//*[@id='gcAccountUserMenu']/div/ul/li[1]/a";
        String profile = "//*[@id='gcAccountUserMenu']/div/div/ul/li[1]/a";
        String titleMyProfile = "/html/body/div[2]/div/div[1]/div/div[3]/h1";


        driver.findElement(By.name(emailField)).sendKeys(email);
        driver.findElement(By.name(passwordField)).sendKeys(password);
        driver.findElement(By.id(enterButton)).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath(menu)).click();
        driver.findElement(By.xpath(profile)).click();

        WebElement getName = driver.findElement(By.xpath(titleMyProfile));

        Thread.sleep(500);

        Assert.assertEquals(getName.getText(), "Мой профиль");
    }

    @Test
    public void testUploadFile() throws InterruptedException {

        //Select the path to any of your files for upload to site and paste it into the  next line
        String filePath = "C:\\Users\\Stan\\Downloads\\Xc90_Volvo.jpg";

        driver.get("https://practice-automation.com/file-upload/");

        WebElement chooseFile = driver.findElement(By.xpath("//*[@id='file-upload']"));
        Thread.sleep(500);
        chooseFile.sendKeys(filePath);

        WebElement uploadBtn = driver.findElement(By.cssSelector("#upload-btn"));
        uploadBtn.click();
        Thread.sleep(500);

        WebElement checkText = driver.findElement(By.xpath("//*[@id='wpcf7-f13587-p1037-o1']/form/div[2]"));
        Thread.sleep(2000);

        Assert.assertEquals(checkText.getText(), "Thank you for your message. It has been sent.");
    }
}