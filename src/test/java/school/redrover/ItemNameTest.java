package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class ItemNameTest extends BaseTest {

    @Test
    public void testEmptyItemName() throws InterruptedException {

        getDriver().findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[1]")).click();

        String errorMessage = getDriver().findElement(By.id("itemname-required")).getText();

        Assert.assertEquals(errorMessage, "» This field cannot be empty, please enter a valid name");
    }
}
