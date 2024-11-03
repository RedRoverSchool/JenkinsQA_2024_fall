package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FooterTest extends BaseTest{

    @Test

    public void testCheckVersion()  {
        getDriver().findElement(By.xpath("//*[@id='jenkins']/footer/div/div[2]/button")).click();
        getDriver().findElement(By.xpath("//a[@href='/manage/about']")).click();

        Assert.assertEquals(getDriver().findElement(By
                .xpath("//p[@class='app-about-version']")).getText(), "Version 2.462.3");
          }
}
