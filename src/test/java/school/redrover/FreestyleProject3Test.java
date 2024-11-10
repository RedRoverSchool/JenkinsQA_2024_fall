package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.time.Duration;

public class FreestyleProject3Test extends BaseTest {

    private static final String PROJECT_NAME = "FreestyleProject fall2024";

    private static final String DESCRIPTION = "Bla-bla-bla project";

    private void createProjectViaSidebarMenu(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[contains(@class, 'FreeStyleProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void addDescriptionOnProjectStatusPage(String description) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link"))).click();
        getDriver().findElement(By.tagName("textarea")).sendKeys(description);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void verifyYouAreOnProjectStatusPage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Permalinks']")));
    }

    @Test
    public void testCreateProjectViaCreateJobButton() {
        WebElement createJobButton = getDriver().findElement(By.xpath("//a[@href='newJob']"));
        createJobButton.click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        WebElement newItemNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        newItemNameField.sendKeys(PROJECT_NAME);

        WebElement freestyleProjectItemType = getDriver().findElement(
                By.xpath("//li[contains(@class, 'FreeStyleProject')]"));
        freestyleProjectItemType.click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@name='Submit']")));
        saveButton.click();

        verifyYouAreOnProjectStatusPage();

        String actualName = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(actualName, PROJECT_NAME);
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

        Assert.assertEquals(newDescription, projectDescriptionOnStatusPage);
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

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        WebElement renameSidebarMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='/job/" + PROJECT_NAME.replace(" ", "%20") + "/confirm-rename']")));
        renameSidebarMenu.click();

        WebElement newNameTextField = getDriver().findElement(By.xpath("//input[@checkdependson ='newName']"));
        newNameTextField.clear();
        newNameTextField.sendKeys(newName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        verifyYouAreOnProjectStatusPage();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), newName);
    }
}
