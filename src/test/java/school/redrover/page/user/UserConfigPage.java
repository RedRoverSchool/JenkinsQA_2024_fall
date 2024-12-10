package school.redrover.page.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class UserConfigPage extends BasePage {

    @FindBy(xpath = "//a[@title='Help for feature: Full Name']")
    private WebElement fullNameTooltip;

    @FindBy(xpath = "//div[@class='help']/div")
    private WebElement fullNameInputHelper;

    @FindBy(xpath = "//span[contains(@class,'task-link-wrapper')]/a[@href='/user/admin/']")
    private WebElement statusSidebarButton;

    public UserConfigPage(WebDriver driver) {
        super(driver);
    }

    public UserConfigPage clickFullNameTooltip() {
        getWait2().until(ExpectedConditions.elementToBeClickable(fullNameTooltip)).click();

        return this;
    }

    public String getFullNameHelperInputText() {
        return getWait2().until(ExpectedConditions.visibilityOf(fullNameInputHelper)).getText();
    }

    public UserPage clickStatusSidebar() {
        getWait2().until(ExpectedConditions.elementToBeClickable(statusSidebarButton)).click();

        return new UserPage(getDriver());
    }
}
