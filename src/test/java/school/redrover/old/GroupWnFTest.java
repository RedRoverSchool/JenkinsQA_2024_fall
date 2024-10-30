package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GroupWnFTest extends BaseTest {

    @Test
    public void welcomeMessage() {
        WebElement welcomeMessage = getDriver().findElement(By.className("empty-state-block"));
        Assert.assertEquals(welcomeMessage.getText(), "Welcome to Jenkins!");
    }
}