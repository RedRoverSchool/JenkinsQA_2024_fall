package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BasePage;

public class PipelineProjectPage extends BasePage {
    public PipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    public HomePage returnToHomePage() {
        getDriver().findElement(By.id("jenkins-home-link")).click();

        return new HomePage(getDriver());
    }
}
