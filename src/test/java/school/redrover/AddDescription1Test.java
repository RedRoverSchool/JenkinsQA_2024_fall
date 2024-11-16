package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class AddDescription1Test extends BaseTest {

    @Test
    public void testEditButton() {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys("Hello World!");
        getWait2().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.name("Submit")))).click();

        Assert.assertEquals(getDriver().findElement(By.id("description-link")).getText(), "Edit description");
    }
}
