package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Simple002Test extends BaseTest {

    @Test
    public void testIconLogout() {
        WebElement elementIL = getDriver().findElement(By.cssSelector("a[href^='/logout']"));

        Assert.assertNotNull(elementIL);
    }
}
