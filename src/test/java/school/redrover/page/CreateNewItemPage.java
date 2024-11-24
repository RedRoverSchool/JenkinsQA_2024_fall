package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BasePage;

public class CreateNewItemPage extends BasePage {

    public CreateNewItemPage(WebDriver driver) {
        super(driver);
    }

    public enum ItemType {
        FOLDER("Folder"),
        FREESTYLE_PROJECT("Freestyle project"),
        MULTICONFIGURATION_PROJECT("Multi-configuration project");

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
}
