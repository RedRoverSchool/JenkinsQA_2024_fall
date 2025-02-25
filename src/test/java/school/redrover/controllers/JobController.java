package school.redrover.controllers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import school.redrover.testdata.JobType;
import school.redrover.testdata.ModeType;

import static io.restassured.RestAssured.given;
import static school.redrover.runner.TestApiUtils.*;

public class JobController {
    private static final String JOB_ENDPOINT = "job";

    public Response createJobXML(JobType jobType, String name) {
        return given()
                .spec(requestSpec())
                .queryParam("name", name)
                .contentType(ContentType.XML)
                .body(toXML(jobType.getProjectConfig()))
                .when()
                .post("createItem")
                .then()
                .spec(responseSpec())
                .extract().response();
    }

    public Response createJob(String name, String mode) {
        return given()
                .spec(requestSpec())
                .formParam("name", name)
                .formParam( "mode", mode)
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
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

    public Response deleteJob(String unused, String projectName) {
        return given()
                .spec(requestSpec())
                .basePath(JOB_ENDPOINT)
                .when()
                .delete("%s/".formatted(projectName))
                .andReturn();
    }

}
