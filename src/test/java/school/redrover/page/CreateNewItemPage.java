package school.redrover.page;

import org.openqa.selenium.By;
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

    public CreateNewItemPage enterItemName(String name) {
        getDriver().findElement(By.id("name")).sendKeys(name);

        return this;
    }

    public ConfigurationPage selectProjectTypeAndSave(ItemType itemType) {
        getDriver().findElement(By.xpath("//span[text()='%s']".formatted(itemType.getItemName()))).click();
        getDriver().findElement(By.id("ok-button")).click();

        return new ConfigurationPage(getDriver());
    }

    public CreateNewItemPage selectProjectType(ItemType itemType) {
        getDriver().findElement(By.xpath("//span[text()='%s']".formatted(itemType.getItemName()))).click();

        return this;
    }

    public ConfigurationPage nameAndSelectItemType(String itemName, ItemType itemType) {
        enterItemName(itemName);
        selectProjectTypeAndSave(itemType);

        return new ConfigurationPage(getDriver());
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

    public String getInvalidNameMessage() {
        return getDriver().findElement(By.id("itemname-invalid")).getText();
    }

    public String getEmptyNameMessage() {
        return getDriver().findElement(By.id("itemname-required")).getText();
    }

    public ErrorPage saveInvalidData(){
        getDriver().findElement(By.id("ok-button")).click();

        return new ErrorPage(getDriver());
    }
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