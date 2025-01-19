package school.redrover.runner;

import org.testng.annotations.BeforeMethod;
import school.redrover.model.CrumbIssuerResponse;

import java.util.Map;

import static io.restassured.RestAssured.*;

public abstract class BaseApiTest {

    protected static CrumbIssuerResponse crumbIssuerResponse;
    protected static String cookieName;
    protected static String cookieValue;

    @BeforeMethod
    protected void setup() {

        JenkinsUtils.clearData();

        Map<String, String> cookies = given()
                .spec(Specifications.baseRequestSpec())
                .when()
                .get(ProjectUtils.getUrl())
                .then()
                .extract().cookies();

        cookieName = cookies.keySet().iterator().next();
        cookieValue = cookies.get(cookieName);

        crumbIssuerResponse = given()
                .spec(Specifications.baseRequestSpec())
                .header("Cookie", cookieName + "=" + cookieValue)
                .when()
                .get("crumbIssuer/api/json")
                .then()
                .spec(Specifications.baseResponseSpec(200))
                .extract().response().as(CrumbIssuerResponse.class);
    }

}
