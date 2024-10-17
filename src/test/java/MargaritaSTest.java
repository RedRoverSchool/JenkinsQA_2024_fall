import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;


public class MargaritaSTest {

    @Test
    public void test() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
////        WebDriver driver = new FirefoxDriver();
////        WebDriver driver = new EdgeDriver();


        driver.get("https://practice-automation.com/form-fields/");
        Thread.sleep(3000);

        WebElement name = driver.findElement(By.id("name-input"));
        name.sendKeys("Test");
        Thread.sleep(3000);

        WebElement drink1 = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/main/div/article/div/form/label[8]"));
        WebElement drink2 = driver.findElement(By.xpath("//label[text()='Water']"));
        WebElement drink3 = driver.findElement(By.id("drink3"));
        drink1.click();
        drink2.click();
        drink3.click();
        Thread.sleep(3000);

        WebElement color = driver.findElement(By.cssSelector("#color2"));
        color.click();
        Thread.sleep(3000);

        WebElement list = driver.findElement(By.id("automation"));
        list.click();
        WebElement yes = driver.findElement(By.cssSelector("#automation > option:nth-child(2)"));
        yes.click();
        Thread.sleep(3000);

        WebElement tools = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/main/div/article/div/form/ul/li[1]"));
        System.out.println(tools.getText());

        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("test@test.com");
        Thread.sleep(3000);

        WebElement message = driver.findElement(By.cssSelector("#message"));
        message.sendKeys("test message");
        Thread.sleep(3000);

        WebElement button = driver.findElement(By.id("submit-btn"));
        button.click();

        driver.quit();

    }
}