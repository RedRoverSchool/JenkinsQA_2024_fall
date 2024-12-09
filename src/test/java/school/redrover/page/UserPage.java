package school.redrover.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;


public class UserPage extends BasePage {

    @FindBy(xpath = "//a[@href='/user/admin/configure']")
    private WebElement configureSidebar;

    @FindBy(xpath = "//div[@id='main-panel']/div[3]")
    private WebElement adminDescription;

    public UserPage(WebDriver driver) {
        super(driver);
    }

    public UserConfigPage clickConfigureSidebar() {
        getWait2().until(ExpectedConditions.elementToBeClickable(configureSidebar)).click();

        return new UserConfigPage(getDriver());
    }

    public String getUserIDText() {
        return getWait2().until(ExpectedConditions.visibilityOf(adminDescription)).getText();
    }
}
