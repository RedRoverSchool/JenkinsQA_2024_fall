package school.redrover.api.restassured;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.ProjectResponse;
import school.redrover.runner.BaseApiTest;
import school.redrover.runner.ProjectUtils;
import school.redrover.runner.TestUtils;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import static school.redrover.runner.TestApiUtils.requestSpec;
import static school.redrover.runner.TestApiUtils.responseSpec;


@Epic("API")
@Story("Folder")
public class FolderApiTest extends BaseApiTest {

    private static final String FOLDER_NAME = "FolderApiTest";
    private static final String FOLDER_CREATE_MODE = "com.cloudbees.hudson.plugins.folder.Folder";
    private static final String FOLDER_NEW_NAME = "FolderApiNewName";
    private static final String FOLDER_DESCRIPTION = "Add descrition to folder!";

    @Test
    @Description("002 Create Folder with valid name")
    public void testCreateFolderWithValidName() {
        given()
                .spec(requestSpec())
                .queryParam("name", FOLDER_NAME)
                .queryParam( "mode", FOLDER_CREATE_MODE)
                .contentType("application/x-www-form-urlencoded")
                .when()
                .post("createItem")
                .then()
                .statusCode(302);

        Response responseGetCreatedItem = given()
                .spec(requestSpec())
                .when()
                .get("job/%s/api/json".formatted(FOLDER_NAME))
                .then()
                .spec(responseSpec(200,500L))
                .body(matchesJsonSchema(TestUtils.loadSchema("folder-schema.json")))
                .extract()
                .response();

        ProjectResponse getItemByNameResponse = responseGetCreatedItem.as(ProjectResponse.class);

        Assert.assertEquals(responseGetCreatedItem.getHeader("Content-Type"), "application/json;charset=utf-8");
        Allure.step(String.format("Expected result: fullName is '%s'", FOLDER_NAME));
        Assert.assertEquals(getItemByNameResponse.getFullName(), FOLDER_NAME);
        Allure.step("(ERR)Expected result: description is null");
        Assert.assertEquals(getItemByNameResponse.getDescription(),null);
        Allure.step(String.format("Expected result: _class is '%s'", FOLDER_CREATE_MODE));
        Assert.assertEquals(getItemByNameResponse.get_class(),FOLDER_CREATE_MODE);
    }

    @Test
    @Description("015 Create Folder with empty name")
    public void testCreateFolderWithEmptyName() {
        Response projectResponse = given().spec(requestSpec())
                .queryParam("name","")
                .queryParam("mode", FOLDER_CREATE_MODE)
                .contentType("application/x-www-form-urlencoded")
                .when()
                .post("createItem")
                .then()
                .spec(responseSpec(400, 500L))
                .extract()
                .response();

        Allure.step("Expected result: Header 'X-Error' has value 'No name is specified'");
        Assert.assertEquals(projectResponse.getHeaders().getValue("X-Error"), "No name is specified");
    }

    @Test(dependsOnMethods = "testCreateFolderWithValidName")
    @Description("008 Rename Folder")
    public void testRenameFolder() {
        given().spec(requestSpec())
                .queryParam("newName", FOLDER_NEW_NAME)
                .contentType("application/x-www-form-urlencoded")
                .when()
                .post("job/%s/confirmRename".formatted(FOLDER_NAME))
                .then()
                .spec(responseSpec(302, 500L));

        Response responseGetItemByName = given()
                .spec(requestSpec())
                .when()
                .get("job/%s/api/json".formatted(FOLDER_NEW_NAME))
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


    @Test(dependsOnMethods = "testRenameFolder")
    @Description("007 Add Description to Folder")
    public void testAddDescriptionToFolder() {

        given()
                .spec(requestSpec())
                .contentType("application/x-www-form-urlencoded")
                .formParam("description",FOLDER_DESCRIPTION)
                .when()
                .post(getAddDescriptionURL(FOLDER_NEW_NAME))
                .then()
                .spec(responseSpec(302, 500L));

        Response responseGetItemByName = given()
                .spec(requestSpec())
                .when()
                .get("job/%s/api/json".formatted(FOLDER_NEW_NAME))
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
                .delete("job/%s/".formatted(FOLDER_NEW_NAME))
                .then()
                .spec(responseSpec(204,500L));

        given()
                .spec(requestSpec())
                .when()
                .get("job/%s/api/json".formatted(FOLDER_NEW_NAME))
                .then()
                .spec(responseSpec(404, 500L));
    }

}
