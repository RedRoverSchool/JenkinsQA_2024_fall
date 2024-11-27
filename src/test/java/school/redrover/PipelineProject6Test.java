package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineProject6Test extends BaseTest {

    final private String PIPELINE_NAME = "PipelineForMightyTest";
    final private String PIPELINE_RENAMED = "RenamedPipeline";

    @Test
    public void testCreate() {
        getDriver().findElement(By.xpath("//a[@it]")).click();
        getDriver().findElement(By.name("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[@class]")).getText(),
                PIPELINE_NAME);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testRename() {
        getDriver().findElement(By.xpath("//a[@href]/span[contains(text(), '" + PIPELINE_NAME + "')]")).click();
        getDriver().findElement(By.xpath("//span[text()='Rename']/..")).click();
        getDriver().findElement(By.xpath("//input[@checkdependson]"))
                .sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        getDriver().findElement(By.xpath("//input[@checkdependson]")).sendKeys(PIPELINE_RENAMED);
        getDriver().findElement(By.xpath("//button[@formnovalidate]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(),
                PIPELINE_RENAMED);
    }


    @Test(dependsOnMethods = "testRename")
    public void testDelete() {
        getDriver().findElement(By.xpath("//a[@href]/span[contains(text(), '" + PIPELINE_RENAMED + "')]")).click();
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
