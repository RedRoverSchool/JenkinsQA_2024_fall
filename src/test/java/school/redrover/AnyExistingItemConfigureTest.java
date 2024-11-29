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

        getDriver().findElement(By.name("Submit")).click();

        getDriver().navigate().back();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("_.displayNameOrNull"))).sendKeys("TestName");
        getDriver().findElement(By.name("Submit")).click();

        String valueOfName = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='main-panel']/h1"))).getText();

        Assert.assertEquals(valueOfName, "TestName");
    }
}
