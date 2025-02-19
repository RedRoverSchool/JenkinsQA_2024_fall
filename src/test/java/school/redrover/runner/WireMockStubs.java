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


    public static void stubVerifyPluginsInstalled() {
        String payload = """
                  {
                  "message": "Mock server is working!",
                  "_class": "hudson.LocalPluginManager",
                  "plugins": [
                    {
                      "active": true,
                      "backupVersion": null,
                      "bundled": false,
                      "deleted": false,
                      "dependencies": [],
                      "detached": true,
                      "downgradable": false,
                      "enabled": true,
                      "hasUpdate": false,
                      "longName": "PluginA",
                      "pinned": false,
                      "requiredCoreVersion": "2.387.3",
                      "shortName": "pluginA",
                      "supportsDynamicLoad": "MAYBE",
                      "url": "https://plugins.jenkins.io/pluginA",
                      "version": "162.v0e6ec0fcfcf6"
                    },
                    {
                      "active": true,
                      "backupVersion": null,
                      "bundled": false,
                      "deleted": false,
                      "dependencies": [],
                      "detached": true,
                      "downgradable": false,
                      "enabled": true,
                      "hasUpdate": false,
                      "longName": "PluginB",
                      "pinned": false,
                      "requiredCoreVersion": "2.440.3",
                      "shortName": "pluginB",
                      "supportsDynamicLoad": "YES",
                      "url": "https://plugins.jenkins.io/pluginB",
                      "version": "511.v0a_a_1a_334f41b_"
                    }
                  ]
                }
                """;

        wireMockServer.stubFor(post(urlEqualTo("/pluginManager/api/json?depth=1"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(payload)));
    }
}
