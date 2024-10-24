import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RamisTest {

    String url = "https://www.saucedemo.com";

    @Test(priority = 1)
    public void successLogin() throws InterruptedException {
        ChromeDriver driver = new ChromeDriver();
        driver.get(url);
        Thread.sleep(500);

        WebElement username = driver.findElement(By.xpath("//*[@id='user-name']"));
        username.sendKeys("standard_user");
        Thread.sleep(500);

        WebElement password = driver.findElement(By.xpath("//*[@id='password']"));
        password.sendKeys("secret_sauce");
        Thread.sleep(500);

        WebElement loginButton = driver.findElement(By.xpath("//*[@id='login-button']"));
        loginButton.click();
        Thread.sleep(1000);

        WebElement product = driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div"));
        Assert.assertEquals(product.getText(), "Sauce Labs Backpack");
        driver.quit();
    }

    @Test(priority = 2)
    public void lockedUserLogin() throws InterruptedException{
        ChromeDriver driver = new ChromeDriver();
        driver.get(url);

        WebElement username = driver.findElement(By.xpath("//*[@id='user-name']"));
        username.sendKeys("locked_out_user");
        Thread.sleep(500);

        WebElement password = driver.findElement(By.xpath("//*[@id='password']"));
        password.sendKeys("secret_sauce");
        Thread.sleep(500);

        WebElement loginButton = driver.findElement(By.xpath("//*[@id='login-button']"));
        loginButton.click();
        Thread.sleep(1000);

        WebElement lockedError = driver.findElement(By.xpath("//*[@data-test='error']"));
        Assert.assertEquals(lockedError.getText(), "Epic sadface: Sorry, this user has been locked out.");
        driver.quit();

    }

    @Test(priority = 3)
    public void problemUserLogin() throws InterruptedException{
        ChromeDriver driver = new ChromeDriver();
        driver.get(url);

        WebElement username = driver.findElement(By.xpath("//*[@id='user-name']"));
        username.sendKeys("problem_user");
        Thread.sleep(500);

        WebElement password = driver.findElement(By.xpath("//*[@id='password']"));
        password.sendKeys("secret_sauce");
        Thread.sleep(500);

        WebElement loginButton = driver.findElement(By.xpath("//*[@id='login-button']"));
        loginButton.click();
        Thread.sleep(1000);

        WebElement headerLabel = driver.findElement(By.xpath("//*[@class='app_logo']"));
        Assert.assertEquals(headerLabel.getText(), "Swag Labs");

        driver.quit();
    }
}




