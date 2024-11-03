package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "My freestyle project";

    private void createFreestyleProjectUtils() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']//button[@name='Submit']")).click();

    }

    private void createFolderUtils(String nameFolder) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(nameFolder);
        getDriver().findElement(By.cssSelector(".com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']//button[@name='Submit']")).click();

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
    public void testCreateFreestyleProjectWithValidName() throws InterruptedException {

        createFreestyleProjectUtils();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Thread.sleep(2000);

        Assert.assertEquals(getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
                .getText(), PROJECT_NAME);
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
    public void testCreateFreestyleProjectWithDuplicateName() throws InterruptedException {

        createFreestyleProjectUtils();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Thread.sleep(2000);

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.id("itemname-invalid"))
                .getText(), String.format("» A job already exists with the name ‘%s’", PROJECT_NAME));
    }

    @Test
    public void testAddDescriptionForFreestyleProject() throws InterruptedException {

        String description = "Description freestyle project.";

        createFreestyleProjectUtils();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Thread.sleep(2000);

        getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
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
    public void testRenameFreestyleProjectViaSidePanel() throws InterruptedException {

        String newProjectName = "My second freestyle project";

        createFreestyleProjectUtils();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Thread.sleep(2000);

        getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
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
        Thread.sleep(2000);

        Assert.assertEquals(getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", newProjectName)))
                .getText(), newProjectName);
    }

    @Test
    public void testDeleteFreestyleProjectViaSidePanel() throws InterruptedException {

        createFreestyleProjectUtils();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Thread.sleep(2000);

        getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
                .click();
        getDriver()
                .findElement(By.xpath("//a[contains(@data-url, 'doDelete')]"))
                .click();
        Thread.sleep(1000);

        getDriver()
                .findElement(By.xpath("//button[@data-id='ok']"))
                .click();

        Thread.sleep(2000);

        boolean isElementPresent = getDriver()
                .findElements(By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
                .isEmpty();

        Assert.assertTrue(isElementPresent);
    }

    @Test
    public void testMoveFreestyleProjectToFolder() throws InterruptedException {

        String folderName = "My folder";

        createFolderUtils(folderName);
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Thread.sleep(2000);

        createFreestyleProjectUtils();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Thread.sleep(2000);

        getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
                .click();

        getDriver()
                .findElement(By.xpath("//a[contains(@href, 'move')]"))
                .click();
        Thread.sleep(1000);

        Select dropdown = new Select(getDriver().findElement(By.xpath("//select[@name='destination']")));
        dropdown.selectByValue("/" + folderName);
        getDriver()
                .findElement(By.xpath("//button[@name='Submit']"))
                .click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Thread.sleep(2000);

        getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", folderName)))
                .click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
                .getText(), PROJECT_NAME);
        }

}