import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class AramHTest {

    @Test
    public void AramHtest() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.bestbuy.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.findElement(By.id("gh-search-input")).sendKeys("laptop");
        driver.findElement(By.xpath("//span[@class='header-search-icon']")).click();

        WebElement message = driver.findElement(By.xpath("//div[@class='title-wrapper title']/span[2]/span[2]"));

        Assert.assertEquals(message.getText(), "in Laptops");

        driver.quit();

    }
}
