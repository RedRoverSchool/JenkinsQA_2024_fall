import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

}