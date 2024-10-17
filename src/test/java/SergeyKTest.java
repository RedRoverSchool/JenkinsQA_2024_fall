import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class SergeyKTest {
    WebDriver driver = new ChromeDriver();

    @BeforeTest
    public void robot() {
        driver.get("https://www.ozon.ru");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.findElement(By.id("reload-button")).click();
    }

    @BeforeMethod
    public void getDriver() {
        driver.manage().window().maximize();
    }


    @Test(priority = 1)
    public void searchTest() {
        driver.get("https://www.ozon.ru");
        WebElement search = driver.findElement(By.xpath("//input[@placeholder='Искать на Ozon']"));
        search.sendKeys("жилетка мужская");
        search.sendKeys(Keys.ENTER);
        Assert.assertEquals(driver.getTitle(), "жилетка мужская - купить на OZON");
    }

    @Test(priority = 2)
    public void putInBasket() {
        driver.get("https://www.ozon.ru/product/zhilet-uteplennyy-sheridi-shop-1136008418");
        driver.findElement(By.xpath("//div[@class='l7t_27 u0l_27']//button[@class='k3v_27 b2115-a0 b2115-b2 b2115-a4']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.findElement(By.xpath("//div[@class='l7t_27 u0l_27']//div[@class='b2115-a']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        Assert.assertEquals(driver.getTitle(), "OZON.ru - Моя корзина");
    }

//    @AfterTest
//    public void quitDriver() {
//        driver.quit();
//    }
}
