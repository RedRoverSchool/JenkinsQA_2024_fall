package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.assertTrue;

public class testJenkinsWelcome extends BaseTest {
    @Test
    public void testWelcome() {
        String welcomeStr = getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText();

        Assert.assertEquals(welcomeStr, "Добро пожаловать в Jenkins!");
    }
}