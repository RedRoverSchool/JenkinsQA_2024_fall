package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineProject1Test extends BaseTest {

    @Test
    public void testCreate() {
        final String nameProject = "New Pipeline Project";

        getDriver().findElement(By.xpath("//a[contains(@href, '/view/all/newJob')]")).click();

        getDriver().findElement(By.id("name")).sendKeys(nameProject);
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[contains(@href, '/')]")).click();

        String actualNameProject = getDriver().findElement(By.xpath("//table[@id='projectstatus']/tbody/tr/td[3]/a/span")).getText();

        Assert.assertEquals(actualNameProject, nameProject);
    }
}
