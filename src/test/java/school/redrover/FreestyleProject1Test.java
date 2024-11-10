package school.redrover;

import com.beust.ah.A;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject1Test extends BaseTest {
    private static final String NEW_FREESTYLE_PROJECT_NAME = "New freestyle project";
    private void createFreestyleProject() {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//*[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.name("name")).sendKeys(NEW_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

    }
    @Test
    public void testCreateFreestyleProject() throws InterruptedException {
        createFreestyleProject();

        getDriver().findElement(By.id("jenkins-name-icon")).click();
        WebElement element = getDriver().findElement(By
                .xpath("//span[contains(text(),'" + NEW_FREESTYLE_PROJECT_NAME + "')]"));
        Assert.assertTrue(element.isDisplayed());
    }

    @Test
    public void testDeleteFreestyleProject() throws InterruptedException {
        createFreestyleProject();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Actions actions = new Actions(getDriver());
        WebElement element = getDriver().findElement(By
                .xpath("//span[contains(text(),'" + NEW_FREESTYLE_PROJECT_NAME + "')]"));
        actions.moveToElement(element).click().perform();
        getDriver().findElement((By.xpath("//*[@data-title='Delete Project']"))).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();
        String emptyDashboardHeader = getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText();
        Assert.assertEquals(emptyDashboardHeader, "Welcome to Jenkins!");
    }

    @Test
    public void testRenameFreestyleProject() throws InterruptedException {
        createFreestyleProject();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Actions actions = new Actions(getDriver());
        WebElement element = getDriver().findElement(By
                .xpath("//span[contains(text(),'" + NEW_FREESTYLE_PROJECT_NAME + "')]"));
        actions.moveToElement(element).click().perform();
        getDriver().findElement((By.xpath("//*[contains(@href,'confirm-rename')]"))).click();
        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        Thread.sleep(200);
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys("Renamed freestyle project");
        getDriver().findElement(By.name("Submit")).click();
        String projectName = getDriver().findElement(By.xpath("//*[@class='job-index-headline page-headline']")).getText();
        Assert.assertEquals(projectName, "Renamed freestyle project");
    }

    @Test
    public void testAddFreestyleProjectDescription() {
        createFreestyleProject();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Actions actions = new Actions(getDriver());
        WebElement element = getDriver().findElement(By
                .xpath("//span[contains(text(),'" + NEW_FREESTYLE_PROJECT_NAME + "')]"));
        actions.moveToElement(element).click().perform();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys("Some description");
        getDriver().findElement(By.name("Submit")).click();
        String description = getDriver().findElement(By.id("description")).getText();
        Assert.assertEquals(description, "Some description");
    }
}