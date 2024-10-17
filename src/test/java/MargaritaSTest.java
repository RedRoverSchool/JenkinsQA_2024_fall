import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class MargaritaSTest {

        @Test
        public void test() throws InterruptedException {

            WebDriver driver = new ChromeDriver();
//        WebDriver driver = new FirefoxDriver();
//        WebDriver driver = new EdgeDriver();

            driver.get("https://ru.pinterest.com/");
            Thread.sleep(15000);
            WebElement show = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[1]/div/div[1]/div[2]/a/div/div/span"));

            Thread.sleep(5000);

//        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

            show.click();
            Thread.sleep(5000);

            WebElement searchBox = driver.findElement(By.cssSelector("#searchBoxContainer > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > input:nth-child(2)"));

            searchBox.sendKeys("Selenium");
            searchBox.sendKeys(Keys.RETURN);

            Thread.sleep(10000);

            WebElement pin = driver.findElement(By.xpath("//div[text()='THE MINERAL SELENIUM.']"));
            pin.click();
//        message.getText();
            Thread.sleep(5000);

            driver.quit();
        }

}
