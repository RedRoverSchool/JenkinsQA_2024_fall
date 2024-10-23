import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class GroupLeadsAndRoversTest {
    private WebDriver driver;

    @BeforeMethod
    private void initDriver() throws InterruptedException {
        this.driver = new ChromeDriver();
        driver.get("https://www.google.com");
        Thread.sleep(1000);
    }

    @AfterMethod
    private void quitDriver() {
        driver.quit();
    }

    @Test
    public void testSearchBoxIsPresent() {
        WebElement searchBox = driver.findElement(By.name("q"));
        Assert.assertTrue(searchBox.isDisplayed(), "Search box should be displayed");
    }

    @Test
    public void testGoogleLogoIsDisplayed() {
        WebElement googleLogo = driver.findElement(By.xpath("//img[@alt='Google']"));
        Assert.assertTrue(googleLogo.isDisplayed(), "Google logo should be displayed");
    }

    @Test
    public void formAuthentication() {

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get("https://the-internet.herokuapp.com/");

        WebElement formAuthentication = driver.findElement(By.xpath("//a[@href='/login']"));
        formAuthentication.click();

        WebElement loginTitle = driver.findElement(By.xpath("//*[@id=\"content\"]/div/h2"));
        Assert.assertEquals(loginTitle.getText(), "Login Page");
    }

    @Test
    public void corectPassword() {

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get("https://the-internet.herokuapp.com/login");

        WebElement userName = driver.findElement(By.id("username"));
        WebElement userPassword = driver.findElement(By.id("password"));
        WebElement buttonLogin = driver.findElement(By.cssSelector("button[type='submit']"));

        userName.sendKeys("tomsmith");
        userPassword.sendKeys("SuperSecretPassword!");
        buttonLogin.click();

        String expectedUrl = "https://the-internet.herokuapp.com/secure";
        String actualUrl = driver.getCurrentUrl();

        Assert.assertEquals(expectedUrl, actualUrl);
    }
}