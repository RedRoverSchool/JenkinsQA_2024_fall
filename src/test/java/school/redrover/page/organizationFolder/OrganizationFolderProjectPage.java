package school.redrover.page.organizationFolder;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseProjectPage;

public class OrganizationFolderProjectPage extends BaseProjectPage<OrganizationFolderProjectPage, OrganizationFolderConfigPage, OrganizationFolderRenamePage> {

    @FindBy(xpath = "//a[@href='./configure']")
    private WebElement configureButton;

    @FindBy(id="view-message")
    private WebElement descriptionText;

    @FindBy(css = "h1 > svg")
    private WebElement titleIcon;

    @FindBy(id="main-panel")
    private WebElement mainPanel;

    public OrganizationFolderProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected OrganizationFolderConfigPage createProjectConfigPage() {
        return new OrganizationFolderConfigPage(getDriver());
    }

    @Override
    public OrganizationFolderRenamePage createProjectRenamePage() {
        return new OrganizationFolderRenamePage(getDriver());
    }

    public OrganizationFolderConfigPage clickConfigure() {
        configureButton.click();

        return new OrganizationFolderConfigPage(getDriver());
    }

    public String getDescriptionWhenAddedViaConfigure() {
        return getWait5().until(ExpectedConditions.visibilityOf(descriptionText)).getText();
    }

    public String getIconAttributeTitle() {
        return titleIcon.getAttribute("title");
    }

    public String getMainPanelText() {
        return mainPanel.getText();
    }
}
