package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class FreestyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "My freestyle project";
    private static final String FOLDER_NAME = "My folder";

    private void createItemUtils(String name, String locator) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.cssSelector(locator)).click();
        getDriver().findElement(By.id("ok-button")).click();

    }

    @DataProvider
    public Object[][] providerUnsafeCharacters() {

        return new Object[][]{
                {"\\"}, {"]"}, {":"}, {"#"}, {"&"}, {"?"}, {"!"}, {"@"},
                {"$"}, {"%"}, {"^"}, {"*"}, {"|"}, {"/"}, {"<"}, {">"},
                {"["}, {";"}
        };
    }

    @Test
    public void testCreateFreestyleProjectWithValidName() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        createItemUtils(PROJECT_NAME, ".hudson_model_FreeStyleProject");
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        WebElement freestyleProjectItem = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
        );

        Assert.assertEquals(freestyleProjectItem.getText(), PROJECT_NAME);
    }

    @Test
    public void testCreateFreestyleProjectWithEmptyName() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.id("itemname-required"))
                .getText(), "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateFreestyleProjectWithDuplicateName() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        createItemUtils(PROJECT_NAME, ".hudson_model_FreeStyleProject");
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[@href='/view/all/newJob']")))
                .click();

        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.id("itemname-invalid"))
                .getText(), String.format("» A job already exists with the name ‘%s’", PROJECT_NAME));
    }

    @Test
    public void testAddDescriptionForFreestyleProject() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        String description = "Description freestyle project.";

        createItemUtils(PROJECT_NAME, ".hudson_model_FreeStyleProject");
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(String.format("//span[text()='%s']", PROJECT_NAME))))
                .click();

        getDriver().findElement(By.id("description-link")).click();
        getDriver()
                .findElement(By.xpath("//textarea[@name='description']"))
                .sendKeys(description);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//div[@id='description']/div"))
                .getText(), description);
    }

    @Test(dataProvider = "providerUnsafeCharacters")
    public void testCreateFreestyleProjectWithUnsafeCharactersInName(String unsafeCharacter) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("name")).sendKeys(unsafeCharacter);
        Assert.assertEquals(getDriver()
                .findElement(By.id("itemname-invalid"))
                .getText(), "» ‘" + unsafeCharacter + "’ is an unsafe character");
    }


    @Test
    public void testRenameFreestyleProjectViaSidePanel() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        String newProjectName = "My second freestyle project";

        createItemUtils(PROJECT_NAME, ".hudson_model_FreeStyleProject");
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(String.format("//span[text()='%s']", PROJECT_NAME))))
                .click();

        getDriver()
                .findElement(By.xpath("//a[contains(@href, 'confirm-rename')]"))
                .click();

        WebElement itemName = getDriver()
                .findElement(By.xpath("//input[@name='newName']"));
        itemName.clear();
        itemName.sendKeys(newProjectName);

        getDriver()
                .findElement(By.xpath("//button[@name='Submit']"))
                .click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", newProjectName)))
                .getText(), newProjectName);
    }

    @Test
    public void testDeleteFreestyleProjectViaSidePanel() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        createItemUtils(PROJECT_NAME, ".hudson_model_FreeStyleProject");
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(String.format("//span[text()='%s']", PROJECT_NAME))))
                .click();

        getDriver()
                .findElement(By.xpath("//a[contains(@data-url, 'doDelete')]"))
                .click();

        getDriver()
                .findElement(By.xpath("//button[@data-id='ok']"))
                .click();

        boolean isElementPresent = getDriver()
                .findElements(By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
                .isEmpty();

        Assert.assertTrue(isElementPresent);
    }

    @Test
    public void testDeleteFreestyleProjectViaChevron() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));

        createItemUtils(PROJECT_NAME, ".hudson_model_FreeStyleProject");
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        WebElement projectItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
        );
        WebElement chevronButton = getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']/following-sibling::button", PROJECT_NAME)));

        new Actions(getDriver())
                .moveToElement(projectItem, 10, 10)
                .moveToElement(chevronButton).click()
                .perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//button[contains(., 'Delete Project')]")))
                .click();

        getDriver()
                .findElement(By.xpath("//button[@data-id='ok']"))
                .click();

        boolean isElementPresent = getDriver()
                .findElements(By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
                .isEmpty();

        Assert.assertTrue(isElementPresent);
    }

    @Test
    public void testMoveFreestyleProjectToFolder() {

        String folderName = "My folder";

        createItemUtils(FOLDER_NAME, ".com_cloudbees_hudson_plugins_folder_Folder");
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        createItemUtils(PROJECT_NAME, ".hudson_model_FreeStyleProject");
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
                .click();

        getDriver()
                .findElement(By.xpath("//a[contains(@href, 'move')]"))
                .click();

        Select dropdown = new Select(getDriver().findElement(By.xpath("//select[@name='destination']")));
        dropdown.selectByValue("/" + folderName);
        getDriver()
                .findElement(By.xpath("//button[@name='Submit']"))
                .click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", folderName)))
                .click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
                .getText(), PROJECT_NAME);
    }

}