package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

import java.time.Duration;

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

    public ConfigurationPage enterDescription(String description) {
        getDriver().findElement(By.xpath("//div[contains(text(),'Description')]/following-sibling::div[1]/textarea"))
                .sendKeys(description);

        return this;
    }

    public ConfigurationPage addBuildStep(String buildStep) {
        getDriver().findElement(By.xpath("//button[contains(text(),'Add build step')]")).click();
        getDriver().findElement(By.xpath("//button[contains(text(),'%s')]".formatted(buildStep))).click();

        return this;
    }

    public ConfigurationPage addExecuteWindowsBatchCommand(String command) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Build Steps')]/.."))).click();
        TestUtils.scrollToBottom(getDriver());
        addBuildStep("Execute Windows batch command");
        getDriver().findElement(By.xpath("//textarea[@name='command']"))
                .sendKeys(command);

        return this;
    }
}
