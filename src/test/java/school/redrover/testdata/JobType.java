package school.redrover.testdata;

import lombok.Getter;

@Getter
public enum JobType {
    PIPELINE(TestData.PIPELINE),
    FREESTYLE(TestData.FREESTYLE),
    FOLDER(TestData.FOLDER);

    private final Object projectConfig;

    JobType(Object projectConfig) {
        this.projectConfig = projectConfig;
    }
}