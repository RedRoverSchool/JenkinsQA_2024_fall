package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseProjectPage;
import school.redrover.runner.TestUtils;

import java.util.List;

public class PipelineProjectPage extends BaseProjectPage<PipelineProjectPage, PipelineConfigurePage, PipelineRenamePage> {

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
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//form[@id='enable-project']"))).getText().split("\n")[0];
    }

    public String getStatusButtonText() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//form[@id='enable-project']/button[@name='Submit']"))).getText();
    }

    public PipelineProjectPage clickOnBuildNowItemOnSidePanelAndWait() {
        getDriver().findElement(By.cssSelector("a[data-build-success='Build scheduled']")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[tooltip$='> Console Output']")));

        return this;
    }

    public PipelineStagesPage clickOnStagesItemOnSidePanel() {
        getDriver().findElement(By.cssSelector("a[href$='multi-pipeline-graph']")).click();

        return new PipelineStagesPage(getDriver());
    }

    public String getTitle() {
        return getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar']//h1")).getText();
    }

    public String getProjectNameBreadcrumb() {
        return getDriver().findElement(
                By.xpath("//ol[@id='breadcrumbs']/li[@class='jenkins-breadcrumbs__list-item'][2]")).getText();
    }

    public PipelineProjectPage clickEnableButton() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@formNoValidate='formNoValidate']"))).click();

        return this;
    }

    public PipelineProjectPage hoverOverBuildStatusMark() {
        new Actions(getDriver())
                .moveToElement(getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[local-name()='svg' and @tooltip]"))))
                .perform();

        return this;
    }

    public String getStatusMarkTooltipText() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='tippy-content']"))).getText();
    }

    public PipelineBuildPage clickBuildStatusMark() {
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title='Success']"))).click();

        return new PipelineBuildPage(getDriver());
    }

    public List<String> getPermalinkList() {
        return getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//li[@class='permalink-item']")))
                .stream()
                .map(WebElement::getText)
                .map(string -> string.split("\\(#")[0].trim())
                .toList();
    }

    public PipelineProjectPage clickSaveButton() {
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

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
        getDriver().findElement(By.xpath("//a[@href='/job/%s/pipeline-syntax']".formatted(projectName))).click();

        return new PipelineSyntaxPage(getDriver());
    }
}
