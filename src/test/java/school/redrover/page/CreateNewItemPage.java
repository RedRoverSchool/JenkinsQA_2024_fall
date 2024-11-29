package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class CreateNewItemPage extends BasePage {

    public CreateNewItemPage(WebDriver driver) {
        super(driver);
    }

    By getMultiConfigurationProject = By.xpath("//li//span[text()='Multi-configuration project']");
    By getSubmitButton = By.xpath("//button[@id = 'ok-button']");
    private final By GET_ORGANIZATION_FOLDER = By.xpath("//li[contains(@class,'jenkins_branch_OrganizationFolder')]");

    public CreateNewItemPage enterItemName(String name) {
        getDriver().findElement(By.id("name")).sendKeys(name);

        return this;
    }

    public void clickOkButton() {
        getDriver().findElement(By.id("ok-button")).click();
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
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        clickOkButton();

        return new MultibranchPipelineConfigPage(getDriver());
    }

    public PipelineConfigurePage selectPipelineAndClickOk() {
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        clickOkButton();

        return new PipelineConfigurePage(getDriver());
    }

    public OrganizationFolderConfigurationPage selectOrganizationFolderAndClickOk() {

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        getDriver()
                .findElement(By.xpath("//li[@class='jenkins_branch_OrganizationFolder']"))
                .click();
        clickOkButton();

        return new OrganizationFolderConfigurationPage(getDriver());
    }

    public String getInvalidNameMessage() {
        return getDriver().findElement(By.id("itemname-invalid")).getText();
    }

    public String getEmptyNameMessage() {
        return getDriver().findElement(By.id("itemname-required")).getText();
    }

    public ErrorPage saveInvalidData(){
        clickOkButton();

        return new ErrorPage(getDriver());
    }

    public CreateNewItemPage selectPipeline() {
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath(("//div[@id='items']//label/span[text()= 'Pipeline']")))).click();
        return new CreateNewItemPage(getDriver());
    }

    public CreateNewItemPage selectFreestyleProject() {
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath(("//div[@id='items']//label/span[text()= 'Freestyle project']")))).click();
        return new CreateNewItemPage(getDriver());
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

    public OrganizationFolderConfigurationPage clickOrganizationFolderAndClickOk() {
        getDriver().findElement(GET_ORGANIZATION_FOLDER).click();
        clickOkButton();

        return new OrganizationFolderConfigurationPage(getDriver());
    }
}