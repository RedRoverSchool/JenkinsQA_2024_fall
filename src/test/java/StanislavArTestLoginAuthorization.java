import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StanislavArTestLoginAuthorization {

    @Test
    public void testAuthorization() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://school.qa.guru/cms/system/login?required=true");

        String email = "gjcjavxusj@zvvzuv.com";
        String password = "E3&i&d1B";

        String emailField = "email";
        String passwordField = "password";
        String enterButton = "xdget33092_1";
        String menu = "//*[@id=\"gcAccountUserMenu\"]/div/ul/li[1]/a";
        String profile = "//*[@id=\"gcAccountUserMenu\"]/div/div/ul/li[1]/a";
        String titleMyProfile = "/html/body/div[2]/div/div[1]/div/div[3]/h1";


        driver.findElement(By.name(emailField)).sendKeys(email);
        driver.findElement(By.name(passwordField)).sendKeys(password);
        driver.findElement(By.id(enterButton)).click(); Thread.sleep(2000);
        driver.findElement(By.xpath(menu)).click();
        driver.findElement(By.xpath(profile)).click();

        WebElement getName = driver.findElement(By.xpath(titleMyProfile));

        Thread.sleep(500);

        Assert.assertEquals(getName.getText(), "Мой профиль");

        driver.quit();
    }
}