package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

@Epic("13 User")
public class UserTest extends BaseTest {

    private static final String FULL_USER_NAME = "Ivan Petrov";

    @Test
    @Story("US_13.001 Create user")
    @Description("TC_13.001.02 Can't Create User with empty fields")
    public void testImpossiblyToCreateNewUserWithEmptyFields() {
        String validationMessage = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .clickCreateUser()
                .clickCreateUserButton()
                .getValidationMessage();

        Allure.step(" \uD83D\uDCCC Expected result: The system prohibits the creation of a new user with empty fields.");
        Assert.assertEquals(validationMessage, "\"\" is prohibited as a username for security reasons.");
    }

    @Test
    @Story("US_13.001 Create user")
    @Description("TC_13.001.01 Can Create User with valid data")
    public void testCreateNewUser() {
        List<String> userList = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .clickCreateUser()
                .fillFormByValidDataToCreateUser(FULL_USER_NAME)
                .getCreatedUserName();

        Allure.step(" \uD83D\uDCCC Expected result: A new user %s is created.".formatted(FULL_USER_NAME));
        Assert.assertEquals(userList.size(), 2);
        Assert.assertEquals(userList.get(1), FULL_USER_NAME);
    }

    @Test
    @Story("US_13.003 Modify User")
    @Description("TC_13.003.01 Can Add Description For User")
    public void testAddDescriptionForUser() {
        String userDescription = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .createNewUser(FULL_USER_NAME)
                .clickToConfigureUser(FULL_USER_NAME)
                .addUserDescription()
                .getUserDescription();

        Allure.step(" \uD83D\uDCCC Expected result: User Description is added.");
        Assert.assertEquals(userDescription, "User Description");
    }

    @Test
    @Story("US_13.003 Modify User")
    @Description("TC_13.003.02 Add Time Zone For User")
    public void testAddTimeZoneForUser() {
        String userTimeZone = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .createNewUser(FULL_USER_NAME)
                .clickToConfigureUser(FULL_USER_NAME)
                .addUserTimeZone()
                .clickConfigureUserSidebar()
                .getUserTimeZone();

        Allure.step(" \uD83D\uDCCC Expected result: User Time Zone is added.");
        Assert.assertEquals(userTimeZone, "Etc/GMT+2");
    }

    @Test
    @Story("US_13.002 Delete User")
    @Description("TC_13.002.01 Can Delete User Via Delete Button On Users Page")
    public void testDeleteUserViaDeleteButtonOnUsersPage() {
        List<String> userList = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .createNewUser(FULL_USER_NAME)
                .deleteUserFromUsersPage()
                .getCreatedUserName();

        Allure.step(" \uD83D\uDCCC Expected result: A new user %s is deleted.".formatted(FULL_USER_NAME), () -> {
            Assert.assertEquals(userList.size(), 1);
            Assert.assertEquals(userList.get(0), "admin");
        });

    }

    @Test
    @Story("US_13.002 Delete User")
    @Description("TC_13.002.03 Can Delete User Via Configure User Page")
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

        Allure.step(" \uD83D\uDCCC Expected result: A new user %s is deleted.".formatted(FULL_USER_NAME), () -> {
            Assert.assertEquals(userList.size(), 1);
            Assert.assertEquals(userList.get(0), "admin");
        });

    }
}
