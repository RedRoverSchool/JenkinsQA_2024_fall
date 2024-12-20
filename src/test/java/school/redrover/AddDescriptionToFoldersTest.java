package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class AddDescriptionToFoldersTest extends BaseTest {

    private static final String FOLDER_NAME = "FolderTest";
    private static final String DESCRIPTION = "Description text";
    private static final String DESCRIPTION_EDITED = "Edited";

    @Test
    public void testExistingFolderWithNoDescription() {
        TestUtils.createFolder(getDriver(), FOLDER_NAME);

        String finalResult = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .editDescription(DESCRIPTION)
                .clickSubmitButton()
                .getDescription();

        Assert.assertEquals(finalResult, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testExistingFolderWithNoDescription")
    public void testEditExistingDescription() {
        String finalResult = new HomePage(getDriver())
                .gotoHomePage()
                .openFolder(FOLDER_NAME)
                .editDescription(DESCRIPTION_EDITED)
                .clickSubmitButton()
                .getDescription();

        Assert.assertEquals(finalResult, DESCRIPTION_EDITED);
    }

    @Test(dependsOnMethods = "testEditExistingDescription")
    public void testDescriptionsPreviewButton() {
        String finalResult = new HomePage(getDriver())
                .gotoHomePage()
                .openFolder(FOLDER_NAME)
                .getDescriptionViaPreview();

        Assert.assertEquals(finalResult, DESCRIPTION_EDITED);
    }

    @Test(dependsOnMethods = "testDescriptionsPreviewButton")
    public void testClearDescription() {
        String finalResult = new HomePage(getDriver())
                .gotoHomePage()
                .openFolder(FOLDER_NAME)
                .clearDescription()
                .getDescriptionButtonText();

        Assert.assertEquals(finalResult, "Add description");
    }
}
