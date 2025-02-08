package school.redrover.api.http.standard;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.ProjectResponse;
import school.redrover.model.ProjectListResponse;
import school.redrover.runner.BaseAPIHttpTest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Epic("Standard HttpClient API Requests")
public class FolderTest extends BaseAPIHttpTest {

    private static final String FOLDER_NAME = "FolderHTTP";
    private static final String FOLDER_CREATE_MODE = "com.cloudbees.hudson.plugins.folder.Folder";
    private static final String FOLDER_NEW_NAME = "FolderNewName";
    private static final String DESCRIPTION = "Add description to rename folder!";

    @Test
    @Story("Folder")
    @Description("002 Create Folder with valid name")
    public void testCreateFolderWithValidName() throws IOException, URISyntaxException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI(getCreateItemURL()))
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(getCreateItemBody(FOLDER_NAME, FOLDER_CREATE_MODE)))
                .build();

        Allure.step("Send POST request -> Create Folder");
        HttpResponse<String> httpResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        Allure.step("Expected result: Successful item creation. Status code 302");
        Assert.assertEquals(httpResponse.statusCode(), 302);

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(getItemByNameURL(FOLDER_NAME)))
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .GET()
                .build();

        Allure.step("Send GET request -> Get item by name");
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        String body = getResponse.body();
        Allure.step("Expected result: Created element is found by name");
        Assert.assertEquals(getResponse.statusCode(), 200);

        ProjectResponse projectResponse = new Gson().fromJson(body, ProjectResponse.class);
        Allure.step(String.format("Expected result: fullName is '%s' response", FOLDER_NAME));
        Assert.assertEquals(projectResponse.getFullName(), FOLDER_NAME);

        Allure.step("Expected result: description is null");
        Assert.assertNull(projectResponse.getDescription());

        Assert.assertEquals(projectResponse.get_class(), FOLDER_CREATE_MODE);
    }

    @Test(dependsOnMethods = "testCreateFolderWithValidName")
    @Story("Folder")
    @Description("008 Rename Folder")
    public void testRenameFolder() throws IOException, InterruptedException, URISyntaxException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI(getRenameItemURL(FOLDER_NAME)))
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(getRenameItemBody(FOLDER_NEW_NAME)))
                .build();


        Allure.step("Send POST request -> Rename folder");
        HttpResponse<String> postRenameResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        Allure.step("Expected result: Successful item rename. Status code 302");
        Assert.assertEquals(postRenameResponse.statusCode(), 302);

        HttpRequest getItemList = HttpRequest.newBuilder()
                .uri(new URI(getAllProjectList()))
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .GET()
                .build();

        Allure.step("Send GET request -> Get project list from Dashboard");
        HttpResponse<String> getItemListResponse = httpClient.send(getItemList, HttpResponse.BodyHandlers.ofString());
        String body = getItemListResponse.body();

        ProjectListResponse projectListResponse = new Gson().fromJson(body, ProjectListResponse.class);
        boolean findNewProjectNameInList = projectListResponse.getJobs().stream()
                .anyMatch(project -> project.getName().equals(FOLDER_NEW_NAME));

        boolean findOriginProjectNameInList = projectListResponse.getJobs().stream()
                .anyMatch(project -> project.getName().equals(FOLDER_NAME));

        Assert.assertTrue(findNewProjectNameInList, "Project name was not found in the list");
        Assert.assertFalse(findOriginProjectNameInList, "Project name was found in the list");
    }

    @Test()
    @Story("Folder")
    @Description("007 Add Description to Folder")
    public void testAddDescriptionToFolder() throws IOException, InterruptedException, URISyntaxException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest postCreateProjectRequest = HttpRequest.newBuilder()
                .uri(new URI(getCreateItemURL()))
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(getCreateItemBody(FOLDER_NAME, FOLDER_CREATE_MODE)))
                .build();

        Allure.step("Send POST request -> Create Folder");
        httpClient.send(postCreateProjectRequest, HttpResponse.BodyHandlers.ofString());

        HttpRequest postAddDescription = HttpRequest.newBuilder()
                .uri(new URI(getAddDescriptionURL(FOLDER_NAME)))
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(getAddDescriptionBody(DESCRIPTION)))
                .build();

        Allure.step("Send POST request -> Add description to folder");
        HttpResponse<String> postAddDescriptionResponse = httpClient.send(postAddDescription, HttpResponse.BodyHandlers.ofString());
        Allure.step("Expected result: Successful add description to item. Status code 302");
        Assert.assertEquals(postAddDescriptionResponse.statusCode(), 302);

        HttpRequest getItemByNameRequest = HttpRequest.newBuilder()
                .uri(new URI(getItemByNameURL(FOLDER_NAME)))
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .GET()
                .build();

        Allure.step("Send GET request -> Get item by name");
        HttpResponse<String> getItemByNameResponse = httpClient.send(getItemByNameRequest, HttpResponse.BodyHandlers.ofString());
        String body = getItemByNameResponse.body();

        ProjectResponse projectResponse = new Gson().fromJson(body, ProjectResponse.class);
        Allure.step("(ERR)Expected result: Response body contains 'description: null' for folder");
        Assert.assertNull(projectResponse.getDescription());

        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(new URI(getDeleteItemURL(FOLDER_NAME)))
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .DELETE()
                .build();

        Allure.step("Send DELETE request -> Delete Folder");
        HttpResponse<String> deleteResponse = httpClient.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        Allure.step("Expected result: Delete item successful. Status code is 204");
        Assert.assertEquals(deleteResponse.statusCode(), 204);
    }
}

