package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;

public class SelectAndApplyScriptsTest extends BaseTest {

    private final static String PIPELINE_NAME = "TestName";
    private final static String SELECT_VALUE = "cleanWs: Delete workspace when build is done";
    private final static String EXPECTED_RESULT = SELECT_VALUE.split(":")[0].trim();

    @Test
    public void testPipelineSyntaxPageIsPresent() {
        String bredCrumbs = new HomePage(getDriver())
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
        String selectItem = new HomePage(getDriver())
                .openPipelineProject(PIPELINE_NAME)
                .gotoPipelineSyntaxPageFromLeftPanel(PIPELINE_NAME)
                .selectNewStep(SELECT_VALUE)
                .getTitleOfSelectedScript(SELECT_VALUE);

        Assert.assertEquals(selectItem, EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testSelectScript")
    public void testCopyAndPasteScript() {
        String pastedText = new HomePage(getDriver())
                .openPipelineProject(PIPELINE_NAME)
                .gotoPipelineSyntaxPageFromLeftPanel(PIPELINE_NAME)
                .selectNewStep(SELECT_VALUE)
                .clickGeneratePipelineScript()
                .clickCopy()
                .gotoHomePage()
                .openPipelineProject(PIPELINE_NAME)
                .clickSidebarConfigButton()
                .pasteScript()
                .getScriptText();

        Assert.assertEquals(pastedText, EXPECTED_RESULT);
    }
}
