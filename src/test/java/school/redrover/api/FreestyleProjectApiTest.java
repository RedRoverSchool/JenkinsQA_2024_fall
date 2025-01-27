package school.redrover.api;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleResponse;
import school.redrover.runner.BaseApiTest;
import school.redrover.runner.ProjectUtils;
import school.redrover.runner.Specifications;
import school.redrover.runner.TestUtils;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

@Epic("API")
public class FreestyleProjectApiTest extends BaseApiTest {

    private static final String PROJECT_NAME = "FreestyleProject1";

    @Test
    @Feature("Freestyle project")
    @Description("001 Create freestyle project with valid name")
    public void createFreestyleProjectTest() {
        Specifications.installSpecification(Specifications.authRequestSpec(), Specifications.authResponseSpec(200));

        String payload = TestUtils.loadPayload("create-empty-freestyle-project.xml");

        given()
                .queryParams("name", PROJECT_NAME)
                .contentType(ContentType.XML)
                .body(payload)
                .baseUri(ProjectUtils.getUrl())
                .when()
                .post("createItem")
                .then();

        Response response = given().basePath("job/" + PROJECT_NAME)
                .body(payload)
                .baseUri(ProjectUtils.getUrl())
                .when()
                .get("api/json")
                .then()
                .body(matchesJsonSchema(TestUtils.loadSchema("freestyle-project-schema.json")))
                .extract().response();

        FreestyleResponse freestyleResponse = response.as(FreestyleResponse.class);

        Assert.assertEquals(response.getHeader("Content-Type"), "application/json;charset=utf-8");
        Assert.assertTrue(response.getTime() <= 500);
        Assert.assertEquals(freestyleResponse.getName(), PROJECT_NAME);
        Assert.assertNull(freestyleResponse.getDescription());
        Assert.assertEquals(freestyleResponse.getClassField(), "hudson.model.FreeStyleProject");
    }
}
