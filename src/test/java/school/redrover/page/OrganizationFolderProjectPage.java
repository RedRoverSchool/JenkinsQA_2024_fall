package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseProjectPage;

public class OrganizationFolderProjectPage extends BaseProjectPage<OrganizationFolderProjectPage, OrganizationFolderConfigPage> {

    public OrganizationFolderProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected OrganizationFolderConfigPage createProjectConfigPage() {
        return new OrganizationFolderConfigPage(getDriver());
    }

    private final By GET_CONFIGURE = By.xpath("//a[@href='./configure']");
    private final By NAME_H1 = By.tagName("h1");

    public OrganizationFolderConfigPage clickConfigure() {
        getDriver().findElement(GET_CONFIGURE).click();

        return new OrganizationFolderConfigPage(getDriver());
    }

    public String getName() {
        return getDriver().findElement(NAME_H1).getText();
    }
}
