package school.redrover.api.restassured;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.controllers.JobController;
import school.redrover.models.job.JobResponse;
import school.redrover.models.job.OrganizationFolder;
import school.redrover.runner.BaseApiTest;
import school.redrover.testdata.JobTestData;
import school.redrover.testdata.ModeType;
import school.redrover.testdata.TestDataProvider;
import school.redrover.runner.TestUtils;

import static io.restassured.RestAssured.given;
import static school.redrover.runner.TestApiUtils.requestSpec;
import static school.redrover.runner.TestApiUtils.responseSpec;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@Epic("API")
@Feature("Organization folder")
public class OrganizationFolderApiTest extends BaseApiTest {

    private static final String ORGANIZATION_FOLDER_NAME_BY_XML_CREATED = "OrganizationFolderName";
    private static final String ORGANIZATION_FOLDER_NAME_BY_MODE_CREATED = "OrganisationFolderViaMode";
    private static final String INVALID_ORGANIZATION_FOLDER = "InvalidOrganizationFolder";
    private static final String RENAMED_ORGANIZATION_FOLDER_NAME = "RenamedOrganizationFolderName";
    private static final String DISPLAY_NAME_ORG_FOLDER = "DisplayNameOrgFolder";
    private static final String DESCRIPTION = "ProjectDescription";
    private static final JobController jobController = new JobController();

    @Test
    @Description("00.007.01 Create Organization folder with valid name")
    public void testCreateWithXML() {
        Response response = jobController.createJob(
                JobTestData.getDefaultOrganizationFolder(), ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(200);
            softly.assertThat(response.time()).isLessThan(500L);
        });

