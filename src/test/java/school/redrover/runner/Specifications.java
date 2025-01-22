package school.redrover.runner;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
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
                .addFilter(new AllureRestAssured())
                .setBaseUri(ProjectUtils.getUrl())
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification authRequestSpec() {
        return new RequestSpecBuilder()
                .addRequestSpecification(baseRequestSpec())
                .addHeader(BaseApiTest.crumbIssuerResponse.getCrumbRequestField(), BaseApiTest.crumbIssuerResponse.getCrumb())
                .addHeader("Cookie", BaseApiTest.cookieName + "=" + BaseApiTest.cookieValue)
                .build();
    }

    public static ResponseSpecification baseResponseSpec(Integer statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .log(LogDetail.ALL)
                .build();
    }

    public static void installSpecification(RequestSpecification requestSpec, ResponseSpecification responseSpec){
        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification = responseSpec;
    }

    public static void installSpecification(RequestSpecification requestSpec){
        RestAssured.requestSpecification = requestSpec;
    }

    public static void installSpecification(ResponseSpecification responseSpec){
        RestAssured.responseSpecification = responseSpec;
    }

}
