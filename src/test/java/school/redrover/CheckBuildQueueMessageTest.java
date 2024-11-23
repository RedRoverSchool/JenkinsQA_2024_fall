package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CheckBuildQueueMessageTest extends BaseTest {

    @Test
    public void testCheckBuildQueueMessageTest() {

        WebElement QueueMessageArrow =  getDriver().findElement(By.className("widget-refresh-reference"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(QueueMessageArrow).click().perform();

        String BuildQueueMessage = getDriver().findElement(By.xpath("//td[text()= 'No builds in the queue.' ]")).getText();

        Assert.assertEquals(BuildQueueMessage, "No builds in the queue.");


    }

}
