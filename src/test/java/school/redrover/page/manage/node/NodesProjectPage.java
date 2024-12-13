package school.redrover.page.manage.node;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BaseProjectPage;


public class NodesProjectPage extends BaseProjectPage<NodesProjectPage, NodesConfigPage, NodesRenamePage> {
    @FindBy(xpath = "//*[@id='name']")
    private WebElement itemName;

    @FindBy(xpath = "//*[.='Permanent Agent']")
    private WebElement radioButtonPermanentAgent;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement buttonCreate;

    public NodesProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected NodesConfigPage createProjectConfigPage() {
        return new NodesConfigPage(getDriver());
    }

    @Override
    protected NodesRenamePage createProjectRenamePage() {
        return new NodesRenamePage(getDriver());
    }

    public NodesProjectPage enterNodeName(String name) {
        itemName.sendKeys(name);

        return this;
    }

    public NodesProjectPage selectPermanentAgent() {
        radioButtonPermanentAgent.click();

        return this;
    }

    public NodesConfigPage clickButtonCreate() {
        buttonCreate.click();

        return new NodesConfigPage(getDriver());
    }
}

