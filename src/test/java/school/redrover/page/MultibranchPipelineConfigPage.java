package school.redrover.page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import school.redrover.page.base.BaseConfigPage;
import school.redrover.runner.TestUtils;

public class MultibranchPipelineConfigPage extends BaseConfigPage<MultibranchPipelineConfigPage, MultibranchPipelineProjectPage> {

    public MultibranchPipelineConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected MultibranchPipelineProjectPage createProjectPage() {
        return new MultibranchPipelineProjectPage(getDriver());
    }

    public MultibranchPipelineConfigPage clickScanMultibranchPipelineButton() {
        getDriver().findElement(By.cssSelector("[data-section-id='scan-multibranch-pipeline-triggers']")).click();

        return this;
    }

    public MultibranchPipelineConfigPage clickPeriodicalScanningCheckbox() {
        WebElement periodicalScanningCheckbox =
                getDriver().findElement(By.cssSelector("[name$='PeriodicFolderTrigger'][id='cb0'] + label"));
        getWait2().until(TestUtils.isElementInViewPort(periodicalScanningCheckbox));
        periodicalScanningCheckbox.click();

        return this;
    }

    public MultibranchPipelineConfigPage selectingIntervalValue() {
        Select select = new Select(getDriver().findElement(By.cssSelector("[name*='interval']")));
        select.selectByValue("12h");

        return this;
    }

    public WebElement getSelectedValue() {
        return  getDriver().findElement(By.cssSelector("[value='12h']"));
    }
}
