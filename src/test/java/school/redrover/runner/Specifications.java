package school.redrover.runner;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Base64;

public class Specifications {

    public static RequestSpecification baseRequestSpec() {
        String auth = ProjectUtils.getUserName() + ":" + ProjectUtils.getPassword();
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        return new RequestSpecBuilder()
                .addHeader("Authorization", "Basic " + encodedAuth)
                .setBaseUri(ProjectUtils.getUrl())
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification authRequestSpec() {
        return new RequestSpecBuilder()
                .addRequestSpecification(baseRequestSpec())
                .addHeader(BaseApiTest.crumbIssuerResponse.getCrumbRequestField(), BaseApiTest.crumbIssuerResponse.getCrumb())
                .addHeader("Cookie", BaseApiTest.firstCookieName + "=" + BaseApiTest.firstCookieValue)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification baseResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(LogDetail.ALL)
                .build();
    }

}
