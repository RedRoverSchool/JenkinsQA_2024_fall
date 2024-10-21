import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EldarTest {

    @Test
    public void testTitleText() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement title = driver.findElement(By.className("display-6"));
        String titleText = title.getText();

        Assert.assertEquals(titleText, "Web form");

        driver.quit();
    }

    @Test
    public void testSubmitButton() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement button = driver.findElement(By.tagName("button"));
        button.click();

        WebElement title = driver.findElement(By.tagName("h1"));
        String titleText = title.getText();

        Assert.assertEquals(titleText, "Form submitted");

        driver.quit();
    }
}
