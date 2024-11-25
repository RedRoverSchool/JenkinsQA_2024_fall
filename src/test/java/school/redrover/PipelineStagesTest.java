package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class PipelineStagesTest extends BaseTest {

    private static final String PIPELINE_NAME = "TestPipeline";
    private static final List<String> PIPELINE_STAGES = List.of("Start", "Build", "Test", "End");
    private static final String PIPELINE_SCRIPT = """
        pipeline {agent any\n stages {
        stage('Build') {steps {echo 'Building the application'}}
        stage('Test') {steps {error 'Test stage failed due to an error'}}
        }
        """;

    @Test
    public void testListOfRecentBuildsISDisplayedOnStages() {

        List<WebElement> pipelineBuilds = new HomePage(getDriver())
                                              .clickNewItem()
                                              .enterItemName(PIPELINE_NAME)
                                              .selectPipelineAndClickOk()
                                              .clickSaveButton()
                                              .clickOnBuildNowItemOnSidePanelAndWait()
                                              .clickOnStagesItemOnSidePanel()
                                              .getAllPipelineBuilds();

        Assert.assertFalse(pipelineBuilds.isEmpty());
    }

    @Test
    public void testStagesAreDisplayedInPipelineGraph() {

        List<String> stagesNames = new HomePage(getDriver())
                                       .clickNewItem()
                                       .enterItemName(PIPELINE_NAME)
                                       .selectPipelineAndClickOk()
                                       .addScriptToPipeline(PIPELINE_SCRIPT)
                                       .clickSaveButton()
                                       .clickOnBuildNowItemOnSidePanelAndWait()
                                       .clickOnStagesItemOnSidePanel()
                                       .getAllStagesNames();

        Assert.assertEquals(stagesNames, PIPELINE_STAGES);
    }

    @Test
    public void testStatusIconsAreDisplayedInPipelineGraph() {

        List<WebElement> icons = new HomePage(getDriver())
                                     .clickNewItem()
                                     .enterItemName(PIPELINE_NAME)
                                     .selectPipelineAndClickOk()
                                     .addScriptToPipeline(PIPELINE_SCRIPT)
                                     .clickSaveButton()
                                     .clickOnBuildNowItemOnSidePanelAndWait()
                                     .clickOnStagesItemOnSidePanel()
                                     .getGreenAndRedIcons();

        Assert.assertTrue(icons.get(0).isDisplayed(), "Green Icon must be displayed");
        Assert.assertTrue(icons.get(1).isDisplayed(), "Red Icon must be displayed");
    }

    @Test
    public void testStatusIconsColor() {

        List<WebElement> icons = new HomePage(getDriver())
                                     .clickNewItem()
                                     .enterItemName(PIPELINE_NAME)
                                     .selectPipelineAndClickOk()
                                     .addScriptToPipeline(PIPELINE_SCRIPT)
                                     .clickSaveButton()
                                     .clickOnBuildNowItemOnSidePanelAndWait()
                                     .clickOnStagesItemOnSidePanel()
                                     .getGreenAndRedIcons();

        Assert.assertEquals(icons.get(0).getCssValue("color"), "rgba(30, 166, 75, 1)");
        Assert.assertEquals(icons.get(1).getCssValue("color"), "rgba(230, 0, 31, 1)");
    }

}
