package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineProject6Test extends BaseTest {

    @Test
    private void testCreate() {

        final String pipelineName = "Pipeline for mighty test";

        getDriver().findElement(By.xpath("//a[@it]")).click();
        getDriver().findElement(By.name("name")).sendKeys(pipelineName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[@class]")).getText(),
                pipelineName);
    }

    @Test
    private void testDelete() {

        testCreate();

        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.xpath("//a[@href]/span[contains(text(), 'Pipeline')]")).click();
        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();
        try {
            getDriver().findElement(By.xpath("//a[@href]/span[contains(text(), 'Pipeline')]"));
        } catch (NoSuchElementException e) {

        }
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1")).getText(),
                "Welcome to Jenkins!"
        );
    }
}
