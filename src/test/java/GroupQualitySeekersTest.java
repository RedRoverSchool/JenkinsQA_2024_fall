import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class GroupQualitySeekersTest {
    @Test
    public void bankManagerLogin() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbx");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement buttonManager = driver.findElement(By.xpath("//div[1]/div[2]/button"));
        buttonManager.click();
        WebElement buttonAddCustomer = driver.findElement(By.xpath("//div[2]/div/div[1]/button[1]"));
        Assert.assertEquals(buttonAddCustomer.getText(), "Add Customer");
        driver.quit();
    }

    @Test
    public void goToHome() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbx");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
        WebElement buttonHome = driver.findElement(By.xpath("//div/div/div[1]/button[1]"));
        buttonHome.click();
        //WebElement buttonManager = driver.findElement(By.xpath("//div/div/div[2]/div/div[1]/div[2]/button"));
        WebElement buttonManager = driver.findElement(By.xpath("//div[1]/div[2]/button"));
        Assert.assertEquals(buttonManager.getText(), "Bank Manager Login");
        driver.quit();
    }
}
