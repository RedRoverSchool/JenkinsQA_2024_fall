package school.redrover.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FreestyleAndPipelineResponse {
    @JsonProperty("_class")
    private String classField;
    private Object description;
    private String displayName;
    private Object displayNameOrNull;
    private String fullDisplayName;
    private String fullName;
    private String name;
    private String url;
}
