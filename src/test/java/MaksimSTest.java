import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MaksimSTest {

    @Test
    public void testText() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement selectDropdown = driver.findElement(By.name("my-select"));
        selectDropdown.click();

        WebElement selectOption = driver.findElement(By.cssSelector("[value='2']"));
        selectOption.click();

        WebElement submitButton = driver.findElement(By.cssSelector("[type='submit']"));
        submitButton.click();

        WebElement messageText = driver.findElement(By.id("message"));

        Assert.assertEquals(messageText.getText(),"Received!");
        driver.quit();
    }

    @Test
    public void testCheck() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement checkInput = driver.findElement(By.id("my-check-2"));
        checkInput.click();

        Assert.assertEquals(checkInput.getDomProperty("checked"),"true");

        driver.quit();
    }

}
