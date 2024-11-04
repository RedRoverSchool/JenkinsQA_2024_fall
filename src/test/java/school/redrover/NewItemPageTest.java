package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class NewItemPageTest extends BaseTest {

    @Test
    public void testCountItemTypes() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        int size = getDriver().findElements(By.xpath("//div[@id='items']//li")).size();

        Assert.assertEquals(size, 6);
    }
}
