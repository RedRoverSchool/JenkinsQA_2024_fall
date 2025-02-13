package school.redrover.runner;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import school.redrover.model.ProjectListResponse;
import school.redrover.model.ProjectResponse;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.preemptive;
import static org.hamcrest.Matchers.lessThan;
import static school.redrover.runner.BaseApiTest.cookieName;
import static school.redrover.runner.BaseApiTest.cookieValue;

public class TestApiUtils {

    public static String getCreateItemPath() {
        return "createItem";
    }
    public static String getDeleteItemPath(String name) {return "/job/%s/".formatted(TestUtils.encodeParam(name));}
    public static String getAddDescriptionToCreatedItemPath(String name) {return "job/%s/submitDescription".formatted(TestUtils.encodeParam(name));}
    public static String getItemByNamePath(String name) {return "job/%s/api/json".formatted(TestUtils.encodeParam(name));}
    public static String getAllProjectListPath() {return "/api/json";}
    public static String getRenameItemPath(String name) {return "job/%s/confirmRename".formatted(name);}
    public static String getDisableProjectPath(String name) {return "/job/%s/disable".formatted(name);}
    public static String getEnableProjectPath(String name) {return "/job/%s/enable".formatted(name);}

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

    public static void createNewEmptyProjectXML(String name, String xmlFile) {
        given()
                .spec(requestSpec())
                .contentType(ContentType.XML)
                .queryParam("name", name)
                .body(TestUtils.loadPayload(xmlFile))
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec(200, 500L));
    }

    public static void deleteProject(String name) {
        given()
                .spec(requestSpec())
                .when()
                .delete(getDeleteItemPath(name))
                .then()
                .spec(responseSpec(204,500L));
    }

    @Step("Get Response -> GET Item by name")
    public static ProjectResponse getResponseGetItemByName(String name) {
        Response responseGetCreatedItem = given()
                .spec(requestSpec())
                .when()
                .get(getItemByNamePath(name))
                .then()
                .extract()
                .response();

        return responseGetCreatedItem.as(ProjectResponse.class);
    }

    @Step("Get Response -> GET All Projects List")
    public static ProjectListResponse getResponseGetAllProjectList() {
        Response responseGetAllProjectList = given()
                .spec(requestSpec())
                .when()
                .get(getAllProjectListPath())
                .then()
                .extract()
                .response();

        return responseGetAllProjectList.as(ProjectListResponse.class);
    }

    @Step("Find  item {name} by name in all projects list from Dashboard")
    public static boolean findItemInAllProjectList(String name) {
        Response responseGetAllProjectList = given()
                .spec(requestSpec())
                .when()
                .get(getAllProjectListPath())
                .then()
                .extract()
                .response();

        ProjectListResponse projectListresponse = responseGetAllProjectList.as(ProjectListResponse.class);

        return  projectListresponse.getJobs().stream()
                .anyMatch(project -> project.getName().equals(name));
    }
}
