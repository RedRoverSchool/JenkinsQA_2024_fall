import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertEquals;

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


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeContains(emailField, "border-color", "rgb(255, 0, 0)"));

        String borderColor = emailField.getCssValue("border-color");
        System.out.println("Actual border color: " + borderColor);
        String expectedColor = "rgb(255, 0, 0)";

        assert borderColor.equals(expectedColor) : "Border color is not red as expected!";

        driver.quit();
    }
    @Test
    public void testRadioButtons() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://qa-practice.netlify.app/radiobuttons");

        List<WebElement> radioButtons = driver.findElements(By.className("form-check-input"));
        for (WebElement radioButton : radioButtons) {
            if (!radioButton.isEnabled()) {
                System.out.println("Радио кнопка ID: " + radioButton.getAttribute("id") + " отключена и пропускается.");
                continue;
            }
            radioButton.click();

            String id = radioButton.getAttribute("id");

            Assert.assertTrue(radioButton.isSelected(), "Радио кнопка ID: " + id + " не выбрана.");

            System.out.println("Радио кнопка ID: " + id + " выбрана.");
        }
        driver.quit();
    }

}
