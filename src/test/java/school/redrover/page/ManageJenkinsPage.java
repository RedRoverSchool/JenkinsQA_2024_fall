package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class ManageJenkinsPage extends BasePage {

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public PluginsPage openPluginsPage() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//a[@href='pluginManager']")))).click();

        return new PluginsPage(getDriver());
    }

}
