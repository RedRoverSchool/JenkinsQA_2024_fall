import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

public class AnnaL {
    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void testOpenRegisterPage(){

        driver.get("https://www.sharelane.com/cgi-bin/main.py");

        WebElement singUpButton = driver.findElement(By.linkText("Sign up"));
        singUpButton.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.sharelane.com/cgi-bin/register.py");
    }

    @Test
    public void testSendInvalidZipCode() throws InterruptedException {
        testOpenRegisterPage();

        WebElement inputZipCode = driver.findElement(By.name("zip_code"));
        inputZipCode.sendKeys("1234");
        sleep(2000);

        WebElement buttonContinue = driver.findElement(By.xpath("//input[@value='Continue']"));
        buttonContinue.click();

        WebElement errorMessage = driver.findElement(By.xpath("//span[@class='error_message']"));

        Assert.assertEquals(errorMessage.getText(), "Oops, error on page. ZIP code should have 5 digits");

    }
}
