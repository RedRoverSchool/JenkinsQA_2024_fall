package school.redrover.model;

public class CrumbIssuerResponse {

    private String crumbRequestField;

    private String crumb;

    public CrumbIssuerResponse(String crumbRequestField, String crumb) {
        this.crumbRequestField = crumbRequestField;
        this.crumb = crumb;
    }

    public String getCrumbRequestField() {
        return crumbRequestField;
    }

    public void setCrumbRequestField(String crumbRequestField) {
        this.crumbRequestField = crumbRequestField;
    }

    public String getCrumb() {
        return crumb;
    }

    public void setCrumb(String crumb) {
        this.crumb = crumb;
    }

}
