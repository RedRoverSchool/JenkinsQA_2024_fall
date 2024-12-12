package school.redrover.page.multibranch;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseProjectPage;

public class MultibranchPipelineProjectPage extends BaseProjectPage<MultibranchPipelineProjectPage, MultibranchPipelineConfigPage, MultibranchPipelineRenamePage> {

    @FindBy(id = "view-message")
    private WebElement description;

    @FindBy(xpath = "//a[contains(@href,'job')][@class='model-link']")
    private WebElement jobNameToMenu;

    public MultibranchPipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public MultibranchPipelineConfigPage createProjectConfigPage() {
        return new MultibranchPipelineConfigPage(getDriver());
    }

    @Override
    public MultibranchPipelineRenamePage createProjectRenamePage() {
        return new MultibranchPipelineRenamePage(getDriver());
    }

    public String getBreadcrumbName() {

        return jobNameToMenu.getText();
    }

    public String getDescription() {
        return getWait5().until(ExpectedConditions.visibilityOf(description)).getText();
    }
}