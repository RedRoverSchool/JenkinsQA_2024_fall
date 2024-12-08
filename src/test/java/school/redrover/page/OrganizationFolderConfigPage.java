package school.redrover.page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BaseConfigPage;

public class OrganizationFolderConfigPage extends BaseConfigPage<OrganizationFolderConfigPage, OrganizationFolderProjectPage> {

    @FindBy(name = "_.displayNameOrNull")
    private WebElement displayNameInput;

    @FindBy(xpath = "//span/label[@for='enable-disable-project']")
    private WebElement enableDisableProjectLabel;

    @FindBy(xpath = "//div[@class='jenkins-app-bar__controls']/span")
    private WebElement tooltip;

    @FindBy(xpath = "//div/input[@name='_.displayNameOrNull']")
    private WebElement displayNameInputField;

    @FindBy(xpath = "//div/textarea[@name='_.description']")
    private WebElement descriptionInputField;

    @FindBy(xpath = "//div/div[@class='textarea-preview']")
    private WebElement textareaPreview;

    @FindBy(xpath = "//div/a[@class='textarea-show-preview']")
    private WebElement showPreviewLink;

    @FindBy(xpath = "//div/a[@class='textarea-hide-preview']")
    private WebElement hidePreviewLink;

    public OrganizationFolderConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected OrganizationFolderProjectPage createProjectPage() {
        return new OrganizationFolderProjectPage(getDriver());
    }

    public String getTooltipGeneralText() {

        Actions actions = new Actions(getDriver());
        actions.moveToElement(enableDisableProjectLabel).perform();

        return tooltip.getAttribute("tooltip");
    }

    public OrganizationFolderConfigPage enterName(String text) {
        displayNameInputField.sendKeys(text);

        return new OrganizationFolderConfigPage(getDriver());
    }

    public OrganizationFolderConfigPage enterDescription(String text) {
        descriptionInputField.sendKeys(text);

        return new OrganizationFolderConfigPage(getDriver());
    }

    public OrganizationFolderConfigPage changeDescriptionPreviewState() {

        if (textareaPreview.getAttribute("style").equals("display: none;")) {
            showPreviewLink.click();
        } else {
            hidePreviewLink.click();
        }

        return new OrganizationFolderConfigPage(getDriver());
    }

    public OrganizationFolderConfigPage setDisplayName(String name) {
        displayNameInput.sendKeys(name);

        return this;
    }

    public OrganizationFolderConfigPage editDisplayName(String name) {
        displayNameInput.sendKeys(Keys.LEFT_CONTROL + "a");
        displayNameInput.sendKeys(name);

        return this;
    }
}

