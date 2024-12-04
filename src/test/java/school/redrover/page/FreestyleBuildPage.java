package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class FreestyleBuildPage extends BasePage {

    private static final By DISPLAY_NAME_FIELD = By.xpath("//input[@name='displayName']");

    private static final By DESCRIPTION_FIELD = By.name("description");

    public FreestyleBuildPage(WebDriver driver) {
        super(driver);
    }

    public String getStatusTitle() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("svg[tooltip='Success']")));
        return getDriver().findElement(By.tagName("h1")).getText();
    }

    public String getBuildDescription() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("description"))).getText();
    }

    public String getConsoleOutputText() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("out"))).getText();
    }

    public FreestyleBuildPage clickEditBuildInformationSidebar() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[contains(text(), 'Edit Build Information')]/.."))).click();

        return this;
    }

    public FreestyleBuildPage addDisplayName(String name) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(DISPLAY_NAME_FIELD)).sendKeys(name);

        return this;
    }

    public FreestyleBuildPage editDisplayName(String newName) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(DISPLAY_NAME_FIELD)).clear();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(DISPLAY_NAME_FIELD)).sendKeys(newName);

        return this;
    }

    public FreestyleBuildPage addBuildDescription(String description) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("description"))).sendKeys(description);

        return this;
    }

    public FreestyleBuildPage editBuildDescription(String newDescription) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(DESCRIPTION_FIELD)).clear();
        getDriver().findElement(DESCRIPTION_FIELD).sendKeys(newDescription);

        return this;
    }

    public FreestyleBuildPage clickSaveButton() {
        getDriver().findElement(By.name("Submit")).click();

        return this;
    }

    public FreestyleBuildPage clickDeleteBuildSidebar() {
        getDriver().findElement(By.xpath("//span[contains(text(), 'Delete build')]/..")).click();

        return this;
    }

    public FreestyleProjectPage confirmDeleteBuild() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit"))).click();

        return new FreestyleProjectPage(getDriver());
    }
}
