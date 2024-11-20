package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class SystemMessageTest extends BaseTest {

    @Test
    public void testSystemMessagePreview() {

        getDriver().findElement(By.xpath("//a[contains(@href, '/manage')]")).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'configureSecurity')]"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//option[text() = 'Plain text']/..")));

        new Select(getDriver().findElement(By.xpath("//option[text() = 'Plain text']/..")))
                .selectByVisibleText("Safe HTML");

        getDriver().findElement(By.name("Apply")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[contains(@href, 'configure')])[1]"))).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name = 'system_message']/.."))).click();
        getDriver().findElement(By.xpath("//div[@class = 'CodeMirror']/div/textarea")).sendKeys("<h2>Hello</h2>");

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@previewendpoint = '/markupFormatter/previewDescription']"))).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class = 'textarea-preview']/h2")).getText(), "Hello");
    }
}
