package school.redrover.model;

public class CrumbIssuerResponse {

    private String crumbRequestField;

    private String crumb;

    public CrumbIssuerResponse() {}

    public CrumbIssuerResponse(String crumbRequestField, String crumb) {
        this.crumbRequestField = crumbRequestField;
        this.crumb = crumb;
    }

    public String getCrumbRequestField() {
        return crumbRequestField;
    }

    public String getCrumb() {
        return crumb;
    }

}
