package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.Arrays;

public class CreateNewPipelineTest extends BaseTest {

    // Test: https://www.jenkins.io/doc/book/pipeline/getting-started/
    @Test
    public void testCreateNewPipeline() {
        String jobName = "test-job";
        clickElement("//a[@href='/view/all/newJob']");
        clickElement("//input[@class='jenkins-input']");
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys(jobName);
        clickElement("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']");
        clickElement("//button[@id='ok-button']");
        clickElement("//div[@class='samples']/select/option[1]");
        clickElement("//option[@value='hello']");
        clickElement("//button[@name='Submit']");
        clickElement("//a[@href='/job/%s/build?delay=0sec']".formatted(jobName));
        clickElement("//a[@tooltip='Success > Console Output']");

        var successResult = Arrays.stream(getDriver()
                .findElement(By.xpath("//pre[@class='console-output']"))
                .getText()
                .split("\n"))
                .toList();
        Assert.assertListContainsObject(successResult, "Hello World", "Could not find Pipeline 'Hello World'");
        Assert.assertListContainsObject(successResult, "Finished: SUCCESS", "Could not find 'Finished: SUCCESS'");
    }

    private void clickElement(String xpath) {
        getDriver().findElement(By.xpath(xpath)).click();
    }
}
