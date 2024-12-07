package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

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
                .getValidationMessage();

        Assert.assertEquals(title, "\"\" is prohibited as a username for security reasons.");
    }

    @Test
    public void testCreateNewUser() {
        final String fullName = "Ivan Petrov";

        List<String> userList = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .clickCreateUser()
                .fillFormByValidDataToCreateUser(fullName)
                .getCreatedUserName();

        Assert.assertEquals(userList.size(),2);
        Assert.assertEquals(userList.get(0), fullName);
    }
}
