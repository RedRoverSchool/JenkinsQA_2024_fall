package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static java.sql.DriverManager.getDriver;

public class WelcomeTest extends BaseTest {
    @Test
    public void testWelcome() {
        String welcomeStr = getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText();

        Assert.assertEquals(welcomeStr, "Добро пожаловать в Jenkins!");
    }
}
