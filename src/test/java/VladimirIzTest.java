import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VladimirIzTest {

    WebDriver driver = new ChromeDriver();
    @BeforeMethod
    public void getDriver() {
        driver.manage().window().maximize();
    }
    @Test
    public void wikiTest() {

        driver.get("https://ru.wikipedia.org/wiki/");
        WebElement search = driver.findElement(By.xpath( "//*[@id='searchInput']"));
        search.sendKeys("Java");
        search.sendKeys(Keys.ENTER);

        Assert.assertEquals(driver.findElement(By.xpath("//div[2]/div[2]/div[1]/a/span")).getText(), "Java");
        driver.quit();

    }

    @Test
    public void wildberriesTest() {

        driver.get("https://www.wildberries.ru/");
        WebElement search = driver.findElement(By.xpath("//*[@id='searchInput']"));
        search.sendKeys("Джинсы");
        search.sendKeys(Keys.ENTER);

        driver.findElement(By.xpath("//button[@class='product-card__heart j-add-to-postpone']")).click();

        driver.manage().getCookies();

//        Assert.assertEquals(driver.findElement(By.xpath("//*[@id='catalog']/div/div[1]/div/h1")).getText(), "Джинсы");
//
//
//        driver.quit();   //h1[@class='searching-results__title']  //*[@id='catalog']/div/div[1]/div/h1
//

    }




}
