package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class SystemButtonTest extends BaseTest {

    @Test
    public void testSystemButton() throws InterruptedException {

        getDriver().findElement(By.xpath("//a[@href=\"/manage\"]")).click();
        getDriver().findElement(By.xpath("//a[@href='configure']")).click();

        String currentUrl = getDriver().getCurrentUrl();
        String getSystemText = getDriver().findElement(By.xpath("//div[@id=\"main-panel\"]")).getText().toLowerCase();

        Assert.assertEquals(currentUrl, "http://localhost:8080/manage/configure", "Current URL does not meet requirement. ");
        Assert.assertTrue(getSystemText.contains("system"), "Current page does not contain the word 'System'");
    }
}
