package school.redrover.page.pipeline;

import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseRenamePage;

public class PipelineRenamePage extends BaseRenamePage<PipelineRenamePage, PipelineProjectPage> {

    public PipelineRenamePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected PipelineProjectPage createProjectPage() {
        return new PipelineProjectPage(getDriver());
    }

}
