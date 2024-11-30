package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class PipelineProjectTemporary extends BaseTest {

    final private String projectNane = "PipelineProject";

    @Test
    public void testVerifyListOfActionsOnSidebar() {

        List<String> actualListActions = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectNane)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .openPipelineProject(projectNane)
                .getSidebarOptionList();

        Assert.assertEquals(actualListActions,
                List.of("Status", "Changes", "Build Now", "Configure", "Delete Pipeline", "Stages", "Rename", "Pipeline Syntax"));
    }
}
