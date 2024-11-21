package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import static school.redrover.runner.TestUtils.newItemsData;

public class AddDescriptionToNewFreestyleProjectTest extends BaseTest {

    @Test
    public void testAddDescription () {
        newItemsData(this,"FreeStyleProjectTest",
                "//*[@id='j-add-item-type-standalone-projects']/ul/li[1]/div[2]/label");

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.name("description"))).sendKeys("It's a simple test project");
        getDriver().findElement(By.name("Submit")).click();

        String addedDescription = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("description"))).getText();

        Assert.assertEquals(addedDescription, "It's a simple test project");
    }
}
