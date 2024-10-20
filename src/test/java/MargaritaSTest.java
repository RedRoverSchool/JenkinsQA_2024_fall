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
        webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("name-input")))).sendKeys("Test");

        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/main/div/article/div/form/label[8]")).click();
        driver.findElement(By.xpath("//label[text()='Water']")).click();
        driver.findElement(By.id("drink3")).click();

        javascriptExecutor.executeScript("window.scrollBy(0,350)", "");
        webDriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("#color2")))).click();

        Select select = new Select( webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("automation")))));
        select.selectByValue("yes");
        Thread.sleep(800);
        javascriptExecutor.executeScript("window.scrollBy(0,750)", "");

        WebElement tools = webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/main/div/article/div/form/ul/li[1]"))));
        Assert.assertEquals("Selenium", tools.getText());

        driver.findElement(By.id("email")).sendKeys("test@test.com");

        javascriptExecutor.executeScript("window.scrollBy(0,1050)", "");
        webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#message")))).sendKeys("test message");
        webDriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("submit-btn")))).click();

        webDriverWait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    @AfterTest
    public void closer(){
        driver.quit();
    }
}