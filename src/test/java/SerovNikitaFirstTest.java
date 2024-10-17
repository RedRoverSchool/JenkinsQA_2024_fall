import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SerovNikitaFirstTest {

    WebDriver driver;

    @BeforeMethod
    public void setDriver() {
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void closeBrowser() {
        driver.quit();
    }

    @Test
    public void testRemove() {
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");

        WebElement addElement = driver.findElement(By.xpath("//*[@id=\"content\"]/div/button"));
        addElement.click();

        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"elements\"]/button"))
                .getText(), "Delete");

        driver.findElement(By.xpath("//*[@id=\"elements\"]/button")).click();
    }
    @Test
    public void selectCheckboxes() {
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        WebElement checkbox1 = driver.findElement(By.xpath("//*[@id=\"checkboxes\"]/input[1]"));
        WebElement checkbox2 = driver.findElement(By.xpath("//*[@id=\"checkboxes\"]/input[2]"));

        checkbox2.isSelected();

        checkbox1.click();

        checkbox1.isSelected();
    }
}
