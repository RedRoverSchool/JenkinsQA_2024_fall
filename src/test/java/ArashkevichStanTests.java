
import java.awt.*;
import org.testng.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import java.awt.event.KeyEvent;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ArashkevichStanTests {

    WebDriver driver;

    @Test
    public void downloadImage() throws AWTException, InterruptedException {

        driver = new ChromeDriver();

        driver.get("https://wallscloud.net/en");
        driver.manage().window().maximize();

        //Maybe this pop-up window won't appear, then just comment out next line
        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[1]/div[2]/div[2]/button[1]")).click();

        Robot robot = new Robot();
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        String search = "//input[@placeholder='Search...']";
        String findText = "Volvo";
        String dLoadButton = "//button[@class='btn waves btn-block search_available_res dl_original starting_download']";
        String picture = "//*[@id=\"main\"]/div[3]/div[1]/figure[26]/div/a";

        WebElement searchField = driver.findElement(By.xpath(search));
        searchField.sendKeys(findText);
        searchField.sendKeys(Keys.ENTER);

        jse.executeScript("window.scrollBy(0,850)");

        driver.findElement(By.xpath(picture)).click();

        Thread.sleep(500);

        jse.executeScript("window.scrollBy(0,1000)");

        driver.findElement(By.xpath(dLoadButton)).click();

        Thread.sleep(1000);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    @Test
    public void testAuthorization() throws InterruptedException {

        driver = new ChromeDriver();

        driver.get("https://school.qa.guru/cms/system/login?required=true");
        driver.manage().window().maximize();

        String email = "gjcjavxusj@zvvzuv.com";
        String password = "E3&i&d1B";

        String emailField = "email";
        String passwordField = "password";
        String enterButton = "xdget33092_1";
        String menu = "//*[@id=\"gcAccountUserMenu\"]/div/ul/li[1]/a";
        String profile = "//*[@id=\"gcAccountUserMenu\"]/div/div/ul/li[1]/a";
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
    public void uploadFile() throws InterruptedException {

        String filePath = "C:\\Users\\Stan\\Downloads\\Xc90_Volvo.jpg";

        driver = new ChromeDriver();
        driver.get("https://practice-automation.com/file-upload/");

        WebElement chooseFile = driver.findElement(By.xpath("//*[@id=\"file-upload\"]"));
        Thread.sleep(500);
        chooseFile.sendKeys(filePath);

        WebElement uploadBtn = driver.findElement(By.cssSelector("#upload-btn"));
        uploadBtn.click();
        Thread.sleep(500);

        WebElement checkText = driver.findElement(By.xpath("//*[@id=\"wpcf7-f13587-p1037-o1\"]/form/div[2]"));
        Thread.sleep(2000);

        Assert.assertEquals(checkText.getText(), "Thank you for your message. It has been sent.");

        driver.quit();
    }
}