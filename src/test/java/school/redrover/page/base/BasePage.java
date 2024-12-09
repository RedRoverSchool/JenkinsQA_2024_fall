package school.redrover.page.base;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.*;

import java.util.List;

public abstract class BasePage extends BaseModel {

    @FindBy(id = "jenkins-home-link")
    private static WebElement logo;

    @FindBy(xpath = "//a[contains(@class,'main-search')]")
    private static WebElement iconQuestion;

    @FindBy(id = "search-box")
    private static WebElement searchBox;

    @FindBy(xpath = "//div[@class='yui-ac-bd']/ul/li[1]")
    private static WebElement firstSuggestion;

    @FindBy(css = "a[id='visible-am-button'] svg")
    private static WebElement iconBell;

    @FindBy(xpath = "//div[@class='am-list']/p/a")
    private static WebElement linkWithPopup;

    @FindBy(xpath = "//div[@class='yui-ac-bd']/ul/li[not(contains(@style, 'display: none'))]")
    private static List<WebElement> listSuggestion;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HomePage gotoHomePage() {
        logo.click();

        return new HomePage(getDriver());
    }

    public SearchBoxPage gotoSearchBox() {
        iconQuestion.click();

        return new SearchBoxPage(getDriver());
    }

    public HomePage enterSearch(String text) {
        searchBox.sendKeys(text);

        return (HomePage) this;
    }

    public SearchPage enter() {
        searchBox.sendKeys(Keys.ENTER);

        return new SearchPage(getDriver());
    }

    public LogPage enterGotoLogPage(String text) {
        searchBox.sendKeys(text, Keys.ENTER);

        return new LogPage(getDriver());
    }

    public ManageJenkinsPage resultManage(String manage) {
        enterSearch(manage);
        enter();

        return new ManageJenkinsPage(getDriver());
    }

    public HomePage getSuggestion() {
        getWait2().until(ExpectedConditions.elementToBeClickable(firstSuggestion));
        new Actions(getDriver()).moveToElement(firstSuggestion).click().perform();

        return (HomePage) this;
    }

    public HomePage clickBell() {
        iconBell.click();

        return (HomePage) this;
    }

    public ManageJenkinsPage clickLinkWithPopup() {
        linkWithPopup.click();

        return new ManageJenkinsPage(getDriver());
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

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

}
