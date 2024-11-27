package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseConfigPage;

public class FolderConfigPage extends BaseConfigPage<FolderConfigPage, FolderProjectPage> {

    public FolderConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected FolderProjectPage createProjectPage() {
        return new FolderProjectPage(getDriver());
    }

    public FolderConfigPage enterConfigurationName(String name) {
        getDriver().findElement(By.xpath("//div[contains(text(),'Display Name')]/following-sibling::div[1]/input"))
                .sendKeys(name);

        return this;
    }
}
