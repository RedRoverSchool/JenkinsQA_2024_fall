import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

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
@Test
    public void testInvalidEmailInput() {
    WebDriver driver = new ChromeDriver();
    driver.get("https://demoqa.com/text-box");



    WebElement emailField = driver.findElement(By.xpath("//*[@id=\"userEmail\"]"));
    emailField.sendKeys("negativetest");

    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollBy(0, 500);");


    WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"submit\"]"));
    submitButton.click();
;

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    wait.until(ExpectedConditions.attributeContains(emailField, "border-color", "rgb(255, 0, 0)"));

    String borderColor = emailField.getCssValue("border-color");
    System.out.println("Actual border color: " + borderColor);
    String expectedColor = "rgb(255, 0, 0)";

    assert borderColor.equals(expectedColor) : "Border color is not red as expected!";

    driver.quit();


}
}
