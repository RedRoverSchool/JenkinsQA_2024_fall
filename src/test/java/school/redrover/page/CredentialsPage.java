package school.redrover.page;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

public class CredentialsPage extends BasePage<CredentialsPage> {

    @FindBy(css = "h1")
    private WebElement pageTitle;

    @FindBy(xpath ="//a[@href='/user/admin/credentials/store/user']")
    private WebElement userName;

    @FindBy(xpath = "//a[@href='/user/admin/credentials/store/user']/button")
    private WebElement dropdownMenu;

    public CredentialsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get page title text")
    public String getPageTitleText() {
        return pageTitle.getText();
    }

    @Step("Get user name")
    public String getUserName() {
        return userName.getText();
    }

    @Step("Click dropdown menu")
    public CredentialsPage clickDropdownMenu() {
        TestUtils.moveAndClickWithJS(getDriver(), dropdownMenu);
        return this;
    }

    @Step("Get displayed item menu")
    public boolean getDisplayedItemMenu () {
        return dropdownMenu.isDisplayed();
    }
}
