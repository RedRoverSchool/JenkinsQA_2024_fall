package school.redrover.api.restassured;

import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.controllers.JobController;
import school.redrover.models.job.JobResponse;
import school.redrover.runner.BaseApiTest;
import school.redrover.testdata.JobTestData;
import school.redrover.testdata.ModeType;
import school.redrover.testdata.TestDataProvider;

import static io.restassured.RestAssured.given;
import static school.redrover.runner.TestApiUtils.*;

@Epic("API")
@Feature("Multi-Configuration Project")
public class MultiConfigurationProjectApiTest extends BaseApiTest {
    private static final JobController jobController = new JobController();

    private final String MULTI_CONFIG_NAME = "MultiConfigurationProject";
    private final String MULTI_CONFIG_MODE = "hudson.matrix.MatrixProject";
    private final String MULTI_CONFIG_NAME_XML = "MultiConfigurationProjectXML";
    private final String DESCRIPTION = "Create Project with Description!";

    @Test(dataProvider = "projectNames", dataProviderClass = TestDataProvider.class)
    @Story("US_00.003 Create Multi-Configuration Project")
    @Description("00.003.09 Create Multi-Configuration Project with valid name")
    public void testCreateMultiConfigWithValidName(String projectName) {
        Response response = given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("name", projectName)
                .formParam("mode", ModeType.MULTI_CONFIGURATION_PROJECT_MODE.getMode())
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec())
                .extract().response();

        JobResponse responseJobByName = (jobController.getJobByName(projectName)).as(JobResponse.class);

        Assert.assertEquals(response.statusCode(), 302);
        Assert.assertTrue(response.time() < 600L);

        SoftAssertions.assertSoftly(softly -> {
            Allure.step(String.format("Expected result: fullName is '%s'", projectName));
            softly.assertThat(responseJobByName.getFullName()).isEqualTo(projectName);
            Allure.step("(ERR)Expected result: description is null");
            softly.assertThat(responseJobByName.getDescription()).isEqualTo(null);
            Allure.step(String.format("Expected result: _class is '%s'", ModeType.MULTI_CONFIGURATION_PROJECT_MODE.getMode()));
            softly.assertThat(responseJobByName.getClassName()).isEqualTo(ModeType.MULTI_CONFIGURATION_PROJECT_MODE.getMode());
            Allure.step("Expected result: Project name found in the list");
            softly.assertThat(findItemInAllProjectList(projectName)).isTrue();
        });

