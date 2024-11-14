package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class AddDescriptionTest extends BaseTest {

    @Test
    public void testDescription() {

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("Hi!");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//*[@id='description']/div[1]")).getText(), "Hi!");
    }
}
