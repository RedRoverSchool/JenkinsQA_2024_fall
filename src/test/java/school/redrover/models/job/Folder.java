package school.redrover.models.job;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JacksonXmlRootElement(localName = "com.cloudbees.hudson.plugins.folder.Folder")
public class Folder implements JobType {

    @JacksonXmlProperty(isAttribute = true, localName = "plugin")
    private String plugin;

    @JacksonXmlProperty(localName = "description")
    private String description;

    @JacksonXmlProperty(localName = "properties")
    private String properties;

    @JacksonXmlProperty(localName = "folderViews")
    private FolderViews folderViews;

    @JacksonXmlProperty(localName = "healthMetrics")
    private String healthMetrics;

    @JacksonXmlProperty(localName = "icon")
    private Icon icon;

    @Builder
    public static class FolderViews {

        @JacksonXmlProperty(localName = "class")
        private String className;

        @JacksonXmlProperty(localName = "views")
        private Views views;

        @JacksonXmlProperty(localName = "tabBar")
        private TabBar tabBar;

        @Builder
        public static class Views {

            @JacksonXmlElementWrapper(useWrapping = false)
            @JacksonXmlProperty(localName = "hudson.model.AllView")
            private List<AllView> allViews;

            @Builder
            public static class AllView {

                @JacksonXmlProperty(localName = "owner")
                private Owner owner;

                @JacksonXmlProperty(localName = "name")
                private String name;

                @JacksonXmlProperty(localName = "filterExecutors")
                private boolean filterExecutors;

                @JacksonXmlProperty(localName = "filterQueue")
                private boolean filterQueue;

                @JacksonXmlProperty(localName = "properties")
                private Properties properties;

                @Builder
                public static class Owner {

                    @JacksonXmlProperty(isAttribute = true, localName = "class")
                    private String className;

                    @JacksonXmlProperty(isAttribute = true, localName = "reference")
                    private String reference;
                }

                @Builder
                public static class Properties {

                    @JacksonXmlProperty(isAttribute = true, localName = "class")
                    private String className;
                }
            }
        }

        @Builder
        public static class TabBar {

            @JacksonXmlProperty(isAttribute = true, localName = "class")
            private String className;
        }
    }

    @Builder
    public static class Icon {

        @JacksonXmlProperty(isAttribute = true, localName = "class")
        private String className;
    }
}
