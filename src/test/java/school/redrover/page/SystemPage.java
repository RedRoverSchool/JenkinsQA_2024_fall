package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BasePage;

public class SystemPage extends BasePage {
    public SystemPage(WebDriver driver) {
        super(driver);
    }

    public String getBreadCrumbs() {

        return getDriver().findElement(By.id("breadcrumbs")).getText();
    }
}
