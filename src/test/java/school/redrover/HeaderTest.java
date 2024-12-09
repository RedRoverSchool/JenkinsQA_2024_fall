package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

public class HeaderTest extends BaseTest {

    @Test
    public void testFullNameHelperText() {
        String fullNameInputTip = new HomePage(getDriver())
                .clickAdmin()
                .clickConfigureSidebar()
                .clickFullNameTooltip()
                .getFullNameHelperInputText();

        Assert.assertTrue(fullNameInputTip.contains(
                "Specify your name in a more human-friendly format, so that people can see your real name as opposed to your ID."));
    }

    @Test
    public void testLogOut() {
        String signInTitle = new HomePage(getDriver())
                .clickLogOut()
                .getSignInTitle();

        Assert.assertEquals(signInTitle, "Sign in to Jenkins");
    }

    @Test
    public void testGetStatusIDDescription() {
        String adminDescription = new HomePage(getDriver())
                .openAdminDropdownMenu()
                .clickConfigureAdminDropdownMenu()
                .clickStatusSidebar()
                .getUserIDText();

        Assert.assertEquals(adminDescription, "Jenkins User ID: admin");
    }
}

