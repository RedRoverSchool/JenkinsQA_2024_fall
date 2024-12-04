package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.Collections;
import java.util.List;

public class DashboardTest extends BaseTest {

    final String invalidPipelineScriptFile = """
                error_pipeline {{{
                    agent any
                    stages {
                        stage('Checkout') {
                            steps {echo 'Step: Checkout code from repository'}
                        }
                     }
                }
                """;

    final String validPipelineScriptFile = """
                pipeline {
                    agent any
                    stages {
                        stage('Checkout') {
                            steps {echo 'Step: Checkout code from repository'}
                        }
                     }
                }
                """;

    final String pSuccessBuild = "FPipelineProject";
    final String pDisable = "APipelineProject";
    final String pFailedBuild = "ZPipelineProject";
    final String pNoBuild = "1PipelineProject";

    @Test
    public void testVerifyProjectOrderByNameASCByDefault() {
        testPreparationCreateNoBuildProject("FPipelineProject");
        testPreparationCreateNoBuildProject("APipelineProject");
        testPreparationCreateNoBuildProject("ZPipelineProject");

        List<String> projectNameList = new HomePage(getDriver())
                .getItemList();

        List<String> expectedList = projectNameList.stream().sorted().toList();

        Assert.assertEquals(projectNameList.size(), 3);
        Assert.assertEquals(projectNameList, expectedList);
    }

    @Test(dependsOnMethods = "testVerifyDisplayIconDownArrowNextToNameByDefault")
    public void testVerifyProjectOrderByNameDesc() {

        List<String> actualList = new HomePage(getDriver())
                .clickNameTableHeaderChangeOrder()
                .getItemList();

        List<String> expectedList = new HomePage(getDriver()).getItemList()
                .stream()
                .sorted(Collections.reverseOrder())
                .toList();

        Assert.assertEquals(actualList, expectedList);
    }

    @Test(dependsOnMethods = "testVerifyProjectOrderByNameASCByDefault")
    public void testVerifyDisplayIconDownArrowNextToNameByDefault() {
        String titleTableHeader = new HomePage(getDriver())
                .getTitleTableHeaderWithDownArrow();

        Assert.assertEquals(titleTableHeader, "Name");
    }

    @Test(dependsOnMethods = "testVerifyDisplayIconDownArrowNextToNameByDefault")
    public void testDisplayDownArrowOnSelectedColumnName() {

        String titleTableHeader = new HomePage(getDriver())
                .clickStatusTableHeaderChangeOrder()
                .getTitleTableHeaderWithUpArrow();

        Assert.assertEquals(titleTableHeader, "S");
    }

    @Test
    public void testVerifyProjectOrderByStatusASCByDefault() {

        testPreparationCreateNoBuildProject(pNoBuild);
        testPreparationCreateDisableProject(pDisable);
        testPreparationCreateSuccessBuildProject(pSuccessBuild);
        testPreparationCreateFailedBuildProject(pFailedBuild);


        List<String> projectNameList = new HomePage(getDriver())
                .clickStatusTableHeaderChangeOrder()
                .getItemList();

        Assert.assertEquals(projectNameList.size(), 4);
        Assert.assertEquals(projectNameList, List.of(pNoBuild, pDisable, pSuccessBuild, pFailedBuild));
    }

    private void testPreparationCreateNoBuildProject(String projectName) {
        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage();
    }

    private void testPreparationCreateDisableProject(String projectName) {
        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .clickToggleToDisableOrEnableProject()
                .clickSaveButton()
                .gotoHomePage();
    }

    private void testPreparationCreateSuccessBuildProject(String projectName) {
        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .enterScriptFromFile(validPipelineScriptFile)
                .clickSaveButton()
                .gotoHomePage()
                .selectBuildNowFromItemMenu(projectName);
    }

    private void testPreparationCreateFailedBuildProject(String projectName) {
        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectPipelineAndClickOk()
                .enterScriptFromFile(invalidPipelineScriptFile)
                .clickSaveButton()
                .gotoHomePage()
                .selectBuildNowFromItemMenu(projectName);
    }


}
