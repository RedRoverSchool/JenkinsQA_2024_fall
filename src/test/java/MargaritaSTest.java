import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;


public class MargaritaSTest {

    private WebDriver driver;
    private WebDriverWait webDriverWait;
    private JavascriptExecutor javascriptExecutor;

    @BeforeTest
    public void setup(){
         driver = new ChromeDriver();
         webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(15));
         javascriptExecutor = (JavascriptExecutor)driver;
         driver.manage().window().maximize();
//        WebDriver driver = new FirefoxDriver();
//        WebDriver driver = new EdgeDriver();
    }

    @Test
    public void test() throws InterruptedException {

        driver.get("https://practice-automation.com/form-fields/");
        webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("name-input"))));
        WebElement name = driver.findElement(By.id("name-input"));
        name.sendKeys("Test");

        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/main/div/article/div/form/label[8]")).click();
        driver.findElement(By.xpath("//label[text()='Water']")).click();
        driver.findElement(By.id("drink3")).click();

        javascriptExecutor.executeScript("window.scrollBy(0,350)", "");
        webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#color2"))));
        WebElement blueColor = driver.findElement(By.cssSelector("#color2"));
        blueColor.click();

        webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("automation"))));
        Select select = new Select(driver.findElement(By.id("automation")));
        select.selectByValue("yes");
        Thread.sleep(800);
        javascriptExecutor.executeScript("window.scrollBy(0,750)", "");

        webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/main/div/article/div/form/ul/li[1]"))));
        WebElement tools = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/main/div/article/div/form/ul/li[1]"));
        Assert.assertEquals("Selenium", tools.getText());

        driver.findElement(By.id("email")).sendKeys("test@test.com");

        webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#message"))));
        driver.findElement(By.cssSelector("#message")).sendKeys("test message");

        webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("submit-btn"))));
        WebElement submitButton = driver.findElement(By.id("submit-btn"));
        submitButton.click();

        webDriverWait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    @AfterTest
    public void closer(){
        driver.quit();
    }
}