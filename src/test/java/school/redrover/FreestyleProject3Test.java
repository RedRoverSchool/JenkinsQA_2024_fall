package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class FreestyleProject3Test extends BaseTest {

    private static final String PROJECT_NAME = "FreestyleProject fall2024";

    private static final String DESCRIPTION = "Bla-bla-bla project";

    private void createProjectViaSidebarMenu(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[contains(@class, 'FreeStyleProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@name='Submit']"))).click();
    }

    private void addDescriptionOnProjectStatusPage(String description) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link"))).click();
        getDriver().findElement(By.tagName("textarea")).sendKeys(description);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void verifyYouAreOnProjectStatusPage() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Permalinks']")));
    }

    @Test
    public void testCreateProjectViaCreateJobButton() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//li[contains(@class, 'FreeStyleProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']")))
                .click();

        verifyYouAreOnProjectStatusPage();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), PROJECT_NAME);
    }

    @Test
    public void testCreateProjectViaSidebarMenu () {
        createProjectViaSidebarMenu(PROJECT_NAME);

        verifyYouAreOnProjectStatusPage();

        String actualName = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(actualName, PROJECT_NAME);
    }

    @Test
    public void testAddDescriptionOnProjectStatusPage() {
        createProjectViaSidebarMenu(PROJECT_NAME);

        addDescriptionOnProjectStatusPage(DESCRIPTION);

        String projectDescriptionOnStatusPage = getDriver().findElement(
                By.xpath("//div[@id='description']//div")).getText();

        Assert.assertTrue(projectDescriptionOnStatusPage.contains(DESCRIPTION));
    }

    @Test
    public void testEditDescriptionOnProjectStatusPage() {
        final String newDescription = "New " + DESCRIPTION;

        createProjectViaSidebarMenu(PROJECT_NAME);
        addDescriptionOnProjectStatusPage(DESCRIPTION);

        WebElement editDescriptionButton = getDriver().findElement(By.id("description-link"));
        editDescriptionButton.click();

        WebElement descriptionTextField = getDriver().findElement(By.tagName("textarea"));
        descriptionTextField.clear();
        descriptionTextField.sendKeys(newDescription);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String projectDescriptionOnStatusPage = getDriver().findElement(
                By.xpath("//div[@id='description']//div")).getText();

        Assert.assertEquals(projectDescriptionOnStatusPage, newDescription);
    }

    @Test
    public void testDeleteDescriptionOnProjectStatusPage() {
        createProjectViaSidebarMenu(PROJECT_NAME);
        addDescriptionOnProjectStatusPage(DESCRIPTION);

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.tagName("textarea")).clear();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertFalse(getDriver().findElement(By.xpath("//div[@id='description']//div")).getText()
                .contains(DESCRIPTION));
    }

    @Test
    public void testRenameProject() {
        final String newName = "New " + PROJECT_NAME;
        createProjectViaSidebarMenu(PROJECT_NAME);

        WebElement renameSidebarMenu = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='/job/" + PROJECT_NAME.replace(" ", "%20") + "/confirm-rename']")));
        renameSidebarMenu.click();

        WebElement newNameTextField = getDriver().findElement(By.xpath("//input[@checkdependson ='newName']"));
        newNameTextField.clear();
        newNameTextField.sendKeys(newName);

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        verifyYouAreOnProjectStatusPage();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), newName);
    }

    @Test
    public void testDeleteProjectViaChevron() {
        createProjectViaSidebarMenu(PROJECT_NAME);

        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();

        WebElement projectToDelete = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='job/" + PROJECT_NAME.replace(" ", "%20") + "/']")));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(projectToDelete)
                .pause(100)
                .perform();

        WebElement chevron = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='job/"+ PROJECT_NAME.replace(" ", "%20") + "/']//button")));

        TestUtils.moveAndClickWithJavaScript(getDriver(), chevron);

        WebElement deleteButton = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(@href, 'doDelete')]")));
        deleteButton.click();

        WebElement deleteAlert = getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-id='ok']")));
        deleteAlert.click();

        Assert.assertFalse(getDriver().findElement(By.id("main-panel")).getText().contains(PROJECT_NAME));
    }
}
