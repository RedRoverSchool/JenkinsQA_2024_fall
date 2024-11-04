package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;

public class PopUpTest {
@Test
    public void testPopUpExitButton() {
        // Initialize the Chrome WebDriver
        WebDriver driver = new ChromeDriver();

        // Open the website
        driver.get("https://candymapper.com/");

        // Implicit wait to allow elements to load
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        // Locate the exit button on the popup by its ID and click to close it
        WebElement exitButton = driver.findElement(By.id("popup-widget62629-close-icon"));
        exitButton.click();

        // Quit the driver, closing all associated windows
        driver.quit();
    }
}