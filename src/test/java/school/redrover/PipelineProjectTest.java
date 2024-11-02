package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;


public class PipelineProjectTest extends BaseTest {

    @Test
    public void testCreatePipelineProjectWithValidNameViaSidebar() {
        final String pipelineName = "Pipeline";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();

        List<String> actualJobList = new ArrayList<>();
        List<WebElement> jobList = getDriver().findElements(By.xpath("//td/a[contains(@href,'job/')]"));
        for(WebElement job:jobList){
            actualJobList.add(job.getText());
        }

        Assert.assertListContainsObject(actualJobList, pipelineName, "Job is not found");
    }
}
