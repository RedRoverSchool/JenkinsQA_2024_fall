package school.redrover.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderResponse {
    private String _class;
    private String description;
    private String displayName;
    private String displayNameOrNull;
    private String fullDisplayName;
    private String fullName;
    private String name;
    private String url;
}


