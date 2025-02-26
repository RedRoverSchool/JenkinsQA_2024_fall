package school.redrover.api.restassured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.controllers.JobController;
import school.redrover.models.job.JobResponse;
import school.redrover.runner.BaseApiTest;
import school.redrover.testdata.JobTestData;
import school.redrover.testdata.TestDataProvider;

@Epic("API")
@Feature("Pipeline project")
public class PipelineApiTest extends BaseApiTest {
    private static final JobController jobController = new JobController();

    @Test(dataProvider = "projectNames", dataProviderClass = TestDataProvider.class)
    @Description("010 Create pipeline project with valid name")
    public void testCreateProjectWithValidName(String projectName) {
        Response resp = jobController.createJob(JobTestData.getDefaultPipeline(), projectName);

        Assert.assertEquals(resp.statusCode(), 200);
        Assert.assertTrue(resp.time() <= 2000);
    }

    @Test(dataProvider = "projectNames", dataProviderClass = TestDataProvider.class)
    @Description("011 Get pipeline project with valid name")
    public void testGetProjectByName(String projectName) {
        jobController.createJob(JobTestData.getDefaultPipeline(), projectName);

        Response resp = jobController.getJobByName(projectName);
        JobResponse jobResponse = resp.as(JobResponse.class);

        Assert.assertEquals(resp.statusCode(), 200);
        Assert.assertTrue(resp.time() <= 2000);
        Assert.assertEquals(resp.getHeader("Content-Type"), "application/json;charset=utf-8");

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(jobResponse.getName()).isEqualTo(projectName);
            softly.assertThat(jobResponse.getDescription()).isEqualTo("");
            softly.assertThat(jobResponse.getClassName()).isEqualTo("org.jenkinsci.plugins.workflow.job.WorkflowJob");
            softly.assertThat(jobResponse).usingRecursiveComparison()
                    .ignoringFieldsMatchingRegexes(".*").isEqualTo(new JobResponse());
        });
    }

    @Test(dataProvider = "renameProjectNames", dataProviderClass = TestDataProvider.class)
    @Description("013 Rename Pipeline project")
    public void testRenameProject(String oldName, String newName) {
        jobController.createJob(JobTestData.getDefaultPipeline(), oldName);

        Response resp = jobController.renameJob(oldName, newName);

        Assert.assertEquals(resp.statusCode(), 302);
        Assert.assertTrue(resp.time() <= 2000);
    }

    @Test(dataProvider = "projectNames", dataProviderClass = TestDataProvider.class)
    @Description("02.005.01 Delete pipeline folder")
    public void testDelete(String projectName) {
        jobController.createJob(JobTestData.getDefaultPipeline(), projectName);

        Response resp = jobController.deleteJob(projectName);

        Assert.assertEquals(resp.statusCode(), 204);

        Response getJob = jobController.getJobByName(projectName);

        Assert.assertEquals(getJob.statusCode(), 404);
        Assert.assertTrue(getJob.time() <= 2000);
    }

    @Test
    @Description("012 Create Pipeline project with empty name")
    public void testCreateProjectWithEmptyName() {
        Response resp = jobController.createJob(JobTestData.getDefaultPipeline(), "");

        Assert.assertEquals(resp.statusCode(), 400);
        Assert.assertTrue(resp.time() <= 2000);
        Assert.assertEquals(resp.getHeader("X-Error"), "No name is specified");
    }

    @Test(dataProvider = "providerUnsafeCharacters", dataProviderClass = TestDataProvider.class)
    @Description("014 Create Pipeline project with unsafe character")
    public void testCreateProjectWithUnsafeCharacter(String unsafeCharacter) {
        Response resp = jobController.createJob(JobTestData.getDefaultPipeline(), unsafeCharacter);

        Assert.assertEquals(resp.statusCode(), 400);
        Assert.assertTrue(resp.time() <= 2000);
        Assert.assertEquals(resp.getHeader("X-Error"), "%s  is an unsafe character".formatted(unsafeCharacter));
    }

}