        jobController.deleteJob(ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);
    }

    @Test
    @Description("00.007.02 Get Organization folder with valid name")
    public void testGetJob() {
        jobController.createJob(JobTestData.getDefaultOrganizationFolder(), ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);

        Response response = jobController.getJobByName(ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);
        JobResponse jobResponse = response.as(JobResponse.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(200);
            softly.assertThat(response.time()).isLessThan(500L);
            softly.assertThat(response.getHeader("Content-Type")).isEqualTo("application/json;charset=utf-8");
            softly.assertThat(jobResponse.getName()).isEqualTo(ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);
            softly.assertThat(jobResponse.getClassName()).isEqualTo(ModeType.ORGANIZATION_FOLDER_MODE.getMode());
            softly.assertThat(jobResponse).usingRecursiveComparison()
                    .ignoringFieldsMatchingRegexes(".*").isEqualTo(new JobResponse());
        });

        jobController.deleteJob(ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);
    }

    @Test
    @Description("00.007.10 Create Organization folder with configurations")
    public void testCreateWithConfigurations() {
        OrganizationFolder organizationFolder = JobTestData.getDefaultOrganizationFolder().toBuilder()
                .disabled(true)
                .description(DESCRIPTION)
                .build();

        Response response = jobController.createJob(organizationFolder, ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(200);
            softly.assertThat(response.time()).isLessThan(500L);
        });

        jobController.deleteJob(ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);
    }

    @Test
    @Description("00.007.05 Get error message when create Organization Folder with the same name")
    public void testCreateSameName() {
        jobController.createJob(JobTestData.getDefaultOrganizationFolder(), ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);

        Response response = jobController.createWithMode(
                ORGANIZATION_FOLDER_NAME_BY_XML_CREATED, ModeType.ORGANIZATION_FOLDER_MODE);

        Document document = Jsoup.parse(response.asString());

        Assert.assertEquals(document.select("h1").text(), "Error");
        Assert.assertEquals(document.select("p").text(),
                String.format("A job already exists with the name ‘%s’", ORGANIZATION_FOLDER_NAME_BY_XML_CREATED));

        jobController.deleteJob(ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);
    }

    @Test
    @Description("00.007.09 Create Organization folder with project mode")
    public void testCreateWithMode() {
        Response response = jobController.createWithMode(
                ORGANIZATION_FOLDER_NAME_BY_MODE_CREATED, ModeType.ORGANIZATION_FOLDER_MODE);

        Assert.assertEquals(response.statusCode(), 302);
        Assert.assertTrue(response.getTime() <= 500L);

        Response getJobResponse = jobController.getJobByName(ORGANIZATION_FOLDER_NAME_BY_MODE_CREATED);
        JobResponse jobResponse = getJobResponse.as(JobResponse.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(getJobResponse.statusCode()).isEqualTo(200);
            softly.assertThat(getJobResponse.time()).isLessThan(500L);
            softly.assertThat(getJobResponse.getHeader("Content-Type")).isEqualTo("application/json;charset=utf-8");
            softly.assertThat(jobResponse.getName()).isEqualTo(ORGANIZATION_FOLDER_NAME_BY_MODE_CREATED);
            softly.assertThat(jobResponse.getClassName()).isEqualTo(ModeType.ORGANIZATION_FOLDER_MODE.getMode());
            softly.assertThat(jobResponse).usingRecursiveComparison()
                    .ignoringFieldsMatchingRegexes(".*").isEqualTo(new JobResponse());
        });
    }

    @Test
    @Description("00.007.06 Create Organization folder without name")
    public void testCreateWithoutName() {
        final String errorMessage = "No name is specified";

        Response response = jobController.createWithMode("", ModeType.ORGANIZATION_FOLDER_MODE);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(400);
            softly.assertThat(response.time()).isLessThan(500);
        });

        Assert.assertEquals(Jsoup.parse(response.asString()).select("p").text(), errorMessage);
        Assert.assertEquals(response.headers().get("X-Error").toString(), "X-Error=" + errorMessage);
    }

    @Test
    @Description("00.007.07 Create Organization folder without mode")
    public void testCreateWithoutMode() {
        final String errorMessage = "No mode given";

        Response response = jobController.createWithMode(INVALID_ORGANIZATION_FOLDER, ModeType.NONE);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(400);
            softly.assertThat(response.time()).isLessThan(500L);
        });

        Assert.assertEquals(Jsoup.parse(response.asString()).select("p").text(), errorMessage);
        Assert.assertEquals(response.headers().get("X-Error").toString(), "X-Error=" + errorMessage);
    }

    @Test(dataProvider = "providerUnsafeCharacters", dataProviderClass = TestDataProvider.class)
    @Description("00.007.08 Create Organization folder with unsafe Characters")
    public void testCreateWithUnsafeCharacters(String unsafeCharacter) {
        final String errorMessage = "‘%s’ is an unsafe character".formatted(unsafeCharacter);

        Response response = jobController.createWithMode(unsafeCharacter, ModeType.ORGANIZATION_FOLDER_MODE);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(400);
            softly.assertThat(response.time()).isLessThan(500L);
            softly.assertThat(Jsoup.parse(response.asString()).select("p").text()).isEqualTo(errorMessage);
            softly.assertThat(response.getHeader("X-Error")).isEqualTo(
                    errorMessage.replaceAll("‘", "").replaceAll("’", " "));
        });
    }

    @Test
    @Description("06.001.04 Change configurations for Organization folder")
    public void testChangeProjectConfigurations() throws JsonProcessingException {
        jobController.createJob(JobTestData.getDefaultOrganizationFolder(), ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);

        String projectJson = TestUtils.loadPayload("organization-folder-configuration.json");

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(projectJson, new TypeReference<>() {
        });

        jsonMap.put("enable", false);
        jsonMap.put("displayNameOrNull", DISPLAY_NAME_ORG_FOLDER);
        jsonMap.put("description", DESCRIPTION);

        String modifiedJson = objectMapper.writeValueAsString(jsonMap);

        given()
                .spec(requestSpec())
                .contentType("application/x-www-form-urlencoded")
                .formParam("json", modifiedJson)
                .when()
                .post("job/%s/configSubmit".formatted(ORGANIZATION_FOLDER_NAME_BY_XML_CREATED))
                .then()
                .spec((responseSpec(302, 500L)))
                .extract().response();

        Response getJobResponse = jobController.getJobByName(ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);
        JobResponse jobResponse = getJobResponse.as(JobResponse.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(getJobResponse.statusCode()).isEqualTo(200);
            softly.assertThat(getJobResponse.time()).isLessThan(500L);
            softly.assertThat(getJobResponse.getHeader("Content-Type")).isEqualTo("application/json;charset=utf-8");
            softly.assertThat(jobResponse.getClassName()).isEqualTo(ModeType.ORGANIZATION_FOLDER_MODE.getMode());
            softly.assertThat(jobResponse.getName()).isEqualTo(ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);
            softly.assertThat(jobResponse.getDescription()).isEqualTo(DESCRIPTION);
            softly.assertThat(jobResponse.getDisplayName()).isEqualTo(DISPLAY_NAME_ORG_FOLDER);
        });

        jobController.deleteJob(ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);
    }

    @Test
    @Description("06.005.01 Rename Organization folder with valid name")
    public void testRename() {
        jobController.createJob(JobTestData.getDefaultOrganizationFolder(), ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);

        Response response = jobController.renameJob(ORGANIZATION_FOLDER_NAME_BY_XML_CREATED, RENAMED_ORGANIZATION_FOLDER_NAME);

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(302);
                    softly.assertThat(response.time()).isLessThan(500L);
                });

        Response responseByName = jobController.getJobByName(RENAMED_ORGANIZATION_FOLDER_NAME);
        JobResponse jobResponse = responseByName.as(JobResponse.class);

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(responseByName.statusCode()).isEqualTo(200);
                    softly.assertThat(responseByName.time()).isLessThan(500L);
                    softly.assertThat(responseByName.getHeader("Content-Type")).isEqualTo("application/json;charset=utf-8");
                    softly.assertThat(jobResponse.getName()).isEqualTo(RENAMED_ORGANIZATION_FOLDER_NAME);
                    softly.assertThat(jobResponse.getClassName()).isEqualTo(ModeType.ORGANIZATION_FOLDER_MODE.getMode());
                });
    }

    @Test
    @Description("06.006.01 Create List view for Organization Folder")
    public void testAddJobListView() {
        final String viewName = "ViewName";

        jobController.createJob(JobTestData.getDefaultOrganizationFolder(), ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);

        Response response = JobController.addJobListView(viewName, ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(200);
                    softly.assertThat(response.time()).isLessThan(500L);
                }
        );
    }

    @Test
    @Description("06.005.02 Delete Organization folder")
    public void testDelete() {
        jobController.createJob(JobTestData.getDefaultOrganizationFolder(), ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);

        Response response = jobController.deleteJob(ORGANIZATION_FOLDER_NAME_BY_XML_CREATED);

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(204);
                    softly.assertThat(response.time()).isLessThan(500L);
                });

        Response responseGetJob = jobController.getJobByName(RENAMED_ORGANIZATION_FOLDER_NAME);

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(responseGetJob.statusCode()).isEqualTo(404);
                    softly.assertThat(responseGetJob.time()).isLessThan(500L);
                });
    }
}