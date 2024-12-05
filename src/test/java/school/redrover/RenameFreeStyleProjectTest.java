package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class RenameFreeStyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "FreeStyleProjectTest";
    private static final String PROJECT_NAME_EDITED = "FreeStyleProjectTestEdited";
    private static final List<String> setOfIncorrectSymbols = new ArrayList<>(List.of("$", "%", "#", "&amp;", "[", "]", "@", "!", "^", "/", ":", "*", "?", "|"));

    @Test
    public void testCorrectName () {
        String renamingResult = new HomePage(getDriver())
                .createFreestyleProject(PROJECT_NAME)
                .openFreestyleProject(PROJECT_NAME)
                .renameItem(PROJECT_NAME_EDITED)
                .getProjectName();

        Assert.assertEquals(renamingResult, PROJECT_NAME_EDITED);
    }

    @Test(dependsOnMethods = "testCorrectName")
    public void testTheSameName () {
        String theSameNameWarning = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME_EDITED)
                .renameItem(PROJECT_NAME_EDITED)
                .getRenameWarningMessage();

        Assert.assertEquals(theSameNameWarning, "The new name is the same as the current name.");
    }

    @Test(dependsOnMethods = "testTheSameName")
    public void testEmptyName () {
        String emptyNameWarning = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME_EDITED)
                .renameItem("")
                .getRenameWarningMessage();

        Assert.assertEquals(emptyNameWarning, "No name is specified");
    }

    @Test(dependsOnMethods = "testEmptyName")
    public void testIncorrectSymbols () {
        for (String symbols : setOfIncorrectSymbols) {
            String incorrectSymbolsMessage = new HomePage(getDriver())
                    .gotoHomePage()
                    .openFreestyleProject(PROJECT_NAME_EDITED)
                    .renameItem(symbols)
                    .getRenameWarningMessage();

            Assert.assertEquals(incorrectSymbolsMessage,"‘%s’ is an unsafe character".formatted(symbols));
        }
    }
}
