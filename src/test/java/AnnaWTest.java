import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AnnaWTest {

    @Test
    public void testSendMessage() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://automationintesting.online/");
        Thread.sleep(500);

        WebElement nameInput = driver.findElement(By.id("name"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nameInput);
        Thread.sleep(500);
        nameInput.sendKeys("John");

        WebElement emailInput = driver.findElement(By.xpath("//input[@id = 'email']"));
        emailInput.sendKeys("js@gmail.com");

        WebElement phoneInput = driver.findElement(By.id("phone"));
        phoneInput.sendKeys("12345678900");

        WebElement subjectInput = driver.findElement(By.xpath("//input[@id = 'subject']"));
        subjectInput.sendKeys("Double queen room");

        WebElement messageInput = driver.findElement(By.cssSelector("textarea"));
        messageInput.sendKeys("Hello! I would like to reserve a room");

        WebElement submitButton = driver.findElement(By.xpath("//button[@id = 'submitContact']"));
        submitButton.click();

        Thread.sleep(5000);
        String confirmationMessage = driver.findElement(By.xpath("//div[@class = 'row contact']//h2")).getText();
        Assert.assertEquals(confirmationMessage, "Thanks for getting in touch John!");
    }
}
