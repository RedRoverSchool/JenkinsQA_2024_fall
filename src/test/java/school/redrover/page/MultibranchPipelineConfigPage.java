package school.redrover.page;

import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseConfigPage;

public class MultibranchPipelineConfigPage extends BaseConfigPage<MultibranchPipelineConfigPage, MultibranchPipelineProjectPage> {

    public MultibranchPipelineConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected MultibranchPipelineProjectPage createProjectPage() {
        return new MultibranchPipelineProjectPage(getDriver());
    }

}
