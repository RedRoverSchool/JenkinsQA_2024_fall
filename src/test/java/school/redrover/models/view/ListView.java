package school.redrover.models.view;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JacksonXmlRootElement(localName = "hudson.model.ListView")
public class ListView {

    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "filterExecutors")
    private boolean filterExecutors;

    @JacksonXmlProperty(localName = "filterQueue")
    private boolean filterQueue;

    @JacksonXmlProperty(localName = "properties")
    private String properties;

    @JacksonXmlProperty(localName = "jobNames")
    private JobNames jobNames;

    @JacksonXmlProperty(localName = "jobFilters")
    private String jobFilters;

    @JacksonXmlProperty(localName = "columns")
    private Columns columns;

    @JacksonXmlProperty(localName = "recurse")
    private boolean recurse;

    @Data
    @Builder
    public static class JobNames {
        @JacksonXmlProperty(localName = "string")
        private String jobName;
    }

    @Data
    @Builder
    public static class Columns {
        @JacksonXmlProperty(localName = "hudson.views.StatusColumn")
        private String statusColumn;

        @JacksonXmlProperty(localName = "hudson.views.WeatherColumn")
        private String weatherColumn;

        @JacksonXmlProperty(localName = "hudson.views.JobColumn")
        private String jobColumn;

        @JacksonXmlProperty(localName = "hudson.views.LastSuccessColumn")
        private String lastSuccessColumn;

        @JacksonXmlProperty(localName = "hudson.views.LastFailureColumn")
        private String lastFailureColumn;

        @JacksonXmlProperty(localName = "hudson.views.LastDurationColumn")
        private String lastDurationColumn;

        @JacksonXmlProperty(localName = "hudson.views.BuildButtonColumn")
        private String buildButtonColumn;
    }
}
