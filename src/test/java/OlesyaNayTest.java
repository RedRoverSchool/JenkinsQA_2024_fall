import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OlesyaNayTest {

    WebDriver driver;

    @BeforeMethod
    public void startDriver() {
        driver = new ChromeDriver(new ChromeOptions().addArguments("incognito", "start-maximized"));
    }

    @AfterMethod
    public void stopDriver() {
        driver.quit();
    }

    @Test
    public void testRatatype() {
        driver.get("https://www.ratatype.com/");

        driver.findElement(By.xpath("//*[text()='Test your speed']")).click();
        WebElement title = driver.findElement(By.xpath("//*[@class='h2']"));

        Assert.assertEquals(title.getText(), "Typing Certification Test");
    }

    @Test
    public void testRatatypeSelectSpanishLanguage() throws InterruptedException {
        driver.get("https://www.ratatype.com/");

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@data-bs-target='#footerLangModal']")).click();
        WebElement language = driver.findElement(By.xpath("//a[contains(@href,'/es/')]"));
        Thread.sleep(2000);
        language.click();

        WebElement title = driver.findElement(By.xpath("//h1"));
        Assert.assertEquals(title.getText(), "Prácticas de escritura a mecanografía");
    }

}
