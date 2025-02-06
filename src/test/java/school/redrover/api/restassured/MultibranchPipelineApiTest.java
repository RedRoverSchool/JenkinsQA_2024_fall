package school.redrover.api.restassured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.ProjectResponse;
import school.redrover.runner.BaseApiTest;
import school.redrover.runner.TestUtils;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static school.redrover.runner.TestApiUtils.requestSpec;
import static school.redrover.runner.TestApiUtils.responseSpec;

@Epic("API")
public class MultibranchPipelineApiTest extends BaseApiTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "MultibranchPipeline";
    private static final String CREATE_MODE = "org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject";

    @Test
    @Feature("Multibranch Pipeline")
    @Description("001 Create Multibranch pipeline with valid name")
    public void testCreateMultibranchPipeline() {
        given()
                .spec(requestSpec())
                .queryParam("name", MULTIBRANCH_PIPELINE_NAME)
                .queryParam("mode", CREATE_MODE)
                .when()
                .post("createItem")
                .then()
                .spec(responseSpec(302, 500L));

        Response response = given()
                .spec(requestSpec())
                .basePath("job/%s".formatted(MULTIBRANCH_PIPELINE_NAME))
                .when()
                .get("api/json")
                .then()
                .spec(responseSpec(200, 500L))
                .body(matchesJsonSchema(TestUtils.loadSchema("multibranch-pipeline-schema.json")))
                .extract().response();

        ProjectResponse receivedGetResponse = response.as(ProjectResponse.class);

        Assert.assertEquals(response.getHeader("Content-Type"), "application/json;charset=utf-8");
        Assert.assertEquals(receivedGetResponse.getName(), MULTIBRANCH_PIPELINE_NAME);
        Assert.assertEquals(receivedGetResponse.get_class(), CREATE_MODE);
    }
}
