package school.redrover.page.folder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.page.base.BaseProjectPage;

import java.util.Arrays;
import java.util.List;

public class FolderProjectPage extends BaseProjectPage<FolderProjectPage, FolderConfigPage, FolderRenamePage> {

    @FindBy(tagName = "h1")
    private WebElement pageTitle;

    @FindBy(id = "description-link")
    private WebElement addDescriptionButton;

    @FindBy(xpath = "//*[@id='description']//div/a[contains(text(), 'Preview')]")
    private WebElement descriptionPreviewLink;

    @FindBy(xpath = "//*[@id='description']//div[@class='textarea-preview']")
    private WebElement descriptionTextareaPreview;

    @FindBy(xpath = "//span[contains(text(), 'Move')]/..")
    private WebElement moveSidebarButton;

    @FindBy(xpath = "//select[@name='destination']")
    private WebElement selectFolderDestinationSelect;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//a[@data-title ='Delete Folder']")
    private WebElement deleteFolderButton;

    @FindBy(xpath = "//button[@data-id='cancel']")
    private WebElement cancelDeleteButton;

    @FindBy(xpath = "//div[@id='breadcrumbBar']//li[@class='jenkins-breadcrumbs__list-item']")
    private List<WebElement> breadcrumbsItemList;

    @FindBy(xpath = "//div[@id='tasks']/div")
    private List<WebElement> sidebarItemsNameList;

    @FindBy(id = "view-message")
    private WebElement folderDescription;

    @FindBy(xpath = "//div[@id='main-panel']")
    private WebElement mainPanel;

    @FindBy(xpath = "//td/a/span")
    private List<WebElement> itemNames;

    public FolderProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public FolderConfigPage createProjectConfigPage() {
        return new FolderConfigPage(getDriver());
    }

    @Override
    public FolderRenamePage createProjectRenamePage() {
        return new FolderRenamePage(getDriver());
    }

    public String getFolderDescription() {
        return folderDescription.getText();
    }

    public String getConfigurationName() {
        return pageTitle.getText();
    }

    public String getFolderName() {
        String fullText = mainPanel.getText();

        return Arrays.stream(fullText.split("\n"))
                .filter(line -> line.startsWith("Folder name: "))
                .map(line -> line.replace("Folder name: ", "").trim())
                .findFirst()
                .orElse("");
    }

    public String getItemNameByOrder(int order) {

        return itemNames
                .stream()
                .skip(order - 1)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Некорректный порядок: " + order))
                .getText();
    }

    public FolderProjectPage runJob(String projectName) {
        getDriver().findElement(By.xpath("//td//a[@title='Schedule a Build for %s']".formatted(projectName))).click();

        return this;
    }

    public FolderProjectPage cancelDeletingViaModalWindow() {
        getWait10().until(ExpectedConditions.elementToBeClickable(deleteFolderButton)).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(cancelDeleteButton)).click();
        return this;
    }

    public String getDescriptionViaPreview() {
        getWait5().until(ExpectedConditions.elementToBeClickable(addDescriptionButton)).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(descriptionPreviewLink)).click();
        return getWait10().until(ExpectedConditions.visibilityOf(descriptionTextareaPreview)).getText();
    }

    public FolderProjectPage clickMoveOnSidebar() {
        moveSidebarButton.click();

        return this;
    }

    public FolderProjectPage selectParentFolderAndClickMove(String folderName) {
        WebElement selectElement = getWait5().until(ExpectedConditions.visibilityOf(selectFolderDestinationSelect));
        Select select = new Select(selectElement);
        select.selectByValue("/%s".formatted(folderName));

        submitButton.click();

        return this;
    }

    public List<String> getBreadcrumsBarItemsList() {
        return breadcrumbsItemList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    private void openItem(String name) {
        getDriver().findElement(By.xpath("//td/a/span[text() = '%s']/..".formatted(name))).click();
    }

    public FolderProjectPage openFolder(String name) {
        openItem(name);
        return new FolderProjectPage(getDriver());
    }

    public List<String> getListOfItemsSidebar() {
        return sidebarItemsNameList
                .stream()
                .map(WebElement::getText)
                .toList();
    }
}
