import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class AyaTest {

    @Test
    public void testLogin(){
        WebDriver driver=new ChromeDriver();
        driver.get("https://www.saucedemo.com/?form=MG0AV3");

        WebElement username= driver.findElement(By.id("user-name"));
        username.sendKeys("standard_user");
        WebElement password= driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");
        WebElement loginBtn=driver.findElement(By.id("login-button"));
        loginBtn.click();
        driver.quit();
    }
}
