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
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProject()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘%s’".formatted(PROJECT_NAME));
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