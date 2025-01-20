package school.redrover;

import com.google.common.net.HttpHeaders;
import io.qameta.allure.Epic;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseAPIHttpTest;
import school.redrover.runner.HttpLogger;
import school.redrover.runner.ProjectUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Epic("Http API  Requests")
public class APIHttpTest extends BaseAPIHttpTest {

    private static final String PIPELINE_NAME = "Pipeline";

    @Test
    public void testCreatePipeline() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("name", PIPELINE_NAME);
        params.put("mode", "org.jenkinsci.plugins.workflow.job.WorkflowJob");

        List<NameValuePair> nameValuePairs = params.entrySet()
                .stream()
                .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem/");
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {

            HttpLogger.logRequestAndResponse(httpPost, response);

            Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
        }
    }
}

