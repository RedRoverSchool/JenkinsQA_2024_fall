package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage  extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }
    By getNewItem = By.xpath("//a[@href='/view/all/newJob']");
    By listOfProjects = By.xpath("//a[@class = 'jenkins-table__link model-link inside'] /span");
    By getCreatedMultiConfigurationProject =By.xpath("//td/a[@href='job/MTC%20project/']");


    public HomePage createFreestyleProject(String name) {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.name("name")).sendKeys(name);
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();
        return this;
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
                getDriver().findElement(By.xpath("//button[@data-href='http://localhost:8080/job/" + name + "/']")));

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

    public FolderProjectPage openFolder(String name) {
        getDriver().findElement(By.xpath("//td/a/span[text() = '%s']/..".formatted(name))).click();

        return new FolderProjectPage(getDriver());
    }

    public PipelineProjectPage openPipelineProject(String name) {
        getDriver().findElement(By.xpath("//td/a[@href='job/%s/']".formatted(name))).click();

        return new PipelineProjectPage(getDriver());
    }

    public MultiConfigurationProjectPage openMultiConfigurationProject(String name) {
        getDriver().findElement(By.xpath("//td/a/span[text() = '%s']/..".formatted(name))).click();

        return new MultiConfigurationProjectPage(getDriver());
    }

    public FreestyleProjectPage openFreestyleProject(String name) {
        getDriver().findElement(By.xpath("//td/a/span[text() = '%s']/..".formatted(name))).click();

        return new FreestyleProjectPage(getDriver());
    }

    public ManageJenkinsPage openManageJenkinsPage() {
        getDriver().findElement(By.xpath("//a[@href = '/manage']")).click();

        return new ManageJenkinsPage(getDriver());
    }

    public CreateNewItemPage clickNewItem() {
        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();

        return new CreateNewItemPage(getDriver());
    }

    public CreateNewItemPage clickCreateJob() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        return new CreateNewItemPage(getDriver());
    }

    public String getItemNameByOrder(int order) {

        return getDriver().findElements(By.xpath("//td/a/span")).stream()
                .skip(order - 1)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Некорректный порядок: " + order))
                .getText();
    }

    public void selectMenuFromItemDropdown(String itemName, String menuName) {
        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath("//td/a/span[text() = '%s']/.."
                .formatted(itemName)))).perform();
        TestUtils.moveAndClickWithJavaScript(getDriver(), getDriver().findElement(
                By.xpath("//td/a/span[text() = '%s']/../button".formatted(itemName))));

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='jenkins-dropdown__item__icon']/parent::*[contains(., '%s')]"
                        .formatted(menuName)))).click();
    }

    public FolderConfigPage selectConfigureFromItemMenu(String itemName) {
        selectMenuFromItemDropdown(itemName, "Configure");

        return new FolderConfigPage(getDriver());
    }

    public HomePage selectDeleteFromItemMenu(String itemName) {
        selectMenuFromItemDropdown(itemName, "Delete");

        return this;
    }

    public HomePage clickYesForConfirmDelete() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@data-id='ok']"))).click();

        return this;
    }

    public CreateNewItemPage selectNewItemFromFolderMenu(String itemName) {
        selectMenuFromItemDropdown(itemName, "New Item");

        return new CreateNewItemPage(getDriver());
    }

    public BuildHistoryPage selectBuildHistoryFromItemMenu(String itemName) {
        selectMenuFromItemDropdown(itemName, "Build History");

        return new BuildHistoryPage(getDriver());
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

    public String getItemName(String projectName) {

        return getDriver().findElement(By.xpath("//td/a[@href='job/%s/']".formatted(projectName))).getText();
    }

    public String getWelcomeText() {
        return getDriver().findElement(By.xpath("//p[contains(text(), 'This page is where')]")).getText();
    }

    public String getWelcomeTitle() {
        return getDriver().findElement(By.xpath("//div[@class='empty-state-block']/h1")).getText();
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

    public HomePage deleteItemViaChevronItem(String itemName) {
        WebElement jobName = getDriver().findElement(
                By.xpath("//td/a/span[text() = '%s']/..".formatted(itemName)));
        new Actions(getDriver()).moveToElement(jobName).perform();

        WebElement chevronButton = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td/a/span[text() = '%s']/../button".formatted(itemName))));

        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", chevronButton);
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].dispatchEvent(new Event('click'));", chevronButton);

        getDriver().findElement(By.xpath("//button[contains(@href,'Delete')]")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        return this;
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

    public HomePage clickScheduleBuild(String name) {
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td[@class='jenkins-table__cell--tight']//a[@tooltip='Schedule a Build for " + name + "']"))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='tippy-content']")));
        getWait10().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='tippy-content']")));

        return this;
    }

    public HomePage scheduleBuild(String projectName) {
        getDriver().findElement(By.xpath("//a[@title = 'Schedule a Build for %s']".formatted(projectName))).click();

        return new HomePage(getDriver());
    }

    public BuildHistoryPage gotoBuildHistoryPageFromLeftPanel() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/builds']")).click();

        return new BuildHistoryPage(getDriver());
    }

    public String getTooltipValue(String projectName) {

        return getDriver().findElement(By.cssSelector("#job_" + projectName + "> td:nth-of-type(1) > div > svg")).getAttribute("tooltip");
    }

    public PipelineProjectPage clickOnPipelineName(String name) {
        getDriver().findElement(By.xpath("//td/a/span[text() = '%s']/..".formatted(name))).click();
        return new PipelineProjectPage(getDriver());
    }

    public PipelineRenamePage goToPipelineRenamePageViaDropdown(String name) {
        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath("//a[@href='job/%s/']/span".formatted(name))))
                .pause(500)
                .perform();
        WebElement chevron = getDriver().findElement(By.xpath("//td//button[@aria-expanded='false']"));
        TestUtils.moveAndClickWithJavaScript(getDriver(), chevron);
        getWait5().until(ExpectedConditions.attributeToBe(chevron, "aria-expanded", "true"));

        getDriver().findElement(By.xpath("//a[@href='/job/%s/confirm-rename']".formatted(name))).click();

        return new PipelineRenamePage(getDriver());
    }

    public HomePage clickDeletePipelineChevronDropdownMenu(String name) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@href = '/job/%s/doDelete']".formatted(name)))).click();

        return new HomePage(getDriver());
    }

    public List<String> showCreatedProject() {
        List<WebElement>itemList = getDriver().findElements(listOfProjects);

       return itemList.stream().map(WebElement::getText).collect(Collectors.toList());

    }
}