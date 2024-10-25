import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;

import static java.lang.Thread.sleep;

public class AutoamigosGroupTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void testClickHomeLink() throws InterruptedException {

        driver.get("https://demoqa.com/links");
        driver.manage().window().maximize();

        String mainWindowHandle = driver.getWindowHandle();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 1000);");

        WebElement linksHome = driver.findElement(By.xpath("//*[@id='simpleLink']"));
        linksHome.click();

        sleep(2000);

        Set<String> allWindowsHandle = driver.getWindowHandles();
        for (String window: allWindowsHandle) {
            if (!window.equals(mainWindowHandle)){
                driver.switchTo().window(window);
            }
        }

        Assert.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/");
    }

    @Test
    public void testElementsCard() {

        driver.get("https://demoqa.com/");
        driver.manage().window().maximize();

        WebElement elements = driver.findElement(By.xpath("//h5[text()='Elements']/../../.."));
        elements.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/elements");
    }

    @Test
    public void testCheckBoxAngular() throws InterruptedException {

        driver.get("https://demoqa.com/checkbox");
        driver.manage().window().maximize();

        WebElement checkBoxMenu = driver.findElement(By.xpath("//span[text()='Check Box']/.."));
        checkBoxMenu.click();

        WebElement expandAllButton = driver.findElement(By.xpath("//button[@aria-label='Expand all']"));
        expandAllButton.click();

        //ищем элемент class='rct-checkbox' являющийся предшествующим sibling для элемента span[text()='Angular']
        WebElement angularCheckBox = driver.findElement(By.xpath("//span[text()='Angular']/preceding-sibling::span[@class='rct-checkbox']"));
        angularCheckBox.click();

        WebElement resultOfSelection = driver.findElement(By.xpath("//span[text()='angular']"));

        Assert.assertEquals(resultOfSelection.getText(), "angular");

    }

    @Test
    public void testYesRadioButton() throws InterruptedException {

        driver.get("https://demoqa.com/elements");
        driver.manage().window().maximize();
        sleep(3000);

        WebElement radioButtonMenu = driver.findElement(By.xpath("//span[text()='Radio Button']/.."));
        radioButtonMenu.click();

        sleep(1000);

//        WebElement yesRadioButton =
                driver.findElement(By.xpath("//div/input[@id='yesRadio']")).click();////label[@for='yesRadio']/preceding-sibling::input"
//        yesRadioButton.click();

        sleep(3000);

        WebElement message = driver.findElement(By.xpath("//p/span[@class='text-success']"));

        Assert.assertEquals(message.getText(), "Yes");

    }

}
