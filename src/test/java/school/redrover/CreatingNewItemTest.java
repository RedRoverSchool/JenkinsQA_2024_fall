package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreatingNewItemTest extends BaseTest {

    @Test
    public void testCreateFreestyleProject() throws InterruptedException {
        WebElement newItemButton = getDriver().findElement(By.cssSelector("[href='/view/all/newJob']"));
        newItemButton.click();

        WebElement inputItemNameField = getDriver().findElement(By.className("jenkins-input"));
        inputItemNameField.sendKeys("Some name of Freestyle project");

        WebElement freestyleProjectButton = getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject"));
        freestyleProjectButton.click();

        WebElement okButton = getDriver().findElement(By.cssSelector("#ok-button"));
        okButton.click();

        WebElement dashboardButtonInBreadcrumbs = getDriver().findElement(By.xpath("//a[@class='model-link' and text()='Dashboard']"));
        dashboardButtonInBreadcrumbs.click();

        WebElement jobNameText = getDriver().findElement(By.xpath("//td/a/span[text()='Some name of Freestyle project']"));

        Assert.assertEquals(jobNameText.getText(), "Some name of Freestyle project");
    }

    @Test
    public void testCreatePipeline() {
        WebElement newItemButton = getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']"));
        newItemButton.click();

        WebElement itemNameField = getDriver().findElement(By.xpath("//input[@class='jenkins-input']"));
        itemNameField.sendKeys("Some name of Pipeline project");

        WebElement pipeline = getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']"));
        pipeline.click();

        WebElement okButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
        okButton.click();

        WebElement dashboardButtonInBreadcrumbs = getDriver().findElement(By.xpath("//*[text()='Dashboard']"));
        dashboardButtonInBreadcrumbs.click();

        WebElement jobNameText = getDriver().findElement(By.xpath("//td/a/span[text()='Some name of Pipeline project']"));

        Assert.assertEquals(jobNameText.getText(), "Some name of Pipeline project");
    }

}
