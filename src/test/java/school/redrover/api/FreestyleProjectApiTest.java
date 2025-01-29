package school.redrover.api;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.FreestyleResponse;
import school.redrover.runner.*;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static school.redrover.runner.Specifications.installSpecification;
import static school.redrover.runner.TestUtils.loadPayload;

@Epic("API")
public class FreestyleProjectApiTest extends BaseApiTest {

    private static final String PROJECT_NAME = "FreestyleProject1";

    @Test
    @Feature("Freestyle project")
    @Description("001 Create freestyle project with valid name")
    public void testCreateFreestyleProjectAndGetByName() {
        installSpecification(Specifications.authRequestSpec(), Specifications.authResponseSpec(200));

        stubEndpoints(PROJECT_NAME, WireMockStubs::stubCreateProject, WireMockStubs::stubGetProjectByName);

        given()
                .queryParam("name", PROJECT_NAME)
                .contentType(ContentType.XML)
                .body(loadPayload("create-empty-freestyle-project.xml"))
                .when()
                .post("createItem")
                .then();

        Response response = given().basePath("job/" + PROJECT_NAME)
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
