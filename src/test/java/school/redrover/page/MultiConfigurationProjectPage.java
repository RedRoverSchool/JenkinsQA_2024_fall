package school.redrover.page;

import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseProjectPage;

public class MultiConfigurationProjectPage extends BaseProjectPage {

    public MultiConfigurationProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected MultiConfigurationProjectPage createProjectPage() {
        return new MultiConfigurationProjectPage(getDriver());
    }
}
