package school.redrover.runner;

import org.testng.annotations.BeforeMethod;
import school.redrover.model.CrumbIssuerResponse;

import java.util.Map;

import static io.restassured.RestAssured.*;

public abstract class BaseApiTest {

    protected static CrumbIssuerResponse crumbIssuerResponse;
    protected static String firstCookieName;
    protected static String firstCookieValue;

    @BeforeMethod
    protected void setUp() {

        JenkinsUtils.clearData();

        Map<String, String> cookies = given()
                .spec(Specifications.baseRequestSpec())
                .when()
                .get(ProjectUtils.getUrl()) // Запрос к корневому URL Jenkins
                .then()
                .extract().cookies();
        try {
            firstCookieName = cookies.keySet().iterator().next();
            firstCookieValue = cookies.get(firstCookieName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        crumbIssuerResponse = given()
                .spec(Specifications.baseRequestSpec())
                .header("Cookie", firstCookieName + "=" + firstCookieValue)
                .when()
                .get("/crumbIssuer/api/json")
                .then()
                .spec(Specifications.baseResponseSpec())
                .extract().response().as(CrumbIssuerResponse.class);
        System.out.println(crumbIssuerResponse.getCrumb());
    }

}
