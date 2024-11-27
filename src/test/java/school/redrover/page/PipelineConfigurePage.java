package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PipelineConfigurePage extends BasePage {

    public PipelineConfigurePage(WebDriver driver) {
        super(driver);
    }

    public PipelineProjectPage clickSaveButton() {
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        return new PipelineProjectPage(getDriver());
    }

    public List<String> getSidebarConfigurationOption() {
        return getWait2().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[@class='task']//span[2]"))).stream().map(WebElement::getText).toList();
    }

    public Map<String, String> getCheckboxWithTooltipTextMap() {
        List<WebElement> checkboxWithQuestionMarkList = getDriver().findElements(
                By.xpath("//div[@hashelp = 'true']//label[@class='attach-previous ']"));
        List<WebElement> questionMarkTooltipTextList = getDriver().findElements(
                By.xpath("//div[@hashelp = 'true']//a[@class='jenkins-help-button']"));

        Map<String, String> labelToTooltipTextMap = new HashMap<>();
        for (int i = 0; i < checkboxWithQuestionMarkList.size(); i++) {
            String checkboxText = checkboxWithQuestionMarkList.get(i).getText();
            String tooltipText = questionMarkTooltipTextList.get(i).getAttribute("tooltip");
            labelToTooltipTextMap.put(checkboxText, tooltipText);
        }

        return labelToTooltipTextMap;
    }

    public PipelineConfigurePage clickToggleToDisableOrEnableProject() {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[@data-title='Disabled']"))).click();

        return this;
    }

    public PipelineConfigurePage addScriptToPipeline(String script) {

        TestUtils.scrollToBottom(getDriver());
        getDriver().findElement(By.cssSelector("textarea[class='ace_text-input']")).sendKeys(script);

        return this;
    }
}
