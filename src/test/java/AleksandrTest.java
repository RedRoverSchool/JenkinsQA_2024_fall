import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

public class AleksandrTest {
    WebDriver driver;
    String BASE_URL = "https://foxhound87.github.io/mobx-react-form-demo";

    By locatorUserName = By.name("username");
    By locatorTerms = By.name("terms");
    By locatorSubmit = By.xpath("*//form/*/button");

    @BeforeTest
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
    }

    @AfterTest
    public void tearDown() {
        driver.close();
    }

    @Test
    public void testReactRegisterForm() throws InterruptedException {
        driver.get(BASE_URL);
        WebElement userName = driver.findElement(locatorUserName);
        clearInput(userName);
        userName.sendKeys("MyUserName");
        WebElement terms = driver.findElement(locatorTerms);
        terms.click();
        Thread.sleep(2000);
        WebElement submit = driver.findElement(locatorSubmit);
        Assert.assertEquals(submit.getText(), "Submit");
        submit.click();
        Thread.sleep(5000);
        Assert.assertTrue(isAlertPresent());
        Assert.assertEquals(driver.switchTo().alert().getText(), "### see console");
        driver.switchTo().alert().accept();
    }

    void clearInput(WebElement element) {
        element.sendKeys(Keys.CONTROL + "a" + Keys.BACK_SPACE);
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException Ex) {
            return false;
        }
    }
}
