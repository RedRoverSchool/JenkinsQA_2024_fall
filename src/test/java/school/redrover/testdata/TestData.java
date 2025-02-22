package school.redrover.testdata;

import school.redrover.models.job.Freestyle;
import school.redrover.models.job.Pipeline;
import school.redrover.models.user.User;

import java.util.UUID;

public class TestData {

    private static final String USER_NAME = "user_" + UUID.randomUUID().toString().substring(0, 8);

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

    public static final User USER = User.builder()
            .username(USER_NAME)
            .password("Pass@" + UUID.randomUUID().toString().substring(0, 6))
            .email(USER_NAME + "@example.com").build();

}
