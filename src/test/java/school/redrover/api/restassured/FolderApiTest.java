package school.redrover.api.restassured;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.ProjectListResponse;
import school.redrover.model.ProjectResponse;
import school.redrover.runner.BaseApiTest;
import school.redrover.runner.TestDataProvider;

import static io.restassured.RestAssured.given;

import static school.redrover.runner.TestApiUtils.requestSpec;
import static school.redrover.runner.TestApiUtils.responseSpec;
import static school.redrover.runner.TestUtils.loadPayload;


@Epic("API")
@Story("Folder")
public class FolderApiTest extends BaseApiTest {

    private static final String FOLDER_NAME = "FolderApiTest";
    private static final String FOLDER_CREATE_MODE = "com.cloudbees.hudson.plugins.folder.Folder";
    private static final String FOLDER_NEW_NAME = "FolderApiNewName";
    private static final String FOLDER_DESCRIPTION = "Add description to folder!";
    private static final String FOLDER_NAME_BY_XML_CREATE = "FolderCreateByXML";
    private static final String FOLDER_NAME_COPY_FROM = "FolderCopyFrom";

    private static String getCreateItemPath() {return "createItem";}
    private static String getItemByNamePath(String name) {return "job/%s/api/json".formatted(name);}
    private static String getRenameItemPath(String name) {return "job/%s/confirmRename".formatted(name);}
    private static String getAddDescriptionToCreatedItemPath(String name) {return "job/%s/submitDescription".formatted(name);}
    private static String getAllProjectListPath() {return "api/json";}
    private static String getDeleteItem(String name) {return "job/%s/".formatted(name);}

    private void createNewFolder(String name, String mode) {
        given()
                .spec(requestSpec())
                .formParam("name", name)
                .formParam( "mode", mode)
                .contentType("application/x-www-form-urlencoded")
                .when()
                .post(getCreateItemPath());
    }

    private ProjectResponse getResponseGetItemByName(String name) {
        Response responseGetCreatedItem = given()
                .spec(requestSpec())
                .when()
                .get(getItemByNamePath(name))
                .then()
                .extract()
                .response();

        return responseGetCreatedItem.as(ProjectResponse.class);
    }

    private ProjectListResponse getResponseGetAllProjectList() {
        Response responseGetAllProjectList = given()
                .spec(requestSpec())
                .when()
                .get(getAllProjectListPath())
                .then()
                .extract()
                .response();

        return responseGetAllProjectList.as(ProjectListResponse.class);
    }

    private boolean findItemInAllProjectList(String name) {
        Response responseGetAllProjectList = given()
                .spec(requestSpec())
                .when()
                .get(getAllProjectListPath())
                .then()
                .extract()
                .response();

        ProjectListResponse projectListresponse = responseGetAllProjectList.as(ProjectListResponse.class);

        return  projectListresponse.getJobs().stream()
                .anyMatch(project -> project.getName().equals(name));
    }

    private void deleteItem(String name) {
        given()
                .spec(requestSpec())
                .when()
                .delete(getDeleteItem(name));
    }

    @Test
    @Description("002 Create Folder with valid name")
    public void testCreateFolderWithValidName() {
        given()
                .spec(requestSpec())
                .formParam("name", FOLDER_NAME)
                .formParam( "mode", FOLDER_CREATE_MODE)
                .contentType("application/x-www-form-urlencoded")
                .when()
                .post(getCreateItemPath())
                .then()
                .statusCode(302);

        Response responseGetCreatedItem = given()
                .spec(requestSpec())
                .when()
                .get(getItemByNamePath(FOLDER_NAME))
                .then()
                .spec(responseSpec(200,500L))
                .extract()
                .response();

        ProjectResponse getItemByNameResponse = responseGetCreatedItem.as(ProjectResponse.class);

        Allure.step(String.format("Expected result: fullName is '%s'", FOLDER_NAME));
        Assert.assertEquals(getItemByNameResponse.getFullName(), FOLDER_NAME);
        Allure.step("(ERR)Expected result: description is null");
        Assert.assertEquals(getItemByNameResponse.getDescription(),null);
        Allure.step(String.format("Expected result: _class is '%s'", FOLDER_CREATE_MODE));
        Assert.assertEquals(getItemByNameResponse.get_class(),FOLDER_CREATE_MODE);

        Response responseGetAllProjectList = given()
                .spec(requestSpec())
                .when()
                .get(getAllProjectListPath())
                .then()
                .spec(responseSpec(200, 500L))
                .extract()
                .response();

        ProjectListResponse projectListresponse = responseGetAllProjectList.as(ProjectListResponse.class);

        boolean findItemByName = projectListresponse.getJobs().stream()
                .anyMatch(project -> project.getName().equals(FOLDER_NAME));

        Allure.step("Expected result: Folder name found in the list");
        Assert.assertTrue(findItemByName, "Folder name not found in the list");
    }

