package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseConfigPage;
import school.redrover.runner.TestUtils;

public class FreestyleConfigPage extends BaseConfigPage<FreestyleConfigPage, FreestyleProjectPage> {

    private final By CODE_MIRROR_FIELD = By.xpath("//div[@class='CodeMirror']");
    private final By SUBMIT_BUTTON = By.xpath("//button[@name = 'Submit']");

    public FreestyleConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected FreestyleProjectPage createProjectPage() {
        return new FreestyleProjectPage(getDriver());
    }

    public FreestyleProjectPage typeDescription (String description) {
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(description);
        getDriver().findElement(SUBMIT_BUTTON).click();

        return new FreestyleProjectPage(getDriver());
    }

    public FreestyleConfigPage addBuildStep(String buildStep) {
        TestUtils.scrollToBottom(getDriver());
        getDriver().findElement(By.xpath("//button[contains(text(),'Add build step')]")).click();
        getDriver().findElement(By.xpath("//button[contains(text(),'%s')]".formatted(buildStep))).click();

        return this;
    }

    public FreestyleConfigPage addExecuteWindowsBatchCommand(String command) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Build Steps')]/.."))).click();
        TestUtils.scrollToBottom(getDriver());
        addBuildStep("Execute Windows batch command");
        getDriver().findElement(By.xpath("//textarea[@name='command']"))
                .sendKeys(command);

        return this;
    }

    public FreestyleProjectPage clickSubmitButton() {
        getDriver().findElement(SUBMIT_BUTTON).click();

        return  new FreestyleProjectPage(getDriver());
    }

    public FreestyleConfigPage clickAddBuildStep() {
        TestUtils.scrollToBottom(getDriver());
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Add build step')]"))).click();

        return this;
    }

    public FreestyleConfigPage selectExecuteShellBuildStep() {
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Execute shell')]"))).click();

        return this;
    }

    public FreestyleConfigPage addExecuteShellCommand(String command) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(CODE_MIRROR_FIELD));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].focus();", getDriver().findElement(CODE_MIRROR_FIELD));
        new Actions(getDriver()).click(getDriver().findElement(CODE_MIRROR_FIELD)).sendKeys(command).perform();

        return this;
    }

    public String getTextExecuteShellTextArea() {
        TestUtils.scrollToBottom(getDriver());

        return (String) ((JavascriptExecutor) getDriver()).executeScript(
                "return arguments[0].CodeMirror.getValue();", getDriver().findElement(CODE_MIRROR_FIELD));
    }
}
