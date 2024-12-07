package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BaseConfigPage;

public class OrganizationFolderConfigPage extends BaseConfigPage<OrganizationFolderConfigPage, OrganizationFolderProjectPage> {

    @FindBy(name = "_.displayNameOrNull")
    private WebElement displayNameInput;

    public OrganizationFolderConfigPage(WebDriver driver) {
        super(driver);
    }

    private static final By DISPLAY_NAME = By.name("_.displayNameOrNull");

    @Override
    protected OrganizationFolderProjectPage createProjectPage() {
        return new OrganizationFolderProjectPage(getDriver());
    }

    public String getTooltipGeneralText() {

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.xpath("//span/label[@for='enable-disable-project']"))).perform();
        String tooltipText = getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__controls']/span")).getAttribute("tooltip");

        return tooltipText;
    }

    public OrganizationFolderConfigPage enterName(String text) {
        getDriver().findElement(By.xpath("//div/input[@name='_.displayNameOrNull']")).sendKeys(text);

        return new OrganizationFolderConfigPage(getDriver());
    }

    public OrganizationFolderConfigPage enterDescription(String text) {
        getDriver().findElement(By.xpath("//div/textarea[@name='_.description']")).sendKeys(text);

        return new OrganizationFolderConfigPage(getDriver());
    }

    public OrganizationFolderConfigPage changeDescriptionPreviewState() {

        if (getDriver().findElement(By.xpath("//div/div[@class='textarea-preview']")).getAttribute("style").equals("display: none;")) {
            getDriver().findElement(By.xpath("//div/a[@class='textarea-show-preview']")).click();
        } else {
            getDriver().findElement(By.xpath("//div/a[@class='textarea-hide-preview']")).click();
        }
        return new OrganizationFolderConfigPage(getDriver());
    }

    public OrganizationFolderConfigPage setDisplayName(String name) {
        getDriver().findElement(DISPLAY_NAME).sendKeys(name);

        return this;
    }

    public OrganizationFolderConfigPage editDisplayName(String name) {
        displayNameInput.sendKeys(Keys.LEFT_CONTROL + "a");
        displayNameInput.sendKeys(name);

        return this;
    }
}

