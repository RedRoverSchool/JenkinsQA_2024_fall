package school.redrover.runner;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.qameta.allure.Allure;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpLogger {

    private static final Logger logger = LoggerFactory.getLogger(HttpLogger.class);

    public static void logRequestAndResponse(HttpRequestBase httpRequest, CloseableHttpResponse response) throws IOException {
        String requestUri = httpRequest.getURI().toString();

        logger.info("Sending request to: {}", requestUri);
        Allure.addAttachment("Request URI", requestUri);

        for (Header header : httpRequest.getAllHeaders()) {
            if ("Authorization".equalsIgnoreCase(header.getName())) {
                String maskedAuthorization = "*".repeat(header.getValue().length() - 5) + header.getValue().substring(header.getValue().length() - 5);
                logger.info("{}: {}", header.getName(), maskedAuthorization);
                Allure.addAttachment(header.getName(), maskedAuthorization);
            } else {
                logger.info("{}: {}", header.getName(), header.getValue());
                Allure.addAttachment(header.getName(), header.getValue());
            }
        }

        if (httpRequest instanceof HttpPost httpPost) {
            if (httpPost.getEntity() != null) {
                String entityContent = EntityUtils.toString(httpPost.getEntity());
                logger.info("Request body: {}", entityContent);
                Allure.addAttachment("Request Body", entityContent);
            }
        }

        logger.info("Response status: {}", response.getStatusLine());
        String responseBody = EntityUtils.toString(response.getEntity());
        logger.info("Response body: {}", responseBody);

        Allure.addAttachment("Response Status", response.getStatusLine().toString());
        Allure.addAttachment("Response Body", responseBody);
    }
}

