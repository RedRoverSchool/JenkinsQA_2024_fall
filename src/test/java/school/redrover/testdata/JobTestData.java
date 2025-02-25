package school.redrover.testdata;

import lombok.Getter;
import lombok.Setter;
import school.redrover.models.job.Folder;
import school.redrover.models.job.Freestyle;
import school.redrover.models.job.MultibranchPipeline;
import school.redrover.models.job.Pipeline;

import java.util.List;

public class JobTestData {

    @Setter
    @Getter
    private static Pipeline defaultPipeline = Pipeline.builder()
            .description("")
            .keepDependencies(false)
            .properties("")
            .definition(Pipeline.Definition.builder()
                    .className("org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition")
                    .script("")
                    .sandbox(true)
                    .build())
            .triggers("")
            .disabled(false)
            .build();

    @Setter
    @Getter
    private static Freestyle defaultFreestyle = Freestyle.builder()
            .keepDependencies(false)
            .properties("")
            .scm("hudson.scm.NullSCM")
            .canRoam(false)
            .disabled(false)
            .blockBuildWhenDownstreamBuilding(false)
            .blockBuildWhenUpstreamBuilding(false)
            .triggers("")
            .concurrentBuild(false)
            .builders("")
            .publishers("")
            .buildWrappers("")
            .build();

    @Setter
    @Getter
    private static Folder defaultFolder = Folder.builder()
            .description("")
            .properties("")
            .folderViews(Folder.FolderViews.builder()
                    .className("com.cloudbees.hudson.plugins.folder.views.DefaultFolderViewHolder")
                    .views(Folder.FolderViews.Views.builder()
                            .allViews(List.of(Folder.FolderViews.Views.AllView.builder()
                                    .owner(Folder.FolderViews.Views.AllView.Owner.builder()
                                            .className("com.cloudbees.hudson.plugins.folder.Folder")
                                            .reference("../../../..").build())
                                    .name("ALL")
                                    .filterExecutors(false)
                                    .filterQueue(false)
                                    .properties(Folder.FolderViews.Views.AllView.Properties.builder()
                                            .className("hudson.model.View$PropertyList").build())
                                    .build()))
                            .build())
                    .tabBar(Folder.FolderViews.TabBar.builder()
                            .className("hudson.views.DefaultViewsTabBar").build())
                    .build())
            .healthMetrics("")
            .icon(Folder.Icon.builder()
                    .className("com.cloudbees.hudson.plugins.folder.icons.StockFolderIcon").build())
            .build();

    @Setter
    @Getter
    private static MultibranchPipeline defaultMultibranchPipeline = MultibranchPipeline.builder()
            .actions("")
            .description("")
            .properties("")
            .folderViews(MultibranchPipeline.FolderViews.builder()
                    .className("jenkins.branch.MultiBranchProjectViewHolder")
                    .plugin("branch-api@2.1200.v4b_a_3da_2eb_db_4")
                    .owner(MultibranchPipeline.Owner.builder()
                            .className("org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject")
                            .reference("../..")
                            .build())
                    .build())
            .healthMetrics("")
            .icon(MultibranchPipeline.Icon.builder()
                    .className("jenkins.branch.MetadataActionFolderIcon")
                    .plugin("branch-api@2.1200.v4b_a_3da_2eb_db_4")
                    .owner(MultibranchPipeline.Owner.builder()
                            .className("org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject")
                            .reference("../..")
                            .build())
                    .build())
            .orphanedItemStrategy(MultibranchPipeline.OrphanedItemStrategy.builder()
                    .className("com.cloudbees.hudson.plugins.folder.computed.DefaultOrphanedItemStrategy")
                    .plugin("cloudbees-folder@6.955.v81e2a_35c08d3")
                    .pruneDeadBranches(true)
                    .daysToKeep(-1)
                    .numToKeep(-1)
                    .abortBuilds(false)
                    .build())
            .triggers("")
            .disabled(false)
            .sources(MultibranchPipeline.Sources.builder()
                    .className("jenkins.branch.MultiBranchProject$BranchSourceList")
                    .plugin("branch-api@2.1200.v4b_a_3da_2eb_db_4")
                    .data("")
                    .owner(MultibranchPipeline.Owner.builder()
                            .className("org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject")
                            .reference("../..")
                            .build())
                    .build())
            .factory(MultibranchPipeline.Factory.builder()
                    .className("org.jenkinsci.plugins.workflow.multibranch.WorkflowBranchProjectFactory")
                    .owner(MultibranchPipeline.Owner.builder()
                            .className("org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject")
                            .reference("../..")
                            .build())
                    .scriptPath("Jenkinsfile")
                    .build())
            .build();
}
