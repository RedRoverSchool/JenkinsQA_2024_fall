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

    public String getWarningDisabledMessage() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//form[@id='enable-project']"))).getText().split("\n")[0];
    }

    public String getStatusButtonText() {
        return  getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//form[@id='enable-project']/button[@name='Submit']"))).getText();
    }
}
