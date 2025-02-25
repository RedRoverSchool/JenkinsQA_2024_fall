package school.redrover.models.job;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JacksonXmlRootElement(localName = "project")
public class Freestyle implements JobType  {
    @JacksonXmlProperty(localName = "keepDependencies")
    private boolean keepDependencies;

    @JacksonXmlProperty(localName = "properties")
    private String properties;

    @JacksonXmlProperty(localName = "scm", isAttribute = true)
    private String scm;

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

    @JacksonXmlProperty(localName = "builders")
    private String builders;

    @JacksonXmlProperty(localName = "publishers")
    private String publishers;

    @JacksonXmlProperty(localName = "buildWrappers")
    private String buildWrappers;
}