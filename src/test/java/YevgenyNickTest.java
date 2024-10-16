import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class YevgenyNickTest {

    @Test
    public void testSuccessfulAuthorization() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        options.addArguments("--no-sandbox");
        options.addArguments("--incognito");
        driver.get("https://www.saucedemo.com/");

        WebElement textBoxUsername = driver.findElement(By.xpath("//input[@name='user-name']"));
        textBoxUsername.sendKeys("standard_user");
        WebElement textBoxPassword = driver.findElement(By.xpath("//input[@name='password']"));
        textBoxPassword.sendKeys("secret_sauce");
        WebElement loginButton = driver.findElement(By.xpath("//input[@id='login-button']"));
        loginButton.click();

        Thread.sleep(2000);

        WebElement itemTitle = driver.findElement(By.xpath("//a[@id='item_4_title_link']/div[@class='inventory_item_name ']"));
        Assert.assertEquals(itemTitle.getText(), "Sauce Labs Backpack");

        driver.quit();
    }
}
