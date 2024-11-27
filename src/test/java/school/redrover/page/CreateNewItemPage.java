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

    public CreateNewItemPage enterItemName(String name) {
        getDriver().findElement(getNameOfProject).sendKeys(name);

        return this;
    }

    public FolderConfigPage selectProjectTypeAndSave(ItemType itemType) {
        getDriver().findElement(By.xpath("//span[text()='%s']".formatted(itemType.getItemName()))).click();
        getDriver().findElement(By.id("ok-button")).click();

        return new FolderConfigPage(getDriver());
    }

    public CreateNewItemPage selectProjectType(ItemType itemType) {
        getDriver().findElement(By.xpath("//span[text()='%s']".formatted(itemType.getItemName()))).click();

        return this;
    }

    public FolderConfigPage nameAndSelectItemType(String itemName, ItemType itemType) {
        enterItemName(itemName);
        selectProjectTypeAndSave(itemType);

        return new FolderConfigPage(getDriver());
    }

    public MultibranchPipelineConfigPage selectMultibranchPipelineAndClickOk() {
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        return new MultibranchPipelineConfigPage(getDriver());
    }

    public PipelineConfigurePage selectPipelineAndClickOk() {
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        return new PipelineConfigurePage(getDriver());
    }

    public OrganizationFolderConfigurationPage selectOrganizationFolderAndClickOk() {

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        getDriver()
                .findElement(By.xpath("//li[@class='jenkins_branch_OrganizationFolder']"))
                .click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        return new OrganizationFolderConfigurationPage(getDriver());
    }

    public String getInvalidNameMessage() {
        return getDriver().findElement(By.id("itemname-invalid")).getText();
    }

    public String getEmptyNameMessage() {
        return getDriver().findElement(By.id("itemname-required")).getText();
    }

    public ErrorPage saveInvalidData() {
        getDriver().findElement(getSubmitButton).click();

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

