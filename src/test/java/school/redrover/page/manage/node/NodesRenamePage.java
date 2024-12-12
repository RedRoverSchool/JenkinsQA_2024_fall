package school.redrover.page.manage.node;

import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseRenamePage;

public class NodesRenamePage extends BaseRenamePage<NodesRenamePage, NodesProjectPage> {

    public NodesRenamePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected NodesProjectPage createProjectPage() {
        return new NodesProjectPage(getDriver());
    }
}
