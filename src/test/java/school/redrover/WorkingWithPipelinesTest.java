package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class WorkingWithPipelinesTest extends BaseTest {

    private void createItemUtils(String name, String locator) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.cssSelector(locator)).click();
        getDriver().findElement(By.id("ok-button")).click();

    }

    @Test
    public void testSearchPipelineOnMainPage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        final String nameRegressionPipeLine = "Regression";

        createItemUtils(nameRegressionPipeLine, ".org_jenkinsci_plugins_workflow_job_WorkflowJob");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("jenkins-home-link")))
                .click();

        String PipeLineText = getDriver().findElement(By.xpath("//a[@href='job/Regression/']/span"))
                .getText();

        Assert.assertEquals(PipeLineText, nameRegressionPipeLine, "Пайплайн не найден на главной странице или текст не совпадает");

        System.out.println("Пайплайн создан и находится в списке на главной странице");
    }
}
