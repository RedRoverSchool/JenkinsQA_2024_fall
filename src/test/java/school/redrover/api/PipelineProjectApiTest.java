package school.redrover.api;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.TestDataProvider;
import school.redrover.model.ProjectResponse;
import school.redrover.runner.BaseApiTest;
import school.redrover.runner.TestUtils;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static school.redrover.runner.TestApiUtils.requestSpec;
import static school.redrover.runner.TestApiUtils.responseSpec;
import static school.redrover.runner.TestUtils.loadPayload;

@Epic("API")
@Feature("Pipeline project")
public class PipelineProjectApiTest extends BaseApiTest {

    @Test
    @Description("012 Create Pipeline project with empty name")
    public void testCreateProjectWithEmptyName() {
        Response response = given()
                .spec(requestSpec())
                .queryParam("name", "")
                .contentType(ContentType.XML)
                .body(loadPayload("create-empty-pipeline-project.xml"))
                .when()
                .post("createItem")
                .then()
                .spec(responseSpec(400, 500L))
                .extract()
                .response();

        Assert.assertEquals(response.headers().get("X-Error").toString(), "X-Error=No name is specified");
    }

    @Test(dataProvider = "projectNames", dataProviderClass = TestDataProvider.class)
    @Description("010 Create pipeline project with valid name")
    public void testCreateProjectWithValidName(String projectName) {
        given()
                .spec(requestSpec())
                .queryParam("name", projectName)
                .contentType(ContentType.XML)
                .body(loadPayload("create-empty-pipeline-project.xml"))
                .when()
                .post("createItem")
                .then()
                .spec(responseSpec(200, 500L));
    }

    @Test(dependsOnMethods = "testCreateProjectWithValidName",
            dataProvider = "projectNames",
            dataProviderClass = TestDataProvider.class)
    @Description("011 Get pipeline project with valid name")
    public void testGetProjectByName(String projectName) {
        Response response = given()
                .spec(requestSpec())
                .basePath("job/%s".formatted(projectName))
                .when()
                .get("api/json")
                .then()
                .spec(responseSpec(200, 500L))
                .body(matchesJsonSchema(TestUtils.loadSchema("pipeline-project-schema.json")))
                .extract().response();

        ProjectResponse freestyleAndPipelineResponse = response.as(ProjectResponse.class);

        Assert.assertEquals(response.getHeader("Content-Type"), "application/json;charset=utf-8");
        Assert.assertEquals(freestyleAndPipelineResponse.getName(), projectName);
        Assert.assertEquals(freestyleAndPipelineResponse.getDescription(), "");
        Assert.assertEquals(freestyleAndPipelineResponse.get_class(), "org.jenkinsci.plugins.workflow.job.WorkflowJob");
    }

    @Test(dataProvider = "providerUnsafeCharacters", dataProviderClass = TestDataProvider.class)
    @Description("014 Create Pipeline project with unsafe character")
    public void testCreateProjectWithUnsafeCharacter(String unsafeCharacter) {
        Response response = given()
                .spec(requestSpec())
                .queryParam("name", unsafeCharacter)
                .contentType(ContentType.XML)
                .body(loadPayload("create-empty-pipeline-project.xml"))
                .when()
                .post("createItem")
                .then()
                .spec(responseSpec(400, 500L))
                .extract()
                .response();

        Assert.assertEquals(response.headers().get("X-Error").toString(), "X-Error=%s  is an unsafe character".formatted(unsafeCharacter));
    }

    @Test(dependsOnMethods = "testGetProjectByName",
            dataProvider = "renameProjectNames",
            dataProviderClass = TestDataProvider.class)
    @Description("013 Rename Pipeline project")
    public void testRenameProject(String oldName, String newName) {
        given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .queryParam("newName", newName)
                .when()
                .post("job/%s/doRename".formatted(oldName))
                .then()
                .spec(responseSpec(302, 500L));
    }

}
