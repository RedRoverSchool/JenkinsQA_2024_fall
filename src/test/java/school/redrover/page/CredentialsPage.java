package school.redrover.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

public class CredentialsPage extends BasePage {

    @FindBy(css = "h1")
    private WebElement pageTitle;

    @FindBy(xpath ="//a[@href='/user/admin/credentials/store/user']")
    private WebElement userName;

    @FindBy(xpath = "//a[@href='/user/admin/credentials/store/user']/button")
    private WebElement dropdownMenu;

    public CredentialsPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitleText() {
        return pageTitle.getText();
    }

    public String getUserName() {
        return userName.getText();
    }

    public CredentialsPage clickDropdownMenu() {
        TestUtils.moveAndClickWithJS(getDriver(), dropdownMenu);
        return this;
    }

    public boolean getDisplayedItemMenu () {
        return dropdownMenu.isDisplayed();
    }

}
