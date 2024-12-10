package school.redrover.page.systemConfiguration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import school.redrover.page.base.BasePage;

import java.util.List;

public class PluginsPage extends BasePage {

    @FindBy(xpath = "//span[contains(@tooltip, 'updates available')]")
    private WebElement numberOfUpdatesAvailable;

    @FindBy(className = "app-plugin-manager__categories")
    private List<WebElement> updatesList;

    @FindBy(xpath = "//a[@href='/manage/pluginManager/available']")
    private WebElement availablePluginsSidebar;

    @FindBy(className = "except")
    private List<WebElement> availablePluginsList;

    @FindBy(xpath = "//a[@href='/manage/pluginManager/installed']")
    private WebElement installedPluginsSidebar;

    @FindBy(id = "filter-box")
    private WebElement searchField;

    @FindBy(css = ".plugin.has-dependents:not(.jenkins-hidden)")
    private List<WebElement> installedPluginsList;

    public PluginsPage(WebDriver driver) {
        super(driver);
    }

    public int getUpdateCountFromIndicator() {
        return Integer.parseInt(numberOfUpdatesAvailable.getText());
    }

    public int getPluginsCountFromUpdateTable() {
        return updatesList.size();
    }

    public int getCountAvailablePlugins() {
        getWait10().until(ExpectedConditions.visibilityOf(availablePluginsSidebar)).click();

        return availablePluginsList.size();
    }

    public PluginsPage searchInstalledPlugin(String name) {
        getWait10().until(ExpectedConditions.visibilityOf(installedPluginsSidebar)).click();
        getWait5().until(ExpectedConditions.visibilityOf(searchField)).sendKeys(name);
        Assert.assertTrue(getWait5().until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//tr[@data-plugin-name='" + name + "']"))).isDisplayed());

        return this;
    }

    public int getCountOfPluginsFound() {
        return installedPluginsList.size();
    }
}
