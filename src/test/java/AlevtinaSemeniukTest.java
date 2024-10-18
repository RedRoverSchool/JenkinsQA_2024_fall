import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AlevtinaSemeniukTest {
    @Test
    public void searchFieldTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.zennioptical.com/");

        Thread.sleep(1000);

        WebElement searchField = driver.findElement(By.xpath("//*[@id=\"search-input\"]/div/input"));
        searchField.sendKeys("sunglasses");

        Thread.sleep(1000);

        searchField.sendKeys(Keys.ENTER);

        Thread.sleep(1000);

        WebElement item = driver.findElement(By.xpath("//*[@id=\"main-section\"]/div[1]/div/div/h1"));
        String text = item.getText();

        Assert.assertEquals(text, "Affordable Prescription Sunglasses");

        driver.quit();
        }


    }


