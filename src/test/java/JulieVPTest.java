import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class JulieVPTest {
    @Test
    public void testMcDonalds() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.mcdonalds.com/us/en-us.html");

        WebElement submitButton = driver.findElement(By.xpath("//*[@id='container-1e52aa8d39']/div/div[2]/div/div/div[2]/div/div[2]/div/nav/ul/li[1]/button"));
        submitButton.click();

        Thread.sleep(1000);

        driver.quit();

    }
}
