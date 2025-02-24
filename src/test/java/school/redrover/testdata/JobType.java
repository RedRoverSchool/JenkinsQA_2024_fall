package school.redrover.testdata;

import lombok.Getter;
import school.redrover.models.job.Folder;
import school.redrover.models.job.Freestyle;
import school.redrover.models.job.Pipeline;

import java.util.List;

@Getter
public enum JobType {
    PIPELINE(Pipeline.builder()
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
            .build()),
    FREESTYLE(Freestyle.builder()
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
            .build()),
    FOLDER(Folder.builder()
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
            .build());

    private final Object projectConfig;

    JobType(Object projectConfig) {
        this.projectConfig = projectConfig;
    }

}
