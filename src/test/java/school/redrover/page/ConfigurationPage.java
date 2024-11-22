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

    public ConfigurationPage enterName(String name) {
        getDriver().findElement(By.xpath("//div[contains(text(),'Display Name')]/following-sibling::div[1]/input"))
                .sendKeys(name);

        return this;
    }

    public ProjectPage saveConfigurations() {
        getDriver().findElement(By.name("Submit")).click();

        return new ProjectPage(getDriver());
    }
}
