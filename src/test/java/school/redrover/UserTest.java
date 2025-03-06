package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.page.user.CreateUserPage;
import school.redrover.page.user.UsersPage;
import school.redrover.runner.BaseTest;

import java.util.Arrays;
import java.util.List;

@Epic("13 User")
public class UserTest extends BaseTest {

    private static final String USERNAME = "Ivan";
    private static final String PASSWORD = "123";
    private static final String FULL_NAME = "Ivan Petrov";
    private static final String EMAIL = "Petrov@gmail.com";
    private static final List<String> ERROR_MESSAGES = Arrays.asList(
            "\"\" is prohibited as a username for security reasons.",
            "Password is required",
            "Password is required",
            "\"\" is prohibited as a full name for security reasons.",
            "Invalid e-mail address"
    );

    @Test
    @Story("US_13.001 Create user")
    @Description("TC_13.001.02 Can't Create User with empty fields")
    public void testImpossiblyToCreateNewUserWithEmptyFields() {
        List<String> validationMessages = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .clickCreateUserButton()
                .clickCreateUserFormSubmit(CreateUserPage.class)
                .getValidationMessages();

        Allure.step(" \uD83D\uDCCC Expected result: The system prohibits the creation of a new user with empty fields.");
        Assert.assertEquals(validationMessages.size(), ERROR_MESSAGES.size());
        for (int i = 0; i < validationMessages.size(); i++) {
            Assert.assertEquals(validationMessages.get(i), ERROR_MESSAGES.get(i));
        }
    }

    @Test
    @Story("US_13.001 Create user")
    @Description("TC_13.001.01 Can Create User with valid data")
    public void testCreateNewUserWithValidData() {
        List<String> userList = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .clickCreateUserButton()
                .enterUsername(USERNAME)
                .enterFullName(FULL_NAME)
                .enterPassword(PASSWORD)
                .enterConfirmPassword(PASSWORD)
                .enterEmail(EMAIL)
                .clickCreateUserFormSubmit(UsersPage.class)
                .getUserList();

        Allure.step(" \uD83D\uDCCC Expected result: A new user %s is created.".formatted(FULL_NAME));
        Assert.assertEquals(userList.size(), 2);
        Assert.assertEquals(userList.get(1), FULL_NAME);
    }

    @Test(dependsOnMethods = "testCreateNewUserWithValidData")
    @Story("US_13.003 Modify User")
    @Description("TC_13.003.01 Can Add Description For User")
    public void testAddDescriptionForUser() {
        String userDescription = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .clickToConfigureUser(FULL_NAME)
                .enterDescription("User Description")
                .submitButton()
                .getUserDescription();

        Allure.step(" \uD83D\uDCCC Expected result: User Description is added.");
        Assert.assertEquals(userDescription, "User Description");
    }

    @Test(dependsOnMethods = "testAddDescriptionForUser")
    @Story("US_13.003 Modify User")
    @Description("TC_13.003.02 Add Time Zone For User")
    public void testAddTimeZoneForUser() {
        String userTimeZone = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .clickToConfigureUser(FULL_NAME)
                .clickAccountSidebar()
                .addUserTimeZone("Etc/GMT+2")
                .clickAccountSidebar()
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
                .clickCreateUserButton()
                .enterUsername(USERNAME)
                .enterFullName(FULL_NAME)
                .enterPassword(PASSWORD)
                .enterConfirmPassword(PASSWORD)
                .enterEmail(EMAIL)
                .clickCreateUserFormSubmit(UsersPage.class)
                .deleteUserFromUsersPage()
                .getUserList();

        Allure.step(" \uD83D\uDCCC Expected result: A new user %s is deleted.".formatted(FULL_NAME), () -> {
            Assert.assertEquals(userList.size(), 1);
            Assert.assertNotEquals(userList.get(0), FULL_NAME);
        });
    }

    @Test(dependsOnMethods = "testAddTimeZoneForUser")
    @Story("US_13.002 Delete User")
    @Description("TC_13.002.03 Can Delete User Via Configure User Page")
    public void testDeleteUserViaConfigureUserPage() {
        List<String> userList = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .clickToConfigureUser(FULL_NAME)
                .deleteUserFromSidePanelAndClickOk()
                .openManageJenkinsPage()
                .openUsersPage()
                .getUserList();

        Allure.step(" \uD83D\uDCCC Expected result: A new user %s is deleted.".formatted(FULL_NAME), () -> {
            Assert.assertEquals(userList.size(), 1);
            Assert.assertNotEquals(userList.get(0), FULL_NAME);
        });
    }
}
