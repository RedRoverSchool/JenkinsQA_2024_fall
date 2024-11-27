package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseConfigPage;
import school.redrover.runner.TestUtils;

public class FolderConfigPage extends BaseConfigPage<FolderConfigPage, FolderProjectPage> {

    public FolderConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected FolderProjectPage createProjectPage() {
        return new FolderProjectPage(getDriver());
    }

    public FolderConfigPage enterName(String name) {
        getDriver().findElement(By.xpath("//div[contains(text(),'Display Name')]/following-sibling::div[1]/input"))
                .sendKeys(name);

        return this;
    }

    public FolderConfigPage addBuildStep(String buildStep) {
        getDriver().findElement(By.xpath("//button[contains(text(),'Add build step')]")).click();
        getDriver().findElement(By.xpath("//button[contains(text(),'%s')]".formatted(buildStep))).click();

        return this;
    }

    public FolderConfigPage addExecuteWindowsBatchCommand(String command) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Build Steps')]/.."))).click();
        TestUtils.scrollToBottom(getDriver());
        addBuildStep("Execute Windows batch command");
        getDriver().findElement(By.xpath("//textarea[@name='command']"))
                .sendKeys(command);

        return this;
    }
}
