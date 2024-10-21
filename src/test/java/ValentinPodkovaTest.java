import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class ValentinPodkovaTest {
    @Test
    public void testButton() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demo.guru99.com/test/radio.html");

        WebElement option1 = driver.findElement(By.xpath("//input[@value='Option 1']"));
        option1.click();
        Assert.assertTrue(option1.isSelected());

        WebElement option2 = driver.findElement(By.xpath("//input[@value='Option 2']"));
        option2.click();
        Assert.assertTrue(option2.isSelected());

        WebElement option3 = driver.findElement(By.xpath("//input[@value='Option 3']"));
        option3.click();
        Assert.assertTrue(option3.isSelected());

        WebElement checkbox1 = driver.findElement(By.xpath("//input[@value='checkbox1']"));
        checkbox1.click();
        Assert.assertTrue(checkbox1.isSelected());

        WebElement checkbox2 = driver.findElement(By.xpath("//input[@value='checkbox2']"));
        checkbox2.click();
        Assert.assertTrue(checkbox2.isSelected());

        WebElement checkbox3 = driver.findElement(By.xpath("//input[@value='checkbox3']"));
        checkbox3.click();
        Assert.assertTrue(checkbox3.isSelected());

        driver.quit();
    }

    @Test
    public void testLoginPage() {

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.get("https://demo.guru99.com/test/login.html");

        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("abcd@gmail.com");

        WebElement passwordField = driver.findElement(By.name("passwd"));
        passwordField.sendKeys("abcdefghlkjl");

        WebElement loginButton = driver.findElement(By.id("SubmitLogin"));
        loginButton.click();

        String expectedUrl = "https://demo.guru99.com/test/success.html";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl, actualUrl);

        driver.quit();
    }
}

