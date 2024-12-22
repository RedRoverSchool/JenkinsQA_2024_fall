package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;

public class AgentTest extends BaseTest {

    @Test
    public void testNewNodeAccessible() {

        String accessible = new HomePage(getDriver())
                .setUpAnAgent()
                .enterNodeName("NewNode")
                .clickPermanentAgent()
                .create()
                .enterRemoteRootDirectory("./jenkins-agent")
                .save()
                .getNewNode();

        Assert.assertEquals(accessible, "NewNode");
    }
}
