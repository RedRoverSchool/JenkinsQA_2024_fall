package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class GetStatusUserTest extends BaseTest {

    @Test
    public void testGetStatusPage() {
        Actions actions = new Actions(getDriver());
        WebDriverWait wait =new WebDriverWait(getDriver(), Duration.ofSeconds(3));

        WebElement dropdown = getDriver().findElement(By.xpath("//button[contains(@data-href,'http://localhost:8080/user/admin')]"));
        actions.moveToElement(dropdown).click().perform();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[contains(@href,'/user/admin/configure')]")
                )).click();

        getDriver().findElement(By.xpath("//span[contains(@class,'task-link-wrapper')]/a[@href='/user/admin/']")).click();

        String status = getDriver().findElement(By.xpath("//div[@id='main-panel']/div[3]")).getText();

        Assert.assertEquals(status, "Jenkins User ID: admin");
    }
}
