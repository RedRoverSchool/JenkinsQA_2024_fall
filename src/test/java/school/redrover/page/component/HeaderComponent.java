package school.redrover.page.component;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.SearchBoxPage;
import school.redrover.page.SearchPage;
import school.redrover.page.base.BaseComponent;
import school.redrover.page.base.BasePage;
import school.redrover.page.home.HomePage;
import school.redrover.page.manage.ManageJenkinsPage;

public class HeaderComponent<T extends BasePage<T>> extends BaseComponent<T> {

    @FindBy(id = "jenkins-home-link")
    private WebElement logo;

    @FindBy(xpath = "//button[@data-keyboard-shortcut='CMD+K']")
    private WebElement search;

    @FindBy(xpath = "//*[@id='search-results']/a")
    private WebElement questionMark;

    @FindBy(xpath = "//input[@autocorrect='off']")
    private WebElement searchBox;

    @FindBy(xpath = "//div[@id='search-results']")
    private WebElement resultField;

    @FindBy(css = "a[id='visible-am-button'] svg")
    private WebElement iconBell;

    @FindBy(xpath = "//a[@href='/manage']")
    private WebElement manageJenkinsLink;

    @FindBy(xpath = "//p[@class='jenkins-command-palette__info']")
    private WebElement searchField;

    public HeaderComponent(WebDriver driver, T owner) {
        super(driver, owner);
    }

    @Step("Go to Home Page")
    public HomePage gotoHomePage() {
        logo.click();

        return new HomePage(getDriver());
    }

    @Step("Enter text into search field")
    public HeaderComponent<T> enterSearchText(String text) {
        getWait5().until(ExpectedConditions.elementToBeClickable(searchBox)).sendKeys(text);
        getWait5().until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@id='search-results']"), "Get help using Jenkins search")));

        return this;
    }

    @Step("Press Enter")
    public SearchPage pressEnter() {
        getWait5().until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(resultField))).click();

        return new SearchPage(getDriver());
    }

    @Step("Select first suggestion")
    public SearchPage selectFirstSuggestion() {
        getWait2().until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(resultField)));
        new Actions(getDriver()).moveToElement(resultField).click().perform();

        return new SearchPage(getDriver());
    }

    @Step("Click 'Bell' icon")
    public HeaderComponent<T> clickBell() {
        iconBell.click();

        return this;
    }

    @Step("Click 'Manage Jenkins' link")
    public ManageJenkinsPage clickManageJenkins() {
        manageJenkinsLink.click();

        return new ManageJenkinsPage(getDriver());
    }

    @Step("Click Search icon")
    public HeaderComponent<T> clickSearch() {
        getWait5().until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(search))).click();

        return this;
    }

    @Step("Click icon question in search field")
    public SearchBoxPage clickQuestionMark() {
        questionMark.click();

        return new SearchBoxPage(getDriver());
    }

    @Step("Get text from search field")
    public String getSearchFieldText() {
        return searchField.getText();
    }
}
