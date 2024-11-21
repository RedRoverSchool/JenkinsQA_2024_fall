package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BasePage;

public class ConfigurationPage extends BasePage {

    public ConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public HomePage goToDashboard() {
        getDriver().findElement(By.xpath("//a[contains(text(),'Dashboard')]")).click();

        return new HomePage(getDriver());
    }
}
