package school.redrover.controllers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static school.redrover.runner.TestApiUtils.*;
import static school.redrover.testdata.TestData.PIPELINE;

public class JobController {
    private static final String JOB_ENDPOINT = "job";

    public Response createPipeline(String name) {
        return given()
                .spec(requestSpec())
                .queryParam("name", name)
                .contentType(ContentType.XML)
                .body(toXML(PIPELINE))
                .when()
                .post("createItem")
                .andReturn();
    }

    public Response getJobByName(String name) {
        return given()
                .spec(requestSpec())
                .basePath(JOB_ENDPOINT)
                .when()
                .get("%s/api/json".formatted(name))
                .andReturn();
    }

    public Response renameJob(String oldName, String newName) {
        return given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .queryParam("newName", newName)
                .basePath(JOB_ENDPOINT)
                .when()
                .post("%s/doRename".formatted(oldName))
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
