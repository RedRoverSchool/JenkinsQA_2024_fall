package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;

public class NegativeNewItemTest extends BaseTest {

    @Test
    public void testCreateNewItemWithEmptyNameField() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        List<WebElement> items = getDriver().findElements(By.xpath("//div[@class='desc']"));

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0, 500);");

        String expectedMessage = "This field cannot be empty, please enter a valid name";

        for (WebElement item : items) {
            item.click();

            WebElement errorMessage = getDriver().findElement(By.xpath("//div[@id='itemname-required']"));
            String actualErrorMessage = errorMessage.getText();

            WebDriverWait webDriverWait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
            webDriverWait.until(ExpectedConditions.elementToBeClickable(item));

            Assert.assertTrue(actualErrorMessage.contains(expectedMessage), "This field cannot be empty, please enter a valid name");
        }
    }
}
