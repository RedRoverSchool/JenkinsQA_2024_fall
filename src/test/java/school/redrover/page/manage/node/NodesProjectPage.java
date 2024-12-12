package school.redrover.page.manage.node;

import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseProjectPage;

public class NodesProjectPage extends BaseProjectPage<NodesProjectPage, NodesConfigPage, NodesRenamePage> {

    public NodesProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected NodesConfigPage createProjectConfigPage() {
        return new NodesConfigPage(getDriver());
    }

    @Override
    protected NodesRenamePage createProjectRenamePage() {
        return new NodesRenamePage(getDriver());
    }
}
