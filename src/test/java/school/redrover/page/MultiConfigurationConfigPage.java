package school.redrover.page;

import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseConfigPage;
import school.redrover.page.base.BaseProjectPage;

public class MultiConfigurationConfigPage extends BaseConfigPage {

    public MultiConfigurationConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected MultiConfigurationProjectPage createProjectPage() {

        return new MultiConfigurationProjectPage(getDriver());
    }
}
