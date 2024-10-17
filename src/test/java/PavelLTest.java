import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;


public class PavelLTest {

    @Test
    public void ExploringTest(){

        WebDriver driver = new ChromeDriver();
        driver.get("https://leetcode.com/");

        String ExpectedUrl = "https://leetcode.com/explore/";

        WebElement StartedButton = driver.findElement(By.xpath("//p[text() = 'Get Started ']"));
        StartedButton.click();

        String CurrentUrl = driver.getCurrentUrl();

        Assert.assertEquals(CurrentUrl, ExpectedUrl);
    }
}
