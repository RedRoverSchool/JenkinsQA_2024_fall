package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class SearchPipelineOnMainPageTest extends BaseTest {

    @Test
    public void CreateAndFindOnMainPage() throws InterruptedException {

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        Thread.sleep(300);

        String namePipeLine = "Regression";
        getDriver().findElement(By.id("name")).sendKeys(namePipeLine);

        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        getDriver().findElement(By.id("ok-button")).click();
        Thread.sleep(300);

        getDriver().findElement(By.name("Submit")).click();
        Thread.sleep(300);


        getDriver().findElement(By.id("jenkins-home-link")).click();
        Thread.sleep(300);

        WebElement elementWithText = getDriver().findElement(By.xpath("//a[@href='job/Regression/']/span"));
        String spanText = elementWithText.getText();

        if (spanText.equals(namePipeLine)) {
            System.out.println("Пайплайн создан и находится в списке на главной странице");
        } else {
            System.out.println("Что то пошло не так");
        }
    }
}
