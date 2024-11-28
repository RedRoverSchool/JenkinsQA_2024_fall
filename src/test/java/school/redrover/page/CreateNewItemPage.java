package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;

import school.redrover.page.base.BasePage;

public class  CreateNewItemPage extends BasePage {

    public CreateNewItemPage(WebDriver driver) {
        super(driver);
    }

<<<<<<< HEAD
    public enum ItemType {
        PIPELINE("Pipeline"),
        FOLDER("Folder"),
        FREESTYLE_PROJECT("Freestyle project"),
        MULTICONFIGURATION_PROJECT("Multi-configuration project"),
        MULTIBRANCH_PIPELINE("Multibranch Pipeline"),
        ORGANIZATION_FOLDER("Organization Folder");

        private final String itemName;

        ItemType(String itemName) {
            this.itemName = itemName;
        }

        public String getItemName() {
            return itemName;
        }
    }

    By getNameOfProject = By.xpath("//input[@id='name']");
    By getTypeOfMultiConfigurationProject = By.xpath("//span[text()='Multi-configuration project']");

    By getSubmitButton = By.xpath("//button[@id = 'ok-button']");
    By getInputField = By.id("itemname-required");

    public CreateNewItemPage choseMultiConfigurationProject() {
        getDriver().findElement(getTypeOfMultiConfigurationProject).click();

        return this;
    }

    public MultiConfigurationConfigPage submitCreationProject() {
        getDriver().findElement(getSubmitButton).click();

        return new MultiConfigurationConfigPage(getDriver());
    }

=======
>>>>>>> e7357be367a870d0f61f746714286a6795ede335
    public CreateNewItemPage enterItemName(String name) {
        getDriver().findElement(getNameOfProject).sendKeys(name);

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

<<<<<<< HEAD
    public ErrorPage saveInvalidData() {
        getDriver().findElement(getSubmitButton).click();
=======
    public ErrorPage saveInvalidData(){
        clickOkButton();
>>>>>>> e7357be367a870d0f61f746714286a6795ede335

        return new ErrorPage(getDriver());
    }
    public String errorEmptyField() {

        return getDriver().findElement(getInputField).getText();
    }

    public CreateNewItemPage selectPipeline() {
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath(("//div[@id='items']//label/span[text()= 'Pipeline']")))).click();
        return this;
    }

    public String getErrorMessage() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='add-item-name']/div[@class='input-validation-message']"))).getText();
    }
}

