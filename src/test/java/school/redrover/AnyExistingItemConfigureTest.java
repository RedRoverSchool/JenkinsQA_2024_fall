package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import static school.redrover.runner.TestUtils.newItemsData;

public class AnyExistingItemConfigureTest extends BaseTest {

    @Test
    public void testExistingFolderConfiguration () {
        newItemsData(this, "NewEmptyFolder", "//*[@id='j-add-item-type-nested-projects']/ul/li[1]/div[2]/div");

        getDriver().findElement(
                By.xpath("//*[@id='main-panel']/form/div[1]/section[2]/section/section/div[3]/div[1]/div/button")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.name("_.name"))).sendKeys("TestName");
        getDriver().findElement(By.name("Submit")).click();

        getDriver().navigate().back();

        String valueOfName = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("_.name"))).getAttribute("value");

        Assert.assertEquals(valueOfName, "TestName");
        Assert.assertTrue(getDriver().findElement(
                By.xpath("//*[@id='main-panel']/form/div[1]/section[2]/section/section/div[3]/div[1]/div/div[1]/div[2]/div[4]/div[1]/span/label")).isEnabled());
        Assert.assertTrue(getDriver().findElement(
                By.xpath("//*[@id='main-panel']/form/div[1]/section[2]/section/section/div[3]/div[1]/div/div[1]/div[2]/div[5]/div[1]/span/label")).isEnabled());
    }
}
