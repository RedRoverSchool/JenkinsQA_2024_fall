package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.NewViewDashboardTest;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(xpath = "//div[contains(@class,'jenkins-table__cell__button-wrapper')]")
    private WebElement projectType;

    @FindBy(css = "[href$='/newJob']")
    private WebElement newJob;

    @FindBy(xpath = "//td/a[@class='jenkins-table__link model-link inside']")
    private WebElement itemName;

    @FindBy(xpath = "//a[@href = '/manage']")
    private WebElement manageJenkinsSidebar;

    @FindBy(xpath = "//a[@href='newJob']")
    private WebElement createAJobOption;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement yesButton;

    @FindBy(xpath = "//p[contains(text(), 'This page is where')]")
    private WebElement headerDescription;

    @FindBy(xpath = "//div[@class='empty-state-block']/h1")
    private WebElement welcomeTitle;

    @FindBy(xpath = "//div[@id='description']/div[1]")
    private WebElement description;

    @FindBy(xpath = "//a[contains(@class, 'task-link-no-confirm')]")
    private List<WebElement> sideBarOptionList;

    @FindBy(className = "content-block")
    private List<WebElement> contentBlock;

    @FindBy(xpath = "//tr/td/a[@class='jenkins-table__link model-link inside']")
    private List<WebElement> projectList;

    @FindBy(xpath = "//button[contains(@href,'Delete')]")
    private WebElement deleteOption;

    @FindBy(xpath = "//div[@class='tippy-content']")
    private WebElement buildScheduledTooltip;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private void openItem(String name) {
        getDriver().findElement(By.xpath("//td/a/span[text() = '%s']/..".formatted(name))).click();
    }

    public FreestyleProjectPage openFreestyleProject(String name) {
        openItem(name);
        return new FreestyleProjectPage(getDriver());
    }

    public PipelineProjectPage openPipelineProject(String name) {
        openItem(name);
        return new PipelineProjectPage(getDriver());
    }

    public MultiConfigurationProjectPage openMultiConfigurationProject(String name) {
        openItem(name);
        return new MultiConfigurationProjectPage(getDriver());
    }

    public OrganizationFolderProjectPage openOrganisationFolderProject(String name) {
        openItem(name);
        return new OrganizationFolderProjectPage(getDriver());
    }

    public MultibranchPipelineProjectPage openMultibranchPipelineProject(String name) {
        openItem(name);
        return new MultibranchPipelineProjectPage(getDriver());
    }

    public FolderProjectPage openFolder(String name) {
        openItem(name);
        return new FolderProjectPage(getDriver());
    }

    public HomePage createFreestyleProject(String name) {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.name("name")).sendKeys(name);
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        gotoHomePage();

        return this;
    }

    public HomePage createFreestyleProject(String name, String description) {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.name("name")).sendKeys(name);
        getDriver().findElement(By.id("ok-button")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.name("description"))).sendKeys(description);
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

    public HomePage deleteFolder(String name) {
        getDriver().findElement(By.xpath("//span[text()='" + name + "']")).click();

        getDriver().findElement(By.xpath("//span[text()='Delete Folder']")).click();
        getDriver().findElement(By.xpath("//button[@data-id= 'ok']")).click();

        return this;
    }

    public HomePage deleteFolderViaChevron(String name) {
        TestUtils.moveAndClickWithJavaScript(getDriver(),
                getDriver().findElement(By.xpath("//td/a/span[text() = '%s']/../button".formatted(name))));

        getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(@href, 'doDelete')]"))).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath(
                "//button[@data-id= 'ok']")))).click();

        return this;
    }

    public ManageJenkinsPage openManageJenkinsPage() {
        manageJenkinsSidebar.click();

        return new ManageJenkinsPage(getDriver());
    }

    public CreateNewItemPage clickNewItem() {
        newJob.click();

        return new CreateNewItemPage(getDriver());
    }

    public CreateNewItemPage clickCreateJob() {
        createAJobOption.click();

        return new CreateNewItemPage(getDriver());
    }

    public String getItemNameByOrder(int order) {

        return getDriver().findElements(By.xpath("//td/a/span")).stream()
                .skip(order - 1)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Некорректный порядок: " + order))
                .getText();
    }

    public void selectMenuFromItemDropdown(String itemName, String menuName) {
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
        getWait5().until(ExpectedConditions.visibilityOf(yesButton)).click();

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

    public HomePage selectBuildNowFromItemMenu(String itemName) {
        selectMenuFromItemDropdown(itemName, "Build Now");

        return this;
    }

    public FolderProjectPage selectMoveFromItemMenuByChevron(String itemName) {
        selectMenuFromItemDropdown(itemName, "Move");

        return new FolderProjectPage(getDriver());
    }

    public HomePage openDropdownViaChevron(String projectName) {
        WebElement chevron = getDriver().findElement(By.xpath("//td/a/span[text() = '%s']/../button".formatted(projectName)));
        TestUtils.moveAndClickWithJavaScript(getDriver(), chevron);
        getWait5().until(ExpectedConditions.attributeToBe(chevron, "aria-expanded", "true"));

        return this;
    }

    public String getWelcomeDescriptionText() {
        return headerDescription.getText();
    }

    public String getWelcomeTitle() {
        return welcomeTitle.getText();
    }

    public String getDescriptionText() {
        return getWait5().until(ExpectedConditions.visibilityOf(description)).getText();
    }

    public List<WebElement> getSideContent() {
        return getWait2().until(ExpectedConditions.visibilityOfAllElements(sideBarOptionList));
    }

    public List<WebElement> getContentBlock() {
        return contentBlock;
    }

    public List<String> getItemList() {
        return projectList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public HomePage deleteItemViaChevronItem(String itemName) {
        WebElement chevronButton = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td/a/span[text() = '%s']/../button".formatted(itemName))));

        TestUtils.moveAndClickWithJavaScript(getDriver(), chevronButton);

        deleteOption.click();
        yesButton.click();

        return this;
    }

    public boolean isDisableCircleSignPresent(String name) {
        try {
            return getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//tr[@id='job_%s']//*[@tooltip='Disabled']".formatted(name)))).isDisplayed();

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
        getDriver().findElement(By.xpath("//td[@class='jenkins-table__cell--tight']//a[@tooltip='Schedule a Build for %s']".formatted(name)))
                .click();
        getWait10().until(ExpectedConditions.visibilityOf(buildScheduledTooltip));
        getWait10().until(ExpectedConditions.invisibilityOf(buildScheduledTooltip));

        return this;
    }

    public BuildHistoryPage gotoBuildHistoryPageFromLeftPanel() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/builds']")).click();

        return new BuildHistoryPage(getDriver());
    }

    public String getStatusBuild(String projectName) {

        return getDriver().findElement(By.cssSelector("#job_" + projectName + "> td:nth-of-type(1) > div > svg")).getAttribute("tooltip");
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

    public HomePage refreshAfterBuild() {
        getWait10().until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//a[contains(@class,'app-progress-bar')]")));

        getDriver().navigate().refresh();

        return this;
    }

    public String getNotificationBarStatus() {

        return getWait2().until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[@id = 'notification-bar']"))).getText();
    }

    public String getNumberOfRuns() {
        return getDriver().findElement(By.xpath("//a[@class='jenkins-table__link jenkins-table__badge model-link inside']")).getText();
    }

    public NewViewPage clickCreateNewViewButton() {
        getDriver().findElement(By.xpath("//a[@href='/newView']")).click();

        return new NewViewPage(getDriver());
    }

    public String getJenkinsVersion() {
        return getDriver().findElement(By.cssSelector("[class$='jenkins_ver']")).getText();
    }

    public HomePage clickJenkinsVersionButton() {
        getDriver().findElement(By.cssSelector("[class$='jenkins_ver']")).click();

        return this;
    }

    public AboutPage gotoAboutPage() {
        getDriver().findElement(By.xpath("//a[normalize-space()='About Jenkins']")).click();

        return new AboutPage(getDriver());
    }

    public String getAboutJenkinsDropdownLabelText() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[href='/manage/about']"))).getText();
    }

    public HomePage clickMyViewsButton() {
        getDriver().findElement(By.linkText("My Views")).click();
        return this;
    }

    public List<String> getViewList() {
        return getWait2().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//div[@class='tab' and not(.//a[@tooltip='New View'])]")))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public ViewPage clickViewByName(String name) {
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/view/%s/']".formatted(name)))).click();

        return new ViewPage(getDriver());
    }

    public HomePage clickDeleteInProjectDropdown(String projectName) {
        getDriver().findElement(By.xpath("//button[@href='/job/%s/doDelete']".formatted(projectName))).click();

        return this;
    }

    public WebElement getDeletionPopup() {
        return getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                By.xpath("//footer/following-sibling::dialog"))));
    }

    public MyViewsPage goToMyViews() {
        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();

        return new MyViewsPage(getDriver());
    }

    public HomePage clickNameTableHeaderChangeOrder() {
        getDriver().findElement(
                By.xpath("//table[@id='projectstatus']/thead/tr/th/a[contains(text(), 'Name')]")).click();

        return new HomePage(getDriver());
    }

    public HomePage clickStatusTableHeaderChangeOrder() {
        getDriver().findElement(
                By.xpath("//table[@id='projectstatus']/thead/tr/th/a[contains(text(), 'S')]")).click();

        return new HomePage(getDriver());
    }

    public String getTitleTableHeaderWithDownArrow() {
        return getDriver().findElement(
                        By.xpath("//table[@id='projectstatus']/thead/tr/th/a/span[contains(text(), '  ↓')]/.."))
                .getText().split(" ")[0].trim();
    }

    public String getTitleTableHeaderWithUpArrow() {
        return getDriver().findElement(
                        By.xpath("//table[@id='projectstatus']/thead/tr/th/a/span[contains(text(), '  ↑')]/.."))
                .getText().split(" ")[0].trim();
    }

    public HomePage clickDescriptionButton() {
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();

        return this;
    }

    public HomePage enterDescription(String description) {
        getDriver().findElement(By.xpath("//textarea[contains(@class, 'jenkins-input')]")).sendKeys(description);

        return this;
    }

    public HomePage clickSaveButton() {
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        return this;
    }

    public HomePage clearDescription() {
        getDriver().findElement(By.xpath("//textarea[@name='description']")).clear();

        return this;
    }

    public String getTextDescriptionButton() {
        return getDriver().findElement(By.id("description-link")).getText();
    }

    public OrganizationFolderProjectPage clickItemName() {
        itemName.click();

        return new OrganizationFolderProjectPage(getDriver());
    }

    public HomePage clickPreviewButton() {
        getDriver().findElement(By.cssSelector("[class$='textarea-show-preview']")).click();

        return this;
    }

    public String getTextPreview() {
        return getDriver().findElement(By.xpath("//div[@class='textarea-preview']")).getText();
    }

    public FreestyleRenamePage clickRenameInProjectDropdown(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/job/%s/confirm-rename']".formatted(projectName))).click();

        return new FreestyleRenamePage(getDriver());
    }
}