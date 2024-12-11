package school.redrover.page.organizationFolder;

import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseRenamePage;

public class OrganizationFolderRenamePage extends BaseRenamePage<OrganizationFolderRenamePage, OrganizationFolderProjectPage> {

    public OrganizationFolderRenamePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected OrganizationFolderProjectPage createProjectPage() {
        return new OrganizationFolderProjectPage(getDriver());
    }
}
