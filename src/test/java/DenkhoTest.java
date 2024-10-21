import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class DenkhoTest {

    WebDriver driver;
    String BASE_URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";

    @BeforeTest
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
    }

    @AfterTest
    public void tearDown() {driver.close();}

    @Test
    public void testCustomerLogin() throws InterruptedException{
        driver.get(BASE_URL);
        Thread.sleep(1000);

        driver.findElement(By.xpath("//button[@ng-click='customer()']")).click();
        Thread.sleep(1000);

        Select select = new Select(driver.findElement(By.id("userSelect")));
        select.selectByVisibleText("Harry Potter");
        Thread.sleep(1000);

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(1000);

        String customerNameFact = driver.findElement(By.xpath("//*[@class='fontBig ng-binding']")).getText();
        Thread.sleep(1000);
        Assert.assertEquals(customerNameFact, "Harry Potter");
    }

}
