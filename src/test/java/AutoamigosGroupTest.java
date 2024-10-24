import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
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

    @Test(description = "Практика заполнение Text Box https://demoqa.com/text-box ")
    public void testTextBox() throws InterruptedException {

        driver.get("https://demoqa.com/text-box");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

        WebElement fullName = driver.findElement(By.xpath("//*[@id='userName']"));
        fullName.sendKeys("Max");

        WebElement email = driver.findElement(By.xpath("//*[@id='userEmail']"));
        email.sendKeys("max@mail.ru");

        WebElement currentAddress = driver.findElement(By.xpath("//*[@id='currentAddress']"));
        currentAddress.sendKeys("Balti, Index:3120, Republic of Moldova, str. Alecu Ruso, ap. 36");

        WebElement permanentAddress = driver.findElement(By.xpath("//*[@id='permanentAddress']"));
        permanentAddress.sendKeys("Balti, Index:3120, Republic of Moldova, str. Alecu Ruso, ap. 36");

        // Прокрутка к элементу, найденному по XPath
        WebElement element = driver.findElement(By.xpath("//*[@id='submit']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);

        sleep(500);

        WebElement buttonSubmit = driver.findElement(By.xpath("//*[@id='submit']"));
        buttonSubmit.click();

        sleep(500);

        WebElement fieldName = driver.findElement(By.xpath("//*[@id='name']"));
        String name = fieldName.getText();
        Assert.assertEquals(name, "Name:Max");

        WebElement fieldEmail = driver.findElement(By.xpath("//*[@id='email']"));
        String inputEmail = fieldEmail.getText();
        Assert.assertEquals(inputEmail, "Email:max@mail.ru");

        WebElement fieldCurrentAddress = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div[2]/form/div[6]/div/p[3]"));
        String inputCurrentAddress = fieldCurrentAddress.getText();
        Assert.assertEquals(inputCurrentAddress,"Current Address :Balti, Index:3120, Republic of Moldova, str. Alecu Ruso, ap. 36");

        WebElement fieldPermanentAddress = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div[2]/form/div[6]/div/p[4]"));
        String inputPermanentAddress = fieldPermanentAddress.getText();
        Assert.assertEquals(inputPermanentAddress, "Permananet Address :Balti, Index:3120, Republic of Moldova, str. Alecu Ruso, ap. 36");
    }

    @Test (description = "Практика работы с check box https://demoqa.com/checkbox")

    public void checkBox() throws InterruptedException {

        driver.get("https://demoqa.com/checkbox");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));

        WebElement toggleButtonHomeOn = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li/span/button"));
        toggleButtonHomeOn.click();
        sleep(5000);

        WebElement toggleButtonDesktopOn = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li/ol/li[1]/span/button"));
        toggleButtonDesktopOn.click();
        sleep(500);

        WebElement checkBoxNotesOn = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li/ol/li[1]/ol/li[1]/span/label/span[1]"));
        checkBoxNotesOn.click();
        sleep(500);

        WebElement checkBoxCommandsOn = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li/ol/li[1]/ol/li[2]/span/label/span[1]"));
        checkBoxCommandsOn.click();
        sleep(500);

        WebElement checkBoxCommandsOff = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li/ol/li[1]/ol/li[2]/span/label/span[1]"));
        checkBoxCommandsOff.click();
        sleep(500);

        WebElement checkBoxNotesOff = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li/ol/li[1]/ol/li[1]/span/label/span[1]"));
        checkBoxNotesOff.click();
        sleep(500);

        WebElement toggleButtonDesktopOff = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li/ol/li[1]/span/button"));
        toggleButtonDesktopOff.click();
        sleep(500);

        WebElement toggleButtonOff = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li/span/button"));
        toggleButtonOff.click();
        sleep(500);

        WebElement openAllFolders = driver.findElement(By.xpath("//*[@id='tree-node']/div/button[1]"));
        openAllFolders.click();
        sleep(500);

        WebElement closeAllFolders = driver.findElement(By.xpath("//*[@id='tree-node']/div/button[2]"));
        closeAllFolders.click();
        sleep(500);

    }
}
