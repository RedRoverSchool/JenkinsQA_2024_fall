import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class AlexLeoTest {

    @Test(description = "Практика работы с чекбоксом  https://demoqa.com/checkbox")
    public void testCheckBox() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://demoqa.com/checkbox");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.findElement(By.xpath("//*[@id='tree-node']/ol/li/span/button")).click();
        driver.quit();
    }

    @Test(description = "Двойной клик https://demoqa.com/buttons")
    public void testDoubleClickButton() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://demoqa.com/buttons");
        driver.manage().window().maximize();
        WebElement doubleClickButton = driver.findElement(By.xpath("//button[@id='doubleClickBtn' and contains(text(), 'Double Click Me')]"));
        Actions actions = new Actions(driver);
        actions.doubleClick(doubleClickButton).perform();
        WebElement doubleClickMessage = driver.findElement(By.id("doubleClickMessage"));
        String doubleClickMessageText = doubleClickMessage.getText();
        Assert.assertEquals(doubleClickMessageText, "You have done a double click");
        driver.quit();

    }
    @Test(description = "Правый клик https://demoqa.com/buttons")
    public void testRightClickButton() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://demoqa.com/buttons");
        driver.manage().window().maximize();
        WebElement rightClickButton = driver.findElement(By.xpath("//button[@id='rightClickBtn' and contains(text(), 'Right Click Me')]"));
        Actions actions = new Actions(driver);
        actions.contextClick(rightClickButton).perform();
        WebElement rightClickClickMessage = driver.findElement(By.id("rightClickMessage"));
        String rightClickClickMessageText = rightClickClickMessage.getText();
        Assert.assertEquals(rightClickClickMessageText, "You have done a right click");
        driver.quit();

    }

    @Test(description = "Динамический клик https://demoqa.com/buttons")
    public void testDynamicClickButton() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://demoqa.com/buttons");
        driver.manage().window().maximize();
        WebElement dynamicClickButton = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div[2]/div[3]/button"));
        Actions actions = new Actions(driver);
        actions.click(dynamicClickButton).perform();
        WebElement dynamicClickClickMessage = driver.findElement(By.id("dynamicClickMessage"));
        String dynamicClickClickMessageText = dynamicClickClickMessage.getText();
        Assert.assertEquals(dynamicClickClickMessageText, "You have done a dynamic click");
        driver.quit();

    }
}
