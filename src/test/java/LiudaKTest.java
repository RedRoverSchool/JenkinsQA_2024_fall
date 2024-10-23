import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LiudaKTest {

    @Test
    public void verifyStandartUserPassword() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.saucedemo.com/");

        driver.manage().window().fullscreen();

        WebElement textBoxUserName = driver.findElement(By.xpath("//*[@id='user-name']"));
        textBoxUserName.sendKeys("standard_user");

        WebElement textBoxPassword = driver.findElement(By.xpath("//*[@id='password']"));
        textBoxPassword.sendKeys("secret_sauce");

        Thread.sleep(2000);

        WebElement buttonLogin = driver.findElement(By.xpath("//*[@id='login-button']"));
        buttonLogin.click();

        Thread.sleep(2000);

        WebElement itemTitle = driver.findElement(By.xpath("//*[@id='header_container']/div[2]/span"));
        Assert.assertEquals(itemTitle.getText(), "Products");

        driver.quit();
    }

}
