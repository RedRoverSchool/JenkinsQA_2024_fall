package school.redrover.models.job;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JacksonXmlRootElement(localName = "flow-definition")
public class Pipeline implements JobType {

    @JacksonXmlProperty(localName = "description")
    private String description;

    @JacksonXmlProperty(localName = "keepDependencies")
    private boolean keepDependencies;

    @JacksonXmlProperty(localName = "properties")
    private String properties;

    @JacksonXmlProperty(localName = "definition")
    private Definition definition;

    @JacksonXmlProperty(localName = "triggers")
    private String triggers;

    @JacksonXmlProperty(localName = "disabled")
    private boolean disabled;

    @Builder
    public static class Definition {

        @JacksonXmlProperty(localName = "class")
        private String className;

        @JacksonXmlProperty(localName = "script")
        private String script;

        @JacksonXmlProperty(localName = "sandbox")
        private boolean sandbox;
    }

}
