package school.redrover.page.multiConfiguration;

import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseRenamePage;

public class MultiConfigurationRenamePage extends BaseRenamePage<MultiConfigurationRenamePage, MultiConfigurationProjectPage> {

    public MultiConfigurationRenamePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected MultiConfigurationProjectPage createProjectPage() {
        return new MultiConfigurationProjectPage(getDriver());
    }
}
