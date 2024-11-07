package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GetAllBuildsTest extends BaseTest {

    @Test
    public void testButtonBuilds() {
        getDriver().findElement(By.xpath("//a[contains(@href, '/view/all/builds')]")).click();

        String header = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(header, "Build History of Jenkins");
    }
}