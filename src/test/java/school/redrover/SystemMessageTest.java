package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class SystemMessageTest extends BaseTest {

    @Test
    public void testSystemMessagePreview() throws InterruptedException {

        getDriver().findElement(By.xpath("//a[contains(@href, '/manage')]")).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'configureSecurity')]"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//option[text() = 'Plain text']/..")));

        new Select(getDriver().findElement(By.xpath("//option[text() = 'Plain text']/..")))
                .selectByVisibleText("Safe HTML");

        getDriver().findElement(By.name("Apply")).click();
        getDriver().findElement(By.name("Submit")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[contains(@href, 'configure')])[1]"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name = 'system_message']/.."))).click();
        Thread.sleep(3000);

        //getDriver().findElement(By.xpath("//textarea[@name = 'system_message']")).sendKeys("<h2>Hello</h2>");

        WebElement textarea = getDriver().findElement(By.xpath("//textarea[@name = 'system_message']"));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].value = arguments[1];", textarea, "<h2>Hello</h2>");


        Thread.sleep(3000);
    }

}
