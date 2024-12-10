package school.redrover.page.pipeline;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

public class PipelineSyntaxPage extends BasePage {

    @FindBy(xpath = "//select[@class='jenkins-select__input dropdownList']")
    private WebElement sampleStepSelect;

    @FindBy(id = "generatePipelineScript")
    private WebElement generatePipelineScriptButton;

    @FindBy(xpath ="//button[@tooltip = 'Copy']")
    private WebElement copyButton;

    public PipelineSyntaxPage(WebDriver driver) { super(driver); }

    public String getBreadCrumb(String projectName) {
        return getDriver().findElement(By.xpath("//a[@href = '/job/%s/pipeline-syntax/']".formatted(projectName))).getText();
    }

    public PipelineSyntaxPage selectNewStep(String value) {
        new Select(sampleStepSelect).selectByValue(value);

        return this;
    }

    public PipelineSyntaxPage clickGeneratePipelineScript() {
        getWait5().until(ExpectedConditions.visibilityOf(generatePipelineScriptButton)).click();

        return this;
    }

    public PipelineSyntaxPage clickCopy() {
        TestUtils.scrollToBottomWithJS(getDriver());
        getWait5().until(ExpectedConditions.visibilityOf(copyButton)).click();

        return this;
    }

    public String getTitleOfSelectedScript(String selectName) {
        String value = getDriver().findElement(By.xpath("//option[@value = '%s']".formatted(selectName))).getText();
        String result = value.split(":")[0].trim();

        return getDriver().findElement(By.xpath("//div[contains(text(),'%s')]".formatted(result))).getText();
    }
}
