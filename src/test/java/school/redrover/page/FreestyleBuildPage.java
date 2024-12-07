package school.redrover.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class FreestyleBuildPage extends BasePage {

    @FindBy(xpath = "//input[@name='displayName']")
    private WebElement displayNameField;

    @FindBy(name = "description")
    private WebElement descriptionField;

    @FindBy(css = "svg[tooltip='Success']")
    private WebElement successIcon;

    @FindBy(tagName = "h1")
    private WebElement statusSectionTitle;

    @FindBy(id = "description")
    private WebElement buildDescription;

    @FindBy(id = "out")
    private WebElement consoleOutputMessage;

    @FindBy(xpath = "//span[contains(text(), 'Edit Build Information')]/..")
    private WebElement editBuildInformationSidebar;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(xpath = "//span[contains(text(), 'Delete build')]/..")
    private WebElement deleteBuildSidebar;

    @FindBy(name = "Submit")
    private WebElement deleteButton;

    public FreestyleBuildPage(WebDriver driver) {
        super(driver);
    }

    public String getStatusTitle() {
        getWait10().until(ExpectedConditions.visibilityOf(successIcon));
        return statusSectionTitle.getText();
    }

    public String getBuildDescription() {
        return getWait10().until(ExpectedConditions.visibilityOf(buildDescription)).getText();
    }

    public String getConsoleOutputText() {
        return getWait5().until(ExpectedConditions.visibilityOf(consoleOutputMessage)).getText();
    }

    public FreestyleBuildPage clickEditBuildInformationSidebar() {
        getWait5().until(ExpectedConditions.visibilityOf(editBuildInformationSidebar)).click();

        return this;
    }

    public FreestyleBuildPage addDisplayName(String name) {
        getWait5().until(ExpectedConditions.visibilityOf(displayNameField)).sendKeys(name);

        return this;
    }

    public FreestyleBuildPage editDisplayName(String newName) {
        getWait5().until(ExpectedConditions.visibilityOf(displayNameField)).clear();
        displayNameField.sendKeys(newName);

        return this;
    }

    public FreestyleBuildPage addBuildDescription(String description) {
        getWait5().until(ExpectedConditions.visibilityOf(descriptionField)).sendKeys(description);

        return this;
    }

    public FreestyleBuildPage editBuildDescription(String newDescription) {
        getWait5().until(ExpectedConditions.visibilityOf(descriptionField)).clear();
        descriptionField.sendKeys(newDescription);

        return this;
    }

    public FreestyleBuildPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public FreestyleBuildPage clickDeleteBuildSidebar() {
        deleteBuildSidebar.click();

        return this;
    }

    public FreestyleProjectPage confirmDeleteBuild() {
        getWait10().until(ExpectedConditions.visibilityOf(deleteButton)).click();

        return new FreestyleProjectPage(getDriver());
    }
}
