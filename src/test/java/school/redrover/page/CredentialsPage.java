package school.redrover.page;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

public class CredentialsPage extends BasePage {

    @FindBy(css = "h1")
    private WebElement pageTitle;

    @FindBy(xpath ="//table[2]/tbody/tr/td[2]/a")
    private WebElement userName;

    @FindBy(xpath = "//tbody/tr/td[2]/a/button")
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
    @Step("Get displayed item menu")
    public boolean getDisplayedItemMenu () {
        return dropdownMenu.isDisplayed();
    }
    @Step("Click dropdown menu of user")
    public CredentialsPage clickDropdownMenu() {
        TestUtils.moveAndClickWithJS(getDriver(), dropdownMenu);
        return this;
    }

}
