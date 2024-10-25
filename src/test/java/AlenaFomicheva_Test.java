import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class AlenaFomicheva_Test {
    @Test
    public void testZara () throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.zara.com/us/");

        WebElement window = driver.findElement(By.xpath("//*[@id=\"onetrust-banner-sdk\"]/div/div"));
        WebElement closeButton = driver.findElement(By.xpath("//*[@id=\"onetrust-close-btn-container\"]/button"));
        Thread.sleep(1000);
        closeButton.click();


        driver.quit();
    }
}
