package school.redrover.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BaseProjectPage;

public class OrganizationFolderProjectPage extends BaseProjectPage<OrganizationFolderProjectPage, OrganizationFolderConfigPage, OrganizationFolderRenamePage> {

    @FindBy(xpath = "//a[@href='./configure']")
    private WebElement configureButton;

    @FindBy(tagName = "h1")
    private WebElement name;

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

    public String getName() {
        return name.getText();
    }
}
