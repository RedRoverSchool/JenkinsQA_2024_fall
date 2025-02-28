package school.redrover.models.job;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@JacksonXmlRootElement(localName = "matrix-project")
public class MultiConfiguration implements JobType {

    @JacksonXmlProperty(isAttribute = true)
    private String plugin;

    @JacksonXmlProperty(localName = "description")
    private String description;

    @JacksonXmlProperty(localName = "keepDependencies")
    private boolean keepDependencies;

    @JacksonXmlProperty(localName = "properties")
    private String properties;

    @JacksonXmlProperty(localName = "scm")
    private Scm scm;

    @JacksonXmlProperty(localName = "canRoam")
    private boolean canRoam;

    @JacksonXmlProperty(localName = "disabled")
    private boolean disabled;

    @JacksonXmlProperty(localName = "blockBuildWhenDownstreamBuilding")
    private boolean blockBuildWhenDownstreamBuilding;

    @JacksonXmlProperty(localName = "blockBuildWhenUpstreamBuilding")
    private boolean blockBuildWhenUpstreamBuilding;

    @JacksonXmlProperty(localName = "triggers")
    private String triggers;

    @JacksonXmlProperty(localName = "concurrentBuild")
    private boolean concurrentBuild;

    @JacksonXmlProperty(localName = "axes")
    private String axes;

    @JacksonXmlProperty(localName = "builders")
    private String builders;

    @JacksonXmlProperty(localName = "publishers")
    private String publishers;

    @JacksonXmlProperty(localName = "buildWrappers")
    private String buildWrappers;

    @JacksonXmlProperty(localName = "executionStrategy")
    private ExecutionStrategy executionStrategy;

    @Builder
    public static class Scm {

        @JacksonXmlProperty(isAttribute = true, localName = "class")
        private String className;
    }

    @Builder
    public static class ExecutionStrategy {

        @JacksonXmlProperty(isAttribute = true, localName = "class")
        private String className;

        @JacksonXmlProperty(localName = "runSequentially")
        private boolean runSequentially;
    }
}
