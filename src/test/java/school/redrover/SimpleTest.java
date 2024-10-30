package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class SimpleTest extends BaseTest {

    @Test
    public void testWelcome() {
        String welcomeStr = getDriver().findElement(By.cssSelector("111.empty-state-block > h1")).getText();

        Assert.assertEquals(welcomeStr, "Welcome to Jenkins!");
    }
}
