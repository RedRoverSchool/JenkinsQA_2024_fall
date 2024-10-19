import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EduardMickelTest {

        private WebDriver driver;
        private ChromeOptions options;

        @BeforeMethod
        public void enter() {
            options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            driver = new ChromeDriver(options);
            driver.get("https://chatgpt.com/");

        }

        @AfterMethod
        public void quit() {
            if (driver != null) {
                driver.quit();
            }
        }

        @Test
        public void getTitle() {
            System.out.println(driver.getTitle());
            Assert.assertEquals(driver.getTitle(), "ChatGPT");

        }

        @Test
        public void checkDialogWindow() {
            WebElement newChatButton = driver.findElement(By.xpath("//span[@class='flex']"));
            newChatButton.click();

            WebElement dialogWindow = driver.findElement(By.xpath("//div[@role='dialog']"));
            Assert.assertTrue(dialogWindow.getText().contains("Clear current chat"));

        }
    }


