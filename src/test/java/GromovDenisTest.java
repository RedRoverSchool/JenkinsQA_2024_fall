import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GromovDenisTest {

    @Test
    public void Test5() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com/");
        Thread.sleep(1000);
        

//        driver.findElement(By.xpath("//*[@id=\"W0wltc\"]")).click();
        WebElement text = driver.findElement(By.className("gLFyf"));
        text.sendKeys("Selenim");
//        Thread.sleep(1000);

        WebElement button = driver.findElement(By.className("gNO89b"));
        button.click();

//        Thread.sleep(1000);


        WebElement textSel = driver.findElement(By.xpath(
                "//*[@id=\"rso\"]/div[1]/div/div/div/div/div/div/div/div[2]/div/span/em"));

        Assert.assertEquals(textSel.getText(), "Selenium");
        driver.quit();

    }
}

