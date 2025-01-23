package school.redrover;

import com.google.common.net.HttpHeaders;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.qameta.allure.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseAPIHttpTest;
import school.redrover.runner.HttpLogger;
import school.redrover.runner.ProjectUtils;
import school.redrover.runner.TestUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Epic("Http API  Requests")
public class APIHttpTest extends BaseAPIHttpTest {

    private static final String PIPELINE_NAME = "Pipeline";
    private static final String PIPELINE_NAME_BY_XML_CREATED = "PipelineXML";
    private static final String FOLDER_NAME_BY_XML_CREATED = "FolderXML";

    private List<String> getAllProjectNamesFromJsonResponseList() throws IOException {
        HttpGet httpGet = new HttpGet(ProjectUtils.getUrl() + "api/json?pretty=true");
        httpGet.addHeader("Authorization", getBasicAuthWithToken());

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {

            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

            JsonArray jobs = jsonResponse.getAsJsonArray("jobs");

            List<String> projectNames = new ArrayList<>();

            for (JsonElement jobElement : jobs) {
                JsonObject jobObject = jobElement.getAsJsonObject();
                String projectName = jobObject.get("name").getAsString();
                projectNames.add(projectName);
            }

            return projectNames;
        }
    }

    @Test
    public void testCreatePipeline() throws IOException {
        final List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("name", PIPELINE_NAME));
        nameValuePairs.add(new BasicNameValuePair("mode", "org.jenkinsci.plugins.workflow.job.WorkflowJob"));

        HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem/");
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {

            HttpLogger.logRequestAndResponse(httpPost, response);

            Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
            Assert.assertListContainsObject(
                    getAllProjectNamesFromJsonResponseList(),
                    PIPELINE_NAME,
                    "The project is not created");
        }
    }

    @Test
    public void testCreatePipelineXML() throws IOException {
        String queryString = "name=" + TestUtils.encodeParam(PIPELINE_NAME_BY_XML_CREATED);

        HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem?" + queryString);
        httpPost.setEntity(new StringEntity(TestUtils.readFileFromResources("create-empty-pipeline-project.xml")));

        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/xml");
        httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {

            HttpLogger.logRequestAndResponse(httpPost, response);

            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            Assert.assertListContainsObject(
                    getAllProjectNamesFromJsonResponseList(),
                    PIPELINE_NAME_BY_XML_CREATED,
                    "The project is not created");
        }
    }

    @Test(dependsOnMethods = "testCreatePipelineXML")
    public void testAddDescription() throws IOException {
        final String description = "This is a description";

        HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "job/" + PIPELINE_NAME_BY_XML_CREATED + "/submitDescription");

        httpPost.setEntity(new StringEntity("description=" + TestUtils.encodeParam(description)));

        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {

            HttpLogger.logRequestAndResponse(httpPost, response);

            Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
        }
        HttpGet httpGet = new HttpGet(ProjectUtils.getUrl() + "job/" + PIPELINE_NAME_BY_XML_CREATED + "/api/json?pretty=true");
        httpGet.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());

            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            Assert.assertTrue(jsonResponse.contains("\"description\" : \"" + description + "\""),
                    "Description not found in job details");
        }
    }

    @Test
    @Story("Folder")
    @Description("003 Create Folder with valid name")
    public void testCreateFolderWithValidNameXML() throws IOException {
        String queryString = "name=" + TestUtils.encodeParam(FOLDER_NAME_BY_XML_CREATED);

        Allure.step(String.format("Compose uri '%s' for POST request", ProjectUtils.getUrl() + "/view/all/createItem?" + queryString));
        HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "/view/all/createItem?" + queryString);

        Allure.step("Create request body from file create-empty-folder.xml");
        httpPost.setEntity(new StringEntity(TestUtils.readFileFromResources("create-empty-folder.xml")));

        Allure.step(String.format("Set header %s : application/xml", HttpHeaders.CONTENT_TYPE));
        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/xml");

        Allure.step(String.format("Set header '%s : %s'", HttpHeaders.AUTHORIZATION, getBasicAuthWithToken()));
        httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());


        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpPost)) {

            HttpLogger.logRequestAndResponse(httpPost, response);

            Allure.step("Expected result: Status code is 200");
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

            Allure.step(String.format("Expected result: '%s' is displayed on Dashboard",FOLDER_NAME_BY_XML_CREATED));
            Assert.assertListContainsObject(getAllProjectNamesFromJsonResponseList(), FOLDER_NAME_BY_XML_CREATED,
                    "The folder is not created");
        }

        Allure.step(String.format("Compose uri for GET request" + ProjectUtils.getUrl() + "job/%s/api/json",FOLDER_NAME_BY_XML_CREATED));
        HttpGet httpGet = new HttpGet(String.format(ProjectUtils.getUrl() + "job/%s/api/json",FOLDER_NAME_BY_XML_CREATED));

        Allure.step(String.format("Set header 'Authorization : %s'", getBasicAuthWithToken()));
        httpGet.addHeader("Authorization", getBasicAuthWithToken());

        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpGet)) {

            String responseBody = EntityUtils.toString(response.getEntity());
            JSONObject jsonResponse = new JSONObject(responseBody);
            String actualFullName = jsonResponse.getString("fullName");

            Allure.step("Expected result: Status code is 200");
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

            Allure.step("Expected result: fullName in json is " + FOLDER_NAME_BY_XML_CREATED);
            Assert.assertEquals(FOLDER_NAME_BY_XML_CREATED, actualFullName, "Folder didn't find");

            Allure.step("Expected result: Header 'Content-Type : application/json;charset=utf-8' ");
            Assert.assertEquals(response.getFirstHeader("Content-Type").getValue(), "application/json;charset=utf-8");
        }
    }

    @Test(dependsOnMethods = "testCreateFolderWithValidNameXML")
    @Story("Folder")
    @Description("004 Delete Folder")
    public void testDeleteFolder() throws IOException {

        Allure.step(String.format("Compose uri '%s' for DELETE request", String.format(ProjectUtils.getUrl() + "job/'%s'/", FOLDER_NAME_BY_XML_CREATED)));
        HttpDelete httpDelete = new HttpDelete(String.format(ProjectUtils.getUrl() + "job/%s/", FOLDER_NAME_BY_XML_CREATED));

        Allure.step(String.format("Set header '%s : %s'", HttpHeaders.AUTHORIZATION, getBasicAuthWithToken()));
        httpDelete.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpDelete)) {

            Allure.step("Expected result: Status code is 204");
            Assert.assertEquals(response.getStatusLine().getStatusCode(), 204);

            Allure.step(String.format("Expected result: '%s' is not displayed on Dashboard",FOLDER_NAME_BY_XML_CREATED));
            Assert.assertListNotContainsObject(getAllProjectNamesFromJsonResponseList(), FOLDER_NAME_BY_XML_CREATED,
                    "The folder is not deleted");
        }

        Allure.step(String.format("Compose uri '%s' for GET request", String.format(ProjectUtils.getUrl() + "job/'%s'/api/json", FOLDER_NAME_BY_XML_CREATED)));
        HttpGet httpGet = new HttpGet(String.format(ProjectUtils.getUrl() + "job/%s/api/json", FOLDER_NAME_BY_XML_CREATED));

        Allure.step(String.format("Set header '%s : %s'", HttpHeaders.AUTHORIZATION, getBasicAuthWithToken()));
        httpGet.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {

            Assert.assertEquals(response.getStatusLine().getStatusCode(), 404);
        }
    }
}