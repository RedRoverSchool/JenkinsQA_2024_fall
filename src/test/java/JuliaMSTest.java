import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class JuliaMSTest {

    @Test
    public void testLongForm() throws InterruptedException {
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
        WebElement selectmonth = driver.findElement(By.className("react-datepicker__month-select"));
        Select valuemonth = new Select(selectmonth);
        valuemonth.selectByValue("5");
        WebElement selectyear = driver.findElement(By.className("react-datepicker__year-select"));
        Select valueyear = new Select(selectyear);
        valueyear.selectByValue("1994");
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
        final List<String> expectedfields = List.of("Julia Ivanova", "juju@mail.com", "Female",
                "7847985784", "19 June,1994", "English", "Reading", "", "12 Warwickshire Mansions", "");
        List<WebElement> fieldselements = driver.findElements(By.xpath("//tbody/tr/td[2]"));
        List<String> actualfields = fieldselements.stream().map(el -> el.getText()).toList();
        Assert.assertEquals(actualfields, expectedfields);


        driver.quit();
    }

}