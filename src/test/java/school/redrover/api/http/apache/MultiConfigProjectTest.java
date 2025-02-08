package school.redrover.api.http.apache;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.ProjectListResponse;
import school.redrover.model.ProjectResponse;
import school.redrover.runner.BaseAPIHttpTest;

import java.io.IOException;

@Epic("Apache HttpClient API Requests")
public class MultiConfigProjectTest extends BaseAPIHttpTest {

    private static final String MULTI_CONFIGURATION_NAME = "MultiConfigurationProject";
    private static final String MULTI_CONFIGURATION_MODE = "hudson.matrix.MatrixProject";

    @Test
    @Story("Multi-Configuration project")
    @Description("00.003.09 Create Multi-Configuration project with valid name")
    public void testCreateMultiConfiguration() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {

            HttpPost postCreateItem = new HttpPost(getCreateItemURL());
            postCreateItem.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            postCreateItem.setEntity(new StringEntity(getCreateItemBody(MULTI_CONFIGURATION_NAME, MULTI_CONFIGURATION_MODE)));

            try (CloseableHttpResponse postCreateItemResponse = httpClient.execute(postCreateItem)) {
                Allure.step("Expected result: Successful item creation. Status code 302");
                Assert.assertEquals(postCreateItemResponse.getStatusLine().getStatusCode(), 302);
            }

            Allure.step("Send GET request -> Get item by name");
            HttpGet getItemByName = new HttpGet(getItemByNameURL(MULTI_CONFIGURATION_NAME));

            try (CloseableHttpResponse getItemByNameResponse = httpClient.execute(getItemByName)) {
                Allure.step("Expected result: Created element is found by name");
                Assert.assertEquals(getItemByNameResponse.getStatusLine().getStatusCode(), 200);

                String jsonResponse = EntityUtils.toString(getItemByNameResponse.getEntity());
                ProjectResponse projectResponse = new Gson().fromJson(jsonResponse, ProjectResponse.class);

                Allure.step(String.format("Expected result: fullName is '%s' response", MULTI_CONFIGURATION_NAME));
                Assert.assertEquals(projectResponse.getFullName(), MULTI_CONFIGURATION_NAME, "Folder didn't find");
                Allure.step("Expected result: description is null");
                Assert.assertNull(projectResponse.getDescription());
                Allure.step(String.format("Expected result: Field '_class': %s", MULTI_CONFIGURATION_MODE));
                Assert.assertEquals(projectResponse.get_class(), MULTI_CONFIGURATION_MODE);
            }

            Allure.step("Send GET request -> Get project list from Dashboard");
            HttpGet getItemList = new HttpGet(getAllProjectList());

            try (CloseableHttpResponse getItemListResponse = httpClient.execute(getItemList)) {
                String getItemListResponseBody = EntityUtils.toString(getItemListResponse.getEntity());
                ProjectListResponse projectListResponse = new Gson().fromJson(getItemListResponseBody, ProjectListResponse.class);

                boolean findFolderNameInProjectList = projectListResponse.getJobs()
                        .stream().anyMatch(project -> project.getName().equals(MULTI_CONFIGURATION_NAME));

                Allure.step("Expected result: Project name found in the list");
                Assert.assertTrue(findFolderNameInProjectList, "Project name was not found in the list");
            }
        }
    }
}
