package school.redrover;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseAPIHttpTest;
import school.redrover.runner.ProjectUtils;
import school.redrover.runner.TestUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Epic("Http API Requests")
public class APIHTTPFolderTest extends BaseAPIHttpTest {

    private static final String FOLDER_NAME = "FolderUn";
    private static final String FOLDER_CREATE_MODE = "com.cloudbees.hudson.plugins.folder.Folder";
    private static final String FOLDER_NEW_NAME = "FolderNewName";
    private static final String DESCRIPTION = "Add description to rename folder!";


    private static final class Folder {
        private String _class;
        private String description;
        private String displayName;
        private String fullName;
        private String url;
    }

    private static final class Project {
        private String _class;
        private String name;
        private String url;
        private String color;

        public Project(String _class, String url, String color, String  name) {
            this._class = _class;
            this.url = url;
            this.color = color;
            this.name = name;
        }

        public String get_class() {
            return _class;
        }
        public String getName() {
            return name;
        }
        public String getUrl() {
            return url;
        }
        public String getColor() {
            return color;
        }
    }

    private static final class Projects {
        private String _class;
        private List<Project> jobs;

        public Projects(String _class, List<Project> jobs) {
            this._class = _class;
            this.jobs = jobs;
        }

        public String get_class() {
            return _class;
        }

        public List<Project> getJobs() {
            return jobs;
        }
    }

    private String getCreateFolderURL() {
        return ProjectUtils.getUrl() + "createItem";
    }

    private String getItemByNameURL(String name) {
        return ProjectUtils.getUrl() + "job/" +
                URLEncoder.encode(name, StandardCharsets.UTF_8) + "/api/json";
    }

    private String getItemListURL() {
        return ProjectUtils.getUrl() + "api/json";
    }

    private String getDeleteItemURL(String name) {
        return String.format(ProjectUtils.getUrl() + "job/%s/", name);
    }

    private String getRenameItemURL(String name) {
        return ProjectUtils.getUrl() + String.format("job/%s/confirmRename",name);
    }

    private String getAddDescriptionURL(String name) {
        return ProjectUtils.getUrl() + String.format("job/%s/submitDescription", name);
    }
    private String getCreateFolderBody(String name, String mode) {
        return "name="+ name +"&mode=" + mode;
    }
    private String getRenameFolderBody(String name) {
        return "newName=" + name;
    }

    private String getAddDescriptionBody(String description) {
        return "description=" + TestUtils.encodeParam(description);
    }

    @Test
    @Story("Folder")
    @Description("002 Create Folder with valid name")
    public void testCreateFolderWithValidName() throws IOException, URISyntaxException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI(getCreateFolderURL()))
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(getCreateFolderBody(FOLDER_NAME,FOLDER_CREATE_MODE)))
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

        Folder folder = new Gson().fromJson(body, Folder.class);
        Allure.step(String.format("Expected result: fullName is '%s' response", FOLDER_NAME));
        Assert.assertEquals(folder.fullName, FOLDER_NAME);

        Allure.step("Expected result: description is null");
        Assert.assertNull(folder.description);
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
                .POST(HttpRequest.BodyPublishers.ofString(getRenameFolderBody(FOLDER_NEW_NAME)))
                .build();


        Allure.step("Send POST request -> Rename folder");
        HttpResponse<String> postRenameResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        Allure.step("Expected result: Successful item rename. Status code 302");
        Assert.assertEquals(postRenameResponse.statusCode(), 302);

        HttpRequest getItemList = HttpRequest.newBuilder()
                .uri(new URI(getItemListURL()))
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .GET()
                .build();

        Allure.step("Send GET request -> Get project list from Dashboard");
        HttpResponse<String> getItemListResponse = httpClient.send(getItemList, HttpResponse.BodyHandlers.ofString());
        String body = getItemListResponse.body();
        Projects projects = new Gson().fromJson(body, Projects.class);
        boolean findNewProjectNameInList = projects.getJobs().stream()
                .anyMatch(project -> project.getName().equals(FOLDER_NEW_NAME));

        boolean findOriginProjectNameInList = projects.getJobs().stream()
                .anyMatch(project -> project.getName().equals(FOLDER_NAME));

        Assert.assertTrue(findNewProjectNameInList, "project name was not found in the list");
        Assert.assertFalse(findOriginProjectNameInList, "Project name was found in the list");
    }

    @Test()
    @Story("Folder")
    @Description("007 Add Description to Folder")
    public void testAddDescriptionToFolder() throws IOException, InterruptedException, URISyntaxException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest postCreateProjectRequest = HttpRequest.newBuilder()
                .uri(new URI(getCreateFolderURL()))
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthWithToken())
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(getCreateFolderBody(FOLDER_NAME,FOLDER_CREATE_MODE)))
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

        Allure.step("Expected result: Response body contains 'description: null' for folder");
        Assert.assertTrue(body.contains("\"description\":null"), "FolderUn");

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

