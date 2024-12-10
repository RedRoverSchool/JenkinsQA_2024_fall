package school.redrover.page.freestyle;

import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseRenamePage;

public class FreestyleRenamePage extends BaseRenamePage<FreestyleRenamePage, FreestyleProjectPage> {

    public FreestyleRenamePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected FreestyleProjectPage createProjectPage() {
        return new FreestyleProjectPage(getDriver());
    }
}
