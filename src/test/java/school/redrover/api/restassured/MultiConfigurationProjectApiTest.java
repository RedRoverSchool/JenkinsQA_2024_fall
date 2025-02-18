package school.redrover.api.restassured;

import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseApiTest;
import school.redrover.runner.TestDataProvider;
import school.redrover.runner.TestUtils;

import static io.restassured.RestAssured.given;
import static school.redrover.runner.TestApiUtils.*;

@Epic("API")
@Feature("Multi-Configuration Project")
public class MultiConfigurationProjectApiTest extends BaseApiTest {

    private final String MULTI_CONFIG_NAME = "MultiConfigurationProject";
    private final String MULTI_CONFIG_MODE = "hudson.matrix.MatrixProject";
    private final String MULTI_CONFIG_NAME_XML = "MultiConfigurationProjectXML";
    private final String DESCRIPTION = "Create Project with Description!";
    private static final String XML_CREATE_EMPTY_PROJECT_FILE = "create-empty-multi-config.xml";
    private static String XML_CREATE_PROJECT_WITH_DESCRIPTION_FILE = "create-multi-config-with-description.xml";


    @Test(dataProvider = "projectNames", dataProviderClass = TestDataProvider.class)
    @Description("00.003.09 Create Multi-Configuration Project with valid name")
    public void testCreateMultiConfigWithValidName(String projectName) {
        given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("name", projectName)
                .formParam("mode", MULTI_CONFIG_MODE)
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec(302, 500L));

        Allure.step(String.format("Expected result: fullName is '%s'", projectName));
        Assert.assertEquals(getResponseGetItemByNameAsObject(projectName).getFullName(), projectName);
        Allure.step("(ERR)Expected result: description is null");
        Assert.assertEquals(getResponseGetItemByNameAsObject(projectName).getDescription(),null);
        Allure.step(String.format("Expected result: _class is '%s'", MULTI_CONFIG_MODE));
        Assert.assertEquals(getResponseGetItemByNameAsObject(projectName).get_class(), MULTI_CONFIG_MODE);

        Allure.step("Expected result: Project name found in the list");
        Assert.assertTrue(findItemInAllProjectList(projectName), "Project name not found in the list");

