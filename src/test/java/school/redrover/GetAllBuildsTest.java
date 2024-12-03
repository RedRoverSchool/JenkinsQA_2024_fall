package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GetAllBuildsTest extends BaseTest {

    @Test
    public void testVerifyTitleBuildHistoryPage() {
        getDriver().findElement(By.xpath("//a[contains(@href, '/view/all/builds')]")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1")).getText(),
                "Build History of Jenkins");
    }
}