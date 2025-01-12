package school.redrover;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;


public class RenameFreeStyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "FreeStyleProjectTest";
    private static final String PROJECT_NAME_EDITED = "FreeStyleProjectTestEdited";

    private String escapeHtml(String input) {
        if (input == null) return null;
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    @DataProvider(name = "providerUnsafeCharacters")
    public Object[][] providerUnsafeCharacters() {
        return new Object[][]{
                {"\\"}, {"]"}, {":"}, {"#"}, {"&"}, {"?"}, {"!"}, {"@"},
                {"$"}, {"%"}, {"^"}, {"*"}, {"|"}, {"/"}, {"<"}, {">"},
                {"["}, {";"}
        };
    }

    @Test
    public void testCorrectName() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String renamingResult = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .renameItem(PROJECT_NAME_EDITED)
                .getProjectName();

        Assert.assertEquals(renamingResult, PROJECT_NAME_EDITED);
    }

    @Test(dependsOnMethods = "testCorrectName")
    public void testEmptyName() {
        String emptyNameWarning = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME_EDITED)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName("")
                .clickRenameButtonLeadingToError()
                .getErrorMessage();

        Assert.assertEquals(emptyNameWarning, "No name is specified");
    }

    @Test(dependsOnMethods = "testEmptyName")
    public void testTheSameName() {
        String theSameNameWarning = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME_EDITED)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName(PROJECT_NAME_EDITED)
                .clickRenameButtonLeadingToError()
                .getErrorMessage();

        Assert.assertEquals(theSameNameWarning, "The new name is the same as the current name.");
    }

    @Test(dataProvider = "providerUnsafeCharacters")
    public void testIncorrectSymbols(String unsafeCharacter) {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        String invalidNameMessage = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName(unsafeCharacter)
                .clickRenameButtonLeadingToError()
                .getErrorMessage();

        Assert.assertEquals(invalidNameMessage, "‘%s’ is an unsafe character".formatted(escapeHtml(unsafeCharacter)));
    }
}

