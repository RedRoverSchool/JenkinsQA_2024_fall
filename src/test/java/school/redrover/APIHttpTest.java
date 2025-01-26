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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseAPIHttpTest;
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
    private static final String FREESTYLE_PROJECT = "NewProject";
    private static final String RENAMED_FREESTYLE_PROJECT = "RenamedFreestyle";

    private List<String> getAllProjectNamesFromJsonResponseList() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            HttpGet httpGet = new HttpGet(ProjectUtils.getUrl() + "api/json?pretty=true");

            httpGet.addHeader("Authorization", getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {

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
    }

    @Story("Pipeline project")
    @Description("Create Pipeline Project with valid name")
    @Test
    public void testCreatePipeline() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem/");

            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("name", PIPELINE_NAME));
            nameValuePairs.add(new BasicNameValuePair("mode", "org.jenkinsci.plugins.workflow.job.WorkflowJob"));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
                Assert.assertListContainsObject(
                        getAllProjectNamesFromJsonResponseList(),
                        PIPELINE_NAME,
                        "The project is not created");
            }
        }
    }

    @Story("Pipeline project")
    @Description("Create Pipeline Project with valid name(XML")
    @Test
    public void testCreatePipelineXML() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            String queryString = "name=" + TestUtils.encodeParam(PIPELINE_NAME_BY_XML_CREATED);

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem?" + queryString);
            httpPost.setEntity(new StringEntity(TestUtils.readFileFromResources("create-empty-pipeline-project.xml")));

            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/xml");
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                Assert.assertListContainsObject(
                        getAllProjectNamesFromJsonResponseList(),
                        PIPELINE_NAME_BY_XML_CREATED,
                        "The project is not created");
            }
        }
    }

    @Story("Pipeline project")
    @Description("Add description to Pipeline project")
    @Test(dependsOnMethods = "testCreatePipelineXML")
    public void testAddDescription() throws IOException {
        final String description = "This is a description";

        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            HttpPost httpPost = new HttpPost(
                    String.format(ProjectUtils.getUrl() + "job/%s/submitDescription", PIPELINE_NAME_BY_XML_CREATED));

            httpPost.setEntity(new StringEntity("description=" + TestUtils.encodeParam(description)));

            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
            }
            HttpGet httpGet = new HttpGet(
                    String.format(ProjectUtils.getUrl() + "job/%s/api/json?pretty=true", PIPELINE_NAME_BY_XML_CREATED));
            httpGet.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                Assert.assertTrue(jsonResponse.contains("\"description\" : \"" + description + "\""),
                        "Description not found in job details");
            }
        }
    }

    @Test
    @Story("Folder")
    @Description("003 Create Folder with valid name (XML)")
    public void testCreateFolderWithValidNameXML() throws IOException {
        String queryString = "name=" + TestUtils.encodeParam(FOLDER_NAME_BY_XML_CREATED);

        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "/view/all/createItem?" + queryString);
            httpPost.setEntity(new StringEntity(TestUtils.readFileFromResources("create-empty-folder.xml")));

            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/xml");
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());


            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
        Allure.step("Send POST request -> Create Folder");
        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpPost)) {

            HttpLogger.logRequestAndResponse(httpPost, response);

                Allure.step("Expected result: Create item status code is 200");
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

                Allure.step(String.format("Expected result: '%s' is displayed on Dashboard", FOLDER_NAME_BY_XML_CREATED));
                Assert.assertListContainsObject(getAllProjectNamesFromJsonResponseList(), FOLDER_NAME_BY_XML_CREATED,
                        "The folder is not created");
            }

            HttpGet httpGet = new HttpGet(String.format(ProjectUtils.getUrl() + "job/%s/api/json", FOLDER_NAME_BY_XML_CREATED));

            httpGet.addHeader("Authorization", getBasicAuthWithToken());
        HttpGet httpGet = new HttpGet(String.format(ProjectUtils.getUrl() + "job/%s/api/json",FOLDER_NAME_BY_XML_CREATED));
        httpGet.addHeader("Authorization", getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
        Allure.step("Send GET request -> Get item by name");
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
    }

    @Test(dependsOnMethods = "testCreateFolderWithValidNameXML")
    @Story("Folder")
    @Description("004 Delete Folder")
    public void testDeleteFolder() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {

            HttpDelete httpDelete = new HttpDelete(String.format(ProjectUtils.getUrl() + "job/%s/", FOLDER_NAME_BY_XML_CREATED));
            httpDelete.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
        Allure.step("Send DELETE request -> Delete Folder");
        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpDelete)) {

                Allure.step("Expected result: Delete item status code is 204");
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 204);

                Allure.step(String.format("Expected result: '%s' is not displayed on Dashboard", FOLDER_NAME_BY_XML_CREATED));
                Assert.assertListNotContainsObject(getAllProjectNamesFromJsonResponseList(), FOLDER_NAME_BY_XML_CREATED,
                        "The folder is not deleted");
            }

            HttpGet httpGet = new HttpGet(String.format(ProjectUtils.getUrl() + "job/%s/api/json", FOLDER_NAME_BY_XML_CREATED));
            httpGet.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
        Allure.step("Send GET request -> Get item by name");
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {

                Allure.step("Expected result: Item not found. Status code is 404");
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 404);
            }
        }
    }

    @Story("Freestyle Project")
    @Description("Create Freestyle Project with valid name")
    @Test
    public void testCreateFreestyleProject() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            String query = "name=" + TestUtils.encodeParam(FREESTYLE_PROJECT);

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem?" + query);
            httpPost.setEntity(new StringEntity(TestUtils.readFileFromResources("create-empty-freestyle-project.xml")));

            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/xml");
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                Assert.assertListContainsObject(
                        getAllProjectNamesFromJsonResponseList(),
                        FREESTYLE_PROJECT,
                        "The project is not created");
            }
        }
    }

    @Story("Freestyle Project")
    @Description("Rename Freestyle Project with valid name")
    @Test(dependsOnMethods = "testCreateFreestyleProject")
    public void testRenameFreestyleProject() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            String renameUrl = ProjectUtils.getUrl() + "job/" + FREESTYLE_PROJECT + "/doRename";

            HttpPost httpPost = new HttpPost(renameUrl);
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("newName", RENAMED_FREESTYLE_PROJECT));
            httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);

                Assert.assertListContainsObject(
                        getAllProjectNamesFromJsonResponseList(),
                        RENAMED_FREESTYLE_PROJECT,
                        "The project was not renamed");
            }
        }
    }

    @Story("Freestyle Project")
    @Description("Delete Freestyle project")
    @Test(dependsOnMethods = "testRenameFreestyleProject")
    public void testDeleteFreestyleProject() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            String deleteUrl = ProjectUtils.getUrl() + "job/" + RENAMED_FREESTYLE_PROJECT + "/";

            HttpDelete httpDelete = new HttpDelete(deleteUrl);

            httpDelete.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            httpDelete.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 204);
            }

            HttpGet httpGet = new HttpGet(ProjectUtils.getUrl() + "job/" + RENAMED_FREESTYLE_PROJECT + "/api/json");
            httpGet.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 404);
            }
        }
    }
}