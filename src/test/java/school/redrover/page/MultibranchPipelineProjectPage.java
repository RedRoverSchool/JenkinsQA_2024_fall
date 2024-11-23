package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class MultibranchPipelineProjectPage extends BasePage {

    public MultibranchPipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getDescription() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("view-message"))).getText();
    }

    public HomePage goToDashboard() {
        getDriver().findElement(By.xpath("//a[contains(text(),'Dashboard')]")).click();

        return new HomePage(getDriver());
    }
}
