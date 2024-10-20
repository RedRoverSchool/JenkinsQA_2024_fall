import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OlesyaNayTest {

    WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("incognito", "start-maximized"));

    @Test
    public void TestRatatype() {
        driver.get("https://www.ratatype.com/");
        driver.findElement(By.xpath("//*[text()='Test your speed']")).click();
        WebElement title = driver.findElement(By.xpath("//*[@class='h2']"));

        Assert.assertEquals(title.getText(), "Typing Certification Test");

        driver.quit(); 
    }

}
