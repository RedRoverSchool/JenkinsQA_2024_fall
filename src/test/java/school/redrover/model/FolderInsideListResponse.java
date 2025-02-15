package school.redrover.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderInsideListResponse {
    @JsonProperty("_class")
    private String classField;
    private String description;
    private String displayName;
    private String displayNameOrNull;
    private String fullDisplayName;
    private String fullName;
    private String name;
    private String url;
    private List<Project> jobs;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Project {
        @JsonProperty("_class")
        private String classField;
        private String name;
        private String url;
        private String color;
    }
}

