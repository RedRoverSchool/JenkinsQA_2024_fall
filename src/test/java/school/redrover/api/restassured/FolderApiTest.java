package school.redrover.api.restassured;

import io.qameta.allure.*;
import io.restassured.http.ContentType;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseApiTest;
import school.redrover.runner.TestDataProvider;
import school.redrover.runner.TestUtils;

import static io.restassured.RestAssured.given;

import static school.redrover.runner.TestApiUtils.*;
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
    private static final String CREATE_EMPTY_FOLDER_XML_FILE = "create-empty-folder.xml";
    private static final String FOLDER_GET_BY_NAME_SCHEMA_JSON = "folder-schema.json";

    @Test(dataProvider = "projectNames", dataProviderClass = TestDataProvider.class)
    @Description("00.006.02 Create Folder with valid name")
    public void testCreateFolderWithValidName(String projectName) {
        given()
                .spec(requestSpec())
                .formParam("name", projectName)
                .formParam( "mode", FOLDER_CREATE_MODE)
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec(302, 500L));

        Allure.step("Expected result: JSON response is matched to JSON schema");
        Assert.assertTrue(matchSchemaWithJsonFile(getResponseGetItemByName(projectName), FOLDER_GET_BY_NAME_SCHEMA_JSON));

        Allure.step(String.format("Expected result: fullName is '%s'", projectName));
        Assert.assertEquals(getResponseGetItemByNameAsObject(projectName).getFullName(), projectName);
        Allure.step("(ERR)Expected result: description is null");
        Assert.assertEquals(getResponseGetItemByNameAsObject(projectName).getDescription(),null);
        Allure.step(String.format("Expected result: _class is '%s'", FOLDER_CREATE_MODE));
        Assert.assertEquals(getResponseGetItemByNameAsObject(projectName).get_class(),FOLDER_CREATE_MODE);

        Allure.step("Expected result: Folder name found in the list");
        Assert.assertTrue(findItemInAllProjectList(projectName), "Folder name not found in the list");

        deleteProject(projectName);
    }

    @Test(dataProvider = "projectNames", dataProviderClass = TestDataProvider.class)
    @Description("00.006.03 Create Folder with valid name (XML)")
    public void testCreateFolderWithValidNameXml(String projectName) {
        given()
                .spec(requestSpec())
                .contentType(ContentType.XML)
                .queryParam("name", projectName)
                .body(loadPayload(CREATE_EMPTY_FOLDER_XML_FILE))
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec(200,500L));

        Allure.step("Expected result: JSON response is matched to JSON schema");
        Assert.assertTrue(matchSchemaWithJsonFile(getResponseGetItemByName(projectName), FOLDER_GET_BY_NAME_SCHEMA_JSON));

        Allure.step(String.format("Expected result: fullName is '%s'", projectName));
        Assert.assertEquals(getResponseGetItemByNameAsObject(projectName).getFullName(), projectName);
        Allure.step("(Expected result: description is empty");
        Assert.assertEquals(getResponseGetItemByNameAsObject(projectName).getDescription(),"");
        Allure.step(String.format("Expected result: _class is '%s'", FOLDER_CREATE_MODE));
        Assert.assertEquals(getResponseGetItemByNameAsObject(projectName).get_class(),FOLDER_CREATE_MODE);

        Allure.step("Expected result: Folder name found in the list");
        Assert.assertTrue(findItemInAllProjectList(projectName), "Folder name not found in the list");

        deleteProject(projectName);
    }

    @Test(dataProvider = "providerUnsafeCharacters", dataProviderClass = TestDataProvider.class)
    @Description("00.006.17 Create Folder with unsafe character")
    public void testCreateFolderWithUnsafeCharacter(String unsafeCharacter) {
        Response projectResponse = given().spec(requestSpec())
                .formParam("name", unsafeCharacter)
                .formParam("mode", FOLDER_CREATE_MODE)
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
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
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
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
        createNewProjectXML(FOLDER_NAME, CREATE_EMPTY_FOLDER_XML_FILE);

        given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("name", FOLDER_NAME_COPY_FROM)
                .formParam("mode", "copy")
                .formParam("from", FOLDER_NAME)
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec(302,500L));


        Allure.step(String.format("Expected result: fullName is '%s'", FOLDER_NAME_BY_XML_CREATE));
        Assert.assertEquals(getResponseGetItemByNameAsObject(FOLDER_NAME_COPY_FROM).getFullName(), FOLDER_NAME_COPY_FROM);
        Allure.step("Expected result: description is empty");
        Assert.assertEquals(getResponseGetItemByNameAsObject(FOLDER_NAME_COPY_FROM).getDescription(),"");
        Allure.step(String.format("Expected result: _class is '%s'", FOLDER_CREATE_MODE));
        Assert.assertEquals(getResponseGetItemByNameAsObject(FOLDER_NAME_COPY_FROM).get_class(),FOLDER_CREATE_MODE);
        Allure.step(String.format("Expected result: Folder name '%s' found in all project list", FOLDER_NAME_COPY_FROM));
        Assert.assertTrue(findItemInAllProjectList(FOLDER_NAME_COPY_FROM));

        deleteProject(FOLDER_NAME);
    }

    @Test()
    @Description("04.001.08 Rename Folder")
    public void testRenameFolder() {
        createNewProjectXML(FOLDER_NAME_BY_XML_CREATE, CREATE_EMPTY_FOLDER_XML_FILE);

        given().spec(requestSpec())
                .formParam("newName", FOLDER_NEW_NAME)
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .when()
                .post(getRenameItemPath(FOLDER_NAME_BY_XML_CREATE))
                .then()
                .spec(responseSpec(302, 500L));

        Allure.step(String.format("Expected result: fullName is '%s'", FOLDER_NEW_NAME));
        Assert.assertEquals(getResponseGetItemByNameAsObject(FOLDER_NEW_NAME).getFullName(),FOLDER_NEW_NAME);
        Allure.step("(Expected result: description is empy");
        Assert.assertEquals(getResponseGetItemByNameAsObject(FOLDER_NEW_NAME).getDescription(),"");
        Allure.step(String.format("Expected result: _class is '%s'", FOLDER_CREATE_MODE));
        Assert.assertEquals(getResponseGetItemByNameAsObject(FOLDER_NEW_NAME).get_class(),FOLDER_CREATE_MODE);

        Allure.step("Expected result: Folder name found in the list");
        Assert.assertTrue(findItemInAllProjectList(FOLDER_NEW_NAME), "Folder name not found in the list");
        Allure.step("Expected result: Folder old name NOT found in the list");
        Assert.assertFalse(findItemInAllProjectList(FOLDER_NAME_BY_XML_CREATE), "Folder name found in the list");
    }

    @Test()
    @Description("04.001.09 Rename Folder with the same name")
    public void testRenameFolderSameName() {
        createNewProjectXML(FOLDER_NAME_BY_XML_CREATE, CREATE_EMPTY_FOLDER_XML_FILE);

        given().spec(requestSpec())
                .formParam("newName", FOLDER_NAME_BY_XML_CREATE)
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .when()
                .post(getRenameItemPath(FOLDER_NAME_BY_XML_CREATE))
                .then()
                .spec(responseSpec(400, 500L));
    }

    @Test()
    @Description("007 Add Description to Folder")
    public void testAddDescriptionToFolder() {
        createNewProjectXML(FOLDER_NEW_NAME, CREATE_EMPTY_FOLDER_XML_FILE);

        given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("description",FOLDER_DESCRIPTION)
                .when()
                .post(getAddDescriptionToCreatedItemPath(FOLDER_NEW_NAME))
                .then()
                .spec(responseSpec(302, 500L));

        Allure.step("Expected result: JSON response is matched to JSON schema");
        Assert.assertTrue(matchSchemaWithJsonFile(getResponseGetItemByName(FOLDER_NEW_NAME), FOLDER_GET_BY_NAME_SCHEMA_JSON));

        Allure.step(String.format("Expected result: fullName is '%s'", FOLDER_NEW_NAME));
        Assert.assertEquals(getResponseGetItemByNameAsObject(FOLDER_NEW_NAME).getFullName(),FOLDER_NEW_NAME);
        Allure.step("(Expected result: description is empty");
        Assert.assertEquals(getResponseGetItemByNameAsObject(FOLDER_NEW_NAME).getDescription(),"");
        Allure.step(String.format("Expected result: _class is '%s'", FOLDER_CREATE_MODE));
        Assert.assertEquals(getResponseGetItemByNameAsObject(FOLDER_NEW_NAME).get_class(),FOLDER_CREATE_MODE);

        deleteProject(FOLDER_NEW_NAME);
    }

    @Test()
    @Description("04.003.04 Delete Folder")
    public void testDeleteFolder() {
        createNewProjectXML(FOLDER_NEW_NAME, CREATE_EMPTY_FOLDER_XML_FILE);

        Allure.step("Send DELETE request -> Delete Folder with name %s".formatted(FOLDER_NEW_NAME));
        given()
                .spec(requestSpec())
                .when()
                .delete(getDeleteItemPath(FOLDER_NEW_NAME))
                .then()
                .spec(responseSpec(204,500L));

        Allure.step("Send GET request -> Get Folder with name %s".formatted(FOLDER_NEW_NAME));
        given()
                .spec(requestSpec())
                .when()
                .get(getItemByNamePath(FOLDER_NEW_NAME))
                .then()
                .spec(responseSpec(404, 500L));
    }

    @Test
    @Description("04.003.05 Delete deleted Folder")
    public void testDeleteDeletedFolder() {
        createNewProjectXML(FOLDER_NAME, CREATE_EMPTY_FOLDER_XML_FILE);

        Allure.step("Send DELETE request -> Delete Folder with name %s".formatted(FOLDER_NAME));
        given()
                .spec(requestSpec())
                .when()
                .delete(getDeleteItemPath(FOLDER_NAME))
                .then()
                .spec(responseSpec(204,500L));

        Allure.step("Send DELETE request -> Try to delete Folder with name %s again".formatted(FOLDER_NAME));
        given()
                .spec(requestSpec())
                .when()
                .delete(getDeleteItemPath(FOLDER_NAME))
                .then()
                .spec(responseSpec(404,500L));
    }

    @Test(dataProvider = "projectNameAndXmlFileCreate", dataProviderClass = TestDataProvider.class,
            dependsOnMethods = "testRenameFolder")
    @Description()
    public void testCreateProjectInFolder(String name, String xmlFile) {
        given()
                .spec(requestSpec())
                .contentType(ContentType.XML)
                .queryParam("name", name)
                .body(TestUtils.loadPayload(xmlFile))
                .when()
                .post("/job/%s/createItem".formatted(FOLDER_NEW_NAME))
                .then()
                .spec(responseSpec(200, 500L));

        Allure.step("Expected result: Folder name found in the list on Dashboard");
        Assert.assertTrue(findItemInAllProjectList(FOLDER_NEW_NAME));

        Allure.step("Expected result: Project name '%s' NOT found in the list on Dashboard".formatted(name));
        Assert.assertFalse(findItemInAllProjectList(name));
    }

    @Test(dependsOnMethods = "testCreateProjectInFolder")
    @Description("Delete Folder with Project")
    public void testDeleteFolderWithProject() {
        deleteProject(FOLDER_NEW_NAME);
    }
}
