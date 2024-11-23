package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import school.redrover.page.base.BasePage;

public class PluginsPage extends BasePage {

    public PluginsPage(WebDriver driver) {
        super(driver);
    }

    public int getUpdateCountFromIndicator() {
        return Integer.parseInt(getDriver().findElement(By.xpath(
                "//span[contains(@tooltip, 'updates available')]")).getText());
    }

    public int getPluginsCountFromUpdateTable() {
        return getDriver().findElements(By.className("app-plugin-manager__categories")).size();
    }

    public int getCountAvailablePlugins() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//a[@href='/manage/pluginManager/available']")))).click();

        return getDriver().findElements(By.className("except")).size();
    }

    public PluginsPage searchInstalledPlugin(String name) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//a[@href='/manage/pluginManager/installed']")))).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("filter-box"))).sendKeys(name);

        Assert.assertTrue(getWait5().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//tr[@data-plugin-name='" + name + "']"))).isDisplayed());

        return this;

    }

    public int getCountOfPluginsFound() {
        return getDriver().findElements(By.cssSelector(".plugin.has-dependents:not(.jenkins-hidden)")).size();
    }

}
