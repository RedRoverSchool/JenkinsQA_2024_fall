import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IrinaSMTest {
    @Test
    public void testSelenium() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/text-box");
        WebElement inputName = driver.findElement(By.id("userName"));
        inputName.sendKeys("Irina");
        // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) driver; // скролл вниз
        js.executeScript("window.scrollBy(0,1000)");
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();

        WebElement actualName = driver.findElement(By.id("name"));
        Assert.assertEquals(actualName.getText(), "Name:"+inputName.getAttribute("value"));

        driver.quit();

    }
}
