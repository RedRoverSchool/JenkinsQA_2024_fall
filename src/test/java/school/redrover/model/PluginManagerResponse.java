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
public class PluginManagerResponse {
    @JsonProperty("_class")
    private String classField;

    private List<Plugin> plugins;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Plugin {
        private boolean active;
        private String backupVersion;
        private boolean bundled;
        private boolean deleted;
        private List<Object> dependencies;
        private boolean detached;
        private boolean downgradable;
        private boolean enabled;
        private boolean hasUpdate;
        private String longName;
        private boolean pinned;
        private String requiredCoreVersion;
        private String shortName;
        private String supportsDynamicLoad;
        private String url;
        private String version;
    }
}
