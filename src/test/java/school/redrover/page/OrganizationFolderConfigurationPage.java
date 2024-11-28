package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import school.redrover.page.base.BasePage;

public class OrganizationFolderConfigurationPage extends BasePage {

    public OrganizationFolderConfigurationPage(WebDriver driver) {
        super(driver);
    }

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
        }
        else {
            getDriver().findElement(By.xpath("//div/a[@class='textarea-hide-preview']")).click();
        }
        return new OrganizationFolderConfigurationPage(getDriver());
    }


}

