package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GetAllBuildsTest extends BaseTest {

    @Test
    public void testButtonBuilds() {
        getDriver().findElement(By.xpath("//div[@class='task '][2]/span/a")).click();

        String title = getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__content']")).getText();

        Assert.assertEquals(title, "История сборок Jenkins");
    }
}