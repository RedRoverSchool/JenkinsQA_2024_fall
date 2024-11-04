
package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;


public class PopUpTest extends BaseTest {

    @Test
    public void PopUptest() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://candymapper.com/");

        driver.getTitle();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));


        WebElement exitButton = driver.findElement((By.id("popup-widget62629-close-icon")));

        exitButton.click();

        driver.quit();
    }
}

