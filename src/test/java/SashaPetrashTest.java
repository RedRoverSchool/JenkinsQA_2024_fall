import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class SashaPetrashTest {

    private WebDriver driver;

        @Test
        public void testPushClose() throws InterruptedException {


            WebDriver driver = new FirefoxDriver();
            //WebDriver driver = new ChromeDriver();

            driver.get("https://7karat.by/online?utm_source=google&utm_medium=cpc&utm_campaign=Performance%20Max%20%7C%20%D0%A3%D0%BA%D1%80%D0%B0%D1%88%D0%B5%D0%BD%D0%B8%D1%8F%20%7C%20%D0%9E%D0%B1%D1%89%D0%B0%D1%8F%20%7C%20%D0%9C%D0%B8%D0%BD%D1%81%D0%BA&utm_content=&gad_source=1&gclid=Cj0KCQjw4Oe4BhCcARIsADQ0cskQKODafOsATNV92RbW29t-mwBPkHFkWBHTPWI4ooVOuTFzIwiFZv8aAroEEALw_wcB");

            Thread.sleep(1000);
            WebElement closePush = driver.findElement(By.xpath("/html/body/div[7]/div/div/button"));

            Thread.sleep(1000);
            closePush.click();

            WebElement searchSite = driver.findElement(By.xpath("/html/body/header/div/div[1]/div[2]/div[2]/react/div/form/input"));
            Thread.sleep(1000);

            searchSite.sendKeys("золото");
            searchSite.sendKeys(Keys.ENTER);

            WebElement ringFirst = driver.findElement(By.xpath("/html/body/div[3]/div/div/h1"));
            Assert.assertEquals(ringFirst.getText(),"Купить онлайн");
            Thread.sleep(2000);

            driver.quit();

        }
    }
