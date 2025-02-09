package school.redrover.api.restassured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import school.redrover.models.ProjectResponse;
import school.redrover.runner.BaseApiTest;
import school.redrover.runner.TestUtils;
import school.redrover.runner.WireMockStubs;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static school.redrover.runner.TestApiUtils.requestSpec;
import static school.redrover.runner.TestApiUtils.responseSpec;
import static school.redrover.runner.TestUtils.loadPayload;

@Epic("API")
public class FreestyleProjectApiTest extends BaseApiTest {

    private static final String PROJECT_NAME = "FreestyleProject1";

    @BeforeClass
    private void stubs() {
        stubEndpoints(
                () -> WireMockStubs.stubCreateProject(PROJECT_NAME),
                () -> WireMockStubs.stubGetProjectByName(PROJECT_NAME));
    }

    @Test
    @Feature("Freestyle project")
    @Description("001 Create freestyle project with valid name")
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

}
