package school.redrover.page.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.ErrorPage;

public abstract class BaseRenamePage<Self extends BaseRenamePage<?, ?>, ProjectPage extends BaseProjectPage> extends BasePage {

    @FindBy(xpath = "//input[@checkdependson ='newName']")
    private WebElement inputField;

    @FindBy(name = "Submit")
    private WebElement renameButton;

    @FindBy(xpath = "//div[@class='validation-error-area validation-error-area--visible']")
    private WebElement warningMessage;

    public BaseRenamePage(WebDriver driver) {
        super(driver);
    }

    protected abstract ProjectPage createProjectPage();

    public Self clearInputFieldAndTypeName(String newName) {
        inputField.clear();
        inputField.sendKeys(newName);

        return (Self) this;
    }

    public String getWarningMessage() {
        return getWait5().until(ExpectedConditions.visibilityOf(warningMessage)).getText();
    }

    public ProjectPage clickRenameButton() {
        renameButton.click();

        return createProjectPage();
    }

    public ErrorPage clickRenameButtonLeadingToError() {
        renameButton.click();

        return new ErrorPage(getDriver());
    }
}
