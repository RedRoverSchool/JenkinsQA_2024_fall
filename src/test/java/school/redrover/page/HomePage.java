package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void createFreestyleProject(String name) {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.name("name")).sendKeys(name);
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    public ProjectPage openProject(String name) {
        getDriver().findElement(By.xpath("//td/a/span[text() = '%s']/..".formatted(name))).click();

        return new ProjectPage(getDriver());
    }

    public ManageJenkinsPage openManageJenkinsPage() {
        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();

        return new ManageJenkinsPage(getDriver());
    }

    public CreateNewItemPage clickNewItem() {
        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();

        return new CreateNewItemPage(getDriver());
    }

    public String getItemNameByOrder(int order) {

        return getDriver().findElements(By.xpath("//td/a/span")).stream()
                .skip(order - 1)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Некорректный порядок: " + order))
                .getText();
    }

    public void selectMenuFromItemDropdown (String itemName, String menuName) {
        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath("//td/a/span[text() = '%s']/.."
                .formatted(itemName)))).perform();
        TestUtils.moveAndClickWithJavaScript(getDriver(), getDriver().findElement(
                By.xpath("//td/a/span[text() = '%s']/../button".formatted(itemName))));

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='jenkins-dropdown__item__icon']/parent::*[contains(., '%s')]"
                        .formatted(menuName)))).click();
    }

    public ConfigurationPage selectConfigureFromItemMenu(String itemName) {
        selectMenuFromItemDropdown(itemName, "Configure");

        return new ConfigurationPage(getDriver());
    }

    public CreateNewItemPage selectNewItemFromFolderMenu(String itemName) {
        selectMenuFromItemDropdown(itemName, "New Item");

        return new CreateNewItemPage(getDriver());
    }

    public BuildHistoryPage selectBuildHistoryFromItemMenu(String itemName) {
        selectMenuFromItemDropdown(itemName, "Build History");

        return new  BuildHistoryPage(getDriver());
    }

    public HomePage openDropdownViaChevron(String projectName) {
        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath("//a[@href='job/%s/']/span".formatted(projectName))))
                .pause(500)
                .perform();
        WebElement chevron = getDriver().findElement(By.xpath("//td//button[@aria-expanded='false']"));
        TestUtils.moveAndClickWithJavaScript(getDriver(), chevron);
        getWait5().until(ExpectedConditions.attributeToBe(chevron, "aria-expanded", "true"));

        return this;
    }

    public String getItemName() {

        return getDriver().findElement(By.xpath("//a[contains(@class,'jenkins-table')]")).getText();
    }
}
