package school.redrover.api.http.apache;

import com.google.common.net.HttpHeaders;
import io.qameta.allure.Allure;
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
import java.util.ArrayList;
import java.util.List;

@Epic("Apache HttpClient API Requests")
public class MultibranchPipelineTest extends BaseAPIHttpTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "MultibranchPipeline";
    private static final String MULTIBRANCH_PIPELINE_NAME_XML = "MultibranchPipelineXML";

    @Test
    @Story("Multibranch pipeline")
    @Description("Create Multibranch pipeline with valid name")
    public void testCreateMultibranchPipeline() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {
            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem");

            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("name", MULTIBRANCH_PIPELINE_NAME));
            nameValuePairs.add(new BasicNameValuePair("mode", "org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject"));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {
            String queryString = "name=" + TestUtils.encodeParam(MULTIBRANCH_PIPELINE_NAME_XML);

            HttpPost httpPost = new HttpPost(ProjectUtils.getUrl() + "view/all/createItem?" + queryString);
            httpPost.setEntity(new StringEntity(TestUtils.loadPayload("create-empty-multibranch-pipeline.xml")));

            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/xml");

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

        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {
            HttpPost requestRename = new HttpPost(ProjectUtils.getUrl() +
                    String.format("/job/%s/confirmRename", TestUtils.encodeParam(MULTIBRANCH_PIPELINE_NAME)));
            requestRename.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            requestRename.setEntity(new StringEntity("newName=" + TestUtils.encodeParam(newMultibranchPipelineName)));

            Allure.step("Send POST request -> Rename " + String.format("%s", MULTIBRANCH_PIPELINE_NAME));
            try (CloseableHttpResponse responseRename = httpClient.execute(requestRename)) {
                Allure.step("Expected result: Status code is 302");
                Assert.assertEquals(responseRename.getStatusLine().getStatusCode(), 302);

                Allure.step("Expected result: " + String.format("%s is renamed to %s and displayed on Dashboard",
                        MULTIBRANCH_PIPELINE_NAME, newMultibranchPipelineName));
                Assert.assertListContainsObject(
                        TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(),
                        newMultibranchPipelineName,
                        "List does not contain the project");
            }
        }
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipelineXML")
    @Story("Multibranch pipeline")
    @Description("Delete Multibranch pipeline")
    public void testDeleteMultibranchPipeline() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {
            HttpDelete httpDelete = new HttpDelete(
                    ProjectUtils.getUrl() + String.format("job/%s/", TestUtils.encodeParam(MULTIBRANCH_PIPELINE_NAME_XML)));

            Allure.step("Send DELETE request -> Delete " + String.format("%s", MULTIBRANCH_PIPELINE_NAME_XML));
            try (CloseableHttpResponse responseDelete = httpClient.execute(httpDelete)) {
                Allure.step("Expected result: Status code is 204");
                Assert.assertEquals(responseDelete.getStatusLine().getStatusCode(), 204);
            }

            HttpGet httpGet = new HttpGet(ProjectUtils.getUrl() + TestUtils.encodeParam(MULTIBRANCH_PIPELINE_NAME_XML));

            Allure.step("Send GET request to check the list of existing projects");
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                Allure.step("Expected result: " + String.format(
                        "%s is deleted and is not displayed on Dashboard", MULTIBRANCH_PIPELINE_NAME_XML));

                Assert.assertEquals(response.getStatusLine().getStatusCode(), 404);
                Assert.assertListNotContainsObject(
                        TestApiHttpUtils.getAllProjectNamesFromJsonResponseList(),
                        MULTIBRANCH_PIPELINE_NAME_XML,
                        "The project is not deleted");
            }
        }
    }
}
