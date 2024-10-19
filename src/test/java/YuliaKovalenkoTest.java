import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class YuliaKovalenkoTest {
    @Test
    public void testCheckRegisterFields() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://parabank.parasoft.com/parabank/register.htm");
        driver.manage().window().fullscreen();

        final List<String> expectedFields = List.of("First Name:", "Last Name:", "Address:",
                "City:", "State:", "Zip Code:", "Phone #:", "SSN:", "Username:", "Password:", "Confirm:");

        List<WebElement> fieldsElements = driver.findElements(By.xpath("//tbody/tr/td/b"));

        List<String> actualFields = fieldsElements.stream().map(el -> el.getText()).toList();

        Assert.assertEquals(actualFields, expectedFields);

        driver.quit();

    }


}
