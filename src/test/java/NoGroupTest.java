import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NoGroupTest {

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
        Assert.assertEquals(itemTitle.getText(), "GOLDEN BUTTON KNIT CARDIGAN");

        driver.quit();
    }
}
