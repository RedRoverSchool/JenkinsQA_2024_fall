package school.redrover.page.multibranch;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.home.HomePage;
import school.redrover.page.base.BaseProjectPage;
import school.redrover.runner.TestUtils;

public class MultibranchPipelineProjectPage extends BaseProjectPage<MultibranchPipelineProjectPage, MultibranchPipelineConfigPage, MultibranchPipelineRenamePage> {

    @FindBy(id = "view-message")
    private WebElement description;

    @FindBy(xpath = "//a[contains(@href,'job')][@class='model-link']")
    private WebElement jobNameToMenu;

    @FindBy(xpath = "//button[@class='jenkins-menu-dropdown-chevron'][contains(@data-href,'job')]")
    private WebElement chevronButton;

    @FindBy(xpath = "//button[contains(@href,'Delete')]")
    private WebElement deleteButton;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement confirmDeletionButton;

    @FindBy(tagName = "h1")
    private WebElement title;

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

    public String getTitle() {
        return title.getText();
    }

    public String getDescription() {
        return getWait5().until(ExpectedConditions.visibilityOf(description)).getText();
    }

    public HomePage deleteJobUsingDropdownBreadcrumbJobPage() {
        new Actions(getDriver()).moveToElement(jobNameToMenu).perform();

        getWait5().until(ExpectedConditions.visibilityOf(chevronButton));
        TestUtils.moveAndClickWithJS(getDriver(), chevronButton);

        deleteButton.click();
        confirmDeletionButton.click();

        return new HomePage(getDriver());
    }
}