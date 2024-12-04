package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import school.redrover.page.base.BasePage;

public class OrganizationFolderConfigurationPage extends BasePage {

    public OrganizationFolderConfigurationPage(WebDriver driver) {
        super(driver);
    }

    private final By LOGO_JENKINS = By.id("jenkins-home-link");
    private final By DISPLAY_NAME = By.name("_.displayNameOrNull");
    private final By BUTTON_SAVE = By.name("Submit");

    public String getTooltipGeneralText() {

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.xpath("//span/label[@for='enable-disable-project']"))).perform();
        String tooltipText = getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__controls']/span")).getAttribute("tooltip");
        return tooltipText;
    }

    public OrganizationFolderConfigurationPage enterName(String text) {

        getDriver().findElement(By.xpath("//div/input[@name='_.displayNameOrNull']")).sendKeys(text);
        return new OrganizationFolderConfigurationPage(getDriver());
    }

    public OrganizationFolderConfigurationPage enterDescription(String text) {

        getDriver().findElement(By.xpath("//div/textarea[@name='_.description']")).sendKeys(text);
        return new OrganizationFolderConfigurationPage(getDriver());
    }

    public OrganizationFolderConfigurationPage changeDescriptionPreviewState() {

        if (getDriver()
                .findElement(By.xpath("//div/div[@class='textarea-preview']"))
                .getAttribute("style").equals("display: none;")) {
            getDriver().findElement(By.xpath("//div/a[@class='textarea-show-preview']")).click();
        } else {
            getDriver().findElement(By.xpath("//div/a[@class='textarea-hide-preview']")).click();
        }
        return new OrganizationFolderConfigurationPage(getDriver());
    }

    public HomePage clickLogoJenkins() {
        getDriver().findElement(LOGO_JENKINS).click();

        return new HomePage(getDriver());
    }

    public OrganizationFolderConfigurationPage setDisplayName(String name) {
        getDriver().findElement(DISPLAY_NAME).sendKeys(name);

        return this;
    }

    public OrganizationFolderPage clickSave() {
        getDriver().findElement(BUTTON_SAVE).click();

        return new OrganizationFolderPage(getDriver());
    }
}