        deleteProject(projectName);
    }

    @Test(dataProvider = "projectNames", dataProviderClass = TestDataProvider.class)
    @Description("00.003.10 Create Multi-Configuration Project with valid name XML")
    public void testCreateProjectWithValidNameXML(String projectName) {

        given()
                .spec(requestSpec())
                .contentType(ContentType.XML)
                .queryParam("name", projectName)
                .body(TestUtils.loadPayload(XML_CREATE_EMPTY_PROJECT_FILE))
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec(200, 500L));

        Allure.step(String.format("Expected result: fullName is '%s'", projectName));
        Assert.assertEquals(getResponseGetItemByNameAsObject(projectName).getFullName(), projectName);
        Allure.step("Expected result: description is empty");
        Assert.assertEquals(getResponseGetItemByNameAsObject(projectName).getDescription(),"");
        Allure.step(String.format("Expected result: _class is '%s'", MULTI_CONFIG_MODE));
        Assert.assertEquals(getResponseGetItemByNameAsObject(projectName).get_class(),MULTI_CONFIG_MODE);

        Allure.step("Expected result: Project name found in the list");
        Assert.assertTrue(findItemInAllProjectList(projectName), "Project name not found in the list");

        deleteProject(projectName);
    }

    @Test
    @Description("00.003.12 Create Multi-Configuration Project with empty name")
    public void testCreateProjectWithEmptyName() {
        Response response = given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("name", "")
                .formParam("mode", MULTI_CONFIG_MODE)
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec(400, 500L))
                .extract()
                .response();

        Allure.step("Expected result: Header 'X-Error' has value 'No name is specified'");
        Assert.assertEquals(response.getHeaders().getValue("X-Error"),"No name is specified");
    }

    @Test
    @Description("00.003.14 Create Multi-Configuration Project without mode")
    public void testCreateProjectWithoutMode() {
        Response response = given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("name", MULTI_CONFIG_NAME)
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec(400, 500L))
                .extract()
                .response();

        Allure.step("Expected result: Header 'X-Error' has value 'No mode given'");
        Assert.assertEquals(response.getHeaders().getValue("X-Error"),"No mode given");
    }

    @Test(dataProvider = "providerUnsafeCharacters", dataProviderClass = TestDataProvider.class)
    @Description("00.003.13 Create Multi-Configuration Project with with unsafe character")
    public void testCreateProjectWithUnsafeCharacter(String unsafeCharacter) {
        Response projectResponse = given().spec(requestSpec())
                .formParam("name", unsafeCharacter)
                .formParam("mode", MULTI_CONFIG_MODE)
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec(400, 500L))
                .extract()
                .response();

        Allure.step("Expected result: Header 'X-Error' has value '%s is an unsafe character'".formatted(unsafeCharacter));
        Assert.assertEquals(projectResponse.getHeaders().getValue("X-Error"), "%s  is an unsafe character".formatted(unsafeCharacter));
    }

    @Test
    @Description("00.003.11 Create Multi-Configuration Project with valid name XML")
    public void testCreateProjectWithValidNameAndDescriptionXML() {

        given()
                .spec(requestSpec())
                .contentType(ContentType.XML)
                .queryParam("name", MULTI_CONFIG_NAME_XML)
                .body(TestUtils.loadPayload("create-multi-config-with-description.xml"))
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec(200, 500L));

        Allure.step(String.format("Expected result: fullName is '%s'", MULTI_CONFIG_NAME_XML));
        Assert.assertEquals(getResponseGetItemByNameAsObject(MULTI_CONFIG_NAME_XML).getFullName(), MULTI_CONFIG_NAME_XML);
        Allure.step("Expected result: description is %s".formatted(DESCRIPTION));
        Assert.assertEquals(getResponseGetItemByNameAsObject(MULTI_CONFIG_NAME_XML).getDescription(),DESCRIPTION);
        Allure.step(String.format("Expected result: _class is '%s'", MULTI_CONFIG_MODE));
        Assert.assertEquals(getResponseGetItemByNameAsObject(MULTI_CONFIG_NAME_XML).get_class(),MULTI_CONFIG_MODE);

        Allure.step("Expected result: Project name found in the list");
        Assert.assertTrue(findItemInAllProjectList(MULTI_CONFIG_NAME_XML), "Project name not found in the list");

        deleteProject(MULTI_CONFIG_NAME_XML);
    }

    @Test
    @Description("00.003.15 Create Multi-Configuration Project by copy from another project")
    public void testCreateProjectCopyFromAnotherProject() {
        createNewProjectXML(MULTI_CONFIG_NAME_XML, XML_CREATE_PROJECT_WITH_DESCRIPTION_FILE);

        given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("name", MULTI_CONFIG_NAME)
                .formParam("mode", "copy")
                .formParam("from", MULTI_CONFIG_NAME_XML)
                .when()
                .post(getCreateItemPath())
                .then()
                .spec(responseSpec(302, 500L));

        Allure.step(String.format("Expected result: fullName is '%s'", MULTI_CONFIG_NAME));
        Assert.assertEquals(getResponseGetItemByNameAsObject(MULTI_CONFIG_NAME).getFullName(), MULTI_CONFIG_NAME);
        Allure.step("Expected result: description is %s".formatted(DESCRIPTION));
        Assert.assertEquals(getResponseGetItemByNameAsObject(MULTI_CONFIG_NAME).getDescription(),DESCRIPTION);
        Allure.step(String.format("Expected result: _class is '%s'", MULTI_CONFIG_MODE));
        Assert.assertEquals(getResponseGetItemByNameAsObject(MULTI_CONFIG_NAME).get_class(),MULTI_CONFIG_MODE);

        Allure.step("Expected result: Project name found in the list");
        Assert.assertTrue(findItemInAllProjectList(MULTI_CONFIG_NAME), "Project name not found in the list");

        deleteProject(MULTI_CONFIG_NAME);
        deleteProject(MULTI_CONFIG_NAME_XML);
    }

    @Test
    @Description("03.001.01 Add description to Project")
    public void testAddDescriptionToCreatedProject() {
        createNewProjectXML(MULTI_CONFIG_NAME_XML, XML_CREATE_EMPTY_PROJECT_FILE);

        given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("description", DESCRIPTION)
                .when()
                .post(getAddDescriptionToCreatedItemPath(MULTI_CONFIG_NAME_XML))
                .then()
                .spec(responseSpec(302, 500L));

        Allure.step(String.format("Expected result: fullName is '%s'", MULTI_CONFIG_NAME_XML));
        Assert.assertEquals(getResponseGetItemByNameAsObject(MULTI_CONFIG_NAME_XML).getFullName(), MULTI_CONFIG_NAME_XML);
        Allure.step("Expected result: description is empty");
        Assert.assertEquals(getResponseGetItemByNameAsObject(MULTI_CONFIG_NAME_XML).getDescription(),DESCRIPTION);
        Allure.step(String.format("Expected result: _class is '%s'", MULTI_CONFIG_MODE));
        Assert.assertEquals(getResponseGetItemByNameAsObject(MULTI_CONFIG_NAME_XML).get_class(),MULTI_CONFIG_MODE);

        deleteProject(MULTI_CONFIG_NAME_XML);
    }

    @Test
    @Description("03.002.01 Disable Project")
    public void testDisableCreatedProject() {
        createNewProjectXML(MULTI_CONFIG_NAME_XML, XML_CREATE_EMPTY_PROJECT_FILE);

        given()
                .spec(requestSpec())
                .when()
                .post(getDisableProjectPath(MULTI_CONFIG_NAME_XML))
                .then()
                .spec(responseSpec(302, 500L));

        boolean disableProject = getResponseGetAllProjectListAsObject().getJobs().stream().filter(project -> project.getName().equals(MULTI_CONFIG_NAME_XML))
                .anyMatch(project -> project.getColor().equals("disabled"));

        Assert.assertTrue(disableProject);
    }

    @Test(dependsOnMethods = "testDisableCreatedProject")
    @Description("03.002.02 Enable Project")
    public void testEnableProject() {
        given()
                .spec(requestSpec())
                .when()
                .post(getEnableProjectPath(MULTI_CONFIG_NAME_XML))
                .then()
                .spec(responseSpec(302, 500L));

        boolean enableProject = getResponseGetAllProjectListAsObject().getJobs().stream().filter(project -> project.getName().equals(MULTI_CONFIG_NAME_XML))
                .anyMatch(project -> project.getColor().equals("notbuilt"));

        Assert.assertTrue(enableProject);

        deleteProject(MULTI_CONFIG_NAME_XML);
    }

    @Test
    @Description("03.005.01 Rename Project")
    public void testRenameProject() {
        createNewProjectXML(MULTI_CONFIG_NAME_XML, XML_CREATE_EMPTY_PROJECT_FILE);

        given()
                .spec(requestSpec())
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("newName", MULTI_CONFIG_NAME)
                .when()
                .post(getRenameItemPath(MULTI_CONFIG_NAME_XML))
                .then()
                .spec(responseSpec(302, 500L));

        Allure.step(String.format("Expected result: fullName is '%s'", MULTI_CONFIG_NAME));
        Assert.assertEquals(getResponseGetItemByNameAsObject(MULTI_CONFIG_NAME).getFullName(), MULTI_CONFIG_NAME);

        Allure.step("Expected result: Project name '%s' found in the list".formatted(MULTI_CONFIG_NAME));
        Assert.assertTrue(findItemInAllProjectList(MULTI_CONFIG_NAME));
        Allure.step("Expected result: Project name '%s' NOT found in the list".formatted(MULTI_CONFIG_NAME_XML));
        Assert.assertFalse(findItemInAllProjectList(MULTI_CONFIG_NAME_XML));

        deleteProject(MULTI_CONFIG_NAME);
    }

    @Test()
    @Description("03.003.01 Delete Project")
    public void testDeleteProject() {
        createNewProjectXML(MULTI_CONFIG_NAME_XML, XML_CREATE_EMPTY_PROJECT_FILE);

        Allure.step("Send DELETE request -> Delete Project with name %s".formatted(MULTI_CONFIG_NAME_XML));
        given()
                .spec(requestSpec())
                .when()
                .delete(getDeleteItemPath(MULTI_CONFIG_NAME_XML))
                .then()
                .spec(responseSpec(204,500L));

        Allure.step("Send GET request -> Get Project with name %s".formatted(MULTI_CONFIG_NAME_XML));
        given()
                .spec(requestSpec())
                .when()
                .get(getItemByNamePath(MULTI_CONFIG_NAME_XML))
                .then()
                .spec(responseSpec(404, 500L));
    }
}
