package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DashboardTest extends BaseTest {

    @Test
    public void testVerifyProjectOrderByNameASCByDefault() {
        List<String> projectNameList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("FPipelineProject")
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .clickNewItem()
                .enterItemName("APipelineProject")
                .selectPipelineAndClickOk()
                .clickToggleToDisableOrEnableProject()
                .clickSaveButton()
                .gotoHomePage()
                .clickNewItem()
                .enterItemName("ZPipelineProject")
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .getItemList();

        List<String> expectedList = projectNameList.stream().sorted().toList();

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
        final String invalidPipelineScriptFile = "InvalidPipelineScript.txt";
        final String validPipelineScriptFile = "ValidPipelineScript.txt";
        final String pSuccessBuild = "FPipelineProject";
        final String pDisable = "APipelineProject";
        final String pFailedBuild = "ZPipelineProject";
        final String pNoBuild = "1PipelineProject";

        List<String> projectNameList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(pSuccessBuild)
                .selectPipelineAndClickOk()
                .enterScriptFromFile(validPipelineScriptFile)
                .clickSaveButton()
                .gotoHomePage()
                .selectBuildNowFromItemMenu(pSuccessBuild)
                .clickNewItem()
                .enterItemName(pDisable)
                .selectPipelineAndClickOk()
                .clickToggleToDisableOrEnableProject()
                .clickSaveButton()
                .gotoHomePage()
                .clickNewItem()
                .enterItemName(pFailedBuild)
                .selectPipelineAndClickOk()
                .enterScriptFromFile(invalidPipelineScriptFile)
                .clickSaveButton()
                .gotoHomePage()
                .selectBuildNowFromItemMenu(pFailedBuild)
                .clickNewItem()
                .enterItemName(pNoBuild)
                .selectPipelineAndClickOk()
                .enterScriptFromFile(invalidPipelineScriptFile)
                .clickSaveButton()
                .gotoHomePage()
                .clickStatusTableHeaderChangeOrder()
                .getItemList();

        Assert.assertEquals(projectNameList, List.of(pNoBuild, pDisable, pSuccessBuild, pFailedBuild));
    }

}
