package school.redrover.page.multibranch;

import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseRenamePage;

public class MultibranchPipelineRenamePage extends BaseRenamePage<MultibranchPipelineRenamePage, MultibranchPipelineProjectPage> {

    public MultibranchPipelineRenamePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected MultibranchPipelineProjectPage createProjectPage() {
        return new MultibranchPipelineProjectPage(getDriver());
    }
}
