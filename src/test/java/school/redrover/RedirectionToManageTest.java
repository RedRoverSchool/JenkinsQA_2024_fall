package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class RedirectionToManageTest extends BaseTest {

    @Test
    public void testRedirectionToManage() {
        String urlBeforeRedirection = getDriver().getCurrentUrl();

        getDriver().findElement(By.xpath("//div[@id='tasks']//a[@href='/manage']")).click();
        String currentURL = getDriver().getCurrentUrl();

        Assert.assertEquals(currentURL, urlBeforeRedirection + "manage/");
    }
}
