import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;

public class DenDemeshchikTest {

        WebDriver driver = new ChromeDriver();
        @BeforeTest
        public void baseURL() {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");

            driver.get("https://tasnet.uz"); // - Открыть страницу
            driver.manage().window().maximize(); // - Открывает всегда во весь экран
        }

        @Test
        public void test() {
            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
        }
        @Test
        public void test2() {
            WebElement cost = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div[1]/ul/li[3]/a"));
            cost.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }

        @Test
        public void test3() {

            driver.get("https://tasnet.uz/business");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            WebElement searchEmail = driver.findElement(By.xpath("//*[@id=\"email\"]"));
            searchEmail.sendKeys("Java", Keys.ENTER);
        }

        @AfterTest
        public void closeBrowse() {
            driver.quit();
        }
}

