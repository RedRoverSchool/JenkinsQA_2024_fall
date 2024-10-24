import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class OlgaNTest {

    WebDriver driver;

    @BeforeTest
    public void SetUp() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1500));
        driver.manage().window().maximize();
    }
    @Test
    public void checkTitle(){
       Assert.assertEquals(driver.getTitle(), "Swag Labs");
   }

   @Test
   public void checkLoginLogo(){
        String name = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]")).getText();
        Assert.assertEquals(name, "Swag Labs");
   }

   @Test
   public void errorButton(){
        WebElement userName = driver.findElement(By.id("user-name"));
        userName.sendKeys("standard_use");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");

        WebElement submit = driver.findElement(By.id("login-button"));
        submit.click();

        String error = driver.findElement(
                By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]")).getText();

        Assert.assertEquals(error, "Epic sadface: Username and password do not match any user in this service");
   }

    @AfterTest
    public void closeBrowser(){
        driver.quit();
    }
}
