package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.AssertJUnit.assertTrue;

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

    private void createProject(String projectName) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[text() = 'Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        goToMainPage();
    }

    private void goToMainPage() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    private void clickJobByName(String PROJECT_NAME) {
        getDriver().findElement(By.xpath("//td/a[@href='job/" + PROJECT_NAME + "/']")).click();
    }
    @Test
    public void testCreateProjectWithInvalidScript() {

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
        goToMainPage();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#projectstatus")));

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[text()='%s']".formatted(PROJECT_NAME))).getText(),
                "MyProjectPipeline");
    }

    @Test
    public void testCreate() {

        createProject(PROJECT_NAME);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#projectstatus")));

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[text()='%s']".formatted(PROJECT_NAME))).getText(),
                "MyProjectPipeline");
    }

    @Test(dependsOnMethods = "testCreate")
    public void testRenameProjectWithSameName() {

        clickJobByName(PROJECT_NAME);
        getDriver().findElement(By.xpath("//a[contains(., 'Rename')]")).click();

        Actions myAction = new Actions(getDriver());
        myAction.doubleClick(getDriver().findElement(By.name("newName"))).perform();

        getDriver().findElement(By.name("newName")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.name("Submit")).click();

        assertTrue(getDriver().findElement(By.xpath("//*[text()='Error']")).isDisplayed());
    }

    @Test(dependsOnMethods = "testRenameProjectWithSameName")
    public void testDeleteFromInsideProject() {

        clickJobByName(PROJECT_NAME);
        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText(),
                "Welcome to Jenkins!");
    }
}
