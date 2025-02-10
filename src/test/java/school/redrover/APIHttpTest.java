package school.redrover;

import com.google.common.net.HttpHeaders;
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
import org.testng.Assert;
import org.testng.annotations.Test;
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


    @Test
    @Story("Folder creation")
    @Description("Create a new folder in Jenkins")
    public void testCreateFolder() throws IOException {
        try (CloseableHttpClient httpClient = createHttpClientWithTokenAuthAndAllureLogging()) {
            String folderName = "MyTestFolder";
            String createFolderURL = getCreateItemURL() + "?name=" + folderName;

            String xmlBody = String.format(TestUtils.loadPayload("create-empty-folder.xml"), folderName);

            HttpPost httpPost = new HttpPost(createFolderURL);
            httpPost.setEntity(new StringEntity(xmlBody, ContentType.APPLICATION_XML));

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