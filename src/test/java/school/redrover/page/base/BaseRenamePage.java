package school.redrover.page.base;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.ErrorPage;

public abstract class BaseRenamePage<Self extends BaseRenamePage<?, ?>, ProjectPage extends BaseProjectPage<?, ?, ?>> extends BasePage<BaseRenamePage<?, ?>> {

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

    @Step("Clear input and type '{newName}' to name input field")
    public Self clearInputFieldAndTypeName(String newName) {
        inputField.clear();
        inputField.sendKeys(newName);

        return (Self) this;
    }

    @Step("Get warning message")
    public String getWarningMessage() {
        return getWait5().until(ExpectedConditions.visibilityOf(warningMessage)).getText();
    }

    @Step("Click 'Rename' button")
    public ProjectPage clickRenameButton() {
        renameButton.click();

        return createProjectPage();
    }

    @Step("Click 'Rename' button")
    public ErrorPage clickRenameButtonLeadingToError() {
        renameButton.click();

        return new ErrorPage(getDriver());
    }
}
