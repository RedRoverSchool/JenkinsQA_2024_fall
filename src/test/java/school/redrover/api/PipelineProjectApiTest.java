package school.redrover.api;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleAndPipelineResponse;
import school.redrover.runner.BaseApiTest;
import school.redrover.runner.TestUtils;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static school.redrover.runner.TestApiUtils.authRequestSpec;
import static school.redrover.runner.TestApiUtils.baseResponseSpec;
import static school.redrover.runner.TestUtils.loadPayload;

@Epic("API")
@Feature("Pipeline project")
public class PipelineProjectApiTest extends BaseApiTest {

    @DataProvider(name = "projectNames")
    public Object[][] projectNames() {
        return new Object[][]{
                {"ValidProject"},
                {"A"},
                {generateMaxName()}
        };
    }

    private static String generateMaxName() {
        return "A".repeat(255);
    }

    @Test
    @Description("012 Create Pipeline project with empty name")
    public void testCreateProjectWithEmptyName() {

        Response response = given()
                .spec(authRequestSpec())
                .queryParam("name", "")
                .contentType(ContentType.XML)
                .body(loadPayload("create-empty-pipeline-project.xml"))
                .when()
                .post("createItem")
                .then()
                .spec(baseResponseSpec(400))
                .extract()
                .response();

        Assert.assertTrue(response.getTime() <= 500);
    }

    @Test(dataProvider = "projectNames")
    @Description("010 Create pipeline project with valid name")
    public void testCreateProjectWithValidName(String projectName) {

        Response response = given()
                .spec(authRequestSpec())
                .queryParam("name", projectName)
                .contentType(ContentType.XML)
                .body(loadPayload("create-empty-pipeline-project.xml"))
                .when()
                .post("createItem")
                .then()
                .spec(baseResponseSpec(200))
                .extract()
                .response();

        Assert.assertTrue(response.getTime() <= 500);
    }

    @Test(dependsOnMethods = "testCreateProjectWithValidName", dataProvider = "projectNames")
    @Description("011 Get pipeline project with valid name")
    public void testGetProjectByName(String projectName) {

        Response response = given()
                .spec(authRequestSpec())
                .basePath("job/%s".formatted(projectName))
                .when()
                .get("api/json")
                .then()
                .spec(baseResponseSpec(200))
                .body(matchesJsonSchema(TestUtils.loadSchema("pipeline-project-schema.json")))
                .extract().response();

        FreestyleAndPipelineResponse freestyleAndPipelineResponse = response.as(FreestyleAndPipelineResponse.class);

        Assert.assertEquals(response.getHeader("Content-Type"), "application/json;charset=utf-8");
        Assert.assertTrue(response.getTime() <= 500);
        Assert.assertEquals(freestyleAndPipelineResponse.getName(), projectName);
        Assert.assertEquals(freestyleAndPipelineResponse.getDescription(), "");
        Assert.assertEquals(freestyleAndPipelineResponse.getClassField(), "org.jenkinsci.plugins.workflow.job.WorkflowJob");
    }



}
