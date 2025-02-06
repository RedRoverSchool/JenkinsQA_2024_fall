package school.redrover.api.restassured;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.ProjectListResponse;
import school.redrover.model.ProjectResponse;
import school.redrover.runner.BaseApiTest;


import static io.restassured.RestAssured.given;
import static school.redrover.runner.TestApiUtils.requestSpec;
import static school.redrover.runner.TestApiUtils.responseSpec;

@Epic("API")
@Story("Multi-Configuration Project")
public class MultiConfigurationProjectApiTest extends BaseApiTest {

    private final String MULTI_CONFIGURATION_NAME = "MultiConfigurationProject";
    private final String MULTI_CONFIGURATION_MODE = "hudson.matrix.MatrixProject";

    @Test
    @Description("00.003.09 Create Multi-Configuration Project with valid name")
    public void testCreateMultiConfigWithValidName() {
        given()
                .spec(requestSpec())
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", MULTI_CONFIGURATION_NAME)
                .formParam("mode", MULTI_CONFIGURATION_MODE)
                .when()
                .post("createItem")
                .then()
                .spec(responseSpec(302, 500L));
        Response responseGetCreatedItem = given()
                .spec(requestSpec())
                .when()
                .get("/job/%s/api/json".formatted(MULTI_CONFIGURATION_NAME))
                .then()
                .spec(responseSpec(200,500L))
                .extract()
                .response();

        ProjectResponse getItemByNameResponse = responseGetCreatedItem.as(ProjectResponse.class);

        Allure.step(String.format("Expected result: fullName is '%s'", MULTI_CONFIGURATION_NAME));
        Assert.assertEquals(getItemByNameResponse.getFullName(), MULTI_CONFIGURATION_NAME);
        Allure.step("(ERR)Expected result: description is null");
        Assert.assertEquals(getItemByNameResponse.getDescription(),null);
        Allure.step(String.format("Expected result: _class is '%s'", MULTI_CONFIGURATION_MODE));
        Assert.assertEquals(getItemByNameResponse.get_class(),MULTI_CONFIGURATION_MODE);

        Response responseGetAllProjectList = given()
                .spec(requestSpec())
                .when()
                .get("/api/json")
                .then()
                .spec(responseSpec(200, 500L))
                .extract()
                .response();

        ProjectListResponse projectListresponse = responseGetAllProjectList.as(ProjectListResponse.class);

        boolean findItemByName = projectListresponse.getJobs().stream()
                .anyMatch(project -> project.getName().equals(MULTI_CONFIGURATION_NAME));

        Allure.step("Expected result: Project name found in the list");
        Assert.assertTrue(findItemByName, "Project name not found in the list");

    }
}
