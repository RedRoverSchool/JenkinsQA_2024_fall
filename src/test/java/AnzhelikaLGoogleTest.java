import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AnzhelikaLGoogleTest {
    @Test
    public void testGoogle() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com/");

        driver.findElement(By.name("q")).sendKeys("bear");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);

        WebElement bear = driver.findElement(By.xpath("//div[@role='heading'][text()='Bears']"));
        Assert.assertEquals(bear.getText(), "Bears");

        driver.quit();
    }
}
