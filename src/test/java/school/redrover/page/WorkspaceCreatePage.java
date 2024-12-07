package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.page.base.BasePage;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WorkspaceCreatePage extends BasePage {

    public WorkspaceCreatePage(WebDriver driver) {
        super(driver);
    }

    public void createWorkspace() {

        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='tasks']/div[1]/span/a"))).click();

        WebElement jobNameField = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        jobNameField.sendKeys("TestJobWorkspace");

        WebElement freestyleProjectOption = getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[1]")));
        freestyleProjectOption.click();

        WebElement okButton = getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button")));
        okButton.click();

        WebElement saveButton = getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='bottom-sticker']/div/button[1]")));
        saveButton.click();

        performBuildActions();

        WebElement workspace = getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='tasks']/div[3]/span/a")));
        workspace.click();
    }

    private void performBuildActions() {
        for (int i = 0; i < 2; i++) {
            WebElement build = getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='tasks']/div[4]/span/a")));
            build.click();
        }
    }
    public String getBreadcrumbText() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#breadcrumbs > li:nth-child(5)"))).getText();
    }

    public void clickBuildTwo() {
        WebElement buildTwo = getWait5().until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#buildHistory tr:nth-child(2) a")));
        buildTwo.click();
    }

    public void navigateBackToWorkspace() {
        WebElement workspaceBreadcrumb = getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='breadcrumbs']/li[3]/a")));
        workspaceBreadcrumb.click();
    }

    public String getWorkspaceBreadcrumbText() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='breadcrumbs']/li[3]/a"))).getText();
    }
}