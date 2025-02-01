package school.redrover.runner;

import io.qameta.allure.httpclient.AllureHttpClientRequest;
import io.qameta.allure.httpclient.AllureHttpClientResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseAPIHttpTest {

    protected String crumb;
    protected String crumbRequestField;
    protected static String token;

    protected static final Logger logger = LoggerFactory.getLogger(BaseAPIHttpTest.class);

    protected String getBasicAuthWithPassword() {
        String auth = ProjectUtils.getUserName() + ":" + ProjectUtils.getPassword();
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }

    protected static String getBasicAuthWithToken() {
        String auth = ProjectUtils.getUserName() + ":" + token;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }

    protected static CloseableHttpClient createHttpClientWithAllureLogging() {
        return HttpClientBuilder.create()
                .addInterceptorFirst(new AllureHttpClientRequest())
                .addInterceptorLast(new AllureHttpClientResponse())
                .build();
    }

    @BeforeMethod
    protected void getToken() {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            HttpGet httpGet = new HttpGet(ProjectUtils.getUrl() + "crumbIssuer/api/json");
            httpGet.addHeader("Authorization", getBasicAuthWithPassword());

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String jsonString = EntityUtils.toString(response.getEntity());
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                Assert.assertNotNull(jsonString);

                JSONObject jsonResponse = new JSONObject(jsonString);
                crumb = jsonResponse.getString("crumb");
                crumbRequestField = jsonResponse.getString("crumbRequestField");

                Assert.assertNotNull(crumb);
                Assert.assertNotNull(crumbRequestField);

                HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "me/descriptorByName/jenkins.security.ApiTokenProperty/generateNewToken");
                httpPost.addHeader("Jenkins-Crumb", crumb);
                httpPost.addHeader("Authorization", getBasicAuthWithPassword());

                try (CloseableHttpResponse postResponse = httpClient.execute(httpPost)) {
                    Assert.assertEquals(postResponse.getStatusLine().getStatusCode(), 200);

                    String postResponseBody = EntityUtils.toString(postResponse.getEntity(), StandardCharsets.UTF_8);
                    JSONObject postJsonResponse = new JSONObject(postResponseBody);
                    token = postJsonResponse.getJSONObject("data").getString("tokenValue");

                }
            }
        } catch (IOException e) {
            logger.error("IOException occurred while getting token: ", e);
            Assert.fail("IOException occurred: " + e.getMessage());
        }
    }
}

