package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

@Epic("15 Nodes")
public class NodeTest extends BaseTest {

    private static final String NODE_NAME = "NewNodeName";

    @Test
    @Story("US_15.001 Create new node")
    @Description("TC_15.001.01 Create New Agent")
    public void testCreateNewAgent() {

        List<String> nodeItemList = new HomePage(getDriver())
                .openManageJenkinsPage()
                .clickNodesButton()
                .clickButtonNewNode()
                .enterNodeName(NODE_NAME)
                .selectPermanentAgent()
                .clickButtonCreate()
                .clickButtonSave()
                .getNodeList();

        Allure.step("Expected Result: The new node appears in the list on the Nodes Page");
        Assert.assertListContainsObject(nodeItemList, NODE_NAME, "List not contain new node");
    }

    @Test()
    @Story("US_15.002 Delete node")
    @Description("TC_15.002.02 Delete Agent (Node) on Status page")
    public void testDeleteAgentFromStatusPageByOpeningFromBuildExecutorsStatusBlock() {
        TestUtils.createNode(getDriver(),NODE_NAME);

        List<String> nodeNameAfterList = new HomePage(getDriver())
                .openNodeFromBuildExecutorStatusBlock(NODE_NAME)
                .clickDeleteButtonSidebarAndConfirm()
                .getNodeNameList();

        Allure.step("The deleted node is not displayed on Home page in 'Build Executor Status' block");
        Assert.assertEquals(nodeNameAfterList.size(), 0);
    }

    @Test
    @Story("US_15.002 Delete node")
    @Description("TC_15.002.03 Delete Agent (Node) via dropdown menu from 'Build Executor Status' block")
    public void testDeleteAgentByChevronFromBuildExecutorsStatusBlock() {
        TestUtils.createNode(getDriver(), NODE_NAME);

        List<String> nodeNameAfterList = new HomePage(getDriver())
                .selectDeleteAgentFromBuildDropdownAndClickYes(NODE_NAME)
                .getNodeNameList();

        Allure.step("The deleted node is not displayed on Main page in 'Build Executor Status' block");
        Assert.assertEquals(nodeNameAfterList.size(), 0);
    }
}
