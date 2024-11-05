package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class FreestyleProject1Test extends BaseTest {
    private static final String NEW_FREESTYLE_PROJECT_NAME = "New freestyle project";

    private void createFreestyleProject() throws InterruptedException {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//*[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.name("name")).sendKeys(NEW_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

    }

    @Test
    public void testCreateFreestyleProject() throws InterruptedException {
        createFreestyleProject();

        //Getting to the main page to ensure that newly created project is there
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        WebElement element = getDriver().findElement(By
                .xpath("//span[contains(text(),'New freestyle project')]"));
        Assert.assertTrue(element.isDisplayed());
    }

    @Test
    public void testDeleteFreestyleProject() throws InterruptedException {
        createFreestyleProject();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Actions actions = new Actions(getDriver());
        //hover over project title to activate menu dropdown
        WebElement element = getDriver().findElement(By
                .xpath("//span[contains(text(),'New freestyle project')]"));
        actions.moveToElement(element).perform();
        getDriver().findElement((By.cssSelector("td:nth-child(3) > a > button"))).click();
        getDriver().findElement((By.cssSelector("#tippy-6 > div > div > div > button:nth-child(5)"))).click();
        getDriver().findElement(By.xpath("//button[contains(text(),'Yes')]")).click();
        String emptyDashboardHeader = getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText();
        Assert.assertEquals(emptyDashboardHeader, "Welcome to Jenkins!");
    }
}