    @Test
    @Description("00.006.03 Create Folder with valid name (XML)")
    public void testCreateFolderWithValidNameXml() {
        given()
                .spec(requestSpec())
                .contentType("application/xml")
                .queryParam("name", FOLDER_NAME_BY_XML_CREATE)
                .body(loadPayload("create-empty-folder.xml"))
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec(200,500L));

        Response response = given()
                .spec(requestSpec())
                .when()
                .get(getItemByNamePath(FOLDER_NAME_BY_XML_CREATE))
                .then()
                .spec(responseSpec(200,500L))
                .extract()
                .response();

        ProjectResponse getItemByNameResponse = response.as(ProjectResponse.class);

        Allure.step(String.format("Expected result: fullName is '%s'", FOLDER_NAME_BY_XML_CREATE));
        Assert.assertEquals(getItemByNameResponse.getFullName(), FOLDER_NAME_BY_XML_CREATE);
        Allure.step("(ERR)Expected result: description is null");
        Assert.assertEquals(getItemByNameResponse.getDescription(),"");
        Allure.step(String.format("Expected result: _class is '%s'", FOLDER_CREATE_MODE));
        Assert.assertEquals(getItemByNameResponse.get_class(),FOLDER_CREATE_MODE);

        Response responseGetAllProjectList = given()
                .spec(requestSpec())
                .when()
                .get(getAllProjectListPath())
                .then()
                .spec(responseSpec(200, 500L))
                .extract()
                .response();

        ProjectListResponse projectListresponse = responseGetAllProjectList.as(ProjectListResponse.class);

        boolean findItemByName = projectListresponse.getJobs().stream()
                .anyMatch(project -> project.getName().equals(FOLDER_NAME_BY_XML_CREATE));

