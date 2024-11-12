package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class PipelineProject4Test extends BaseTest {

    @Test
    public void testCreatePipeline() {

        final String PROJECT_NAME = "ProjectOne";
        final String NEW_NAME = "ProjectRenamed";

        getDriver().findElement(By.xpath("//li[@class = 'content-block']//span[contains(text(), 'Create a job')]")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.cssSelector(".org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getDriver().findElement(By.cssSelector(".jenkins-button.jenkins-submit-button.jenkins-button--primary ")).click();

        getDriver().findElement(By.id("jenkins-head-icon")).click();

        WebElement createdPipeline = getDriver().findElement(By.xpath(String.format("//td/a[@href = 'job/%s/']", PROJECT_NAME)));

        WebElement chevron = getDriver().findElement(By.xpath("//a[@href = 'job/ProjectOne/']/button[@class = 'jenkins-menu-dropdown-chevron']"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(createdPipeline)
                .pause(Duration.ofMillis(2000))
                .moveToElement(chevron)
                .click()
                .perform();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'rename')]"))).click();

        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys("ProjectRenamed");

        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-head-icon")).click();

        String renamedText = getDriver().findElement(By.xpath(String.format("//td/a[@href = 'job/%s/']", NEW_NAME))).getText();

        Assert.assertEquals(renamedText, NEW_NAME);
    }

}
