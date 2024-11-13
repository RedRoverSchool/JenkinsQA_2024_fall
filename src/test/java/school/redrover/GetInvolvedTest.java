package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GetInvolvedTest extends BaseTest {

    @Ignore
    @Test

    public void testConnect() {

        getDriver().findElement(By.xpath("//button[@type='button']")).click();
        getDriver().findElement(By.xpath( "//*[@id='tippy-1']/div/div/div/a[2]")).click();
        String textConnect = getDriver().findElement(By.xpath("/html/body/div/div/a[1]/h3")).getText();

        Assert.assertEquals(textConnect, "Connect");
    }
}
