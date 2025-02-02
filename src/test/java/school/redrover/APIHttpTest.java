package school.redrover;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import io.qameta.allure.*;
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
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.ProjectResponse;
import school.redrover.model.ProjectListResponse;
import school.redrover.runner.TestApiHttpUtils;
import school.redrover.runner.BaseAPIHttpTest;
import school.redrover.runner.ProjectUtils;
import school.redrover.runner.TestUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Epic("Http API  Requests")
public class APIHttpTest extends BaseAPIHttpTest {  // Using Apache HttpClient

    private static final String FOLDER_NAME_BY_XML_CREATED = "FolderXML";
    private static final String FOLDER_NAME = "Folder";
    private static final String FOLDER_NEW_NAME = "NewFolderName";
    private static final String FOLDER_MODE = "com.cloudbees.hudson.plugins.folder.Folder";
    private static final String FREESTYLE_PROJECT = "NewProject";
    private static final String RENAMED_FREESTYLE_PROJECT = "RenamedFreestyle";
    private static final String MULTIBRANCH_PIPELINE_NAME = "MultibranchPipeline";
    private static final String MULTIBRANCH_PIPELINE_NAME_XML = "MultibranchPipelineXML";
    private static final String MULTI_CONFIGURATION_PROJECT = "MultiConfigurationProject";
    private static final String MULTI_CONFIGURATION_MODE = "hudson.matrix.MatrixProject";


    private String getCreateItemBody(String name, String mode) {
        return "name=" + TestUtils.encodeParam(name) + "&mode=" + mode;
    }

    private String getRenameItemBody(String name) {
        return "newName=" + TestUtils.encodeParam(name);
    }

    private String getAddDescriptionBody(String description) {
        return "description=" + TestUtils.encodeParam(description);

    }

    private String getItemByNameURL(String name) {
        return ProjectUtils.getUrl() + String.format("job/%s/api/json", TestUtils.encodeParam(name));
    }

    private String getCreateItemURL() {
        return ProjectUtils.getUrl() + "createItem";
    }

    private String getRenameItemURL(String name) {
        return ProjectUtils.getUrl() + String.format("job/%s/confirmRename", TestUtils.encodeParam(name));
    }

    private String getAddDescriptionURL(String name) {
        return ProjectUtils.getUrl() + String.format("job/%s/submitDescription", TestUtils.encodeParam(name));
    }

