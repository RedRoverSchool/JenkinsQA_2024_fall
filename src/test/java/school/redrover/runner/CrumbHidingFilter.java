package school.redrover.runner;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.json.JSONObject;

public class CrumbHidingFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext context) {
        Response response = context.next(requestSpec, responseSpec);
        maskCrumbInLogs(response);
        return response;
    }

    private void maskCrumbInLogs(Response response) {
        if (response.getContentType() != null && response.getContentType().contains("application/json")) {
            String responseBody = response.getBody().asString();
            try {
                JSONObject jsonObject = new JSONObject(responseBody);
                if (jsonObject.has("crumb")) {
                    jsonObject.put("crumb", "******");
                }
                System.out.println("Response with masked crumb: " + jsonObject.toString(2));
            } catch (Exception e) {
                System.err.println("Failed to parse response as JSON: " + responseBody);
            }
        } else {
            System.out.println("Response: " + response.getBody().asString());
        }
    }
}
