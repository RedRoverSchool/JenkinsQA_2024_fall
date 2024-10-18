import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class AlexeyVorobyevTest {
@Test
    public void testFieldName() {
    WebDriver driver = new ChromeDriver();
    driver.get("https://demoqa.com/automation-practice-form");

    WebElement fieldName = driver.findElement(By.xpath("//*[@id=\"firstName\"]"));

    fieldName.sendKeys("Alexey");

    assertEquals("Alexey", fieldName.getAttribute("value"), "The name field should contain 'Alexey'");

    driver.quit();
}
}
