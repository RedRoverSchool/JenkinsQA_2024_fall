package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class FreeStyleProjectEnablingTest extends BaseTest {

    private static final String PROJECT_NAME = "FreeStyleProjectTest";

    @Test
    public void testDefaultState() {
        TestUtils.createFreestyleProject(getDriver(), PROJECT_NAME);

        boolean currentState = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .getEnablingCurrentState();

        Assert.assertTrue(currentState);
    }

    @Test(dependsOnMethods = "testDefaultState")
    public void testDisableEnabled() {
        String indicatorText = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .changeEnablingState()
                .getDisabledProjectIndicator();

        Assert.assertEquals(indicatorText, "This project is currently disabled\n" +
                "Enable");
    }

    @Test (dependsOnMethods = "testDisableEnabled")
    public void testEnableWithIndicator() {
        boolean currentState = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .changeEnablingStateViaIndicator()
                .gotoHomePage()
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .getEnablingCurrentState();

        Assert.assertTrue(currentState);
    }

    @Test (dependsOnMethods = "testEnableWithIndicator")
    public void testEnabledFromProjectPage() {
        boolean currentState = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .changeEnablingState().gotoHomePage()
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .changeEnablingState()
                .gotoHomePage()
                .openFreestyleProject(PROJECT_NAME)
                .clickSidebarConfigButton()
                .getEnablingCurrentState();

        Assert.assertTrue(currentState);
    }
}
