package school.redrover;

import com.google.common.net.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import school.redrover.runner.ProjectUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class APIJenkinsTest {

    private static final String PIPELINE_NAME = "Pipeline";

    private String crumb;
    private String crumbRequestField;
    private String token;

    @BeforeMethod
    private void getToken() {
        try {
            String encodedXpath = URLEncoder.encode("concat(//crumbRequestFields,\":\",//crumb)", StandardCharsets.UTF_8);

            String url = ProjectUtils.getUrl() + "crumbIssuer/api/json?xpath=" + encodedXpath;

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

                HttpGet httpGet = new HttpGet(url);

                httpGet.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithPassword());

                try (CloseableHttpResponse response = httpClient.execute(httpGet)) {

                    String jsonString = EntityUtils.toString(response.getEntity());
                    System.out.println("Response: " + jsonString);

                    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200, "Status code is not 200");

                    Assert.assertNotNull(jsonString, "Response body is null");
                    Assert.assertTrue(jsonString.contains(":"), "Crumb format is incorrect");

                    JSONObject jsonResponse = new JSONObject(jsonString);
                    crumb = jsonResponse.getString("crumb");
                    crumbRequestField = jsonResponse.getString("crumbRequestField");

                    Assert.assertNotNull(crumb, "Crumb is not initialized!");
                    Assert.assertNotNull(crumbRequestField, "Crumb Request Field is not initialized!");
                    System.out.println("Crumb: " + crumb);

                    HttpPost httpPost = new HttpPost(
                            ProjectUtils.getUrl() + "me/descriptorByName/jenkins.security.ApiTokenProperty/generateNewToken");

                    httpPost.addHeader("Jenkins-Crumb", crumb);
                    httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithPassword());

                    try (CloseableHttpResponse postResponse = httpClient.execute(httpPost)) {
                        Assert.assertEquals(postResponse.getStatusLine().getStatusCode(), 200, "Status code is not 200");

                        String postResponseBody = EntityUtils.toString(postResponse.getEntity(), StandardCharsets.UTF_8);
                        System.out.println("Token Response: " + postResponseBody);

                        JSONObject postJsonResponse = new JSONObject(postResponseBody);
                        if (postJsonResponse.has("data") && postJsonResponse.getJSONObject("data").has("tokenValue")) {
                            token = postJsonResponse.getJSONObject("data").getString("tokenValue");
                            System.out.println("New Token: " + token);
                        } else {
                            System.out.println("Token not found in response.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("IOException occurred: " + e.getMessage());
        }
    }

    @Test
    public void testCreatePipeline() throws IOException {
        HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem/");

        final List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("name", PIPELINE_NAME));
        nameValuePairs.add(new BasicNameValuePair("mode", "org.jenkinsci.plugins.workflow.job.WorkflowJob"));

        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {

            Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
        }
    }

    private String getBasicAuthWithPassword() {
        String username = ProjectUtils.getUserName();
        String password = ProjectUtils.getPassword();
        String auth = username + ":" + password;
        return "Basic " + java.util.Base64.getEncoder().encodeToString(auth.getBytes());
    }

    private String getBasicAuthWithToken() {
        String username = ProjectUtils.getUserName();
        String password = token;
        String auth = username + ":" + password;
        return "Basic " + java.util.Base64.getEncoder().encodeToString(auth.getBytes());
    }
}