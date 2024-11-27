package school.redrover.page;

import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseConfigPage;

public class FreestyleConfigPage extends BaseConfigPage<FreestyleConfigPage, FreestyleProjectPage> {

    public FreestyleConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected FreestyleProjectPage createProjectPage() {
        return new FreestyleProjectPage(getDriver());
    }
}
