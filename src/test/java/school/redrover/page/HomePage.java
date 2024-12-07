package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(css = "[href$='/newJob']")
    private WebElement newJob;

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

    @FindBy(xpath = "//div[@class='tippy-content']")
    private WebElement buildScheduledTooltip;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private void openItem(String name) {
        getDriver().findElement(By.xpath("//td/a/span[text() = '%s']/..".formatted(name))).click();
    }

    private void selectMenuFromItemDropdown(String itemName, String menuName) {
        TestUtils.moveAndClickWithJavaScript(getDriver(), getDriver().findElement(
                By.xpath("//td/a/span[text() = '%s']/../button".formatted(itemName))));

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='jenkins-dropdown__item__icon']/parent::*[contains(., '%s')]"
                        .formatted(menuName)))).click();
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

    public FolderConfigPage selectConfigureFromItemMenu(String itemName) {
        selectMenuFromItemDropdown(itemName, "Configure");

        return new FolderConfigPage(getDriver());
    }

    public HomePage selectDeleteFromItemMenu(String itemName) {
        selectMenuFromItemDropdown(itemName, "Delete");

        return this;
    }

    public HomePage selectDeleteFromItemMenuAndClickYes(String itemName) {
        selectDeleteFromItemMenu(itemName);
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

    public String getItemNameByOrder(int order) {

        return getDriver().findElements(By.xpath("//td/a/span")).stream()
                .skip(order - 1)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Некорректный порядок: " + order))
                .getText();
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

        return getDriver().findElement(By.cssSelector("#job_%s> td:nth-of-type(1) > div > svg".formatted(projectName))).getAttribute("tooltip");
    }

    public PipelineRenamePage goToPipelineRenamePageViaDropdown(String name) {
        selectMenuFromItemDropdown(name, "Rename");

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

    public HomePage clickPreviewButton() {
        getDriver().findElement(By.cssSelector("[class$='textarea-show-preview']")).click();

        return this;
    }

    public String getTextPreview() {
        return getDriver().findElement(By.xpath("//div[@class='textarea-preview']")).getText();
    }

    public FreestyleRenamePage clickRenameInProjectDropdown(String projectName) {
        selectMenuFromItemDropdown(projectName, "Rename");

        return new FreestyleRenamePage(getDriver());
    }
}