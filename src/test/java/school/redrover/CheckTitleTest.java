package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CheckTitleTest extends BaseTest {

    @Test
    public void CheckTitle() {

        String title = getDriver().findElement(By.xpath("(//h1[contains(text(),'Добро пожаловать в Jenkins!')])[1]")).getText();

        Assert.assertEquals(title,"Добро пожаловать в Jenkins!");
    }
}
