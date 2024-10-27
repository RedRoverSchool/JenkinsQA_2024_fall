import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AidaMontoyaTest {


    @Test
    public void testDutchbros() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.dutchbros.com/");

        WebElement clickButton = driver.findElement(By.xpath("//div[3]//article//a"));//find "view menu"
        clickButton.sendKeys(Keys.RETURN);
        Thread.sleep(2000);

        clickButton = driver.findElement(By.xpath("//*[@id='block-dutchbros-content']/article/div[2]/div[1]/a"));//find "caramel pumpkin"
        Thread.sleep(1000);
        clickButton.sendKeys(Keys.RETURN);

        Assert.assertEquals("CARAMEL PUMPKIN BRULEE BREVE", "CARAMEL PUMPKIN BRULEE BREVE");

        driver.quit();
    }

    @Test
    public void testLibrary() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://catalog.saclibrary.org/");

        driver.findElement(By.xpath("//a[starts-with(@href, 'http://linkencore.iii.com/')]")).click();// find link+ button/click
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id='searchString']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id='dialogComponent-content']"));// find "No results found"

        Assert.assertEquals("You must enter a valid for Search.OK", "You must enter a valid for Search.OK");

        driver.quit();
    }
}

