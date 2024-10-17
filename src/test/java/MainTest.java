
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;


public class MainTest {

    @Test
    public void testSelenium() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        //driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textBox = driver.findElement(By.name("my-text"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textBox.sendKeys("Selenium");
        submitButton.click();

        WebElement message = driver.findElement(By.id("message"));
        Assert.assertEquals(message.getText(), "Received!");

        driver.quit();
    }

    @Test
    public void testZara() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.zara.com/us/en/search/home");

        WebElement textBox = driver.findElement(By.xpath("//input[@aria-label='Search text input']"));
        textBox.sendKeys("red");
        Thread.sleep(500);
        textBox.sendKeys(Keys.RETURN);

        Thread.sleep(2000);

        WebElement itemTitle = driver.findElement(
                By.xpath("//*[@id='main']/article/div/div/section/div/section[1]/ul/li[1]/div[2]/div/div/div/div[1]/a/h2"));
        Assert.assertEquals(itemTitle.getText(), "BUTTONED KNIT CARDIGAN");

        driver.quit();
    }
}
