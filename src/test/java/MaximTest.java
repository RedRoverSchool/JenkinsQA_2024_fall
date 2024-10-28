import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MaximTest {

        @Test
        public void testAutomation() throws InterruptedException {
            WebDriver driver = new ChromeDriver();

            driver.get("https://www.automationexercise.com/");
            WebElement Button1 = driver.findElement(By.xpath("/html/body/section[2]/div/div/div[2]/div[1]/div[2]/div/div[2]/ul/li/a"));
            Button1.click();
            WebElement Button = driver.findElement(By.xpath("//section/div/div/div[2]/div[2]/div[2]/div/span/button"));
            Button.click();
            Thread.sleep(1000);
            WebElement itemTitle = driver.findElement(By.xpath("//section/div/div/div[2]/div[1]/div/div/div[1]/h4"));
            Assert.assertEquals(itemTitle.getText(), "Added!");

            driver.quit();
        }
}

