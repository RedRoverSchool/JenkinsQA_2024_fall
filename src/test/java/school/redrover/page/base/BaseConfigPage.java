package school.redrover.page.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseConfigPage<Self extends BaseConfigPage<?, ?>, ProjectPage extends BaseProjectPage> extends BasePage {

    @FindBy(css = "[name$='description']")
    private WebElement descriptionField;

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(xpath = "//div/input[@name='_.displayNameOrNull']")
    private WebElement displayNameInputField;

    @FindBy(xpath = "//div/div[@class='textarea-preview']")
    private WebElement textareaPreview;

    @FindBy(xpath = "//div/a[@class='textarea-show-preview']")
    private WebElement showPreviewLink;

    @FindBy(xpath = "//div/a[@class='textarea-hide-preview']")
    private WebElement hidePreviewLink;

    @FindBy(xpath = "//div/div[@class='textarea-preview']")
    private WebElement descriptionPreviewText;

    public BaseConfigPage(WebDriver driver) {
        super(driver);
    }

    protected abstract ProjectPage createProjectPage();

    public Self enterDescription(String description) {
        descriptionField.sendKeys(description);

        return (Self) this;
    }

    public ProjectPage clickSaveButton() {
        saveButton.click();

        return createProjectPage();
    }

    public Self enterDisplayName(String text) {
        displayNameInputField.sendKeys(text);

        return (Self) this;
    }

    public Self changeDescriptionPreviewState() {

        if (textareaPreview.getAttribute("style").equals("display: none;")) {
            showPreviewLink.click();
        } else {
            hidePreviewLink.click();
        }

        return (Self) this;
    }

    public String getDescriptionPreviewText() {
        return descriptionPreviewText.getText();
    }
}
