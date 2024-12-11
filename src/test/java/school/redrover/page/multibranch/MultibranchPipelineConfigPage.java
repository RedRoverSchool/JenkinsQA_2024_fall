package school.redrover.page.multibranch;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import school.redrover.page.base.BaseConfigPage;
import school.redrover.runner.TestUtils;

public class MultibranchPipelineConfigPage extends BaseConfigPage<MultibranchPipelineConfigPage, MultibranchPipelineProjectPage> {

    @FindBy(css = "[data-section-id='scan-multibranch-pipeline-triggers']")
    private WebElement scanMultibranchPipelineButton;

    @FindBy(xpath = "//*[contains(@class,'group-start row-set-start')]/div/span[1]")
    private WebElement periodicalScanningCheckbox;

    @FindBy(css = "[name*='interval']")
    private WebElement intervalSelect;

    @FindBy(css = "[value='12h']")
    private WebElement selectedValue;

    public MultibranchPipelineConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected MultibranchPipelineProjectPage createProjectPage() {
        return new MultibranchPipelineProjectPage(getDriver());
    }

    public MultibranchPipelineConfigPage clickScanMultibranchPipelineButton() {
        scanMultibranchPipelineButton.click();

        return this;
    }

    public MultibranchPipelineConfigPage clickPeriodicalScanningCheckbox() {
        getWait2().until(TestUtils.isElementInViewPort(periodicalScanningCheckbox));
        ((JavascriptExecutor) getDriver()).
                executeScript("arguments[0].scrollIntoView({block: 'center'});", periodicalScanningCheckbox);
        periodicalScanningCheckbox.click();

        return this;
    }

    public MultibranchPipelineConfigPage selectingIntervalValue() {
        Select select = new Select(intervalSelect);
        select.selectByValue("12h");

        return this;
    }

    public WebElement getSelectedValue() {
        return selectedValue;
    }
}
