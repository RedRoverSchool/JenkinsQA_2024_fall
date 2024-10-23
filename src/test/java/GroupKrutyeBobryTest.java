import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class GroupKrutyeBobryTest {

    @Test
    public void testWaitForLoadingPicture() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        Boolean textInp = wait.until(ExpectedConditions.textToBe(By.id("text"), "Done!"));
        Assert.assertEquals(textInp, true);

        driver.quit();
    }
}
