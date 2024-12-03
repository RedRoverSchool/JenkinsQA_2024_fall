package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

public class ManageJenkinsUsersTest extends BaseTest {

    @Test
    public void testCheckTitle() {
        String title = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .getTitle();

        Assert.assertTrue(title.startsWith("Users"), title);
    }

    @Test
    public void testImpossiblyToCreateNewUserWithEmptyFields() {
        String title = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .clickCreateUser()
                .clickCreateUserButton()
                .getValidationNessage();

        Assert.assertEquals(title, "\"\" is prohibited as a username for security reasons.");
    }

    @Test
    public void testCreateNewUser() {
        String userName = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .clickCreateUser()
                .fillFormByValidDataToCreateUser()
                .getCreatedUserName();

        Assert.assertEquals(userName, "Ivan Petrov");
    }

}
