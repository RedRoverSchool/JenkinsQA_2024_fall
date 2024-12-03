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

    private static final String DESCRIPTION = "Bla-bla-bla project";

    private static final String DISPLAY_BUILD_NAME = "BuildName";

    private void createProjectViaSidebarMenu(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[contains(@class, 'FreeStyleProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();
    }

    private void openProject(String name) {
        getDriver().findElement(By.xpath("//td/a/span[text()='%s']".formatted(name))).click();
    }

    private void clickOnSuccessBuildIcon() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@tooltip='Success > Console Output']"))).click();
    }

    private void goToEditBuildInformationPage() {
        getDriver().findElement(By.xpath("//span[contains(text(), 'Edit Build Information')]/..")).click();
    }

    private WebElement findDisplayNameTextField() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@name='displayName']")));
    }

    private void clickSubmitButtonOnBuildInformationPage() {
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
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
    public void testBuildProjectViaSidebarMenuOnProjectStatusPage() {

        createProjectViaSidebarMenu(PROJECT_NAME);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@data-build-success='Build scheduled']"))).click();

        clickOnSuccessBuildIcon();

        Assert.assertTrue(
                getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("out"))).getText()
                        .contains("Finished: SUCCESS"));
    }

    @Test(dependsOnMethods = "testBuildProjectViaSidebarMenuOnProjectStatusPage")
    public void testAddBuildDisplayName() {
        openProject(PROJECT_NAME);

        clickOnSuccessBuildIcon();

        goToEditBuildInformationPage();

        findDisplayNameTextField().sendKeys(DISPLAY_BUILD_NAME);

        clickSubmitButtonOnBuildInformationPage();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@name='Submit']")));

        Assert.assertTrue(getDriver().findElement(By.tagName("h1")).getText().contains(DISPLAY_BUILD_NAME));
    }

    @Test(dependsOnMethods = {"testBuildProjectViaSidebarMenuOnProjectStatusPage", "testAddBuildDisplayName"})
    public void testEditBuildDisplayName() {
        final String newDisplayProjectName = "New " + DISPLAY_BUILD_NAME;

        openProject(PROJECT_NAME);

        clickOnSuccessBuildIcon();

        goToEditBuildInformationPage();

        findDisplayNameTextField().clear();
        findDisplayNameTextField().sendKeys(newDisplayProjectName);

        clickSubmitButtonOnBuildInformationPage();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@name='Submit']")));

        Assert.assertTrue(getDriver().findElement(By.tagName("h1")).getText().contains(newDisplayProjectName));
    }

    @Test(dependsOnMethods = "testBuildProjectViaSidebarMenuOnProjectStatusPage")
    public void testAddBuildDescription() {
        openProject(PROJECT_NAME);

        clickOnSuccessBuildIcon();

        goToEditBuildInformationPage();

        WebElement descriptionTextField = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//textarea")));
        descriptionTextField.sendKeys(DESCRIPTION);

        clickSubmitButtonOnBuildInformationPage();

        Assert.assertEquals(
                getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("description"))).getText(),
                DESCRIPTION);
    }

    @Test(dependsOnMethods = {"testBuildProjectViaSidebarMenuOnProjectStatusPage", "testAddBuildDescription"})
    public void testEditBuildDescription() {
        final String newDescriptionTextField = "New " + DESCRIPTION;
        openProject(PROJECT_NAME);

        clickOnSuccessBuildIcon();

        goToEditBuildInformationPage();

        WebElement descriptionTextField = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//textarea")));
        descriptionTextField.clear();
        descriptionTextField.sendKeys(newDescriptionTextField);

        clickSubmitButtonOnBuildInformationPage();

        Assert.assertEquals(
                getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("description"))).getText(),
                newDescriptionTextField);
    }

    @Test
    public void testDeleteLastBuild() {
        createProjectViaSidebarMenu(PROJECT_NAME);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@data-build-success='Build scheduled']"))).click();

        clickOnSuccessBuildIcon();

        String buildName = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[contains(text(), 'Delete build')]"))).getText();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Delete build')]/..")).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit"))).click();

        List<String> buildHistory = getDriver().findElements(By.xpath("//tr")).stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertFalse(buildHistory.contains(buildName));
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
