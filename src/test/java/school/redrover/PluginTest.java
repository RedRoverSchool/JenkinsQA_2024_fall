package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.List;

public class PluginTest extends BaseTest {

    private void goTiPluginPage() {

        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//a[@href='pluginManager']")))).click();

    }

    @Test
    public void testNumberOfUpdatePlugin() {

        goTiPluginPage();

        List<WebElement> pluginsForUpdate = getDriver().findElements(By.className("app-plugin-manager__categories"));

        Assert.assertEquals(getDriver().findElement(By.xpath(
                "//span[contains(@tooltip, 'updates available')]")).getText() , "" + pluginsForUpdate.size());

    }

    @Test
    public void testNubmerOfAllUpdatePlugin() {

        goTiPluginPage();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//a[@href='/manage/pluginManager/available']")))).click();

        List<WebElement> pluginsForUpdate = getDriver().findElements(By.className("except"));

        Assert.assertTrue(pluginsForUpdate.size() >= Integer.parseInt(getDriver().findElement(By.xpath(
                "//span[contains(@tooltip, 'updates available')]")).getText()));

    }

    @Test
    public void testSearchPluginViaTag() {

        goTiPluginPage();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//a[@href='/manage/pluginManager/installed']")))).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("filter-box"))).sendKeys("Theme Manager");

        Assert.assertTrue(getWait5().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//tr[@data-plugin-name='Theme Manager']"))).isDisplayed());

        Assert.assertEquals(getDriver().findElements(By.cssSelector(".plugin.has-dependents:not(.jenkins-hidden)")).size(), 1);

    }
}
