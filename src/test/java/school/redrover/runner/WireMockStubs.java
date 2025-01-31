package school.redrover.runner;

import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static school.redrover.runner.BaseApiTest.wireMockServer;

public class WireMockStubs {

    public static void stubCreateProject(String projectName) {
        wireMockServer.stubFor(post("/createItem?name=%s".formatted(projectName))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\": \"Mock server is working!\"}")
                )
        );
    }

    public static void stubGetProjectByName(String projectName) {
        String payload = """
                {
                    "_class": "hudson.model.FreeStyleProject",
                    "description": null,
                    "displayName": "%1$s",
                    "displayNameOrNull": null,
                    "fullDisplayName": "%1$s",
                    "fullName": "%1$s",
                    "name": "%1$s",
                    "url": "http://localhost:8080/job/%1$s/",
                    "message": "Mock server is working!"
                }
                """.formatted(projectName);

        wireMockServer.stubFor(WireMock.get("/job/%s/api/json".formatted(projectName))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json;charset=utf-8")
                        .withBody(payload)
                )
        );
    }

    public static void stubCreateUser() {
        String successPayload = """
                {
                    "id": "123456",
                    "userName": "Ivan",
                    "userFullName": "Ivanov",
                    "e-mail": "ivan@gmail.com"
                }
                """;
        String errorPayload = """
            {
                "error": "Missing required fields"
            }
            """;

        wireMockServer.stubFor(post(urlEqualTo("/createUser"))
                .atPriority(1)
                .withRequestBody(matchingJsonPath("$.userName"))
                .withRequestBody(matchingJsonPath("$.userFullName"))
                .withRequestBody(matchingJsonPath("$.e-mail"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json;charset=utf-8")
                        .withBody(successPayload)
                )
        );

        wireMockServer.stubFor(post(urlEqualTo("/createUser"))
                .atPriority(2)
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json;charset=utf-8")
                        .withBody(errorPayload)
                )
        );

    }

}
