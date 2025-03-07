package school.redrover.page.home;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.ErrorPage;
import school.redrover.page.base.BasePage;
import school.redrover.page.folder.FolderConfigPage;
import school.redrover.page.freestyle.FreestyleConfigPage;
import school.redrover.page.multiConfiguration.MultiConfigurationConfigPage;
import school.redrover.page.multibranch.MultibranchPipelineConfigPage;
import school.redrover.page.organizationFolder.OrganizationFolderConfigPage;
import school.redrover.page.pipeline.PipelineConfigurePage;
import school.redrover.runner.TestUtils;

import java.util.List;

public class CreateNewItemPage extends BasePage<CreateNewItemPage> {

    @FindBy(xpath = "//span[text()='Multibranch Pipeline']")
    private WebElement multibranchPipeline;

    @FindBy(xpath = "//span[text()='Folder']")
    private WebElement folder;

    @FindBy(xpath = "//span[text()='Multi-configuration project']")
    private WebElement multiConfigurationProject;

    @FindBy(xpath = "//span[text()='Freestyle project']")
    private WebElement freestyleProject;

    @FindBy(xpath = "//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")
    private WebElement pipeline;

    @FindBy(id = "itemname-required")
    private WebElement emptyNameMessage;

    @FindBy(xpath = "//div[@class='add-item-name']/div[@class='input-validation-message']")
    private WebElement invalidOrSameNameMessage;

    @FindBy(id = "from")
    private WebElement copyFromField;

    @FindBy(xpath = "//li[contains(@class,'jenkins_branch_OrganizationFolder')]")
    private WebElement organizationFolder;

    @FindBy(id = "name")
    private WebElement nameField;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(id = "add-item-panel")
    private WebElement pageField;

    @FindBy(id = "itemname-required")
    private WebElement warningMessage;

    @FindBy(xpath = "//div[@id='items']//li//label/span")
    private List<WebElement> itemsTypesList;

    public CreateNewItemPage(WebDriver driver) {
        super(driver);
    }

    @Step("Type '{name}' to name input field")
    public CreateNewItemPage enterItemName(String name) {
        nameField.sendKeys(name);

        return this;
    }

    @Step("Select 'Folder' type")
    public CreateNewItemPage selectFolderType() {
        folder.click();

        return this;
    }

    @Step("Select 'Folder' and click 'Ok' button")
    public FolderConfigPage selectFolderAndClickOk() {
        selectFolderType();
        okButton.click();

        return new FolderConfigPage(getDriver());
    }

    @Step("Type name '{itemName}', select 'Folder' and click 'Ok'")
    public FolderConfigPage nameAndSelectFolderType(String itemName) {
        enterItemName(itemName);
        selectFolderAndClickOk();

        return new FolderConfigPage(getDriver());
    }

    @Step("Select 'MultiConfiguration' and click 'Ok'")
    public MultiConfigurationConfigPage selectMultiConfigurationAndClickOk() {
        multiConfigurationProject.click();
        okButton.click();

        return new MultiConfigurationConfigPage(getDriver());
    }

    @Step("Select 'MultiConfiguration'")
    public CreateNewItemPage selectMultiConfigurationProject() {
        multiConfigurationProject.click();

        return new CreateNewItemPage(getDriver());
    }

    @Step("Select 'Freestyle project' and click 'Ok' button")
    public FreestyleConfigPage selectFreestyleProjectAndClickOk() {
        freestyleProject.click();
        okButton.click();

        return new FreestyleConfigPage(getDriver());
    }

    @Step("Type name '{itemName}', select 'FreestyleProject' and click 'Ok'")
    public FreestyleConfigPage nameAndSelectFreestyleProject(String itemName) {
        enterItemName(itemName);
        selectFreestyleProjectAndClickOk();

        return new FreestyleConfigPage(getDriver());
    }

    @Step("Select 'Multibranch Pipeline' and click 'Ok' button")
    public MultibranchPipelineConfigPage selectMultibranchPipelineAndClickOk() {
        TestUtils.scrollToBottomWithJS(getDriver());

        multibranchPipeline.click();
        okButton.click();

        return new MultibranchPipelineConfigPage(getDriver());
    }

    @Step("Select 'Multibranch Pipeline'")
    public CreateNewItemPage selectMultibranchPipeline() {
        multibranchPipeline.click();

        return this;
    }

    @Step("Select 'Pipeline' and click 'Ok' button")
    public PipelineConfigurePage selectPipelineAndClickOk() {
        pipeline.click();
        okButton.click();

        return new PipelineConfigurePage(getDriver());
    }

    public OrganizationFolderConfigPage selectOrganizationFolderAndClickOk() {
        TestUtils.scrollToBottomWithJS(getDriver());
        organizationFolder.click();
        okButton.click();

        return new OrganizationFolderConfigPage(getDriver());
    }

    @Step("Get error message")
    public String getInvalidNameMessage() {
        return invalidOrSameNameMessage.getText();
    }

    @Step("Get invalid message for empty name field")
    public String getEmptyNameMessage() {
        return emptyNameMessage.getText();
    }

    @Step("Select 'Pipeline'")
    public CreateNewItemPage selectPipeline() {
        getWait10().until(ExpectedConditions.elementToBeClickable(pipeline)).click();

        return new CreateNewItemPage(getDriver());
    }

    @Step("Select 'Freestyle project'")
    public CreateNewItemPage selectFreestyleProject() {
        freestyleProject.click();

        return this;
    }

    @Step("Select 'Multibranch Pipeline'")
    public CreateNewItemPage selectMultibranchPipelineProject() {
        multibranchPipeline.click();

        return this;
    }
  
    @Step("Get Error Message")
    public String getErrorMessage() {
        return getWait5().until(ExpectedConditions.visibilityOf(invalidOrSameNameMessage)).getText();
    }

    @Step("Type '{name}' to 'Copy from' input field")
    public CreateNewItemPage enterName(String name) {
        TestUtils.scrollToElementWithJS(getDriver(), copyFromField);
        copyFromField.sendKeys(name);

        return this;
    }

    @Step("Click 'Ok' button")
    public <T> T clickOkLeadingToCofigPageOfCopiedProject(T page) {
        okButton.click();

        return page;
    }

    @Step("Click 'Ok' button")
    public ErrorPage clickOkButtonLeadingToErrorPage() {
        okButton.click();

        return new ErrorPage(getDriver());
    }

    @Step("Click on random area on CreateNewItemPage")
    public CreateNewItemPage clickSomewhere() {
        pageField.click();

        return this;
    }

    @Step("Get warning message")
    public String getWarningMessageText() {
        return warningMessage.getText();
    }

    @Step("Check if 'Ok' button is enabled")
    public boolean getOkButton() {
        return okButton.isEnabled();
    }

    @Step("Get list of available item types")
    public List<WebElement> getTextList() {

        return itemsTypesList;
    }

    @Step("Get list of names for available item types")
    public List<String> getItemList() {
        return itemsTypesList.stream().map(WebElement::getText).toList();
    }
}
