package school.redrover.api.restassured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.ProjectResponse;
import school.redrover.runner.BaseApiTest;

import static io.restassured.RestAssured.given;
import static school.redrover.runner.TestApiUtils.requestSpec;
import static school.redrover.runner.TestApiUtils.responseSpec;
import static school.redrover.runner.TestUtils.loadPayload;

@Epic("API")
public class OrganizationFolderApiTest extends BaseApiTest {

    private static final String ORGANIZATION_FOLDER_NAME = "OrganizationFolderName";

    @Test
    @Feature("Organization folder")
    @Description("00.007.01 Create Organization folder with valid name")
    public void testCreateWithXML() {
       given()
               .spec(requestSpec())
               .queryParam("name", ORGANIZATION_FOLDER_NAME)
               .contentType(ContentType.XML)
               .body(loadPayload("create-empty-organization-folder.xml"))
               .when()
               .post("view/all/createItem")
               .then()
               .spec(responseSpec(200, 500L));

        Response response = given()
                .spec(requestSpec())
                .basePath("job/%s".formatted(ORGANIZATION_FOLDER_NAME))
                .when()
                .get("api/json")
                .then()
                .spec(responseSpec(200, 500L))
                .extract().response();

        ProjectResponse projectResponse = response.as(ProjectResponse.class);

        Assert.assertEquals(response.getHeader("Content-Type"), "application/json;charset=utf-8");
        Assert.assertTrue(response.getTime() <= 500);

        Assert.assertEquals(projectResponse.getName(), ORGANIZATION_FOLDER_NAME);
        Assert.assertEquals(projectResponse.get_class(), "jenkins.branch.OrganizationFolder");
    }
}
