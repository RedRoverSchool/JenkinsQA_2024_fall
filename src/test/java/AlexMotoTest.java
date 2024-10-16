import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class AlexMotoTest {
    @Test
    public void testMyBag (){
        WebDriver driver = new ChromeDriver();
        driver.get("https://us.sportsdirect.com/");
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement acceptCookie = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
            acceptCookie.click();
        } catch (TimeoutException e){
            System.out.println("Cookie accept button not found or already accepted.");
        }
        WebElement myBagButton = driver.findElement(By.xpath("//a[@id='aBagLink']"));
        Assert.assertTrue(myBagButton.isDisplayed(), "My button should displayed");
        driver.quit();
    }
}
