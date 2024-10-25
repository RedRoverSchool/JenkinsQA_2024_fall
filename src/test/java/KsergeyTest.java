import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

public class KsergeyTest {

    WebDriver driver=new ChromeDriver();

    @Test
    public void testSearch() throws InterruptedException  {
        // declaration and instantiation of objects/variables
        WebDriver driver=new ChromeDriver();

        // Launch website
        driver.get("https://www.google.com/");
        sleep(1000);

        // Click on the search text box and send value
        driver.findElement(By.id("APjFqb")).sendKeys("Java WebDriver testing");
        sleep(1000);

        // Click on the search button
        driver.findElement(By.name("btnK")).click();
        sleep(1000);
        driver. quit();
    }
}