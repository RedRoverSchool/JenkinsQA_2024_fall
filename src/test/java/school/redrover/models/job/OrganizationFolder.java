package school.redrover.models.job;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
@JacksonXmlRootElement(localName = "jenkins.branch.OrganizationFolder")
public class OrganizationFolder implements JobType {

    @JacksonXmlProperty(isAttribute = true)
    private String plugin;

    @JacksonXmlProperty(localName = "actions")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Action> actions;

    @JacksonXmlProperty(localName = "description")
    private String description;

    @JacksonXmlProperty(localName = "properties")
    private Properties properties;

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

    @JacksonXmlProperty(localName = "navigators")
    private String navigators;

    @JacksonXmlProperty(localName = "projectFactories")
    private ProjectFactories projectFactories;

    @JacksonXmlProperty(localName = "buildStrategies")
    private String buildStrategies;

    @JacksonXmlProperty(localName = "strategy")
    private Strategy strategy;

    @JacksonXmlProperty(localName = "disabled")
    private boolean disabled;

    @Data
    @Builder
    public static class Properties {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "jenkins.branch.OrganizationChildHealthMetricsProperty")
        private String healthMetricsProperty;

        @JacksonXmlProperty(localName = "jenkins.branch.OrganizationChildOrphanedItemsProperty")
        private String orphanedItemsProperty;

        @JacksonXmlProperty(localName = "jenkins.branch.OrganizationChildTriggersProperty")
        private String triggersProperty;

        @JacksonXmlProperty(localName = "jenkins.branch.NoTriggerOrganizationFolderProperty")
        private String noTriggerProperty;
    }

    @Data
    @Builder
    public static class FolderViews {
        @JacksonXmlProperty(localName = "class")
        private String className;

        @JacksonXmlProperty(localName = "plugin")
        private String plugin;

        @JacksonXmlProperty(localName = "owner")
        private Owner owner;
    }

    @Data
    @Builder
    public static class Owner {
        @JacksonXmlProperty(localName = "class")
        private String className;

        @JacksonXmlProperty(localName = "reference")
        private String reference;
    }

    @Data
    @Builder
    public static class Icon {
        @JacksonXmlProperty(localName = "class")
        private String className;

        @JacksonXmlProperty(localName = "plugin")
        private String plugin;

        @JacksonXmlProperty(localName = "owner")
        private Owner owner;
    }

    @Data
    @Builder
    public static class OrphanedItemStrategy {
        @JacksonXmlProperty(localName = "class")
        private String className;

        @JacksonXmlProperty(isAttribute = true)
        private String plugin;

        @JacksonXmlProperty(localName = "pruneDeadBranches")
        private boolean pruneDeadBranches;

        @JacksonXmlProperty(localName = "daysToKeep")
        private int daysToKeep;

        @JacksonXmlProperty(localName = "numToKeep")
        private int numToKeep;

        @JacksonXmlProperty(localName = "abortBuilds")
        private boolean abortBuilds;
    }

    @Data
    @Builder
    public static class ProjectFactories {
        @JacksonXmlProperty(localName = "org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProjectFactory")
        private WorkflowMultiBranchProjectFactory workflowFactory;
    }

    @Data
    @Builder
    public static class WorkflowMultiBranchProjectFactory {
        @JacksonXmlProperty(localName = "scriptPath")
        private String scriptPath;
    }

    @Data
    @Builder
    public static class Strategy {
        @JacksonXmlProperty(localName = "properties")
        private String properties;
    }

    @Data
    @Builder
    public static class Action {
        @JacksonXmlProperty(localName = "someField")
        private String action;
    }
}
