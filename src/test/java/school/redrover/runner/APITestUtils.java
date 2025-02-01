package school.redrover.runner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class APITestUtils {

    private static final String API_JSON_URL = "api/json?pretty=true";

    public static List<String> getProjectNamesFromJsonResponseList(String url, String jsonArrayKey) throws IOException {
        try (CloseableHttpClient httpClient = BaseAPIHttpTest.createHttpClientWithAllureLogging()) {
            HttpGet httpGet = new HttpGet(url);

            httpGet.addHeader("Authorization", BaseAPIHttpTest.getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

                String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                JsonArray jobs = new Gson().fromJson(responseBody, JsonObject.class).getAsJsonArray(jsonArrayKey);

                List<String> projectNames = new ArrayList<>();
                jobs.forEach(job -> projectNames.add(job.getAsJsonObject().get("name").getAsString()));

                return projectNames;
            }
        }
    }

    public static List<String> getAllProjectNamesFromJsonResponseList() throws IOException {
        return getProjectNamesFromJsonResponseList(ProjectUtils.getUrl() + API_JSON_URL, "jobs");
    }

    public static List<String> getAllProjectViewNamesFromJsonResponseList() throws IOException {
        return getProjectNamesFromJsonResponseList(ProjectUtils.getUrl() + API_JSON_URL, "views");
    }
}
