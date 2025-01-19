package school.redrover.api;

import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseApiTest;
import school.redrover.runner.ProjectUtils;
import school.redrover.runner.Specifications;
import school.redrover.runner.TestUtils;

import static io.restassured.RestAssured.given;

@Epic("API")
public class FreestyleProjectApiTest extends BaseApiTest {

    private static final String PROJECT_NAME = "FreestyleProject1";

    @Test
    @Feature("Freestyle project")
    @Description("Create freestyle project with valid name")
    public void createFreestyleProjectTest() {
        Specifications.installSpecification(Specifications.authRequestSpec(), Specifications.baseResponseSpec(200));
        String xml = TestUtils.readFileFromResources("create-empty-freestyle-project.xml");

        Allure.step("Creating project with name: " + PROJECT_NAME);

        given()
                .queryParams("name", PROJECT_NAME)
                .contentType(ContentType.XML)
                .body(xml)
                .baseUri(ProjectUtils.getUrl())
                .when()
                .post("createItem")
                .then();

        String projectName = given().basePath("job/" + PROJECT_NAME)
                .body(xml)
                .baseUri(ProjectUtils.getUrl())
                .when()
                .get("api/json")
                .then()
                .extract().body().jsonPath().getString("fullName");

        Assert.assertEquals(projectName, PROJECT_NAME);
    }
}
