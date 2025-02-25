package school.redrover.models.job;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JacksonXmlRootElement(localName = "org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject")
public class MultibranchPipeline implements JobType {

    @JacksonXmlProperty(isAttribute = true, localName = "plugin")
    private String plugin;

    @JacksonXmlProperty(localName = "actions")
    private String actions;

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

    @JacksonXmlProperty(localName = "orphanedItemStrategy")
    private OrphanedItemStrategy orphanedItemStrategy;

    @JacksonXmlProperty(localName = "triggers")
    private String triggers;

    @JacksonXmlProperty(localName = "disabled")
    private boolean disabled;

    @JacksonXmlProperty(localName = "sources")
    private Sources sources;

    @JacksonXmlProperty(localName = "factory")
    private Factory factory;

    @JacksonXmlProperty(localName = "text")
    private String text;

    @Builder
    public static class FolderViews {

        @JacksonXmlProperty(localName = "owner")
        private Owner owner;

        @JacksonXmlProperty(localName = "class")
        private String className;

        @JacksonXmlProperty(localName = "plugin")
        private String plugin;
    }

    @Builder
    public static class Owner {

        @JacksonXmlProperty(localName = "class")
        private String className;

        @JacksonXmlProperty(localName = "reference")
        private String reference;
    }

    @Builder
    public static class Icon {

        @JacksonXmlProperty(localName = "owner")
        private Owner owner;

        @JacksonXmlProperty(localName = "class")
        private String className;

        @JacksonXmlProperty(localName = "plugin")
        private String plugin;
    }

    @Builder
    public static class OrphanedItemStrategy {

        @JacksonXmlProperty(localName = "pruneDeadBranches")
        private boolean pruneDeadBranches;

        @JacksonXmlProperty(localName = "daysToKeep")
        private int daysToKeep;

        @JacksonXmlProperty(localName = "numToKeep")
        private int numToKeep;

        @JacksonXmlProperty(localName = "abortBuilds")
        private boolean abortBuilds;

        @JacksonXmlProperty(localName = "class")
        private String className;

        @JacksonXmlProperty(localName = "plugin")
        private String plugin;

        @JacksonXmlProperty(localName = "text")
        private String text;
    }

    @Builder
    public static class Sources {

        @JacksonXmlProperty(localName = "data")
        private Object data;

        @JacksonXmlProperty(localName = "owner")
        private Owner owner;

        @JacksonXmlProperty(localName = "class")
        private String className;

        @JacksonXmlProperty(localName = "plugin")
        private String plugin;
    }

    @Builder
    public static class Factory {

        @JacksonXmlProperty(localName = "owner")
        public Owner owner;

        @JacksonXmlProperty(localName = "scriptPath")
        public String scriptPath;

        @JacksonXmlProperty(localName = "class")
        public String className;

        @JacksonXmlProperty(localName = "text")
        public String text;
    }
}