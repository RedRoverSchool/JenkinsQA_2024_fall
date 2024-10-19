import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AzamatS {
    String URL = "https://openweather.co.uk/";
    String productBtnLocator = "//*[@id=\"desktop-menu\"]/ul/li[1]/a";
    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void testProducts(){
        driver.get(URL);
        WebElement productBtn = driver.findElement(By.xpath(productBtnLocator));
        productBtn.click();
        Assert.assertEquals(driver.getTitle(), "Products - OpenWeather");
    }
}
