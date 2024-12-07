package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseCreatePage;

public class CreateNewItemPage extends BaseCreatePage {

    @FindBy(css = "[class$='MultiBranchProject']")
    private WebElement multibranchPipelineType;

    @FindBy(id = "name")
    private WebElement nameInput;

    public CreateNewItemPage(WebDriver driver) {
        super(driver);
    }

    private static final By getMultiConfigurationProject = By.xpath("//li//span[text()='Multi-configuration project']");
    private static final By getSubmitButton = By.xpath("//button[@id = 'ok-button']");
    private static final By GET_ORGANIZATION_FOLDER = By.xpath("//li[contains(@class,'jenkins_branch_OrganizationFolder')]");
    private static final By getInputName = By.id("name");
    private static final By getOkButton = By.id("ok-button");

    public CreateNewItemPage enterItemName(String name) {
        nameInput.sendKeys(name);

        return this;
    }

    public CreateNewItemPage selectTypeOfProject(String name) {
        getDriver().findElement(By.xpath("//span[text()='" + name + "']")).click();

        return new CreateNewItemPage(getDriver());
    }

    public CreateNewItemPage selectFolderType() {
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();

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
        getDriver().findElement(By.xpath("//span[text()='Multi-configuration project']")).click();
        clickOkButton();

        return new MultiConfigurationConfigPage(getDriver());
    }

    public FreestyleConfigPage selectFreestyleProjectAndClickOk() {
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        clickOkButton();

        return new FreestyleConfigPage(getDriver());
    }

    public FreestyleConfigPage nameAndSelectFreestyleProject(String itemName) {
        enterItemName(itemName);
        selectFreestyleProjectAndClickOk();

        return new FreestyleConfigPage(getDriver());
    }

    public MultibranchPipelineConfigPage selectMultibranchPipelineAndClickOk() {
        multibranchPipelineType.click();
        clickOkButton();

        return new MultibranchPipelineConfigPage(getDriver());
    }

    public PipelineConfigurePage selectPipelineAndClickOk() {
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        clickOkButton();

        return new PipelineConfigurePage(getDriver());
    }

    public OrganizationFolderConfigPage selectOrganizationFolderAndClickOk() {
        getDriver().findElement(GET_ORGANIZATION_FOLDER).click();
        clickOkButton();

        return new OrganizationFolderConfigPage(getDriver());
    }

    public String getInvalidNameMessage() {
        return getDriver().findElement(By.id("itemname-invalid")).getText();
    }

    public String getEmptyNameMessage() {
        return getDriver().findElement(By.id("itemname-required")).getText();
    }

    public ErrorPage saveInvalidData() {
        clickOkButton();

        return new ErrorPage(getDriver());
    }

    public CreateNewItemPage selectPipeline() {
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath(("//div[@id='items']//label/span[text()= 'Pipeline']")))).click();
        return new CreateNewItemPage(getDriver());
    }

    public CreateNewItemPage selectFreestyleProject() {
        getDriver().findElement(By.xpath(("//span[text()= 'Freestyle project']"))).click();
        return this;
    }

    public CreateNewItemPage selectMultibranchPipelineProject() {
        multibranchPipelineType.click();
        return this;
    }

    public String getErrorMessage() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='add-item-name']/div[@class='input-validation-message']"))).getText();
    }

    public CreateNewItemPage choseMultiConfigurationProject() {
        getDriver().findElement(getMultiConfigurationProject).click();

        return this;
    }

    public MultiConfigurationProjectPage submitCreationProject() {
        getDriver().findElement(getSubmitButton).click();

        return new MultiConfigurationProjectPage(getDriver());
    }

    public OrganizationFolderConfigPage clickOrganizationFolderAndClickOk() {
        getDriver().findElement(GET_ORGANIZATION_FOLDER).click();
        clickOkButton();

        return new OrganizationFolderConfigPage(getDriver());
    }

    public CreateNewItemPage scrollToCopyFromFieldAndEnterName(String name) {
        final WebElement copyFromField = getDriver().findElement(By.id("from"));

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", copyFromField);

        copyFromField.sendKeys(name);

        return this;
    }

    public PipelineConfigurePage clickOkAndGoToPipelineConfigPage() {
        getDriver().findElement(getOkButton).click();

        return new PipelineConfigurePage(getDriver());
    }
}