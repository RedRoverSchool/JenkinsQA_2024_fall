package school.redrover.controllers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import school.redrover.models.job.JobType;
import school.redrover.testdata.ListViewTestData;
import school.redrover.testdata.ModeType;

import static io.restassured.RestAssured.given;
import static school.redrover.runner.TestApiUtils.*;

public class JobController {
    private static final String JOB_ENDPOINT = "job";

    public <T extends JobType> Response createJob(T jobType, String name) {
        return given()
                .spec(requestSpec())
                .queryParam("name", name)
                .contentType(ContentType.XML)
                .body(toXML(jobType))
                .when()
                .post("createItem")
                .then()
                .spec(responseSpec())
                .extract().response();
    }

    public Response getJobByName(String name) {
        return given()
                .spec(requestSpec())
                .basePath(JOB_ENDPOINT)
                .when()
                .get("%s/api/json".formatted(name))
                .then()
                .spec(responseSpec())
                .extract().response();
    }

    public Response createWithMode(String name, ModeType modeType) {
        RequestSpecification request = given()
                .spec(requestSpec())
                .formParam("name", name);

        if (modeType != ModeType.NONE) {
            request.formParam("mode", modeType.getMode());
        }

        return request
                .when()
                .post("createItem")
                .then()
                .spec(responseSpec())
                .extract().response();
    }

    public Response renameJob(String oldName, String newName) {
        return given()
                .spec(requestSpec())
                .contentType("application/x-www-form-urlencoded")
                .queryParam("newName", newName)
                .basePath(JOB_ENDPOINT)
                .when()
                .post("%s/confirmRename".formatted(oldName))
                .andReturn();
    }

    public Response deleteJob(String projectName) {
        return given()
                .spec(requestSpec())
                .basePath(JOB_ENDPOINT)
                .when()
                .delete("%s/".formatted(projectName))
                .andReturn();
    }

    public static void createJobInFolder(String projectName, ModeType mode, String folderName) {
        given()
                .spec(requestSpec())
                .formParam("name", projectName)
                .formParam("mode", mode.getMode())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .when()
                .post("/job/%s/createItem".formatted(folderName))
                .then()
                .spec(responseSpec());
    }

    public static Response addJobListView(String viewName, String projectName) {
        return given()
                .spec(requestSpec())
                .contentType(ContentType.XML)
                .queryParam("name", viewName)
                .body(toXML(ListViewTestData.getListView(projectName)))
                .when()
                .post("createView")
                .then()
                .spec(responseSpec())
                .extract().response();
    }

    public Response moveFolderToFolder(String parentFolder, String childFolder) {
        return given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("destination", "/%s".formatted(parentFolder))
                .when()
                .post("job/%s/move/move".formatted(childFolder))
                .then()
                .spec(responseSpec())
                .extract().response();
    }
}
