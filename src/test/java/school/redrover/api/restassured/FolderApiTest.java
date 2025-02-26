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
@Story("Folder")
public class FolderApiTest extends BaseApiTest {
    private static final JobController jobController = new JobController();

    private static final String FOLDER_NAME = "FolderApiTest";
    private static final String FOLDER_CREATE_MODE = "com.cloudbees.hudson.plugins.folder.Folder";
    private static final String FOLDER_NEW_NAME = "FolderApiNewName";
    private static final String FOLDER_DESCRIPTION = "Add description to folder!";
    private static final String FOLDER_NAME_BY_XML_CREATE = "FolderCreateByXML";
    private static final String FOLDER_NAME_COPY_FROM = "FolderCopyFrom";
    private static final String CREATE_EMPTY_FOLDER_XML_FILE = "create-empty-folder.xml";
    private static final String FOLDER_GET_BY_NAME_SCHEMA_JSON = "folder-schema.json";

    @Test(dataProvider = "projectNames", dataProviderClass = TestDataProvider.class)
    @Description("00.006.02 Create Folder with valid name")
    public void testCreateFolderWithValidName(String projectName) {

        Response response = given()
                .spec(requestSpec())
                .formParam("name", projectName)
                .formParam( "mode", ModeType.FOLDER_MODE.getMode())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
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
            Allure.step(String.format("Expected result: _class is '%s'", ModeType.FOLDER_MODE.getMode()));
            softly.assertThat(responseJobByName.getClassName()).isEqualTo(ModeType.FOLDER_MODE.getMode());
            Allure.step("Expected result: Folder name found in the list");
            softly.assertThat(findItemInAllProjectList(projectName)).isTrue();
        });

        jobController.deleteJob("",projectName);
    }

    @Test(dataProvider = "projectNames", dataProviderClass = TestDataProvider.class)
    @Description("00.006.03 Create Folder with valid name (XML)")
    public void testCreateFolderWithValidNameXml(String projectName) {
        Response response = given()
                .spec(requestSpec())
                .contentType(ContentType.XML)
                .queryParam("name", projectName)
                .body(toXML(JobTestData.getDefaultFolder()))
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec())
                .extract().response();

        JobResponse responseJobByName = (jobController.getJobByName(projectName)).as(JobResponse.class);

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(response.time() < 600L);

        SoftAssertions.assertSoftly(
                softly -> {
                    Allure.step(String.format("Expected result: fullName is '%s'", projectName));
                    softly.assertThat(responseJobByName.getFullName()).isEqualTo(projectName);
                    Allure.step("(Expected result: description is empty");
                    softly.assertThat(responseJobByName.getDescription()).isEqualTo("");
                    Allure.step(String.format("Expected result: _class is '%s'", FOLDER_CREATE_MODE));
                    softly.assertThat(responseJobByName.getClassName()).isEqualTo(FOLDER_CREATE_MODE);
                });

        jobController.deleteJob(projectName);
    }

    @Test(dataProvider = "providerUnsafeCharacters", dataProviderClass = TestDataProvider.class)
    @Description("00.006.17 Create Folder with unsafe character")
    public void testCreateFolderWithUnsafeCharacter(String unsafeCharacter) {
        Response response = jobController.createJob(JobTestData.getDefaultFolder(), unsafeCharacter);

        Allure.step("Expected result: Status code is 400");
        Assert.assertEquals(response.statusCode(), 400);

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(400);
                    softly.assertThat(response.time()).isLessThan(500);
                    Allure.step("Expected result: Header 'X-Error' has value '%s is an unsafe character'".formatted(unsafeCharacter));
                    softly.assertThat(response.getHeaders().getValue("X-Error")).isEqualTo("%s  is an unsafe character".formatted(unsafeCharacter));
                });
    }

    @Test
    @Description("")
    public void testCreateFolderWithSameName() {
        jobController.createJob(JobTestData.getDefaultFolder(), ModeType.FOLDER_MODE.getMode());

        Response response = jobController.createJob(JobTestData.getDefaultFolder(), ModeType.FOLDER_MODE.getMode());

        SoftAssertions.assertSoftly(
                softly -> {
                    Allure.step("Expected result: Status code is 400");
                    softly.assertThat(response.statusCode()).isEqualTo(400);
                    softly.assertThat(response.time()).isLessThan(500L);
                    Allure.step("Expected result: Header 'X-Error' has value 'A job already exists with the name  %s'".formatted(FOLDER_NAME));
                    softly.assertThat(response.getHeaders().getValue("X-Error"))
                            .isEqualTo("A job already exists with the name  %s".formatted(FOLDER_NAME));
                    softly.assertThat(response.getHeaders().getValue("Content-Type"))
                            .isEqualTo("text/html;charset=utf-8");
                });
    }

    @Test
    @Description("015 Create Folder with empty name")
    public void testCreateFolderWithEmptyName() {
        Response response = jobController.createJob(JobTestData.getDefaultFolder(), "");

        SoftAssertions.assertSoftly(
                softly -> {
                    Allure.step("Expected result: Status code is 400");
                    softly.assertThat(response.statusCode()).isEqualTo(400);
                    softly.assertThat(response.time()).isLessThan(500L);
                    Allure.step("Expected result: Header 'X-Error' has value 'No name is specified'");
                    softly.assertThat(response.getHeaders().getValue("X-Error")).isEqualTo("No name is specified");
                });
    }

    @Test
    @Description("00.006.16 Create Folder by copy from another folder")
    public void testCreateFolderByCopyFromAnotherFolder() {
        jobController.createJob(JobTestData.getDefaultFolder(), FOLDER_NAME_BY_XML_CREATE);

        Response response = given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("name", FOLDER_NAME_COPY_FROM)
                .formParam("mode", "copy")
                .formParam("from", FOLDER_NAME)
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec())
                .extract().response();

        Assert.assertEquals(response.statusCode(), 302);
        Assert.assertTrue(response.time() < 600L);

        JobResponse responseJobByName = (jobController.getJobByName(FOLDER_NAME_COPY_FROM)).as(JobResponse.class);

        SoftAssertions.assertSoftly(softly -> {
            Allure.step(String.format("Expected result: fullName is '%s'", FOLDER_NAME_BY_XML_CREATE));
            softly.assertThat(responseJobByName.getFullName()).isEqualTo(FOLDER_NAME_COPY_FROM);
            Allure.step("Expected result: description is empty");
            softly.assertThat(responseJobByName.getDescription()).isEqualTo("");
            Allure.step(String.format("Expected result: _class is '%s'", FOLDER_CREATE_MODE));
            softly.assertThat(responseJobByName.getClassName()).isEqualTo(FOLDER_CREATE_MODE);
            Allure.step(String.format("Expected result: Folder name '%s' found in all project list", FOLDER_NAME_COPY_FROM));
            softly.assertThat(findItemInAllProjectList(FOLDER_NAME_COPY_FROM)).isTrue();

        });

        jobController.deleteJob("", FOLDER_NAME_COPY_FROM);
    }

    @Test()
    @Description("04.001.08 Rename Folder")
    public void testRenameFolder() {
        jobController.createJob(JobTestData.getDefaultFolder(), FOLDER_NAME_BY_XML_CREATE);

        Response response = given().spec(requestSpec())
                .formParam("newName", FOLDER_NEW_NAME)
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .when()
                .post(getRenameItemPath(FOLDER_NAME_BY_XML_CREATE))
                .then()
                .spec(responseSpec())
                .extract().response();

        JobResponse responseJobByName = (jobController.getJobByName(FOLDER_NEW_NAME)).as(JobResponse.class);

        Assert.assertEquals(response.statusCode(), 302);
        Assert.assertTrue(response.time() < 600L);

        SoftAssertions.assertSoftly(softly -> {
            Allure.step(String.format("Expected result: fullName is '%s'", FOLDER_NEW_NAME));
            softly.assertThat(responseJobByName.getFullName()).isEqualTo(FOLDER_NEW_NAME);
            Allure.step("(Expected result: description is empty");
            softly.assertThat(responseJobByName.getDescription()).isEqualTo("");
            Allure.step(String.format("Expected result: _class is '%s'", ModeType.FOLDER_MODE.getMode()));
            softly.assertThat(responseJobByName.getClassName()).isEqualTo(ModeType.FOLDER_MODE.getMode());
            Allure.step("Expected result: Folder name found in the list");
            softly.assertThat(findItemInAllProjectList(FOLDER_NEW_NAME)).isTrue();
            Allure.step("Expected result: Folder old name NOT found in the list");
            softly.assertThat(findItemInAllProjectList(FOLDER_NAME_BY_XML_CREATE)).isFalse();
        });

        jobController.deleteJob("", FOLDER_NEW_NAME);
    }

    @Test()
    @Description("04.001.09 Rename Folder with the same name")
    public void testRenameFolderSameName() {
        jobController.createJob(JobTestData.getDefaultFolder(), FOLDER_NAME_BY_XML_CREATE);

        Response response = given().spec(requestSpec())
                .formParam("newName", FOLDER_NAME_BY_XML_CREATE)
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .when()
                .post(getRenameItemPath(FOLDER_NAME_BY_XML_CREATE))
                .then()
                .spec(responseSpec())
                .extract().response();

        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertTrue(response.time() < 600L);
        Assert.assertEquals(response.getHeaders().getValue("X-Error"), "The new name is the same as the current name.");

        jobController.deleteJob("", FOLDER_NAME_BY_XML_CREATE);
    }

    @Test()
    @Description("007 Add Description to Folder")
    public void testAddDescriptionToFolder() {
        jobController.createJob(JobTestData.getDefaultFolder(), FOLDER_NAME_BY_XML_CREATE);

        Response response = given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("description",FOLDER_DESCRIPTION)
                .when()
                .post(getAddDescriptionToCreatedItemPath(FOLDER_NEW_NAME))
                .then()
                .spec(responseSpec(302, 500L))
                .extract().response();

        JobResponse responseJobByName = (jobController.getJobByName(FOLDER_NEW_NAME)).as(JobResponse.class);

        Assert.assertEquals(response.statusCode(), 302);
        Assert.assertTrue(response.time() < 600L);

        SoftAssertions.assertSoftly(softly -> {
            Allure.step(String.format("Expected result: fullName is '%s'", FOLDER_NEW_NAME));
            softly.assertThat(responseJobByName.getFullName()).isEqualTo(FOLDER_NEW_NAME);
            Allure.step("(Expected result: description is empty");
            softly.assertThat(responseJobByName.getDescription()).isEqualTo("");
            Allure.step(String.format("Expected result: _class is '%s'", ModeType.FOLDER_MODE.getMode()));
            softly.assertThat(responseJobByName.getClassName()).isEqualTo(ModeType.FOLDER_MODE.getMode());
        });

        jobController.deleteJob("", FOLDER_NEW_NAME);
    }

    @Test()
    @Description("04.003.04 Delete Folder")
    public void testDeleteFolder() {
        jobController.createJob(JobTestData.getDefaultFolder(), FOLDER_NEW_NAME);

        Allure.step("Send DELETE request -> Delete Folder with name %s".formatted(FOLDER_NEW_NAME));
        Response response = given()
                .spec(requestSpec())
                .when()
                .delete(getDeleteItemPath(FOLDER_NEW_NAME))
                .then()
                .spec(responseSpec())
                .extract().response();

        Assert.assertEquals(response.statusCode(),204);
        Assert.assertTrue(response.time() < 600L);

        Allure.step("Send GET request -> Get Folder with name %s".formatted(FOLDER_NEW_NAME));
        Response getJobByNameResponse = jobController.getJobByName(FOLDER_NEW_NAME);

        Assert.assertEquals(getJobByNameResponse.statusCode(),404);
        Assert.assertTrue(getJobByNameResponse.time() < 600L);
    }

    @Test
    @Description("04.003.05 Delete deleted Folder")
    public void testDeleteDeletedFolder() {
        jobController.createJob(JobTestData.getDefaultFolder(), FOLDER_NAME);

        Allure.step("Send DELETE request -> Delete Folder with name %s".formatted(FOLDER_NAME));
        Response response = jobController.deleteJob("", FOLDER_NAME);

        Assert.assertEquals(response.statusCode(), 204);
        Assert.assertTrue(response.time() < 600L);

        Allure.step("Send DELETE request -> Try to delete Folder with name %s again".formatted(FOLDER_NAME));
        Response responseDeleteJob = jobController.deleteJob("", FOLDER_NAME);

        Assert.assertEquals(responseDeleteJob.statusCode(), 404);
        Assert.assertTrue(responseDeleteJob.time() < 600L);
    }

    @Test(dataProvider = "projectNameAndXmlFileCreate", dataProviderClass = TestDataProvider.class)
    @Description("04.007.01 Create Project in Folder")
    public void testCreateProjectInFolder(String name, ModeType mode) {
        jobController.createJob(JobTestData.getDefaultFolder(), FOLDER_NEW_NAME);

        Response response = given()
                .spec(requestSpec())
                .formParam("name", name)
                .formParam( "mode", mode.getMode())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .when()
                .post("/job/%s/createItem".formatted(FOLDER_NEW_NAME))
                .then()
                .spec(responseSpec())
                .extract().response();

        Assert.assertEquals(response.statusCode(), 302);
        Assert.assertTrue(response.time() < 600L);

        Allure.step("Expected result: Project name '%s' NOT found in the list on Dashboard".formatted(name));
        Assert.assertFalse(findItemInAllProjectList(name));

        Allure.step("Expected result: Project name '%s' found inside the Folder '%s'".formatted(name, FOLDER_NEW_NAME));
        Assert.assertTrue(findProjectByNameInsideFolder(FOLDER_NEW_NAME, name));
    }

    @Test()
    @Description("04.003.06 Delete Folder with Project")
    public void testDeleteFolderWithProject() {
        jobController.createJob(JobTestData.getDefaultFolder(), FOLDER_NEW_NAME);
        createSeveralJobsInFolder(FOLDER_NEW_NAME);

        jobController.deleteJob("",FOLDER_NEW_NAME);
    }

    @Test
    @Description("04.002.01 Move Folder to Folder")
    public void testMoveFolderToFolder() {
        String parentFolder = "ParentFolder";
        String childFolder = "ChildFolder";

        jobController.createJob(JobTestData.getDefaultFolder(), parentFolder);
        jobController.createJob(JobTestData.getDefaultFolder(), childFolder);

        Response response = given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("destination", "/%s".formatted(parentFolder))
                .when()
                .post("job/%s/move/move".formatted(childFolder))
                .then()
                .spec(responseSpec())
                .extract().response();

        Assert.assertEquals(response.statusCode(), 302);
        Assert.assertTrue(response.time() < 600L);

        Allure.step("Expected result: Project name '%s' NOT found in the list on Dashboard".formatted(childFolder));
        Assert.assertFalse(findItemInAllProjectList(childFolder));
        Allure.step("Expected result: Project name '%s' found in the list on Dashboard".formatted(parentFolder));
        Assert.assertTrue(findItemInAllProjectList(parentFolder));

        Allure.step("Expected result: Project name '%s' found inside the Folder '%s'".formatted(childFolder, parentFolder));
        Assert.assertTrue(findProjectByNameInsideFolder(parentFolder, childFolder));
    }
}
