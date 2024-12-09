package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseProjectPage;
import school.redrover.runner.TestUtils;

import java.util.List;

public class PipelineProjectPage extends BaseProjectPage<PipelineProjectPage, PipelineConfigurePage, PipelineRenamePage> {

    @FindBy(xpath = "//form[@id='enable-project']")
    private WebElement warningDisabledMessageForm;

    @FindBy(xpath = "//form[@id='enable-project']/button[@name='Submit']")
    private WebElement statusButton;

    @FindBy(css = "a[data-build-success='Build scheduled']")
    private WebElement buildNowItemOnSidePanel;

    @FindBy(css = "a[href$='multi-pipeline-graph']")
    private WebElement stagesItemOnSidePanel;

    @FindBy(xpath = "//div[@class='jenkins-app-bar']//h1")
    private WebElement title;

    @FindBy(xpath = "//ol[@id='breadcrumbs']/li[@class='jenkins-breadcrumbs__list-item'][2]")
    private WebElement projectNameBreadcrumb;

    @FindBy(xpath = "//button[@formNoValidate='formNoValidate']")
    private WebElement enableButton;

    @FindBy(xpath = "//*[local-name()='svg' and @tooltip]")
    private WebElement buildStatusMark;

    @FindBy(xpath = "//div[@class='tippy-content']")
    private WebElement statusMarkTooltip;

    @FindBy(xpath = "//*[@title='Success']")
    private WebElement buildStatusSuccessMark;

    @FindBy(xpath = "//li[@class='permalink-item']")
    private List<WebElement> permalinkList;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//a[@href='/job/%s/']/button[@class='jenkins-menu-dropdown-chevron']")
    private WebElement chevronDropdownButton;

    @FindBy(xpath = "//a[@href='/job/%s/pipeline-syntax']")
    private WebElement pipelineSyntaxPageLink;

    public PipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public PipelineConfigurePage createProjectConfigPage() {
        return new PipelineConfigurePage(getDriver());
    }

    @Override
    public PipelineRenamePage createProjectRenamePage() {
        return new PipelineRenamePage(getDriver());
    }

    public String getWarningDisabledMessage() {
        return getWait5().until(ExpectedConditions.visibilityOf(warningDisabledMessageForm)).getText().split("\n")[0];
    }

    public String getStatusButtonText() {
        return getWait2().until(ExpectedConditions.visibilityOf(statusButton)).getText();
    }

    public PipelineProjectPage clickOnBuildNowItemOnSidePanelAndWait() {
        buildNowItemOnSidePanel.click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[tooltip$='> Console Output']")));
        return this;
    }

    public PipelineStagesPage clickOnStagesItemOnSidePanel() {
        stagesItemOnSidePanel.click();

        return new PipelineStagesPage(getDriver());
    }

    public String getTitle() {
        return title.getText();
    }

    public String getProjectNameBreadcrumb() {
        return projectNameBreadcrumb.getText();
    }

    public PipelineProjectPage clickEnableButton() {
        getWait2().until(ExpectedConditions.elementToBeClickable(enableButton)).click();

        return this;
    }

    public PipelineProjectPage hoverOverBuildStatusMark() {
        new Actions(getDriver())
                .moveToElement(getWait10().until(ExpectedConditions.visibilityOf(buildStatusMark)))
                .perform();

        return this;
    }

    public String getStatusMarkTooltipText() {
        return getWait10().until(ExpectedConditions.visibilityOf(statusMarkTooltip)).getText();
    }

    public PipelineBuildPage clickBuildStatusMark() {
        getWait10().until(ExpectedConditions.elementToBeClickable(buildStatusSuccessMark)).click();

        return new PipelineBuildPage(getDriver());
    }

    public List<String> getPermalinkList() {
        return getWait10().until(ExpectedConditions.visibilityOfAllElements(permalinkList))
                .stream()
                .map(WebElement::getText)
                .map(string -> string.split("\\(#")[0].trim())
                .toList();
    }

    public PipelineProjectPage clickSaveButton() {
        saveButton.click();

        return new PipelineProjectPage(getDriver());
    }

    public PipelineProjectPage openDropDownMenuByChevronBreadcrumb(String name) {
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.xpath("//li/a[@href='/job/%s/']".formatted(name))))
                .click()
                .perform();

        WebElement buttonChevron = getWait10().until(TestUtils.ExpectedConditions.elementIsNotMoving(
                By.xpath("//a[@href ='/job/%s/']/button[@class='jenkins-menu-dropdown-chevron']"
                        .formatted(name))));

        TestUtils.moveAndClickWithJS(getDriver(), buttonChevron);

        return new PipelineProjectPage(getDriver());
    }

    public PipelineSyntaxPage gotoPipelineSyntaxPageFromLeftPanel(String projectName) {
        pipelineSyntaxPageLink.click();

        return new PipelineSyntaxPage(getDriver());
    }

}
