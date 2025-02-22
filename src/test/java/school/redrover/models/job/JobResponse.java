package school.redrover.models.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobResponse {

    @JsonProperty("_class")
    private String className;
    private List<Object> actions;
    private String description;
    private String displayName;
    private String displayNameOrNull;
    private String fullDisplayName;
    private String fullName;
    private String name;
    private String url;
    private boolean buildable;
    private List<Object> builds;
    private String color;
    private Object firstBuild;
    private List<Object> healthReport;
    private boolean keepDependencies;
    private Object lastBuild;
    private Object lastCompletedBuild;
    private Object lastFailedBuild;
    private Object lastStableBuild;
    private Object lastSuccessfulBuild;
    private Object lastUnstableBuild;
    private Object lastUnsuccessfulBuild;
    private int nextBuildNumber;
    private List<Object> property;
    private boolean concurrentBuild;
    private boolean inQueue;
    private Object queueItem;
    private boolean resumeBlocked;
    private boolean disabled;
}
