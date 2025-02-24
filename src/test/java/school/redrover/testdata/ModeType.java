package school.redrover.testdata;

import lombok.Getter;

@Getter
public enum ModeType {

    FREESTYLE_PROJECT_MODE("hudson.model.FreeStyleProject"),
    ORGANIZATION_FOLDER_MODE("jenkins.branch.OrganizationFolder"),
    MULTIBRANCH_PIPELINE_MODE("org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject"),
    FOLDER_MODE("com.cloudbees.hudson.plugins.folder.Folder"),
    PIPELINE_MODE("org.jenkinsci.plugins.workflow.job.WorkflowJob"),
    MULTI_CONFIGURATION_PROJECT_MODE("hudson.matrix.MatrixProject"),
    NONE(null);

    private final String mode;

    ModeType(String mode) {
        this.mode = mode;
    }
    }
