package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.FolderDescriptionPage;
import school.redrover.runner.BaseTest;

public class AddDescriptionToFoldersTest extends BaseTest {

    private static final String FOLDER_NAME = "FolderTest";
    private static final String FOLDER_BUTTON_XPATH = "//*[@id='j-add-item-type-nested-projects']/ul/li[1]/div[2]";

    @Test
    public void testExistingFolderWithNoDescription () {
        String finalResult = new FolderDescriptionPage(getDriver())
                .createFolder(FOLDER_NAME, FOLDER_BUTTON_XPATH)
                .clickOnSave()
                .provideDescription("Description text")
                .clickOnSave()
                .getCurrentDescription();

        Assert.assertEquals(finalResult, "Description text");
    }

    @Test(dependsOnMethods = "testExistingFolderWithNoDescription")
    public void testEditExistingDescription () {
        String finalResult = new FolderDescriptionPage(getDriver())
                .openFolder(FOLDER_NAME)
                .provideDescription("Edited ")
                .clickOnSave()
                .getCurrentDescription();

        Assert.assertEquals(finalResult, "Edited Description text");
    }

    @Test(dependsOnMethods = "testEditExistingDescription")
    public void testDescriptionsPreviewButton () {
        String finalResult = new FolderDescriptionPage(getDriver())
                .openFolder(FOLDER_NAME)
                .enablePreview()
                .getDescriptionViaPreview();

        Assert.assertEquals(finalResult, "Edited Description text");
    }

    @Test(dependsOnMethods = "testDescriptionsPreviewButton")
    public void testClearDescription () {
        String finalResult = new FolderDescriptionPage(getDriver())
                .openFolder(FOLDER_NAME)
                .clearDescription()
                .clickOnSave()
                .getCurrentDescription();

        Assert.assertEquals(finalResult, "");
    }
}
