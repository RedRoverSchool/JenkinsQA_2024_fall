package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;
import school.redrover.page.base.BaseProjectPage;

import java.util.Arrays;
import java.util.List;

public class FolderProjectPage extends BaseProjectPage {

    public FolderProjectPage(WebDriver driver) {
        super(driver);
    }

    public FolderProjectPage editDescription(String text) {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(text);
        getDriver().findElement(By.name("Submit")).click();

        getWait2().until(ExpectedConditions.textToBe(By.id("description"), text));

        return this;
    }

    public FolderProjectPage clearDescription() {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.name("Submit")).click();

        return this;
    }

    public String getFolderDescription() {
        return getDriver().findElement(By.id("view-message")).getText();
    }

    public String getDescription() {
        return getDriver().findElement(By.id("description")).getText();
    }

    public String getDisplayName() {
        return getDriver().findElement(By.tagName("h1")).getText();
    }

    public String getFolderName() {
        String fullText = getDriver().findElement(By.xpath("//div[@id='main-panel']")).getText();
        return Arrays.stream(fullText.split("\n"))
                .filter(line -> line.startsWith("Folder name: "))
                .map(line -> line.replace("Folder name: ", "").trim())
                .findFirst()
                .orElse("");
    }

    public String getItemNameByOrder(int order) {

        return getDriver().findElements(By.xpath("//td/a/span")).stream()
                .skip(order - 1)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Некорректный порядок: " + order))
                .getText();
    }

    public CreateNewItemPage clickNewItem() {
        getDriver().findElement(By.xpath("//span[text()='New Item']/ancestor::a")).click();

        return new CreateNewItemPage(getDriver());
    }

    public FolderProjectPage runJob(String projectName) {
        getDriver().findElement(By.xpath("//td//a[@title='Schedule a Build for %s']".formatted(projectName))).click();

        return this;
    }

    public List<String> getSidebarOptionList() {
        return getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[@class='task ']//span[2]"))).stream().map(WebElement::getText).toList();
    }

    public PipelineConfigurePage clickConfigureSidebar(String name) {
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/job/%s/configure']".formatted(name)))).click();

        return new PipelineConfigurePage(getDriver());
    }

    public FolderProjectPage clickEnableButton() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@formNoValidate='formNoValidate']"))).click();

        return this;
    }

    public FolderProjectPage hoverOverBuildStatusMark() {
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

    public List<String> getPermalinkList() {
        return getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//li[@class='permalink-item']")))
                .stream()
                .map(WebElement::getText)
                .map(string -> string.split("\\(#")[0].trim())
                .toList();
    }

    public PipelineBuildPage clickBuildStatusMark() {
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title='Success']"))).click();

        return new PipelineBuildPage(getDriver());
    }

    public PipelineRenamePage clickRenameSidebar(String name) {
        getDriver().findElement(By.xpath("//a[@href='/job/%s/confirm-rename']".formatted(name))).click();

        return new PipelineRenamePage(getDriver());
    }

    public HomePage clickDeletePipelineSidebarAndConfirmDeletion() {
        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        return new HomePage(getDriver());
    }
}
