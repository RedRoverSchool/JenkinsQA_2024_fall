package school.redrover.testdata;

import school.redrover.models.job.Folder;
import school.redrover.models.job.Freestyle;
import school.redrover.models.job.Pipeline;
import school.redrover.models.user.User;

import java.util.List;
import java.util.UUID;

public class TestData {

    private static final String USER_NAME = "user_" + UUID.randomUUID().toString().substring(0, 8);

    public static final User USER = User.builder()
            .username(USER_NAME)
            .password("Pass@" + UUID.randomUUID().toString().substring(0, 6))
            .email(USER_NAME + "@example.com").build();

    public static final Pipeline PIPELINE = Pipeline.builder()
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

    public static final Freestyle FREESTYLE = Freestyle.builder()
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

    public static final Folder FOLDER = Folder.builder()
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
}
