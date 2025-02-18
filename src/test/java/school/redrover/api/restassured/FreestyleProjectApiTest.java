package school.redrover.api.restassured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import school.redrover.model.ProjectResponse;
import school.redrover.runner.BaseApiTest;
import school.redrover.runner.TestUtils;
import school.redrover.runner.WireMockStubs;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static school.redrover.runner.TestApiUtils.requestSpec;
import static school.redrover.runner.TestApiUtils.responseSpec;
import static school.redrover.runner.TestUtils.loadPayload;

@Epic("API")
@Feature("Freestyle project")
public class FreestyleProjectApiTest extends BaseApiTest {

    private static final String PROJECT_NAME = "FreestyleProject1";
    private static final String RENAMED_FREESTYLE_PROJECT = "RenamedFreestyle";
    private static final String PROJECT_DESCRIPTION = "It's my first project";

    @BeforeClass
    private void stubs() {
        stubEndpoints(
                () -> WireMockStubs.stubCreateProject(PROJECT_NAME),
                () -> WireMockStubs.stubGetProjectByName(PROJECT_NAME));
    }

    @Test
    @Description("00.001.01 Create Freestyle Project  with valid name")
    public void testCreateFreestyleProjectAndGetByName() {
        given()
                .spec(requestSpec())
                .queryParam("name", PROJECT_NAME)
                .contentType(ContentType.XML)
                .body(loadPayload("create-empty-freestyle-project.xml"))
                .when()
                .post("createItem")
                .then()
                .spec(responseSpec(200, 1000L));

        Response response = given()
                .spec(requestSpec())
                .basePath("job/%s".formatted(PROJECT_NAME))
                .when()
                .get("api/json")
                .then()
                .spec(responseSpec(200, 500L))
                .body(matchesJsonSchema(TestUtils.loadSchema("freestyle-project-schema.json")))
                .extract().response();

        ProjectResponse freestyleAndPipelineResponse = response.as(ProjectResponse.class);

        Assert.assertEquals(response.getHeader("Content-Type"), "application/json;charset=utf-8");
        Assert.assertTrue(response.getTime() <= 500);
        Assert.assertEquals(freestyleAndPipelineResponse.getName(), PROJECT_NAME);
        Assert.assertNull(freestyleAndPipelineResponse.getDescription());
        Assert.assertEquals(freestyleAndPipelineResponse.get_class(), "hudson.model.FreeStyleProject");
    }
    @Test()
    @Description("01.002.01 Rename FreestyleProject with correct name")
    public void testRename() {
        testCreateFreestyleProjectAndGetByName();

        given()
                .spec(requestSpec())
                .contentType("application/x-www-form-urlencoded")
                .queryParam("newName", RENAMED_FREESTYLE_PROJECT)
                .when()
                .post("job/%s/doRename".formatted(PROJECT_NAME))
                .then()
                .spec(responseSpec(302, 1000L));

        Response response = given()
                .spec(requestSpec())
                .when()
                .get("job/%s/api/json".formatted(RENAMED_FREESTYLE_PROJECT))
                .then()
                .spec(responseSpec(200, 500L))
                .extract().response();

        ProjectResponse freestyleResponse = response.as(ProjectResponse.class);

        Assert.assertTrue(response.getTime() <= 500);
        Assert.assertEquals(response.getHeader("Content-Type"), "application/json;charset=utf-8");
        Assert.assertEquals(freestyleResponse.get_class(), "hudson.model.FreeStyleProject");
        Assert.assertEquals(freestyleResponse.getName(), RENAMED_FREESTYLE_PROJECT);
    }
    @Test()
    @Description("01.001.02 Add description to an existing FreestyleProject")
    public void testAddDescription() {
        testCreateFreestyleProjectAndGetByName();

        given()
                .spec(requestSpec())
                .queryParam("description", PROJECT_DESCRIPTION)
                .when()
                .post("job/%s/submitDescription".formatted(PROJECT_NAME))
                .then()
                .spec(responseSpec(302, 500L));

        Response response = given()
                .spec(requestSpec())
                .when()
                .get("job/%s/api/json".formatted(PROJECT_NAME))
                .then()
                .spec(responseSpec(200, 500L))
                .extract().response();

        ProjectResponse freestyleResponse = response.as(ProjectResponse.class);

        Assert.assertTrue(response.getTime() <= 500);
        Assert.assertEquals(response.getHeader("Content-Type"), "application/json;charset=utf-8");
        Assert.assertEquals(freestyleResponse.getName(), PROJECT_NAME);
        Assert.assertEquals(freestyleResponse.getDescription(), PROJECT_DESCRIPTION);
    }
    @Test()
    @Description("01.001.02 Delete the project from the workspace")
    public void testDelete() {
        testCreateFreestyleProjectAndGetByName();

        given()
                .spec(requestSpec())
                .when()
                .delete("job/%s/".formatted(PROJECT_NAME))
                .then()
                .spec(responseSpec(204, 500L));

        given()
                .spec(requestSpec())
                .when()
                .get("job/%s/api/json".formatted(PROJECT_NAME))
                .then()
                .spec(responseSpec(404, 500L))
                .extract().response();
    }

}