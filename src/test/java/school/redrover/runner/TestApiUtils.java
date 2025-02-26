package school.redrover.runner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import school.redrover.models.FolderInsideListResponse;
import school.redrover.models.ProjectListResponse;
import school.redrover.models.ProjectResponse;
import school.redrover.testdata.ModeType;
import school.redrover.testdata.TestDataProvider;
import school.redrover.controllers.JobController;

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

    public static String getDeleteItemPath(String name) {
        return "/job/%s/".formatted(TestUtils.encodeParam(name));
    }

    public static String getAddDescriptionToCreatedItemPath(String name) {
        return "job/%s/submitDescription".formatted(TestUtils.encodeParam(name));
    }

    public static String getItemByNamePath(String name) {
        return "job/%s/api/json".formatted(TestUtils.encodeParam(name));
    }

    public static String getAllProjectListPath() {
        return "/api/json";
    }

    public static String getRenameItemPath(String name) {
        return "job/%s/confirmRename".formatted(TestUtils.encodeParam(name));
    }

    public static String getDisableProjectPath(String name) {
        return "/job/%s/disable".formatted(TestUtils.encodeParam(name));
    }

    public static String getEnableProjectPath(String name) {
        return "/job/%s/enable".formatted(TestUtils.encodeParam(name));
    }

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

    public static ResponseSpecification responseSpec() {
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }

    @Step("Create Project (XML) {name}")
    public static void createNewProjectXML(String name, String xmlFile) {
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

    @Step(("Send DELETE request -> Delete project {name}"))
    public static void deleteProject(String name) {
        given()
                .spec(requestSpec())
                .when()
                .delete(getDeleteItemPath(name))
                .then()
                .spec(responseSpec(204, 500L));
    }

    @Step("Get Response -> GET Item by name")
    public static Response getResponseGetItemByName(String name) {
        return given()
                .spec(requestSpec())
                .when()
                .get(getItemByNamePath(name))
                .then()
                .extract()
                .response();
    }

    @Step("Get Response as Object -> GET Item by name")
    public static ProjectResponse getResponseGetItemByNameAsObject(String name) {
        Response responseGetCreatedItem = given()
                .spec(requestSpec())
                .when()
                .get(getItemByNamePath(name))
                .then()
                .extract()
                .response();

        return responseGetCreatedItem.as(ProjectResponse.class);
    }

    public static FolderInsideListResponse getResponseGetFolderInsideListAsObject(String parentFolderName) {
        Response responseGetFolder = given()
                .spec(requestSpec())
                .when()
                .get("/job/%s/api/json".formatted(parentFolderName))
                .then()
                .extract()
                .response();

        return responseGetFolder.as(FolderInsideListResponse.class);
    }

    @Step("Find  item {projectName} by name inside Folder {parentFolderName}")
    public static boolean findProjectByNameInsideFolder(String parentFolderName, String projectName) {
        return getResponseGetFolderInsideListAsObject(parentFolderName).getJobs().stream()
                .anyMatch(project -> project.getName().equals(projectName));
    }

    @Step("Get Response -> GET All Projects List")
    public static Response getResponseGetAllProjectList() {
        return given()
                .spec(requestSpec())
                .when()
                .get(getAllProjectListPath())
                .then()
                .extract()
                .response();
    }

    @Step("Get Response as Object -> GET All Projects List")
    public static ProjectListResponse getResponseGetAllProjectListAsObject() {
        try {
            Response responseGetAllProjectList = given()
                    .spec(requestSpec())
                    .when()
                    .get(getAllProjectListPath())
                    .then()
                    .extract()
                    .response();

            return responseGetAllProjectList.as(ProjectListResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Find  item {name} by name in all projects list from Dashboard")
    public static boolean findItemInAllProjectList(String name) {
        try {
            Response responseGetAllProjectList = given()
                    .spec(requestSpec())
                    .when()
                    .get(getAllProjectListPath())
                    .then()
                    .extract()
                    .response();

            ProjectListResponse projectListresponse = responseGetAllProjectList.as(ProjectListResponse.class);

            return projectListresponse.getJobs().stream()
                    .anyMatch(project -> project != null && project.getName().equals(name));
        } catch (Exception e) {
            System.err.println("Error parsing response: " + e.getMessage());
            return false;
        }
    }

    /**
     * Validates whether the response body matches the specified JSON schema.
     * <p>
     * This method takes a {@link Response} object and a JSON schema file name as input.
     * It extracts the response body as a string and validates it against the JSON schema
     * loaded from the specified file. If the response body matches the schema, the method
     * returns {@code true}; otherwise, it returns {@code false}.
     *
     * @param response     The {@link Response} object containing the API response.
     * @param nameJsonFile The name of the JSON schema file to validate against.
     */
    public static boolean matchSchemaWithJsonFile(Response response, String nameJsonFile) {
        try {
            String responseBody = response.getBody().asString();
            return JsonSchemaValidator.matchesJsonSchema(TestUtils.loadSchema(nameJsonFile)).matches(responseBody);
        } catch (Exception e) {
            System.err.println("Error parsing response: " + e.getMessage());
            return false;
        }
    }

    public static String toXML(Object classObject) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.writeValueAsString(classObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createSeveralJobsInFolder(String folder) {
        Object[][] projects = TestDataProvider.projectNameAndXmlFileCreate();
        for (Object[] projectData : projects) {
            String projectName = (String) projectData[0];
            ModeType mode = (ModeType) projectData[1];
            JobController.createJobInFolder(projectName, mode, folder);
        }
    }

}
