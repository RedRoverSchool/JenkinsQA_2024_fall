package school.redrover.page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseComponent;
import school.redrover.page.base.BasePage;

import java.util.List;

public class HeaderComponent<Target extends BasePage<Target>> extends BaseComponent<Target> {

    @FindBy(id = "jenkins-home-link")
    private WebElement logo;

    @FindBy(xpath = "(//button[@class='jenkins-menu-dropdown-chevron'])[1]")
    private WebElement adminDropdown;

    @FindBy(xpath = "//a[contains(@href,'/user/admin/configure')]")
    private WebElement configureAdminDropdown;

    @FindBy(xpath = "//*[@href='/user/admin']")
    private WebElement admin;

    @FindBy(xpath = "//a[contains(@href,'/user/admin/credentials')]")
    private WebElement credentialsDropdown;

    @FindBy(css = "a[href^='/logout']")
    private WebElement logOut;

    @FindBy(css = "a[id='visible-am-button'] svg")
    private WebElement iconBell;

    @FindBy(id = "search-box")
    private WebElement searchBox;

    @FindBy(xpath = "//a[contains(@class,'main-search')]")
    private WebElement iconQuestion;

    @FindBy(xpath = "//div[@class='yui-ac-bd']/ul/li[1]")
    private WebElement firstSuggestion;

    @FindBy(xpath = "//div[@class='am-list']/p/a")
    private WebElement linkWithPopup;

    @FindBy(xpath = "//div[@class='yui-ac-bd']/ul/li[not(contains(@style, 'display: none'))]")
    private List<WebElement> listSuggestion;

    public HeaderComponent(WebDriver driver, Target owner) {
        super(driver, owner);
    }

    public HomePage gotoHomePage() {
        logo.click();

        return new HomePage(getDriver());
    }

    public SearchPage enter() {
        searchBox.sendKeys(Keys.ENTER);

        return new SearchPage(getDriver());
    }

    public Target enterSearch(String text) {
        searchBox.sendKeys(text);

        return getReturnPage();
    }

    public SearchBoxPage gotoSearchBox() {
        iconQuestion.click();

        return new SearchBoxPage(getDriver());
    }

    public Target getSuggestion() {
        getWait2().until(ExpectedConditions.elementToBeClickable(firstSuggestion));
        new Actions(getDriver()).moveToElement(firstSuggestion).click().perform();

        return getReturnPage();
    }

    public ManageJenkinsPage resultManage(String manage) {
        enterSearch(manage);
        enter();

        return new ManageJenkinsPage(getDriver());
    }

    public Target clickBell() {
        iconBell.click();

        return getReturnPage();
    }

    public ManageJenkinsPage clickLinkWithPopup() {
        linkWithPopup.click();

        return new ManageJenkinsPage(getDriver());
    }

    public LogPage enterGotoLogPage(String text) {
        searchBox.sendKeys(text, Keys.ENTER);

        return new LogPage(getDriver());
    }

    public List<String> getListSuggestion() {
        getWait2().until(ExpectedConditions.elementToBeClickable(firstSuggestion));

        return listSuggestion.stream().map(WebElement::getText).toList();
    }

    public ManageJenkinsPage getResultManage() {
        getWait2().until(ExpectedConditions.elementToBeClickable(firstSuggestion));
        enterSearch(Keys.BACK_SPACE + listSuggestion.stream().map(WebElement::getText).toList().get(2));
        enter();

        return new ManageJenkinsPage(getDriver());
    }

    public UserPage clickAdmin() {
        admin.click();

        return new UserPage(getDriver());
    }

    public Target openAdminDropdownMenu() {
        new Actions(getDriver()).moveToElement(adminDropdown).click().perform();

        return getReturnPage();
    }

    public UserConfigPage clickConfigureAdminDropdownMenu() {
        getWait2().until(ExpectedConditions.elementToBeClickable(configureAdminDropdown)).click();

        return new UserConfigPage(getDriver());
    }

    public SignInPage clickLogOut() {
        logOut.click();

        return new SignInPage(getDriver());
    }

    public CredentialsPage clickCredentialsAdminDropdownMenu() {
        getWait2().until(ExpectedConditions.elementToBeClickable(credentialsDropdown)).click();

        return new CredentialsPage(getDriver());
    }
}
