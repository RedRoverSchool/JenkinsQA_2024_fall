package school.redrover.page.user;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class UserPage extends BasePage<UserPage> {

    @FindBy(xpath = "//a[@href='/user/admin/configure']")
    private WebElement configureAdminSidebar;

    @FindBy(xpath = "//div[@id='main-panel']/div[3]")
    private WebElement adminDescription;

    @FindBy(css = "#description")
    private WebElement userDescription;

    public UserPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get user ID text")
    public String getUserIDText() {
        return getWait2().until(ExpectedConditions.visibilityOf(adminDescription)).getText();
    }

    @Step("Get user description")
    public String getUserDescription() {
        return userDescription.getText();
    }
}
