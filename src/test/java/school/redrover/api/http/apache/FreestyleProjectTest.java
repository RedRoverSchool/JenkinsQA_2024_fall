package school.redrover.api.http.apache;

import com.google.common.net.HttpHeaders;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseAPIHttpTest;
import school.redrover.runner.ProjectUtils;
import school.redrover.runner.TestApiHttpUtils;
import school.redrover.runner.TestUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Epic("Apache HttpClient API Requests")
public class FreestyleProjectTest extends BaseAPIHttpTest {

    private static final String FREESTYLE_PROJECT = "NewProject";
    private static final String RENAMED_FREESTYLE_PROJECT = "RenamedFreestyle";

    @Test
    @Story("Freestyle Project")
    @Description("Create Freestyle Project with valid name")
    public void testCreateFreestyleProject() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {
            String query = "name=" + TestUtils.encodeParam(FREESTYLE_PROJECT);

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem?" + query);
            httpPost.setEntity(new StringEntity(TestUtils.loadPayload("create-empty-freestyle-project.xml")));

            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/xml");

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                Assert.assertListContainsObject(
                        TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(),
                        FREESTYLE_PROJECT,
                        "The project is not created");
            }
        }
    }

    @Test(dependsOnMethods = "testCreateFreestyleProject")
    @Story("Freestyle Project")
    @Description("Rename Freestyle Project with valid name")
    public void testRenameFreestyleProject() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {
            String renameUrl = ProjectUtils.getUrl() + "job/" + FREESTYLE_PROJECT + "/doRename";

            HttpPost httpPost = new HttpPost(renameUrl);
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("newName", RENAMED_FREESTYLE_PROJECT));
            httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);

                Assert.assertListContainsObject(
                        TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(),
                        RENAMED_FREESTYLE_PROJECT,
                        "The project was not renamed");
            }
        }
    }

    @Test(dependsOnMethods = "testRenameFreestyleProject")
    @Story("Freestyle Project")
    @Description("Delete Freestyle project")
    public void testDeleteFreestyleProject() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {
            String deleteUrl = ProjectUtils.getUrl() + "job/" + RENAMED_FREESTYLE_PROJECT + "/";

            HttpDelete httpDelete = new HttpDelete(deleteUrl);

            httpDelete.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 204);
            }

            HttpGet httpGet = new HttpGet(ProjectUtils.getUrl() + "job/" + RENAMED_FREESTYLE_PROJECT + "/api/json");

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 404);
                Assert.assertListNotContainsObject(
                        TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(),
                        RENAMED_FREESTYLE_PROJECT,
                        "The project was not deleted");

            }
        }
    }
}

