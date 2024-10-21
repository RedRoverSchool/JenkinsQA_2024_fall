import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

public class AyaTest {
    WebDriver driver=new ChromeDriver();

    @Test
    public void testLogin(){

        driver.get( "https://www.saucedemo.com/?form=MG0AV3");

        WebElement username= driver.findElement(By.id("user-name"));
        username.sendKeys("standard_user");
        WebElement password= driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");
        WebElement loginBtn=driver.findElement(By.id("login-button"));
        loginBtn.click();
       // driver. quit();
    }
    @Test
    public void testLogOut() throws InterruptedException {
        driver.get( "https://www.saucedemo.com/?form=MG0AV3");
        sleep(1000);
        WebElement username= driver.findElement(By.id("user-name"));
        username.sendKeys("standard_user");
        WebElement password= driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");
        WebElement loginBtn=driver.findElement(By.id("login-button"));
        loginBtn.click();
        WebElement menuBtn= driver.findElement(By.id("react-burger-menu-btn"));
        menuBtn.click();
        sleep(1000);
        WebElement logoutBtn=driver.findElement(By.id("logout_sidebar_link"));
        logoutBtn.click();
    }
}
