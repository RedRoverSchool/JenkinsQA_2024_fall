package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FooterTest extends BaseTest {

    @Test
    public void testFooter() {

        getDriver().findElement(By.xpath("//button[@type='button']")).click();


        getDriver().findElement(By.xpath("//*[@id='tippy-1']/div/div/div/a[1]")).click();


        String hiStr = getDriver().findElement(By.xpath("//*[@id='main-panel']/p")).getText();

        Assert.assertEquals(hiStr, "The leading open source automation server which enables " +
                "developers around the world to reliably build, test, and deploy their software.");
    }
    }