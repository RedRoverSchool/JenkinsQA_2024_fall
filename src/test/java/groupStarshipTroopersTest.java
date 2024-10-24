import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.AssertJUnit.assertEquals;
// comment
public class groupStarshipTroopersTest {

    WebDriver driver;
/*
    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
    }

 */

    @Test
    public void eightComponents() {
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get("https://www.21vek.by/");

        String title = driver.getTitle();
        assertEquals("Онлайн-гипермаркет 21vek.by", title);

        WebElement cacheButton = driver.findElement(By.xpath("//*[@id=\"modal-cookie\"]/div/div/div/button[2]/div"));
        cacheButton.click();

        //  WebElement textBox = driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[3]/div/button/span"));
        WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"header\"]/div[1]/div[3]/div/button"));

        //  textBox.sendKeys("Selenium");
        submitButton.click();

        WebElement message = driver.findElement(By.xpath("//*[@id=\"header\"]/div[1]/div[5]/div/div[2]/div[3]/div[1]/div[1]/div[1]/a[2]/span"));
        String value = message.getText();
        assertEquals("Холодильники", value);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
           driver.quit();
    }

    /*
    @AfterTest
    public void teardown() {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
     //   driver.quit();
    }

     */
}
