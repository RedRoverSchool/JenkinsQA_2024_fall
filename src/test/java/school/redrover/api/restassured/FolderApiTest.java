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
import school.redrover.runner.TestUtils;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import static school.redrover.runner.TestApiUtils.requestSpec;
import static school.redrover.runner.TestApiUtils.responseSpec;


@Epic("API")
public class FolderApiTest extends BaseApiTest {

    private static final String FOLDER_NAME = "FolderApiTest";
    private static final String FOLDER_CREATE_MODE = "com.cloudbees.hudson.plugins.folder.Folder";

    @Test
    @Story("Folder")
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
        Allure.step(String.format("Expected result: -class is '%s'", FOLDER_CREATE_MODE));
        Assert.assertEquals(getItemByNameResponse.get_class(),FOLDER_CREATE_MODE);
    }
}
