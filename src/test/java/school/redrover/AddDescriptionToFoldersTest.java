package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

public class AddDescriptionToFoldersTest extends BaseTest {

    private static final String FOLDER_NAME = "FolderTest";
    private static final String DESCRIPTION = "Description text";

    @Test
    public void testExistingFolderWithNoDescription () {
        String finalResult = new HomePage(getDriver())
                .createNewFolder(FOLDER_NAME)
                .openFolder(FOLDER_NAME)
                .editDescription(DESCRIPTION)
                .clickSubmitButton()
                .getDescription();

        Assert.assertEquals(finalResult, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testExistingFolderWithNoDescription")
    public void testEditExistingDescription () {
        String finalResult = new HomePage(getDriver())
                .gotoHomePage()
                .openFolder(FOLDER_NAME)
                .editDescription("Edited ")
                .clickSubmitButton()
                .getDescription();

        Assert.assertEquals(finalResult, "Edited");
    }

    @Test(dependsOnMethods = "testEditExistingDescription")
    public void testDescriptionsPreviewButton () {
        String finalResult = new HomePage(getDriver())
                .gotoHomePage()
                .openFolder(FOLDER_NAME)
                .getDescriptionViaPreview();

        Assert.assertEquals(finalResult, "Edited");
    }

    @Test(dependsOnMethods = "testDescriptionsPreviewButton")
    public void testClearDescription () {
        String finalResult = new HomePage(getDriver())
                .gotoHomePage()
                .openFolder(FOLDER_NAME)
                .clearDescription()
                .getDescriptionButtonText();

        Assert.assertEquals(finalResult, "Add description");
    }
}
