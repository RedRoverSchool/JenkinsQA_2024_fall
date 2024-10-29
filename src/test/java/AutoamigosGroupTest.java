import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.lang.Thread.sleep;

public class AutoamigosGroupTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = new ChromeDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void testClickHomeLink() throws InterruptedException {

        driver.get("https://demoqa.com/links");
        driver.manage().window().maximize();

        String maimWindowHandle = driver.getWindowHandle();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 1000);");

        WebElement linksHome = driver.findElement(By.xpath("//*[@id='simpleLink']"));
        linksHome.click();

        sleep(2000);

        Set<String> allWindowsHandle = driver.getWindowHandles();
        for (String window: allWindowsHandle) {
            if (!window.equals(maimWindowHandle)){
                driver.switchTo().window(window);
            }
        }

        Assert.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/");
    }

    @Test
    public void testSelectAllCheckBoxes() throws InterruptedException {

        driver.get("https://demoqa.com/checkbox");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500);");

        sleep(1000);

        WebElement buttonExpandAll = driver.findElement(By.xpath("//button[@title='Expand all']"));
        buttonExpandAll.click();

        List<WebElement> checkboxes = driver.findElements(By.xpath("//span[@class='rct-checkbox']"));
        checkboxes.get(0).click();
        sleep(10000);

        for (WebElement el: checkboxes){
            WebElement childEl = driver.findElement(By.xpath("//span[@class='rct-checkbox']/*"));
            boolean isSelected = childEl.getAttribute("class").contains("check");
            Assert.assertTrue(isSelected);
        }
    }

    @Test
    public void testRadioButtons() throws InterruptedException {

        driver.get("https://demoqa.com/radio-button");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500);");

        sleep(1000);

        WebElement radiobuttonYes = driver.findElement(By.xpath("//input[@id='yesRadio']/following-sibling::label"));
        radiobuttonYes.click();

        WebElement textSelectedButton = driver.findElement(By.xpath("//span[@class='text-success']"));

        Assert.assertEquals(textSelectedButton.getText(), "Yes");
    }
}
