package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.List;

public class PluginTest extends BaseTest {

    @Test
    public void testNumberOfUpdatePlugin() {

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//a[@href='pluginManager']")))).click();

        List<WebElement> pluginsForUpdate = getDriver().findElements(By.className("app-plugin-manager__categories"));

        Assert.assertEquals(getDriver().findElement(By.xpath(
                "//span[contains(@tooltip, 'updates available')]")).getText() , "" + pluginsForUpdate.size());

    }

    @Test
    public void testNubmerOfAllUpdatePlugin() {

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//a[@href='pluginManager']")))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//a[@href='/manage/pluginManager/available']")))).click();

        List<WebElement> pluginsForUpdate = getDriver().findElements(By.className("except"));

        Assert.assertTrue(pluginsForUpdate.size() >= Integer.parseInt(getDriver().findElement(By.xpath(
                "//span[contains(@tooltip, 'updates available')]")).getText()));

    }

    @Test
    public void testSearchPluginViaTag() {

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//a[@href='pluginManager']")))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//a[@href='/manage/pluginManager/installed']")))).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("filter-box"))).sendKeys("Theme Manager");

        Assert.assertTrue(getWait5().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//tr[@data-plugin-name='Theme Manager']"))).isDisplayed());

        Assert.assertFalse(getDriver().findElements(By.xpath("//tr[@data-plugin-name='Theme Manager']")).isEmpty());

    }
}