    @Test
    @Story("Folder")
    @Description("003 Create Folder with valid name (XML)")
    public void testCreateFolderWithValidNameXML() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            String queryString = "name=" + FOLDER_NAME_BY_XML_CREATED;

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "/view/all/createItem?" + queryString);

            httpPost.setEntity(new StringEntity(TestUtils.loadPayload("create-empty-folder.xml")));
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/xml");
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            Allure.step("Send POST request -> Create Folder");
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Allure.step("Expected result: Create item status code is 200");
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

                Allure.step(String.format("Expected result: '%s' is displayed on Dashboard", FOLDER_NAME_BY_XML_CREATED));
                Assert.assertListContainsObject(TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(), FOLDER_NAME_BY_XML_CREATED,
                        "The folder is not created");
            }

            HttpGet httpGet = new HttpGet(getItemByNameURL(FOLDER_NAME_BY_XML_CREATED));
            httpGet.addHeader("Authorization", getBasicAuthWithToken());

            Allure.step("Send GET request -> Get item by name");
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String responseBody = EntityUtils.toString(response.getEntity());

                ProjectResponse projectResponse = new Gson().fromJson(responseBody, ProjectResponse.class);

                Allure.step("Expected result: Status code is 200");
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

                Allure.step("Expected result: fullName in json is " + FOLDER_NAME_BY_XML_CREATED);
                Assert.assertEquals(projectResponse.getFullName(), FOLDER_NAME_BY_XML_CREATED, "Folder didn't find");

                Allure.step("Send GET request -> Get project list from Dashboard");
                HttpGet getItemList = new HttpGet(ProjectUtils.getUrl() + "api/json");
                getItemList.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

                try (CloseableHttpResponse getItemListResponse = httpClient.execute(getItemList)) {
                    String getItemListResponseBody = EntityUtils.toString(getItemListResponse.getEntity());
                    ProjectListResponse projectListResponse = new Gson().fromJson(getItemListResponseBody, ProjectListResponse.class);

                    boolean findFolderNameInProjectList = projectListResponse.getJobs()
                            .stream().anyMatch(project -> project.getName().equals(FOLDER_NAME_BY_XML_CREATED));

                    Allure.step("Expected result: Project name found in the list");
                    Assert.assertTrue(findFolderNameInProjectList, "Project name was not found in the list");
                }
            }
        }
    }

    @Test
    @Story("Folder")
    @Description("002 Create Folder with valid name")
    public void testCreateFolderWithValidName() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {

            Allure.step("Send POST request -> Create Folder");
            HttpPost postCreateItem = new HttpPost(getCreateItemURL());
            postCreateItem.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());
            postCreateItem.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            postCreateItem.setEntity(new StringEntity(getCreateItemBody(FOLDER_NAME, FOLDER_MODE)));

            try (CloseableHttpResponse postCreateItemResponse = httpClient.execute(postCreateItem)) {
                Allure.step("Expected result: Successful item creation. Status code 302");
                Assert.assertEquals(postCreateItemResponse.getStatusLine().getStatusCode(), 302);
            }

            Allure.step("Send GET request -> Get item by name");
            HttpGet getItemByName = new HttpGet(getItemByNameURL(FOLDER_NAME));
            getItemByName.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

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
                getItemList.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

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

    @Test(dependsOnMethods = "testCreateFolderWithValidName")
    @Story("Folder")
    @Description("008 Rename Folder")
    public void testRenameFolder() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {

            Allure.step("Send POST request -> Rename Folder");
            HttpPost postRenameItem = new HttpPost(getRenameItemURL(FOLDER_NAME));
            postRenameItem.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());
            postRenameItem.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            postRenameItem.setEntity(new StringEntity(getRenameItemBody(FOLDER_NEW_NAME)));

            try (CloseableHttpResponse postRenameItemResponse = httpClient.execute(postRenameItem)) {
                Allure.step("Expected result: Item renamed successful. Status code 302");
                Assert.assertEquals(postRenameItemResponse.getStatusLine().getStatusCode(), 302);

                Allure.step(String.format("Expected result: '%s' is displayed on Dashboard", FOLDER_NEW_NAME));
                Assert.assertListContainsObject(TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(), FOLDER_NEW_NAME,
                        "List is not contain folder");
            }
        }
    }

    @Test()
    @Story("Folder")
    @Description("007 Add Description to Folder")
    public void testAddDescriptionToFolder() throws IOException {
        String description = "Add description to folder!";
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {

            Allure.step("Send POST request -> Create Folder");
            HttpPost postCreateItem = new HttpPost(getCreateItemURL());
            postCreateItem.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());
            postCreateItem.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            postCreateItem.setEntity(new StringEntity(getCreateItemBody(FOLDER_NAME, FOLDER_MODE)));

            try (CloseableHttpResponse postCreateItemResponse = httpClient.execute(postCreateItem)) {
                Allure.step("Expected result: Successful item creation. Status code 302");
                Assert.assertEquals(postCreateItemResponse.getStatusLine().getStatusCode(), 302);
            }

            Allure.step("Send POST request -> Add Description to Folder");
            HttpPost postAddDescriptionItem = new HttpPost(getAddDescriptionURL(FOLDER_NAME));
            postAddDescriptionItem.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());
            postAddDescriptionItem.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            postAddDescriptionItem.setEntity(new StringEntity(getAddDescriptionBody(description)));

            try (CloseableHttpResponse postAddDescriptionResponse = httpClient.execute(postAddDescriptionItem)) {
                Allure.step("Expected result: Successful add description to item. Status code 302");
                Assert.assertEquals(postAddDescriptionResponse.getStatusLine().getStatusCode(), 302);
            }

            Allure.step("Send GET request -> Get item by name");
            HttpGet getItemByName = new HttpGet(getItemByNameURL(FOLDER_NAME));
            getItemByName.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse getItemByNameResponse = httpClient.execute(getItemByName)) {
                Allure.step("Expected result: Created element is found by name");
                Assert.assertEquals(getItemByNameResponse.getStatusLine().getStatusCode(), 200);

                String jsonResponse = EntityUtils.toString(getItemByNameResponse.getEntity());

                ProjectResponse projectResponse = new Gson().fromJson(jsonResponse, ProjectResponse.class);

                Allure.step(String.format("(ERR) Expected result: description is '%s'", description));
                Assert.assertNull(projectResponse.getDescription());

            }

            Allure.step("Send DELETE request -> Delete Folder");
            HttpDelete deleteFolder = new HttpDelete(ProjectUtils.getUrl() + String.format("job/%s/", TestUtils.encodeParam(FOLDER_NAME)));
            deleteFolder.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse deleteFolderResponse = httpClient.execute(deleteFolder)) {
                Allure.step("Expected result: Delete item successful. Status code is 204");
                Assert.assertEquals(deleteFolderResponse.getStatusLine().getStatusCode(), 204);
            }
        }
    }

    @Test(dependsOnMethods = "testCreateFolderWithValidNameXML")
    @Story("Folder")
    @Description("004 Delete Folder")
    public void testDeleteFolder() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {

            Allure.step("Send DELETE request -> Delete Folder");
            HttpDelete httpDelete = new HttpDelete(String.format(ProjectUtils.getUrl() + "job/%s/", FOLDER_NAME_BY_XML_CREATED));
            httpDelete.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {

                Allure.step("Expected result: Delete item status code is 204");
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 204);

                Allure.step(String.format("Expected result: '%s' is not displayed on Dashboard", FOLDER_NAME_BY_XML_CREATED));
                Assert.assertListNotContainsObject(TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(), FOLDER_NAME_BY_XML_CREATED,
                        "The folder is not deleted");
            }

            Allure.step("Send GET request -> Get item by name");
            HttpGet httpGet = new HttpGet(getItemByNameURL(FOLDER_NAME_BY_XML_CREATED));
            httpGet.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {

                Allure.step("Expected result: Item not found. Status code is 404");
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 404);
            }
        }
    }

    @Story("Freestyle Project")
    @Description("Create Freestyle Project with valid name")
    @Test
    public void testCreateFreestyleProject() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            String query = "name=" + TestUtils.encodeParam(FREESTYLE_PROJECT);

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem?" + query);
            httpPost.setEntity(new StringEntity(TestUtils.loadPayload("create-empty-freestyle-project.xml")));

            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/xml");
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                Assert.assertListContainsObject(
                        TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(),
                        FREESTYLE_PROJECT,
                        "The project is not created");
            }
        }
    }

    @Story("Freestyle Project")
    @Description("Rename Freestyle Project with valid name")
    @Test(dependsOnMethods = "testCreateFreestyleProject")
    public void testRenameFreestyleProject() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            String renameUrl = ProjectUtils.getUrl() + "job/" + FREESTYLE_PROJECT + "/doRename";

            HttpPost httpPost = new HttpPost(renameUrl);
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("newName", RENAMED_FREESTYLE_PROJECT));
            httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);

                Assert.assertListContainsObject(
                        TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(),
                        RENAMED_FREESTYLE_PROJECT,
                        "The project was not renamed");
            }
        }
    }

    @Story("Freestyle Project")
    @Description("Delete Freestyle project")
    @Test(dependsOnMethods = "testRenameFreestyleProject")
    public void testDeleteFreestyleProject() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            String deleteUrl = ProjectUtils.getUrl() + "job/" + RENAMED_FREESTYLE_PROJECT + "/";

            HttpDelete httpDelete = new HttpDelete(deleteUrl);

            httpDelete.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            httpDelete.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 204);
            }

            HttpGet httpGet = new HttpGet(ProjectUtils.getUrl() + "job/" + RENAMED_FREESTYLE_PROJECT + "/api/json");
            httpGet.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 404);
                Assert.assertListNotContainsObject(
                        TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(),
                        RENAMED_FREESTYLE_PROJECT,
                        "The project was not deleted");

            }
        }
    }

    @Test
    @Story("Multibranch pipeline")
    @Description("Create Multibranch pipeline with valid name")
    public void testCreateMultibranchPipeline() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem");

            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("name", MULTIBRANCH_PIPELINE_NAME));
            nameValuePairs.add(new BasicNameValuePair("mode", "org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject"));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 302);
                Assert.assertListContainsObject(
                        TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(),
                        MULTIBRANCH_PIPELINE_NAME,
                        "The project is not created");
            }
        }
    }

    @Test
    @Story("Multibranch pipeline")
    @Description("Create Multibranch pipeline with valid name using XML")
    public void testCreateMultibranchPipelineXML() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            String queryString = "name=" + TestUtils.encodeParam(MULTIBRANCH_PIPELINE_NAME_XML);

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem?" + queryString);
            httpPost.setEntity(new StringEntity(TestUtils.loadPayload("create-empty-multibranch-pipeline.xml")));

            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/xml");
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
                Assert.assertListContainsObject(
                        TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(),
                        MULTIBRANCH_PIPELINE_NAME_XML,
                        "The project is not created");
            }
        }
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipeline")
    @Story("Multibranch pipeline")
    @Description("Rename Multibranch pipeline")
    public void testRenameMultibranchPipeline() throws IOException {
        final String newMultibranchPipelineName = "New" + MULTIBRANCH_PIPELINE_NAME;

        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            HttpPost requestRename = new HttpPost(ProjectUtils.getUrl() +
                    String.format("/job/%s/confirmRename", TestUtils.encodeParam(MULTIBRANCH_PIPELINE_NAME)));
            requestRename.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());
            requestRename.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            requestRename.setEntity(new StringEntity("newName=" + TestUtils.encodeParam(newMultibranchPipelineName)));

            Allure.step("Send POST request -> Rename " + String.format("%s", MULTIBRANCH_PIPELINE_NAME));
            try (CloseableHttpResponse responseRename = httpClient.execute(requestRename)) {
                Allure.step("Expected result: Status code is 302");
                Assert.assertEquals(responseRename.getStatusLine().getStatusCode(), 302);

                Allure.step("Expected result: " + String.format("%s is renamed to %s and displayed on Dashboard",
                        MULTIBRANCH_PIPELINE_NAME, newMultibranchPipelineName));
                Assert.assertListContainsObject(TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(), newMultibranchPipelineName,
                        "List does not contain the project");
            }
        }
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipelineXML")
    @Story("Multibranch pipeline")
    @Description("Delete Multibranch pipeline")
    public void testDeleteMultibranchPipeline() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            HttpDelete httpDelete = new HttpDelete(ProjectUtils.getUrl() + String.format("job/%s/", TestUtils.encodeParam(MULTIBRANCH_PIPELINE_NAME_XML)));
            httpDelete.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            Allure.step("Send DELETE request -> Delete " + String.format("%s", MULTIBRANCH_PIPELINE_NAME_XML));
            try (CloseableHttpResponse responseDelete = httpClient.execute(httpDelete)) {
                Allure.step("Expected result: Status code is 204");
                Assert.assertEquals(responseDelete.getStatusLine().getStatusCode(), 204);
            }

            HttpGet httpGet = new HttpGet(ProjectUtils.getUrl() + TestUtils.encodeParam(MULTIBRANCH_PIPELINE_NAME_XML));
            httpGet.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            Allure.step("Send GET request to check the list of existing projects");
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                Allure.step("Expected result: " + String.format("%s is deleted and is not displayed on Dashboard", MULTIBRANCH_PIPELINE_NAME_XML));
                Assert.assertListNotContainsObject(TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(), MULTIBRANCH_PIPELINE_NAME_XML,
                        "The project is not deleted");
            }
        }
    }

    @Test
    @Story("Multi-Configuration project")
    @Description("Create Multi-Configuration project with valid name")
    public void testCreateMultiConfiguration() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {

            HttpPost postCreateItem = new HttpPost(getCreateItemURL());
            postCreateItem.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());
            postCreateItem.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            postCreateItem.setEntity(new StringEntity(getCreateItemBody(MULTI_CONFIGURATION_PROJECT, MULTI_CONFIGURATION_MODE)));

            try (CloseableHttpResponse postCreateItemResponse = httpClient.execute(postCreateItem)) {
                Allure.step("Expected result: Successful item creation. Status code 302");
                Assert.assertEquals(postCreateItemResponse.getStatusLine().getStatusCode(), 302);
            }

            Allure.step("Send GET request -> Get item by name");
            HttpGet getItemByName = new HttpGet(getItemByNameURL(MULTI_CONFIGURATION_PROJECT));
            getItemByName.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse getItemByNameResponse = httpClient.execute(getItemByName)) {
                Allure.step("Expected result: Created element is found by name");
                Assert.assertEquals(getItemByNameResponse.getStatusLine().getStatusCode(), 200);

                String jsonResponse = EntityUtils.toString(getItemByNameResponse.getEntity());
                ProjectResponse projectResponse = new Gson().fromJson(jsonResponse, ProjectResponse.class);

                Allure.step(String.format("Expected result: fullName is '%s' response", MULTI_CONFIGURATION_PROJECT));
                Assert.assertEquals(projectResponse.getFullName(), MULTI_CONFIGURATION_PROJECT, "Folder didn't find");
                Allure.step("Expected result: description is null");
                Assert.assertNull(projectResponse.getDescription());
                Allure.step(String.format("Expected result: Field '_class': %s", MULTI_CONFIGURATION_MODE));
                Assert.assertEquals(projectResponse.get_class(), MULTI_CONFIGURATION_MODE);
            }

            Allure.step("Send GET request -> Get project list from Dashboard");
            HttpGet getItemList = new HttpGet(ProjectUtils.getUrl() + "api/json");
            getItemList.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            try (CloseableHttpResponse getItemListResponse = httpClient.execute(getItemList)) {
                String getItemListResponseBody = EntityUtils.toString(getItemListResponse.getEntity());
                ProjectListResponse projectListResponse = new Gson().fromJson(getItemListResponseBody, ProjectListResponse.class);

                boolean findFolderNameInProjectList = projectListResponse.getJobs()
                        .stream().anyMatch(project -> project.getName().equals(MULTI_CONFIGURATION_PROJECT));

                Allure.step("Expected result: Project name found in the list");
                Assert.assertTrue(findFolderNameInProjectList, "Project name was not found in the list");
            }
        }
    }

    @Test
    @Story("Folder creation")
    @Description("Create a new folder in Jenkins")
    public void testCreateFolder() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithAllureLogging()) {
            String folderName = "MyTestFolder";
            String createFolderURL = getCreateItemURL() + "?name=" + folderName;

            String xmlBody = String.format(TestUtils.loadPayload("create-empty-folder.xml"), folderName);

            HttpPost httpPost = new HttpPost(createFolderURL);
            httpPost.setEntity(new StringEntity(xmlBody, ContentType.APPLICATION_XML));
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken());

            Allure.step("Expected result: The folder creating process is done with 200 status");
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                Assert.assertEquals(response.getStatusLine().getStatusCode(), 200, "Folder creation failed");

                Assert.assertListContainsObject(
                        TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(),
                        folderName,
                        "The folder is not created"
                );
            }
        }
    }
}