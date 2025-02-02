package school.redrover.api.restassured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import school.redrover.runner.BaseApiTest;
import school.redrover.runner.WireMockStubs;

import static io.restassured.RestAssured.given;
import static school.redrover.runner.TestApiUtils.requestSpec;
import static school.redrover.runner.TestApiUtils.responseSpec;

@Epic("API")
@Feature("Mock user")
public class MockUserTest extends BaseApiTest {

    @BeforeClass
    private void stubs() {
        if (!"qa".equalsIgnoreCase(ENV)) {
            throw new SkipException("Skipping MockUserTest: Not running in QA environment");
        }
        stubEndpoints(WireMockStubs::stubCreateUser);
    }

    @Test
    @Description("001 Create user with valid data")
    public void testFullRequestReturns200() {
        String successPayload = """
                {
                    "userName": "Ivan",
                    "userFullName": "Ivanov",
                    "e-mail": "ivan@gmail.com"
                }
                """;

        given()
                .spec(requestSpec())
                .contentType(ContentType.JSON)
                .body(successPayload)
                .when()
                .post("createUser")
                .then()
                .spec(responseSpec(200, 1000L))
                .body("id", Matchers.equalTo("123456"))
                .body("userName", Matchers.equalTo("Ivan"))
                .body("userFullName", Matchers.equalTo("Ivanov"))
                .body("e-mail", Matchers.equalTo("ivan@gmail.com"));
    }

    @Test
    @Description("002 Create user without e-mail")
    public void testMissingFieldReturns400() {
        given()
                .spec(requestSpec())
                .contentType(ContentType.JSON)
                .body("{\"userName\": \"Ivan\", \"userFullName\": \"Ivanov\"}") // Без e-mail
                .when()
                .post("createUser")
                .then()
                .spec(responseSpec(400, 1000L))
                .body("error", Matchers.equalTo("Missing required fields"));
    }
}
