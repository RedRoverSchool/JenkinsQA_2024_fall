package school.redrover.page.manage.node;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BaseConfigPage;


public class NodesConfigPage extends BaseConfigPage<NodesConfigPage, NodesProjectPage> {

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement buttonSave;

    public NodesConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected NodesProjectPage createProjectPage() {
        return new NodesProjectPage(getDriver());
    }

    public NodesPage clickButtonSave() {
        buttonSave.click();

        return new NodesPage(getDriver());
    }
}
