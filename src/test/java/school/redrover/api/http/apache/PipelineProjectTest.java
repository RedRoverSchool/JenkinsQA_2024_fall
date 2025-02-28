package school.redrover.api.http.apache;

import com.google.common.net.HttpHeaders;
import com.google.gson.JsonObject;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.TestApiHttpUtils;
import school.redrover.runner.BaseAPIHttpTest;
import school.redrover.runner.ProjectUtils;
import school.redrover.runner.TestUtils;
import school.redrover.testdata.ListViewTestData;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static school.redrover.runner.TestApiUtils.toXML;

@Epic("Apache HttpClient API Requests")
public class PipelineProjectTest extends BaseAPIHttpTest {

    private static final String PIPELINE_NAME = "Pipeline";
    private static final String PIPELINE_NAME_BY_XML_CREATED = "PipelineXML";
    private static final String VIEW_NAME = "ViewName";

    @Test
    @Story("Pipeline project")
    @Description("Create Pipeline Project with valid name")
    public void testCreatePipeline() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {
            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem/");

            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("name", PIPELINE_NAME));
            nameValuePairs.add(new BasicNameValuePair("mode", "org.jenkinsci.plugins.workflow.job.WorkflowJob"));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
                Assert.assertListContainsObject(
                        TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(),
                        PIPELINE_NAME,
                        "The project is not created");
            }
        }
    }

    @Test(dependsOnMethods = "testCreatePipeline")
    @Story("Pipeline project")
    @Description("Disable Project")
    public void testDisableProject() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {
            HttpPost httpPost = new HttpPost(
                    String.format(ProjectUtils.getUrl() + "job/%s/configSubmit", PIPELINE_NAME));

            String jsonPayload = TestUtils.loadPayload("pipeline-configuration.json")
                    .replace("\"enable\": true", "\"enable\": false");

            String body = "json=" + URLEncoder.encode(jsonPayload, StandardCharsets.UTF_8)
                    + "&core:apply=";

            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

            HttpEntity entity = new StringEntity(body, ContentType.APPLICATION_FORM_URLENCODED);
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getStatusLine().getStatusCode();

                Assert.assertEquals(statusCode, 302);
            }
        }

        JsonObject jsonResponse = TestApiHttpUtils.getProjectJson(PIPELINE_NAME);

        Assert.assertFalse(jsonResponse.get("buildable").getAsBoolean(), "Pipeline should be disabled");

    }


    @Test(dependsOnMethods = "testDisableProject")
    @Story("View")
    @Description("Add List View for Pipeline project")
    public void testAddListViewForProject() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {
            String query = "name=" + VIEW_NAME;
            String payloadForProject = String.format(toXML(ListViewTestData.getListView(PIPELINE_NAME)));

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "createView?" + query);

            httpPost.setEntity(new StringEntity(payloadForProject));
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/xml ");

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                Assert.assertListContainsObject(
                        TestApiHttpUtils.getAllProjectViewNamesFromJsonResponseList(),
                        VIEW_NAME,
                        "View is not created");
            }
        }
    }

    @Test(dependsOnMethods = "testAddListViewForProject")
    @Story("View")
    @Description("Delete List View from project")
    public void testDeleteListView() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {
            HttpPost httpPost = new HttpPost(String.format(ProjectUtils.getUrl() + "view/%s/doDelete/", VIEW_NAME));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
                Assert.assertListNotContainsObject(
                        TestApiHttpUtils.getAllProjectViewNamesFromJsonResponseList(),
                        VIEW_NAME,
                        "View is not deleted");
            }
        }
    }

    @Test
    @Story("Pipeline project")
    @Description("Create Pipeline Project with valid name(XML")
    public void testCreatePipelineXML() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {
            String queryString = "name=" + PIPELINE_NAME_BY_XML_CREATED;

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem?" + queryString);
            httpPost.setEntity(new StringEntity(TestUtils.loadPayload("create-empty-pipeline-project.xml")));

            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/xml");

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                Assert.assertListContainsObject(
                        TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(),
                        PIPELINE_NAME_BY_XML_CREATED,
                        "The project is not created");
            }
        }
    }

    @Test(dependsOnMethods = "testCreatePipelineXML")
    @Story("Pipeline project")
    @Description("Add description to Pipeline project")
    public void testAddDescription() throws IOException {
        final String description = "This is a description";

        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {
            HttpPost httpPost = new HttpPost(
                    String.format(ProjectUtils.getUrl() + "job/%s/submitDescription", PIPELINE_NAME_BY_XML_CREATED));

            httpPost.setEntity(new StringEntity("description=" + TestUtils.encodeParam(description)));

            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
            }

            JsonObject jsonResponse = TestApiHttpUtils.getProjectJson(PIPELINE_NAME_BY_XML_CREATED);

            Assert.assertTrue(
                    jsonResponse.get("description").getAsString().contains(description),
                    "Description not found in job details"
            );
        }
    }

    @Test(dependsOnMethods = "testAddDescription")
    @Story("Pipeline project")
    @Description("Delete project by HttpDelete request")
    public void testDeleteProjectByHttpDelete() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {
            HttpDelete httpDelete = new HttpDelete(
                    String.format(ProjectUtils.getUrl() + "/job/%s/", PIPELINE_NAME_BY_XML_CREATED));

            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 204);
                Assert.assertListNotContainsObject(
                        TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(),
                        PIPELINE_NAME_BY_XML_CREATED,
                        "Project is not deleted");
            }
        }
    }

    @Test(dependsOnMethods = "testDeleteListView")
    @Story("Pipeline project")
    @Description("Delete project by HttpPost request")
    public void testDeletePipelineByHttpPost() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {
            HttpPost httpPost = new HttpPost(
                    String.format(ProjectUtils.getUrl() + "/job/%s/doDelete", PIPELINE_NAME));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
            }

            HttpGet httpGet = new HttpGet(
                    String.format(ProjectUtils.getUrl() + "/job/%s/api/json", PIPELINE_NAME));

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 404);
                Assert.assertListNotContainsObject(
                        TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(),
                        PIPELINE_NAME,
                        "Project is not deleted");
            }
        }
    }
}
