package school.redrover.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CrumbIssuerResponse {

    private String crumbRequestField;

    private String crumb;

    @JsonProperty("_class")
    private String crumbClass;

    public CrumbIssuerResponse() {}

    public CrumbIssuerResponse(String crumbRequestField, String crumb, String crumbClass) {
        this.crumbRequestField = crumbRequestField;
        this.crumb = crumb;
        this.crumbClass = crumbClass;
    }

    public String getCrumbRequestField() {
        return crumbRequestField;
    }

    public String getCrumb() {
        return crumb;
    }

    public String getCrumbClass() {
        return crumbClass;
    }
}
