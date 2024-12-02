package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.PipelineProjectPage;
import school.redrover.page.PipelineSyntaxPage;
import school.redrover.runner.BaseTest;

public class SelectAndApplyScriptsTest extends BaseTest {

    private static final String PIPELINE_NAME = "TestName";

    @Test
    public void testPipelineSyntaxPageIsPresent() {
        String bredCrumbs = new PipelineProjectPage(getDriver())
                .clickNewItem()
                .enterItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .gotoHomePage()
                .openPipelineProject(PIPELINE_NAME)
                .gotoPipelineSyntaxPageFromLeftPanel(PIPELINE_NAME)
                .getBreadCrumb(PIPELINE_NAME);

        Assert.assertEquals(bredCrumbs, "Pipeline Syntax");
    }

    @Test(dependsOnMethods = "testPipelineSyntaxPageIsPresent")
    public void testSelectScript() {
        String selectItem = new PipelineProjectPage(getDriver())
                .gotoHomePage()
                .openPipelineProject(PIPELINE_NAME)
                .gotoPipelineSyntaxPageFromLeftPanel(PIPELINE_NAME)
                .selectNewStep("bat: Windows Batch Script")
                .clickGeneratePipelineScript()
                .clickCopy()
                .getTitleOfSelectedScript("bat: Windows Batch Script");

        Assert.assertEquals(selectItem, "bat");
    }

    @Test(dependsOnMethods = "testSelectScript")
    public void testCopyAndPasteScript() {
        String pastedText = new PipelineSyntaxPage(getDriver())
                .gotoHomePage()
                .openPipelineProject(PIPELINE_NAME)
                .clickConfigureSidebar(PIPELINE_NAME)
                .pastScript()
                .getScriptText();

        Assert.assertEquals(pastedText, "bat ''");
    }
}
