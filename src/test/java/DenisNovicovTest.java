import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

public class DenisNovicovTest {
    @Test
    public void test1 () throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get
                ("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        sleep(2000);
        WebElement userName = driver.findElement(By.xpath("(//input[@placeholder='Username'][1])"));
        userName.sendKeys("Admin");
        WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
        password.sendKeys("admin123");
        WebElement buttonLogin = driver.findElement(By.xpath("//button[@type='submit']"));
        buttonLogin.click();

        sleep(2000);

        Assert.assertEquals(driver.getCurrentUrl(),"https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");

        driver.quit();

    }
}