        jobController.deleteJob(projectName);
    }

    @Test(dataProvider = "projectNames", dataProviderClass = TestDataProvider.class)
    @Story("US_00.003 Create Multi-Configuration Project")
    @Description("00.003.10 Create Multi-Configuration Project with valid name XML")
    public void testCreateProjectWithValidNameXML(String projectName) {

        Response response = given()
                .spec(requestSpec())
                .contentType(ContentType.XML)
                .queryParam("name", projectName)
                .body(toXML(JobTestData.getDefaultMultiConfiguration()))
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec())
                .extract().response();

        JobResponse responseJobByName = (jobController.getJobByName(projectName)).as(JobResponse.class);

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(response.time() < 600L);

        SoftAssertions.assertSoftly(softly -> {
            Allure.step(String.format("Expected result: fullName is '%s'", projectName));
            softly.assertThat(responseJobByName.getFullName()).isEqualTo(projectName);
            Allure.step("Expected result: description is empty");
            softly.assertThat(responseJobByName.getDescription()).isEqualTo("");
            Allure.step(String.format("Expected result: _class is '%s'", ModeType.MULTI_CONFIGURATION_PROJECT_MODE.getMode()));
            softly.assertThat(responseJobByName.getClassName()).isEqualTo(ModeType.MULTI_CONFIGURATION_PROJECT_MODE.getMode());

            Allure.step("Expected result: Project name found in the list");
            softly.assertThat(findItemInAllProjectList(projectName)).isTrue();
        });

        jobController.deleteJob(projectName);
    }

    @Test
    @Story("US_00.003 Create Multi-Configuration Project")
    @Description("00.003.12 Create Multi-Configuration Project with empty name")
    public void testCreateProjectWithEmptyName() {
        Response response = jobController.createJob(JobTestData.getDefaultMultiConfiguration(), "");

        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertTrue(response.time() < 600);

        Allure.step("Expected result: Header 'X-Error' has value 'No name is specified'");
        Assert.assertEquals(response.getHeaders().getValue("X-Error"),"No name is specified");
    }

    @Test
    @Story("US_00.003 Create Multi-Configuration Project")
    @Description("00.003.14 Create Multi-Configuration Project without mode")
    public void testCreateProjectWithoutMode() {
        Response response = jobController.createWithMode(MULTI_CONFIG_NAME, ModeType.NONE);

        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertTrue(response.time() < 600);

        Allure.step("Expected result: Header 'X-Error' has value 'No mode given'");
        Assert.assertEquals(response.getHeaders().getValue("X-Error"),"No mode given");
    }

    @Test(dataProvider = "providerUnsafeCharacters", dataProviderClass = TestDataProvider.class)
    @Story("US_00.003 Create Multi-Configuration Project")
    @Description("00.003.13 Create Multi-Configuration Project with with unsafe character")
    public void testCreateProjectWithUnsafeCharacter(String unsafeCharacter) {

        Response response = jobController.createJob(JobTestData.getDefaultMultiConfiguration(), unsafeCharacter);

        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertTrue(response.time() < 600L);

        Allure.step("Expected result: Header 'X-Error' has value '%s is an unsafe character'".formatted(unsafeCharacter));
        Assert.assertEquals(response.getHeaders().getValue("X-Error"), "%s  is an unsafe character".formatted(unsafeCharacter));
    }

    @Test
    @Story("US_00.003 Create Multi-Configuration Project")
    @Description("00.003.11 Create Multi-Configuration Project with valid name and description XML")
    public void testCreateProjectWithValidNameAndDescriptionXML() {

        Response response = jobController.createJob(JobTestData.getDefaultMultiConfiguration().toBuilder()
                .description(DESCRIPTION).build(), MULTI_CONFIG_NAME_XML);
        JobResponse responseJobByName = (jobController.getJobByName(MULTI_CONFIG_NAME_XML)).as(JobResponse.class);

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(response.time() < 600L);

        SoftAssertions.assertSoftly(softly -> {
            Allure.step(String.format("Expected result: fullName is '%s'", MULTI_CONFIG_NAME_XML));
            softly.assertThat(responseJobByName.getFullName()).isEqualTo(MULTI_CONFIG_NAME_XML);
            Allure.step("Expected result: description is %s".formatted(DESCRIPTION));
            softly.assertThat(responseJobByName.getDescription()).isEqualTo(DESCRIPTION);
            Allure.step(String.format("Expected result: _class is '%s'", ModeType.MULTI_CONFIGURATION_PROJECT_MODE.getMode()));
            softly.assertThat(responseJobByName.getClassName()).isEqualTo(ModeType.MULTI_CONFIGURATION_PROJECT_MODE.getMode());
            Allure.step("Expected result: Project name found in the list");
            softly.assertThat(findItemInAllProjectList(MULTI_CONFIG_NAME_XML)).isTrue();
        });

        jobController.deleteJob(MULTI_CONFIG_NAME_XML);
    }

    @Test
    @Story("US_00.003 Create Multi-Configuration Project")
    @Description("00.003.15 Create Multi-Configuration Project by copy from another project")
    public void testCreateProjectCopyFromAnotherProject() {
        jobController.createJob(JobTestData.getDefaultMultiConfiguration(), MULTI_CONFIG_NAME_XML);

        Response response = given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("name", MULTI_CONFIG_NAME)
                .formParam("mode", "copy")
                .formParam("from", MULTI_CONFIG_NAME_XML)
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec())
                .extract().response();

        JobResponse responseJobByName = (jobController.getJobByName(MULTI_CONFIG_NAME_XML)).as(JobResponse.class);

        Assert.assertEquals(response.statusCode(), 302);
        Assert.assertTrue(response.time() < 600L);

        SoftAssertions.assertSoftly(softly -> {
            Allure.step(String.format("Expected result: fullName is '%s'", MULTI_CONFIG_NAME));
            softly.assertThat(responseJobByName.getFullName()).isEqualTo(MULTI_CONFIG_NAME_XML);
            Allure.step("Expected result: description is empty");
            softly.assertThat(responseJobByName.getDescription()).isEqualTo("");
            Allure.step(String.format("Expected result: _class is '%s'", ModeType.MULTI_CONFIGURATION_PROJECT_MODE.getMode()));
            softly.assertThat(responseJobByName.getClassName()).isEqualTo(ModeType.MULTI_CONFIGURATION_PROJECT_MODE.getMode());
            Allure.step("Expected result: Project name %s found in the list on the Dashboard".formatted(MULTI_CONFIG_NAME_XML));
            softly.assertThat(findItemInAllProjectList(MULTI_CONFIG_NAME_XML)).isTrue();
            Allure.step("Expected result: Project name %s found in the list on the Dashboard".formatted(MULTI_CONFIG_NAME));
            softly.assertThat(findItemInAllProjectList(MULTI_CONFIG_NAME)).isTrue();
        });

        jobController.deleteJob(MULTI_CONFIG_NAME);
        jobController.deleteJob(MULTI_CONFIG_NAME_XML);
    }

    @Test
    @Story("03.001 Add/edit description")
    @Description("03.001.01 Add description to Project")
    public void testAddDescriptionToCreatedProject() {
        jobController.createJob(JobTestData.getDefaultMultiConfiguration(), MULTI_CONFIG_NAME_XML);

        Response response = given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("description", DESCRIPTION)
                .when()
                .post(getAddDescriptionToCreatedItemPath(MULTI_CONFIG_NAME_XML))
                .then()
                .spec(responseSpec())
                .extract().response();

        JobResponse responseJobByName = (jobController.getJobByName(MULTI_CONFIG_NAME_XML)).as(JobResponse.class);

        Assert.assertEquals(response.statusCode(), 302);
        Assert.assertTrue(response.time() < 600L);

        SoftAssertions.assertSoftly(softly -> {
            Allure.step(String.format("Expected result: fullName is '%s'", MULTI_CONFIG_NAME_XML));
            softly.assertThat(responseJobByName.getFullName()).isEqualTo(MULTI_CONFIG_NAME_XML);
            Allure.step("Expected result: description is %s".formatted(DESCRIPTION));
            softly.assertThat(responseJobByName.getDescription()).isEqualTo(DESCRIPTION);
            Allure.step(String.format("Expected result: _class is '%s'", ModeType.MULTI_CONFIGURATION_PROJECT_MODE.getMode()));
            softly.assertThat(responseJobByName.getClassName()).isEqualTo(ModeType.MULTI_CONFIGURATION_PROJECT_MODE.getMode());

        });

        jobController.deleteJob(MULTI_CONFIG_NAME_XML);
    }

    @Test
    @Story("03.002 Enable / Disable Project")
    @Description("03.002.01 Disable Project")
    public void testDisableCreatedProject() {
        jobController.createJob(JobTestData.getDefaultMultiConfiguration(), MULTI_CONFIG_NAME_XML);

        Response response = given()
                .spec(requestSpec())
                .when()
                .post(getDisableProjectPath(MULTI_CONFIG_NAME_XML))
                .then()
                .spec(responseSpec())
                .extract().response();

        boolean disableProject = getResponseGetAllProjectListAsObject().getJobs().stream().filter(project -> project.getName().equals(MULTI_CONFIG_NAME_XML))
                .anyMatch(project -> project.getColor().equals("disabled"));

        Assert.assertEquals(response.statusCode(), 302);
        Assert.assertTrue(response.time() < 600L);
        Allure.step("Expected result: Project '%s' disabled".formatted(MULTI_CONFIG_NAME_XML));
        Assert.assertTrue(disableProject);

        jobController.deleteJob(MULTI_CONFIG_NAME_XML);
    }

    @Test()
    @Story("03.002 Enable / Disable Project")
    @Description("03.002.02 Enable Project")
    public void testEnableProject() {
        jobController.createJob(JobTestData.getDefaultMultiConfiguration().toBuilder().disabled(true).build(), MULTI_CONFIG_NAME_XML);

        Response response = given()
                .spec(requestSpec())
                .when()
                .post(getEnableProjectPath(MULTI_CONFIG_NAME_XML))
                .then()
                .spec(responseSpec())
                .extract().response();

        boolean enableProject = getResponseGetAllProjectListAsObject().getJobs().stream().filter(project -> project.getName().equals(MULTI_CONFIG_NAME_XML))
                .anyMatch(project -> project.getColor().equals("notbuilt"));

        Assert.assertEquals(response.statusCode(), 302);
        Assert.assertTrue(response.time() < 600L);
        Allure.step("Expected result: Project '%s' enabled".formatted(MULTI_CONFIG_NAME_XML));
        Assert.assertTrue(enableProject, "Project %s NOT enabled");

        jobController.deleteJob(MULTI_CONFIG_NAME_XML);
    }

    @Test
    @Story("03.005 Rename Project")
    @Description("03.005.01 Rename Project")
    public void testRenameProject() {
        jobController.createJob(JobTestData.getDefaultMultiConfiguration(), MULTI_CONFIG_NAME_XML);

        Response response = given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("newName", MULTI_CONFIG_NAME)
                .when()
                .post(getRenameItemPath(MULTI_CONFIG_NAME_XML))
                .then()
                .spec(responseSpec())
                .extract().response();

        JobResponse responseJobByName = (jobController.getJobByName(MULTI_CONFIG_NAME)).as(JobResponse.class);

        Assert.assertEquals(response.statusCode(), 302);
        Assert.assertTrue(response.time() < 600L);

        SoftAssertions.assertSoftly(softly -> {
            Allure.step(String.format("Expected result: fullName is '%s'", MULTI_CONFIG_NAME));
            softly.assertThat(responseJobByName.getFullName()).isEqualTo(MULTI_CONFIG_NAME);
            Allure.step("Expected result: description is empty");
            softly.assertThat(responseJobByName.getDescription()).isEqualTo("");
            Allure.step(String.format("Expected result: _class is '%s'", ModeType.MULTI_CONFIGURATION_PROJECT_MODE.getMode()));
            softly.assertThat(responseJobByName.getClassName()).isEqualTo(ModeType.MULTI_CONFIGURATION_PROJECT_MODE.getMode());
            Allure.step("Expected result: Project name '%s' found in the list".formatted(MULTI_CONFIG_NAME));
            softly.assertThat(findItemInAllProjectList(MULTI_CONFIG_NAME)).isTrue();
            Allure.step("Expected result: Project name '%s' NOT found in the list".formatted(MULTI_CONFIG_NAME_XML));
            softly.assertThat(findItemInAllProjectList(MULTI_CONFIG_NAME_XML)).isFalse();

        });

        jobController.deleteJob(MULTI_CONFIG_NAME);
    }

    @Test
    @Story("03.005 Rename Project")
    @Description("03.005.02 Rename Project with the same name")
    public void testRenameWithSameName() {
        jobController.createJob(JobTestData.getDefaultMultiConfiguration(), MULTI_CONFIG_NAME);

        Response response = jobController.renameJob(MULTI_CONFIG_NAME, MULTI_CONFIG_NAME);

        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertTrue(response.time() < 600L);
        Assert.assertEquals(response.getHeaders().getValue("X-Error"),
                "The new name is the same as the current name.");
    }

    @Test()
    @Story("03.003 Delete Project")
    @Description("03.003.01 Delete Project")
    public void testDeleteProject() {
        jobController.createJob(JobTestData.getDefaultMultiConfiguration(), MULTI_CONFIG_NAME_XML);

        Allure.step("Send DELETE request -> Delete Project with name %s".formatted(MULTI_CONFIG_NAME_XML));
        Response response = given()
                .spec(requestSpec())
                .when()
                .delete(getDeleteItemPath(MULTI_CONFIG_NAME_XML))
                .then()
                .spec(responseSpec())
                .extract().response();

        Allure.step("Send GET request -> Get Project with name %s".formatted(MULTI_CONFIG_NAME_XML));
        Response responseJobByName = jobController.getJobByName(MULTI_CONFIG_NAME_XML);

        Assert.assertEquals(response.statusCode(), 204);
        Assert.assertTrue(response.time() < 600L);

        Assert.assertEquals(responseJobByName.statusCode(), 404);
        Assert.assertTrue(responseJobByName.time() < 600L);
    }
}
