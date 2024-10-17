import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class VitaliiITest {

    @Test
    public void testLogin() {

        WebDriver driver=new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://practicetestautomation.com/practice-test-login/");

        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement login = driver.findElement(By.id("submit"));

        username.sendKeys("student");
        password.sendKeys("Password123");
        login.click();

        Assert.assertTrue(driver.getCurrentUrl().contains("practicetestautomation.com/logged-in-successfully/"));

        driver.quit();
        }
    @Test
    public void checkIfElementExists() {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://practicetestautomation.com/practice-test-login/");


        Assert.assertNotNull(driver.findElement(By.id("username")));

        driver.quit();
    }
}
