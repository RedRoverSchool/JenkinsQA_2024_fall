import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class ArtyomAvitoTest {

    @Test
    public void testAvito() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.avito.ru/");

        WebElement allCategoriesButton = driver.findElement(By.xpath("//div/div[4]/div/div[1]/div/div/div[3]/div[1]/div/div"));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        allCategoriesButton.click();

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        WebElement transportCategory = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div/div/div/div/div/div/div[2]/div[1]/a/strong"));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        transportCategory.click();

        String redirectURL = driver.getCurrentUrl();

        Assert.assertEquals(redirectURL, "https://www.avito.ru/all/transport");
    }
}
