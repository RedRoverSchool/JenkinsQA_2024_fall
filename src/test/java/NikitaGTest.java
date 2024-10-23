import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Driver;


public class NikitaGTest {

    private static final String validPassword = "secret_sauce";
    private static final String URL = "https://www.saucedemo.com/";
    private static WebDriver driver;

    @BeforeTest
    public void setUpWebDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testAuth() {

        driver.get(URL);
        WebElement userName = driver.findElement(By.xpath("//*[@id=\"user-name\"]"));
        WebElement password = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        WebElement login = driver.findElement(By.xpath("//*[@id=\"login-button\"]"));
        userName.sendKeys("visual_user");
        password.sendKeys(validPassword);
        login.click();

        WebElement result = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span"));
        Assert.assertEquals(result.getText(), "Products");

    }

}
