package school.redrover.page.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.page.base.BasePage;
import school.redrover.page.home.HomePage;

public class UserConfigPage extends BasePage {

    @FindBy(xpath = "//a[@title='Help for feature: Full Name']")
    private WebElement fullNameTooltip;

    @FindBy(xpath = "//div[@class='help']/div")
    private WebElement fullNameInputHelper;

    @FindBy(xpath = "//span[contains(@class,'task-link-wrapper')]/a[@href='/user/admin/']")
    private WebElement statusSidebarButton;

    @FindBy (xpath = "//textarea[@name='_.description']")
    private WebElement descriptionField;

    @FindBy (xpath = "//button[@name='Submit'] ")
    private WebElement submitButton;

    @FindBy (xpath = "//select[@checkdependson='timeZoneName']")
    private WebElement selectTimeZone;

    @FindBy (xpath = "//a[@data-title='Delete']")
    private WebElement deleteUserSidebarButton;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement okToDeleteButton;

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

    public UserPage addUserDescription () {
        descriptionField.sendKeys("User Description");
        submitButton.click();
        return new UserPage(getDriver());
    }

    public UserPage addUserTimeZone() {
        Select timeZone = new Select(selectTimeZone);
        timeZone.selectByValue("Etc/GMT+2");
        submitButton.click();
        return new UserPage(getDriver());
    }

    public String getUserTimeZone() {
        return selectTimeZone.getAttribute("value");
    }

    public HomePage deleteUserFromConfigureUserPage() {
        deleteUserSidebarButton.click();
        okToDeleteButton.click();
        return new HomePage(getDriver());

    }

}
