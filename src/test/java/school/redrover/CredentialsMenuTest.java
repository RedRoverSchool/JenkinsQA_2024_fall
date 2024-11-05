package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class CredentialsMenuTest extends BaseTest {

    @Test

    public void NavigateCredentialsMenuTest() {

        getDriver().findElement(By.className("model-link")).click();
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/user/admin/credentials']")))
                .click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(),"Credentials");






    }


}
