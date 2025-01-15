package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class UserTest extends BaseTest {

    private static final String FULL_USER_NAME = "Ivan Petrov";

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
        String validationMessage = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .clickCreateUser()
                .clickCreateUserButton()
                .getValidationMessage();

        Assert.assertEquals(validationMessage, "\"\" is prohibited as a username for security reasons.");
    }

    @Test
    public void testCreateNewUser() {
        List<String> userList = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .clickCreateUser()
                .fillFormByValidDataToCreateUser(FULL_USER_NAME)
                .getCreatedUserName();

        Assert.assertEquals(userList.size(), 2);
        Assert.assertEquals(userList.get(1), FULL_USER_NAME);
    }

    @Test
    public void testAddDescriptionForUser() {
        String userDescription = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .createNewUser(FULL_USER_NAME)
                .clickToConfigureUser(FULL_USER_NAME)
                .addUserDescription()
                .getUserDescription();

        Assert.assertEquals(userDescription, "User Description");
    }

    @Test
    public void testAddTimeZoneForUser() {
        String userTimeZone = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .createNewUser(FULL_USER_NAME)
                .clickToConfigureUser(FULL_USER_NAME)
                .addUserTimeZone()
                .clickConfigureUserSidebar()
                .getUserTimeZone();

        Assert.assertEquals(userTimeZone, "Etc/GMT+2");
    }

    @Test
    public void testDeleteUserViaDeleteButtonOnUsersPage() {
        List<String> userList = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .createNewUser(FULL_USER_NAME)
                .deleteUserFromUsersPage()
                .getCreatedUserName();

        Assert.assertEquals(userList.size(), 1);
        Assert.assertEquals(userList.get(0), "admin");
    }

    @Test
    public void testDeleteUserViaConfigureUserPage() {
        List<String> userList = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .createNewUser(FULL_USER_NAME)
                .clickToConfigureUser(FULL_USER_NAME)
                .deleteUserFromConfigureUserPage()
                .openManageJenkinsPage()
                .openUsersPage()
                .getCreatedUserName();

        Assert.assertEquals(userList.size(), 1);
        Assert.assertEquals(userList.get(0), "admin");
    }
}
