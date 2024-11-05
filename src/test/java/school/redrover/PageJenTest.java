package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class PageJenTest extends BaseTest {
    @Test
    public void PageJTest() {
        String ItemElement = getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).getText();
        Assert.assertEquals(ItemElement, "New Item");
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
    }


}