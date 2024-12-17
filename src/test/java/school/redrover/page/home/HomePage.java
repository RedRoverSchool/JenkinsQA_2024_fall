package school.redrover.page.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.*;
import school.redrover.page.base.BasePage;
import school.redrover.page.folder.FolderConfigPage;
import school.redrover.page.folder.FolderProjectPage;
import school.redrover.page.freestyle.FreestyleProjectPage;
import school.redrover.page.freestyle.FreestyleRenamePage;
import school.redrover.page.manage.ManageJenkinsPage;
import school.redrover.page.multiConfiguration.MultiConfigurationProjectPage;
import school.redrover.page.multibranch.MultibranchPipelineProjectPage;
import school.redrover.page.organizationFolder.OrganizationFolderProjectPage;
import school.redrover.page.pipeline.PipelineProjectPage;
import school.redrover.page.pipeline.PipelineRenamePage;
import school.redrover.page.user.UserConfigPage;
import school.redrover.page.user.UserPage;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomePage extends BasePage {

    @FindBy(css = "[href$='/newJob']")
    private WebElement newJob;

    @FindBy(xpath = "//a[@href='newJob']")
    private WebElement newJobContentBlock;

    @FindBy(xpath = "//a[@href = '/manage']")
    private WebElement manageJenkinsSidebar;

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

    @FindBy(xpath = "(//button[@class='jenkins-menu-dropdown-chevron'])[1]")
    private WebElement adminDropdown;

    @FindBy(xpath = "//a[contains(@href,'/user/admin/configure')]")
    private WebElement configureAdminDropdown;

    @FindBy(xpath = "//*[@href='/user/admin']")
    private WebElement admin;

    @FindBy(xpath = "//a[contains(@href,'/user/admin/credentials')]")
    private WebElement credentialsDropdown;

    @FindBy(css = "a[href^='/logout']")
    private WebElement logOut;

    @FindBy(xpath = "//a[@href ='/view/all/builds']")
    private WebElement buildHistoryLink;

    @FindBy(xpath = "//a[contains(@class,'app-progress-bar')]")
    private WebElement progressBar;

    @FindBy(xpath = "//div[@id = 'notification-bar']")
    private WebElement notificationBar;

    @FindBy(xpath = "//a[@class='jenkins-table__link jenkins-table__badge model-link inside']")
    private WebElement numberOfRuns;

    @FindBy(xpath = "//a[@href='/newView']")
    private WebElement createNewViewButton;

    @FindBy(css = "[class$='jenkins_ver']")
    private WebElement jenkinsVersion;

    @FindBy(xpath = "//a[normalize-space()='About Jenkins']")
    private WebElement aboutJenkinsButton;

    @FindBy(css = "[href='/manage/about']")
    private WebElement aboutJenkinsDropdownLabel;

    @FindBy(linkText = "My Views")
    private WebElement myViewsButton;

    @FindBy(xpath = "//div[@class='tab' and not(.//a[@tooltip='New View'])]")
    private List<WebElement> viewList;

    @FindBy(xpath = "//footer/following-sibling::dialog")
    private WebElement deletionPopup;

    @FindBy(xpath = "//a[@href='/me/my-views']")
    private WebElement myViewsLink;

    @FindBy(xpath = "//table[@id='projectstatus']/thead/tr/th/a[contains(text(), 'Name')]")
    private WebElement tableNameHeaderChangeOrder;

    @FindBy(xpath = "//table[@id='projectstatus']/thead/tr/th/a[contains(text(), 'S')]")
    private WebElement statusTableHeaderChangeOrder;

    @FindBy(xpath = "//table[@id='projectstatus']/thead/tr/th/a/span[contains(text(), '  ↓')]/..")
    private WebElement titleTableHeaderWithDownArrow;

    @FindBy(xpath = "//table[@id='projectstatus']/thead/tr/th/a/span[contains(text(), '  ↑')]/..")
    private WebElement titleTableHeaderWithUpArrow;

    @FindBy(id = "description-link")
    private WebElement descriptionButton;

    @FindBy(xpath = "//textarea[contains(@class, 'jenkins-input')]")
    private WebElement descriptionTextArea;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement clearDescriptionTextArea;

    @FindBy(css = "[class$='textarea-show-preview']")
    private WebElement previewButton;

    @FindBy(xpath = "//div[@class='textarea-preview']")
    private WebElement previewText;

    @FindBy(xpath = "//td[text()= 'No builds in the queue.' ]")
    private WebElement buildQueueText;

    @FindBy(xpath = "//div[@class='tippy-box']//a")
    private List<WebElement> breadcrumbBarMenuList;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private void openItem(String name) {
        getDriver().findElement(By.xpath("//td/a/span[text() = '%s']/..".formatted(name))).click();
    }

    private void selectMenuFromItemDropdown(String itemName, String menuName) {
        TestUtils.moveAndClickWithJS(getDriver(), getDriver().findElement(
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

    public CreateNewItemPage clickNewItemContentBlock() {
        newJobContentBlock.click();

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

    public List<String> getSideContent() {
        getWait2().until(ExpectedConditions.visibilityOfAllElements(sideBarOptionList));
        return sideBarOptionList.stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<String> getSideContentAttribute() {
        getWait2().until(ExpectedConditions.visibilityOfAllElements(sideBarOptionList));
        return sideBarOptionList.stream()
                .map(el -> el.getAttribute("href"))
                .toList();
    }

    public List<String> getContentBlock() {
        return contentBlock.stream()
                .map(WebElement::getText)
                .toList();
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
        getWait10().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                        By.xpath("//td[@class='jenkins-table__cell--tight']//a[@tooltip='Schedule a Build for %s']".formatted(name)))))
                .click();
        getWait10().until(ExpectedConditions.visibilityOf(buildScheduledTooltip));
        getWait10().until(ExpectedConditions.invisibilityOf(buildScheduledTooltip));

        return this;
    }

    public BuildHistoryPage gotoBuildHistoryPageFromLeftPanel() {
        buildHistoryLink.click();

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
        getWait10().until(ExpectedConditions.invisibilityOf(progressBar));

        getDriver().navigate().refresh();

        return this;
    }

    public String getNotificationBarStatus() {

        return getWait2().until(ExpectedConditions.visibilityOf(notificationBar)).getText();
    }

    public String getNumberOfRuns() {
        return numberOfRuns.getText();
    }

    public NewViewPage clickCreateNewViewButton() {
        createNewViewButton.click();

        return new NewViewPage(getDriver());
    }

    public String getJenkinsVersion() {
        return jenkinsVersion.getText();
    }

    public HomePage clickJenkinsVersionButton() {
        jenkinsVersion.click();

        return this;
    }

    public AboutPage gotoAboutPage() {
        aboutJenkinsButton.click();

        return new AboutPage(getDriver());
    }

    public String getAboutJenkinsDropdownLabelText() {
        return getWait2().until(ExpectedConditions.visibilityOf(aboutJenkinsDropdownLabel)).getText();
    }

    public HomePage clickMyViewsButton() {
        myViewsButton.click();

        return this;
    }

    public List<String> getViewList() {
        return getWait2().until(ExpectedConditions.visibilityOfAllElements(viewList))
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

        return getWait5().until(ExpectedConditions.visibilityOf(deletionPopup));
    }

    public MyViewsPage goToMyViews() {
        myViewsLink.click();

        return new MyViewsPage(getDriver());
    }

    public HomePage clickNameTableHeaderChangeOrder() {
        tableNameHeaderChangeOrder.click();

        return new HomePage(getDriver());
    }

    public HomePage clickStatusTableHeaderChangeOrder() {
        statusTableHeaderChangeOrder.click();

        return new HomePage(getDriver());
    }

    public String getTitleTableHeaderWithDownArrow() {
        return titleTableHeaderWithDownArrow.getText().split(" ")[0].trim();
    }

    public String getTitleTableHeaderWithUpArrow() {
        return titleTableHeaderWithUpArrow.getText().split(" ")[0].trim();
    }

    public HomePage clickDescriptionButton() {
        descriptionButton.click();

        return this;
    }

    public HomePage addDescription(String description) {
        descriptionTextArea.sendKeys(description);

        return this;
    }

    public HomePage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public HomePage clearDescription() {
        clearDescriptionTextArea.clear();

        return this;
    }

    public String getDescriptionButtonTitle() {
        return descriptionButton.getText();
    }

    public HomePage clickPreviewButton() {
        previewButton.click();

        return this;
    }

    public String getTextPreview() {
        return previewText.getText();
    }

    public FreestyleRenamePage clickRenameInProjectDropdown(String projectName) {
        selectMenuFromItemDropdown(projectName, "Rename");

        return new FreestyleRenamePage(getDriver());
    }

    public Boolean isInAlphabeticalOrder() {
        List<String> actualOrder = getItemList();
        List<String> expectedOrder = new ArrayList<>(actualOrder);
        Collections.sort(expectedOrder);

        return actualOrder.equals(expectedOrder);
    }

    public UserPage clickAdmin() {
        admin.click();

        return new UserPage(getDriver());
    }

    public HomePage openAdminDropdownMenu() {
        new Actions(getDriver()).moveToElement(adminDropdown).click().perform();

        return this;
    }

    public UserConfigPage clickConfigureAdminDropdownMenu() {
        getWait2().until(ExpectedConditions.elementToBeClickable(configureAdminDropdown)).click();

        return new UserConfigPage(getDriver());
    }

    public SignInPage clickLogOut() {
        logOut.click();

        return new SignInPage(getDriver());
    }

    public CredentialsPage clickCredentialsAdminDropdownMenu() {
        getWait2().until(ExpectedConditions.elementToBeClickable(credentialsDropdown)).click();

        return new CredentialsPage(getDriver());
    }

    public String getBuildQueueText() {
        return buildQueueText.getText();
    }

    public HomePage selectBreadcrumbBarMenu() {
        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//a"))).perform();

        TestUtils.moveAndClickWithJS(getDriver(), getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='breadcrumbBar']//a/button"))));

        return this;
    }

    public List<WebElement> getBreadcrumbBarMenuList() {
        return breadcrumbBarMenuList;
    }
}