package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseCreatePage;
import school.redrover.runner.TestUtils;

public class CreateNewItemPage extends BaseCreatePage {

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

    @FindBy(xpath = "//button[@id = 'ok-button']")
    private WebElement okButton;

    @FindBy(xpath = "//li[contains(@class,'jenkins_branch_OrganizationFolder')]")
    private WebElement organizationFolder;

    @FindBy(id = "name")
    private WebElement nameField;

    public CreateNewItemPage(WebDriver driver) {
        super(driver);
    }

    public CreateNewItemPage enterItemName(String name) {
        nameField.sendKeys(name);

        return this;
    }

    public CreateNewItemPage selectTypeOfProject(String name) {
        getDriver().findElement(By.xpath("//span[text()='" + name + "']")).click();

        return new CreateNewItemPage(getDriver());
    }

    public CreateNewItemPage selectFolderType() {
        folder.click();

        return this;
    }

    public FolderConfigPage selectFolderAndClickOk() {
        selectFolderType();
        clickOkButton();

        return new FolderConfigPage(getDriver());
    }

    public FolderConfigPage nameAndSelectFolderType(String itemName) {
        enterItemName(itemName);
        selectFolderAndClickOk();

        return new FolderConfigPage(getDriver());
    }

    public MultiConfigurationConfigPage selectMultiConfigurationAndClickOk() {
        multiConfigurationProject.click();
        clickOkButton();

        return new MultiConfigurationConfigPage(getDriver());
    }

    public FreestyleConfigPage selectFreestyleProjectAndClickOk() {
        freestyleProject.click();
        clickOkButton();

        return new FreestyleConfigPage(getDriver());
    }

    public FreestyleConfigPage nameAndSelectFreestyleProject(String itemName) {
        enterItemName(itemName);
        selectFreestyleProjectAndClickOk();

        return new FreestyleConfigPage(getDriver());
    }

    public MultibranchPipelineConfigPage selectMultibranchPipelineAndClickOk() {
        TestUtils.scrollToBottom(getDriver());

        multibranchPipeline.click();
        clickOkButton();

        return new MultibranchPipelineConfigPage(getDriver());
    }

    public PipelineConfigurePage selectPipelineAndClickOk() {
        pipeline.click();
        clickOkButton();

        return new PipelineConfigurePage(getDriver());
    }

    public OrganizationFolderConfigPage selectOrganizationFolderAndClickOk() {
        TestUtils.scrollToBottom(getDriver());
        organizationFolder.click();
        clickOkButton();

        return new OrganizationFolderConfigPage(getDriver());
    }

    public String getInvalidNameMessage() {
        return invalidOrSameNameMessage.getText();
    }

    public String getEmptyNameMessage() {
        return emptyNameMessage.getText();
    }

    public ErrorPage saveInvalidData() {
        clickOkButton();

        return new ErrorPage(getDriver());
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

    public CreateNewItemPage scrollToCopyFromFieldAndEnterName(String name) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", copyFromField);

        copyFromField.sendKeys(name);

        return this;
    }

    public PipelineConfigurePage clickOkAndGoToPipelineConfigPage() {
        okButton.click();

        return new PipelineConfigurePage(getDriver());
    }
}