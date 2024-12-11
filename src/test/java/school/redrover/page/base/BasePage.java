package school.redrover.page.base;

import org.openqa.selenium.WebDriver;
import school.redrover.page.*;
import school.redrover.page.home.HomePage;
import school.redrover.page.home.ManageJenkinsPage;

import java.util.List;

public abstract class BasePage<Target extends BasePage<Target>> extends BaseModel {

    private HeaderComponent<Target> header;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HeaderComponent<Target> getHeader() {
        if (header == null) {
            header = new HeaderComponent<>(getDriver(), (Target) this);
        }
        return header;
    }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

}
