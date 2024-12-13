package school.redrover.page.freestyle;

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

    @FindBy(xpath = "//label[normalize-space()='Throttle builds']")
    private WebElement throttleBuildsCheckbox;

    @FindBy(xpath = "//select[@name='_.durationName']")
    private WebElement timePeriodSelect;

    @FindBy(xpath = "//*[@id='toggle-switch-enable-disable-project']/label")
    private WebElement enableDisableToggle;

    public FreestyleConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected FreestyleProjectPage createProjectPage() {
        return new FreestyleProjectPage(getDriver());
    }

    public FreestyleConfigPage addBuildStep(String buildStep) {
        TestUtils.scrollToBottomWithJS(getDriver());
        addBuildStepButton.click();
        getDriver().findElement(By.xpath("//button[contains(text(),'%s')]".formatted(buildStep))).click();

        return this;
    }

    public FreestyleConfigPage addExecuteWindowsBatchCommand(String command) {
        getWait10().until(ExpectedConditions.visibilityOf(buildStepsSidebar)).click();
        TestUtils.scrollToBottomWithJS(getDriver());
        addBuildStep("Execute Windows batch command");
        executeWindowsBatchCommandField.sendKeys(command);

        return this;
    }

    public FreestyleConfigPage clickAddBuildStep() {
        TestUtils.scrollToBottomWithJS(getDriver());
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
        TestUtils.scrollToBottomWithJS(getDriver());

        return (String) ((JavascriptExecutor) getDriver()).executeScript(
                "return arguments[0].CodeMirror.getValue();", executeShellCommandField);
    }

    public FreestyleConfigPage selectDurationCheckbox (String durationPeriod) {
        throttleBuildsCheckbox.click();
        new Select(timePeriodSelect).selectByValue(durationPeriod);

        return this;
    }

    public String getTimePeriod() {
        return timePeriodSelect.getAttribute("value");
    }

    public boolean getEnablingCurrentState() {
        return getWait10().until(ExpectedConditions.visibilityOf(enableDisableToggle)).isEnabled();
    }

    public FreestyleProjectPage changeEnablingState() {
        getWait10().until(ExpectedConditions.visibilityOf(enableDisableToggle)).click();
        submitButton.click();

        return new FreestyleProjectPage(getDriver());
    }
}

