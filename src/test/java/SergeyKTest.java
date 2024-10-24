import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class SergeyKTest {

    WebDriver driver;

    @BeforeTest
    public void getDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }


    @Test
    public void searchTest() {
        driver.get("https://www.wildberries.ru/");
        WebElement search = driver.findElement(By.xpath("//input[@id='searchInput']"));
        search.sendKeys("жилетка мужская");
        search.sendKeys(Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement searchResponse = driver.findElement(By.xpath("//h1[@class='searching-results__title']"));
        Assert.assertEquals(searchResponse.getText(), "жилетка мужская");
    }

    @Test
    public void putInBasket() {
        driver.get("https://www.wildberries.ru/catalog/230563558/detail.aspx");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.xpath("//button[@class='cookies__btn btn-minor-md']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.xpath("//label[@class='j-size sizes-list__button']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.xpath("//div[@class='product-page__aside']//button[@class='order__button btn-main']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
//        driver.findElement(By.xpath("//div[@class='product-page__order']//a[@class='order__button btn-base j-go-to-basket']")).click();
//        WebElement basket = driver.findElement(By.xpath("//h1[@class='basket-section__header basket-section__header--main active']"));
//        Assert.assertEquals(basket.getText(), "            Корзина                    ");
    }

    @AfterTest
    public void quitDriver() {
        driver.quit();
    }

}