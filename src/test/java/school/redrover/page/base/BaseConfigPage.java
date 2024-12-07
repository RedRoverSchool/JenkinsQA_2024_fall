package school.redrover.page.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseConfigPage<Self extends BaseConfigPage<?, ?>, ProjectPage extends BaseProjectPage> extends BasePage {

    @FindBy(css = "[name$='description']")
    private WebElement descriptionField;

    @FindBy(name = "Submit")
    private WebElement saveButton;

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
}
