package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static school.redrover.runner.TestUtils.newItemsData;

public class FreeStyleProjectEnablingTest extends BaseTest {

    private void clickOnSave () {
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testDefaultState () {
        newItemsData(this,"FreeStyleProjectTest",
                "//*[@id='j-add-item-type-standalone-projects']/ul/li[1]/div[2]/label");

        boolean defaultState = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='toggle-switch-enable-disable-project']/label"))).isEnabled();

        Assert.assertTrue(defaultState);
    }

    @Test(dependsOnMethods = "testDefaultState")
    public void testDisableEnabled () {
        getDriver().navigate().back();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='toggle-switch-enable-disable-project']/label"))).click();
        clickOnSave();

        String indicatorText = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("enable-project"))).getText();

        Assert.assertEquals(indicatorText, "This project is currently disabled\n" +
                "Enable");
    }

    @Test (dependsOnMethods = "testDisableEnabled")
    public void testEnableWithIndicator () {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='job_FreeStyleProjectTest']/td[3]/a/span"))).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(6) > span > a")).click();

        boolean isItEnabled = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='toggle-switch-enable-disable-project']/label"))).isEnabled();

        Assert.assertTrue(isItEnabled);
    }
}
