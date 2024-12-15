package school.redrover.page.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class UserPage extends BasePage {

    @FindBy(xpath = "//a[@href='/user/admin/configure']")
    private WebElement configureAdminSidebar;

    @FindBy(css = "a[href*='configure']")
    private WebElement configureUserSidebar;

    @FindBy(xpath = "//div[@id='main-panel']/div[3]")
    private WebElement adminDescription;

    @FindBy(css = "#description")
    private WebElement userDescription;

    public UserPage(WebDriver driver) {
        super(driver);
    }

    public UserConfigPage clickConfigureSidebar() {
        getWait2().until(ExpectedConditions.elementToBeClickable(configureAdminSidebar)).click();

        return new UserConfigPage(getDriver());
    }

    public UserConfigPage clickConfigureUserSidebar() {
        getWait2().until(ExpectedConditions.elementToBeClickable(configureUserSidebar)).click();

        return new UserConfigPage(getDriver());
    }

    public String getUserIDText() {
        return getWait2().until(ExpectedConditions.visibilityOf(adminDescription)).getText();
    }

    public String getUserDescription() {
        return userDescription.getText();
    }
}
