package school.redrover.api.restassured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.controllers.UserController;
import school.redrover.models.user.AllUsersResponse;
import school.redrover.models.user.User;
import school.redrover.runner.BaseApiTest;
import school.redrover.testdata.UserTestData;

import java.util.List;

@Epic("API")
@Feature("13 User")
public class UserApiTest extends BaseApiTest {
    UserController userController = new UserController();

    @Test
    @Description("13.001.01 Create user with valid data")
    public void testCreateUser() {
        Response userResponse = userController.createUser(UserTestData.getDefaultUser());

        Assert.assertEquals(userResponse.statusCode(), 200);
        Assert.assertTrue(userResponse.time() <= 2000);
    }

    @Test
    @Description("13.001.04 Get all users")
    public void testGetAllUsers() {
        userController.createUser(UserTestData.getDefaultUser());

        Response allUserResponse = userController.getAllUsers();
        AllUsersResponse allUsers = allUserResponse.as(AllUsersResponse.class, ObjectMapperType.JACKSON_2);
        List<AllUsersResponse.User> userList = allUsers.getUsers();

        Assert.assertEquals(allUserResponse.statusCode(), 200);
        Assert.assertTrue(allUserResponse.time() <= 2000);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(userList.stream().anyMatch(user ->
                    user.getId().equals(UserTestData.getDefaultUser().getUsername()))).isTrue();
            softly.assertThat(allUsers).usingRecursiveComparison()
                    .ignoringFieldsMatchingRegexes(".*")
                    .isEqualTo(new AllUsersResponse());
        });
    }

    @Test
    @Description("13.003.01 Delete user")
    public void testDeleteUser() {
        User user = UserTestData.getDefaultUser().toBuilder().username("IVAN").build();
        userController.createUser(user);

        userController.deleteUser(user.getUsername());

        Response allUserResponse = userController.getAllUsers();
        List<AllUsersResponse.User> userList = allUserResponse.as(
                AllUsersResponse.class, ObjectMapperType.JACKSON_2).getUsers();

        Assert.assertEquals(allUserResponse.statusCode(), 200);
        Assert.assertTrue(allUserResponse.time() <= 2000);
        Assert.assertFalse(userList.stream().anyMatch(u ->
                u.getId().equals(UserTestData.getDefaultUser().getUsername())));
    }

}
