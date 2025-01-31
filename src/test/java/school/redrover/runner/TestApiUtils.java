package school.redrover.runner;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.List;

import static io.restassured.RestAssured.preemptive;
import static org.hamcrest.Matchers.lessThan;
import static school.redrover.runner.BaseApiTest.cookieName;
import static school.redrover.runner.BaseApiTest.cookieValue;

public class TestApiUtils {

    public static RequestSpecification baseRequestSpec() {
        LogConfig logConfig = LogConfig.logConfig().blacklistHeaders(List.of("Authorization", "Jenkins-Crumb"));
        RestAssured.config = RestAssured.config().logConfig(logConfig);

        return new RequestSpecBuilder()
                .setAuth(preemptive().basic(ProjectUtils.getUserName(), ProjectUtils.getPassword()))
                .setBaseUri(RestAssured.baseURI)
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .addRequestSpecification(baseRequestSpec())
                .addFilter(new AllureRestAssured()
                        .setRequestTemplate("request.ftl")
                        .setResponseTemplate("response.ftl"))
                .addHeader(BaseApiTest.crumbIssuerResponse.getCrumbRequestField(), BaseApiTest.crumbIssuerResponse.getCrumb())
                .addHeader("Cookie", cookieName + "=" + cookieValue)
                .build();
    }

    public static ResponseSpecification responseSpec(Integer statusCode, Long responseTime) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectResponseTime(lessThan(responseTime))
                .log(LogDetail.ALL)
                .build();
    }

}
