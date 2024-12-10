package school.redrover.page.folder;

import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseRenamePage;

public class FolderRenamePage extends BaseRenamePage<FolderRenamePage, FolderProjectPage> {

    public FolderRenamePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected FolderProjectPage createProjectPage() {
        return new FolderProjectPage(getDriver());
    }
}
