package school.redrover.testdata;

import school.redrover.models.jobs.Pipeline;

public class TestData {

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

}
