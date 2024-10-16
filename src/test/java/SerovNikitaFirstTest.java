import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SerovNikitaFirstTest {


    @Test
    public void testRemove() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");

        WebElement addElement = driver.findElement(By.xpath("//*[@id=\"content\"]/div/button"));
        addElement.click();

        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"elements\"]/button"))
                .getText(), "Delete");

        driver.findElement(By.xpath("//*[@id=\"elements\"]/button")).click();
    }
}
