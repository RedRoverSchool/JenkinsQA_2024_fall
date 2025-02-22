package school.redrover.testdata;

import lombok.Getter;
import school.redrover.models.job.Freestyle;
import school.redrover.models.job.Pipeline;

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
            .build());

    private final Object projectConfig;

    JobType(Object projectConfig) {
        this.projectConfig = projectConfig;
    }

}
