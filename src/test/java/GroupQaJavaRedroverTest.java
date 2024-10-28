import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class GroupQaJavaRedroverTest {
    @Test
    public void testSaleButton() {

        String url = "https://sport-marafon.ru/";

        WebDriver driver = new ChromeDriver();

        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement saleButton = driver.findElement(By.xpath("//ul[@class='shop-menu__wrap']/li[2]/a"));
        saleButton.click();
        WebElement text = driver.findElement(By.xpath("//h1[@class='catalog__head h1 h1_small']"));

        Assert.assertEquals(text.getText(), "Распродажа");

        driver.quit();
    }
}
