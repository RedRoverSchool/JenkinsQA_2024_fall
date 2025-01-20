package school.redrover.runner;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseAPIHttpTest {

    protected String crumb;
    protected String crumbRequestField;
    protected String token;

   protected static final Logger logger = LoggerFactory.getLogger(BaseAPIHttpTest.class);

    protected String getBasicAuthWithPassword() {
        String auth = ProjectUtils.getUserName() + ":" + ProjectUtils.getPassword();
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }

    protected String getBasicAuthWithToken() {
        String auth = ProjectUtils.getUserName() + ":" + token;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }

    @BeforeMethod
    protected void getToken() {
        try {
            String encodedXpath = URLEncoder.encode("concat(//crumbRequestFields,\":\",//crumb)", StandardCharsets.UTF_8);
            String url = ProjectUtils.getUrl() + "crumbIssuer/api/json?xpath=" + encodedXpath;

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpGet httpGet = new HttpGet(url);
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

                    HttpPost httpPost = new HttpPost(ProjectUtils.getUrl()
                            + "me/descriptorByName/jenkins.security.ApiTokenProperty/generateNewToken");
                    httpPost.addHeader("Jenkins-Crumb", crumb);
                    httpPost.addHeader("Authorization", getBasicAuthWithPassword());

                    try (CloseableHttpResponse postResponse = httpClient.execute(httpPost)) {
                        Assert.assertEquals(postResponse.getStatusLine().getStatusCode(), 200);

                        String postResponseBody = EntityUtils.toString(postResponse.getEntity(), StandardCharsets.UTF_8);
                        JSONObject postJsonResponse = new JSONObject(postResponseBody);
                        token = postJsonResponse.getJSONObject("data").getString("tokenValue");

                        logger.info("Crumb: " + "*".repeat(crumb.length() - 5) + crumb.substring(crumb.length() - 5));
                        logger.info("Token: " + "*".repeat(token.length() - 5) + token.substring(token.length() - 5));
                    }
                }
            }
        } catch (IOException e) {
            logger.error("IOException occurred while getting token: ", e);
            Assert.fail("IOException occurred: " + e.getMessage());
        }
    }
}

