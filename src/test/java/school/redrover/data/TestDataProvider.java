package school.redrover.data;

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

}
