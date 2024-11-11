package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class PipelineProject3Test extends BaseTest {

    private static final String PIPELINE_NAME = "Pipeline_name";
    private static final By ADVANCED_PROJECT_OPTIONS_MENU = By.xpath("//button[@data-section-id='advanced-project-options']");

    private void createPipelineProject(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void returnToHomePage() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void clickJobByName(String projectName) {
        getDriver().findElement(By.xpath("//td/a[@href='job/" + projectName + "/']")).click();
    }

    public void navigateToConfigurePageFromDashboard(String projectName) {
        clickJobByName(projectName);
        getDriver().findElement(By.xpath("//a[contains(@href, 'configure')]")).click();
    }

    private static WebDriverWait getWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickOnAdvancedButton() {
        WebElement advancedButton = getDriver().findElement(By.xpath("//section[@class='jenkins-section']//button[@type='button']"));

        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].dispatchEvent(new Event('click'));",
                advancedButton);
    }

    @Test
    public void testVerifySectionHasTooltip() {
        String labelText = "Display Name";
        String tooltipText = "Help for feature: Display Name";

        createPipelineProject(PIPELINE_NAME);
        returnToHomePage();

        navigateToConfigurePageFromDashboard(PIPELINE_NAME);
        getWait(getDriver()).until(ExpectedConditions.elementToBeClickable(ADVANCED_PROJECT_OPTIONS_MENU)).click();
        clickOnAdvancedButton();

        String actualTooltip = getDriver().findElement(By.xpath("//*[contains(text(), '" + labelText + "')]//a")).getAttribute("tooltip");
        Assert.assertEquals(actualTooltip, tooltipText);
    }

}
