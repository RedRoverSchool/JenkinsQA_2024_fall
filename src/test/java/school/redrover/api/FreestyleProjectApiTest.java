package school.redrover.api;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseApiTest;
import school.redrover.runner.ProjectUtils;
import school.redrover.runner.Specifications;
import school.redrover.runner.TestUtils;

import static io.restassured.RestAssured.given;

public class FreestyleProjectApiTest extends BaseApiTest {

    private static final String PROJECT_NAME = "FreestyleProject";

    @Test
    public void createFreestyleProjectTest() {
        String xml = TestUtils.readFileFromResources("create-empty-freestyle-project.xml");

        given()
                .queryParams("name", PROJECT_NAME)
                .spec(Specifications.authRequestSpec())
                .header("Content-Type", "application/xml")
                .body(xml)
                .baseUri(ProjectUtils.getUrl())
                .when()
                .post("/createItem")
                .then()
                .spec(Specifications.baseResponseSpec());

        String jobName = given().basePath("job/" + PROJECT_NAME)
                .spec(Specifications.authRequestSpec())
                .body(xml)
                .baseUri(ProjectUtils.getUrl())
                .when()
                .post("/api/json")
                .then()
                .extract().body().jsonPath().getString("jobs[0].name");

        Assert.assertEquals(jobName, PROJECT_NAME);

    }
}
