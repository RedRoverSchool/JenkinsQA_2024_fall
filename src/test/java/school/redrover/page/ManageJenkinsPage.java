package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

import java.util.List;

public class ManageJenkinsPage extends BasePage {

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public PluginsPage openPluginsPage() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//a[@href='pluginManager']")))).click();

        return new PluginsPage(getDriver());
    }

    public UsersPage openUsersPage() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//a[@href='securityRealm/']")))).click();

        return new UsersPage(getDriver());
    }

    public List<String> getAllSections() {
        List<WebElement> sections = getDriver().findElements(By.xpath("//h2[@class='jenkins-section__title']"));

        return sections.stream().map(WebElement::getText).toList();
    }

    public List<String> getSystemConfigurationItems() {
        List<WebElement> systemConfigItems = getDriver()
                                                 .findElements(By.xpath("(//div[@class='jenkins-section__items'])[1]//dt"));

       return systemConfigItems.stream().map(WebElement::getText).toList();
    }

    public SystemPage clickSystemSection() {
        getDriver().findElement(By.cssSelector("a[href='configure']")).click();

        return new SystemPage(getDriver());
    }

    public ManageJenkinsPage typeSearchInputField(String text) {
        getDriver().findElement(By.cssSelector("input[id=settings-search-bar]")).sendKeys(text);

        return this;
    }

    public String getNoResultLabelText() {
        WebElement noResultLabel = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class$='no-results-label']")));

        return noResultLabel.getText();
    }

    public CloudsPage pressEnterAfterInput(String text) {
        WebElement searchSettingInput = getDriver().findElement(By.cssSelector("input[id=settings-search-bar]"));

        searchSettingInput.sendKeys(text);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-search__results-item--selected")));

        searchSettingInput.sendKeys(Keys.ENTER);

        return new CloudsPage(getDriver());
    }

    public boolean isHiddenShortcutTooltipDisplayed() {
        WebElement tooltip = getDriver().findElement(By.cssSelector("[class='jenkins-search__shortcut']"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(tooltip).perform();

        WebElement hiddenTooltipValue = getWait5().until(ExpectedConditions
                                                  .visibilityOfElementLocated(By.xpath("//div[@aria-describedby='tippy-6']")));

        return hiddenTooltipValue.isDisplayed();
    }

    public ManageJenkinsPage switchFocusToSearchFieldAndTypeText(String text) {
        Actions actions = new Actions(getDriver());
        actions.sendKeys(Keys.chord("/", text)).perform();

        return this;
    }

    public String getInputText() {

        return getDriver().findElement(By.cssSelector("input[id='settings-search-bar']")).getAttribute("value");
    }

    public boolean isSearchSettingFieldDisplayed() {

        return  getDriver().findElement(By.cssSelector("input[id='settings-search-bar']")).isDisplayed();
    }

    public List<String> getSearchResults() {

        List<WebElement> searchResultElements = getWait5()
                                                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                                        By.xpath("//div[@class='jenkins-search__results']//a")));

        return searchResultElements.stream().map(WebElement::getText).toList();
    }

    public CredentialsConfigurePage clickConfigureCredentialsItem() {
        WebElement configureCredentialsItem = getWait5()
                                                  .until(ExpectedConditions.visibilityOfElementLocated(
                                                      By.cssSelector("a[href$='configureCredentials']")));
        configureCredentialsItem.click();

        return new CredentialsConfigurePage(getDriver());
    }
}