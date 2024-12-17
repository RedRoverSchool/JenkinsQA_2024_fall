package school.redrover.page.organizationFolder;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.page.base.BaseConfigPage;
import java.util.List;

public class OrganizationFolderConfigPage extends BaseConfigPage<OrganizationFolderConfigPage, OrganizationFolderProjectPage> {

    @FindBy(xpath = "//span/label[@for='enable-disable-project']")
    private WebElement enableDisableProjectLabel;

    @FindBy(xpath = "//div[@class='jenkins-app-bar__controls']/span")
    private WebElement tooltip;

    @FindBy(xpath = "(//select[contains(@class, 'dropdownList')])[2]")
    private WebElement iconOptions;

    @FindBy(xpath = "//button[@title='Delete']")
    private WebElement closeButton;

    @FindBy(xpath = "//div/div[@class='textarea-preview']")
    private WebElement textareaPreview;

    @FindBy(xpath = "//div/a[@class='textarea-show-preview']")
    private WebElement showPreviewLink;

    @FindBy(xpath = "//div/a[@class='textarea-hide-preview']")
    private WebElement hidePreviewLink;

    @FindBy(xpath = "//div[@id='tasks']/div")
    private List<WebElement> sidebarItemsNameList;

    @FindBy(xpath = "//a[@tooltip='Help for feature: Display Name']")
    private WebElement questionMark;

    @FindBy(xpath = "//div[@class='tippy-content']")
    private WebElement questionMarkToolTip;

    public OrganizationFolderConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected OrganizationFolderProjectPage createProjectPage() {
        return new OrganizationFolderProjectPage(getDriver());
    }

    public String getTooltipGeneralText() {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(enableDisableProjectLabel).perform();

        return tooltip.getAttribute("tooltip");
    }

    public String getPreviewStyleAttribute() {
        return textareaPreview.getAttribute("style");
    }

    public OrganizationFolderConfigPage selectDefaultIcon() {
        new Select(iconOptions).selectByVisibleText("Default Icon");

        return this;
    }

    public String getCloseButtonTooltip() {
        return closeButton.getAttribute("tooltip");
    }

    public List<String> getListOfItemsSidebar() {
        return sidebarItemsNameList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public OrganizationFolderConfigPage hoverOverDisplayNameQuestionMark() {
        new Actions(getDriver())
                .moveToElement(getWait10().until(ExpectedConditions.visibilityOf(questionMark)))
                .perform();

        return this;
    }

    public String getQuestionMarkTooltipText() {
        return getWait10().until(ExpectedConditions.visibilityOf(questionMarkToolTip)).getText();
    }

    public boolean toolTipQuestionMarkVisible() {
        return getWait10().until(ExpectedConditions.visibilityOf(questionMarkToolTip)).isDisplayed();
    }
}