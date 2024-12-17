package school.redrover.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

import javax.annotation.processing.Generated;

public class AgentPage extends BasePage {
    public AgentPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "name")
    private WebElement nodeName;

    @FindBy(xpath = "//fieldset/div/label")
    private WebElement permanentAgent;

    @FindBy(id = "ok")
    private WebElement create;

    @FindBy(xpath = "//div[4]/div[2]/input")
    private WebElement remoteRootDirectory;

    @FindBy(xpath = "//div[5]/div[2]/input")
    private WebElement labels;

    @FindBy(xpath = "//select")
    private WebElement usageSelect;

    @FindBy(xpath = "//div[7]/div[3]/select")
    private WebElement methodLaunch;

    @FindBy(xpath = "//*[@id='bottom-sticker']/div/button")
    private WebElement save;

    @FindBy(xpath = "//*[@id='node_NewNode']//a")
    private WebElement newNode;

    @FindBy(xpath = "//*[@id='main-panel']/p[1]")
    private WebElement connectedAgent;

    public AgentPage enterNodeName(String name) {
        getWait5().until(ExpectedConditions.visibilityOf(nodeName)).sendKeys(name);

        return this;
    }

    public AgentPage clickPermanentAgent() {
        permanentAgent.click();

        return this;
    }

    public AgentPage create() {
        create.click();

        return this;
    }

    public AgentPage save() {
        save.click();

        return this;
    }

    public AgentPage enterRemoteRootDirectory(String rootDirectory) {
        remoteRootDirectory.sendKeys(rootDirectory);

        return this;
    }

    public AgentPage label(){
        labels.sendKeys();

        return this;
    }

    public AgentPage usage() {
        usageSelect.click();

        return this;
    }

    public AgentPage methodLaunch() {
        methodLaunch.click();

        return this;
    }

    public String getNewNode() {

        return newNode.getText();
    }

    public String getConnected() {
        return connectedAgent.getText();
    }

}
