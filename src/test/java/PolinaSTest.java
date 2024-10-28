import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;
public class PolinaSTest  {

    @Test

    public void scrollTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://formy-project.herokuapp.com/scroll");

        driver.getTitle();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(4000);


        WebElement textBox = driver.findElement(By.id("name"));
        Thread.sleep(2000);
        WebElement datePicker = driver.findElement((By.id("date")));
        Thread.sleep(2000);
        textBox.sendKeys("Polina");
        Thread.sleep(2000);
        datePicker.sendKeys("12/12/2024");
        Thread.sleep(3000);
        //exitButton.click();

        //WebElement message = driver.findElement(By.id("message"));
        //message.getText();

        driver.quit();
    }
}
