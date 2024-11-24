package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class PipelineProjectPage extends BasePage {
    public PipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    public HomePage returnToHomePage() {
        getDriver().findElement(By.id("jenkins-home-link")).click();

        return new HomePage(getDriver());
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
