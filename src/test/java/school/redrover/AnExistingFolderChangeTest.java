package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class AnExistingFolderChangeTest extends BaseTest {

    private static final String FOLDER_NAME = "Folder";
    private static final String EDITED_FOLDER_NAME = "FolderEdited";
    private static final List<String> setOfIncorrectSymbols = new ArrayList<>(List.of("$", "%", "#", "&amp;", "[", "]", "@", "!", "^", "/", ":", "*", "?", "|"));

    @Test
    public void testNoChangesWarning () {
        String warningMessage = new HomePage(getDriver())
                .createNewFolder(FOLDER_NAME)
                .openFolder(FOLDER_NAME)
                .renameItem(FOLDER_NAME)
                .getRenameWarningMessage();

        Assert.assertEquals(warningMessage,"The new name is the same as the current name.");
    }

    @Test(dependsOnMethods = "testNoChangesWarning")
    public void testSavingWithEmptyName () {
        String warningMessage = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .renameItem("")
                .getRenameWarningMessage();

        Assert.assertEquals(warningMessage,"No name is specified");
    }

    @Test(dependsOnMethods = "testSavingWithEmptyName")
    public void testRenameFromFoldersPage () {
        String newItemName = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .renameItem(EDITED_FOLDER_NAME)
                .getItemName();

        Assert.assertEquals(newItemName, EDITED_FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testRenameFromFoldersPage")
    public void testNotAllowedSymbols () {
        for (String symbols : setOfIncorrectSymbols) {
            String incorrectSymbolsMessage = new HomePage(getDriver())
                    .gotoHomePage()
                    .openFolder(EDITED_FOLDER_NAME)
                    .renameItem(symbols)
                    .getRenameWarningMessage();

            Assert.assertEquals(incorrectSymbolsMessage,"‘%s’ is an unsafe character".formatted(symbols));
        }
    }
}
