package school.redrover.api.restassured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.controllers.JobController;
import school.redrover.models.ProjectResponse;
import school.redrover.runner.BaseApiTest;
import school.redrover.testdata.JobTestData;

import static io.restassured.RestAssured.given;
import static school.redrover.runner.TestApiUtils.requestSpec;
import static school.redrover.runner.TestApiUtils.responseSpec;

@Epic("API")
public class MultibranchPipelineApiTest extends BaseApiTest {

    private static final JobController jobController = new JobController();
    private static final String MULTIBRANCH_PIPELINE_NAME = "MultibranchPipeline";
    private static final String MULTIBRANCH_PIPELINE_RENAMED = "MultibranchPipelineRenamed";

    @Test
    @Feature("Multibranch Pipeline")
    @Description("00.005.01 Create Multibranch pipeline with valid name")
    public void testCreateWithValidName() {
        Response response = jobController.createJob(JobTestData.getDefaultMultibranchPipeline(), MULTIBRANCH_PIPELINE_NAME);

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(response.time() <= 500);
    }

    @Test
    @Feature("Multibranch Pipeline")
    @Description("05.001.01 Rename Multibranch pipeline")
    public void testRenameWithValidName() {
        jobController.createJob(JobTestData.getDefaultMultibranchPipeline(), MULTIBRANCH_PIPELINE_NAME);

        Response response = jobController.renameJob(MULTIBRANCH_PIPELINE_NAME, MULTIBRANCH_PIPELINE_RENAMED);

        Assert.assertEquals(response.statusCode(), 302);
        Assert.assertTrue(response.time() <= 500);
    }

    @Test(dependsOnMethods = "testRenameWithValidName")
    @Feature("Multibranch Pipeline")
    @Description("05.005.01 Delete Multibranch pipeline")
    public void testDeleteMultibranchPipeline() {
        given()
                .spec(requestSpec())
                .when()
                .delete("job/%s/".formatted(MULTIBRANCH_PIPELINE_RENAMED))
                .then()
                .spec(responseSpec(204, 500L));

        given()
                .spec(requestSpec())
                .when()
                .get("job/%s/api/json".formatted(MULTIBRANCH_PIPELINE_RENAMED))
                .then()
                .spec(responseSpec(404, 500L));
    }
}
