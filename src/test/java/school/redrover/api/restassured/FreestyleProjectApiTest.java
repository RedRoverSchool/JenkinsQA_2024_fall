package school.redrover.api.restassured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import school.redrover.models.ProjectResponse;
import school.redrover.models.job.JobResponse;
import school.redrover.runner.BaseApiTest;
import school.redrover.runner.WireMockStubs;
import school.redrover.controllers.JobController;
import school.redrover.testdata.JobTestData;

import static io.restassured.RestAssured.given;

import static school.redrover.runner.TestApiUtils.*;

@Epic("API")
@Feature("Freestyle project")
public class FreestyleProjectApiTest extends BaseApiTest {

    private static final String PROJECT_NAME = "FreestyleProject";
    private static final String NEW_PROJECT = "NewFreestyleProject";
    private static final String RENAMED_FREESTYLE_PROJECT = "RenamedFreestyle";
    private static final String PROJECT_DESCRIPTION = "It's my first project";
    private static final String CREATE_XML_FILE = "create-empty-freestyle-project.xml";
    private static final JobController jobController = new JobController();

    @BeforeClass
    private void stubs() {
        stubEndpoints(
                () -> WireMockStubs.stubCreateProject(PROJECT_NAME),
                () -> WireMockStubs.stubGetProjectByName(PROJECT_NAME));
    }

    @Test
    @Description("00.001.01 Create Freestyle Project  with valid name")
    public void testCreateWithValidName() {
        Response resp = jobController.createJob(JobTestData.getDefaultFreestyle(), PROJECT_NAME);

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(resp.statusCode()).isEqualTo(200);
                    softly.assertThat(resp.time()).isLessThan(500L);
                });
    }

    @Test(dependsOnMethods = "testCreateWithValidName")
    @Description("00.001.01 Get project with valid name")
    public void testGetProjectByName() {
        Response response = jobController.getJobByName(PROJECT_NAME);

        JobResponse jobResponse = response.as(JobResponse.class);

        Assert.assertEquals(response.getHeader("Content-Type"), "application/json;charset=utf-8");
        Assert.assertEquals(jobResponse.getName(), PROJECT_NAME);
        Assert.assertNull(jobResponse.getDescription());
        Assert.assertEquals(jobResponse.getClassName(), "hudson.model.FreeStyleProject");
    }

    @Test
    @Description("01.002.01 Rename FreestyleProject with correct name")
    public void testRename() {
        createNewProjectXML(NEW_PROJECT, CREATE_XML_FILE);

        given()
                .spec(requestSpec())
                .contentType("application/x-www-form-urlencoded")
                .queryParam("newName", RENAMED_FREESTYLE_PROJECT)
                .when()
                .post(getRenameItemPath(NEW_PROJECT))
                .then()
                .spec(responseSpec(302, 1000L));

        Response response = given()
                .spec(requestSpec())
                .when()
                .get(getItemByNamePath(RENAMED_FREESTYLE_PROJECT))
                .then()
                .spec(responseSpec(200, 500L))
                .extract().response();

        ProjectResponse freestyleResponse = response.as(ProjectResponse.class);

        Assert.assertEquals(response.getHeader("Content-Type"), "application/json;charset=utf-8");
        Assert.assertEquals(freestyleResponse.get_class(), "hudson.model.FreeStyleProject");
        Assert.assertEquals(freestyleResponse.getName(), RENAMED_FREESTYLE_PROJECT);
    }

    @Test
    @Description("01.001.02 Add description to an existing FreestyleProject")
    public void testAddDescription() {
        createNewProjectXML(NEW_PROJECT, CREATE_XML_FILE);

        given()
                .spec(requestSpec())
                .queryParam("description", PROJECT_DESCRIPTION)
                .when()
                .post(getAddDescriptionToCreatedItemPath(NEW_PROJECT))
                .then()
                .spec(responseSpec(302, 500L));

        Response response = given()
                .spec(requestSpec())
                .when()
                .get(getItemByNamePath(NEW_PROJECT))
                .then()
                .spec(responseSpec(200, 500L))
                .extract().response();

        ProjectResponse freestyleResponse = response.as(ProjectResponse.class);

        Assert.assertEquals(response.getHeader("Content-Type"), "application/json;charset=utf-8");
        Assert.assertEquals(freestyleResponse.getName(), NEW_PROJECT);
        Assert.assertEquals(freestyleResponse.getDescription(), PROJECT_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testAddDescription")
    @Description("01.001.02 Delete the project from the workspace")
    public void testDelete() {

        given()
                .spec(requestSpec())
                .when()
                .delete(getDeleteItemPath(NEW_PROJECT))
                .then()
                .spec(responseSpec(204, 500L));

        given()
                .spec(requestSpec())
                .when()
                .get(getItemByNamePath(NEW_PROJECT))
                .then()
                .spec(responseSpec(404, 500L));
    }

}