package school.redrover.api;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleAndPipelineResponse;
import school.redrover.runner.*;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static school.redrover.runner.TestApiUtils.*;
import static school.redrover.runner.TestApiUtils.requestSpec;
import static school.redrover.runner.TestUtils.loadPayload;

@Epic("API")
public class FreestyleProjectApiTest extends BaseApiTest {

    private static final String PROJECT_NAME = "FreestyleProject1";

    @Test
    @Feature("Freestyle project")
    @Description("001 Create freestyle project with valid name")
    public void testCreateFreestyleProjectAndGetByName() {

        stubEndpoints(PROJECT_NAME, WireMockStubs::stubCreateProject, WireMockStubs::stubGetProjectByName);

        given()
                .spec(requestSpec())
                .queryParam("name", PROJECT_NAME)
                .contentType(ContentType.XML)
                .body(loadPayload("create-empty-freestyle-project.xml"))
                .when()
                .post("createItem")
                .then()
                .spec(responseSpec(200));

        Response response = given()
                .spec(requestSpec())
                .basePath("job/%s".formatted(PROJECT_NAME))
                .when()
                .get("api/json")
                .then()
                .spec(responseSpec(200))
                .body(matchesJsonSchema(TestUtils.loadSchema("freestyle-project-schema.json")))
                .extract().response();

        FreestyleAndPipelineResponse freestyleAndPipelineResponse = response.as(FreestyleAndPipelineResponse.class);

        Assert.assertEquals(response.getHeader("Content-Type"), "application/json;charset=utf-8");
        Assert.assertTrue(response.getTime() <= 500);
        Assert.assertEquals(freestyleAndPipelineResponse.getName(), PROJECT_NAME);
        Assert.assertNull(freestyleAndPipelineResponse.getDescription());
        Assert.assertEquals(freestyleAndPipelineResponse.getClassField(), "hudson.model.FreeStyleProject");
    }

}
