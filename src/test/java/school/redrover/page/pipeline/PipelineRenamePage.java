package school.redrover.page;

import org.openqa.selenium.By;
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
