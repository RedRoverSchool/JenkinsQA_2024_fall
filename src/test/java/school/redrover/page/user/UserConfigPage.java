package school.redrover.page.user;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.page.base.BasePage;
import school.redrover.page.home.HomePage;

public class UserConfigPage extends BasePage<UserConfigPage> {

    @FindBy(xpath = "//a[@title='Help for feature: Full Name']")
    private WebElement fullNameTooltip;

    @FindBy(xpath = "//div[@class='help']/div")
    private WebElement fullNameInputHelper;

    @FindBy(xpath = "//span[contains(@class,'task-link-wrapper')]/a[@href='/user/admin/']")
    private WebElement statusSidebarButton;

    @FindBy(xpath = "//span[text()='Account']/..")
    private WebElement accountSidebarButton;

    @FindBy(xpath = "//a[@id='description-link']")
    private WebElement descriptionButton;

    @FindBy (xpath = "//textarea[@name='description']")
    private WebElement descriptionField;

    @FindBy (xpath = "//button[@name='Submit']")
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

    @Step("Click 'Status' at sidebar")
    public UserPage clickStatusSidebar() {
        getWait2().until(ExpectedConditions.elementToBeClickable(statusSidebarButton)).click();

        return new UserPage(getDriver());
    }

    public UserConfigPage enterDescription (String description) {
        descriptionButton.click();
        descriptionField.sendKeys(description);
        return this;
    }

    @Step("Click save button")
    public UserPage submitButton() {
        submitButton.click();
        return new UserPage(getDriver());
    }

    @Step("Click 'Account' at sidebar")
    public UserConfigPage clickAccountSidebar() {
        getWait5().until(ExpectedConditions.elementToBeClickable(accountSidebarButton)).click();

        return this;
    }

    @Step("Add user time zone")
    public UserConfigPage addUserTimeZone(String value) {
        Select timeZone = new Select(selectTimeZone);
        timeZone.selectByValue(value);
        submitButton.click();
        return new UserConfigPage(getDriver());
    }

    @Step("Get user time zone")
    public String getUserTimeZone() {
        return selectTimeZone.getAttribute("value");
    }

    @Step("Delete user from configure user page")
    public HomePage deleteUserFromSidePanelAndClickOk() {
        deleteUserSidebarButton.click();
        okToDeleteButton.click();
        return new HomePage(getDriver());
    }

}