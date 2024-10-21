import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Driver;
import java.time.Duration;
public class DmitrySTest {


    public static final int MAIN_PAUSE_MS = 1000;
    public static final int INPUT_PAUSE = 500;
    public static final int INPUT_PAUSE_MS = INPUT_PAUSE;
    public static final String EVILTESTER_URL = "https://testpages.eviltester.com/styled/webdriver-example-page";

    @Test
    public void testPages() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get(EVILTESTER_URL);

        Thread.sleep(MAIN_PAUSE_MS);

        testInputNumbersAndEnter(driver);

        WebElement processOnServerInput = driver.findElement(By.xpath("//*[@id=\"numentry\"]"));
        processOnServerInput.sendKeys("123");
        Thread.sleep(INPUT_PAUSE_MS);
        WebElement processOnServerButton = driver.findElement(By.xpath("//*[@id=\"submit-to-server\"]"));
        processOnServerButton.click();
        Thread.sleep(MAIN_PAUSE_MS);
        WebElement processOnServerResult = driver.findElement(By.xpath("//*[@id='message']"));
        String processOnServerTextResult = processOnServerResult.getText();
        Assert.assertEquals(processOnServerTextResult, "one, two, three");


        WebElement showAsAlertInput = driver.findElement(By.xpath("//*[@id=\"numentry\"]"));
        showAsAlertInput.sendKeys("123");
        Thread.sleep(MAIN_PAUSE_MS);
        WebElement showAsAlertButton = driver.findElement(By.xpath("//*[@id=\"show-as-alert\"]"));
        showAsAlertButton.click();
        Thread.sleep(MAIN_PAUSE_MS);

        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        Assert.assertEquals(text, "one, two, three");
        driver.switchTo().alert().accept();

        Thread.sleep(MAIN_PAUSE_MS);
        WebElement showAsParaButton = driver.findElement(By.xpath("//*[@id=\"show-as-para\"]"));
        showAsParaButton.click();
        Thread.sleep(MAIN_PAUSE_MS);
        WebElement showParaResult = driver.findElement(By.xpath("//*[@id='message']"));
        String showParaTextResult = showParaResult.getText();
        Assert.assertEquals(showParaTextResult, "one, two, three");
        Thread.sleep(MAIN_PAUSE_MS);

        WebElement url = driver.findElement(By.xpath("//*[@id=\"clickable-link\"]"));
        url.click();
        Thread.sleep(MAIN_PAUSE_MS);
        WebElement urlTextResult = driver.findElement(By.xpath("//*[@id='message']"));
        String textUrlResult = urlTextResult.getText();
        Assert.assertEquals(textUrlResult, "one, two, three, four, five, six, seven, eight, nine");
        Thread.sleep(MAIN_PAUSE_MS);


        driver.quit();
    }

    private static void testInputNumbersAndEnter(WebDriver driver) throws InterruptedException {
        WebElement textInputAndEnter = driver.findElement(By.xpath("//*[@id=\"numentry\"]"));
        Thread.sleep(INPUT_PAUSE_MS);
        textInputAndEnter.sendKeys("123");
        Thread.sleep(INPUT_PAUSE_MS);
        textInputAndEnter.sendKeys(Keys.ENTER);
        WebElement textInputAndEnterResult = driver.findElement(By.xpath("//*[@id='message']"));
        String textResult = textInputAndEnterResult.getText();
        Assert.assertEquals(textResult, "one, two, three");

        Thread.sleep(MAIN_PAUSE_MS);
    }
}