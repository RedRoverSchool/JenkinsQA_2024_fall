package school.redrover.api.restassured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import school.redrover.controllers.JobController;
import school.redrover.models.job.JobResponse;
import school.redrover.runner.BaseApiTest;
import school.redrover.testdata.JobType;
import school.redrover.testdata.TestDataProvider;

@Epic("API")
@Feature("Pipeline project")
public class PipelineApiTest extends BaseApiTest {
    private static final JobController jobController = new JobController();

    @Test(dataProvider = "projectNames", dataProviderClass = TestDataProvider.class)
    @Description("010 Create pipeline project with valid name")
    public void testCreateProjectWithValidName(String projectName) {
        Response resp = jobController.createJobXML(JobType.PIPELINE, projectName);

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(resp.statusCode()).isEqualTo(200);
                    softly.assertThat(resp.time()).isLessThan(2000);
                });
    }

    @Test(dependsOnMethods = "testCreateProjectWithValidName",
            dataProvider = "projectNames",
            dataProviderClass = TestDataProvider.class)
    @Description("011 Get pipeline project with valid name")
    public void testGetProjectByName(String projectName) {
        Response resp = jobController.getJobByName(projectName);

        JobResponse jobResponse = resp.as(JobResponse.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(resp.statusCode()).isEqualTo(200);
            softly.assertThat(resp.time()).isLessThan(500);
            softly.assertThat(resp.getHeader("Content-Type")).isEqualTo("application/json;charset=utf-8");
            softly.assertThat(jobResponse.getName()).isEqualTo(projectName);
            softly.assertThat(jobResponse.getDescription()).isEqualTo("");
            softly.assertThat(jobResponse.getClassName()).isEqualTo("org.jenkinsci.plugins.workflow.job.WorkflowJob");
            softly.assertThat(jobResponse).usingRecursiveComparison()
                    .ignoringFieldsMatchingRegexes(".*").isEqualTo(new JobResponse());
        });

    }

    @Test(dependsOnMethods = "testGetProjectByName",
            dataProvider = "renameProjectNames",
            dataProviderClass = TestDataProvider.class)
    @Description("013 Rename Pipeline project")
    public void testRenameProject(String oldName, String newName) {
        Response resp = jobController.renameJob(oldName, newName);

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(resp.statusCode()).isEqualTo(302);
                    softly.assertThat(resp.time()).isLessThan(500);
                });
    }

    @Test(dependsOnMethods = "testRenameProject",
            dataProvider = "renameProjectNames",
            dataProviderClass = TestDataProvider.class)
    @Description("02.005.01 Delete pipeline folder")
    public void testDelete(String unused, String projectName) {
        Response resp = jobController.deleteJob(unused, projectName);

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(resp.statusCode()).isEqualTo(204);
                    softly.assertThat(resp.time()).isLessThan(2000);
                });

        Response getJob = jobController.getJobByName(projectName);

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(getJob.statusCode()).isEqualTo(404);
                    softly.assertThat(getJob.time()).isLessThan(2000);
                });
    }

    @Test
    @Description("012 Create Pipeline project with empty name")
    public void testCreateProjectWithEmptyName() {
        Response resp = jobController.createJobXML(JobType.PIPELINE, "");

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(resp.statusCode()).isEqualTo(400);
                    softly.assertThat(resp.time()).isLessThan(2000);
                    softly.assertThat(resp.getHeader("X-Error")).isEqualTo("No name is specified");
                });
    }


    @Test(dataProvider = "providerUnsafeCharacters", dataProviderClass = TestDataProvider.class)
    @Description("014 Create Pipeline project with unsafe character")
    public void testCreateProjectWithUnsafeCharacter(String unsafeCharacter) {
        Response resp = jobController.createJobXML(JobType.PIPELINE, unsafeCharacter);

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(resp.statusCode()).isEqualTo(400);
                    softly.assertThat(resp.time()).isLessThan(2000);
                    softly.assertThat(resp.getHeader("X-Error")).isEqualTo("%s  is an unsafe character".formatted(unsafeCharacter));
                });
    }

}
