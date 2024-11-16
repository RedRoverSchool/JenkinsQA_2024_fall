package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineProject5Test extends BaseTest {

    private static final String PROJECT_NAME = "MyProjectPipeline";

        String pipelineInfo = """
        pipeline {
        agent any

        stages {
        stage('Build') {
        steps {
        sh 'mvn clean compile'
        }
        }
        }

        post {
        always {
        echo 'Cleaning up workspace'
        cleanWs()
        }
        }
        }
        """;


    @Test
    public void testCreate() {

        getDriver().findElement(By.xpath("//span[text() = 'New Item']/..")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[text() = 'Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("general")));

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        WebElement inputTextArea = getDriver().findElement(By.id("workflow-editor-1"));
        inputTextArea.click();
        getDriver().findElement(By.xpath("//textarea[@class = 'ace_text-input']"))
                .sendKeys(pipelineInfo);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#main-panel h2")));
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#projectstatus")));

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[text()='%s']".formatted(PROJECT_NAME))).getText(),
                "MyProjectPipeline");
    }

}
