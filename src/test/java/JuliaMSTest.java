import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JuliaMSTest {

    @Test
    void fillShortFormTest() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/text-box");
        driver.findElement(By.id("userName")).sendKeys("Julia");
        driver.findElement(By.id("userEmail")).sendKeys("julia@mail.com");
        driver.findElement(By.id("currentAddress")).sendKeys("12 Warwickshire Mansions");
        driver.findElement(By.id("permanentAddress")).sendKeys("12 Warwickshire Mansions");
        driver.findElement(By.id("submit")).click();

        driver.quit();
    }

    @Test
    void fillLongFormTest() throws InterruptedException {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.get("https://demoqa.com/automation-practice-form");

        driver.findElement(By.id("firstName")).sendKeys("Julia");
        driver.findElement(By.id("lastName")).sendKeys("Ivanova");
        driver.findElement(By.id("userEmail")).sendKeys("juju@mail.com");
        driver.findElement(By.xpath ("//*[contains(text(),'Female')]")).click();
        driver.findElement(By.id("userNumber")).sendKeys("78479857847");
        driver.findElement(By.id("dateOfBirthInput")).click();
        driver.findElement(By.className("react-datepicker__month-select").xpath("//*[contains(text(),'June')]")).click();
        driver.findElement(By.className("react-datepicker__year-select").xpath("//*[contains(text(),'1994')]")).click();
        driver.findElement(By.className("react-datepicker__day--019")).click();

        WebElement subjects = driver.findElement(By.id("subjectsInput"));
        subjects.sendKeys("English");
        subjects.sendKeys(Keys.ENTER);

        driver.findElement(By.xpath ("//*[contains(text(),'Reading')]")).click();
        driver.findElement(By.id("currentAddress")).sendKeys("12 Warwickshire Mansions");

        WebElement pagedown = driver.findElement(By.id("currentAddress"));
        pagedown.sendKeys(Keys.PAGE_DOWN);

        Thread.sleep(1000);

        driver.findElement(By.id("submit")).click();

        WebElement finaltext = driver.findElement(By.id("example-modal-sizes-title-lg"));
        Assert.assertEquals(finaltext.getText(), "Thanks for submitting the form");

        driver.quit();
    }

}