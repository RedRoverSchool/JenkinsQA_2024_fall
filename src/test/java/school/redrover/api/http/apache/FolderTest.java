package school.redrover.api.http.apache;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import io.qameta.allure.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.models.ProjectListResponse;
import school.redrover.models.ProjectResponse;
import school.redrover.runner.*;
import school.redrover.testdata.JobTestData;
import school.redrover.testdata.TestDataProvider;

import java.io.IOException;

import static school.redrover.runner.TestApiUtils.toXML;

@Epic("Apache HttpClient API Requests")
@Feature("04 Folder")
public class FolderTest extends BaseAPIHttpTest {
    private static final String FOLDER_NAME_BY_XML_CREATED = "FolderXML";
    private static final String FOLDER_NAME = "Folder";
    private static final String FOLDER_NEW_NAME = "NewFolderName";
    private static final String FOLDER_NAME_COPY_FROM = "FolderCopyFrom";
    private static final String FOLDER_MODE = "com.cloudbees.hudson.plugins.folder.Folder";

    private void deleteItem(String name) throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {

            Allure.step("Send DELETE request -> Delete Folder");
            HttpDelete httpDelete = new HttpDelete(String.format(ProjectUtils.getUrl() + "job/%s/", name));

            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {

                Allure.step("Expected result: Delete item status code is 204");
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 204);
            }
        }
    }

    @Test
    @Story("US_00.006 Create Folder")
    @Description("00.006.03 Create Folder with valid name (XML)")
    public void testCreateFolderWithValidNameXML() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {
            String queryString = "name=" + FOLDER_NAME_BY_XML_CREATED;

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "/view/all/createItem?" + queryString);

            httpPost.setEntity(new StringEntity(toXML(JobTestData.getDefaultFolder())));
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/xml");

            Allure.step("Send POST request -> Create Folder");
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Allure.step("Expected result: Create item status code is 200");
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

                Allure.step(String.format("Expected result: '%s' is displayed on Dashboard", FOLDER_NAME_BY_XML_CREATED));
                Assert.assertListContainsObject(
                        TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(),
                        FOLDER_NAME_BY_XML_CREATED, "The folder is not created");
            }

            Allure.step("Send GET request -> Get item by name");
            HttpGet httpGet = new HttpGet(getItemByNameURL(FOLDER_NAME_BY_XML_CREATED));

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String responseBody = EntityUtils.toString(response.getEntity());

                ProjectResponse projectResponse = new Gson().fromJson(responseBody, ProjectResponse.class);

                Allure.step("Expected result: Status code is 200");
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

                Allure.step("Expected result: fullName in json is " + FOLDER_NAME_BY_XML_CREATED);
                Assert.assertEquals(projectResponse.getFullName(), FOLDER_NAME_BY_XML_CREATED,
                        "Folder didn't find");

                Allure.step("Send GET request -> Get project list from Dashboard");
                HttpGet getItemList = new HttpGet(ProjectUtils.getUrl() + "api/json");

                try (CloseableHttpResponse getItemListResponse = httpClient.execute(getItemList)) {
                    String getItemListResponseBody = EntityUtils.toString(getItemListResponse.getEntity());
                    ProjectListResponse projectListResponse = new Gson().fromJson(getItemListResponseBody, ProjectListResponse.class);

                    boolean findFolderNameInProjectList = projectListResponse.getJobs()
                            .stream().anyMatch(project -> project.getName().equals(FOLDER_NAME_BY_XML_CREATED));

                    Allure.step("Expected result: Folder name found in the list");
                    Assert.assertTrue(findFolderNameInProjectList, "Folder name not found in the list");
                }
            }
        }
    }

    @Test
    @Story("US_00.006 Create Folder")
    @Description("00.006.02 Create Folder with valid name")
    public void testCreateFolderWithValidName() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {

            Allure.step("Send POST request -> Create Folder");
            HttpPost postCreateItem = new HttpPost(getCreateItemURL());
            postCreateItem.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            postCreateItem.setEntity(new StringEntity(getCreateItemBody(FOLDER_NAME, FOLDER_MODE)));

            try (CloseableHttpResponse postCreateItemResponse = httpClient.execute(postCreateItem)) {
                Allure.step("Expected result: Successful item creation. Status code 302");
                Assert.assertEquals(postCreateItemResponse.getStatusLine().getStatusCode(), 302);
            }

            Allure.step("Send GET request -> Get item by name");
            HttpGet getItemByName = new HttpGet(getItemByNameURL(FOLDER_NAME));

            try (CloseableHttpResponse getItemByNameResponse = httpClient.execute(getItemByName)) {
                Allure.step("Expected result: Created element is found by name");
                Assert.assertEquals(getItemByNameResponse.getStatusLine().getStatusCode(), 200);

                String jsonResponse = EntityUtils.toString(getItemByNameResponse.getEntity());
                ProjectResponse projectResponse = new Gson().fromJson(jsonResponse, ProjectResponse.class);

                Allure.step(String.format("Expected result: fullName is '%s' response", FOLDER_NAME));
                Assert.assertEquals(projectResponse.getFullName(), FOLDER_NAME, "Folder didn't find");
                Allure.step("Expected result: description is null");
                Assert.assertNull(projectResponse.getDescription());
                Allure.step(String.format("Expected result: Field '_class': %s", FOLDER_MODE));
                Assert.assertEquals(projectResponse.get_class(), FOLDER_MODE);

                Allure.step("Send GET request -> Get project list from Dashboard");
                HttpGet getItemList = new HttpGet(ProjectUtils.getUrl() + "api/json");

                try (CloseableHttpResponse getItemListResponse = httpClient.execute(getItemList)) {
                    String getItemListResponseBody = EntityUtils.toString(getItemListResponse.getEntity());
                    ProjectListResponse projectListResponse = new Gson().fromJson(getItemListResponseBody, ProjectListResponse.class);

                    boolean findFolderNameInProjectList = projectListResponse.getJobs()
                            .stream().anyMatch(project -> project.getName().equals(FOLDER_NAME));

                    Allure.step("Expected result: Project name found in the list");
                    Assert.assertTrue(findFolderNameInProjectList, "Project name was not found in the list");
                }
            }
        }
    }

    @Test
    @Story("US_00.006 Create Folder")
    @Description("00.006.15 Create Folder with empty name")
    public void testCreateFolderWithEmptyName() throws IOException {
        try (CloseableHttpClient httpclient = createHttpClientWithTokenAuthAndAllureLogging()) {

            Allure.step("Send POST request -> Create Folder with Empty name");
            HttpPost postCreateItem = new HttpPost((getCreateItemURL()));
            postCreateItem.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            postCreateItem.setEntity(new StringEntity(getCreateItemBody("", FOLDER_MODE)));

            try (CloseableHttpResponse postCreateItemResponse = httpclient.execute(postCreateItem)) {
                Allure.step("Expected result: Create item status code is 400");
                Assert.assertEquals(postCreateItemResponse.getStatusLine().getStatusCode(), 400);
                Allure.step("Expected result: Header 'X-Error' : 'No name is specified'");
                Assert.assertEquals(postCreateItemResponse.getFirstHeader("X-Error").getValue(), "No name is specified");
            }
        }
    }

    @Test
    @Story("US_00.006 Create Folder")
    @Description("00.006.16 Create Folder by copy from another folder")
    public void testCreateFolderCopyFrom() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {

            Allure.step("Send POST request -> Create Folder");
            HttpPost postCreateItem = new HttpPost((getCreateItemURL()));
            postCreateItem.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            postCreateItem.setEntity(new StringEntity(getCreateItemBody(FOLDER_NAME, FOLDER_MODE)));

            try (CloseableHttpResponse postCreateItemResponse = httpClient.execute(postCreateItem)) {
                Allure.step("Expected result: Successful item creation. Status code 302");
                Assert.assertEquals(postCreateItemResponse.getStatusLine().getStatusCode(), 302);
            }

            Allure.step("Send POST request -> Create Folder copy from another folder");
            HttpPost postCreateItemCopyFrom = new HttpPost(getCreateItemURL());
            postCreateItemCopyFrom.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            postCreateItemCopyFrom.setEntity(new StringEntity(getCreateItemCopyFromBody(FOLDER_NAME_COPY_FROM, FOLDER_NAME)));

            try (CloseableHttpResponse postCreateItemCopyFromResponse = httpClient.execute(postCreateItemCopyFrom)) {

                Allure.step("Expected result: Successful item creation. Status code 302");
                Assert.assertEquals(postCreateItemCopyFromResponse.getStatusLine().getStatusCode(), 302);
            }

            Allure.step("Send GET request -> Get item by name");
            HttpGet getItemByName = new HttpGet(getItemByNameURL(FOLDER_NAME_COPY_FROM));

            try (CloseableHttpResponse getItemByNameResponse = httpClient.execute(getItemByName)) {
                Allure.step("Expected result: Created element is found by name");
                Assert.assertEquals(getItemByNameResponse.getStatusLine().getStatusCode(), 200);

                String jsonResponse = EntityUtils.toString(getItemByNameResponse.getEntity());
                ProjectResponse projectResponse = new Gson().fromJson(jsonResponse, ProjectResponse.class);

                Allure.step(String.format("Expected result: fullName is '%s' response", FOLDER_NAME_COPY_FROM));
                Assert.assertEquals(projectResponse.getFullName(), FOLDER_NAME_COPY_FROM, "Folder didn't find");
                Allure.step("Expected result: description is null");
                Assert.assertNull(projectResponse.getDescription());
                Allure.step(String.format("Expected result: Field '_class': %s", FOLDER_MODE));
                Assert.assertEquals(projectResponse.get_class(), FOLDER_MODE);
            }

            Allure.step("Send GET request -> Get project list from Dashboard");
            HttpGet getItemList = new HttpGet(ProjectUtils.getUrl() + "api/json");

            try (CloseableHttpResponse getItemListResponse = httpClient.execute(getItemList)) {
                String getItemListResponseBody = EntityUtils.toString(getItemListResponse.getEntity());
                ProjectListResponse projectListResponse = new Gson().fromJson(getItemListResponseBody, ProjectListResponse.class);

                boolean findFolderNameInProjectList = projectListResponse.getJobs()
                        .stream().anyMatch(project -> project.getName().equals(FOLDER_NAME_COPY_FROM));

                Allure.step("Expected result: Project name found in the list");
                Assert.assertTrue(findFolderNameInProjectList, "Project name was not found in the list");
            }
            deleteItem(FOLDER_NAME_COPY_FROM);
            deleteItem(FOLDER_NAME);
        }
    }

    @Test(dataProvider = "providerUnsafeCharacters", dataProviderClass = TestDataProvider.class)
    @Story("US_00.006 Create Folder")
    @Description("00.006.17 Create Folder with unsafe character")
    public void testCreateFolderWithUnsafeCharacter(String unsafeCharacter) throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {

            Allure.step("Send POST request -> Create Folder");
            HttpPost postCreateItem = new HttpPost((getCreateItemURL()));
            postCreateItem.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            postCreateItem.setEntity(new StringEntity(getCreateItemBody(unsafeCharacter, FOLDER_MODE)));

            try (CloseableHttpResponse postCreateItemResponse = httpClient.execute(postCreateItem)) {
                Allure.step("Expected result: Failed item creation. Status code 400");
                Assert.assertEquals(postCreateItemResponse.getStatusLine().getStatusCode(), 400);
                Allure.step(String.format("Expected result: Header 'X-Error' : '%s' is an unsafe character", unsafeCharacter));
                Assert.assertEquals(
                        postCreateItemResponse.getFirstHeader("X-Error").getValue(),
                        String.format("%s  is an unsafe character", unsafeCharacter));
            }
        }
    }

    @Test(dependsOnMethods = "testCreateFolderWithValidName")
    @Story("US_04.001 Rename Folder")
    @Description("04.001.08 Rename Folder")
    public void testRenameFolder() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {

            Allure.step("Send POST request -> Rename Folder");
            HttpPost postRenameItem = new HttpPost(getRenameItemURL(FOLDER_NAME));
            postRenameItem.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            postRenameItem.setEntity(new StringEntity(getRenameItemBody(FOLDER_NEW_NAME)));

            try (CloseableHttpResponse postRenameItemResponse = httpClient.execute(postRenameItem)) {
                Allure.step("Expected result: Item renamed successful. Status code 302");
                Assert.assertEquals(postRenameItemResponse.getStatusLine().getStatusCode(), 302);

                Allure.step(String.format("Expected result: '%s' is displayed on Dashboard", FOLDER_NEW_NAME));
                Assert.assertListContainsObject(TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(), FOLDER_NEW_NAME,
                        "List is not contain folder");
            }
            deleteItem(FOLDER_NEW_NAME);
        }
    }

    @Test()
    @Story("US_04.004 Add and edit description of the folder")
    @Description("04.004.07 Add Description to Folder")
    public void testAddDescriptionToFolder() throws IOException {
        String description = "Add description to folder!";
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {

            Allure.step("Send POST request -> Create Folder");
            HttpPost postCreateItem = new HttpPost(getCreateItemURL());
            postCreateItem.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            postCreateItem.setEntity(new StringEntity(getCreateItemBody(FOLDER_NAME, FOLDER_MODE)));

            try (CloseableHttpResponse postCreateItemResponse = httpClient.execute(postCreateItem)) {
                Allure.step("Expected result: Successful item creation. Status code 302");
                Assert.assertEquals(postCreateItemResponse.getStatusLine().getStatusCode(), 302);
            }

            Allure.step("Send POST request -> Add Description to Folder");
            HttpPost postAddDescriptionItem = new HttpPost(getAddDescriptionURL(FOLDER_NAME));
            postAddDescriptionItem.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            postAddDescriptionItem.setEntity(new StringEntity(getAddDescriptionBody(description)));

            try (CloseableHttpResponse postAddDescriptionResponse = httpClient.execute(postAddDescriptionItem)) {
                Allure.step("Expected result: Successful add description to item. Status code 302");
                Assert.assertEquals(postAddDescriptionResponse.getStatusLine().getStatusCode(), 302);
            }

            Allure.step("Send GET request -> Get item by name");
            HttpGet getItemByName = new HttpGet(getItemByNameURL(FOLDER_NAME));

            try (CloseableHttpResponse getItemByNameResponse = httpClient.execute(getItemByName)) {
                Allure.step("Expected result: Created element is found by name");
                Assert.assertEquals(getItemByNameResponse.getStatusLine().getStatusCode(), 200);

                String jsonResponse = EntityUtils.toString(getItemByNameResponse.getEntity());

                ProjectResponse projectResponse = new Gson().fromJson(jsonResponse, ProjectResponse.class);

                Allure.step(String.format("(ERR) Expected result: description is '%s'", description));
                Assert.assertNull(projectResponse.getDescription());

            }
            deleteItem(FOLDER_NAME);
        }
    }

    @Test(dependsOnMethods = "testCreateFolderWithValidNameXML")
    @Story("US_04.003 Delete Folder")
    @Description("04.003.04 Delete Folder")
    public void testDeleteFolder() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {

            Allure.step("Send DELETE request -> Delete Folder");
            HttpDelete httpDelete = new HttpDelete(String.format(ProjectUtils.getUrl() + "job/%s/", FOLDER_NAME_BY_XML_CREATED));

            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {

                Allure.step("Expected result: Delete item status code is 204");
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 204);

                Allure.step(String.format("Expected result: '%s' is not displayed on Dashboard", FOLDER_NAME_BY_XML_CREATED));
                Assert.assertListNotContainsObject(TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(), FOLDER_NAME_BY_XML_CREATED,
                        "The folder is not deleted");
            }

            Allure.step("Send GET request -> Get item by name");
            HttpGet httpGet = new HttpGet(getItemByNameURL(FOLDER_NAME_BY_XML_CREATED));

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {

                Allure.step("Expected result: Item not found. Status code is 404");
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 404);
            }
        }
    }
}
