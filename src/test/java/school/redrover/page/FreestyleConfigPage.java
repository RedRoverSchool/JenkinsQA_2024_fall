package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.page.base.BaseConfigPage;
import school.redrover.runner.TestUtils;

public class FreestyleConfigPage extends BaseConfigPage<FreestyleConfigPage, FreestyleProjectPage> {

    @FindBy(xpath = "//div[@class='CodeMirror']")
    private WebElement executeShellCommandField;

    @FindBy(xpath = "//button[@name = 'Submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement descriptionField;

    @FindBy(xpath = "//button[contains(text(),'Add build step')]")
    private WebElement addBuildStepButton;

    @FindBy(xpath = "//span[contains(text(),'Build Steps')]/..")
    private WebElement buildStepsSidebar;

    @FindBy(xpath = "//textarea[@name='command']")
    private WebElement executeWindowsBatchCommandField;

    @FindBy(xpath = "//button[contains(text(), 'Execute shell')]")
    private WebElement executeShellBuildStep;

    public FreestyleConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected FreestyleProjectPage createProjectPage() {
        return new FreestyleProjectPage(getDriver());
    }

    public FreestyleProjectPage typeDescription (String description) {
        descriptionField.sendKeys(description);
        submitButton.click();

        return new FreestyleProjectPage(getDriver());
    }

    public FreestyleConfigPage addBuildStep(String buildStep) {
        TestUtils.scrollToBottom(getDriver());
        addBuildStepButton.click();
        getDriver().findElement(By.xpath("//button[contains(text(),'%s')]".formatted(buildStep))).click();

        return this;
    }

    public FreestyleConfigPage addExecuteWindowsBatchCommand(String command) {
        getWait10().until(ExpectedConditions.visibilityOf(buildStepsSidebar)).click();
        TestUtils.scrollToBottom(getDriver());
        addBuildStep("Execute Windows batch command");
        executeWindowsBatchCommandField.sendKeys(command);

        return this;
    }

    public FreestyleProjectPage clickSubmitButton() {
        submitButton.click();

        return  new FreestyleProjectPage(getDriver());
    }

    public FreestyleConfigPage clickAddBuildStep() {
        TestUtils.scrollToBottom(getDriver());
        getWait10().until(ExpectedConditions.elementToBeClickable(addBuildStepButton)).click();

        return this;
    }

    public FreestyleConfigPage selectExecuteShellBuildStep() {
        getWait10().until(ExpectedConditions.elementToBeClickable(executeShellBuildStep)).click();

        return this;
    }

    public FreestyleConfigPage addExecuteShellCommand(String command) {
        getWait10().until(ExpectedConditions.visibilityOf(executeShellCommandField));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].focus();", executeShellCommandField);
        new Actions(getDriver()).click(executeShellCommandField).sendKeys(command).perform();

        return this;
    }

    public String getTextExecuteShellTextArea() {
        TestUtils.scrollToBottom(getDriver());

        return (String) ((JavascriptExecutor) getDriver()).executeScript(
                "return arguments[0].CodeMirror.getValue();", executeShellCommandField);
    }

    public FreestyleConfigPage selectDurationCheckbox (String durationPeriod) {
        getDriver().findElement(By.xpath("//label[normalize-space()='Throttle builds']")).click();
        new Select(getDriver().findElement(By.xpath("//select[@name='_.durationName']")))
                .selectByValue(durationPeriod);

        return this;
    }

    public String getTimePeriod() {
        return getDriver().findElement(By.xpath("//*[@name='_.durationName']")).getAttribute("value");
    }
}

