package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;
import java.util.List;

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

    public HomePage createNewFolder(String name) {

        getDriver().findElement(By.xpath("//a[@href = 'newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(name);
        getDriver().findElement(By.xpath("//span[text() = 'Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Apply")).click();

        getDriver().findElement(By.xpath("//a[contains(text(), 'Dashboard')]")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.id("projectstatus"))));

        return this;
    }

    public HomePage crateDescription(String text) {

        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.xpath("//textarea[contains(@class, 'jenkins-input')]")).sendKeys(text);

        getDriver().findElement(By.name("Submit")).click();

        return this;
    }

    public HomePage deleteFolder(String name) {
        getDriver().findElement(By.xpath("//span[text()='" + name + "']")).click();

        getDriver().findElement(By.xpath("//span[text()='Delete Folder']")).click();
        getDriver().findElement(By.xpath("//button[@data-id= 'ok']")).click();

        return this;
    }

    public HomePage deleteFolderViaChevron(String name) {

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.xpath("//span[contains(text(), '" + name + "')]")))
                .perform();

        TestUtils.moveAndClickWithJavaScript(getDriver(),
                getDriver().findElement(By.xpath("//button[@data-href='http://localhost:8080/job/" + name + "/']")) );

        WebElement deleteButton = getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(@href, 'doDelete')]")));
        actions
                .moveToElement(deleteButton)
                .click()
                .perform();

        WebElement buttonOk = getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath(
                "//button[@data-id= 'ok']"))));
        buttonOk.click();

        return this;
    }

    public String getFirstFolderName() {
        return getDriver().findElement(By.className("jenkins-table__link")).getText();
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

    public String getWelcomeText() {
        return getDriver().findElement(By.xpath("//p[contains(text(), 'This page is where')]")).getText();
    }

    public String getDescriptionText() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='description']/div[1]"))).getText();
    }

    public List<WebElement> getSideContent() {

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[contains(text(), 'My Views')]")));

        return getDriver().findElements(By.xpath("//a[contains(@class, 'task-link-no-confirm')]"));
    }

    public List<WebElement> getContentBlock() {

        return getDriver().findElements(By.className("content-block"));
    }

    public List<String> getItemList() {

        return getDriver().findElements(By.xpath("//td/a[contains(@href,'job/')]"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public MultibranchPipelineProjectPage clickOnCreatedItem(String itemName) {
        getDriver().findElement(
                By.xpath(("//a[contains(@href,'%s')][@class='jenkins-table__link model-link inside']")
                        .formatted(itemName))).click();

        return new MultibranchPipelineProjectPage(getDriver());
    }

    public boolean isDisableCircleSignPresent(String name) {
        try {
            WebElement disableCircleSign = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//tr[@id='job_%s']//*[@tooltip='Disabled']".formatted(name))));

            return disableCircleSign.isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isGreenScheduleBuildTrianglePresent(String name) {
        return !getDriver().findElements(
                By.xpath("//td[@class='jenkins-table__cell--tight']//a[@tooltip='Schedule a Build for %s']"
                        .formatted(name))).isEmpty();
    }
}