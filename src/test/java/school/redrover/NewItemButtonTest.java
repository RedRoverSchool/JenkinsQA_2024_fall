package school.redrover;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItemButtonTest extends BaseTest {
    @Test
    public void testNewItemButton() {
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();

    }

}
