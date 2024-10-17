import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class EvgenySTest {
    @Test
    public void testSearchWiki() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://en.wikipedia.org/wiki/Main_Page");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.findElement(By.xpath("//*[@id='p-search']/a/span[1]")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.findElement(By.xpath("//*[@id='searchform']/div/div/div[1]/input")).sendKeys("Selenium");

        driver.findElement(By.cssSelector("#searchform > div > button")).click();

        WebElement artickleTitle = driver.findElement(By.xpath("//*[@id='firstHeading']/span[text()='Selenium']"));
        Assert.assertEquals(artickleTitle.getText(), ("Selenium"));
        driver.quit();
    }
}
