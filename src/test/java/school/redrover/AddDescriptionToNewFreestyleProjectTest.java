package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;

public class AddDescriptionToNewFreestyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "FreeStyleProjectTest";
    private static final String DESCRIPTION = "It's a simple test project";
    private static final String DESCRIPTION_EDITED = "It's a simple test project new version";

    @Test
    public void testAddDescription() {
        String getDescription = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .enterDescription(DESCRIPTION)
                .clickSaveButton()
                .gotoHomePage()
                .openFreestyleProject(PROJECT_NAME)
                .getDescription();

        Assert.assertEquals(getDescription, DESCRIPTION);
    }


    @Test (dependsOnMethods = "testAddDescription")
    public void testAddDescriptionToExisting() {
        String getDescription = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clearDescription()
                .editDescription(DESCRIPTION_EDITED)
                .clickSubmitButton()
                .getDescription();

        Assert.assertEquals(getDescription, DESCRIPTION_EDITED);
    }
}
