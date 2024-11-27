package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;
import school.redrover.page.base.BaseProjectPage;

public class PipelineProjectPage extends BaseProjectPage {

    public PipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getWarningDisabledMessage() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//form[@id='enable-project']"))).getText().split("\n")[0];
    }

    public String getStatusButtonText() {
        return  getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//form[@id='enable-project']/button[@name='Submit']"))).getText();
    }

    public PipelineProjectPage clickOnBuildNowItemOnSidePanelAndWait() {
        getDriver().findElement(By.cssSelector("a[data-build-success='Build scheduled']")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[tooltip$='> Console Output']")));

        return this;
    }

    public PipelineStagesPage clickOnStagesItemOnSidePanel() {
        getDriver().findElement(By.cssSelector("a[href$='multi-pipeline-graph']")).click();

        return new PipelineStagesPage(getDriver());
    }
}
