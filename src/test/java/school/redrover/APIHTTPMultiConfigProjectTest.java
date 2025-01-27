package school.redrover;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.qameta.allure.Allure;
import org.apache.http.HttpHeaders;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseAPIHttpTest;
import school.redrover.runner.ProjectUtils;
import school.redrover.runner.TestUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIHTTPMultiConfigProjectTest extends BaseAPIHttpTest { //Using HttpClient from standard Java library

    private static final String MULTI_CONFIG_PROJECT_NAME = "MultiConfigName";

    @Test
    public void testCreate() throws IOException, InterruptedException, URISyntaxException {
        HttpClient httpClient = HttpClient.newHttpClient();

        String query = "name=" + MULTI_CONFIG_PROJECT_NAME;
        String createItemUrl = ProjectUtils.getUrl() + "view/all/createItem?" + query;

        HttpRequest createRequest = HttpRequest.newBuilder()
                .uri(new URI(createItemUrl))
                .header(HttpHeaders.CONTENT_TYPE, "application/xml")
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .POST(HttpRequest.BodyPublishers.ofString(TestUtils.loadPayload("create-empty-multi-config.xml")))
                .build();

        Allure.step("Sending POST request to create project: " + createItemUrl, () -> {
            Allure.step("Request Headers: " + createRequest.headers().map());
            String requestBody = createRequest.bodyPublisher().map(Object::toString).orElse("No body");
            Allure.step("Request Body: " + requestBody);
        });

        HttpResponse<String> createResponse = httpClient.send(createRequest, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(createResponse.statusCode(), 200);

        String projectApiUrl = String.format(ProjectUtils.getUrl() + "job/%s/api/json", MULTI_CONFIG_PROJECT_NAME);

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(projectApiUrl))
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .GET()
                .build();

        Allure.step("Sending GET request to get project details: " + projectApiUrl, () -> Allure.step(
                "Request Headers: " + getRequest.headers().map()));

        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(getResponse.statusCode(), 200);

        String body = getResponse.body();
        Assert.assertNotNull(body);
        Assert.assertTrue(body.startsWith("{\"_class\":\"hudson.matrix.MatrixProject\""));

        Gson gson = new Gson();
        JsonObject jsonResponse = gson.fromJson(getResponse.body(), JsonObject.class);
        String projectName = jsonResponse.get("name").getAsString();

        Assert.assertEquals(projectName, MULTI_CONFIG_PROJECT_NAME, "The project is not created");
        Allure.step("Response Status Code: " + getResponse.statusCode());
        Allure.step("Response Body: " + body);
    }
}