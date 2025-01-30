package school.redrover.runner;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import school.redrover.model.CrumbIssuerResponse;

import java.util.Map;
import java.util.function.Consumer;

import static io.restassured.RestAssured.given;

public abstract class BaseApiTest {

    protected static CrumbIssuerResponse crumbIssuerResponse;
    protected static String cookieName;
    protected static String cookieValue;
    protected static WireMockServer wireMockServer;
    private static final String ENV = System.getProperty("env", "prod");
    private static final String PROD_BASE_URI = ProjectUtils.getUrl();
    private static final String MOCK_HOST = "localhost";
    private static final Integer MOCK_PORT = 20115;

    @BeforeClass
    protected void setup() {
        JenkinsUtils.clearData();

        RestAssured.baseURI = PROD_BASE_URI;

        Map<String, String> cookies = given()
                .spec(TestApiUtils.baseRequestSpec())
                .when()
                .get()
                .then()
                .extract().cookies();

        cookieName = cookies.keySet().iterator().next();
        cookieValue = cookies.get(cookieName);

        crumbIssuerResponse = given()
                .filter(new CrumbHidingFilter())
                .spec(TestApiUtils.baseRequestSpec())
                .header("Cookie", cookieName + "=" + cookieValue)
                .when()
                .get("crumbIssuer/api/json")
                .then()
                .spec(TestApiUtils.baseResponseSpec(200))
                .extract().response().as(CrumbIssuerResponse.class);

        if ("qa".equalsIgnoreCase(ENV)) {
            wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(MOCK_PORT));
            wireMockServer.start();
            WireMock.configureFor(MOCK_HOST, MOCK_PORT);

            RestAssured.baseURI = "http://%S:%S".formatted(MOCK_HOST, MOCK_PORT);

            ProjectUtils.log("\n***Running tests in QA environment (WireMock server started)***\n");
        } else {
            RestAssured.baseURI = PROD_BASE_URI;

            ProjectUtils.log("\n***Running tests in PROD environment***\n");
        }
    }

    @SafeVarargs
    protected final void stubEndpoints(String projectName, Consumer<String>... stubs) {
        if ("qa".equalsIgnoreCase(ENV)) {
            for (Consumer<String> stub : stubs) {
                stub.accept(projectName);
            }
        }
    }

    @AfterClass
    protected void teardown() {
        if (wireMockServer != null) {
            wireMockServer.stop();
            ProjectUtils.log("\n***WireMock server stopped***\n");
        }
    }

}