        Allure.step("Expected result: Folder name found in the list");
        Assert.assertTrue(findItemByName, "Folder name not found in the list");
    }

    @Test(dataProvider = "providerUnsafeCharacters", dataProviderClass = TestDataProvider.class)
    @Description("00.006.17 Create Folder with unsafe character")
    public void testCreateFolderWithUnsafeCharacter(String unsafeCharacter) {
        Response projectResponse = given().spec(requestSpec())
                .formParam("name", unsafeCharacter)
                .formParam("mode", FOLDER_CREATE_MODE)
                .contentType("application/x-www-form-urlencoded")
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec(400, 500L))
                .extract()
                .response();

        Allure.step("Expected result: Header 'X-Error' has value '%s is an unsafe character'".formatted(unsafeCharacter));
        Assert.assertEquals(projectResponse.getHeaders().getValue("X-Error"), "%s  is an unsafe character".formatted(unsafeCharacter));
    }

    @Test
    @Description("015 Create Folder with empty name")
    public void testCreateFolderWithEmptyName() {
        Response projectResponse = given().spec(requestSpec())
                .formParam("name","")
                .formParam("mode", FOLDER_CREATE_MODE)
                .contentType("application/x-www-form-urlencoded")
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec(400, 500L))
                .extract()
                .response();

        Allure.step("Expected result: Header 'X-Error' has value 'No name is specified'");
        Assert.assertEquals(projectResponse.getHeaders().getValue("X-Error"), "No name is specified");
    }

    @Test
    @Description("00.006.16 Create Folder by copy from another folder")
    public void testCreateFolderByCopyFromAnotherFolder() {
        createNewFolder(FOLDER_NAME, FOLDER_CREATE_MODE);

        given()
                .spec(requestSpec())
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", FOLDER_NAME_COPY_FROM)
                .formParam("mode", "copy")
                .formParam("from", FOLDER_NAME)
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec(302,500L));


        Allure.step(String.format("Expected result: fullName is '%s'", FOLDER_NAME_BY_XML_CREATE));
        Assert.assertEquals(getResponseGetItemByName(FOLDER_NAME_COPY_FROM).getFullName(), FOLDER_NAME_COPY_FROM);
        Allure.step("(ERR)Expected result: description is null");
        Assert.assertEquals(getResponseGetItemByName(FOLDER_NAME_COPY_FROM).getDescription(),null);
        Allure.step(String.format("Expected result: _class is '%s'", FOLDER_CREATE_MODE));
        Assert.assertEquals(getResponseGetItemByName(FOLDER_NAME_COPY_FROM).get_class(),FOLDER_CREATE_MODE);
        Allure.step(String.format("Expected result: Folder name '%s' found in all project list", FOLDER_NAME_COPY_FROM));
        Assert.assertTrue(findItemInAllProjectList(FOLDER_NAME_COPY_FROM));

        deleteItem(FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testCreateFolderWithValidName")
    @Description("008 Rename Folder")
    public void testRenameFolder() {
        given().spec(requestSpec())
                .formParam("newName", FOLDER_NEW_NAME)
                .contentType("application/x-www-form-urlencoded")
                .when()
                .post(getRenameItemPath(FOLDER_NAME))
                .then()
                .spec(responseSpec(302, 500L));

        Response responseGetItemByName = given()
                .spec(requestSpec())
                .when()
                .get(getItemByNamePath(FOLDER_NEW_NAME))
                .then()
                .spec(responseSpec(200, 500L))
                .extract()
                .response();

        ProjectResponse getItemByNameResponse = responseGetItemByName.as(ProjectResponse.class);

        Allure.step(String.format("Expected result: fullName is '%s'", FOLDER_NEW_NAME));
        Assert.assertEquals(getItemByNameResponse.getFullName(),FOLDER_NEW_NAME);
        Allure.step("(ERR)Expected result: description is null");
        Assert.assertEquals(getItemByNameResponse.getDescription(),null);
        Allure.step(String.format("Expected result: _class is '%s'", FOLDER_CREATE_MODE));
        Assert.assertEquals(getItemByNameResponse.get_class(),FOLDER_CREATE_MODE);

        Response responseGetAllProjectList = given()
                .spec(requestSpec())
                .when()
                .get(getAllProjectListPath())
                .then()
                .spec(responseSpec(200, 500L))
                .extract()
                .response();

        ProjectListResponse projectListresponse = responseGetAllProjectList.as(ProjectListResponse.class);

        boolean findItemByNewName = projectListresponse.getJobs().stream()
                .anyMatch(project -> project.getName().equals(FOLDER_NEW_NAME));

        boolean findItemByOldName = projectListresponse.getJobs().stream()
                .anyMatch(project -> project.getName().equals(FOLDER_NAME));

        Allure.step("Expected result: Folder name found in the list");
        Assert.assertTrue(findItemByNewName, "Folder name not found in the list");
        Allure.step("Expected result: Folder old name NOT found in the list");
        Assert.assertFalse(findItemByOldName, "Folder name found in the list");
    }

    @Test(dependsOnMethods = "testRenameFolder")
    @Description("007 Add Description to Folder")
    public void testAddDescriptionToFolder() {

        given()
                .spec(requestSpec())
                .contentType("application/x-www-form-urlencoded")
                .formParam("description",FOLDER_DESCRIPTION)
                .when()
                .post(getAddDescriptionToCreatedItemPath(FOLDER_NEW_NAME))
                .then()
                .spec(responseSpec(302, 500L));

        Response responseGetItemByName = given()
                .spec(requestSpec())
                .when()
                .get(getItemByNamePath(FOLDER_NEW_NAME))
                .then()
                .spec(responseSpec(200, 500L))
                .extract()
                .response();

        ProjectResponse getItemByNameResponse = responseGetItemByName.as(ProjectResponse.class);

        Allure.step(String.format("Expected result: fullName is '%s'", FOLDER_NEW_NAME));
        Assert.assertEquals(getItemByNameResponse.getFullName(),FOLDER_NEW_NAME);
        Allure.step("(ERR)Expected result: description is null");
        Assert.assertEquals(getItemByNameResponse.getDescription(),null);
        Allure.step(String.format("Expected result: _class is '%s'", FOLDER_CREATE_MODE));
        Assert.assertEquals(getItemByNameResponse.get_class(),FOLDER_CREATE_MODE);
    }

    @Test(dependsOnMethods = "testAddDescriptionToFolder")
    @Description("004 Delete Folder")
    public void testDeleteFolder() {
        given()
                .spec(requestSpec())
                .when()
                .delete(getDeleteItem(FOLDER_NEW_NAME))
                .then()
                .spec(responseSpec(204,500L));

        given()
                .spec(requestSpec())
                .when()
                .get(getItemByNamePath(FOLDER_NEW_NAME))
                .then()
                .spec(responseSpec(404, 500L));
    }

}
