package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class PipelineProject2Test extends BaseTest {

    private static final String PROJECT_NAME = "MyPipelineProject";
    private static final String FOLDER_NAME = "MyFolder";

    private void createItem(String nameItem, String locator) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(nameItem);
        getDriver().findElement(By.xpath(locator)).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void goToMainPage() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testCreatePipelineProjectWithValidName() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        createItem(PROJECT_NAME, "//span[text()='Pipeline']");
        goToMainPage();

        String pipelineProjectName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))).getText();

        Assert.assertEquals(pipelineProjectName, PROJECT_NAME);
    }

    @Test
    public void testCreatePipelineProjectWithEmptyName() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();

        String errorMessage = getDriver()
                .findElement(By.id("itemname-required")).getText();

        Assert.assertEquals(errorMessage, "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreatePipelineProjectWithDuplicateName() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        createItem(PROJECT_NAME, "//span[text()='Pipeline']");
        goToMainPage();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='/view/all/newJob']"))).click();

        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();

        String errorMessage = getDriver()
                .findElement(By.id("itemname-invalid")).getText();

        Assert.assertEquals(errorMessage, String.format("» A job already exists with the name ‘%s’", PROJECT_NAME));
    }

    @Test
    public void testAddDescriptionForPipelineProject() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        String description = "Description pipeline project.";

        createItem(PROJECT_NAME, "//span[text()='Pipeline']");
        goToMainPage();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(String.format("//span[text()='%s']", PROJECT_NAME))))
                .click();

        getDriver().findElement(By.id("description-link")).click();
        getDriver()
                .findElement(By.xpath("//textarea[@name='description']"))
                .sendKeys(description);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String actualDescription = getDriver()
                .findElement(By.xpath("//div[@id='description']/div"))
                .getText();

        Assert.assertEquals(actualDescription, description);
    }

    @Test
    public void testMovePipelineProjectToFolder() {

        createItem(FOLDER_NAME, "//span[text()='Folder']");
        goToMainPage();

        createItem(PROJECT_NAME, "//span[text()='Pipeline']");
        goToMainPage();

        getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
                .click();

        getDriver()
                .findElement(By.xpath("//a[contains(@href, 'move')]"))
                .click();

        new Select(getDriver().findElement(By.xpath("//select[@name='destination']")))
                .selectByValue("/" + FOLDER_NAME);

        getDriver()
                .findElement(By.xpath("//button[@name='Submit']"))
                .click();
        goToMainPage();

        getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", FOLDER_NAME)))
                .click();

        String movedPipelineProjectName = getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
                .getText();

        Assert.assertEquals(movedPipelineProjectName, PROJECT_NAME);
    }

    @Test
    public void testDeletePipelineProjectViaSidePanel() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        createItem(PROJECT_NAME, "//span[text()='Pipeline']");
        goToMainPage();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
                )
                .click();

        getDriver()
                .findElement(By.xpath("//a[contains(@data-url, 'doDelete')]"))
                .click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[@data-id='ok']"))
                )
                .click();

        boolean isElementPresent = getDriver()
                .findElements(By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
                .isEmpty();

        Assert.assertTrue(isElementPresent);
    }

    @Test
    public void testDeletePipelineProjectViaChevron() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        createItem(PROJECT_NAME, "//span[text()='Pipeline']");
        goToMainPage();

        WebElement projectItem = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
        );
        WebElement chevronButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(String.format("//span[text()='%s']/following-sibling::button", PROJECT_NAME))));

        new Actions(getDriver())
                .moveToElement(projectItem)
                .moveToElement(chevronButton).click().pause(10000)
                .perform();
        System.out.println(chevronButton.isDisplayed());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='tippy-6']")));

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@href, 'doDelete')]"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@data-id='ok']"))).click();

        boolean isElementPresent = getDriver()
                .findElements(By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
                .isEmpty();

        Assert.assertTrue(isElementPresent);
    }

}
