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

public class TestApiUtils {

    public static RequestSpecification baseRequestSpec() {
        LogConfig logConfig = LogConfig.logConfig().blacklistHeaders(List.of("Authorization", "Cookie", "Jenkins-Crumb"));
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
                .addHeader("Cookie", BaseApiTest.cookieName + "=" + BaseApiTest.cookieValue)
                .build();
    }

    public static ResponseSpecification responseSpec(Integer statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .log(LogDetail.ALL)
                .build();
    }

}
