package school.redrover.api.restassured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import school.redrover.controllers.UserController;
import school.redrover.models.user.AllUsersResponse;
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
        Response resp = userController.createUser(UserTestData.getDefaultUser());

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(resp.statusCode()).isEqualTo(200);
                    softly.assertThat(resp.time()).isLessThan(2000);
                });
    }

    @Test(dependsOnMethods = "testCreateUser")
    @Description("13.001.04 Get all users")
    public void testGetAllUsers() {
        Response resp = userController.getAllUsers();
        AllUsersResponse allUsers = resp.as(AllUsersResponse.class, ObjectMapperType.JACKSON_2);
        List<AllUsersResponse.User> userList = allUsers.getUsers();

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(resp.statusCode()).isEqualTo(200);
                    softly.assertThat(resp.time()).isLessThan(2000);
                    softly.assertThat(userList.stream().anyMatch(user -> user.getId().equals(UserTestData.getDefaultUser().getUsername()))).isTrue();
                    softly.assertThat(allUsers).usingRecursiveComparison()
                            .ignoringFieldsMatchingRegexes(".*")
                            .isEqualTo(new AllUsersResponse());
                });
    }

    @Test(dependsOnMethods = "testGetAllUsers")
    @Description("13.003.01 Delete user")
    public void testDeleteUser() {
        Response resp = userController.deleteUser(UserTestData.getDefaultUser().getUsername());

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(resp.statusCode()).isEqualTo(302);
                    softly.assertThat(resp.time()).isLessThan(2000);

                });

        Response respUsers = userController.getAllUsers();
        AllUsersResponse allUsers = respUsers.as(AllUsersResponse.class, ObjectMapperType.JACKSON_2);
        List<AllUsersResponse.User> userList = allUsers.getUsers();

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(respUsers.statusCode()).isEqualTo(200);
                    softly.assertThat(respUsers.time()).isLessThan(2000);
                    softly.assertThat(userList.stream().anyMatch(user -> user.getId().equals(UserTestData.getDefaultUser().getUsername()))).isFalse();
                });
    }

}
