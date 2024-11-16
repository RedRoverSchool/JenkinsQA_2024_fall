package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class PipelineStagesTest extends BaseTest {

    private static final List<String> PIPELINE_STAGES = List.of("Start", "Build", "Test", "End");
    private static final String PIPELINE_SCRIPT = """
            pipeline {agent any\n stages {
            stage('Build') {steps {echo 'Building the application'}}
            stage('Test') {steps {error 'Test stage failed due to an error'}}
            }
            """;

    private void createPipeline(String pipelineName) {

        getDriver().findElement(By.cssSelector("[href$='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.cssSelector("button[formnovalidate='formNoValidate']")).click();
    }

    private void addScriptToPipeline(String script) {

        getDriver().findElement(By.cssSelector("a[href$='/configure']")).click();
        TestUtils.scrollToBottom(getDriver());
        getDriver().findElement(By.cssSelector("textarea[class='ace_text-input']")).sendKeys(script);
        getDriver().findElement(By.cssSelector("button[formnovalidate='formNoValidate']")).click();
    }

    @Test
    public void testListOfRecentBuildsISDisplayedOnStages() {

        createPipeline("TestPipeline");

        getDriver().findElement(By.cssSelector("a[data-build-success='Build scheduled']")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[tooltip$='> Console Output']")));

        getDriver().findElement(By.cssSelector("a[href$='multi-pipeline-graph']")).click();

        List<WebElement> pipelineBuilds = getDriver().findElements(By.cssSelector("a[href$='/pipeline-graph/']"));

        Assert.assertFalse(pipelineBuilds.isEmpty());
    }

    @Test
    public void testStagesAreDisplayedInPipelineGraph() {

        createPipeline("TestPipeline");
        addScriptToPipeline(PIPELINE_SCRIPT);

        getDriver().findElement(By.cssSelector("a[data-build-success='Build scheduled']")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[tooltip$='> Console Output']")));

        getDriver().findElement(By.cssSelector("a[href$='multi-pipeline-graph']")).click();

        List<WebElement> stages = getDriver().findElements(By.cssSelector("div[class^='TruncatingLabel']"));
        List<String> actualStagesNames = stages.stream().map(el-> el.getAttribute("title")).toList();

        Assert.assertEquals(actualStagesNames, PIPELINE_STAGES);
    }

    @Test
    public void testStatusIconsAreDisplayedInPipelineGraph() {

        createPipeline("TestPipeline");
        addScriptToPipeline(PIPELINE_SCRIPT);

        getDriver().findElement(By.cssSelector("a[data-build-success='Build scheduled']")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[tooltip$='> Console Output']")));

        getDriver().findElement(By.cssSelector("a[href$='multi-pipeline-graph']")).click();

        WebElement greenIcon = getDriver().findElement(By.cssSelector("g[class$='icon-blue']"));
        WebElement redIcon = getDriver().findElement(By.cssSelector("g[class$='icon-red']"));

        Assert.assertTrue(greenIcon.isDisplayed() && redIcon.isDisplayed());
    }

    @Test
    public void testStatusIconsColor() {

        createPipeline("TestPipeline");
        addScriptToPipeline(PIPELINE_SCRIPT);

        getDriver().findElement(By.cssSelector("a[data-build-success='Build scheduled']")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[tooltip$='> Console Output']")));

        getDriver().findElement(By.cssSelector("a[href$='multi-pipeline-graph']")).click();

        WebElement greenIcon = getDriver().findElement(By.cssSelector("g[class$='icon-blue']"));
        WebElement redIcon = getDriver().findElement(By.cssSelector("g[class$='icon-red']"));

        Assert.assertEquals(greenIcon.getCssValue("color"), "rgba(30, 166, 75, 1)");
        Assert.assertEquals(redIcon.getCssValue("color"),  "rgba(230, 0, 31, 1)");
    }
}
