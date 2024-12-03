package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class FreestyleBuildPage extends BasePage {

    public FreestyleBuildPage(WebDriver driver) {
        super(driver);
    }

    public String getConsoleOutputText() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("out"))).getText();
    }
}
