package school.redrover;

import com.google.common.net.HttpHeaders;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.qameta.allure.Epic;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
}
