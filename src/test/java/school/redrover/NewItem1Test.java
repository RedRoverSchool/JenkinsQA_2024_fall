package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;

public class NewItem1Test extends BaseTest {

    private static final String Project_Name = "Project name";

    private void createProjectInPage(String projectName) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary jenkins-buttons-row--equal-width']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void returnToHomePage() {

        getDriver().findElement(By.xpath("//img[@alt='Jenkins']")).click();
    }

    @Test
    public void testAddDescriptionToProject() {
        final String enterProjectDescription= "Check case in project";
        createProjectInPage(Project_Name);
        returnToHomePage();

        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.xpath("//*[@id='description']/form/div[1]/div[1]/textarea")).sendKeys(enterProjectDescription);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='description']/div[1]"))
                .getText(),enterProjectDescription);
    }
/*
    @Test
    public void testDeleteProjectInDropdownMenu() {
        createProjectInPage(Project_Name);
        returnToHomePage();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        Actions actions = new Actions(getDriver());

        WebElement projectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='job/Project%20name/']")));

        actions.moveToElement(projectElement, projectElement.getSize().width/2,projectElement.getSize().height/2)
                .pause(3000).perform();

        WebElement chevronButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='job/Project%20name/']//button[@class='jenkins-menu-dropdown-chevron']")));

        actions.moveToElement(chevronButton).click().perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='jenkins-dropdown']")));

        getDriver().findElement(By.xpath("//button[@href='/job/Project%20name/doDelete']")).click();
        getDriver().findElement(By.xpath("//*[@id='jenkins']/dialog/div[3]/button[1]")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Welcome to Jenkins!");
    }
*/
    @Test
    public void testDeleteProjeckInSidebar() {
        createProjectInPage(Project_Name);
        returnToHomePage();

        getDriver().findElement(By.xpath("//a[@href='job/Project%20name/']")).click();
        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Welcome to Jenkins!");

    }
}
