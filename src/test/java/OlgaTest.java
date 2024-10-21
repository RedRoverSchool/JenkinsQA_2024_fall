import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class OlgaTest {
    WebDriver driver = new ChromeDriver();
    @BeforeMethod
    public void BaseURL() {

        driver.get("https://beta.chelznak.ru/made/ ");
        driver.manage().window().maximize();
    }
    @Test
    public void testTitle() {
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Изготовление наград на заказ в ТПП Челзнак");
    }

    @Test
    public void test2(){

        WebElement textBox = driver.findElement(By.xpath("/html/body/div[1]/header/div[2]/div/div/div[1]/div/form/input"));
        textBox.sendKeys("Награда МВД");
        textBox.sendKeys(Keys.ENTER);

        String AssertMVD = driver.findElement(By.xpath("/html/body/div[1]/div[4]/div/div[1]/h1")).getText();
        Assert.assertEquals(AssertMVD,"РЕЗУЛЬТАТОВ ПО ЗАПРОСУ «НАГРАДА МВД»: 235");
    }

    @AfterTest
    public void closeBrowser(){
        driver.quit();
    }
}
