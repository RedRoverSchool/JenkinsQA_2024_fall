package school.redrover.page.base;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.home.HomePage;
import school.redrover.runner.TestUtils;

import java.util.List;

public abstract class BaseProjectPage<Self extends BaseProjectPage<?, ?, ?>, ProjectConfigPage, ProjectRenamePage> extends BasePage<BaseProjectPage<?, ?, ?>> {

    @FindBy(id = "description-link")
    private WebElement descriptionButton;

    @FindBy(name = "description")
    private WebElement descriptionField;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@id='description']/div[1]")
    private WebElement descriptionText;

    @FindBy(xpath = "//div[@class='task ']//span[2]")
    private List<WebElement> sidebarElementList;

    @FindBy(xpath = "//*[@id='main-panel']/h1")
    private WebElement itemName;

    @FindBy(className = "textarea-show-preview")
    private WebElement previewOption;

    @FindBy(className = "textarea-preview")
    private WebElement previewDescriptionText;

    @FindBy(css = "[class*='task-link-wrapper'] [href$='/configure']")
    private WebElement sidebarConfigureButton;

    @FindBy(xpath = "//a[contains(@href, 'confirm-rename')]")
    private WebElement renameSidebarButton;

    @FindBy(xpath = "//span[contains(text(),'Delete')]")
    private WebElement deleteButtonSidebar;

    @FindBy(xpath = "//button[@data-id='cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement yesButton;

    @FindBy(xpath = "//button[@class='jenkins-menu-dropdown-chevron'][contains(@data-href,'job')]")
    private WebElement chevronButton;

    @FindBy(xpath = "//button[contains(@href,'Delete')]")
    private WebElement deleteBreadcrumbButton;

    @FindBy(xpath = "//div[@class='jenkins-dropdown']//a[contains(@href,'rename')]")
    private WebElement renameBreadcrumbButton;

    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    protected abstract ProjectConfigPage createProjectConfigPage();

    protected abstract ProjectRenamePage createProjectRenamePage();

    @Step("Edit description: clear input and type '{text}'")
    public Self editDescription(String text) {
        descriptionButton.click();
        descriptionField.clear();
        descriptionField.sendKeys(text);

        return (Self) this;
    }

    @Step("Click 'Save' button")
    public Self clickSubmitButton() {
        submitButton.click();

        return (Self) this;
    }

    @Step("Clear description and click 'Save'")
    public Self clearDescription() {
        descriptionButton.click();
        descriptionField.clear();
        submitButton.click();

        return (Self) this;
    }

    @Step("Click 'Preview'")
    public Self clickPreview() {
        previewOption.click();

        return (Self) this;
    }

    public String getPreviewDescriptionText() {
        return previewDescriptionText.getText();
    }

    public String getItemName() {
        return getWait10().until(ExpectedConditions.visibilityOf(itemName)).getText();
    }

    @Step("Get description from project page")
    public String getDescription() {
        return descriptionText.getText();
    }

    @Step("Get current text from 'Add/edit description button'")
    public String getDescriptionButtonText() {
        return descriptionButton.getText();
    }

    @Step("Get Sidebar item list")
    public List<String> getSidebarItemList() {
        return getWait5().until(ExpectedConditions.visibilityOfAllElements(sidebarElementList))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    @Step("Click 'Configure' sidebar")
    public ProjectConfigPage clickSidebarConfigButton() {
        getWait2().until(ExpectedConditions.elementToBeClickable(sidebarConfigureButton)).click();

        return createProjectConfigPage();
    }

    @Step("Click 'Delete' sidebar and confirm")
    public HomePage clickDeleteButtonSidebarAndConfirm() {
        getWait2().until(ExpectedConditions.elementToBeClickable(deleteButtonSidebar)).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(yesButton)).click();

        return new HomePage(getDriver());
    }

    @Step("Click 'Delete button' at sidebar and cancel")
    public Self clickDeleteButtonSidebarAndCancel() {
        getWait2().until(ExpectedConditions.elementToBeClickable(deleteButtonSidebar)).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(cancelButton)).click();

        return (Self) this;
    }

    @Step("Click 'Rename' sidebar")
    public ProjectRenamePage clickRenameSidebarButton() {
        getWait2().until(ExpectedConditions.elementToBeClickable(renameSidebarButton)).click();

        return createProjectRenamePage();
    }

    @Step("Open breadcrumb dropdown menu")
    public Self openBreadcrumbDropdown() {
        TestUtils.moveAndClickWithJS(getDriver(), chevronButton);

        return (Self) this;
    }

    @Step("Click 'Delete' breadcrumb item dropdown and confirm deletion")
    public HomePage clickDeleteBreadcrumbDropdownAndConfirm() {
        deleteBreadcrumbButton.click();
        yesButton.click();

        return new HomePage(getDriver());
    }

    @Step("Click 'Rename' in breadcrumb dropdown menu")
    public ProjectRenamePage clickRenameBreadcrumbDropdown(){
        renameBreadcrumbButton.click();

        return createProjectRenamePage();
    }
}
