package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class NegativeNewItemTest extends BaseTest {

    @Test
    public void testNegativeNewItem() throws InterruptedException {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        List<WebElement> items = getDriver().findElements(By.xpath("//div[@class='desc']"));

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0, 500);");

        String expectedMessage = "This field cannot be empty, please enter a valid name";

        for (WebElement item : items) {
            item.click();

            WebElement errorMessage = getDriver().findElement(By.xpath("//div[@id='itemname-required']"));
            String actualErrorMessage = errorMessage.getText();

            Assert.assertTrue(actualErrorMessage.contains(expectedMessage), "This field cannot be empty, please enter a valid name");

            Thread.sleep(1000);
        }
    }
}
