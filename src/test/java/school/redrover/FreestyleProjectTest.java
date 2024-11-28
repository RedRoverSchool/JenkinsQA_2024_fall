package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;


public class FreestyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "MyFreestyleProject";

    @Test
    public void testCreateFreestyleProjectWithEmptyName() {
        String emptyNameMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("")
                .selectFreestyleProject()
                .getEmptyNameMessage();

        Assert.assertEquals(emptyNameMessage, "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateFreestyleProjectWithDuplicateName() {
        String errorMessage = new HomePage(getDriver()).clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProject()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘%s’".formatted(PROJECT_NAME));
    }

    @Test
    public void testMoveFreestyleProjectToFolder() {
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
        dropdown.selectByValue("/" + FOLDER_NAME);
        getDriver()
                .findElement(By.xpath("//button[@name='Submit']"))
                .click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", FOLDER_NAME)))
                .click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath(String.format("//span[text()='%s']", PROJECT_NAME)))
                .getText(), PROJECT_NAME);
    }

    @Test
    public void testCreateProjectViaCreateJobButton() {
        String actualProjectName = new HomePage(getDriver())
                .clickCreateJob()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getProjectName();

        Assert.assertEquals(actualProjectName, PROJECT_NAME);
    }
}