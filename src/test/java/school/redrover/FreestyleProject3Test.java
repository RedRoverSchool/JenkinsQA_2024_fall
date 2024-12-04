package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProject3Test extends BaseTest {

    private static final String PROJECT_NAME = "FreestyleProject fall2024";

    private void createProjectViaSidebarMenu(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[contains(@class, 'FreeStyleProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();
    }

    private void clickWorkspaceSidebarMenu() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='Workspace']/.."))).click();
    }

    private void clickBuildNowAndWaitForBuildHistoryUpdate() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@data-build-success='Build scheduled']"))).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@tooltip='Success > Console Output']")));
    }

    private void wipeOutCurrentWorkspace() {
    getWait10().until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[@data-title='Wipe Out Current Workspace']"))).click();
    }

    @Test
    public void testDeleteProjectViaSidebarMenuOnProjectStatusPage() {
        createProjectViaSidebarMenu(PROJECT_NAME);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@data-title='Delete Project']"))).click();

        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-id='ok']"))).click();

        Assert.assertEquals(
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))).getText(),
                "Welcome to Jenkins!",
                "There were more than one project on Dashboard");
    }

    @Test
    public void testDeleteProjectViaSidebarMenuOnProjectStatusPageWhenSeveralProjectsExist() {
        createProjectViaSidebarMenu(PROJECT_NAME + 2);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(), 'Dashboard')]"))).click();

        createProjectViaSidebarMenu(PROJECT_NAME);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@data-title='Delete Project']"))).click();

        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-id='ok']"))).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("projectstatus"))).isDisplayed();

        Assert.assertFalse(
                getDriver().findElements(By.xpath("//tbody//a[contains(@href, 'job')]//span")).stream()
                        .allMatch(w -> w.getText().equals(PROJECT_NAME) && w.getText().length() == PROJECT_NAME.length()));
    }

    @Test
    public void testDeleteWorkspace() {
        final String expectedText = "Error: no workspace";
        createProjectViaSidebarMenu(PROJECT_NAME);

        clickBuildNowAndWaitForBuildHistoryUpdate();

        clickWorkspaceSidebarMenu();

        wipeOutCurrentWorkspace();

        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-id='ok']"))).click();

        clickWorkspaceSidebarMenu();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), expectedText);
    }

    @Test
    public void testDeleteWorkspaceConfirmationOptions() {
        List<String> dialogOptions = List.of("Are you sure about wiping out the workspace?", "Cancel", "Yes");
        createProjectViaSidebarMenu(PROJECT_NAME);

        clickBuildNowAndWaitForBuildHistoryUpdate();

        clickWorkspaceSidebarMenu();

        wipeOutCurrentWorkspace();

        getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("dialog")));

        List<String> confirmationDialog = getDriver().findElements(By.cssSelector("dialog *")).stream()
                .map(WebElement::getText)
                .toList();

        confirmationDialog.forEach(System.out::println);

        for (String option : dialogOptions) {
            Assert.assertTrue(confirmationDialog.contains(option), "Missing option: " + option);
        }
    }
}
