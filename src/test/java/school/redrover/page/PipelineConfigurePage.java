package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseConfigPage;
import school.redrover.runner.TestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PipelineConfigurePage extends BaseConfigPage<PipelineConfigurePage, PipelineProjectPage> {

    @FindBy(xpath = "//div[@class='task']//span[2]")
    private List<WebElement> sidebarConfigurationOptionList;

    @FindBy(xpath = "//div[@hashelp = 'true']//label[@class='attach-previous ']")
    private List<WebElement> checkboxWithQuestionMarkList;

    @FindBy(xpath = "//div[@hashelp = 'true']//a[@class='jenkins-help-button']")
    private List<WebElement> questionMarkTooltipTextList;

    @FindBy(xpath = "//label[@data-title='Disabled']")
    private WebElement toggleToDisableOrEnableProjectButton;

    @FindBy(css = "textarea[class='ace_text-input']")
    private WebElement scriptTextArea;

    @FindBy(xpath = "//span[@class = 'ace_identifier']")
    private WebElement scriptIdentifier;

    public PipelineConfigurePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected PipelineProjectPage createProjectPage() {
        return new PipelineProjectPage(getDriver());
    }

    public List<String> getSidebarConfigurationOption() {
        return getWait2().until(ExpectedConditions.visibilityOfAllElements(sidebarConfigurationOptionList))
                .stream().map(WebElement::getText).toList();
    }

    public Map<String, String> getCheckboxWithTooltipTextMap() {
        Map<String, String> labelToTooltipTextMap = new HashMap<>();
        for (int i = 0; i < checkboxWithQuestionMarkList.size(); i++) {
            String checkboxText = checkboxWithQuestionMarkList.get(i).getText();
            String tooltipText = questionMarkTooltipTextList.get(i).getAttribute("tooltip");
            labelToTooltipTextMap.put(checkboxText, tooltipText);
        }

        return labelToTooltipTextMap;
    }

    public PipelineConfigurePage clickToggleToDisableOrEnableProject() {
        getWait5().until(ExpectedConditions.elementToBeClickable(toggleToDisableOrEnableProjectButton)).click();

        return this;
    }

    public PipelineConfigurePage addScriptToPipeline(String script) {

        TestUtils.scrollToBottomWithJS(getDriver());
        scriptTextArea.sendKeys(script);

        return this;
    }

    public PipelineConfigurePage enterScriptFromFile(String script) {
        TestUtils.pasteTextWithJavaScript(getDriver(), scriptTextArea, script);

        return this;
    }

    public PipelineConfigurePage pasteScript() {
        TestUtils.scrollToBottomWithJS(getDriver());

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("workflow-editor-1"))).click();
        new Actions(getDriver())
                .keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).build().perform();

        return this;
    }

    public String getScriptText() {
        return getWait2().until(ExpectedConditions.visibilityOf(scriptIdentifier)).getText();
    }

}
