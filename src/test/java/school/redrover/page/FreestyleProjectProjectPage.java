package school.redrover.page;

import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseProjectPage;

public class FreestyleProjectProjectPage extends BaseProjectPage {

    public FreestyleProjectProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected FreestyleProjectProjectPage createProjectPage() {
        return new FreestyleProjectProjectPage(getDriver());
    }
}
