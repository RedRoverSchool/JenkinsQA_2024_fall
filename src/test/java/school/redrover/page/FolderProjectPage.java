package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseProjectPage;

import java.util.Arrays;

public class FolderProjectPage extends BaseProjectPage<FolderProjectPage> {

    public FolderProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getFolderDescription() {
        return getDriver().findElement(By.id("view-message")).getText();
    }

    public String getConfigurationName() {
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

    public FolderProjectPage runJob(String projectName) {
        getDriver().findElement(By.xpath("//td//a[@title='Schedule a Build for %s']".formatted(projectName))).click();

        return this;
    }

    public FolderProjectPage cancelDeletingViaModalWindow () {
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[2]/div[1]/div[1]/div[4]/span/a"))).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/dialog/div[3]/button[2]"))).click();
        return this;
    }

    public FolderConfigPage clickConfigureSidebar(String name) {
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/job/%s/configure']".formatted(name)))).click();

        return new FolderConfigPage(getDriver());
    }

    public String getDescriptionViaPreview () {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("description-link"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='description']/form/div[1]/div[1]/div[1]/a[1]"))).click();
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='description']/form/div[1]/div[1]/div[2]"))).getText();
    }
}
