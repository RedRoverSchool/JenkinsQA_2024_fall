package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class Simple25Test extends BaseTest {

    @Test
    public  void testCreateNewItem() {

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys("New_item");

        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[2]/div[2]/div")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@id='jenkins-name-icon']")).click();

        Assert.assertNotNull(
                getDriver().findElement(By.xpath("//*[@id='job_New_item']/td[3]/a/span")));
    }
}
