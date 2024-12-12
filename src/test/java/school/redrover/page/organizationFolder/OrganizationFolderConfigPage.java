package school.redrover.page.organizationFolder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import school.redrover.page.base.BaseConfigPage;
import school.redrover.page.home.HomePage;


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
}

