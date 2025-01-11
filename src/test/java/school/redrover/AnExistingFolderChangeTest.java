package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class AnExistingFolderChangeTest extends BaseTest {

    private static final String FOLDER_NAME = "Folder";
    private static final String EDITED_FOLDER_NAME = "FolderEdited";

    @Test
    public void testNoChangesWarning() {
        TestUtils.createFolder(getDriver(), FOLDER_NAME);

        String warningMessage = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .renameItem(FOLDER_NAME)
                .getRenameWarningMessage();

        Assert.assertEquals(warningMessage,"The new name is the same as the current name.");
    }

    @Test(dependsOnMethods = "testNoChangesWarning")
    public void testSavingWithEmptyName() {
        String warningMessage = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .renameItem("")
                .getRenameWarningMessage();

        Assert.assertEquals(warningMessage,"No name is specified");
    }

    @Test(dependsOnMethods = "testSavingWithEmptyName")
    public void testRenameFromFoldersPage() {
        String newItemName = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .renameItem(EDITED_FOLDER_NAME)
                .getItemName();

        Assert.assertEquals(newItemName, EDITED_FOLDER_NAME);
    }
}
