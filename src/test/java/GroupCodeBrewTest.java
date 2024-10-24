import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class GroupCodeBrewTest {

    @Test
    public void testSearchField() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.zennioptical.com/");

        Thread.sleep(1000);

        WebElement searchField = driver.findElement(By.xpath("//*[@id=\"search-input\"]/div/input"));
        searchField.sendKeys("sunglasses");

        Thread.sleep(1000);

        searchField.sendKeys(Keys.ENTER);

        Thread.sleep(1000);

        WebElement item = driver.findElement(By.xpath("//*[@id=\"main-section\"]/div[1]/div/div/h1"));
        String text = item.getText();

        Assert.assertEquals(text, "Affordable Prescription Sunglasses");

        driver.quit();
    }
    @Test
    public void testMainCheckbox() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/checkbox");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement mainCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='rct-checkbox']")));
        mainCheckbox.click();

        List<WebElement> childChekboxes = driver.findElements(By.xpath("//input[@type='checkbox']"));

        for (WebElement checkbox : childChekboxes){

            Assert.assertTrue(checkbox.isSelected());
        }
        driver.quit();
    }

}
