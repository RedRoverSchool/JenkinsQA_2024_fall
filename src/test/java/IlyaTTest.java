import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class IlyaTTest {

    private static WebDriver driver;

    @BeforeMethod
    public static void setUp() {
        ChromeOptions options = new ChromeOptions();

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }


    @Test
    public void testTitleGoogle() {

        String expectedTitle = "Google";

        driver.get("https://www.google.com/");

        String actualTitle = driver.getTitle();

        Assert.assertEquals(actualTitle, expectedTitle);
    }
}
