package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseCreatePage;

public class CreateNewItemPage extends BaseCreatePage<CreateNewItemPage> {

    public CreateNewItemPage(WebDriver driver) {
        super(driver);
    }

    private static final By GET_ORGANIZATION_FOLDER = By.xpath("//li[contains(@class,'jenkins_branch_OrganizationFolder')]");
    private static final By getInputName = By.id("name");

    public CreateNewItemPage enterItemName(String name) {
        getDriver().findElement(getInputName).sendKeys(name);

        return this;
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

    public String getErrorMessage() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='add-item-name']/div[@class='input-validation-message']"))).getText();
    }

    public CreateNewItemPage scrollToCopyFromFieldAndEnterName(String name) {
        final WebElement copyFromField = getDriver().findElement(By.id("from"));

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", copyFromField);

        copyFromField.sendKeys(name);

        return this;
    }
}