package school.redrover.testdata;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

    private static final String maxProjectName = "A".repeat(255);

    @DataProvider(name = "projectNames")
    public Object[][] projectNames() {
        return new Object[][]{
                {"ValidProject"},
                {"A"},
                {maxProjectName}
        };
    }

    @DataProvider(name = "renameProjectNames")
    public static Object[][] renameProjectNames() {
        return new Object[][]{
                {"ValidProject", "B".repeat(255)},
                {"A", "B"},
                {maxProjectName, "NewValidProject"}
        };
    }

    @DataProvider
    public Object[][] providerUnsafeCharacters() {
        return new Object[][]{
                {"\\"}, {"]"}, {":"}, {"#"}, {"&"}, {"?"}, {"!"}, {"@"},
                {"$"}, {"%"}, {"^"}, {"*"}, {"|"}, {"/"}, {"<"}, {">"},
                {"["}, {";"}
        };
    }

    @DataProvider
    public Object[][] provideSystemConfigurationItems() {
        return new Object[][]{
                {"System"}, {"Tools"}, {"Plugins"}, {"Nodes"}, {"Clouds"}, {"Appearance"}
        };
    }

    @DataProvider
    public Object[][] projectNameProvider() {
        return new Object[][]{
                {"FPipelineProject"},
                {"APipelineProject"},
                {"ZPipelineProject"}
        };
    }

    @DataProvider
    public static Object[][] projectNameAndXmlFileCreate() {
        return new Object[][] {
                {"FreeStyleProjectName", ModeType.FREESTYLE_PROJECT_MODE},
                {"PipelineProjectName", ModeType.PIPELINE_MODE},
                {"MultiConfigurationProjectName", ModeType.MULTI_CONFIGURATION_PROJECT_MODE},
                {"FolderName", ModeType.FOLDER_MODE},
                {"MultiBranchPipelineProjectName", ModeType.MULTIBRANCH_PIPELINE_MODE}
        };
    }

}
