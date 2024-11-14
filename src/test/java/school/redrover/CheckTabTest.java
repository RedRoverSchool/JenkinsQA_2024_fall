package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CheckTabTest extends BaseTest {

    @Test
    public void testQueue() {

        Assert.assertEquals(getDriver().findElement(By.xpath(
                        "//*[@id=\"buildQueue\"]/div[1]/span")).getText(),
                        "Build Queue");
    }

}

