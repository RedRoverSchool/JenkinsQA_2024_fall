package school.redrover.controllers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import school.redrover.models.job.JobType;
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

}
