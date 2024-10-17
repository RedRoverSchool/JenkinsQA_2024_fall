import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DianaKTest {
    @Test
    public void testGarfild() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://garfield.by/?utm_source=google&utm_medium=cpc&utm_campaign=17619287198&utm_content=&utm_term=&gad_source=1&gclid=CjwKCAjw9p24BhB_EiwA8ID5BlycoY_gj8tdIObtHyJdlX0EleOFbNVrF1vokbRJm7b6uhC1n2gh9hoCTCwQAvD_BwE");

        WebElement menu = driver.findElement(By.className("icon-open"));
        menu.click();
        Thread.sleep(500);

        WebElement puppies = driver.findElement(By.partialLinkText("Для щенков"));
        puppies.click();

        WebElement food = driver.findElement(By.partialLinkText("Сухие корма"));
        food.click();
        Thread.sleep(500);

        WebElement grandorf = driver.findElement(By.xpath("//div[@class='manufacturer__default']//p[@class='catalog-filter-content-checkbox__label'][normalize-space()='Grandorf']"));
        grandorf.click();
        Thread.sleep(1000);

        WebElement filter = driver.findElement(By.id("line_arrFilterCat_124_3205278765"));
        Assert.assertEquals(filter.getText(), "Grandorf");

        driver.quit();
    }
}
