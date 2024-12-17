package school.redrover.page.home;

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

public class CreateNewItemPage extends BasePage {

    @FindBy(xpath = "//span[text()= 'Multibranch Pipeline']")
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

    public CreateNewItemPage(WebDriver driver) {
        super(driver);
    }

    public CreateNewItemPage enterItemName(String name) {
        nameField.sendKeys(name);

        return this;
    }

    public CreateNewItemPage selectFolderType() {
        folder.click();

        return this;
    }

    public FolderConfigPage selectFolderAndClickOk() {
        selectFolderType();
        okButton.click();

        return new FolderConfigPage(getDriver());
    }

    public FolderConfigPage nameAndSelectFolderType(String itemName) {
        enterItemName(itemName);
        selectFolderAndClickOk();

        return new FolderConfigPage(getDriver());
    }

    public MultiConfigurationConfigPage selectMultiConfigurationAndClickOk() {
        multiConfigurationProject.click();
        okButton.click();

        return new MultiConfigurationConfigPage(getDriver());
    }

    public CreateNewItemPage selectMultiConfigurationProject() {
        multiConfigurationProject.click();

        return new CreateNewItemPage(getDriver());
    }

    public FreestyleConfigPage selectFreestyleProjectAndClickOk() {
        freestyleProject.click();
        okButton.click();

        return new FreestyleConfigPage(getDriver());
    }

    public FreestyleConfigPage nameAndSelectFreestyleProject(String itemName) {
        enterItemName(itemName);
        selectFreestyleProjectAndClickOk();

        return new FreestyleConfigPage(getDriver());
    }

    public MultibranchPipelineConfigPage selectMultibranchPipelineAndClickOk() {
        TestUtils.scrollToBottomWithJS(getDriver());

        multibranchPipeline.click();
        okButton.click();

        return new MultibranchPipelineConfigPage(getDriver());
    }

    public CreateNewItemPage selectMultibranchPipeline() {
        multibranchPipeline.click();

        return this;
    }

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

    public String getInvalidNameMessage() {
        return invalidOrSameNameMessage.getText();
    }

    public String getEmptyNameMessage() {
        return emptyNameMessage.getText();
    }

    public CreateNewItemPage selectPipeline() {
        getWait10().until(ExpectedConditions.elementToBeClickable(pipeline)).click();

        return new CreateNewItemPage(getDriver());
    }

    public CreateNewItemPage selectFreestyleProject() {
        freestyleProject.click();

        return this;
    }

    public CreateNewItemPage selectMultibranchPipelineProject() {
        multibranchPipeline.click();

        return this;
    }

    public String getErrorMessage() {
        return getWait5().until(ExpectedConditions.visibilityOf(invalidOrSameNameMessage)).getText();
    }

    public CreateNewItemPage enterName(String name) {
        TestUtils.scrollToElementWithJS(getDriver(), copyFromField);
        copyFromField.sendKeys(name);

        return this;
    }

    public <T> T clickOkLeadingToCofigPageOfCopiedProject(T page) {
        okButton.click();

        return page;
    }

    public ErrorPage clickOkButtonLeadingToErrorPage() {
        okButton.click();

        return new ErrorPage(getDriver());
    }

    public CreateNewItemPage clickSomewhere() {
        pageField.click();

        return this;
    }

    public String getWarningMessageText() {
        return warningMessage.getText();
    }

    public boolean getOkButton() {
        return okButton.isEnabled();
    }
}
