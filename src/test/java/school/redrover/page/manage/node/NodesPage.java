package school.redrover.page.manage.node;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

public class NodesPage extends BasePage {

    @FindBy(xpath = "//a[@href='new']")
    private WebElement buttonNewNode;

    @FindBy(xpath = "//*[@id='name']")
    private WebElement itemName;

    @FindBy(xpath = "//*[.='Permanent Agent']")
    private WebElement checkboxAgent;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement buttonCreate;

    public NodesPage(WebDriver driver) {
        super(driver);
    }

    public NodesPage clickButtonNewNode() {
        buttonNewNode.click();

        return new NodesPage(getDriver());
    }

    public NodesPage inputName(String name) {
        itemName.sendKeys(name);

        return this;
    }

    public NodesPage clickCheckboxAgent() {
        checkboxAgent.click();

        return this;
    }

    public NodesConfigPage clickButtonCreate() {
        buttonCreate.click();

        return new NodesConfigPage(getDriver());
    }
}
